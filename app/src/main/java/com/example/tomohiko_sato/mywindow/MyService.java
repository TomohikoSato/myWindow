package com.example.tomohiko_sato.mywindow;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MyService extends Service {
	private final static String TAG = MyService.class.getSimpleName();

	private WindowManager windowManager;
	private FrameLayout overlapView;
	private WindowManager.LayoutParams overlapViewParams;
	private IBinder binder = new MyServiceBinder();

	public MyService() {
	}

	public static void startService(Context context) {
		Intent intent = new Intent(context, MyService.class);
		context.startService(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "onStartCommand Received start id " + startId + ": " + intent);
		Toast.makeText(this, "MyService#onStartCommand", Toast.LENGTH_SHORT).show();
		//明示的にサービスの起動、停止が決められる場合の返り値
		return START_STICKY;
	}

	public void addView(View inflateView) {
		overlapView.addView(inflateView);
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

		windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		overlapView = new FrameLayout(getApplicationContext());
		overlapViewParams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,       // アプリケーションのTOPに配置
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |  // フォーカスを当てない(下の画面の操作がd系なくなるため)
						WindowManager.LayoutParams.FLAG_FULLSCREEN |        // OverlapするViewを全画面表示
						WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, // モーダル以外のタッチを背後のウィンドウへ送信
				PixelFormat.TRANSLUCENT);  // viewを透明にする

		windowManager.addView(overlapView, overlapViewParams);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Log.d(TAG, "removeView");
				windowManager.removeView(overlapView);
				stopSelf();
			}
		}, 10 * 1000);


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
	}
}
