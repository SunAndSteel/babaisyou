package com.baba.babaisyou.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe qui représente les cellules de la liste des niveaux dans "LevelBuilderView.java"
 */
public class LevelCell extends ListCell<String> {
    HBox hbox = new HBox();
    HBox hbox2 = new HBox();
    Label label = new Label("");
    Label label2 = new Label("");
    Pane pane = new Pane();
    Pane pane2 = new Pane();
    Button button = new Button("X");
    Button button2 = new Button("");
    Set<String> defaultLevels = new HashSet<String>(
            Arrays.asList(
                    "level1",
                    "level2",
                    "level3",
                    "level4",
                    "level5",
                    "level6",
                    "level7"
            )
    );

    public LevelCell() {
        super();

        //Bouton supprimer
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = new File("src/main/resources/com/baba/babaisyou/levels/" + getItem() + ".txt");
                RandomAccessFile raf;
                try {
                    raf = new RandomAccessFile(file, "rw");
                    raf.close();
                    if (file.delete()) {
                        LevelCell.this.getListView().getItems().remove(getItem());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        hbox.getChildren().addAll(label, pane, button);
        hbox2.getChildren().addAll(label2, pane2, button2);
        HBox.setHgrow(pane, Priority.ALWAYS);
        hbox.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER_LEFT);
    }

    /**
     * Réécriture de la méthode updateItem pour placer le bouton dans la cellule
     * @see javafx.scene.control.ListCell#updateItem(Object, boolean)
     */
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);

        //Si un level est dans le set "defaultLevels", on n'ajoute pas le bouton supprimer
        boolean cond = false;
        try {
            if(defaultLevels.contains(getItem())) {
                label2.setText(item);
                setGraphic(hbox2);
                cond = true;
            }
        } catch (Exception ignored) {}
        if (!cond && item != null && !empty) {
            label.setText(item);
            setGraphic(hbox);
        }
    }
}
