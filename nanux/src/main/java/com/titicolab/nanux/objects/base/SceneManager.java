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

package com.titicolab.nanux.objects.base;

import com.titicolab.nanux.core.RunnableTask;
import com.titicolab.nanux.core.RunnerTask;
import com.titicolab.nanux.graphics.texture.TextureManager;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.list.FixList;
import com.titicolab.nanux.util.DisplayInfo;
import com.titicolab.nanux.graphics.draw.DrawTools;

/**
 * Created by campino on 03/12/2017.
 *
 *
 */

public class SceneManager  implements ObservableInput.InputListener{

    private static final int STATUS_IDLE = 0;
    private static final int STATUS_LAUNCH_SCENE      = 1;
    private static final int STATUS_TRANSITION_IN     = 3;
    private static final int STATUS_TRANSITION_OUT    = 5;
    private static final int STATUS_LOADING_TRANSITION = 7;

    private final  DisplayInfo    mDisplayInfo;
    private int mStatus;
    private boolean mTransitionFlat;
    private boolean isRunningTransition;

    private final RunnerTask mRunnerTask;
    private final TextureManager mTextureManager;

    //Sub status for loading_transition
    private boolean mLoadingTransition;

    private boolean mAsyncLaunch;
    private Scene   mCurrentScene;
    private Transition mTransition;

    private final FixList<SceneCommand> mCommandList;
    private SceneCommand mNextCommand;
    private boolean mLoadingScene;
    private boolean mLoadingTransitionIn;
    private boolean mLoadingTransitionOut;
    private boolean mTransitionFinished;


    public SceneManager(RunnerTask runnerTask,
                        TextureManager textureManager,
                        DisplayInfo screenMetrics) {


        mTransitionFlat=false;
        mAsyncLaunch = false;
        mStatus = STATUS_IDLE;
        mRunnerTask=runnerTask;
        mTextureManager = textureManager;
        mDisplayInfo = screenMetrics;
        mCommandList = new FixList<>(5);

       // mTouchManager.add(this);
    }


    public void setAsyncLaunch(boolean async){
        mAsyncLaunch=async;
    }


    public Transition getTransition(){
        return mTransition;
    }


    public Scene getScene(){
        return mCurrentScene;
    }


    public boolean isTransitionFinished() {
        return mTransitionFinished;
    }

    public void setTransitionsEnable(boolean transitions) {
        mTransitionFlat = transitions;
    }

    private boolean hasTransition() {
        return mTransitionFlat && (mTransition!=null);
    }


    private void queueCommand(final SceneCommand command){
        mRunnerTask.queueTask(new RunnableTask() {
            @Override
            public void run() {
                mCommandList.add(command);
            }
        });
    }

    /**
     * Launch a scene, the scene will be build in a working thread.
     * @param scene playScene to launch
     */
    synchronized public void play(final Scene scene) {
        queueCommand(new SceneCommand
                (SceneCommand.TYPE_PLAY_SCENE,scene));
        mTransitionFinished = false;
    }


    public void setTransition(final Transition transition){
        queueCommand(new SceneCommand
                (SceneCommand.TYPE_LOAD_TRANSITION,
                        transition));
    }




    private void updateNextCommand() {
        synchronized (mCommandList) {

            mLoadingScene=false;
            mLoadingTransition=false;
            mLoadingTransitionIn=false;
            mLoadingTransitionOut=false;


            if (mCommandList.size() > 0) {
                mNextCommand = mCommandList.get(0);
                mCommandList.remove(mNextCommand);
                if (mNextCommand.isLoadTransition()) {
                    mStatus = STATUS_LOADING_TRANSITION;
                } else if (mNextCommand.isPlayScene() &&
                        hasTransition()) {
                    mStatus = STATUS_TRANSITION_IN;
                } else if (mNextCommand.isPlayScene() &&
                        !hasTransition()) {
                    mStatus = STATUS_LAUNCH_SCENE;
                }
            }
        }
    }


    public void onUpdateLogic() {

        if(mStatus == STATUS_IDLE)
            updateNextCommand();

        if(mStatus == STATUS_LOADING_TRANSITION){
            loadTransition((Transition)
                    mNextCommand.scene);
            updateScene();
        }else if(mStatus == STATUS_LAUNCH_SCENE){
            loadScene(mNextCommand.scene,true);
            updateScene();
        }else if(mStatus == STATUS_TRANSITION_IN) {
            transitionIn();
            updateScene();
            updateTransition();
        }else  if(mStatus == STATUS_TRANSITION_OUT) {
            transitionOut();
            updateScene();
            updateTransition();
        }else  if(mStatus == STATUS_IDLE){
            updateScene();
        }
    }



    private void updateScene() {
        if( mCurrentScene!=null) {
            mCurrentScene.updateLogic();
            mCurrentScene.updateRender();
        }
    }

    private void updateTransition() {
        if(mTransition!=null && isRunningTransition) {
            mTransition.updateLogic();
            mTransition.updateRender();
        }
    }


