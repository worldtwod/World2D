package com.titicolab.nanux;

import android.os.Bundle;

import com.titicolab.android.GameActivity;

public class MainActivity extends GameActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getAndroidGame().getGLGameView());
    }


}
