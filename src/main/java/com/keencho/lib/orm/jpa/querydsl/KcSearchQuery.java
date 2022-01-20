package com.keencho.lib.orm.jpa.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QSort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

public interface KcSearchQuery<E> {

    List<E> findList(@Nullable Predicate predicate, @Nullable KcJoinHelper joinHelper, @Nullable QSort sort);

    <P> List<P> findList(@Nullable Predicate predicate, @NonNull Class<P> projectionType, @NonNull Map<String, Expression<?>> binding, @Nullable KcJoinHelper joinHelper, @Nullable QSort sort);

}
