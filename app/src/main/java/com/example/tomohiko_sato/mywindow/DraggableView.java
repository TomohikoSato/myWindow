package com.example.tomohiko_sato.mywindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.youtube.player.YouTubePlayerView;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

/**
 * YoutubePlayerViewを内包する、ドラッグできるビュー
 */
public class DraggableView extends FrameLayout {
    private final static String TAG = DraggableView.class.getSimpleName();

    private final WindowManager wm;
    private final WindowManager.LayoutParams wmParams;
    private final DisplayMetrics dm;

    public DraggableView(Context context) {
        super(context);
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,       // アプリケーションのTOPに配置
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |  // フォーカスを当てない(下の画面の操作ができなくなるため)
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,        // OverlapするViewを全画面表示
                PixelFormat.TRANSLUCENT);  // viewを透明にする
        wm.addView(this, wmParams);
        dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
    }

    public void addPlayerView(YouTubePlayerView playerView) {
        addView(playerView, (int) (200 * dm.density), (int) (110 * dm.density));
        wm.updateViewLayout(this, wmParams);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return true;
    }

    float pressedX, pressedY = 0f;
    int pressedParamsX, pressedParamsY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case ACTION_DOWN:
                pressedX = event.getRawX();
                pressedY = event.getRawY();

                pressedParamsX = wmParams.x;
                pressedParamsY = wmParams.y;

                Log.d("ACTION_DOWN", Float.toString(pressedX));
                Log.d("ACTION_DOWN", Float.toString(pressedY));
                break;
            case ACTION_MOVE:
                if (pressedX == 0f && pressedY == 0f) {
                    break;
                }
                wmParams.x = (int) (pressedParamsX + (event.getRawX() - pressedX));
                wmParams.y = (int) (pressedParamsY + (event.getRawY() - pressedY));
                wm.updateViewLayout(this, wmParams);

                break;
            case ACTION_UP:
                pressedX = 0f;
                pressedY = 0f;
                break;
            default:
                return false;
        }

        return true;
    }
}
