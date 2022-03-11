package com.baba.babaisyou.model.enums;

import com.baba.babaisyou.model.Level;
import com.baba.babaisyou.model.Object;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    public final int dX, dY;

    Direction(int dX, int dY) {
        this.dX = dX; this.dY = dY;
    }

//    public Direction verifyNewPos(Object object, Direction direction) {
//        int newX = object.getX() + direction.dX;
//        int newY = object.getY() + direction.dY;
//        if (0 <= newX && newX <= Level.sizeX - 1 && 0 <= newY && newY <= Level.sizeY - 1)
//            return direction;
//        return Direction.NONE;
//
//    }
}
