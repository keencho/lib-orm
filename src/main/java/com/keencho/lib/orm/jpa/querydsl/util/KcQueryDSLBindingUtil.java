package com.keencho.lib.orm.jpa.querydsl.util;

import com.keencho.test.model.QRider;
import com.keencho.test.vo.QRiderVO;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.EntityPathBase;
import org.springframework.lang.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KcQueryDSLBindingUtil {

    /**
     * This method maps only keys with the same field name of the data object and the entity.
     */
    public static <E extends EntityPathBase<?>,  P extends ConstructorExpression<?>> P buildConstructor(E entityClass, Class<P> projectionClass) {

        Constructor<P>[] constructors = (Constructor<P>[]) projectionClass.getConstructors();

        for (Constructor<P> constructor : constructors) {
            Parameter[] parameters = constructor.getParameters();

            List<Path<?>> entityFieldList = new ArrayList<>();
            for (Parameter parameter : parameters) {
                for (Field field : entityClass.getClass().getDeclaredFields()) {
                    if (field.getName().equals(parameter.getName())) {
                        try {
                            entityFieldList.add((Path<?>) field.get(entityClass));
                        } catch (IllegalAccessException e) {
                            return null;
                        }
                    }
                }
            }

            if (parameters.length == entityFieldList.size()) {
                try {
                    return constructor.newInstance(entityFieldList.toArray());
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    return null;
                }
            }
        }

        return null;
    }

    public static <E extends EntityPathBase<?>, P extends ConstructorExpression<?>> void putBindings(HashMap<String, Expression<?>> bindings, String key, E entityClass, Class<P> projectionClass) {
        putBindings(bindings, key, entityClass, projectionClass, null);
    }

    /**
     * build querydsl constructor and put into binding
     * ignore put if querydsl constructor instance is null
     */
    public static <E extends EntityPathBase<?>, P extends ConstructorExpression<?>> void putBindings(HashMap<String, Expression<?>> bindings, String key, E entityClass, Class<P> projectionClass, @Nullable Runnable functionOnFailure) {
        P projection = buildConstructor(entityClass, projectionClass);

        if (projection == null) {
            if (functionOnFailure != null) {
                functionOnFailure.run();
            }
            return;
        }

        bindings.put(key, projection);
    }
}
