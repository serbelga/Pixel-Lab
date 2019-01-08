package com.example.sergiobelda.photoeditor.editableimageview;

import com.example.sergiobelda.photoeditor.editableimageview.figures.Line;
import com.example.sergiobelda.photoeditor.editableimageview.figures.Polygon;
import com.example.sergiobelda.photoeditor.editableimageview.paint.Path;

import java.util.List;
import java.util.Map;

class EditableImageViewProxy {
    List<Polygon> polygons;
    List<Path> paths;
    List<Line> lines;
    Map<Integer, Path> pathMap;

    public EditableImageViewProxy(List<Polygon> polygons, List<Path> paths, List<Line> lines, Map<Integer, Path> pathMap) {
        this.polygons = polygons;
        this.paths = paths;
        this.lines = lines;
        this.pathMap = pathMap;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<Polygon> polygons) {
        this.polygons = polygons;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public Map<Integer, Path> getPathMap() {
        return pathMap;
    }

    public void setPathMap(Map<Integer, Path> pathMap) {
        this.pathMap = pathMap;
    }
}