    public void onDrawScene(DrawTools drawer) {
        if(mCurrentScene !=null)
            mCurrentScene.onDraw(drawer);

        drawTransition(drawer);
    }


    private void drawTransition(DrawTools drawer) {
        if(hasTransition() && isRunningTransition)
            mTransition.onDraw(drawer);
    }

    /************************************************** STAUS LOGIC *******************************
     * ********************************************************************************************
     */

    private void loadScene(final Scene scene, final boolean goToIdle){
        if(!mLoadingScene) {
            if (mAsyncLaunch) {
                mLoadingScene = true;
                new Thread(new Runnable() {
                    public void run() {
                        changeScene(scene);
                        if(goToIdle)
                            setSyncStatus(STATUS_IDLE);
                    }
                }).start();

            } else {
                changeScene(scene);
                if(goToIdle)
                    mStatus = STATUS_IDLE;
            }
        }
    }


    private void loadTransition(final Transition transition){
        if(!mLoadingTransition) {
            if (mAsyncLaunch) {
                mLoadingTransition = true;
                new Thread(new Runnable() {
                    public void run() {
                        mTransition = (Transition) startScene(transition);
                        setSyncStatus(STATUS_IDLE);
                    }
                }).start();

            } else {
                mLoadingTransition = false;
                mTransition = (Transition) startScene(transition);
                mStatus = STATUS_IDLE;
            }
        }
    }


    /**
     * The SceneManager is an observer of onFullIn(), the Transition notify of this event
     */
    private void transitionIn() {
        if(!mLoadingTransitionIn) {
            mLoadingTransitionIn=true;
            isRunningTransition = true;
            mTransition.in(new Transition.OnFullIn() {
                @Override
                public void onFullIn() {
                    setSyncStatus(STATUS_TRANSITION_OUT);
                }
            });
        }
    }

    /**
     * The SceneManager is an observer of onFullOut(), the Transition notify of this event
     */
    private void transitionOut() {
        if(!mLoadingTransitionOut) {
            mLoadingTransitionOut=true;
            loadScene(mNextCommand.scene,false);
            mTransition.out(new Transition.OnFullOut() {
                @Override
                public void onFullOut() {
                    isRunningTransition = false;
                    setSyncStatus(STATUS_IDLE);
                    mTransitionFinished =true;
                }
            });
        }
    }


    private void setSyncStatus(final int status) {
        mRunnerTask.queueTask(new RunnableTask() {
            @Override
            public void run() {
                mStatus=status;
            }
        });
    }



    /************************************************** START SCENE *******************************
     * ********************************************************************************************
     */

    private void changeScene(Scene next){
        stopCurrentScene(mCurrentScene);
        startScene(next);
        mCurrentScene =next;
    }

    void stopCurrentScene(Scene scene) {
        mCurrentScene=null;
        if(scene!=null){
            scene.onStop();
            scene.onDestroy();
        }
    }


    public Scene startScene(Scene scene){

        scene.onAttachParameters(null);

        scene.onAttachSceneManager(this);

        scene.onAttachDisplayInfo(mDisplayInfo);


        ObjectFactory factory = new ObjectFactory(scene, mTextureManager);
        factory.factoryGroupLayer(scene);

        // This run in the looper
        notifyStartScene(scene);

        return scene;
    }

    public void onDestroy() {
        stopCurrentScene(mCurrentScene);
    }


    private void notifyStartScene(final Scene next) {
        mRunnerTask.queueTask(new RunnableTask() {
            @Override
            public void run() {
                next.onStart();
            }
        });
    }


    public RunnerTask getRunnerTask() {
        return mRunnerTask;
    }

    public  void queueTask(RunnableTask task) {
        mRunnerTask.queueTask(task);
    }



    private class SceneCommand {
        static final int TYPE_PLAY_SCENE = 1;
        static final int TYPE_LOAD_TRANSITION = 2;

        final int type;
        final Scene scene;

        SceneCommand(int type, Scene scene) {
            this.type = type;
            this.scene = scene;
        }

        boolean isPlayScene(){
            return type== TYPE_PLAY_SCENE;
        }
        boolean isLoadTransition(){
            return type== TYPE_LOAD_TRANSITION;
        }
    }





    @Override
    public boolean onTouch(ObservableInput.Event event) {
        boolean r = false;
        float uiX = mCurrentScene.getCameraUi().pxToCameraX(event.getPixelX());
        float uiY = mCurrentScene.getCameraUi().pxToCameraY(event.getPixelY());
        float x = mCurrentScene.getCamera2D().pxToCameraX(event.getPixelX());
        float y = mCurrentScene.getCamera2D().pxToCameraY(event.getPixelY());
        event.setPositionUi(uiX,uiY);
        event.setPosition2D(x,y);

        if(mStatus == STATUS_IDLE) {
            if (mCurrentScene != null && mCurrentScene.isTouchable()){
                r=mCurrentScene.onTouch(event);
            }
        }
        return r;
    }

    @Override
    public boolean onKey(int keyEvent) {
        return false;
    }




}
