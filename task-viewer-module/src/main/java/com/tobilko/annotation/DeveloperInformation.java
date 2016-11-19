package com.tobilko.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * Created by Andrew Tobilko on 11/16/2016.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DeveloperInformation {

    String name();
    int age();
    String position();

}
