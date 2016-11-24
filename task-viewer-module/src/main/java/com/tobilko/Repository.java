package com.tobilko;

import java.util.Iterator;

/**
 *
 * Created by Andrew Tobilko on 11/24/2016.
 *
 */
public interface Repository<T extends Viewable> {

    void save(T... elements);
    void remove(T... element);

    T find(Long id);

}
