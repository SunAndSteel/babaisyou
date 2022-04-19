package com.baba.babaisyou.model.enums;

import com.baba.babaisyou.view.Frames;
import javafx.scene.image.WritableImage;

/**
 * Textures qui peuvent être attribuées aux objets
 */
public enum Material {
    //Objets
    Baba("Ba", null, null, Frames.getFrames(1, 0, 1)),
    Floor("  ", null, null, Frames.getFrames(0, 0, 1)),
    Flag("Fg", null, null, Frames.getFrames(2, 0, 1)),
    Wall("Wl", null, null, Frames.getFrames(2, 0, 1)),
    Rock("RR", null, null, Frames.getFrames(2, 0, 1)),
    Button("Bt", null, null, Frames.getImages("file:src\\main\\resources\\com\\baba\\babaisyou\\views\\coin.png")),
    Is("Is", null, null, Frames.getImages("file:src\\main\\resources\\com\\baba\\babaisyou\\views\\Test35.png")),

    //Effets
    Push("Ps", Effects.Movable, null, Frames.getImages("file:src\\main\\resources\\com\\baba\\babaisyou\\views\\push.png")),
    Kill("Ki", Effects.Killer, null, Frames.getFrames(2, 0, 1)),
    Win("Wi", Effects.Winner, null, Frames.getImages("file:src\\main\\resources\\com\\baba\\babaisyou\\views\\win.png")),
    Stop("St", Effects.Hittable, null, Frames.getImages("file:src\\main\\resources\\com\\baba\\babaisyou\\views\\stop.png")),
    You("Yo", Effects.Player, null, Frames.getFrames(2, 0, 1)),
    Play("Pr", Effects.Play, null, Frames.getImages("file:src\\main\\resources\\com\\baba\\babaisyou\\views\\play.png")),

    //Textes
    TextWall("TW", null, "Wall", Frames.getFrames(2, 0, 1)),
    TextBaba("TB", null, "Baba", Frames.getFrames(2, 0, 1)),
    TextFlag("TF", null, "Flag", Frames.getFrames(2, 0, 1)),
    TextRock("TR", null, "Rock", Frames.getFrames(2, 0, 1)),
    TextBtn("BT", null, "Button", Frames.getImages("file:src\\main\\resources\\com\\baba\\babaisyou\\views\\btn.png")),

    //Titre
//    Title1("", null, null, Frames.getFrames(17, 17, 1)),
//    Title2("", null, null, Frames.getFrames(17, 18, 1)),
//    Title3("", null, null, Frames.getFrames(17, 19, 1)),
//    Title4("", null, null, Frames.getFrames(17, 20, 1)),
//    Title5("", null, null, Frames.getFrames(18, 17, 1)),
//    Title6("", null, null, Frames.getFrames(18, 18, 1)),
//    Title7("", null, null, Frames.getFrames(18, 19, 1)),
//    Title8("", null, null, Frames.getFrames(18, 20, 1)),
//    Title9("", null, null, Frames.getFrames(19, 17, 1)),
//    Title10("", null, null, Frames.getFrames(19, 18, 1)),
//    Title11("", null, null, Frames.getFrames(19, 19, 1)),
//    Title12("", null, null, Frames.getFrames(19, 20, 1)),
//    Title13("", null, null, Frames.getFrames(20, 17, 1)),
//    Title14("", null, null, Frames.getFrames(20, 18, 1)),
//    Title15("", null, null, Frames.getFrames(20, 19, 1)),
//    Title16("", null, null, Frames.getFrames(20, 20, 1));

