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
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
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
    public <P> List<P> findList(Predicate predicate, Class<P> projectionType, KcJoinHelper joinHelper, QSort sort) throws IllegalAccessException {
        KcBindingGenerator classBindGenerator = projectionType.getAnnotation(KcBindingGenerator.class);

        // KcBindGenerator annotation must be declared in 'root parent' projection type class
        if (classBindGenerator == null) {
            throw new RuntimeException("KcBindGenerator annotation must be declared in the projection type class");
        }

        Map<String, Expression<?>> bindings = new HashMap<>();

        List<Field> tempProjectionFieldList = List.of(projectionType.getDeclaredFields());

        List<Field> projectionFieldList = new ArrayList<>();
        List<Field> projectionEntityFieldList = new ArrayList<>();

        // first, filtering pure field and entity field list
        for (Field entityField : this.path.getClass().getDeclaredFields()) {
            entityField.setAccessible(true);

            Object object = entityField.get(this.path);

            if (object instanceof EntityPathBase) {
                if (this.path.getClass() == object.getClass()) {
                    for (Field field : tempProjectionFieldList) {
                        if (Modifier.isStatic(field.getModifiers())) {
                            continue;
                        }

                        KcBindingGenerator fieldBindingGenerator = field.getAnnotation(KcBindingGenerator.class);

                        // except field which has annotation and except option is true
                        if (fieldBindingGenerator != null && fieldBindingGenerator.except()) {
                            continue;
                        }

                        projectionFieldList.add(field);
                    }
                } else {
                    projectionEntityFieldList.add(entityField);
                }
            }
        }

        // second, matching key and value of root parent projection class
        for (Field projectionField : projectionFieldList) {
            Field matchField = null;

            projectionField.setAccessible(true);

            for (Field entityField : this.path.getClass().getDeclaredFields()) {

                if (projectionField.getName().equals(entityField.getName())) {
                    matchField = entityField;
                    break;
                }

            }

            if (matchField == null) {
                continue;
            }

            matchField.setAccessible(true);

            Object object = matchField.get(this.path);
            if (object instanceof EntityPathBase) {
                continue;
            }

            bindings.put(matchField.getName(), (Expression<?>) object);
        }

        // third, match Object projection
        for (Field entityField : projectionEntityFieldList) {
            System.out.println(entityField);
        }

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
