package com.example.sergiobelda.photoeditor.editableimageview;

import android.view.MotionEvent;

abstract class StrategyTool {
    EditableImageView imageView;

    void setImageView(EditableImageView imageView) {
        this.imageView = imageView;
    }

    void invalidate() {
        imageView.invalidate();
    }

    abstract void onTouchEvent(MotionEvent event);
}
