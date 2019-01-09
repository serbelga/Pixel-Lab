package com.example.sergiobelda.photoeditor.editableimageview.figures;

public class Square implements Polygon {
    private float x, y;
    private double side;
    private float color;
    private float rotation;

    public Square(float x, float y, double side, int color) {
        this.x = x;
        this.y = y;
        this.side = side;
        this.color = color;
        this.rotation = 0;
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

    public float getSize(){
        return (float) getSide();
    }

    @Override
    public void setSize(float size) {
        this.setSide(size);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
