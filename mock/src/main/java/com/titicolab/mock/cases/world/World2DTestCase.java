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

package com.titicolab.mock.cases.world;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.titicolab.mock.cases.GraphicsTestCase;
import com.titicolab.nanux.core.GameContext;
import com.titicolab.nanux.core.RunnableTask;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.AndroidDrawToolsBuilder;
import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.graphics.draw.Rectangle;
import com.titicolab.nanux.core.Puppeteer;
import com.titicolab.puppet.objects.World2D;

import org.junit.runner.RunWith;

/**
 * Created by campino on 11/11/2016.
 *
 */
@RunWith(AndroidJUnit4.class)
public class World2DTestCase extends GraphicsTestCase {

    private Puppeteer mController;
    private Rectangle mWorldBoundary;

    @Override
    public void onSurfaceCreated(GameContext game, GPUInfo eglConfig) {
        super.onSurfaceCreated(game, eglConfig);
        Context appContext = InstrumentationRegistry.getTargetContext();
        mController  = new Puppeteer(new AndroidDrawToolsBuilder(appContext));
        mController.onSurfaceCreated(game,eglConfig);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        super.onSurfaceChanged(width, height);
        mController.onSurfaceChanged(width,height);
    }

    @Override
    public void onDrawFrame() {
        super.onDrawFrame();
        mController.onDrawFrame();
        onDraw(mController.getDrawTools());
    }

    private void onDraw(DrawTools drawTools) {
        if(mWorldBoundary!=null) {
            mWorldBoundary.updateRender();
            drawTools.geometry.begin(getWorld2D().getCamera2D().getMatrix());
            drawTools.geometry.add(mWorldBoundary);
            drawTools.geometry.end();
        }
    }

    @Override
    public boolean onTouch(ObservableInput.Event event) {
        super.onTouch(event);
        return  mController.onTouch(event);
    }


    protected void syncPlay(World2D world2D) {
        mController.getSceneManager().play(world2D);
        world2D.waitOnCreated(60 * 10);
    }

    public void runAndWaitOnGLThread(RunnableTask runnable){
        mController.getRunnerTask().runAndWait(runnable);
    }

    public World2D getWorld2D() {
        return (World2D) mController
                .getSceneManager().getScene();
    }


    public void setWorldBoundary(final boolean set){
       mController.getRunnerTask().queueTask(new RunnableTask() {
           @Override
           public void run() {
               mController.getDrawTools()
                       .geometry.setBrushSize(10);
               setupBoundary(set);
           }
       });
    }

    private void setupBoundary(boolean set){
        if(set) {
            World2D world2D = getWorld2D();
            float width = world2D.getMapWorld().getWidth();
            float height = world2D.getMapWorld().getHeight();
            mWorldBoundary = new Rectangle(width, height,getDisplayInfo().getScalePixel());
            mWorldBoundary.setPosition(width / 2, height / 2);
        }else
            mWorldBoundary=null;
    }
}
