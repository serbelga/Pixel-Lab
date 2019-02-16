package com.example.sergiobelda.photoeditor.editableimageview

import android.view.MotionEvent

abstract class StrategyTool {
    abstract var editableImageView: EditableImageView

    fun setImageView(editableImageView: EditableImageView) {
        this.editableImageView = editableImageView;
    }

    fun invalidate() {
        editableImageView.invalidate()
    }

    abstract fun onTouchEvent(event: MotionEvent)
}