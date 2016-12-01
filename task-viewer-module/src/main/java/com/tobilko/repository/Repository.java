package com.tobilko.repository;

import com.tobilko.Viewable;
import com.tobilko.entity.Project;
import com.tobilko.entity.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public abstract class Repository<T extends Viewable> {

    private final static String CONFIGURATION_FILE = "hibernate.cfg.xml";
    private final static String DEFAULT_PACKAGE = "com.tobilko";
    protected Session session;
    private SessionFactory factory;

    {
        factory = new Configuration()
                .configure(CONFIGURATION_FILE)
                .addPackage(DEFAULT_PACKAGE)
                .addAnnotatedClass(Project.class)
                .addAnnotatedClass(Task.class)
                .buildSessionFactory();
    }

    public Repository() {
        session = factory.openSession();
    }

    public abstract void save(T... elements);

    public abstract void remove(T... element);

    public abstract T find(Long id);

    public abstract List<T> findAll();

}
