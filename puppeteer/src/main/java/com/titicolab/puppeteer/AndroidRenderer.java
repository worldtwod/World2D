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

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import androidx.annotation.NonNull;

import com.titicolab.nanux.core.GraphicContext;
import com.titicolab.puppeteer.util.ParamsChecker;
import com.titicolab.puppeteer.view.GLGraphicView;
import com.titicolab.nanux.core.ObservableRenderer;
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



    private GraphicContext mGraphicContext;
    private final GLGraphicView mGLGraphicView;
    private boolean mFlagNotify;
    private float clearColor[];

    @Deprecated
    AndroidRenderer(@NonNull GraphicContext game, @NonNull GLGraphicView gLGraphicView) {
        super(1);
        ParamsChecker.checkNull(game,"GraphicContext game");
        ParamsChecker.checkNull(gLGraphicView,"GLGameView gLGameView");
        mFlagNotify = true;
        mGraphicContext =game;
        mGLGraphicView = gLGraphicView;
        clearColor= new float[]{0,0,0,1};
    }

    AndroidRenderer(@NonNull GLGraphicView gLGraphicView) {
        super(1);
        ParamsChecker.checkNull(gLGraphicView,"GLGameView gLGameView");
        mFlagNotify = true;
        mGraphicContext =null;
        mGLGraphicView = gLGraphicView;
        clearColor= new float[]{0,0,0,1};
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        ParamsChecker.checkNullMessage(mGraphicContext,"Before of You onSurfaceCreated(), " +
                "you must be to set a GraphicContext, use the setter ");
        glClearColor(clearColor[0], clearColor[1], clearColor[2], clearColor[3]);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        notifySurfaceCreated(mGraphicContext,new AndroidGPUInfo(eglConfig));
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

    private void notifySurfaceCreated(GraphicContext gameView, GPUInfo config){
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
        mGLGraphicView.setRenderer(this);
    }

    @Override
    public void reStart() {
        mFlagNotify=true;
    }

    @Override
    public void stop() {
        mFlagNotify=false;
    }

    /**
     *  Specify the red, green, blue, and alpha values used when the color buffers are cleared.
     *  The initial values are all 0.
     * @param red   r
     * @param green g
     * @param blue  b
     * @param alpha a
     */
    public void setClearColor(float red, float green, float blue, float alpha) {
             clearColor[0] = red;
             clearColor[1] = green;
             clearColor[2] = blue;
             clearColor[2] = alpha;
    }

    public void setGraphicContext(GraphicContext mGraphicContext) {
        this.mGraphicContext = mGraphicContext;
    }
}
