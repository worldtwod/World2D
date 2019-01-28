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

package com.titicolab.mock.opengl.shaders;

import android.content.Context;

import com.titicolab.mock.rule.GraphicTestRule;
import com.titicolab.mock.rule.ObserverGraphicContext;
import com.titicolab.nanux.core.GraphicContext;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.R;
import com.titicolab.opengl.shader.ImageShaderProgram;
import com.titicolab.opengl.util.TextResourceReader;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.InstrumentationRegistry;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by campino on 11/11/2016.
 *
 */
public class CompileImageShaderTest implements ObserverGraphicContext.SurfaceCreated {

    @Rule
    public GraphicTestRule rule = new GraphicTestRule.Builder()
            .setObserverSurfaceCreated(this)
            .build();

    private ImageShaderProgram shader;


    @Override
    public void onSurfaceCreated(GraphicContext game, GPUInfo eglConfig) {
        Context appContext = InstrumentationRegistry.getTargetContext();
        String vertexSh =  TextResourceReader.readTextFileFromResource(
                appContext, R.raw.image_vertex);
        String fragmentSh = TextResourceReader.readTextFileFromResource(
                appContext, R.raw.image_fragment);
        shader = new ImageShaderProgram(vertexSh,fragmentSh);
        shader.buildProgram();
    }


    @Test
    public void compileShaderTest(){
        assertNotNull(shader);
        assertNotEquals(0,shader.getProgramId());
    }




}
