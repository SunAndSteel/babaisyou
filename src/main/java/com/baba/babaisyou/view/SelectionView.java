package com.baba.babaisyou.view;

import com.baba.babaisyou.model.LevelLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;

import static com.baba.babaisyou.view.MainView.scene;
import static javafx.scene.input.KeyEvent.KEY_PRESSED;

/**
 * Classe qui représente la vue de la selection de niveaux (Menu principal -> le bouton "Niveaux" appelle cette classe)
 */
public class SelectionView {
    static String selectedLevel;
    public static void show(Stage primaryStage) {

        VBox root = new VBox(30);
        HBox buttonHolder = new HBox();

        Text text = new Text("Niveaux");
        Button backBtn = new Button("Retour");
        Button playBtn = new Button("Commencer");

        //Ajouter des boutons dans des layers
        buttonHolder.getChildren().addAll(backBtn, playBtn);
        buttonHolder.setAlignment(Pos.CENTER);

        // Récupère les noms des niveaux à afficher dans la liste.
        ObservableList<String> levelsNames = FXCollections.observableArrayList(LevelLoader.getLevelNames());
        ListView<String> levels  = new ListView<>(levelsNames);
        levels.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                return new CenteredListViewCell();
            }
        });

        //Initialisation de la scène
        scene = MainView.getScene();
        scene.setRoot(root);
        scene.getStylesheets().add((new File("src/selection.css")).toURI().toString());

        //Définition de l'interface
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(text, levels, buttonHolder);

        //Récupérer la valeur l'item sélectionné
        levels.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedLevel = newValue;
            }
        });

        //Gestionnaire des événements sur les boutons
        backBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MainView.show();
            }
        });
        playBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selectedLevel != null) {
                    LevelView.show(primaryStage, selectedLevel);
                }
            }
        });
        levels.addEventFilter( KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ESCAPE || keyEvent.getCode() == KeyCode.ENTER) {
                    if (levels.getEditingIndex() == -1) {
                        final Parent parent = levels.getParent();
                        parent.fireEvent(keyEvent.copyFor(parent, parent));
                    }
                    keyEvent.consume();
                }
            }
        });
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    if (selectedLevel != null) {
                        LevelView.show(primaryStage, selectedLevel);
                    }
                }
            }
        });
    }
}

