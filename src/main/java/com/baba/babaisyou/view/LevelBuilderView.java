package com.baba.babaisyou.view;

import com.baba.babaisyou.model.GameObject;
import com.baba.babaisyou.model.Level;
import com.baba.babaisyou.model.Mouvement;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Effect;
import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.presenter.Game;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LevelBuilderView {

    static class LevelCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("X");

        public LevelCell() {
            super();

            hbox.getChildren().addAll(label, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
            hbox.setAlignment(Pos.CENTER);
            button.setOnAction(event -> {
                getListView().getItems().remove(getItem());

                File file = new File("src/main/resources/com/baba/babaisyou/levels/" + this.getText() + ".txt");
                System.out.println("src/main/resources/com/baba/babaisyou/levels/" + this.getText() + ".txt");
                RandomAccessFile raf= null;
                try {
                    raf = new RandomAccessFile(file,"rw");
                    raf.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(file.delete()) {
                    System.out.println("Deleted");
                } else {
                    System.out.println("Not deleted");
                }
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                label.setText(item);
                setGraphic(hbox);
            }
        }
    }


    private static Level level;
    private static String selectedMat;
    private static String selectedLevel;
    private static GameObject cursor;

    /**
     * Vue du constructeur de niveau
     * @param stage Le stage dans lequel afficher la scène
     */
    public static void show(Stage stage) {
        Game game = Game.getInstance();
//        HBox root = new HBox();

        BorderPane root = new BorderPane();
        Button backbtn = new Button("Retour");
        HBox btnHolder = new HBox();
        Popup popup = new Popup();
        Button newLevelBtn = new Button("Ajouter un niveau");
        Button editBtn = new Button("Editer le niveau");
        GridPane map = new GridPane();

        ObservableList<Material> materialsNames = FXCollections.observableArrayList(LevelBuilder.getMaterials());
        ListView<Material> materials  = new ListView<>(materialsNames);
        materials.setCellFactory(l -> new ListCell<>() {
            @Override
            public void updateItem(Material mat, boolean empty) {
                super.updateItem(mat, empty);
                if (empty) {
                    setText("");
                    setGraphic(null);
                } else {
                    setText(mat.name());
                    setGraphic(new ImageView(mat.getFrames()[0]));
                }
            }
        });
        materials.getSelectionModel().select(0);

        root.setLeft(materials);
//        lists.getChildren().add(materials);


        ObservableList<String> levelsNames = FXCollections.observableArrayList(LevelBuilder.getLevels());
        ListView<String> levels  = new ListView<>(levelsNames);
        levels.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new LevelCell();
            }
        });
        levels.getSelectionModel().select(0);


        root.setRight(levels);
//        lists.getChildren().add(levels);

        btnHolder.getChildren().add(backbtn);
        btnHolder.getChildren().add(newLevelBtn);
        btnHolder.getChildren().add(editBtn);
        root.setBottom(btnHolder);
        btnHolder.setAlignment(Pos.CENTER);
//      lists.getChildren().add(btnHolder);


//        root.getChildren().add(lists);

        Scene scene = new Scene(root, MainView.width, MainView.height);
        stage.setScene(scene);
        scene.getStylesheets().add((new File("src/levelBuilder.css")).toURI().toString());


        //Listener des listes
        materials.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Material>() {
            @Override
            public void changed(ObservableValue<? extends Material> observable, Material oldValue, Material newValue) {
                LevelBuilderView.selectedMat = newValue.name();
            }
        });
        levels.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                game.mapLoadLevel(newValue);
                selectedLevel = newValue;
                level = game.getLevel();
                addCursor();
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

        backbtn.setOnMouseClicked((MouseEvent event) -> {
            MenuView.show(stage);
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
//        root.getChildren().add(map);
        root.setCenter(map);
        game.mapLoadLevel("level0");
        level = game.getLevel();

        cursor = new GameObject(Material.Cursor, 0, 0);
        cursor.getTags().add(Effect.Player);
        addCursor();

        map.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.rgb(21, 24, 31), CornerRadii.EMPTY, Insets.EMPTY)));

        scene.setOnKeyPressed( (KeyEvent event) -> {

                KeyCode code = event.getCode();
                switch (code) {
                    case Z -> Mouvement.moveWithoutChecking(cursor, Direction.UP, level);
                    case S -> Mouvement.moveWithoutChecking(cursor, Direction.DOWN, level);
                    case Q -> Mouvement.moveWithoutChecking(cursor, Direction.LEFT, level);
                    case D -> Mouvement.moveWithoutChecking(cursor, Direction.RIGHT, level);
                    case ESCAPE -> stage.close();
                    case R -> {
                        game.mapLoadLevel(Level.getCurrentLevelNbr());
                        level = game.getLevel();
                        addCursor();
                    }
                    case ENTER -> {
                        LevelBuilder.PlaceObjects(level, selectedMat, cursor.getX(), cursor.getY());
                    }
                    case F11 -> stage.setFullScreen(!stage.isFullScreen());
                    case BACK_SPACE -> {
                        LevelBuilder.removeObject(level, cursor.getX(), cursor.getY());
                    }
                }
                LevelView.drawMovedObjects(map);
        });
        LevelView.drawMovedObjects(map);
        stage.show();
    }

    private static void addCursor() {

        int x = cursor.getX();
        int y = cursor.getY();

        if (0 <= x && x < level.getSizeX() && 0 <= y && y < level.getSizeY()) {
            level.get(cursor.getX(), cursor.getY()).add(cursor);

        } else {
            level.get(0, 0).add(cursor);
            cursor.setX(0);
            cursor.setY(0);
        }
        level.getInstances().get(Material.Cursor).add(cursor);

        Mouvement.getMovedObjects().put(cursor, Direction.NONE);
    }

    public static GameObject getCursor() {
        return cursor;
    }
}

