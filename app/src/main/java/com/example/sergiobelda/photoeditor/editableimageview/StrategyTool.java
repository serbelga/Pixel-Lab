package com.example.sergiobelda.photoeditor.editableimageview;

import android.view.MotionEvent;

import java.util.Random;

abstract class StrategyTool {
    EditableImageView imageView;
    Random random = new Random();

    void setImageView(EditableImageView imageView) {
        this.imageView = imageView;
    }

    void invalidate(){
        imageView.invalidate();
    }

    abstract void onTouchEvent(MotionEvent event);
}
