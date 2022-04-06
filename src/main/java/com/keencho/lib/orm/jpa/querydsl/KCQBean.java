package com.keencho.lib.orm.jpa.querydsl;

import com.google.common.reflect.TypeToken;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.QBean;

import java.util.HashMap;

public class KCQBean<T> extends QBean<T> {
    public KCQBean(T data) {
        super((Class<T>) new TypeToken<T>(data.getClass()) {}.getType(), new HashMap<String, Expression<?>>());
    }
}
