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

package com.titicolab.android;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.titicolab.android.engine.AndroidGame;
import com.titicolab.nanux.test.Monitor;

public class GameActivity extends AppCompatActivity {

    /** This flag let that screen rote in horizontal axis  **/
    public static boolean sFlagSensorLandscape = false;

    /** Full screen  **/
    public boolean sFlagFullScreen = false;


    /** AndroidGame */
    private AndroidGame mAndroidGame;

    static Monitor.OnEngineCreated monitor=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(sFlagFullScreen) hideNavigation();

        super.onCreate(savedInstanceState);

        if(sFlagSensorLandscape)
            setRequestedOrientation(ActivityInfo.
                    SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        mAndroidGame = new AndroidGame(this);
    }


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

    public AndroidGame getAndroidGame() {
        return mAndroidGame;
    }


    /************************************************* Life Cycle *********************************
     **********************************************************************************************/
    @Override
    public void onStart() {
        super.onStart();
        mAndroidGame.getObservableLifeCycle().onStart();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        mAndroidGame.getObservableLifeCycle().onRestart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mAndroidGame.getObservableLifeCycle().onResume();
    }
    @Override
    public void onPause() {
        mAndroidGame.getObservableLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        mAndroidGame.getObservableLifeCycle().onStop();
        super.onStop();

    }

    @Override
    public void onDestroy() {
        mAndroidGame.getObservableLifeCycle().onDestroy();
        super.onDestroy();
    }
}
