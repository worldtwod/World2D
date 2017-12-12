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

package com.titicolab.android.engine;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.annotation.NonNull;

import com.titicolab.android.view.GLGameView;
import com.titicolab.nanux.animation.GameContext;
import com.titicolab.nanux.animation.ObservableRenderer;
import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.nanux.util.GPUInfo;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static javax.microedition.khronos.opengles.GL10.GL_COLOR_BUFFER_BIT;

/**
 * Created by campino on 10/11/2016.
 *
 */

public class AndroidRenderer extends FlexibleList<ObservableRenderer.Renderer>
        implements GLSurfaceView.Renderer,
        ObservableRenderer {


   // private String mThreadName;
    private final GameContext mGameContext;
    private final GLGameView mGLGameView;
    private boolean mFlagNotify;

    AndroidRenderer(@NonNull GameContext game, @NonNull GLGameView gLGameView) {
        super(1);
        mFlagNotify = true;
        mGameContext=game;
        mGLGameView = gLGameView;
        mGLGameView.setUpConfiguration();
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //glClearColor(83f/255, 93f/255, 108f/255, 1f);
        glClearColor(55f/255, 62f/255, 72f/255, 1f);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        //mThreadName =  Thread.currentThread().getName();
        notifySurfaceCreated(mGameContext,new AndroidGPUInfo(eglConfig));
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        //GLES20.glViewport(0, 0, 1280, 720);
        GLES20.glViewport(0, 0, width, height);
        notifySurfaceChanged(width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT); // Clear the screen with the color setClip in glClearColor
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        notifyDrawFrame();
    }

    /** Notify ***********************************************************************************/

    private void notifySurfaceCreated(GameContext gameView, GPUInfo config){
        if(mFlagNotify)
        for (int i = 0; i < size(); i++) {
            get(i).onSurfaceCreated(gameView,config);
        }
    }

    private void notifyDrawFrame(){
        if(mFlagNotify)
        for (int i = 0; i <size(); i++) {
            get(i).onDrawFrame();
        }
    }

    private void notifySurfaceChanged(int width, int height){
        if(mFlagNotify)
        for (int i = 0; i < size(); i++) {
           get(i).onSurfaceChanged(width,height);
        }
    }


    @Override
    public void start() {
        mFlagNotify=true;
        mGLGameView.setRenderer(this);
    }

    @Override
    public void stop() {
        mFlagNotify=false;
    }


}
