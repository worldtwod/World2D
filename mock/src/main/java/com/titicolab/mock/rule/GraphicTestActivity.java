package com.titicolab.mock.rule;

import android.os.Bundle;

import com.titicolab.nanux.core.Controller;
import com.titicolab.puppeteer.AndroidGraphic;
import com.titicolab.puppeteer.AndroidGraphicBuilder;
import com.titicolab.puppeteer.GraphicActivity;


public class GraphicTestActivity extends GraphicActivity {

    private AndroidGraphicBuilder builder;

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
        builder = new AndroidGraphicBuilder(getApplicationContext())
                .setSizeGeometries(sMaximumSizeGeometries)
                .setSizeSprites(sMaximumSizeSprites)
                .setDisplayFPS(sFlatDisplayFPS);
      return builder.build();
    }

    Controller getController(){
        return builder.getController();
    }

}
