package com.keencho.lib.orm.jpa.querydsl;

import com.keencho.test.vo.Q;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.QSort;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

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
    public <P> List<P> findList(Predicate predicate, Class<P> projectionType, Map<String, Expression<?>> binding, KcJoinHelper joinHelper, QSort sort) {
        Assert.notNull(projectionType, "projection type must not be null");
        Assert.notEmpty(binding, "bindings must not be empty");

        QBean<P> expression = Projections.bean(projectionType, binding);
        JPQLQuery<P> query = this.createQuery(predicate).select(expression);

        if (joinHelper != null) {
            query = joinHelper.join(query);
        }

        if (sort != null) {
            query = this.sort(binding, sort, query);
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
