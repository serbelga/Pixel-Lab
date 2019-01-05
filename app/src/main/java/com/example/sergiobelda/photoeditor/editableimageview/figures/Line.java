package com.example.sergiobelda.photoeditor.editableimageview.figures;

public class Line {
    float x0, y0, xf, yf;
    int color;

    public Line(float x0, float y0, float xf, float yf, int color) {
        this.x0 = x0;
        this.y0 = y0;
        this.xf = xf;
        this.yf = yf;
        this.color = color;
    }

    public float getX0() {
        return x0;
    }

    public void setX0(float x0) {
        this.x0 = x0;
    }

    public float getY0() {
        return y0;
    }

    public void setY0(float y0) {
        this.y0 = y0;
    }

    public float getXf() {
        return xf;
    }

    public void setXf(float xf) {
        this.xf = xf;
    }

    public float getYf() {
        return yf;
    }

    public void setYf(float yf) {
        this.yf = yf;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
