package com.baba.babaisyou.view;

import com.baba.babaisyou.presenter.Main;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class MainView extends Application {

    public static int width;
    public static int height;
    public static Scene scene;
    private static StackPane root;

    public static void show(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        root = new StackPane();
        Button resume = new Button("Reprendre");
        Button play = new Button("Commencer");
        Button builder = new Button("Constructeur de niveau");
        Button levelsBtn = new Button("Niveaux");
        Button quitBtn = new Button("Quitter");
        Label title = new Label("Baba is you");
        title.getStyleClass().add("title");

        File currentLevel = new File("src/main/resources/com/baba/babaisyou/levels/currentLevel.txt");

        VBox vb;
        if (currentLevel.exists()) {
            vb = new VBox(title, resume, play, levelsBtn, builder, quitBtn);
        } else {
            vb = new VBox(title, play, levelsBtn, builder, quitBtn);
        }

        vb.setAlignment(Pos.CENTER);

        root.getChildren().add(vb);

        //Fenêtre deux fois moins grande que l'écran
        width = (int) Screen.getPrimary().getBounds().getWidth()/2;
        height = (int) Screen.getPrimary().getBounds().getHeight()/2;

        scene = new Scene(root, MainView.width, MainView.height);
        scene.getStylesheets().add((new File("src/menu.css")).toURI().toString());

        primaryStage.setScene(scene);

        resume.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LevelView.show(primaryStage, "currentLevel");
            }
        });
        play.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LevelView.show(primaryStage, "level1");
            }
        });
        builder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LevelBuilderView.show(primaryStage);
            }
        });
        levelsBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { Selection.show(primaryStage); }
        });
        quitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.close();
            }
        });

        primaryStage.setTitle("BabaIsYou");

        primaryStage.getIcons().add(new Image("file:src/main/resources/com/baba/babaisyou/views/Baba.png"));

        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);

        primaryStage.show();
    }

    public static void show() {

        scene.setRoot(root);
        scene.getStylesheets().add((new File("src/menu.css")).toURI().toString());
    }

    public static Scene getScene() {
        return scene;
    }
}
