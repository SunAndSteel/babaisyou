package com.baba.babaisyou.view;

import com.baba.babaisyou.presenter.LevelBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public class Selection {
    static String selectedLevel;
    public static void show(Stage primaryStage) {

        VBox root = new VBox(30);

        Text tx = new Text("Niveaux");
        Button back = new Button("Retour");
        root.getChildren().add(tx);


        ObservableList<String> levelsNames = FXCollections.observableArrayList(LevelBuilder.getLevels());
        ListView<String> levels  = new ListView<>(levelsNames);
        levels.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                return new CenteredListViewCell();
            }
        });
        levels.getSelectionModel().select(0);

        root.getChildren().add(levels);
        Scene scene = new Scene(root, MainView.width, MainView.height);
        scene.getStylesheets().add((new File("src/selection.css")).toURI().toString());
        root.setAlignment(Pos.CENTER);

        levels.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedLevel = newValue;
            }
        });

        root.getChildren().add(back);

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MenuView.show(primaryStage);
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
                    LevelView.show(primaryStage, selectedLevel);
                }
            }
        });

        primaryStage.setScene(scene);

    }
}

final class CenteredListViewCell extends ListCell<String> {
    @Override
    protected void updateItem(String item, boolean empty) {
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
