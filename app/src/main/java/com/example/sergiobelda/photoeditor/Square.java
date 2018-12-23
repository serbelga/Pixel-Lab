package com.example.sergiobelda.photoeditor;

public class Square {
    private float x, y;
    private double side;
    private float color;

    public Square(float x, float y, double side, int color) {
        this.x = x;
        this.y = y;
        this.side = side;
        this.color = color;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    public float getColor() {
        return color;
    }

    public void setColor(float color) {
        this.color = color;
    }
}
