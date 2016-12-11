package com.tobilko.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DeveloperInformation {

    String name();

    int age();

    String position();

}
