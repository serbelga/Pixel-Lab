package com.example.sergiobelda.photoeditor.editableimageview.figures

import android.graphics.Bitmap
import android.graphics.Color

class CropSquare(x: Float, y: Float, side: Float) : Square(x, y, side, Color.BLACK) {
    lateinit var bitmap: Bitmap
}
