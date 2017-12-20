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

package com.titicolab.puppet.loop;

import com.titicolab.nanux.animation.Controller;
import com.titicolab.nanux.animation.GameContext;
import com.titicolab.nanux.animation.GamePerformance;
import com.titicolab.nanux.animation.RunnerTask;
import com.titicolab.nanux.graphics.textures.TextureManager;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.DisplayInfo;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.puppet.draw.DrawTools;
import com.titicolab.puppet.objects.Physics;
import com.titicolab.puppet.objects.base.Scene;
import com.titicolab.puppet.objects.base.SceneManager;

/**
 * Created by campino on 10/11/2016.
 *
 */

public class Puppeteer extends Controller {


    private GameContext           mGameContext;
    private DisplayInfo           mDisplayInfo;
    private GamePerformance       mPerformance;
    private RunnerTask            mRunnerTask;
    private GPUInfo               mGPUInfo;

    private int mGlWidth;
    private int mGlHeight;

    private TextureManager mTextureManager;
    private SceneManager   mSceneManager;


    private DrawTools         mDrawTools;
    private DrawTools.Builder mDrawToolsBuilder;

    private boolean mFlatStart;
    private Scene mStartScene;

    public Puppeteer(DrawTools.Builder drawToolsBuilder) {
        mDrawToolsBuilder = drawToolsBuilder;
    }

    private synchronized  void play(Scene scene) {
        mSceneManager.play(scene);
    }

    public synchronized void setStartScene(Scene startScene) {
        if(mFlatStart)
            play(startScene);
        else
            mStartScene = startScene;
    }

    /************************* Render    *******************************************************/
    @Override
    public void onSurfaceCreated(GameContext game, GPUInfo eglConfig) {


        /*if(mTextureManager!=null) { //TODO
            onRecoveryContext();
            return;
        }*/

        mGameContext =     game;
        mDisplayInfo =     mGameContext.getDisplayInfo();
        mPerformance =     new GamePerformance();
        mRunnerTask =      mGameContext.getTextureManager().getRunnerTask();
        mTextureManager =  mGameContext.getTextureManager();

        mRunnerTask.setRunnerThread(Thread.currentThread());
        mGPUInfo =eglConfig;

        mSceneManager = new SceneManager(mRunnerTask,
                mTextureManager,
               mDisplayInfo);
        mSceneManager.setAsyncLaunch(true);
        mSceneManager.setTransitionsEnable(false);
        //mSceneManager.setTransition(new Transition());

        mDrawTools = mDrawToolsBuilder.build();

        if(mStartScene!=null){
            play(mStartScene);
        }

        mFlatStart=true;

    }


    @Override
    public void onSurfaceChanged(int width, int height) {
        mGlWidth = width;
        mGlHeight = height;
    }

    @Override
    public void onDrawFrame() {

        mPerformance.update();
        mRunnerTask.update();
        Physics.time = mPerformance.getAVRsTimePhysics();

        if(mTextureManager.isLock())
            return;

        mSceneManager.onUpdateLogic();
        mSceneManager.onDrawScene(mDrawTools);
    }

   /********************* Input Event   **********************************************************/


    @Override
    public boolean onTouch(ObservableInput.Event event) {
        return mSceneManager.onTouch(event);
    }

    @Override
    public boolean onKey(int keyEvent) {
        return false;
    }


    /************************Life Cycle **********************************************************/

    @Override
    public void onRestart() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {

    }


    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    public SceneManager getSceneManager() {
        return mSceneManager;
    }

    public RunnerTask getRunnerTask() {
        return mRunnerTask;
    }

    public DrawTools getDrawTools() {
        return mDrawTools;
    }


    /************************* Helper *************************************************************/









}
