package az.ingress.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {
    String key();

    long waitTime() default 10;

    long leaseTime() default 5;

    TimeUnit timeUnit() default TimeUnit.SECONDS;
}