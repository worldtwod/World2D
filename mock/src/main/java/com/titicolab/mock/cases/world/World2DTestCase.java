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

import com.titicolab.mock.cases.GraphicsTestCase;
import com.titicolab.nanux.core.GameContext;
import com.titicolab.nanux.core.Puppeteer;
import com.titicolab.nanux.core.RunnableTask;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.AndroidDrawToolsBuilder;
import com.titicolab.puppet.objects.World2D;

import androidx.test.platform.app.InstrumentationRegistry;


/**
 * Created by campino on 11/11/2016.
 *
 */
public class World2DTestCase extends GraphicsTestCase {

    private Puppeteer mController;

    protected Puppeteer onAttachController(Context appContext){
        return  new Puppeteer(new AndroidDrawToolsBuilder(appContext));
    }

    @Override
    public void onSurfaceCreated(GameContext game, GPUInfo eglConfig) {
        super.onSurfaceCreated(game, eglConfig);
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        mController  = onAttachController(appContext);
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

    public void setWorldBoundary(boolean b) {
        getWorld2D().setDrawBoundary(b);
    }

}
