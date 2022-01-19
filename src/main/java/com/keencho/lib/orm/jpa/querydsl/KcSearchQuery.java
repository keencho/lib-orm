package com.keencho.lib.orm.jpa.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

public interface KcSearchQuery<E> {

    <P> List<P> selectList(@Nullable Predicate predicate, Class<P> projectionType, Map<String, Expression<?>> binding, @Nullable Sort sort);

}
