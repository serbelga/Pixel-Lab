package com.example.sergiobelda.photoeditor.editableimageview.figures

class Circle(private var x: Float, private var y: Float, var radius: Float, private var color: Float) : Polygon {

    override fun getColor(): Float {
        return color
    }

    override fun getSize(): Float {
        return this.radius
    }

    override fun setSize(size: Float) {
        this.radius = size
    }

    fun setColor(color: Float) {
        this.color = color
    }

    override fun getX(): Float {
        return x
    }

    override fun setX(x: Float) {
        this.x = x
    }

    override fun getY(): Float {
        return y
    }

    override fun setY(y: Float) {
        this.y = y
    }
}