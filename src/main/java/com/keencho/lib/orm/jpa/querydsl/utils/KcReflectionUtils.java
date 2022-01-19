package com.keencho.lib.orm.jpa.querydsl.utils;

import org.modelmapper.internal.util.Maps;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

public class KcReflectionUtils {
    public KcReflectionUtils() {
    }

    public static Field getField(Class<?> clazz, String fieldName, boolean superClass) throws NoSuchFieldException {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return field;
        } catch (NoSuchFieldException var5) {
            if (superClass && clazz.getSuperclass() != null) {
                return getField(clazz.getSuperclass(), fieldName, superClass);
            } else {
                throw var5;
            }
        } catch (SecurityException var6) {
            throw var6;
        }
    }

    public static Object getFieldValue(Object obj, String fieldName, boolean superClass) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field f = getField(obj.getClass(), fieldName, superClass);
        boolean accessible = f.canAccess(obj);
        if (!accessible) {
            f.setAccessible(true);
        }

        Object value = f.get(obj);
        if (!accessible) {
            f.setAccessible(false);
        }

        return value;
    }

    public static Object getFieldValue(Class<?> clazz, Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field f = clazz.getDeclaredField(fieldName);
        boolean accessible = f.canAccess(obj);
        if (!accessible) {
            f.setAccessible(true);
        }

        Object value = f.get(obj);
        if (!accessible) {
            f.setAccessible(false);
        }

        return value;
    }

//    public static Field[] getFieldsToArray(Class<?> clazz) {
//        Field[] fields = clazz.getDeclaredFields();
//        if (clazz.getSuperclass() != null) {
//            Field[] superFields = getFieldsToArray(clazz.getSuperclass());
//            return (Field[])NpArrayUtils.concat(superFields, fields);
//        } else {
//            return fields;
//        }
//    }
//
//    public static Map<String, Field> getFieldsToMap(Class<?> clazz) {
//        Field[] fields = getFieldsToArray(clazz);
//        if (fields != null && fields.length >= 1) {
//            Map<String, Field> fieldMap = Maps.newHashMap();
//            Field[] var6 = fields;
//            int var5 = fields.length;
//
//            for(int var4 = 0; var4 < var5; ++var4) {
//                Field field = var6[var4];
//                fieldMap.put(field.getName(), field);
//            }
//
//            return fieldMap;
//        } else {
//            return Collections.emptyMap();
//        }
//    }
//
//    public static Method getMethod(Class<?> clazz, boolean superClass, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
//        try {
//            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
//            return method;
//        } catch (NoSuchMethodException var5) {
//            if (superClass && clazz.getSuperclass() != null) {
//                return getMethod(clazz.getSuperclass(), superClass, methodName, parameterTypes);
//            } else {
//                throw var5;
//            }
//        } catch (SecurityException var6) {
//            throw var6;
//        }
//    }
//
//    public static Object invokeMethod(Method method, Object obj, Object... params) throws IllegalAccessException, InvocationTargetException {
//        NpAssert.notNull(method, "method");
//        NpAssert.notNull(obj, "obj");
//        boolean accessible = method.canAccess(obj);
//
//        Object var6;
//        try {
//            method.setAccessible(true);
//            var6 = method.invoke(obj, params);
//        } catch (IllegalAccessException var11) {
//            throw var11;
//        } catch (IllegalArgumentException var12) {
//            throw var12;
//        } catch (InvocationTargetException var13) {
//            throw var13;
//        } finally {
//            if (method != null) {
//                method.setAccessible(accessible);
//            }
//
//        }
//
//        return var6;
//    }
}
