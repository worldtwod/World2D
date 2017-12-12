package com.titicolab.mock;

import android.os.Bundle;

import com.titicolab.android.GameActivity;

public class MockActivity extends GameActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sFlagFullScreen=true;
        sFlagSensorLandscape=true;
        super.onCreate(savedInstanceState);
        setContentView(getAndroidGame().getGLGameView());
    }


}
