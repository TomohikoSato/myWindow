package com.example.tomohiko_sato.mywindow;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
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

		windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		//overlapView = new FrameLayout(getApplicationContext());
		final View inflateView = layoutInflater.inflate(R.layout.layout_external, null);
		Button closeButton = (Button) inflateView.findViewById(R.id.close_button);
		closeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "onclick");
			}
		});
		//overlapView.addView(inflateView);

		overlapViewParams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,       // アプリケーションのTOPに配置
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |  // フォーカスを当てない(下の画面の操作がd系なくなるため)
						WindowManager.LayoutParams.FLAG_FULLSCREEN |        // OverlapするViewを全画面表示
						WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, // モーダル以外のタッチを背後のウィンドウへ送信
				PixelFormat.TRANSLUCENT);  // viewを透明にする

		windowManager.addView(inflateView, overlapViewParams);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				windowManager.removeView(inflateView);
				stopSelf();
			}
		}, 10 * 1000);

		return START_STICKY;
	}


	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		Toast.makeText(this, "MyService#onDestroy", Toast.LENGTH_SHORT).show();
	}
}
