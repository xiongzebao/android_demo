package com.example.android_demo;

import android.content.Context;
import android.util.Log;
import android.view.View;

public class MyUtils {

    public static void printMode(int mode){
        switch (mode){
            case View.MeasureSpec.AT_MOST :
                Log.e("xiong","AT_MOST");
                break;
            case View.MeasureSpec.EXACTLY :
                Log.e("xiong","EXACTLY");
                break;
            case View.MeasureSpec.UNSPECIFIED :
                Log.e("xiong","UNSPECIFIED");
                break;
        }
    }
    // dp转px
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
