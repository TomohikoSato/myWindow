package com.example.tomohiko_sato.mywindow;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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
        draggableView.setOnTouchListener(new View.OnTouchListener() {
            Rect draggableRect = new Rect();
            Rect trashRect = new Rect();

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
/*
                    draggableView.getGlobalVisibleRect(draggableRect);
                    trashView.getGlobalVisibleRect(trashRect);
*/
                    draggableRect = getRectInScreen(draggableView);
                    trashRect = getRectInScreen(trashView);

                    if (Rect.intersects(draggableRect, trashRect)) {
                        windowManager.removeView(draggableView);
                    }
                }
                return false;
            }

            private Rect getRectInScreen(View view) {
                final int[] locationOnScreen = new int[2];
                view.getLocationOnScreen(locationOnScreen);
                Log.d(TAG, "location on screen : " + locationOnScreen[0] + ", " + locationOnScreen[1]);
                int left = locationOnScreen[0];
                int top = locationOnScreen[1];
                Rect rect = new Rect(left, top, left + view.getWidth(), top + view.getHeight());
                Log.d(TAG, "rect: " + rect);
                return rect;
            }

            private void logView(View view) {
                Log.d(TAG, "left: " + view.getLeft() + " right: " + view.getRight() + " top: " + view.getTop() + " bottom: " + view.getBottom() + " paddingleft: " + view.getPaddingLeft() + " paddingTop: " + view.getPaddingTop() + " paddingRight: " + view.getPaddingRight() + " paddingBottom: " + view.getPaddingBottom());
                Log.d(TAG, "width: " + view.getWidth() + " height: " + view.getHeight() + " x: " + view.getX() + " y: " + view.getY());
                Rect drawingRect = new Rect();
                view.getDrawingRect(drawingRect);
                Log.d(TAG, "getDrawingRect: " + drawingRect);


                final int[] locationOnScreen = new int[2];
                view.getLocationOnScreen(locationOnScreen);
                Log.d(TAG, "location on screen : " + locationOnScreen[0] + ", " + locationOnScreen[1]);

                final int[] locationOnWindow = new int[2];
                view.getLocationInWindow(locationOnWindow);
                Log.d(TAG, "location on window : " + locationOnWindow[0] + ", " + locationOnWindow[1]);

                Rect rectInGlobal = new Rect();
                view.getGlobalVisibleRect(rectInGlobal);
                Log.d(TAG, "globalVisibleRect=" + rectInGlobal);
            }
        });

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