    Title1("", null, null, Frames.getFrames(9, 4, 1)),
    Title2("", null, null, Frames.getFrames(9, 5, 1)),
    Title3("", null, null, Frames.getFrames(9, 6, 1)),
    Title4("", null, null, Frames.getFrames(9, 7, 1)),
    Title5("", null, null, Frames.getFrames(9, 8, 1)),
    Title6("", null, null, Frames.getFrames(9, 9, 1)),
    Title7("", null, null, Frames.getFrames(9, 10, 1)),
    Title8("", null, null, Frames.getFrames(9, 11, 1)),
    Title9("", null, null, Frames.getFrames(9, 12, 1)),
    Title10("", null, null, Frames.getFrames(9, 13, 1)),
    Title11("", null, null, Frames.getFrames(9, 14, 1)),
    Title12("", null, null, Frames.getFrames(9, 15, 1)),
    Title13("", null, null, Frames.getFrames(9, 16, 1)),
    Title14("", null, null, Frames.getFrames(9, 17, 1)),
    Title15("", null, null, Frames.getFrames(9, 18, 1)),
    Title16("", null, null, Frames.getFrames(9, 19, 1)),
    Title17("", null, null, Frames.getFrames(9, 20, 1)),
    Title18("", null, null, Frames.getFrames(10, 4, 1)),
    Title19("", null, null, Frames.getFrames(10, 5, 1)),
    Title20("", null, null, Frames.getFrames(10, 6, 1)),
    Title21("", null, null, Frames.getFrames(10, 7, 1)),
    Title22("", null, null, Frames.getFrames(10, 8, 1)),
    Title23("", null, null, Frames.getFrames(10, 9, 1)),
    Title24("", null, null, Frames.getFrames(10, 10, 1)),
    Title25("", null, null, Frames.getFrames(10, 11, 1)),
    Title26("", null, null, Frames.getFrames(10, 12, 1)),
    Title27("", null, null, Frames.getFrames(10, 13, 1)),
    Title28("", null, null, Frames.getFrames(10, 14, 1)),
    Title29("", null, null, Frames.getFrames(10, 15, 1)),
    Title30("", null, null, Frames.getFrames(10, 16, 1)),
    Title31("", null, null, Frames.getFrames(10, 17, 1)),
    Title32("", null, null, Frames.getFrames(10, 18, 1)),
    Title33("", null, null, Frames.getFrames(10, 19, 1)),
    Title34("", null, null, Frames.getFrames(10, 20, 1)),
    Title35("", null, null, Frames.getFrames(11, 4, 1)),
    Title36("", null, null, Frames.getFrames(11, 5, 1)),
    Title37("", null, null, Frames.getFrames(11, 6, 1)),
    Title38("", null, null, Frames.getFrames(11, 7, 1)),
    Title39("", null, null, Frames.getFrames(11, 8, 1)),
    Title40("", null, null, Frames.getFrames(11, 9, 1)),
    Title41("", null, null, Frames.getFrames(11, 10, 1)),
    Title42("", null, null, Frames.getFrames(11, 11, 1)),
    Title43("", null, null, Frames.getFrames(11, 12, 1)),
    Title44("", null, null, Frames.getFrames(11, 13, 1)),
    Title45("", null, null, Frames.getFrames(11, 14, 1)),
    Title46("", null, null, Frames.getFrames(11, 15, 1)),
    Title47("", null, null, Frames.getFrames(11, 16, 1)),
    Title48("", null, null, Frames.getFrames(11, 17, 1)),
    Title49("", null, null, Frames.getFrames(11, 18, 1)),
    Title50("", null, null, Frames.getFrames(11, 19, 1)),
    Title51("", null, null, Frames.getFrames(11, 20, 1));




    private final String str;
    private final Effects effect;
    private final String nameObjectAffected;
    private final WritableImage[] frames;

    Material(String str, Effects effect, String nameObjectAffected, WritableImage[] frames) {
        this.str = str;
        this.effect = effect;
        this.nameObjectAffected = nameObjectAffected;
        this.frames = frames;
    }

    public String toString() { return str; }

    public Effects getEffect() { return effect; }

    public String getNameObjectAffected() { return nameObjectAffected; }

    public WritableImage[] getFrames() { return frames; }
}