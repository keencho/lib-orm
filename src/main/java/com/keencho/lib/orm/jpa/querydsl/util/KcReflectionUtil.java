package com.keencho.lib.orm.jpa.querydsl.util;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.EntityPathBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.LinkedList;

public class KcReflectionUtil {

    /**
     * This method maps only keys with the same field name of the data object and the entity.
     */
    public static <E extends EntityPathBase<?>, P extends ConstructorExpression<?>> P bindQueryDSLObject(Class<E> entityClass, Class<P> projectionClass) throws InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor<P>[] constructors = (Constructor<P>[]) projectionClass.getConstructors();

        for (Constructor<P> constructor : constructors) {
            Parameter[] parameters = constructor.getParameters();

            int countMatch = 0;
            for (Parameter parameter : parameters) {
                for (Field field : entityClass.getDeclaredFields()) {
                    if (field.getName().equals(parameter.getName())) {
                        countMatch ++;
                    }
                }
            }

            if (parameters.length == countMatch) {
                return constructor.newInstance((Object) parameters);
            }
        }

        return null;
    }

}
