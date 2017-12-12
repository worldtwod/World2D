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

package com.titicolab.mock.cases.puppet;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.titicolab.mock.cases.GraphicsTestCase;
import com.titicolab.nanux.animation.GameContext;
import com.titicolab.nanux.animation.RunnableTask;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.AndroidDrawToolsBuilder;
import com.titicolab.puppet.loop.Puppeteer;
import com.titicolab.puppet.objects.base.Scene;

import org.junit.runner.RunWith;

/**
 * Created by campino on 11/11/2016.
 *
 */
@RunWith(AndroidJUnit4.class)
public class SceneTestCase extends GraphicsTestCase {

    private Puppeteer mController;

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
    }

    @Override
    public boolean onTouch(ObservableInput.Event event) {
        super.onTouch(event);
        return  mController.onTouch(event);
    }


    protected  void syncPlay(Scene scene){
        mController.getSceneManager().play(scene);
        scene.waitOnCreated(60*10);
    }

    public void runAndWaitOnGLThread(RunnableTask runnable){
        mController.getRunnerTask().runAndWait(runnable);
    }

    public Scene getScene() {
        return mController.getSceneManager().getScene();
    }


}
