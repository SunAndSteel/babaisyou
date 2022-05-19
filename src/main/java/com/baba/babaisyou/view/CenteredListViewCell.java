package com.baba.babaisyou.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;


/**
 * Class qui représente les cellules de la liste dans "SelectionView.java"
 */
public class CenteredListViewCell extends ListCell<String> {

    /**
     * Réécriture de la méthode updateItem pour centrer les cellules de la liste
     * @see javafx.scene.control.ListCell#updateItem(Object, boolean)
     */
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);

            Label label = new Label(item);
            label.setAlignment(Pos.CENTER);

            hBox.getChildren().add(label);
            setGraphic(hBox);

        }
    }
}
