package com.keencho.lib.orm.jpa.querydsl;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.QSort;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KcSearchQueryImpl<E> implements KcSearchQuery<E> {

    private static final EntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;

    private final EntityPath<E> path;
    private final Querydsl querydsl;
    private final PathBuilder<?> builder;
    private final EntityManager entityManager;

    public KcSearchQueryImpl(JpaEntityInformation<E, ?> entityInformation, EntityManager entityManager) {
        this.path = resolver.createPath(entityInformation.getJavaType());
        this.builder = new PathBuilder<E>(this.path.getType(), this.path.getMetadata());
        this.querydsl = new Querydsl(entityManager, this.builder);
        this.entityManager = entityManager;
    }

    //////////////////////////////////////////////////////////////// public method area

    @Override
    public List<E> findList(Predicate predicate, KcJoinHelper joinHelper, QSort sort) {
        JPQLQuery<E> query = this.createQuery(predicate).select(this.path);

        if (joinHelper != null) {
            query = joinHelper.join(query);
        }

        if (sort != null) {
            query = this.sort(null, sort, query);
        }

        return query.fetch();
    }

    @Override
    public <P> List<P> findList(Predicate predicate, Class<P> projectionType, KcJoinHelper joinHelper, QSort sort) {
        KcBindingGenerator bindGenerator = projectionType.getAnnotation(KcBindingGenerator.class);

        if (bindGenerator == null) {
            throw new RuntimeException("BindGenerator annotation must not be null");
        }

        Map<String, Expression<?>> bindings = Stream.of(projectionType.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(field -> {
                    KcBindingGenerator kcBindingGenerator = field.getAnnotation(KcBindingGenerator.class);
                    if (kcBindingGenerator == null) {
                        return true;
                    }

                    return !kcBindingGenerator.except();
                })
                .collect(Collectors.toList())
                .stream()
                .map(field1 -> Stream.of(this.path.getClass().getDeclaredFields())
                        .filter(field2 -> field2.getName().equals(field1.getName()))
                        .findFirst()
                        .orElse(null)
                )
                .filter(Objects::nonNull)
                .filter(field -> {
                    field.setAccessible(true);
                    try {
                        Object object = field.get(this.path);
                        return !(object instanceof EntityPathBase);
                    } catch (IllegalAccessException e) {
                        throw new IllegalArgumentException("error occurred while reflect EntityBasePath");
                    }
                })
                .collect(
                        Collectors.toMap(
                                Field::getName,
                                (Field field) -> {
                                    field.setAccessible(true);
                                    try {
//                                        if (!(object instanceof EntityPathBase)) {
                                        return (Expression<?>) field.get(this.path);
                                    } catch (IllegalAccessException e) {
                                        throw new IllegalArgumentException("error occurred while reflect EntityBasePath");
                                    }
                                }
                        )
                );

        return this.findList(predicate, projectionType, bindings, joinHelper, sort);
    }

    @Override
    public <P> List<P> findList(Predicate predicate, Class<P> projectionType, Map<String, Expression<?>> bindings, KcJoinHelper joinHelper, QSort sort) {
        Assert.notNull(projectionType, "projection type must not be null");
        Assert.notEmpty(bindings, "bindings must not be empty");

        QBean<P> expression = Projections.bean(projectionType, bindings);
        JPQLQuery<P> query = this.createQuery(predicate).select(expression);

        if (joinHelper != null) {
            query = joinHelper.join(query);
        }

        if (sort != null) {
            query = this.sort(bindings, sort, query);
        }

        return query.fetch();
    }

    //////////////////////////////////////////////////////////////// private method area

    private JPQLQuery<?> createQuery(Predicate... predicate) {
        AbstractJPAQuery<?, ?> query = this.querydsl.createQuery(this.path);
        if (predicate != null) {
            query = query.where(predicate);
        }

        return query;
    }

    private <Q> JPQLQuery<Q> sort (@Nullable Map<String, Expression<?>> bindings, QSort sort, JPQLQuery<Q> query) {
        Assert.notNull(sort, "sort must not be null");
        Assert.notNull(query, "query must not be null");

        if (bindings == null) {
            return this.querydsl.applySorting(sort, query);
        }

        if (sort.isUnsorted()) {
            return query;
        }

        List<OrderSpecifier<?>> orderSpecifierList = sort.getOrderSpecifiers();

        return query.orderBy(orderSpecifierList.toArray(new OrderSpecifier[0]));
    }
}
