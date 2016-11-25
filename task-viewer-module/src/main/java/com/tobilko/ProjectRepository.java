package com.tobilko;

import com.tobilko.entity.Project;
import com.tobilko.entity.Task;
import com.tobilko.entity.TaskType;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

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
        fillInitialData();
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

    @Override
    public List<Project> findAll() {
        return session.createCriteria(Project.class).list();
    }


    private void perform(Consumer<Project> consumer, Project... projects) {
        Transaction transaction = session.beginTransaction();
        stream(projects).forEach(consumer);
        transaction.commit();
    }

    private void fillInitialData() {
        Transaction transaction = session.beginTransaction();

        Task t1 = new Task("title task 3.1", "description task 3.1", TaskType.BUG);
        Task t2 = new Task("title task 3.1", "description task 3.1", TaskType.FEATURE);
        Task t3 = new Task("title task 3.1", "description task 3.1", TaskType.BUG);

        Task t5 = new Task("title task 1.1", "description task 1.2", TaskType.IMPROVEMENT);
        Task t6 = new Task("title task 1.2", "description task 1.2", TaskType.FEATURE);
        Task t7 = new Task("title task 1.3", "description task 1.3", TaskType.BUG);

        Task t8 = new Task("title task 2.1", "description task 2.1", TaskType.FEATURE);
        Task t9 = new Task("title task 2.2", "description task 2.2", TaskType.BUG);
        Task t10 = new Task("title task 2.3", "description task 2.3", TaskType.IMPROVEMENT);

        session.save(t1);
        session.save(t2);
        session.save(t3);
        session.save(t5);
        session.save(t6);
        session.save(t7);
        session.save(t8);
        session.save(t9);
        session.save(t10);

        session.save(new Project("Project 3", Arrays.asList(t1, t2, t3)));
        session.save(new Project("Project 1", Arrays.asList(t5, t6, t7)));
        session.save(new Project("Project 2", Arrays.asList(t8, t9, t10)));
        transaction.commit();
    }

}