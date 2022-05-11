package com.baba.babaisyou.view;

import com.baba.babaisyou.model.Level;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.Game;
import com.baba.babaisyou.presenter.LevelBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class LevelBuilderView {
    private static Level level;
    private static String selectedMat;
    private static String selectedLevel;

    /**
     * Vue du constructeur de niveau
     * @param stage Le stage dans lequel afficher la scène
     */
    public static void show(Stage stage) {
        Game game = Game.getInstance();
        HBox root = new HBox();
        VBox lists = new VBox();
        HBox btnHolder = new HBox();
        VBox popuplHolder = new VBox();
        Popup popup = new Popup();
        Button newLevelBtn = new Button("Ajouter un niveau");
        Button editBtn = new Button("Editer le niveau");
        GridPane map = new GridPane();

        ObservableList<String> materialsNames = FXCollections.observableArrayList(LevelBuilder.getMaterials());
        ListView<String> materials  = new ListView<>(materialsNames);

        lists.getChildren().add(materials);

        ObservableList<String> levelsNames = FXCollections.observableArrayList(LevelBuilder.getLevels());
        ListView<String> levels  = new ListView<>(levelsNames);


        lists.getChildren().add(levels);


        btnHolder.getChildren().add(newLevelBtn);
        btnHolder.getChildren().add(editBtn);
        lists.getChildren().add(btnHolder);


        root.getChildren().add(lists);

        Scene scene = new Scene(root, MainView.width, MainView.height);
        stage.setScene(scene);


        //Listener des listes
        materials.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                LevelBuilderView.selectedMat = newValue;
            }
        });
        levels.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                game.mapLoadLevel(newValue, true);
                selectedLevel = newValue;
                level = game.getLevel();
                LevelView.drawMovedObjects(map);
            }
        });

        //Listener boutons
        editBtn.setOnMouseClicked((MouseEvent event) -> {
            LevelBuilder.EditButtonAction(levels, newLevelBtn, editBtn, selectedLevel, level );
        });
        newLevelBtn.setOnMouseClicked((MouseEvent event) -> {
            popup.show(stage);
            LevelBuilder.NewLevelButtonAction(levelsNames);
            levels.refresh();
        });

        //Les events Enter et Escape ne s'affichent pas à cause des listes donc j'ajoute un eventfilter pour éviter le bug
        materials.addEventFilter( KeyEvent.KEY_PRESSED, keyEvent -> {
            if( keyEvent.getCode() == KeyCode.ESCAPE || keyEvent.getCode() == KeyCode.ENTER) {
                if( materials.getEditingIndex() == -1 ) {
                    // Not editing.
                    final Parent parent = materials.getParent();
                    parent.fireEvent( keyEvent.copyFor( parent, parent ) );
                }
                keyEvent.consume();
            }
        } );
        levels.addEventFilter( KeyEvent.KEY_PRESSED, keyEvent -> {
            if( keyEvent.getCode() == KeyCode.ESCAPE || keyEvent.getCode() == KeyCode.ENTER) {
                if( levels.getEditingIndex() == -1 ) {
                    // Not editing.
                    final Parent parent = levels.getParent();
                    parent.fireEvent( keyEvent.copyFor( parent, parent ) );
                }
                keyEvent.consume();
            }
        } );

        //Afficher un level vide
        root.getChildren().add(map);
        game.mapLoadLevel("level0", true);
        level = game.getLevel();
        map.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.rgb(21, 24, 31), CornerRadii.EMPTY, Insets.EMPTY)));

        scene.setOnKeyPressed( (KeyEvent event) -> {

                KeyCode code = event.getCode();
                switch (code) {
                    case Z -> game.movePlayers(Direction.UP);
                    case S -> game.movePlayers(Direction.DOWN);
                    case Q -> game.movePlayers(Direction.LEFT);
                    case D -> game.movePlayers(Direction.RIGHT);
                    case ESCAPE -> stage.close();
                    case R -> {
                        game.mapLoadLevel(Level.getCurrentLevelNbr(), true);
                        level = game.getLevel();
                    }
                    case ENTER -> {
                        LevelBuilder.PlaceObjects(level, selectedMat, level.getCursorPosition());
                    }
                    case F11 -> stage.setFullScreen(!stage.isFullScreen());
                    case BACK_SPACE -> {
                        System.out.println("Back");
                        LevelBuilder.removeObject(level, level.getCursorPosition());
                        LevelView.drawMovedObjects(map);
                    }
                }
                LevelView.drawMovedObjects(map);
        });
        LevelView.drawMovedObjects(map);
        stage.show();
    }
}

