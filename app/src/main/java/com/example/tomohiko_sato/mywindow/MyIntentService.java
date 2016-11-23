package com.example.tomohiko_sato.mywindow;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class MyIntentService extends IntentService {
	// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
	private static final String ACTION_FOO = "com.example.tomohiko_sato.mywindow.action.FOO";

	private static final String EXTRA_PARAM1 = "com.example.tomohiko_sato.mywindow.extra.PARAM1";
	private static final String EXTRA_PARAM2 = "com.example.tomohiko_sato.mywindow.extra.PARAM2";

	public MyIntentService() {
		super("MyIntentService");
	}

	public static void startIntentService(Context context, String param1, String param2) {
		Intent intent = new Intent(context, MyIntentService.class);
		intent.setAction(ACTION_FOO);
		intent.putExtra(EXTRA_PARAM1, param1);
		intent.putExtra(EXTRA_PARAM2, param2);
		context.startService(intent);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (intent != null) {
			final String action = intent.getAction();
			if (ACTION_FOO.equals(action)) {
				final String param1 = intent.getStringExtra(EXTRA_PARAM1);
				final String param2 = intent.getStringExtra(EXTRA_PARAM2);
				handleActionFoo(param1, param2);
			}
		}
	}

	/**
	 * Handle action Foo in the provided background thread with the provided
	 * parameters.
	 */
	private void handleActionFoo(String param1, String param2) {
		// TODO: Handle action Foo
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
