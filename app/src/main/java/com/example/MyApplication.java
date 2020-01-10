package com.example;

import android.app.Application;

import com.example.bean.DaoManager;

public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        initGreenDao();
    }

    private void initGreenDao()
    {
        DaoManager mManager = DaoManager.getInstance();
        mManager.init(this);
    }
}