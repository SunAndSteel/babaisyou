package com.baba.babaisyou.presenter;

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

public class LevelCell extends ListCell<String> {
    HBox hbox = new HBox();
    HBox hbox2 = new HBox();
    Label label = new Label("");
    Label label2 = new Label("");
    Pane pane = new Pane();
    Pane pane2 = new Pane();
    Button button = new Button("X");
    Button button2 = new Button("");


    public LevelCell() {
        super();

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = new File("src/main/resources/com/baba/babaisyou/levels/" + getItem() + ".txt");
                RandomAccessFile raf;
                try {
                    raf = new RandomAccessFile(file, "rw");
                    raf.close();
                    if (file.delete()) {
                        System.out.println("Deleted");
                        LevelCell.this.getListView().getItems().remove(getItem());
                    } else {
                        System.out.println("Not deleted");
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


    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);

        boolean cond = false;

        try {
            if(getItem().equals("level0") ||getItem().equals("level1") ||getItem().equals("level2") ||getItem().equals("level3")) {
                label2.setText(item);
                setGraphic(hbox2);
                cond = true;
            }
        } catch (Exception e) {}
        if (!cond && item != null && !empty) {
            label.setText(item);
            setGraphic(hbox);
            cond = false;
        }
    }
}
