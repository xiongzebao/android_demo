package com.example.android_demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyView extends View implements ScaleGestureDetector.OnScaleGestureListener {
    private static final int  SCALE = 1;

    private Paint mDotPaint;
    private Paint mPathPaint;
    private Interpolator mFastOutSlowInInterpolator;
    private Interpolator mLinearOutSlowInInterpolator;
    private int normalColor = Color.BLACK;
    private int correctColor = Color.BLUE;
    private int wrongColor = Color.RED;

    private float scaleX;
    private float scaleY;

    private float scaleTemp = 1;

    private ScaleGestureDetector scaleGestureDetector;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SCALE:
                    setScaleX((Float) msg.obj);
                    setScaleY((Float) msg.obj);
                    break;
            }

        }
    };


    public MyView(Context context) {
        super(context);
        initView();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        initDotPaint();
        initPathPaint();
        initAnimator();

        scaleGestureDetector = new ScaleGestureDetector(getContext(), this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
           /*     startSizeAnimation(10, 500, 3000, mLinearOutSlowInInterpolator, Dot.dots.get(2), new Runnable() {

                    @Override
                    public void run() {

                    }
                });*/

                //setScaleX(15.5f);
               // setScaleY(15.5f);
            }
        }, 2000);



    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("xiong", "w:" + w + "h:" + h);
        initDot(w, h);

    }


    private void initDot(int w, int h) {
        Dot.clearDots();
        for (int i = 0; i < 500; i++) {
            int x = (int) (Math.random() * w);
            int y = (int) (Math.random() * h);
            Dot.addDot(x, y);
        }
    }

    private void initDotPaint() {
        mDotPaint = new Paint();
        mDotPaint.setAntiAlias(true);
        mDotPaint.setDither(true);
    }

    private void initPathPaint() {
        mPathPaint = new Paint();
        mPathPaint.setAntiAlias(true);
        mPathPaint.setDither(true);
        mPathPaint.setColor(normalColor);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeJoin(Paint.Join.ROUND);
        mPathPaint.setStrokeCap(Paint.Cap.ROUND);
        mPathPaint.setStrokeWidth(12);
    }


    private void initAnimator() {
        mLinearOutSlowInInterpolator = AnimationUtils.loadInterpolator(
                getContext(), android.R.interpolator.linear_out_slow_in);
    }


    private void drawDot(Canvas canvas, float centerX, float centerY, float size, int color) {
        mDotPaint.setColor(color);

        canvas.drawCircle(centerX, centerY, size / 2, mDotPaint);

        // canvas.drawText();
    }


    private void startSizeAnimation(int start, int end, long duration,
                                    Interpolator interpolator, final Dot dot,
                                    final Runnable endRunnable) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dot.size = (int) animation.getAnimatedValue();
                invalidate();
            }

        });
        if (endRunnable != null) {
            valueAnimator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (endRunnable != null) {
                        endRunnable.run();
                    }
                }
            });
        }
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

   /*     switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleActionDown(event);
                return true;
            case MotionEvent.ACTION_UP:
                handleActionUp(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                handleActionMove(event);
                return true;
            case MotionEvent.ACTION_CANCEL:

                return true;
        }*/

        return scaleGestureDetector.onTouchEvent(event);
    }

    private void handleActionMove(MotionEvent e) {

    }

    private void handleActionUp(MotionEvent e) {

    }


    private void handleActionDown(MotionEvent e) {

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDots(canvas);


    }

    private void drawLine(Canvas canvas) {
        Path path = new Path();
        path.lineTo(200, 200);                      // lineTo
        path.setLastPoint(200, 50);                 // setLastPoint
        //  path.lineTo(200,0);
        canvas.drawPath(path, mPathPaint);
    }


    private void drawDots(Canvas canvas) {
        for (Dot dot : Dot.dots) {
            drawDot(canvas, dot.x, dot.y, dot.size, normalColor);
        }
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {

        float x = scaleGestureDetector.getFocusX();
        float y = scaleGestureDetector.getFocusY();

     /*   Log.e("xiong", "focusX:" + x);
        Log.e("xiong", "focusY:" + y);*/
        Log.e("xiong", "ScaleFactor:" + scaleGestureDetector.getScaleFactor());
        float factor =  scaleGestureDetector.getScaleFactor();

        factor = scaleTemp *factor;
        scaleTemp= factor;

       // setScaleY(factor);
       // setScaleX(factor);
       // scaleTemp=factor;


        Message message = Message.obtain();
        message.what = SCALE;
        message.obj = scaleGestureDetector.getCurrentSpan()/60;
        handler.sendMessage(message);

        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        Log.e("xiong", "onScaleBegin");
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        Log.e("xiong", "onScaleEnd");
       // scaleTemp = scaleGestureDetector.getScaleFactor();
    }


    private static class Dot {
        public static ArrayList<Dot> dots = new ArrayList<>();

        public static void clearDots() {
            dots.clear();
        }

        public static void addDot(int x, int y) {
            Dot dot = new Dot();
            dot.x = x;
            dot.y = y;
            dots.add(dot);
        }

        public int x;
        public int y;
        public int size = 10;
        public int alpha = 1;
        public String text;
    }


}
