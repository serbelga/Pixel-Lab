package com.example.sergiobelda.photoeditor.editableimageview.figures;

public class Circle implements Polygon {
    private float x, y, radius;
    private float color;

    public Circle(float x, float y, float radius, float color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
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

    @Override
    public float getSize() {
        return this.getRadius();
    }

    @Override
    public void setSize(float size) {
        this.setRadius(size);
    }

    public void setColor(float color) {
        this.color = color;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }
}
