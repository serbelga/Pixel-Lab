package com.example.sergiobelda.photoeditor.editableimageview;

import android.graphics.Color;
import android.view.MotionEvent;
import com.example.sergiobelda.photoeditor.editableimageview.figures.Line;
import com.example.sergiobelda.photoeditor.editableimageview.paint.Path;

import java.util.ArrayList;

public class StrategyPaint extends StrategyTool  {

    @Override
    public void onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int id = event.getPointerId(pointerIndex);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
                addPath(id);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    int mId = event.getPointerId(i);
                    updateLines(mId, event.getX(i), event.getY(i));
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                Path p = new Path(imageView.paths.get(id).getLines(), imageView.paths.get(id).getColor());
                imageView.pathList.add(p);
                imageView.paths.get(id).setLines(new ArrayList<Line>());
                break;
        }
    }

    private void addPath(int id) {
        imageView.paths.put(id, new Path(Color.rgb(random.nextInt(255), random.nextInt(255),random.nextInt(255))));
    }

    public void updateLines(int id, float x, float y){
        Path path = imageView.paths.get(id);
        ArrayList<Line> lines = path.getLines();
        if (lines.size() > 1) {
            lines.get(lines.size() - 1).setXf(x);
            lines.get(lines.size() - 1).setYf(y);
        }
        lines.add(new Line(x, y, x, y, Color.BLACK));
    }
}
