package com.example.tomohiko_sato.mywindow;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;


public class DraggableView extends FrameLayout {
    private final static String TAG = DraggableView.class.getSimpleName();

    public DraggableView(Context context) {
        super(context);
    }

    public DraggableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DraggableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DraggableView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return true;
    }

    private OnMoveListener listener;

    public interface OnMoveListener {
        void onMove(float x, float y);
    }

    public void setOnMoveListener(OnMoveListener listener) {
        this.listener = listener;
    }

    float pressedX, pressedY = 0f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case ACTION_DOWN:
                pressedX = getX() - event.getRawX();
                pressedY = getY() - event.getRawY();
                Log.d("ACTION_DOWN", Float.toString(pressedX));
                Log.d("ACTION_DOWN", Float.toString(pressedY));
                break;
            case ACTION_MOVE:
                if (pressedX == 0f && pressedY == 0f) {
                    break;
                }
                float x = (event.getRawX() + pressedX) / 2; // FIXME: なぜ2で割るのか不明
                float y = (event.getRawY() + pressedY) / 2;
                Log.d("ACTION_MOVE", Float.toString(x));
                Log.d("ACTION_MOVE", Float.toString(y));

                setX(x);
                setY(y);
                listener.onMove(x, y);
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
