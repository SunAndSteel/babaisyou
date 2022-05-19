package com.baba.babaisyou.view;

import com.baba.babaisyou.model.GameObject;
import com.baba.babaisyou.model.Level;
import com.baba.babaisyou.model.LevelLoader;
import com.baba.babaisyou.model.Mouvement;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Effect;
import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.presenter.LevelBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

/**
 * Class qui représente la vue du constructeur de niveau
 */
public class LevelBuilderView {

    private static Level level;
    private static Material selectedMat;
    private static String selectedLevel;
    private static GameObject cursor;
    private static Scene scene;
    private static Stage stage;
    private static final MapView map = new MapView();

    /**
     * Vue du constructeur de niveau
     * @param stage Le stage dans lequel afficher la scène
     */
    public static void show(Stage stage) {

        LevelBuilderView.stage = stage;

        BorderPane root = new BorderPane();
        Button backBtn = new Button("Retour");
        HBox btnHolder = new HBox();
        Popup popup = new Popup();
        Button newLevelBtn = new Button("Ajouter un niveau");
        Button editBtn = new Button("Editer le niveau");

        //Initialiser la liste des matériaux avec l'image du matériel à côté du nom
        ObservableList<Material> materialsNames = FXCollections.observableArrayList(LevelBuilder.getMaterials());
        ListView<Material> materials  = new ListView<>(materialsNames);

        //Permet de customiser les cellules de la liste
        materials.setCellFactory(new Callback<ListView<Material>, ListCell<Material>>() {
            @Override
            public ListCell<Material> call(ListView<Material> l) {
                return new ListCell<>() {
                    @Override
                    public void updateItem(Material mat, boolean empty) {
                        super.updateItem(mat, empty);
                        if (empty) {
                            setText("");
                            setGraphic(null);
                        } else {
                            setText(mat.name());
                            setGraphic(new ImageView(mat.getImages()[0]));
                        }
                    }
                };
            }
        });

        //Initialiser la liste des niveaux avec des cellules customs définies dans le fichier "LevelCell.java"
        ObservableList<String> levelNames = FXCollections.observableArrayList(LevelLoader.getLevelNames());
        ListView<String> levels  = new ListView<>(levelNames);
        levels.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new LevelCell();
            }
        });

        //Définir l'interface
        root.setLeft(materials);
        root.setRight(levels);
        root.setBottom(btnHolder);
        btnHolder.getChildren().add(backBtn);
        btnHolder.getChildren().add(newLevelBtn);
        btnHolder.getChildren().add(editBtn);
        btnHolder.setAlignment(Pos.CENTER);
        scene = MainView.getScene();
        scene.setRoot(root);
        scene.getStylesheets().add((new File("src/levelBuilder.css")).toURI().toString());

        // Permet que si le niveau choisi est mauvais, alors ça choisi un autre niveau tant qu'il en troue un de correct.
        // Il en trouvera toujours un car il y a les niveaux de base du jeu qui sont corrects.
        boolean isCorrect;
        int index = 0;
        do {
            levels.getSelectionModel().select(index);
            selectedLevel = levels.getSelectionModel().getSelectedItem();
            isCorrect = map.setLevel(selectedLevel);
            index++;

        } while (!isCorrect && index < levelNames.size());

        // Charge le niveau.
        map.setLevel(selectedLevel);
        level = map.getLevel();
        root.setCenter(map);

        // Ajoute le curseur dans le niveau.
        cursor = new GameObject(Material.Cursor, 0, 0);
        cursor.getTags().add(Effect.Player);
        addCursor();

        map.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.rgb(21, 24, 31), CornerRadii.EMPTY, Insets.EMPTY)));

        // Initialise-les listeners.
        listListeners(materials, levels);
        buttonListeners(editBtn, newLevelBtn, backBtn, levels, levelNames, popup);

        // Initialise-les controls
        loadControls();

        // Calcule la taille correcte des ImageViews et change leur taille.
        map.WidthHeightListener(stage, true);
        map.resizeIVs();


        map.drawMovedObjects();
        stage.show();
    }

    /**
     * Méthode qui permet d'ajouter un curseur dans le niveau
     */
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

    /**
     * Getter de cursor.
     * @return Le curseur
     */
    public static GameObject getCursor() {
        return cursor;
    }

    /**
     * Méthode qui initialise les controls.
     */
    private static void loadControls() {

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {

                if (map.getTransitions().isEmpty()) {
                    KeyCode code = event.getCode();

                    switch (code) {
                        case Z:
                            Mouvement.moveWithoutChecking(cursor, Direction.UP, level);
                            break;
                        case S:
                            Mouvement.moveWithoutChecking(cursor, Direction.DOWN, level);
                            break;
                        case Q:
                            Mouvement.moveWithoutChecking(cursor, Direction.LEFT, level);
                            break;
                        case D:
                            Mouvement.moveWithoutChecking(cursor, Direction.RIGHT, level);
                            break;

                        case ESCAPE:
                            stage.close();
                            break;

                        case R:
                            if (map.setLevel(selectedLevel)) {
                                level = map.getLevel();
                                addCursor();
                            }
                            break;

                        case ENTER:
                            LevelBuilder.PlaceObjectsAtCursor(level, selectedMat, cursor);
                            break;

                        case F11:
                            stage.setFullScreen(!stage.isFullScreen());
                            break;

                        case BACK_SPACE:
                            LevelBuilder.removeObject(level, cursor.getX(), cursor.getY());
                            break;
                    }

                    map.drawMovedObjects();
                }
            }
        });
    }

    /**
     * Gestionnaire d'événements sur les boutons
     * @param editBtn Bouton "éditer"
     * @param newLevelBtn Bouton "ajouter un niveau"
     * @param backBtn Bouton "retour"
     * @param levels Liste des niveaux
     * @param levelNames Liste du nom des niveaux
     * @param popup Popup ajouter un niveau
     */
    private static void buttonListeners(Button editBtn,
                                        Button newLevelBtn,
                                        Button backBtn,
                                        ListView<String> levels,
                                        ObservableList<String> levelNames,
                                        Popup popup) {

        editBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LevelBuilder.EditButtonAction(levels, newLevelBtn, editBtn, selectedLevel, level);
            }
        });
        newLevelBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popup.show(stage);
                LevelBuilder.NewLevelButtonAction(levelNames);
                levels.refresh();
            }
        });

        backBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MainView.show();
            }
        });
    }

    /**
     * Gestionnaires des événements sur les listes
     * @param materials Liste des matériaux
     * @param levels Liste des niveaux
     */
    private static void listListeners(ListView<Material> materials, ListView<String> levels) {

        materials.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Material>() {
            @Override
            public void changed(ObservableValue<? extends Material> observable, Material oldValue, Material newValue) {
                selectedMat = newValue;
            }
        });
        levels.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (map.setLevel(newValue)) {
                    selectedLevel = newValue;
                    level = map.getLevel();
                    addCursor();
                    map.drawMovedObjects();
                    map.calculateTileSize(stage, true);
                    map.resizeIVs();
                } else {

                    levels.getSelectionModel().select(oldValue);

                }
            }
        });

        //Les events Enter et Escape ne s'affichent pas à cause des listes donc on ajoute un EventFilter pour éviter le bug
        materials.addEventFilter( KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ESCAPE || keyEvent.getCode() == KeyCode.ENTER) {
                    if (materials.getEditingIndex() == -1) {
                        // Not editing.
                        final Parent parent = materials.getParent();
                        parent.fireEvent(keyEvent.copyFor(parent, parent));
                    }
                    keyEvent.consume();
                }
            }
        });
        levels.addEventFilter( KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ESCAPE || keyEvent.getCode() == KeyCode.ENTER) {
                    if (levels.getEditingIndex() == -1) {
                        // Not editing.
                        final Parent parent = levels.getParent();
                        parent.fireEvent(keyEvent.copyFor(parent, parent));
                    }
                    keyEvent.consume();
                }
            }
        });
    }
}

