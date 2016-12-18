package com.example.tomohiko_sato.mywindow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    @Override
    protected void onResume() {
        super.onResume();

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView();
    }
}
