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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
                if (isBound) {
                    YouTubePlayerView playerView = (YouTubePlayerView) LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_external, null);
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

    boolean pausing = false;


    @Override
    protected void onPause() {
        Log.d(TAG, "onPause isPlaying 1: " + youTubePlayer.isPlaying());
        //super.onPause();


        try {
            Field field = MainActivity.class.getSuperclass().getDeclaredField("b");
            field.setAccessible(true);
            YouTubePlayerView ypv = (YouTubePlayerView) field.get(this);
            field.set(this, null);
            super.onPause();
            field.set(this, ypv);

/*
                Method method = MainActivity.class.getSuperclass().getSuperclass().getDeclaredMethod("onPause");
                method.setAccessible(true);
                method.invoke(this);
*/

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        //youTubePlayer.play();
        Log.d(TAG, "onPause isPlaying 2: " + youTubePlayer.isPlaying());
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop isPlaying: " + youTubePlayer.isPlaying());
        super.onStop();
        Log.d(TAG, "onStop");
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
        Log.d(TAG, "onStop isPlaying: " + youTubePlayer.isPlaying());

        Log.d(TAG, "onStop isPlaying: " + youTubePlayer.isPlaying());

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
        youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
            private final String TAG = YouTubePlayer.PlayerStateChangeListener.class.getSimpleName();

            @Override
            public void onLoading() {
                Log.d(TAG, "onLoading");
            }

            @Override
            public void onLoaded(String s) {
                Log.d(TAG, "onLoaded: " + s);
            }

            @Override
            public void onAdStarted() {
                Log.d(TAG, "onAdStarted");
            }

            @Override
            public void onVideoStarted() {
                Log.d(TAG, "onVideoStarted");
            }

            @Override
            public void onVideoEnded() {
                Log.d(TAG, "onVideoEnded");
            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {
                Log.d(TAG, "onError: " + errorReason.toString());
            }
        });

        youTubePlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
            private final String TAG = YouTubePlayer.PlaybackEventListener.class.getSimpleName();

            @Override
            public void onPlaying() {
                Log.d(TAG, "onPlaying");
            }

            @Override
            public void onPaused() {
                Log.d(TAG, "onPaused");
            }

            @Override
            public void onStopped() {
                Log.d(TAG, "onStopped");
            }

            @Override
            public void onBuffering(boolean b) {

            }

            @Override
            public void onSeekTo(int i) {

            }
        });

        youTubePlayer.loadVideo("HFlgNoUsr4k");
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
