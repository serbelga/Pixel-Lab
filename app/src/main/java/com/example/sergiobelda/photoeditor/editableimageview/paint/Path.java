package com.example.sergiobelda.photoeditor.editableimageview.paint;

import com.example.sergiobelda.photoeditor.editableimageview.figures.Line;

import java.util.ArrayList;

public class Path {
    private ArrayList<Line> lines;
    private int color = -1;

    public Path() {
        this.lines = new ArrayList<>();
    }

    public Path(int color) {
        this.lines = new ArrayList<>();
        this.color = color;
    }

    public Path(ArrayList<Line> lines, int color) {
        this.lines = lines;
        this.color = color;
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
}
