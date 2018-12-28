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
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.titicolab.mock.R;
import com.titicolab.mock.cases.opengl.TextureManagerTestCase;
import com.titicolab.nanux.core.GameContext;
import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.nanux.graphics.texture.Texture;
import com.titicolab.nanux.list.FixList;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.DrawerImage;
import com.titicolab.opengl.shader.ImageShaderProgram;
import com.titicolab.opengl.util.TextResourceReader;
import com.titicolab.nanux.graphics.draw.Image;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by campino on 15/11/2016.
 *
 */

@RunWith(AndroidJUnit4.class)
public class ImageDrawerTest  extends TextureManagerTestCase {


    private DrawerImage imageDrawer;
    private ProjectionUi projection;
    private FixList<Image> list;
    private ImageShaderProgram shader;



    @Override
    public void onSurfaceCreated(GameContext game, GPUInfo eglConfig) {
        super.onSurfaceCreated(game,eglConfig);


        projection= new ProjectionUi(game.getDisplayInfo());
        projection.setViewport(1280,720,ProjectionUi.SCALE_HEIGHT);


        shader = getMockProgram();
        shader.buildProgram();

    }


    @Override
    public void onDrawFrame() {
        super.onDrawFrame();


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

        Texture texture = mTextureManager.getTexture(R.drawable.test_square_256);
        FixList<Image> listImages = new FixList<>(N);

        for (int i = 0; i < listImages.capacity(); i++) {
            Image image = new Image(texture);
            image.setSize(size,size);
            image.setPosition(size/2+size*i,size);
            listImages.add(image);
        }

        imageDrawer = new DrawerImage(N,shader);
        list = listImages;
        waitTouchSeconds(60*10);
    }



    private ImageShaderProgram getMockProgram() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        String vertexSh =  TextResourceReader.readTextFileFromResource(
                appContext, R.raw.image_vertex);
        String fragmentSh = TextResourceReader.readTextFileFromResource(
                appContext, R.raw.image_fragment);
        return  new ImageShaderProgram(vertexSh,fragmentSh);
    }

}
