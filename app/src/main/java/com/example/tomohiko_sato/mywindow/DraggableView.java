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

    float dx, dy = 0f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case ACTION_DOWN:
                dx = getX() - event.getRawX();
                dy = getY() - event.getRawY();
                Log.d("ACTION_DOWN", Float.toString(dx));
                Log.d("ACTION_DOWN", Float.toString(dy));
                break;
            case ACTION_MOVE:
                if (dx == 0f && dy == 0f) {
                    break;
                }
                float x = event.getRawX() + dx;
                float y = event.getRawY() + dy;
                Log.d("ACTION_MOVE", Float.toString(x));
                Log.d("ACTION_MOVE", Float.toString(y));
                setX(x);
                setY(y);
                listener.onMove(x, y);
                break;
            case ACTION_UP:
                dx = 0f;
                dy = 0f;
            default:
                return false;
        }

        return true;
    }
}
