package com.example.sergiobelda.photoeditor.editableimageview.figures;

import android.graphics.Bitmap;
import android.graphics.Color;

public class CropSquare extends Square {
    Bitmap bitmap;
    public CropSquare(float x, float y, double side) {
        super(x, y, side, Color.BLACK);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
