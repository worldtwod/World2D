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

package com.titicolab.nanux.core;

import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.nanux.graphics.texture.TextureManager;
import com.titicolab.nanux.objects.Physics;
import com.titicolab.nanux.objects.base.Scene;
import com.titicolab.nanux.objects.base.SceneManager;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.DisplayInfo;
import com.titicolab.nanux.util.GPUInfo;

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


    private TextureManager mTextureManager;
    private SceneManager   mSceneManager;


    private DrawTools         mDrawTools;
    private DrawTools.Builder mDrawToolsBuilder;

    private boolean mFlatStart;


    private Scene   mStartScene;



    private boolean mShowFPS;

    private ProjectionUi mProjectionUi;

    public Puppeteer(DrawTools.Builder drawToolsBuilder) {
        mDrawToolsBuilder = drawToolsBuilder;
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

        mDrawTools = mDrawToolsBuilder.build(mTextureManager);
        DrawTools.setScalePixel(mDisplayInfo.getScalePixel());

        if(mStartScene!=null){
            mSceneManager.play(mStartScene);
        }

        mFlatStart=true;

        mProjectionUi= new ProjectionUi(game.getDisplayInfo());
    }


    @Override
    public void onSurfaceChanged(int width, int height) {
        //mGlWidth = width;
        //mGlHeight = height;
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

        if(mShowFPS) {
            mDrawTools.text.setMatrix(mProjectionUi.getMatrix());
            mDrawTools.text.print(mPerformance.getAverageFPS() + " FPS",
                    120, 700, 20);
        }
    }


    /************************   Scene play ********************************************************/




    @Override
    public synchronized void setStartScene(Scene startScene) {
        if(mFlatStart)
            mSceneManager.play(startScene);
        else
            mStartScene = startScene;
    }


   /********************* Input Event   **********************************************************/

    @Override
    public boolean onTouch(ObservableInput.Event event) {

        return mSceneManager.onTouch(event);
    }

    @Override
    public boolean onKey(int keyEvent) {
        return mSceneManager.onKey(keyEvent);
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

    public void showFPS(boolean mShowFPS) {
        this.mShowFPS = mShowFPS;
    }







}
