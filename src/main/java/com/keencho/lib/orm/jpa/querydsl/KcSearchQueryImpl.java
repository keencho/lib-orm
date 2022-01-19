package com.keencho.lib.orm.jpa.querydsl;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.EntityPathResolver;
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

    private AbstractJPAQuery<?, ?> doCreateQuery(@Nullable com.querydsl.core.types.Predicate... predicate) {
        AbstractJPAQuery<?, ?> query = this.querydsl.createQuery(this.path);
        if (predicate != null) {
            query = query.where(predicate);
        }

        return query;
    }

    private JPQLQuery<?> createQuery(Predicate... predicate) {
        return this.doCreateQuery(predicate);
    }

    @Override
    public <P> List<P> selectList(Predicate predicate, Class<P> projectionType, Map<String, Expression<?>> binding, Sort sort) {
        Assert.notNull(projectionType, "projection type must not be null");
        Assert.notEmpty(binding, "bindings must not be empty");

        QBean<P> expression = Projections.bean(projectionType, binding);
        JPQLQuery<P> query = this.createQuery(predicate).select(expression);

        return query.fetch();
    }
}
