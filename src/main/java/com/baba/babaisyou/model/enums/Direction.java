package com.baba.babaisyou.model.enums;

/**
 * Directions dans lesquelles un objet sur la map peut se déplacer
 */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    NONE(0, 0);

    public final int dX, dY;

    Direction(int dX, int dY) {
        this.dX = dX; this.dY = dY;
    }
}
