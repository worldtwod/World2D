/*
 * Copyright  2017   Fredy Campiño
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.titicolab.puppeteer;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.titicolab.nanux.core.SceneLauncher;
import com.titicolab.nanux.objects.base.Scene;
import com.titicolab.puppeteer.util.LogHelper;
import com.titicolab.puppeteer.view.GLGraphicView;
import com.titicolab.nanux.test.Monitor;


/**
 * This activity is the container of all game elements, scenes, layer, objects. Also it is the
 * provider of ContentView, you only needs add @Link{setContentView(getGLGameView());}
 *
 * Created by campino on 10/11/2016.
 */
public class GraphicActivity extends AppCompatActivity {

    /** Maximum size of sprite buffer  **/
    protected static int sMaximumSizeSprites = 1000;

    /** Maximum size of geometries buffer  **/
    protected static int sMaximumSizeGeometries = 2500;

    /** This flag let that screen rote in horizontal axis  **/
    protected static boolean sFlagSensorLandscape = false;

    /** This flag make that screen go full screen  **/
    protected boolean sFlagFullScreen = false;

    /** This flat enable the visibility of FPS in the screen **/
    protected static boolean sDisplayFPS = false;

    /** AndroidGraphic */
    private AndroidGraphic mAndroidGame;

    /** Flat to active logs **/
    public static boolean sFlatEnableLogs = false;


    /** This flat is for test settings, do no touch **/
    static Monitor.OnEngineCreated monitor=null;

    /** flat to check if the on-create was executed **/
    private boolean mFlatOnCreate = false;

    /** logs ***/
    private LogHelper log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        log = new LogHelper(this,"LogsWord2D");
        log.setEnableDebug(sFlatEnableLogs);
        log.debug("onCreate");

        if(sFlagFullScreen)
            hideNavigation();
        super.onCreate(savedInstanceState);

        if(sFlagSensorLandscape)
            setRequestedOrientation(ActivityInfo.
                    SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        mAndroidGame = new AndroidGraphicBuilder(getApplicationContext())
                .setSizeGeometries(sMaximumSizeGeometries)
                .setSizeSprites(sMaximumSizeSprites)
                .setDisplayFPS(sDisplayFPS)
                .build();

        mAndroidGame.start();
        mFlatOnCreate = true;
    }

    public void syncPlay(Scene scene){
        mAndroidGame.syncPlay(scene);
    }

    public void setSceneLauncher(SceneLauncher sceneLauncher) {
        checkOnCreate("setSceneLauncher");
        Scene scene = sceneLauncher.onLaunchScene();
        mAndroidGame.setStartScene(scene);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideNavigation();
        }
    }

    /**
     * Hide the navigation bar, it set the screen to:
     * HIDE_NAVIGATION + FULLSCREEN + IMMERSIVE_STICKY
     */
    private void hideNavigation(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView =getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    /**
     * Return the GLSurfaceView when the action will live
     * @return  GLGameView
     */
    public GLGraphicView getGLGameView() {
        return mAndroidGame.getGLGameView();
    }

    @Override
    public void onStart() {
        super.onStart();
        log.debug("onStart()");
        mAndroidGame.onStart();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        log.debug("onRestart()");
        mAndroidGame.onRestart();
    }
    @Override
    public void onResume() {
        super.onResume();
        log.debug("onResume()");
        mAndroidGame.onResume();
    }
    @Override
    public void onPause() {
        log.debug("onPause() ");
        mAndroidGame.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        log.debug("onStop()");
        mAndroidGame.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        log.debug("onDestroy()");
        mAndroidGame.onDestroy();
        super.onDestroy();
    }

    private void checkOnCreate(String method) {
        if(!mFlatOnCreate)
            throw new RuntimeException("The method "+ method +"needs be called after super.onCreate()");
    }

}
