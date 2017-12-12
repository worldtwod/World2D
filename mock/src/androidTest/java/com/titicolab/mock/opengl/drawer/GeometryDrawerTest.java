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

package com.titicolab.mock.opengl.drawer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.titicolab.android.engine.AndroidDisplayMetrics;
import com.titicolab.mock.cases.GraphicsTestCase;
import com.titicolab.nanux.animation.GameContext;
import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.R;
import com.titicolab.opengl.shader.DrawerGeometry;
import com.titicolab.opengl.shader.GeometryShaderProgram;
import com.titicolab.opengl.util.TextResourceReader;
import com.titicolab.puppet.draw.Geometry;
import com.titicolab.puppet.model.GeometryModel;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertNotNull;

/**
 * Created by campino on 11/11/2016.
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class GeometryDrawerTest extends GraphicsTestCase{


    private GeometryShaderProgram shader;
    private DrawerGeometry drawer;
    private ProjectionUi projectionUi;
    private Geometry geometry;

    @Override
    public void onSurfaceCreated(GameContext game, GPUInfo eglConfig) {
        Context appContext = InstrumentationRegistry.getTargetContext();
        AndroidDisplayMetrics displayMetrics = new AndroidDisplayMetrics(appContext);
        projectionUi = new ProjectionUi(displayMetrics);


        String vertexSh =  TextResourceReader.readTextFileFromResource(
                appContext, R.raw.geometry_vertex_shader);
        String fragmentSh = TextResourceReader.readTextFileFromResource(
                appContext, R.raw.geometry_fragment_shader);

        shader = new GeometryShaderProgram(vertexSh,fragmentSh);
        shader.buildProgram();

        drawer = new DrawerGeometry(1,shader);


        geometry = new Geometry(new GeometryModel(displayMetrics.getScalePixel()));
        geometry.setPosition(projectionUi.getViewPortWidth()/2,projectionUi.getViewPortHeight()/2);
        geometry.setSize((int)projectionUi.getViewPortWidth(),(int)projectionUi.getViewPortHeight());

    }

    @Override
    public void onDrawFrame() {

        geometry.updateRender();
        drawer.begin(projectionUi.getMatrix());
         drawer.add(geometry);
        drawer.end();

    }

    @Test
    public void a_shader(){
        assertNotNull(drawer);
        drawer.setColor(0,1,1,1);
        drawer.setLineWidth(10.0f);

        waitTouchSeconds(60);

    }




}
