package com.keencho.lib.orm.jpa.querydsl;

import com.querydsl.jpa.JPQLQuery;

public interface KcQueryHandler {
    <T> JPQLQuery<T> apply(JPQLQuery<T> query);
}
