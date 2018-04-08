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
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.titicolab.nanux.core.SceneLauncher;
import com.titicolab.nanux.objects.base.Scene;
import com.titicolab.puppeteer.view.GLGameView;
import com.titicolab.nanux.test.Monitor;


/**
 * This activity is the container of all game elements, scenes, layer, objects. Also it is the
 * provider of ContentView, you only needs add @Link{setContentView(getGLGameView());}
 *
 * Created by campino on 10/11/2016.
 */
public class GameActivity extends AppCompatActivity {

    /** This flag let that screen rote in horizontal axis  **/
    public static boolean sFlagSensorLandscape = false;

    /** This flag make that screen go full screen  **/
    public boolean sFlagFullScreen = false;


    /** AndroidGame */
    private AndroidGame mAndroidGame;

    /** This flat is for test settings, do no touch **/
    static Monitor.OnEngineCreated monitor=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(sFlagFullScreen)
            hideNavigation();

        super.onCreate(savedInstanceState);
        if(sFlagSensorLandscape)
            setRequestedOrientation(ActivityInfo.
                    SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        mAndroidGame = new AndroidGame(this);
    }


    public void setSceneLauncher(SceneLauncher sceneLauncher) {
        Scene scene = sceneLauncher.onLaunchScene();
        mAndroidGame.setStartScene(scene);
    }



    public void setShowFPS(boolean showFPS) {
        mAndroidGame.setShowFPS(showFPS);
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
    public GLGameView getGLGameView() {
        return mAndroidGame.getGLGameView();
    }


    @Override
    public void onStart() {
        super.onStart();
        mAndroidGame.onStart();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        mAndroidGame.onRestart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mAndroidGame.onResume();
    }
    @Override
    public void onPause() {
        mAndroidGame.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        mAndroidGame.onStop();
        super.onStop();

    }

    @Override
    public void onDestroy() {
        mAndroidGame.onDestroy();
        super.onDestroy();
    }
}
