package com.example.sergiobelda.photoeditor.editableimageview.paint

import com.example.sergiobelda.photoeditor.editableimageview.figures.Line
import java.util.ArrayList

class Path {
    var lines: ArrayList<Line>? = null
    var color = -1
    var strokeWidth: Float = 0.toFloat()

    constructor() {
        this.lines = ArrayList()
    }

    constructor(color: Int, strokeWidth: Float) {
        this.lines = ArrayList()
        this.color = color
        this.strokeWidth = strokeWidth
    }

    constructor(lines: ArrayList<Line>, color: Int, strokeWidth: Float) {
        this.lines = lines
        this.color = color
        this.strokeWidth = strokeWidth
    }
}