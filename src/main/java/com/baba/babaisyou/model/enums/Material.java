package com.baba.babaisyou.model.enums;

/**
 * Textures qui peuvent être attribuées aux objets
 */
public enum Material {
    Baba("Ba", null, null, "file:src/main/resources/com/baba/babaisyou/views/Baba.png"),
    Floor("  ", null, null, "file:src/main/resources/com/baba/babaisyou/views/Floor.png"),
    Flag("Fg", null, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png"),
    Wall("Wl", null, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png"),
    Rock("RR", null, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png"),
    Is("Is", null, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png"),
    Push("Ps", Effects.Movable, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png"),
    Kill("Ki", Effects.Killer, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png"),
    Win("Wi", Effects.Winner, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png"),
    Stop("St", Effects.Hittable, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png"),
    You("Yo", Effects.Player, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png"),
    TextWall("TW", null, "Wall", "file:src/main/resources/com/baba/babaisyou/views/Wall.png"),
    TextBaba("TB", null, "Baba", "file:src/main/resources/com/baba/babaisyou/views/Wall.png"),
    TextFlag("TF", null, "Flag", "file:src/main/resources/com/baba/babaisyou/views/Wall.png"),
    TextRock("TR", null, "Rock", "file:src/main/resources/com/baba/babaisyou/views/Wall.png");

    private final String str;
    private final Effects effect;
    private final String nameObjectAffected;
    private final String imageUrl;

    Material(String str, Effects effect, String nameObjectAffected, String imageUrl) {
        this.str = str;
        this.effect = effect;
        this.nameObjectAffected = nameObjectAffected;
        this.imageUrl = imageUrl;
    }

    public String toString() { return str; }

    public Effects getEffect() { return effect; }

    public String getNameObjectAffected() { return nameObjectAffected; }

    public String getImageUrl() { return imageUrl; }
}