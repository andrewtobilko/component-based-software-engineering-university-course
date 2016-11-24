package com.tobilko;

import com.tobilko.entity.Project;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;
import java.util.function.Consumer;

import static java.util.Arrays.*;

/**
 *
 * Created by Andrew Tobilko on 11/24/2016.
 *
 */
public class ProjectRepository implements Repository<Project> {

    private Session session;

    public ProjectRepository(Session session) {
        this.session = session;
    }

    @Override
    public void save(Project... projects) {
        perform(session::save, projects);
    }

    @Override
    public void remove(Project... projects) {
        perform(session::remove, projects);
    }

    @Override
    public Project find(Long id) {
        return session.get(Project.class, id);
    }

    private void perform(Consumer<Project> consumer, Project... projects) {
        Transaction transaction = session.beginTransaction();
        stream(projects).forEach(consumer);
        transaction.commit();
    }

}