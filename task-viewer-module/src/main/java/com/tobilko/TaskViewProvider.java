package com.tobilko;

import com.tobilko.annotation.DeveloperInformation;
import com.tobilko.entity.Project;
import com.tobilko.entity.Task;
import com.tobilko.entity.TaskType;
import com.tobilko.event.MyEvent;
import com.tobilko.event.MyEventTypeProvider;
import com.tobilko.stuff.ProcessingService;
import com.tobilko.stuff.TaskViewService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static java.util.stream.Collectors.*;
import static javafx.collections.FXCollections.observableArrayList;

/**
 *
 * Created by Andrew Tobilko on 11/16/2016.
 *
 */
@DeveloperInformation(name = "Andrew Tobilko", age = 20, position = "Java developer")
public class TaskViewProvider implements Initializable {

    private @FXML Button filterButton;
    private @FXML Button sortButton;
    private @FXML ComboBox<String> typeComboBox;
    private @FXML ComboBox<String> projectComboBox;
    private @FXML TableView<Task> table;
    private @FXML Label label;

    private List<Project> projects = new ArrayList<>();

    private TaskViewService service = new TaskViewService();
    private ProcessingService processingService = new ProcessingService();

    public void configure(Stage stage) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("task-viewer.fxml")), 500, 350));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void configureFilterProcessing() {
        filterButton.setOnAction(e -> {
            if (projectComboBox.getSelectionModel().isEmpty() || typeComboBox.getSelectionModel().isEmpty()) {
                System.out.println("COMPONENT: I'm going to fire the event!");
                MyEvent event = new MyEvent(MyEventTypeProvider.getEventType());
                filterButton.fireEvent(event);
            }

            List<Project> projects = processingService.getProjectProcessor().filter(this.projects, p -> p.getTitle().equals(projectComboBox.getSelectionModel().getSelectedItem()));
            List<Task> tasks = processingService.getTaskProcessor().filter(projects.get(0).getTasks(), t -> t.getType() == TaskType.getByTitle(typeComboBox.getSelectionModel().getSelectedItem()));

            table.setItems(observableArrayList(tasks));

        });
    }
    public void configureSortProcessing() {
        sortButton.setOnAction(e -> {
            List<Task> allTasks = projects.stream().flatMap(p -> p.getTasks().stream()).collect(toList());


            processingService.getTaskProcessor().sort(allTasks, (t1, t2) -> t1.getTitle().compareTo(t2.getTitle()));
            table.setItems(observableArrayList(allTasks));
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureFilterProcessing();
        configureSortProcessing();

        fillComponents();
        displayMetaInformation();
    }
    private void fillComponents() {
        service.fillProjects(projects);
        service.fillTypeComboBox(typeComboBox);
        service.fillProjectComboBox(projectComboBox, projects);
        service.fillTable(table, projects);
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
