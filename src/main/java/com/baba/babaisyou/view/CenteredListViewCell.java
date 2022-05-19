package com.baba.babaisyou.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;


/**
 * Class qui représente les cellules de la liste dans "SelectionView.java"
 */
public class CenteredListViewCell extends ListCell<String> {
    @Override

    //Afficher les cellules au centre de la vue
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            // Create the HBox
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);

            // Create centered Label
            Label label = new Label(item);
            label.setAlignment(Pos.CENTER);

            hBox.getChildren().add(label);
            setGraphic(hBox);

        }
    }
}
