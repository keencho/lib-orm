package com.keencho.lib.orm.jpa.querydsl.util;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.EntityPathBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class KcReflectionUtil {

    /**
     * This method maps only keys with the same field name of the data object and the entity.
     */
    public static <E extends EntityPathBase<?>,  P extends ConstructorExpression<?>> P bindQueryDSLObject(E entityClass, Class<P> projectionClass) throws InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor<P>[] constructors = (Constructor<P>[]) projectionClass.getConstructors();

        for (Constructor<P> constructor : constructors) {
            Parameter[] parameters = constructor.getParameters();

            List<Path<?>> entityFieldList = new ArrayList<>();
            for (Parameter parameter : parameters) {
                for (Field field : entityClass.getClass().getDeclaredFields()) {
                    if (field.getName().equals(parameter.getName())) {
                        field.setAccessible(true);
                        entityFieldList.add((Path<?>) field.get(entityClass));
                    }
                }
            }

            if (parameters.length == entityFieldList.size()) {
                return constructor.newInstance(entityFieldList.toArray());
            }
        }

        return null;
    }

}
