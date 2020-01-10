package com.example.android_demo;

import android.text.format.DateUtils;
import android.util.Log;


import com.blankj.utilcode.util.TimeUtils;

import org.junit.Test;



import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        //Log.e("test", TimeUtils.getNowDate().toString());
        System.out.println(TimeUtils.getNowString().split(" ")[0]);
    }
}