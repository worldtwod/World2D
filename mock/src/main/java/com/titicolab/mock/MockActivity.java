package com.titicolab.mock;

import android.os.Bundle;

import com.titicolab.puppeteer.GraphicActivity;


@Deprecated
public class MockActivity extends GraphicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sFlagFullScreen=true;
        sFlagSensorLandscape=true;
        sFlatEnableLogs=true;
        super.onCreate(savedInstanceState);
        setContentView(getGLGameView());
    }

}
