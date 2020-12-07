package ru.vsu.cs.course2.converter;

public class RealPoint {
    //хранит описание некоторой точки в сист коорд реал мира
    private double x, y;

    //alt+insert
    public RealPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
