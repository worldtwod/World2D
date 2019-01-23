package com.titicolab.mock.rule;

import android.os.Bundle;

import com.titicolab.nanux.core.SceneLauncher;
import com.titicolab.nanux.objects.base.Scene;
import com.titicolab.puppet.objects.World2D;
import com.titicolab.puppeteer.GraphicActivity;


public class TestRuleActivity extends GraphicActivity implements SceneLauncher {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sFlagFullScreen=true;
        sFlagSensorLandscape=true;
        sFlatEnableLogs=true;
        super.onCreate(savedInstanceState);
        setSceneLauncher(this);
        setContentView(getGLGameView());
    }

    @Override
    public Scene onLaunchScene() {
        return null; //instance here
    }


    protected void syncPlay(World2D world2D) {
        get
        mController.getSceneManager().play(world2D);
        world2D.waitOnCreated(60 * 10);
    }

}
