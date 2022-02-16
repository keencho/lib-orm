package com.keencho.lib.orm.jpa.querydsl.util;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.EntityPathBase;
import org.springframework.lang.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class KcBindingUtil {
    /**
     * Match projection and entity
     * match if name of field is same.
     * ignore if field is entity (not support for entity association)
     * ignore IllegalAccessException
     */
    public static HashMap<String, Expression<?>> buildBindingsViaReflection(EntityPath<?> entityPath, Class<?> projectionClass) {
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
