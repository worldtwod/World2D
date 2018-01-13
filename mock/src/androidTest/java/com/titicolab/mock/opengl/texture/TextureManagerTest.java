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

package com.titicolab.mock.opengl.texture;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.titicolab.puppeteer.AndroidDisplayMetrics;
import com.titicolab.mock.R;
import com.titicolab.mock.cases.GraphicsTestCase;
import com.titicolab.nanux.core.GameContext;
import com.titicolab.nanux.core.RunnerTask;
import com.titicolab.nanux.list.FixList;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.AndroidTextureManager;
import com.titicolab.opengl.shader.GLTexture;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by campino on 11/11/2016.
 *
 */
@RunWith(AndroidJUnit4.class)
public class TextureManagerTest extends GraphicsTestCase {

    private AndroidTextureManager mTextureManager;

    private final int[] resources = new int[]
            {R.drawable.test_square_64,
                R.drawable.test_cricle_256,
                    R.drawable.test_cricle_720};


    @Override
    public void onSurfaceCreated(GameContext game, GPUInfo eglConfig) {
        super.onSurfaceCreated(game, eglConfig);
        Context appContext = InstrumentationRegistry.getTargetContext();
        mTextureManager = new AndroidTextureManager(appContext,
                new RunnerTask(Thread.currentThread()),
                new AndroidDisplayMetrics(appContext));
    }


    @Override
    public void onDrawFrame() {
        super.onDrawFrame();
        mTextureManager.getRunnerTask().update();
    }

    @Test
    public void aTestOneTexture(){

        FixList<GLTexture> list = getTextures(resources);
        assertEquals(list.size(),resources.length);
        assertEquals(mTextureManager.size(),resources.length);

        FixList<GLTexture> list1 = getTextures(resources); // does no create new GLTextures
        assertEquals(list1.size(),resources.length);

        assertEquals(mTextureManager.size(),resources.length);

        for (int i = 0; i <list.size(); i++) {
            assertEquals(list.get(i),list1.get(i));
        }


        mTextureManager.freeAllGLTextures();
        for (int i = 0; i <list.size(); i++) {
            assertEquals(list.get(i).getTextureId(),-1);
        }


        mTextureManager.recoverAllTextures();
        for (int i = 0; i <list.size(); i++) {
            assertThat(true,is(list.get(i).getTextureId()>0));
        }

    }



    private FixList<GLTexture> getTextures(int resources[]){
        FixList<GLTexture> list = new FixList<>(resources.length);
        for (int i = 0; i < resources.length; i++) {
            GLTexture texture = mTextureManager.getTexture(resources[i]);
            list.add(texture);
        }
        return list;
    }


}
