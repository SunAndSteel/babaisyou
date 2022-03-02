package com.baba.babaisyou.model;

public class Object {
    private int x, y;
    private boolean isHittable = false;
    private boolean isMovable = false;
    private boolean isWin = false;
    private boolean isPlayer = false;
    private boolean isKiller = false;


    public Object(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public boolean getHittable() {
        return isHittable;
    }

    public boolean getMovable() {
        return isMovable;
    }

    public boolean getWin() {
        return isWin;
    }

    public boolean getPlayer() {
        return isPlayer;
    }

    public boolean getKiller() {
        return isKiller;
    }

    public void setY(int dY) {
        y += dY;
    }

    public void setX(int dX) {
        x += dX;
    }

    public void setHittable(boolean state) {
         isHittable = state;
    }

    public void setMovable(boolean state) {
        isMovable = state;
    }

    public void setWin(boolean state) {
        isWin = state;
    }

    public void setPlayer(boolean state) {
        isPlayer = state;
    }

    public void setKiller(boolean state) {
        isKiller = state;
    }
}
