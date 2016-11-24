package com.tobilko;

import com.tobilko.event.MyEventTypeProvider;
import com.tobilko.exception.FilterParameterNotSpecified;
import javafx.application.Application;
import javafx.stage.Stage;

public class EntryPoint extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        new ProjectController().configure(stage);
        stage.addEventHandler(MyEventTypeProvider.getEventType(),  e -> {
            System.out.println("CLIENT: I got the message! Generating an exception...");
            throw new FilterParameterNotSpecified("Not all filter parameters are specified!");
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
