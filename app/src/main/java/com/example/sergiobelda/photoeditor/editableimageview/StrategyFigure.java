package com.example.sergiobelda.photoeditor.editableimageview;

import android.view.MotionEvent;


public class StrategyFigure extends StrategyTool {

    @Override
    public void onTouchEvent(MotionEvent event) {
        imageView.gestureDetector.onTouchEvent(event);
        imageView.invalidate();
    }
}