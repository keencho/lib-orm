package com.keencho.lib.orm.jpa.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.QBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class KCProjections {
    public static <T> QBean<T> bean(T data) {
//        Class<T> classType = (Class<T>) new TypeToken<T>(data.getClass()) {}.getType();
        var bindings = new HashMap<String, Expression<?>>();

        List<Field> fieldList = new ArrayList<>(Arrays.asList(data.getClass().getDeclaredFields()));
        fieldList.stream()
                //
                .filter(field -> field.getType().isAssignableFrom(Expression.class))
                .forEach(field -> {
                    System.out.println(field);
                });


//        return Projections.bean(classType, bindings);
        return null;
    }
}
