package com.example.sergiobelda.photoeditor.editableimageview;

import android.view.MotionEvent;
import com.example.sergiobelda.photoeditor.editableimageview.figures.Line;
import com.example.sergiobelda.photoeditor.editableimageview.paint.Path;

import java.util.ArrayList;

/**
 * Manages the Paint tool
 */
public class StrategyPaint extends StrategyTool  {

    @Override
    public void onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int id = event.getPointerId(pointerIndex);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
                imageView.addPath(id);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    int mId = event.getPointerId(i);
                    imageView.updateLines(mId, event.getX(i), event.getY(i));
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                Path p = new Path(imageView.pathMap.get(id).getLines(), imageView.pathMap.get(id).getColor(), imageView.pathMap.get(id).getStrokeWidth());
                imageView.paths.add(p);
                imageView.pathMap.get(id).setLines(new ArrayList<Line>());
                imageView.pathMap.get(id).setStrokeWidth(8);
                break;
        }
    }
}
