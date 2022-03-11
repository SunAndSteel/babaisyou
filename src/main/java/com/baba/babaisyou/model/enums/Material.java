package com.baba.babaisyou.model.enums;

public enum Material {
    Baba("Ba", null, null, "C:\\Users\\jerem\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\views\\Baba.png"),
    Floor("  ", null, null, "C:\\Users\\jerem\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\views\\Floor.png"),
    Flag("Fg", null, null, "C:\\Users\\jerem\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\views\\Wall.png"),
    Wall("Wl", null, null, "C:\\Users\\jerem\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\views\\Wall.png"),
    Rock("RR", null, null, "C:\\Users\\jerem\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\views\\Wall.png"),
    Is("Is", null, null, "C:\\Users\\jerem\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\views\\Wall.png"),
    Push("Ps", Effects.Movable, null, "C:\\Users\\jerem\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\views\\Wall.png"),
    Kill("Ki", Effects.Killer, null, "C:\\Users\\jerem\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\views\\Wall.png"),
    Win("Wi", Effects.Winner, null, "C:\\Users\\jerem\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\views\\Wall.png"),
    Stop("St", Effects.Hittable, null, "C:\\Users\\jerem\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\views\\Wall.png"),
    You("Yo", Effects.Player, null, "C:\\Users\\jerem\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\views\\Wall.png"),
    TextWall("TW", null, "Wall", "C:\\Users\\jerem\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\views\\Wall.png"),
    TextBaba("TB", null, "Baba", "C:\\Users\\jerem\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\views\\Wall.png"),
    TextFlag("TF", null, "Flag", "C:\\Users\\jerem\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\views\\Wall.png"),
    TextRock("TR", null, "Rock", "C:\\Users\\jerem\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\views\\Wall.png");

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