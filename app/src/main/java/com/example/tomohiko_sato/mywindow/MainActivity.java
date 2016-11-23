package com.example.tomohiko_sato.mywindow;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	private final static String TAG = MainActivity.class.getSimpleName();

	private boolean bound = false;
	private MyService myService;
	private ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MyService.MyServiceBinder binder = (MyService.MyServiceBinder) service;
			myService = binder.getService();
			bound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			bound = false;
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button toExternalButton = (Button) findViewById(R.id.to_external);
		toExternalButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MyService.startService(MainActivity.this);
				View inflateView = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_external, null);
				Button closeButton = (Button) inflateView.findViewById(R.id.close_button);
				closeButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Log.d(TAG, "onclick");
					}
				});

				myService.addView(inflateView);
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		Intent intent = new Intent(this, MyService.class);
		bindService(intent, connection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (bound) {
			unbindService(connection);
			bound = false;
		}
	}

	private int toPixel(int dp) {
		final float scale = getResources().getDisplayMetrics().density;
		int pixel = (int) (dp * scale + 0.5f); //TODO: 0.5?
		return pixel;
	}

}
