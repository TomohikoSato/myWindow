package com.example.tomohiko_sato.mywindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

public class TrashView extends ImageView {
    private final WindowManager wm;
    private final WindowManager.LayoutParams wmParams;

    public TrashView(Context context) {
        super(context);
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,       // アプリケーションのTOPに配置
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |  // フォーカスを当てない(下の画面の操作ができなくなるため)
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,        // OverlapするViewを全画面表示
                PixelFormat.TRANSLUCENT);  // viewを透明にする
        wmParams.gravity = Gravity.BOTTOM;
        setImageResource(R.drawable.trash);
        wm.addView(this, wmParams);
    }

    public void show () {

    }

    public void hide() {

    }
}
