package com.tobilko.stuff;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * Created by Andrew Tobilko on 11/16/2016.
 *
 */
public class Processor<T> {

    public List<T> filter(List<T> list, Predicate<T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }
    public List<T> sort(List<T> list, Comparator<T> comparator) {
        Collections.sort(list, comparator);

        return list;
    }

}