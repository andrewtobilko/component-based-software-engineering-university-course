package com.tobilko;

import com.tobilko.entity.Project;
import com.tobilko.entity.Task;
import com.tobilko.entity.TaskType;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.hibernate.query.NativeQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Arrays.stream;

/**
 *
 * Created by Andrew Tobilko on 11/24/2016.
 *
 */
public class ProjectNativeRepository implements Repository<Project> {

    private static final String SELECT_ALL_PROJECTS = "SELECT * FROM public.project;";
    private static final String SELECT_PROJECT_BY_ID = "SELECT * FROM public.project WHERE id = ?;";
    private static final String DELETE_PROJECT_BY_ID = "DELETE FROM public.project WHERE id = ?";
    private static final String INSERT_PROJECT = "INSERT INTO public.project VALUES() ";

    private Session session;

    public ProjectNativeRepository(Session session) {
        this.session = session;
        fillInitialData();
    }

    @Override
    public void save(Project... projects) {
        session.doWork(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_PROJECT);
            ResultSet set = statement.executeQuery();
        });g
/*        Arrays.stream(projects).forEach(project -> {
            NativeQuery query = session.createNativeQuery("INSERT INTO project (id, title) VALUES(?, ?)");
            query.setParameter(2, project.getTitle());
            List result = query.getResultList();
            System.out.println(result);
        });*/
        perform(session::save, projects);
    }

    @Override
    public void remove(Project... projects) {
        Arrays.stream(projects).forEach(project -> {
            NativeQuery query = session.createNativeQuery(DELETE_PROJECT_BY_ID).setParameter(1, project.getId());
            List list = query.getResultList();
            System.out.println(list);
        });
    }

    @Override
    public Project find(Long id) {
        NativeQuery<Project> query = session.createNativeQuery(SELECT_PROJECT_BY_ID, Project.class);
        query.setParameter(1, id);

        return query.getSingleResult();
    }

    @Override
    public List<Project> findAll() {
        return session
                .createNativeQuery(SELECT_ALL_PROJECTS, Project.class)
                .getResultList();
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