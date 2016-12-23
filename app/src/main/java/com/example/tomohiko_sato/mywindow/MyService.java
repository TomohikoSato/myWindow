package com.example.tomohiko_sato.mywindow;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayerView;

public class MyService extends Service {
    private final static String TAG = MyService.class.getSimpleName();

    private WindowManager windowManager;
    private DraggableView draggableView;
    private TrashView trashView;
    private YouTubePlayerView playerView;
    private WindowManager.LayoutParams wmParams;
    private IBinder binder = new MyServiceBinder();

    public MyService() {
    }



    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        draggableView = new DraggableView(this);
        trashView = new TrashView(this);
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


    public void addView(YouTubePlayerView playerView) {
        if (this.playerView == null) {
            this.playerView = playerView;
            draggableView.addPlayerView(playerView);
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
        windowManager.removeView(draggableView);
    }
}
