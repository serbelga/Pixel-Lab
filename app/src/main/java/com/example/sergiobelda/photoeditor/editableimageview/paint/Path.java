package com.example.sergiobelda.photoeditor.editableimageview.paint;

import com.example.sergiobelda.photoeditor.editableimageview.figures.Line;

import java.util.ArrayList;

public class Path {
    private ArrayList<Line> lines;
    private int color = -1;
    private float strokeWidth;

    public Path() {
        this.lines = new ArrayList<>();
    }

    public Path(int color, float strokeWidth) {
        this.lines = new ArrayList<>();
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    public Path(ArrayList<Line> lines, int color, float strokeWidth) {
        this.lines = lines;
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }
}
