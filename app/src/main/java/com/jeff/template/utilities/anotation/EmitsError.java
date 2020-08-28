package com.jeff.template.utilities.anotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used for RxJava streams to denote that the {@link Throwable} emitted by the stream
 * will be one of the given values as class.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(value = ElementType.METHOD)
public @interface EmitsError {
    Class[] value();
}