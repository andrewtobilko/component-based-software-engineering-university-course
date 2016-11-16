package com.tobilko;

import com.tobilko.annotation.DeveloperInformation;
import com.tobilko.entity.Project;
import com.tobilko.entity.Filter;
import com.tobilko.entity.Task;
import com.tobilko.entity.TaskType;
import com.tobilko.exception.FilterParameterNotSpecified;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.stream.Stream.*;
import static javafx.collections.FXCollections.observableArrayList;

/**
 *
 * Created by Andrew Tobilko on 11/16/2016.
 *
 */
@DeveloperInformation(name = "Andrew Tobilko", age = 20, position = "Java developer")
public class TaskViewProvider implements Initializable {

    private @FXML Button filterButton;
    private @FXML ComboBox<String> typeComboBox;
    private @FXML ComboBox<String> projectComboBox;
    private @FXML TableView<Task> table;
    private @FXML Label label;

    private Filter<Project> projectFilter = new Filter<>();
    private Filter<Task> taskFilter = new Filter<>();

    private List<Project> projects;

    public void configure(Stage stage) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("task-viewer.fxml")), 500, 500));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void configureFilterProcessing() {
        filterButton.setOnAction(e -> {
            if (projectComboBox.getSelectionModel().isEmpty() || typeComboBox.getSelectionModel().isEmpty()) {
                throw new FilterParameterNotSpecified("Not all filter parameters are specified!");
            }

            List<Project> projects = projectFilter.filter(this.projects, p -> p.getTitle().equals(projectComboBox.getSelectionModel().getSelectedItem()));
            List<Task> tasks = taskFilter.filter(projects.get(0).getTasks(), t -> t.getType() == TaskType.getByTitle(typeComboBox.getSelectionModel().getSelectedItem()));

            table.setItems(observableArrayList(tasks));

        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureFilterProcessing();
        fillDataStructures();
        fillComponents();
        displayMetaInformation();
    }
    private void fillDataStructures() {
        projects = Arrays.asList(
                new Project("Project 1", Arrays.asList(
                        new Task("title task 1.1", "description task 1.1", TaskType.IMPROVEMENT),
                        new Task("title task 1.2", "description task 1.2", TaskType.BUG),
                        new Task("title task 1.3", "description task 1.3", TaskType.FEATURE)
                )),
                new Project("Project 2", Arrays.asList(
                        new Task("title task 2.1", "description task 2.1", TaskType.BUG),
                        new Task("title task 2.2", "description task 2.2", TaskType.IMPROVEMENT),
                        new Task("title task 2.3", "description task 2.3", TaskType.FEATURE)
                )),
                new Project("Project 3", Arrays.asList(
                        new Task("title task 3.1", "description task 3.1", TaskType.BUG),
                        new Task("title task 3.2", "description task 3.2", TaskType.IMPROVEMENT),
                        new Task("title task 3.3", "description task 3.3", TaskType.FEATURE)
                ))
        );
    }
    private void fillComponents() {
        typeComboBox.setItems(observableArrayList(of(TaskType.values()).map(TaskType::toString).toArray(String[]::new)));
        projectComboBox.setItems(observableArrayList(projects.stream().map(Project::getTitle).toArray(String[]::new)));
        table.setItems(observableArrayList(projects.stream().flatMap(p -> p.getTasks().stream()).toArray(Task[]::new)));
    }

    private void displayMetaInformation() {
        Class<TaskViewProvider> clazz = TaskViewProvider.class;
        if (clazz.isAnnotationPresent(DeveloperInformation.class)) {
            DeveloperInformation a = clazz.getAnnotation(DeveloperInformation.class);
            label.setText(a.name() + ", " + a.age() + ", " + a.position());
        } else {
            throw new RuntimeException("The 'DeveloperInformation' annotation hasn't been found!");
        }
    }

}
