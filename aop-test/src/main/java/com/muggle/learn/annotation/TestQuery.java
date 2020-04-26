package com.muggle.learn.annotation;


import org.springframework.stereotype.Repository;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repository
public @interface TestQuery {

    String processor();
}
