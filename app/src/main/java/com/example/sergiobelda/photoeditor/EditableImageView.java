package com.example.sergiobelda.photoeditor;

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

    //Tools
    public boolean squareTool;
    public boolean lineTool;

    public EditableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        squares = new ArrayList<>();
        squareTool = false;
        lineTool = false;
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
        switch (event.getAction()) {
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

    public void setSquareTool(boolean b){
        squareTool = b;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (squareTool) {
                Log.d("Tap: ", Float.toString(e.getX()));
                int color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                Square s = new Square(e.getX(), e.getY(), 100, color);
                squares.add(s);
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
