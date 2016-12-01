package com.tobilko.controller;

import com.tobilko.annotation.DeveloperInformation;
import com.tobilko.entity.Project;
import com.tobilko.entity.Task;
import com.tobilko.entity.TaskType;
import com.tobilko.event.MyEvent;
import com.tobilko.event.MyEventTypeProvider;
import com.tobilko.exception.FilterParameterNotSpecified;
import com.tobilko.service.ProcessingService;
import com.tobilko.service.ProjectService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.stream.Collectors.toList;
import static javafx.collections.FXCollections.observableArrayList;

/**
 *
 * Created by Andrew Tobilko on 11/16/2016.
 *
 */
@DeveloperInformation(name = "Andrew Tobilko", age = 20, position = "Java developer")
public class ProjectController implements Initializable {

    private static final ApplicationContext CONTEXT = new AnnotationConfigApplicationContext("com.tobilko");

    private Stage mainStage;

    private @FXML Button filterButton;
    private @FXML Button sortButton;
    private @FXML ComboBox<String> typeComboBox;
    private @FXML ComboBox<String> projectComboBox;
    private @FXML TableView<Task> table;
    private @FXML Label label;
    private @FXML ComboBox<String> languageComboBox;

    private List<Project> projects = new ArrayList<>();

    private ProjectService service = new ProjectService();
    private ProcessingService processingService = new ProcessingService();

    public void configure(Stage stage) {
        try {
            mainStage = stage;
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("task-viewer.fxml"), ResourceBundle.getBundle("com.tobilko.controller.locales.locale", Locale.ENGLISH)), 500, 350));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void configureFilterProcessing() {
        filterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (projectComboBox.getSelectionModel().isEmpty() || typeComboBox.getSelectionModel().isEmpty()) {
                    System.out.println("COMPONENT: I'm going to fire the event!");
                    filterButton.fireEvent(new MyEvent(MyEventTypeProvider.getEventType()));
                    throw new FilterParameterNotSpecified("Not all filter parameters are specified!");
                }

                List<Project> projects = processingService.getProjectProcessor().filter(ProjectController.this.projects, p -> p.getTitle().equals(projectComboBox.getSelectionModel().getSelectedItem()));
                List<Task> tasks = processingService.getTaskProcessor().filter(projects.get(0).getTasks(), t -> t.getType() == TaskType.getByTitle(typeComboBox.getSelectionModel().getSelectedItem()));

                table.setItems(observableArrayList(tasks));

            }
        });
    }
    private void configureSortProcessing() {
        sortButton.setOnAction(e -> {
            List<Task> allTasks = projects.stream().flatMap(p -> p.getTasks().stream()).collect(toList());


            processingService.getTaskProcessor().sort(allTasks, (t1, t2) -> t1.getTitle().compareTo(t2.getTitle()));
            table.setItems(observableArrayList(allTasks));
        });
    }
    private void configureLanguageSwitching() {
        languageComboBox.valueProperty().addListener((value, previous, current) -> {
            changeLanguage(current.equals("EN") ? Locale.ENGLISH : new Locale(current.toLowerCase(), current));
        });
    }

    private void changeLanguage(Locale locale) {
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("com.tobilko.controller.locales.locale", locale));

        try {
            Pane pane = (BorderPane) loader.load(this.getClass().getResource("task-viewer.fxml").openStream());
            StackPane content = (StackPane) ((VBox) mainStage.getScene().getRoot()).getChildren().get(1);
            content.getChildren().clear();
            content.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureFilterProcessing();
        configureSortProcessing();
        configureLanguageSwitching();
        //fillComponents();
        displayMetaInformation();
    }

    private void fillComponents() {
        service.fillProjects(projects);
        service.fillTypeComboBox(typeComboBox);
        service.fillProjectComboBox(projectComboBox, projects);
        service.fillTable(table, projects);
    }
    private void displayMetaInformation() {
        Class<ProjectController> clazz = ProjectController.class;
        if (clazz.isAnnotationPresent(DeveloperInformation.class)) {
            DeveloperInformation a = clazz.getAnnotation(DeveloperInformation.class);
            label.setText(a.name() + ", " + a.age() + ", " + a.position());
        } else {
            throw new RuntimeException("The 'DeveloperInformation' annotation hasn't been found!");
        }
    }

}