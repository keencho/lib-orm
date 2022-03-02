package com.keencho.lib.orm.jpa.querydsl;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.QSort;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.support.PageableExecutionUtils;
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
    public E findOne(Predicate predicate) {
        return this.createQuery(predicate).select(this.path).fetchOne();
    }

    @Override
    public <P> P selectOne(Predicate predicate, Class<P> projectionType, Map<String, Expression<?>> bindings, KcJoinHelper joinHelper) {
        Assert.notNull(projectionType, "projection type must not be null");
        Assert.notEmpty(bindings, "bindings must not be empty");

        QBean<P> expression = Projections.bean(projectionType, bindings);
        JPQLQuery<P> query = this.createQuery(predicate).select(expression);

        if (joinHelper != null) {
            query = joinHelper.join(query);
        }

        return query.fetchOne();
    }

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
    public <P> List<P> selectList(Predicate predicate, Class<P> projectionType, Map<String, Expression<?>> bindings, KcJoinHelper joinHelper, QSort sort) {
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

    @Override
    public Page<E> findPage(Predicate predicate, Pageable pageable) {
        Assert.notNull(pageable, "pageable must not be null!");

        JPQLQuery<?> countQuery = this.createQuery(predicate);
        JPQLQuery<E> query = countQuery.select(this.path);

        query = this.querydsl.applyPagination(pageable, query);

        return PageableExecutionUtils.getPage(query.fetch(), pageable, countQuery::fetchCount);
    }

    @Override
    public <P> Page<P> selectPage(Predicate predicate, Class<P> projectionType, Map<String, Expression<?>> bindings, KcJoinHelper joinHelper, Pageable pageable) {
        Assert.notNull(projectionType, "projection type must not be null!");
        Assert.notEmpty(bindings, "bindings must not be empty!");
        Assert.notNull(pageable, "pageable must not be null!");

        QBean<P> expression = Projections.bean(projectionType, bindings);

        JPQLQuery<?> countQuery = this.createQuery(predicate);
        JPQLQuery<P> query = countQuery.select(expression);

        if (pageable.isPaged()) {
            query.offset(pageable.getOffset());
            query.limit(pageable.getPageSize());
        }

        query = this.sort(bindings, pageable.getSort(), query);

        return PageableExecutionUtils.getPage(query.fetch(), pageable, countQuery::fetchCount);
    }

    //////////////////////////////////////////////////////////////// private method area

    private JPQLQuery<?> createQuery(Predicate... predicate) {
        AbstractJPAQuery<?, ?> query = this.querydsl.createQuery(this.path);
        if (predicate != null) {
            query = query.where(predicate);
        }

        return query;
    }

    private <Q, S extends Sort> JPQLQuery<Q> sort (@Nullable Map<String, Expression<?>> bindings, S sort, JPQLQuery<Q> query) {
        Assert.notNull(sort, "sort must not be null");
        Assert.notNull(query, "query must not be null");

        if (bindings == null) {
            return this.querydsl.applySorting(sort, query);
        }

        if (sort.isUnsorted()) {
            return query;
        }

        var iterator = sort.iterator();

        while(iterator.hasNext()) {
            query = this.querydsl.applySorting(sort, query);
        }

        return query;
    }
}
