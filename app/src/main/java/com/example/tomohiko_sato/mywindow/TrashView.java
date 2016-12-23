package com.example.tomohiko_sato.mywindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

public class TrashView extends ImageView {
    private final WindowManager wm;
    private final WindowManager.LayoutParams wmParams;
    private final DisplayMetrics metrics;

    public TrashView(Context context) {
        super(context);
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        wmParams = new WindowManager.LayoutParams(
                96,
                96,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,       // アプリケーションのTOPに配置
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |  // フォーカスを当てない(下の画面の操作ができなくなるため)
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,        // OverlapするViewを全画面表示
                PixelFormat.TRANSLUCENT);  // viewを透明にする
        wmParams.gravity = Gravity.BOTTOM;

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setImageResource(R.drawable.trash);
        wm.addView(this, wmParams);
    }

    public void show() {

    }

    public void hide() {

    }

    public boolean contains(float x, float y) {
        return true;
    }
}
