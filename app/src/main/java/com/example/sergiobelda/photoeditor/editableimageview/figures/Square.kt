package com.example.sergiobelda.photoeditor.editableimageview.figures

open class Square(private var x: Float, private var y: Float, var side: Float, color: Int) : Polygon {
    private var color: Float = 0.toFloat()
    var rotation: Float = 0.toFloat()

    init {
        this.color = color.toFloat()
        this.rotation = 0f
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

    override fun getColor(): Float {
        return color
    }

    fun setColor(color: Float) {
        this.color = color
    }

    override fun getSize(): Float {
        return side.toFloat()
    }

    override fun setSize(size: Float) {
        this.side = size
    }
}