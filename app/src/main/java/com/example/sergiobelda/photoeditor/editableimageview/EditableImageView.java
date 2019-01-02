package com.example.sergiobelda.photoeditor.editableimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EditableImageView extends androidx.appcompat.widget.AppCompatImageView {
    private float mScaleFactor = 1.f;
    GestureDetector gestureDetector;
    ScaleGestureDetector mScaleDetector;
    Random random = new Random();
    Paint paint = new Paint();
    List<Square> squares;
    List<Circle> circles;

    //Tools
    private final int SQUARE_MODE = 0;
    private final int CIRCLE_MODE = 1;
    private final int LINE_MODE = 2;

    private final int PAINT_MODE = 0;
    private final int FIGURE_MODE = 1;
    private final int STICKER_MODE = 2;

    int figureMode = -1;
    int editMode = -1;

    //Paint


    public EditableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        squares = new ArrayList<>();
        circles = new ArrayList<>();
        GestureListener gestureListener = new GestureListener();
        gestureDetector = new GestureDetector(getContext(), gestureListener);
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        mScaleDetector.onTouchEvent(event);
        float xTouch, yTouch;
        Square s = null;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
                xTouch = event.getX();
                yTouch = event.getY();
                s = getSquare(xTouch, yTouch);
                if (s != null) {
                    s.setX(xTouch);
                    s.setY(yTouch);
                    Log.d("Square: ", Float.toString(s.getX()) + Float.toString(s.getY()));
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                xTouch = event.getX();
                yTouch = event.getY();
                s = getSquare(xTouch, yTouch);
                if (s != null) {
                    s.setX(xTouch);
                    s.setY(yTouch);
                    Log.d("Square: ", Float.toString(s.getX()) + Float.toString(s.getY()));
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                break;
        }
        invalidate();
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
        canvas.restore();
    }

    private Square getSquare(float xTouch, float yTouch) {
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

    public void setFigureMode(int figureMode) {
        this.figureMode = figureMode;
    }

    public void setEditMode(int editMode) {
        this.editMode = editMode;
    }

    /**
     * Listener of gesture actions
     * Double tap: figure mode determinate is the figure will be drawn
     */
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            int color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            if (editMode == FIGURE_MODE) {
                switch (figureMode) {
                    case SQUARE_MODE:
                        Square s = new Square(e.getX(), e.getY(), 100, color);
                        squares.add(s);
                        break;
                    case CIRCLE_MODE:
                        Circle c = new Circle(e.getX(), e.getY(), 50, color);
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
}
