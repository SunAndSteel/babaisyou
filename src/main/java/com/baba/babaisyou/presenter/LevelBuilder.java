package com.baba.babaisyou.presenter;

import com.baba.babaisyou.model.GameObject;
import com.baba.babaisyou.model.Level;
import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.view.LevelBuilderView;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LevelBuilder {
    private static boolean editing;

    /**
     * Afficher la scène
     * @param primaryStage le Stage dans lequel on affiche la scène
     */
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

    /**
     * @return La liste des niveaux
     */
    public static ArrayList<String> getLevels() {
        ArrayList<String> levelsNames = new ArrayList<>();
        File f = new File("C:\\Users\\Florent\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\levels");

        for (File file : f.listFiles()) {
            String filteredName = file.getName().substring(0, file.getName().length() - 4);
            levelsNames.add(filteredName);
        }

        return levelsNames;
    }

    /**
     * Fonction appelée quand le bouton éditer est cliqué
     * @param levels Liste des niveaux
     * @param newLevelBtn Bouton "ajouter un niveau"
     * @param editBtn bouton "éditer un niveau"
     * @param selectedLevel niveau sélectionné
     * @param level Niveau a sauvegarder
     */
    public static void EditButtonAction(ListView<String> levels,Button newLevelBtn, Button editBtn, String selectedLevel, Level level) {
        editing = !editing;
        if(editing) {
            levels.setDisable(true);
            newLevelBtn.setDisable(true);
            editBtn.setText("Sauvegarder");
            editBtn.setOnMouseClicked((MouseEvent event) -> {
                try {
                    File savedLevel = new File("C:\\Users\\Florent\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\levels\\" + selectedLevel + ".txt");
                    BufferedWriter nl = new BufferedWriter(new FileWriter(savedLevel));

                    ArrayList<String> tmp = level.save();

                    for (String i: tmp) {
                        nl.write(i);
                    }

                    nl.close();

                    System.out.println("Saved.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        if(!editing) {
            levels.setDisable(false);
            editBtn.setText("Editer le niveau");
        }
    }

    /**
     * Fonction appelée quand l'utilisateur appuie sur entrée
     * @param level le niveau sur lequel placer l'objet
     * @param SelectedMat l'objet a placer
     * @param p la position où placer l'objet
     */
    public static void PlaceObjects(Level level, String SelectedMat, Point p) {
        if(editing && level.getObjects(p).size() == 2) {
            level.addObject(new GameObject(SelectedMat,p.x, p.y), p);
        }
    }

    /**
     * Fonction appelée quand l'utilisateur appuie sur effacer
     * @param level le niveau où enlever l'objet
     * @param p la postion de l'objet à enlever
     */
    public static void removeObject(Level level, Point p) {
        if(editing) {
            level.removeObject(p);
        }
    }

    /**
     * Fonction appelée quand l'utilisateur clique sur le bouton "ajouter un niveau
     * @param updatedList La liste des niveaux qu'il faut actualiser
     */
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
