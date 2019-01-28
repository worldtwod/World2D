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

package com.titicolab.mock.cases.opengl;

import android.content.Context;
import androidx.annotation.CallSuper;
import androidx.test.platform.app.InstrumentationRegistry;

import com.titicolab.mock.rule.GraphicTestRule;
import com.titicolab.mock.rule.ObserverGraphicContext;
import com.titicolab.nanux.core.GraphicContext;
import com.titicolab.nanux.core.RunnerTask;
import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.graphics.texture.TextureManager;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.AndroidTextureManager;

import org.junit.Rule;


/**
 * Created by campino on 11/11/2016.
 *
 */
@Deprecated
public class TextureManagerTestCase implements ObserverGraphicContext.SurfaceCreated,
                                                    ObserverGraphicContext.DrawFrame {

    protected  TextureManager mTextureManager;

    @Rule
    public GraphicTestRule rule = new GraphicTestRule.Builder()
            .setStartActivity(true)
            .setObserverSurfaceCreated(this)
            .setObserverDrawFrame(this)
            .build();

    @CallSuper
    @Override
    public void onSurfaceCreated(GraphicContext game, GPUInfo eglConfig) {
        Context appContext =InstrumentationRegistry.getInstrumentation().getTargetContext();
        mTextureManager = new AndroidTextureManager(appContext,
                new RunnerTask(Thread.currentThread()),
                game.getDisplayInfo());
    }

    @CallSuper
    @Override
    public void onDrawFrame(DrawTools drawTools) {
        mTextureManager.getRunnerTask().update();
    }

}
