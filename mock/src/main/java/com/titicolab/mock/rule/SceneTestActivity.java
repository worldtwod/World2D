package com.titicolab.mock.rule;

import android.os.Bundle;

import com.titicolab.puppeteer.AndroidGraphic;
import com.titicolab.puppeteer.AndroidGraphicBuilder;
import com.titicolab.puppeteer.GraphicActivity;


public class SceneTestActivity extends GraphicActivity {

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
    protected AndroidGraphic onAttachGraphicsContext() {
        AndroidGraphicBuilder builder = new AndroidGraphicBuilder(getApplicationContext())
                .setSizeGeometries(sMaximumSizeGeometries)
                .setSizeSprites(sMaximumSizeSprites)
                .setDisplayFPS(sFlatDisplayFPS);
      return builder.build();
    }
}
