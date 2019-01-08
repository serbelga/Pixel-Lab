package com.example.sergiobelda.photoeditor.editableimageview;

import android.view.MotionEvent;

public class MyContext {
    private EditableImageView editableImageView;
    private StrategyTool strategyTool;

    public MyContext(EditableImageView editableImageView) {
        this.editableImageView = editableImageView;
    }

    public void setStrategyTool(StrategyTool strategyTool) {
        strategyTool.setImageView(editableImageView);
        this.strategyTool = strategyTool;
    }

    public void onTouchEvent(MotionEvent event) {
        strategyTool.onTouchEvent(event);
    }

    public EditableImageView getEditableImageView() {
        return editableImageView;
    }

    public void setEditableImageView(EditableImageView editableImageView) {
        this.editableImageView = editableImageView;
    }
}
