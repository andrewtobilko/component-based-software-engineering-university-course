package com.tobilko.stuff;

import com.tobilko.entity.Project;
import com.tobilko.entity.Task;
import com.tobilko.entity.TaskType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Stream.of;
import static javafx.collections.FXCollections.observableArrayList;

/**
 *
 * Created by Andrew Tobilko on 11/17/2016.
 *
 */
public class TaskViewService {

    public void fillProjects(List<Project> projects) {
        projects.add(new Project("Project 1", Arrays.asList(
                new Task("title task 1.1", "description task 1.1", TaskType.IMPROVEMENT),
                new Task("title task 1.2", "description task 1.2", TaskType.BUG),
                new Task("title task 1.3", "description task 1.3", TaskType.FEATURE)
        )));
        projects.add(new Project("Project 3", Arrays.asList(
                new Task("title task 3.1", "description task 3.1", TaskType.BUG),
                new Task("title task 3.2", "description task 3.2", TaskType.IMPROVEMENT),
                new Task("title task 3.3", "description task 3.3", TaskType.FEATURE)
        )));
        projects.add(new Project("Project 2", Arrays.asList(
                new Task("title task 2.1", "description task 2.1", TaskType.BUG),
                new Task("title task 2.2", "description task 2.2", TaskType.IMPROVEMENT),
                new Task("title task 2.3", "description task 2.3", TaskType.FEATURE)
        )));
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
