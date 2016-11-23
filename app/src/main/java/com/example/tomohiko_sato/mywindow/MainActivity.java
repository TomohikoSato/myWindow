package com.example.tomohiko_sato.mywindow;

import android.graphics.PixelFormat;
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

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
	private final static String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button toExternalButton = (Button) findViewById(R.id.to_external);
		toExternalButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MyService.startService(MainActivity.this);
/*				final View overlayView = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_external, null);
				Button closeButton = (Button) overlayView.findViewById(R.id.close_button);
				closeButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(MainActivity.this, "click!!", Toast.LENGTH_SHORT).show();
						getWindowManager().removeView(overlayView);
					}
				});

				closeButton.setOnTouchListener(MainActivity.this);

				WindowManager.LayoutParams params = new WindowManager.LayoutParams(
						toPixel(400),
						toPixel(220),
						WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
						WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
								WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
								WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
						PixelFormat.TRANSLUCENT);

				getWindowManager().addView(overlayView, params);*/
			}
		});
	}

	private int toPixel(int dp) {
		final float scale = getResources().getDisplayMetrics().density;
		int pixel = (int) (dp * scale + 0.5f); //TODO: 0.5?
		return pixel;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.d(TAG, "Touch event: " + event.toString());

		return false;
	}
}
