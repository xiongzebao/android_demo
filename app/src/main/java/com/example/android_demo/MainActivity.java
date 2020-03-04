package com.example.android_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.blankj.utilcode.util.CacheDiskUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.utils.SignBeanDaoUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MapContainer mMapContainer;
    ArrayList<Marker> mMarkers;
    PatternLockView mPatternLockView;
    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            Log.d(getClass().getName(), "Pattern drawing started");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d(getClass().getName(), "Pattern progress: " +
                    PatternLockUtils.patternToString(mPatternLockView, progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            String lockString = PatternLockUtils.patternToString(mPatternLockView, pattern);
            Log.d(getClass().getName(), "Pattern complete: " +lockString);
            if(!TextUtils.isEmpty(lockString)&&lockString.equals("012457")){
                ToastUtils.showShort("解锁成功");
                startActivity(new Intent(MainActivity.this,SignActivity.class));
                finish();
            }else{
                ToastUtils.showShort("解锁失败");
                mPatternLockView.clearPattern();
            }

        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setNavigationBarVisibility(false);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);

        initMapView();


    }

    public void onClick(View view) {

    }



    private void initMapView(){
        mMapContainer = findViewById(R.id.mc_map);
        //这里用女神赵丽颖的照片作地图~~
        mMapContainer.getMapView().setImageResource(R.drawable.vector_test);
        mMarkers = new ArrayList<>();
        mMarkers.add(new Marker(0.1f, 0.2f, R.drawable.ic_launcher_round));
        mMarkers.add(new Marker(0.3f, 0.7f, R.drawable.ic_launcher_round));
        mMarkers.add(new Marker(0.3f, 0.3f, R.drawable.ic_launcher_round));
        mMarkers.add(new Marker(0.2f, 0.4f, R.drawable.ic_launcher_round));
        mMarkers.add(new Marker(0.8f, 0.4f, R.drawable.ic_launcher_round));
        mMarkers.add(new Marker(0.5f, 0.6f, R.drawable.ic_launcher_round));
        mMarkers.add(new Marker(0.8f, 0.8f, R.drawable.ic_launcher_round));
        mMapContainer.setMarkers(mMarkers);
        mMapContainer.setOnMarkerClickListner(new MapContainer.OnMarkerClickListner() {
            @Override
            public void onClick(View view, int position) {

            }
        });
    }
    /**
     * 设置导航栏显示状态
     *
     * @param visible
     */
    private void setNavigationBarVisibility(boolean visible) {
        int flag = 0;
        if (!visible) {
            flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        getWindow().getDecorView().setSystemUiVisibility(flag);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
}
