package com.example.sergiobelda.photoeditor.editableimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import com.example.sergiobelda.photoeditor.editableimageview.figures.Circle;
import com.example.sergiobelda.photoeditor.editableimageview.figures.Line;
import com.example.sergiobelda.photoeditor.editableimageview.figures.Square;
import com.example.sergiobelda.photoeditor.editableimageview.paint.Path;

import java.util.*;

import static com.example.sergiobelda.photoeditor.editableimageview.EditorTool.PAINT;
import static com.example.sergiobelda.photoeditor.editableimageview.figures.Figure.*;
import static com.example.sergiobelda.photoeditor.editableimageview.EditorTool.FIGURE;

public class EditableImageView extends ImageFilterView {
    private float mScaleFactor = 1.f;
    GestureDetector gestureDetector;
    ScaleGestureDetector mScaleDetector;
    Random random = new Random();
    Paint paint = new Paint();
    List<Square> squares;
    List<Circle> circles;
    List<Path> paths;
    List<Line> lines;
    Map<Integer, Path> pathMap;

    float mLastTouchx, mLastTouchy;

    float contrast = 1;

    int currentColor = Color.BLACK;

    int figureMode = -1;
    int editMode = -1;

    MyContext myContext;

    public EditableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        squares = new ArrayList<>();
        circles = new ArrayList<>();
        paths = new ArrayList<>();
        lines = new ArrayList<>();
        pathMap = new HashMap<>();
        myContext = new MyContext(this);
        GestureListener gestureListener = new GestureListener();
        gestureDetector = new GestureDetector(getContext(), gestureListener);
        paint.setStrokeWidth(8);
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (editMode) {
            case PAINT :
                myContext.setStrategyTool(new StrategyPaint());
                myContext.onTouchEvent(event);
                break;
            case FIGURE :
                myContext.setStrategyTool(new StrategyFigure());
                myContext.onTouchEvent(event);
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        for (Square s : squares) {
            paint.setColor((int) s.getColor());
            canvas.drawRect((float) (s.getX() - s.getSide()), (float) (s.getY() - s.getSide()), (float) (s.getX() + s.getSide()), (float) (s.getY() + s.getSide()), paint);
        }
        for (Circle c : circles) {
            paint.setColor((int) c.getColor());
            canvas.drawCircle(c.getX(), c.getY(), c.getRadius(), paint);
        }
        for (Path p : paths) {
            paint.setColor(p.getColor());
            for (Line l : p.getLines()){
                canvas.drawLine(l.getX0(), l.getY0(), l.getXf(), l.getYf(), paint);
            }
        }
        for (Integer id : pathMap.keySet()) {
            Path path = pathMap.get(id);
            paint.setColor(path.getColor());
            for (Line l : path.getLines()){
                canvas.drawLine(l.getX0(), l.getY0(), l.getXf(), l.getYf(), paint);
            }
        }
        for (Line l : lines) {
            paint.setColor(l.getColor());
            canvas.drawLine(l.getX0(), l.getY0(), l.getXf(), l.getYf(), paint);
        }
        this.setContrast(contrast);
        canvas.restore();
    }

    public void setFigureMode(int figureMode) {
        this.figureMode = figureMode;
    }

    public void setEditMode(int editMode) {
        this.editMode = editMode;
    }

    public void setCurrentColor(int currentColor) {
        this.currentColor = currentColor;
    }

    /**
     * Listener of gesture actions
     * Double tap: figure mode determinate the figure will be drawn
     */
    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            distanceY = Math.max(-100, Math.min(100, distanceY));
            if (e2.getAction() == MotionEvent.ACTION_UP) {
                return false;
            }
            if (distanceY < 10 && distanceY > -10) {
                contrast = 1;
            } else {
                contrast = Math.max(0.2f, Math.min(1.8f, contrast + distanceY / 100));
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (editMode == FIGURE) {
                switch (figureMode) {
                    case SQUARE:
                        Square s = new Square(e.getX(), e.getY(), 100, currentColor);
                        squares.add(s);
                        break;
                    case CIRCLE:
                        Circle c = new Circle(e.getX(), e.getY(), 100, currentColor);
                        circles.add(c);
                        break;
                }
            }
            return true;
        }
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            invalidate();
            return true;
        }
    }

    public Square getTouchedSquare(float xTouch, float yTouch) {
        Square touched = null;
        for (Square s : squares) {
            double side = s.getSide();
            double halfside = s.getSide() / 2;
            float x = s.getX();
            float y = s.getY();
            if (((x - halfside) < xTouch && (x + halfside) > xTouch) &&
                    ((y + halfside) > yTouch && (y - halfside) < yTouch)) {
                touched = s;
            }
        }
        return touched;
    }

    public void addPath(int id) {
        pathMap.put(id, new Path(currentColor));
    }

    public void updateLines(int id, float x, float y){
        Path path = pathMap.get(id);
        ArrayList<Line> lines = path.getLines();
        if (lines.size() > 1) {
            lines.get(lines.size() - 1).setXf(x);
            lines.get(lines.size() - 1).setYf(y);
        }
        lines.add(new Line(x, y, x, y, Color.BLACK));
    }
}
