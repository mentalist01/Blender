package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.model.Model;
import org.example.objreader.ObjReader;
import org.example.objwriter.ObjWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Blender Java");

        // Создание главного контейнера
        BorderPane root = new BorderPane();

        // Здесь добавьте код для работы с интерфейсом и 3D-моделями

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);


//      Получение из файла и запись модели в файл
        String fileContent = Files.readString(Path.of("3DModels/Faceform/WrapUpperTeeth.obj"));
        Model model = ObjReader.read(fileContent);
        String file = "New file.obj";
        ObjWriter.writeModelToObjFile(file, model);

        primaryStage.show();
    }
}