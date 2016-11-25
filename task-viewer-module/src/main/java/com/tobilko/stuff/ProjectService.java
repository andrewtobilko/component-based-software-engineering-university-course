package com.tobilko.stuff;

import com.tobilko.ProjectNativeRepository;
import com.tobilko.Repository;
import com.tobilko.entity.Project;
import com.tobilko.entity.Task;
import com.tobilko.entity.TaskType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

import static java.util.stream.Stream.of;
import static javafx.collections.FXCollections.observableArrayList;

/**
 *
 * Created by Andrew Tobilko on 11/17/2016.
 *
 */
public class ProjectService {

    private final static String CONFIGURATION_FILE = "hibernate.cfg.xml";
    private final static String DEFAULT_PACKAGE = "com.tobilko";
    private static SessionFactory factory;

    static {
        factory = new Configuration()
                .configure(CONFIGURATION_FILE)
                    .addPackage(DEFAULT_PACKAGE)
                    .addAnnotatedClass(Project.class)
                    .addAnnotatedClass(Task.class)
                .buildSessionFactory();
    }

    private Repository<Project> repository = new ProjectNativeRepository(factory.openSession());

    public void fillProjects(List<Project> projects) {
        repository.findAll().forEach(projects::add);
    }
    public void fillTypeComboBox(ComboBox<String> box) {
        box.setItems(observableArrayList(of(TaskType.values()).map(TaskType::toString).toArray(String[]::new)));
    }
    public void fillProjectComboBox(ComboBox<String> box, List<Project> projects) {
        box.setItems(observableArrayList(projects.stream().map(Project::getTitle).toArray(String[]::new)));
    }
    public void fillTable(TableView<Task> table, List<Project> projects) {
        table.setItems(observableArrayList(projects.stream().flatMap(p -> p.getTasks().stream()).toArray(Task[]::new)));
    }

}
