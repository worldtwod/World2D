package com.titicolab.mock.rule;

import android.os.Bundle;

import com.titicolab.nanux.core.SceneLauncher;
import com.titicolab.nanux.objects.base.Scene;
import com.titicolab.puppeteer.GraphicActivity;


public class Word2DTestActivity extends GraphicActivity implements SceneLauncher {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sFlagFullScreen=true;
        sFlagSensorLandscape=true;
        sFlatEnableLogs=true;
        sFlatDisplayFPS = true;
        super.onCreate(savedInstanceState);
        setContentView(getGLGameView());
    }

    @Override
    public Scene onLaunchScene() {
        return null; //instance here
    }


}
