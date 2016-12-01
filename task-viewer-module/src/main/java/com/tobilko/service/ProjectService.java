package com.tobilko.service;

import com.tobilko.repository.Repository;
import com.tobilko.entity.Project;
import com.tobilko.entity.Task;
import com.tobilko.entity.TaskType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Stream.of;
import static javafx.collections.FXCollections.observableArrayList;

@Component
public class ProjectService {

    private Repository<Project> repository;

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

    @Autowired
    @Qualifier("nativeRepository")
    public void setRepository(Repository<Project> repository) {
        this.repository = repository;
    }

}
