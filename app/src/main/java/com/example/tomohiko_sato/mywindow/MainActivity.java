package com.example.tomohiko_sato.mywindow;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.lang.reflect.Field;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    private boolean isBound = false;
    private MyService myService;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onserviceconnected");
            myService = ((MyService.MyServiceBinder) service).getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onservicedisconnected");
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.to_external).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBound && !myService.hasPlayerView()) {
                    YouTubePlayerView playerView = (YouTubePlayerView) LayoutInflater.from(MainActivity.this).inflate(R.layout.external_player, null);
                    playerView.initialize(Key.Youtube.API_KEY, MainActivity.this);
                    myService.addView(playerView);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        MyService.startService(this);
        MyService.bindService(this, connection);
    }

    @Override
    protected void onPause() {
//        super.onPause();
        try {
            Field field = MainActivity.class.getSuperclass().getDeclaredField("b");
            field.setAccessible(true);
            YouTubePlayerView ypv = (YouTubePlayerView) field.get(this);
            field.set(this, null);
            super.onPause();
            field.set(this, ypv);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    YouTubePlayer youTubePlayer;

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        this.youTubePlayer = youTubePlayer;
        youTubePlayer.loadVideo("HFlgNoUsr4k");
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
    }
}
