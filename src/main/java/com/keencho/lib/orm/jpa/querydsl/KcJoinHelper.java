package com.keencho.lib.orm.jpa.querydsl;

import com.querydsl.jpa.JPQLQuery;

public interface KcJoinHelper {
    <T>JPQLQuery<T> join(JPQLQuery<T> query);
}
