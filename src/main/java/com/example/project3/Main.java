package com.example.project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("RU Bank - Transaction Manager");
        stage.setScene(scene);
        stage.show();

        MenuBar menuBar = new MenuBar();
        Pane pane = new Pane();
        pane.getChildren().add(menuBar);
    }

    public static void main(String[] args) {
        launch();
    }
}