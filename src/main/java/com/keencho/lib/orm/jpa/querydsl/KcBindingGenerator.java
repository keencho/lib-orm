package com.keencho.lib.orm.jpa.querydsl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KcBindingGenerator {

    boolean except() default false;

    String bindName() default "";
}
