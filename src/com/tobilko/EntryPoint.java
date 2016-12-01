package com.tobilko;

import com.tobilko.controller.ProjectController;
import com.tobilko.event.MyEventTypeProvider;
import javafx.application.Application;
import javafx.stage.Stage;

public class EntryPoint extends Application {

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        new ProjectController().configure(stage);
        stage.addEventHandler(MyEventTypeProvider.getEventType(), e -> {
            System.out.println("CLIENT: I got the message!");
        });
        stage.show();
    }

}
