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
import android.support.annotation.CallSuper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.titicolab.mock.R;
import com.titicolab.mock.cases.GraphicsTestCase;
import com.titicolab.nanux.animation.GameContext;
import com.titicolab.nanux.animation.RunnerTask;
import com.titicolab.nanux.graphics.textures.TextureManager;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.AndroidTextureManager;
import com.titicolab.opengl.shader.DrawerImage;
import com.titicolab.opengl.shader.ImageShaderProgram;
import com.titicolab.opengl.util.TextResourceReader;

import org.junit.runner.RunWith;

/**
 * Created by campino on 11/11/2016.
 *
 */
@RunWith(AndroidJUnit4.class)
public abstract class ImageDrawerTestCase extends GraphicsTestCase {

    private static final int  IMAGE_LENGTH = 1000;



    protected  TextureManager mTextureManager;
    private    DrawerImage mImageDrawer;


    @CallSuper
    @Override
    public void onSurfaceCreated(GameContext game, GPUInfo eglConfig) {
        super.onSurfaceCreated(game, eglConfig);
        Context appContext = InstrumentationRegistry.getTargetContext();
        mTextureManager = new AndroidTextureManager(appContext,
                new RunnerTask(Thread.currentThread()),
                game.getDisplayInfo());

        String vertexSh =  TextResourceReader.readTextFileFromResource(
                appContext, R.raw.image_vertex);
        String fragmentSh = TextResourceReader.readTextFileFromResource(
                appContext, R.raw.image_fragment);
        ImageShaderProgram shader = new ImageShaderProgram(vertexSh,fragmentSh);
        shader.buildProgram();
        mImageDrawer = new DrawerImage(IMAGE_LENGTH,shader);
    }


    @CallSuper
    @Override
    public void onDrawFrame() {
        super.onDrawFrame();
        mTextureManager.getRunnerTask().update();
        onDrawImage(mImageDrawer);
    }

    protected abstract void onDrawImage(DrawerImage imageDrawer);

    public TextureManager getTextureManager() {
        return mTextureManager;
    }

    public RunnerTask getRunnerTask(){
        return mTextureManager.getRunnerTask();
    }
}
