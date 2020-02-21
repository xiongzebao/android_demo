package com.example.android_demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Debug;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import androidx.annotation.Nullable;

import com.andrognito.patternlockview.PatternLockView;

import java.util.ArrayList;

public class MyView extends View {

    private Paint mDotPaint;
    private Paint mPathPaint;
    private Interpolator mFastOutSlowInInterpolator;
    private Interpolator mLinearOutSlowInInterpolator;
    private int normalColor = Color.BLACK;
    private int correctColor = Color.BLUE;
    private int wrongColor = Color.RED;

    private float scaleX;
    private float scaleY;



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

    private void initView(){
        initDotPaint();
        initPathPaint();
        initAnimator();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startSizeAnimation(10,500,3000,mLinearOutSlowInInterpolator,Dot.dots.get(2),new Runnable(){

                    @Override
                    public void run() {

                    }
                });
            }
        },2000);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("xiong","w:"+w+"h:"+h);
        initDot(w,h);

    }




    private void initDot(int w,int h){
        Dot.clearDots();
        for (int i=0;i<500;i++){
          int x = (int) (Math.random()*w);
          int y = (int) (Math.random()*h);
          Dot.addDot(x,y);
        }
    }

    private void initDotPaint(){
        mDotPaint = new Paint();
        mDotPaint.setAntiAlias(true);
        mDotPaint.setDither(true);
    }

    private void initPathPaint(){
        mPathPaint = new Paint();
        mPathPaint.setAntiAlias(true);
        mPathPaint.setDither(true);
        mPathPaint.setColor(normalColor);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeJoin(Paint.Join.ROUND);
        mPathPaint.setStrokeCap(Paint.Cap.ROUND);
        mPathPaint.setStrokeWidth(12);
    }


    private void initAnimator(){
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
                dot.size = (int)animation.getAnimatedValue();
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

        switch (event.getAction()) {
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
        }

        return true;
    }

    private void handleActionMove(MotionEvent e){

    }

    private void handleActionUp(MotionEvent e){

    }


    private void handleActionDown(MotionEvent e){
        setScaleX(++scaleX);
        setScaleY(++scaleY);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Log.e("xiong","MyView onLayout");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {



        Log.e("xiong","MyView getSuggestedMinimumWidth:"+getSuggestedMinimumWidth());
       // MyUtils.printMode(MeasureSpec.getMode(widthMeasureSpec));
     //   Log.e("xiong","MyView heightMeasureSpec");
     //   MyUtils.printMode(MeasureSpec.getMode(heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDots(canvas);


    }

    private void drawLine(Canvas canvas){
        Path path  = new Path();
        path.lineTo(200, 200);                      // lineTo
        path.setLastPoint(200,50);                 // setLastPoint
        //  path.lineTo(200,0);
        canvas.drawPath(path,mPathPaint);
    }



    private void drawDots(Canvas canvas){
        for (Dot dot :Dot.dots){
            drawDot(canvas,dot.x,dot.y,dot.size,normalColor);
        }
    }


    private static class Dot{

        public static   ArrayList<Dot> dots = new ArrayList<>();

        public static void clearDots(){
            dots.clear();
        }


        public static void addDot(int x,int y){
            Dot dot = new Dot();
            dot.x = x;
            dot.y = y;
            dots.add(dot);
        }

        public int x;
        public int y;
        public  int size=10;
        public int alpha=1;
        public String text;
    }


}
