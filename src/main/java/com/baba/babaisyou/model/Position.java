package com.baba.babaisyou.model;

/**
 * Classe qui représente la position d'un objet dans la map
 */
public class Position {
    private final int x, y;

    public Position(int x, int y) {
        this.x = x; this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
