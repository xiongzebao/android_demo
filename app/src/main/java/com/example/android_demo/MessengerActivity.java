package com.example.android_demo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.CacheDiskUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.TimeUtils;

public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = "MessengerActivity";
    private Messenger mService;
    TextView tv_time;


    private  class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 2:
                    Log.e(TAG, "receive message from service:" + message.getData().getString("msg"));
                    Log.e(TAG, "mClientMessenger Thread:" +Thread.currentThread().getName());
                 //  String now =  CacheDiskUtils.getInstance().getString("time_now");
                    tv_time.setText(TimeUtils.getNowString());

                    break;
                default:
                    super.handleMessage(message);
                    break;
            }
        }
    }

    /**
     * 客户端Messenger对象
     */
    private Messenger mClientMessenger = new Messenger(new MessengerHandler());


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(TAG, "ServiceConnection-->" + System.currentTimeMillis());
            Log.e(TAG,  "MessengerActivity process id:"+android.os.Process.myPid());
            Log.e(TAG,  "MessengerActivity thread id:"+Thread.currentThread().getId());
            mService = new Messenger(iBinder);
            Message message = Message.obtain(null, 1);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "hello service,this is client");
            message.setData(bundle);
            //将客户端的Messenger对象传递给服务端
            message.replyTo = mClientMessenger;
            try {

                mService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TAG, "onServiceDisconnected-->binder died");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        tv_time = findViewById(R.id.time);
        //绑定服务
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);



    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        //解绑服务
        unbindService(mConnection);
        super.onDestroy();
    }


    public static class MessengerService extends Service {
        private static final String TAG = "MessagerService";

        /**
         * 处理来自客户端的消息，并用于构建Messenger
         */
        private class MessengerHandler extends Handler {


            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:

                        Log.e(TAG, "receive message from client:" + message.getData().getString("msg"));
                        Log.e(TAG,  "MessengerService process id:"+android.os.Process.myPid());
                        Log.e(TAG,  "MessengerService thread id:"+Thread.currentThread().getId());
                        //获取客户端传递过来的Messenger，通过这个Messenger回传消息给客户端
                        final Messenger client = message.replyTo;
                        //当然，回传消息还是要通过message
                        final Message msg = Message.obtain(null, 2);
                        Bundle bundle = new Bundle();
                        bundle.putString("msg", "hello client, I have received your message!");
                        msg.setData(bundle);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i=0;i<200000;i++){

                                    try {
                                        Thread.sleep(1000);
                                        try {
                                            client.send(msg);

                                         String now =    TimeUtils.getNowString();

                                         CacheDiskUtils.getInstance().put("time_now",now);

                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                        break;
                    default:
                        super.handleMessage(message);
                        break;
                }
            }
        }


        /**
         * 构建Messenger对象
         */
        private final Messenger mMessenger = new Messenger(new MessengerHandler());

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            //将Messenger对象的Binder返回给客户端
            return mMessenger.getBinder();
        }
    }


}
