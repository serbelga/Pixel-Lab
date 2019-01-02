package com.example.sergiobelda.photoeditor.editableimageview;

public class Circle {
    private float x, y, radius;
    private float color;

    public Circle(float x, float y, float radius, float color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
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

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getColor() {
        return color;
    }

    public void setColor(float color) {
        this.color = color;
    }
}
