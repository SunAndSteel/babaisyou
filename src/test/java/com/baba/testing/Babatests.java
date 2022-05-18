package com.baba.testing;

import com.baba.babaisyou.model.*;
import com.baba.babaisyou.model.enums.Effect;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.view.MapView;
import javafx.application.Platform;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class Babatests {

    private static String path = "src/test/resources/";

    @BeforeAll
    public static void setUpJavaFXRuntime() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await(5, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void tearDownJavaFXRuntime() {
        Platform.exit();
    }

    @Test
    public void moveTest() {
        // On lance le niveau "ruleTest"
        Level level = new Level("ruleTest", path);

        // Dans le niveau, il y a Baba en x = 0 et y = 0
        // et une règle Baba Is You


        //Tester si on ne peut pas effectuer de déplacement interdit
        // Essaie de bouger le joueur en dehors de la map en position x : 0, y :-1
        Mouvement.movePlayers(Direction.UP, level);

        //On s'attend à ce que l'objet ne bouge pas puisque le déplacement est interdit
        Assertions.assertSame(level.get(0, 0).get(1).getMaterial(), Material.Baba);

        //Tester si le déplacement est bien effectué
        // On déplace le joueur de x : 0, y : 0 vers x : 0, y : 1
        Mouvement.movePlayers(Direction.DOWN, level);

        // On s'attend à ce que le joueur soit en position x : 0, y : 1
        Assertions.assertSame(level.get(0, 1).get(1).getMaterial(), Material.Baba);
    }

    @Test
    public void updatedRulesTest() {

        // On lance le niveau "ruleTest"
        Level level = new Level("ruleTest", path);

        // Dans le niveau, il y a Baba en x = 0 et y = 0
        // TextBaba en x = 0 et y = 1
        // Is en x = 1 et y = 1
        // You en x = 2 et y = 1

        // On bouge l'objet "Baba" vers le bas.
        Mouvement.movePlayers(Direction.DOWN, level);

        // On s'attend à ce qu'il bouge vers x : 0, y : 1
        Assertions.assertSame(level.get(0, 1).get(1).getMaterial(), Material.Baba);

        // Vu que baba s'est déplacer vers textBaba, alors textBaba s'est aussi déplacer vers le bas.
        Assertions.assertSame(level.get(0, 2).get(1).getMaterial(), Material.TextBaba);

        Rule.checkRules(level);

        // On bouge l'objet "Baba" vers le bas
        Mouvement.movePlayers(Direction.DOWN, level);

        // Vu que textBaba ne forme plus une règle avec les autres objets, Baba n'est pas censé bouger et rester en x = 0 et y = 1
        Assertions.assertSame(level.get(0, 1).get(1).getMaterial(), Material.Baba);

    }

    @Test
    public void winLooseTest() {

        // On lance le niveau "winTest"
        Level level = new Level("winTest", path);

        // Dans ce niveau, il y a Baba en x = 0 et y = 0
        // Flag en x = 0 et y = 1
        // et il y a une règle Baba Is You
        // et Flag Is Win

        // On bouge le player vers le drapeau.
        Mouvement.movePlayers(Direction.DOWN, level);

        Assertions.assertTrue(level.isWin());

        Mouvement.getMovedObjects().clear();

        // On lance le niveau "ruleTest"
        level = new Level("ruleTest", path);

        // Dans le niveau, il y a Baba en x = 0 et y = 0
        // TextBaba en x = 0 et y = 1
        // Is en x = 1 et y = 1
        // You en x = 2 et y = 1

        // On bouge l'objet "Baba" vers le bas.
        Mouvement.movePlayers(Direction.DOWN, level);

        Rule.checkRules(level);

        // La règle Baba Is You n'est plus formé, donc on s'attend à ce que c'est loose.
        Assertions.assertTrue(level.checkLoose());
    }

    @Test
    public void mapFormatTest() {

        // On charge un bon niveau
        MapView mapView = new MapView();
        mapView.setLevel("level1");
        Level level = mapView.getLevel();

        // On s'attend à ce que levelGrid est non null
        Assertions.assertNotNull(level.getLevelGrid());

        // On s'attend à ce qu'un objet Baba est en x = 5 et y = 8.
        boolean isInGrid = false;

        for (GameObject object : level.get(5, 8)) {
            if (object.getMaterial() == Material.Baba) {
                isInGrid = true;
                break;
            }
        }
        Assertions.assertTrue(isInGrid);


        // On essaye de charger un mauvais niveau
        mapView.setLevel("incorrectMap", "src/test/resources/");

        // On s'attend à ce que le niveau n'est pas changer
        Assertions.assertSame(level, mapView.getLevel());
    }

}
