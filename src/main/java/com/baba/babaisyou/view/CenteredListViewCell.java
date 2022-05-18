package com.baba.babaisyou.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public final class CenteredListViewCell extends ListCell<String> {
    @Override
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
