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
import android.support.annotation.CallSuper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.titicolab.mock.cases.GraphicsTestCase;
import com.titicolab.nanux.core.GameContext;
import com.titicolab.nanux.core.RunnerTask;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.AndroidDrawToolsBuilder;
import com.titicolab.opengl.shader.AndroidTextureManager;
import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.objects.base.SceneManager;

import org.junit.runner.RunWith;

/**
 * Created by campino on 15/11/2016.
 *
 */
@RunWith(AndroidJUnit4.class)
public class SceneManagerTestCase extends GraphicsTestCase{




    private SceneManager mSceneManager;
    private DrawTools mDrawTools;
    private AndroidTextureManager mTextureManager;
    private RunnerTask mRunnerTask;

    @CallSuper
    @Override
    public void onSurfaceCreated(GameContext game, GPUInfo eglConfig) {
        super.onSurfaceCreated(game, eglConfig);

        Context appContext = InstrumentationRegistry.getTargetContext();

        mRunnerTask = new RunnerTask(Thread.currentThread());

        mTextureManager = new AndroidTextureManager(appContext,
                mRunnerTask,
                game.getDisplayInfo());

        mSceneManager= new SceneManager(mTextureManager.getRunnerTask()
                ,mTextureManager,
                getDisplayInfo());

        mSceneManager.setAsyncLaunch(true);

        DrawTools.Builder drawToolsBuilder  = new AndroidDrawToolsBuilder(appContext);
        mDrawTools = drawToolsBuilder.build(mTextureManager);
    }


    @Override
    public void onDrawFrame() {
        super.onDrawFrame();

        mRunnerTask.update();


        if(mTextureManager.isLock())
            return;

        mSceneManager.onUpdateLogic();
        mSceneManager.onDrawScene(mDrawTools);
    }

    public SceneManager getSceneManager() {
        return mSceneManager;
    }

}
