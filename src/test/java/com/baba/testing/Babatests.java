package com.baba.testing;

import com.baba.babaisyou.model.Game;
import com.baba.babaisyou.model.GameObject;
import com.baba.babaisyou.model.Level;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Material;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class Babatests {

    @BeforeAll
    public static void setUpJavaFXRuntime() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await(5, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void tearDownJavaFXRuntime() throws InterruptedException {
        Platform.exit();
    }

    @Test
    public void moveTest() {
        //Initialiser un niveau vide avec juste un curseur déplaçable
        Game game = Game.getInstance();
        game.mapLoadLevel(new Level(), true);
        Level level = game.getLevel();

        GameObject player = new GameObject(Material.Cursor, 0, 0);
        level.addObject(player, new Point(0, 0));

        //Tester si on ne peut pas effectuer de déplacement interdit
        game.movePlayers(Direction.UP); // Essaie de bouger le joueur en dehors de la map en position x : 0, y :-1
        Assertions.assertSame(level.getObjects(new Point(0, 0)).get(1), player); //On s'attend à ce que l'objet ne bouge pas puisque le déplacement est interdit

        //Tester si le déplacement est bien effectué
        game.movePlayers(Direction.DOWN); // On déplace le joueur de x : 0, y : 0 vers x : 0, y : 1
        Assertions.assertSame(level.getObjects(new Point(0, 1)).get(1), player); // On s'attend à ce que le joueur soit en position x : 0, y : 1

    }

    @Test
    public void updatedRulesTest() {
        //Initialiser un niveau vide avec la règle "baba is you" qui permet à l'objet "baba" de se déplacer
        Game game = Game.getInstance();
        game.mapLoadLevel(new Level());
        Level level = game.getLevel();

        GameObject player = new GameObject(Material.Baba, 5, 0);

        level.addObject(player, new Point(5, 0));
        level.addObject(new GameObject(Material.TextBaba, 0, 0), new Point(0, 0));
        level.addObject(new GameObject(Material.Is, 1, 0), new Point(1, 0));
        level.addObject(new GameObject(Material.You, 2, 0), new Point(2, 0));


        game.movePlayers(Direction.RIGHT); // On bouge l'objet "Baba" à droite
        Assertions.assertSame(level.get(6, 0).get(1), player.getMaterial()); // On s'attend à ce qu'il bouge vers x : 6, y : 0

        level.removeObject(new Point(1, 0)); // Enlève l'objet "is", rendant la règle obsolete

        game.movePlayers(Direction.RIGHT); // On bouge l'objet "Baba" à droite
        Assertions.assertNotSame(level.getObjects(new Point(6, 0)).get(0), player); // On s'attend à ce qu'il ne bouge pas vers x : 7, y : 0

    }

    @Test
    public void winTest() {
        Game game = Game.getInstance();
        game.mapLoadLevel(new Level());
        Level level = game.getLevel();

        //tester les conditions de victoire et de défaite
        GameObject player = new GameObject(Material.Baba, 5, 0);

        level.addObject(player, new Point(5, 0));
        level.addObject(new GameObject(Material.TextBaba, 0, 0), new Point(0, 0));
        level.addObject(new GameObject(Material.Is, 1, 0), new Point(1, 0));
        level.addObject(new GameObject(Material.You, 2, 0), new Point(2, 0));

        level.addObject(new GameObject(Material.TextFlag, 1, 0), new Point(0, 1));
        level.addObject(new GameObject(Material.Is, 1, 1), new Point(1, 1));
        level.addObject(new GameObject(Material.Win,2, 1), new Point(2, 1));

        level.addObject(new GameObject(Material.Flag, 6, 0), new Point(0, 1));

        game.update();
        game.movePlayers(Direction.RIGHT); // On bouge le joueur vers la position du drapeau, on s'attend à ce que le niveau change
        game.checkWin();

        Assertions.assertFalse(level == game.getLevel());


    }

    @Test
    public void mapFormatTest() {
        Game game = Game.getInstance();

        //On charge un mauvais niveau dans le jeu
        game.mapLoadLevel(new Level("incorrectMap", "src/test/java/incorrectMap.txt"));
        Level levelTest1 = game.getLevel(); //Génère une erreur

        //On charge un bon niveau dans le jeu, on s'attend à avoir "level1.txt" dans level
        game.mapLoadLevel(new Level("level1"));
        Level levelTest2 = game.getLevel();

        ArrayList<String> test2 = levelTest2.save();

        //on s'attend à avoir test1 vide et test2 chargé avec level1.txt
        //Assertions.assertEquals(null, levelTest1 );
        Assertions.assertTrue(test2.size() > 0);
    }

}
