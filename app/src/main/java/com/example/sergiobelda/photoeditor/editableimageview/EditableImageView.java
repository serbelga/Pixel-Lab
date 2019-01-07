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
import com.example.sergiobelda.photoeditor.editableimageview.figures.Polygon;
import com.example.sergiobelda.photoeditor.editableimageview.figures.Square;
import com.example.sergiobelda.photoeditor.editableimageview.paint.Path;

import java.util.*;

import static com.example.sergiobelda.photoeditor.editableimageview.EditorTool.PAINT;
import static com.example.sergiobelda.photoeditor.editableimageview.figures.Figure.*;
import static com.example.sergiobelda.photoeditor.editableimageview.EditorTool.FIGURE;

public class EditableImageView extends ImageFilterView {
    private float mScaleFactor = 1.f;
    GestureDetector gestureDetector;
    ScaleGestureDetector scaleDetector;
    Paint paint = new Paint();
    List<Polygon> polygons;
    List<Path> paths;
    List<Line> lines;
    Map<Integer, Path> pathMap;

    Stack<EditableImageViewProxy> stateStack;
    Stack<List<Polygon>> statePolygon;

    float contrast = 1;

    int currentColor = Color.WHITE;

    int figureMode = -1;
    int editMode = -1;

    MyContext myContext;
    private float currentStroke;
    private final float STROKE_WIDTH = 8;

    public EditableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paths = new ArrayList<>();
        lines = new ArrayList<>();
        polygons = new ArrayList<>();
        pathMap = new HashMap<>();
        stateStack = new Stack<>();
        statePolygon = new Stack<>();
        currentStroke = STROKE_WIDTH;
        myContext = new MyContext(this);
        GestureListener gestureListener = new GestureListener();
        gestureDetector = new GestureDetector(getContext(), gestureListener);
        paint.setStrokeWidth(STROKE_WIDTH);
        scaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        scaleDetector.onTouchEvent(event);
        switch (editMode) {
            case PAINT :
                myContext.setStrategyTool(new StrategyPaint());
                myContext.onTouchEvent(event);
                break;
            case FIGURE :
                if (figureMode == LINE) {
                    myContext.setStrategyTool(new StrategyLine());
                    myContext.onTouchEvent(event);
                }
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        for (Polygon p : polygons) {
            paint.setColor((int) p.getColor());
            if (p instanceof Circle) {
                canvas.drawCircle(p.getX(), p.getY(), p.getSize(), paint);
            } else if (p instanceof Square) {
                canvas.drawRect(p.getX() - p.getSize(), p.getY() - p.getSize(), p.getX() + p.getSize(), p.getY() + p.getSize(), paint);
            }
        }
        for (Path p : paths) {
            paint.setColor(p.getColor());
            paint.setStrokeWidth(p.getStrokeWidth());
            for (Line l : p.getLines()){
                canvas.drawLine(l.getX0(), l.getY0(), l.getXf(), l.getYf(), paint);
            }
            paint.setStrokeWidth(STROKE_WIDTH);
        }
        for (Integer id : pathMap.keySet()) {
            Path path = pathMap.get(id);
            paint.setColor(path.getColor());
            paint.setStrokeWidth(path.getStrokeWidth());
            for (Line l : path.getLines()){
                canvas.drawLine(l.getX0(), l.getY0(), l.getXf(), l.getYf(), paint);
            }
            paint.setStrokeWidth(STROKE_WIDTH);
        }
        paint.setStrokeWidth(STROKE_WIDTH);
        for (Line l : lines) {
            paint.setColor(l.getColor());
            canvas.drawLine(l.getX0(), l.getY0(), l.getXf(), l.getYf(), paint);
        }

        //this.setContrast(contrast);
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

    public void setCurrentStroke(float currentStroke) {
        this.currentStroke = currentStroke;
    }

    /**
     * Listener of gesture actions
     * Double tap: figure mode determinate the figure will be drawn
     */
    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        Polygon p;
        @Override
        public boolean onDown(MotionEvent e) {
            p = getTouchedPolygon(e.getX(), e.getY());
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (p != null && figureMode != LINE) {
                p.setX(e2.getX());
                p.setY(e2.getY());
                //newState();
            }
            /*
            distanceY = Math.max(-100, Math.min(100, distanceY));
            Log.d("Distancia", String.valueOf(distanceY));
            Log.d("Distancia", String.valueOf(distanceX));
            if (e2.getAction() == MotionEvent.ACTION_UP) {
                return false;
            }
            if (distanceY < 10 && distanceY > -10) {
                contrast = 1;
            } else {
                contrast = Math.max(0.2f, Math.min(1.8f, contrast + distanceY / 100));
            }*/
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (editMode == FIGURE) {
                switch (figureMode) {
                    case SQUARE:
                        Polygon s = new Square(e.getX(), e.getY(), 100, currentColor);
                        polygons.add(s);
                        //newState();
                        break;
                    case CIRCLE:
                        Polygon c = new Circle(e.getX(), e.getY(), 100, currentColor);
                        polygons.add(c);
                        //newState();
                        break;
                }
            }
            return true;
        }
    }

    private void newState() {
        List<Polygon> polygons;
        polygons = this.polygons;
        statePolygon.push(polygons);
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        public Square s;
        public Circle c;
        public Polygon p;
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            p = getTouchedPolygon(detector.getFocusX(), detector.getFocusY());
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(85f, Math.min(250f, mScaleFactor));
            if (p != null) {
                p.setSize(mScaleFactor);
            }
            //newState();
            invalidate();
            return true;
        }
    }

    public Polygon getTouchedPolygon(float xTouch, float yTouch) {
        Polygon touched = null;
        for (Polygon p : polygons) {
            double size = p.getSize();
            float x = p.getX();
            float y = p.getY();
            if (((x - size) < xTouch && (x + size) > xTouch) &&
                    ((y + size) > yTouch && (y - size) < yTouch)) {
                touched = p;
            }
        }
        return touched;
    }

    public void addPath(int id) {
        pathMap.put(id, new Path(currentColor, currentStroke));
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
