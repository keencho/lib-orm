package com.keencho.lib.orm.jpa.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QSort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

public interface KcSearchQuery<E> {

    E findOne(@Nullable Predicate predicate);

    <P> P selectOne(@Nullable Predicate predicate, @NonNull Class<P> projectionType, @NonNull Map<String, Expression<?>> bindings, @Nullable KcJoinHelper joinHelper);

    List<E> findList(@Nullable Predicate predicate, @Nullable KcJoinHelper joinHelper, @Nullable QSort sort);

    <P> List<P> select(@Nullable Predicate predicate, @NonNull Class<P> projectionType, @NonNull Map<String, Expression<?>> bindings, @Nullable KcJoinHelper joinHelper, @Nullable QSort sort);

    Page<E> findPage(@Nullable Predicate predicate, Pageable pageable);

    <P> Page<P> selectPage(@Nullable Predicate predicate, @NonNull Class<P> projectionType, @NonNull Map<String, Expression<?>> bindings, @Nullable KcJoinHelper joinHelper, Pageable pageable);

}
