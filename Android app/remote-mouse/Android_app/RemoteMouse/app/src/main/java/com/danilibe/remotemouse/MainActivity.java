package com.danilibe.remotemouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout touchable_area = (RelativeLayout) findViewById(R.id.touchable_area);

        RemoteMouse remote_mouse = new RemoteMouse(MainActivity.this, touchable_area, "wh1t3-sh1p", 7898);
        remote_mouse.execute();
    }
}
