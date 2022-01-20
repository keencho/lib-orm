package com.keencho.lib.orm.jpa.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QSort;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

public interface KcSearchQuery<E> {

    <P> List<P> findListProjections(@Nullable Predicate predicate, Class<P> projectionType, Map<String, Expression<?>> binding);

    <P> List<P> findListProjections(@Nullable Predicate predicate, Class<P> projectionType, Map<String, Expression<?>> binding, KcJoinHelper joinHelper);

    <P> List<P> findListProjections(@Nullable Predicate predicate, Class<P> projectionType, Map<String, Expression<?>> binding, QSort sort);

    <P> List<P> findListProjections(@Nullable Predicate predicate, Class<P> projectionType, Map<String, Expression<?>> binding, @Nullable KcJoinHelper joinHelper, @Nullable QSort sort);

}
