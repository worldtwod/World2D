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

import com.titicolab.mock.R;
import com.titicolab.mock.cases.opengl.TextureManagerTestCase;
import com.titicolab.nanux.animation.GameContext;
import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.nanux.graphics.textures.Texture;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.DrawerImage;
import com.titicolab.opengl.shader.ImageShaderProgram;
import com.titicolab.opengl.util.TextResourceReader;
import com.titicolab.puppet.draw.Image;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by campino on 15/11/2016.
 *
 */

@RunWith(AndroidJUnit4.class)
public class ImageDrawerTest  extends TextureManagerTestCase {

    final int IMAGE_LENGTH=5;
    private DrawerImage imageDrawer;
    private Image[] imageArray;
    private float imageWidth;
    private ProjectionUi projection;


    @Override
    public void onSurfaceCreated(GameContext game, GPUInfo eglConfig) {
        super.onSurfaceCreated(game,eglConfig);


        projection= new ProjectionUi(game.getDisplayInfo());
        projection.setViewport(1280,720,ProjectionUi.SCALE_HEIGHT);


        ImageShaderProgram shader = getMockProgram();
        shader.buildProgram();

        imageDrawer = new DrawerImage(IMAGE_LENGTH,shader);


        imageArray = new Image[IMAGE_LENGTH];
        imageWidth = game.getDisplayInfo().getReferenceWidth()/IMAGE_LENGTH;
        int i;

        for (i = 0; i < imageArray.length; i++) {
            Texture texture = mTextureManager.getTexture(R.drawable.test_square_256);
            imageArray[i] = new Image(texture);
            imageArray[i].setSize((int)imageWidth,(int)imageWidth);
        }
    }


    @Override
    public void onDrawFrame() {
        super.onDrawFrame();
        float y=0;
        for (int i = 0; i < imageArray.length; i++) {
            float x = imageWidth / 2 + i * imageWidth;
            y+= imageWidth/2;
            imageArray[i].setPosition(x,y);
            imageArray[i].updateRender();
        }

        imageDrawer.begin(projection.getMatrix());
        for (Image anImageArray : imageArray)
            imageDrawer.add(anImageArray);
        imageDrawer.end();
    }



    @Test
    public void testAnimation(){
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
