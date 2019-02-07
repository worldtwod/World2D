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

import com.titicolab.mock.R;
import com.titicolab.mock.rule.GraphicTestRule;
import com.titicolab.mock.rule.ObserverGraphicContext;
import com.titicolab.nanux.core.GraphicContext;
import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.graphics.draw.Image;
import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.nanux.graphics.texture.Texture;
import com.titicolab.nanux.list.FixList;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.DrawerImage;
import com.titicolab.opengl.shader.ImageShaderProgram;
import com.titicolab.opengl.util.TextResourceReader;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.platform.app.InstrumentationRegistry;

/**
 * Created by campino on 15/11/2016.
 * This test shows how use a DrawerImage
 */

public class ImageDrawerTest implements ObserverGraphicContext.SurfaceCreated, ObserverGraphicContext.DrawFrame {

    @Rule
    public GraphicTestRule graphicRule = new GraphicTestRule.Builder()
            .setObserverSurfaceCreated(this)
            .setObserverDrawFrame(this)
            .build();

    private DrawerImage imageDrawer;
    private ProjectionUi projection;
    private FixList<Image> list;
    private ImageShaderProgram shader;


    @Override
    public void onSurfaceCreated(GraphicContext game, GPUInfo eglConfig) {
        projection= new ProjectionUi(game.getDisplayInfo());
        projection.setViewport(1280,720,ProjectionUi.SCALE_HEIGHT);
        shader = getMockProgram();
        shader.buildProgram();
    }


    @Override
    public void onDrawFrame(DrawTools drawTools) {
        if(list==null) return;

        for (int i = 0; i < list.size(); i++) {
            list.get(i).updateRender();
        }

        imageDrawer.begin(projection.getMatrix());
            for (int i = 0; i < list.size(); i++)
                imageDrawer.add(list.get(i));
        imageDrawer.end();
    }



    @Test
    public void testAnimation(){
        int N=4;
        float size = projection.getViewPortWidth()/N;
        Texture texture = graphicRule.getTextureManager().getTexture(R.drawable.test_square_256);
        FixList<Image> listImages = new FixList<>(N);

        for (int i = 0; i < listImages.capacity(); i++) {
            Image image = new Image(texture);
            image.setSize(size,size);
            image.setPosition(size/2+size*i,size);
            listImages.add(image);
        }

        imageDrawer = new DrawerImage(N,shader);
        list = listImages;
        graphicRule.waitTouchSeconds(20);
    }



    private ImageShaderProgram getMockProgram() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String vertexSh =  TextResourceReader.readTextFileFromResource(
                appContext, R.raw.image_vertex);
        String fragmentSh = TextResourceReader.readTextFileFromResource(
                appContext, R.raw.image_fragment);
        return  new ImageShaderProgram(vertexSh,fragmentSh);
    }

}
