package com.tobilko;

import javafx.application.Application;
import javafx.stage.Stage;

public class EntryPoint extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        new TaskViewProvider().configure(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
