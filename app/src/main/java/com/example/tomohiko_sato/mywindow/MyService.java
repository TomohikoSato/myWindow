package com.example.tomohiko_sato.mywindow;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MyService extends Service {
    private final static String TAG = MyService.class.getSimpleName();

    private WindowManager windowManager;
    //    private FrameLayout overlapView;
    View inflateView;
    private WindowManager.LayoutParams wmParams;
    private IBinder binder = new MyServiceBinder();

    public MyService() {
    }

    View view;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        overlapView = new FrameLayout(this);
//        overlapView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        View inflateView = LayoutInflater.from(this).inflate(R.layout.trash, null);
//        overlapView.addView(inflateView);
        wmParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,       // アプリケーションのTOPに配置
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |  // フォーカスを当てない(下の画面の操作がd系なくなるため)
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,        // OverlapするViewを全画面表示
                PixelFormat.TRANSLUCENT);  // viewを透明にする
        wmParams.gravity = Gravity.BOTTOM;

        windowManager.addView(inflateView, wmParams);
    }

    public static void startService(Context context) {
        Intent intent = new Intent(context, MyService.class);
        context.startService(intent);
    }

    public static void bindService(Context context, ServiceConnection connection) {
        Intent intent = new Intent(context, MyService.class);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand Received start id " + startId + ": " + intent);
        Toast.makeText(this, "MyService#onStartCommand", Toast.LENGTH_SHORT).show();
        //明示的にサービスの起動、停止が決められる場合の返り値

        return START_STICKY;
    }


    public void addView(View inflateView) {
        if (this.inflateView == null) {
            this.inflateView = inflateView;
//            overlapView.addView(inflateView);
//            windowManager.updateViewLayout(overlapView, wmParams);
        }
    }

    public class MyServiceBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "MyService#onBind", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "MyService onBind");

        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        Toast.makeText(this, "MyService#onDestroy", Toast.LENGTH_SHORT).show();
        windowManager.removeView(inflateView);
    }
}
