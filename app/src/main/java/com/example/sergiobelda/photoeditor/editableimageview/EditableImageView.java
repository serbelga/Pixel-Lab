package com.example.sergiobelda.photoeditor.editableimageview;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import com.example.sergiobelda.photoeditor.editableimageview.figures.*;
import com.example.sergiobelda.photoeditor.editableimageview.paint.Path;

import java.util.*;

import static com.example.sergiobelda.photoeditor.editableimageview.EditorTool.PAINT;
import static com.example.sergiobelda.photoeditor.editableimageview.EditorTool.STICKER;
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

    List<Square> squares;

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
        squares = new ArrayList<>();
        pathMap = new HashMap<>();
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
            case STICKER :
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
            if (p instanceof Circle) {
                paint.setColor((int) p.getColor());
                canvas.drawCircle(p.getX(), p.getY(), p.getSize(), paint);
            } else if (p instanceof CropSquare) {
                CropSquare c = (CropSquare) p;
                if(c.getBitmap() != null) {
                    canvas.drawBitmap(c.getBitmap(), c.getX() - c.getSize(), c.getY() - c.getSize(), paint);
                } else {
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor((int) p.getColor());
                    canvas.drawRect(p.getX() - p.getSize(), p.getY() - p.getSize(), p.getX() + p.getSize(), p.getY() + p.getSize(), paint);
                }
            } else if (p instanceof Square) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor((int) p.getColor());
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
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            p = getTouchedPolygon(e.getX(), e.getY());
            if (p != null) {
                Drawable d = myContext.getEditableImageView().getDrawable();
                Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, myContext.getEditableImageView().getWidth(), myContext.getEditableImageView().getHeight(), true);
                Bitmap cropBitmap = Bitmap.createBitmap(bitmap,(int)(p.getX() - p.getSize()), (int)(p.getY() - p.getSize()), (int) p.getSize()*2, (int) p.getSize()*2);
                if (p instanceof CropSquare) ((CropSquare) p).setBitmap(cropBitmap);
            }

        }

        @Override
        public boolean onDown(MotionEvent e) {
            p = getTouchedPolygon(e.getX(), e.getY());
            if (e.getPointerCount() == 2) {
                double delta_x = (e.getX(0) - e.getX(1));
                double delta_y = (e.getY(0) - e.getY(1));
                double radians = Math.atan2(delta_y, delta_x);
                Log.d("Rotation", delta_x+" ## "+delta_y+" ## "+radians+" ## "
                        +Math.toDegrees(radians));
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (!scaleDetector.isInProgress()) {
                if (p != null && figureMode != LINE) {
                    p.setX(e2.getX());
                    p.setY(e2.getY());
                }
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (editMode == FIGURE) {
                switch (figureMode) {
                    case SQUARE:
                        Polygon s = new Square(e.getX(), e.getY(), 100, currentColor);
                        polygons.add(s);
                        break;
                    case CIRCLE:
                        Polygon c = new Circle(e.getX(), e.getY(), 100, currentColor);
                        polygons.add(c);
                        break;
                }
            } else if (editMode == STICKER) {
                CropSquare s = new CropSquare(e.getX(), e.getY(), 100);
                polygons.add(s);
            }
            return true;
        }
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        public Polygon p;
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            p = getTouchedPolygon(detector.getFocusX(), detector.getFocusY());
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(85f, Math.min(250f, mScaleFactor));
            if (p != null) {
                p.setSize(mScaleFactor);
            }
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
