package com.tobilko.entity;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * Created by Andrew Tobilko on 11/16/2016.
 *
 */
public class Filter<T> {

    public List<T> filter(List<T> list, Predicate<T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

}