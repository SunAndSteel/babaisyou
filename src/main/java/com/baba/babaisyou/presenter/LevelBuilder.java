package com.baba.babaisyou.presenter;

import com.baba.babaisyou.model.GameObject;
import com.baba.babaisyou.model.Level;
import com.baba.babaisyou.model.LevelLoader;
import com.baba.babaisyou.model.Mouvement;
import com.baba.babaisyou.model.enums.Direction;
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
import java.util.Objects;

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
            if (mat != Material.Cursor && mat != Material.Floor) {
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
        File f = new File("src/main/resources/com/baba/babaisyou/levels");

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
                    System.out.println(selectedLevel);
                    LevelLoader.save(level, selectedLevel);

                    System.out.println("Saved.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            levels.setDisable(false);
            editBtn.setText("Editer le niveau");
        }
    }

    /**
     * Fonction appelée quand l'utilisateur appuie sur entrée
     * @param level le niveau sur lequel placer l'objet
     * @param SelectedMat l'objet a placer
     * @param x la position x où placer l'objet
     * @param y la position y où placer l'objet
     */
    public static void PlaceObjects(Level level, String SelectedMat, int x, int y) {
        ArrayList<GameObject> objects = level.get(x, y);

        if(editing && objects.size() == 2) {
//            level.addObject(new GameObject(SelectedMat,p.x, p.y), p);
            Material material = Material.valueOf(SelectedMat);
            objects.add(new GameObject(material, x, y));
            Mouvement.getMovedObjects().put(LevelBuilderView.getCursor(), Direction.NONE);
        }
    }

    /**
     * Fonction appelée quand l'utilisateur appuie sur effacer
     * @param level le niveau où enlever l'objet
     * @param x la position x de l'objet à enlever
     * @param y la position y de l'objet à enlever
     */
    public static void removeObject(Level level, int x, int y) {
        if(editing) {
            ArrayList<GameObject> objects = level.get(x, y);

            for (GameObject object : objects) {

                Material material = object.getMaterial();

                if (material != Material.Cursor && material != Material.Floor) {
                    objects.remove(object);
                    Mouvement.getMovedObjects().put(object, Direction.NONE);
                    break;
                }
            }
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
                File newLevel = new File("src/main/resources/com/baba/babaisyou/levels/" + name + ".txt");
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
