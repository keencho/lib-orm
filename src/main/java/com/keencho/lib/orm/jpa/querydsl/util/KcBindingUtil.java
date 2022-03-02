package com.keencho.lib.orm.jpa.querydsl.util;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.EntityPathBase;

import java.util.Arrays;
import java.util.HashMap;

public class KcBindingUtil {
    /**
     * match entity field and value object field via reflection
     * If the field is an entity object, ignore it.
     */
    public static HashMap<String, Expression<?>> buildBindings(EntityPath<?> entityPath, Class<?> projectionClass) {
        HashMap<String, Expression<?>> bindings = new HashMap<>();

        var entityFieldList = Arrays.asList(entityPath.getClass().getDeclaredFields());
        var projectionFieldList = Arrays.asList(projectionClass.getDeclaredFields());

        projectionFieldList.forEach(pf -> {
            entityFieldList.stream()
                    .filter(ef -> pf.getName().equals(ef.getName()))
                    .forEach(ef -> {
                        try {
                            ef.setAccessible(true);
                            Expression<?> exp = (Expression<?>) ef.get(entityPath);

                            if (!(exp instanceof EntityPathBase)) {
                                bindings.put(ef.getName(), exp);
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
        });

        return bindings;
    }
}
