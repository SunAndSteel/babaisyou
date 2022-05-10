package com.baba.babaisyou.view;

import com.baba.babaisyou.model.GameObject;
import com.baba.babaisyou.model.Level;
import com.baba.babaisyou.model.enums.Material;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LevelBuilder {
    private static boolean editing;

    public static void start(Stage primaryStage) {
        LevelBuilderView.show(primaryStage);
    }

    /**
     * @return La liste des materiaux
     */
    public static ArrayList<String> getMaterials() {
        Material[] materials = Material.values();

        ArrayList<String> materialsNames = new ArrayList<>();

        for(Material mat : materials) {
            if (mat.name().equals("Cursor") || mat.name().equals("Floor")) {}
            else {
                materialsNames.add(mat.name());
            }

        }
        return materialsNames;
    }

    public static ArrayList<String> getLevels() {
        ArrayList<String> levelsNames = new ArrayList<>();
        File f = new File("C:\\Users\\Florent\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\levels");

        for (File file : f.listFiles()) {
            String filteredName = file.getName().substring(0, file.getName().length() - 4);
            levelsNames.add(filteredName);
        }

        return levelsNames;
    }

    public static void EditButtonAction(ListView levels, Button editBtn) {
        editing = !editing;
        if(editing) {
            levels.setDisable(true);
            editBtn.setText("Sauvegarder");
        }
        if(!editing) {
            levels.setDisable(false);
            editBtn.setText("Editer le niveau");
        }
    }

    public static void PlaceObjects(Level level, String SelectedMat, Point p) {
        if(editing && level.getObjects(p).size() == 2) {
            level.addObject(new GameObject(SelectedMat,p.x, p.y), p);
        }
    }

    public static void removeObject(Level level, Point p) {
        if(editing) {
            level.removeObject(p);
        }
    }

    public static void NewLevelButtonAction(ObservableList<String> updatedList) {
        VBox nlPane = new VBox();
        TextField nlName = new TextField("Nom");
        TextField nlY = new TextField("Y");
        TextField nlX = new TextField("X");
        Button nlBtn = new Button("Submit");

        nlPane.getChildren().setAll(nlName, nlY, nlX, nlBtn);
        Stage stage = new Stage();
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(nlPane));
        stage.show();

        nlBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                int x = Integer.parseInt(nlX.getText());
                int y = Integer.parseInt(nlY.getText());
                String name = nlName.getText();

                File newLevel = new File("C:\\Users\\Florent\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\levels\\" + name + ".txt");
                BufferedWriter nl = new BufferedWriter(new FileWriter(newLevel));
                nl.write(x + " " + y);
                nl.close();
                newLevel.createNewFile();
                updatedList.add(name);
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
