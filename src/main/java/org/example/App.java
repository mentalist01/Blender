package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Blender Java");

        // Создание главного контейнера
        BorderPane root = new BorderPane();

        // Здесь добавьте код для работы с интерфейсом и 3D-моделями

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}