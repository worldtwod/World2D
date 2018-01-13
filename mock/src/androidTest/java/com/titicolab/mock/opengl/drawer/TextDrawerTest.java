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
import com.titicolab.nanux.core.GameContext;
import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.nanux.graphics.texture.Texture;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.DrawerText;
import com.titicolab.opengl.shader.ImageShaderProgram;
import com.titicolab.opengl.util.TextResourceReader;
import com.titicolab.nanux.animation.GridFrame;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by campino on 15/11/2016.
 *
 */

@RunWith(AndroidJUnit4.class)
public class TextDrawerTest extends TextureManagerTestCase {


    private DrawerText imageDrawer;
    private ProjectionUi projection;
    private ImageShaderProgram shader;
    private String textToPrint;


    @Override
    public void onSurfaceCreated(GameContext game, GPUInfo eglConfig) {
        super.onSurfaceCreated(game,eglConfig);


        projection= new ProjectionUi(game.getDisplayInfo());
        projection.setViewport(1280,720,ProjectionUi.SCALE_HEIGHT);

        Texture textureFont = mTextureManager.getTexture(R.drawable.lucida_console);
        GridFrame gridFrame = new GridFrame(textureFont);
        gridFrame.setGridShape(DrawerText.FONT_COLUMNS,DrawerText.FONT_ROWS)
                 .setGridSize(512,512)
                 .createFrames();

        shader = getMockProgram();
        shader.buildProgram();

        imageDrawer = new DrawerText(gridFrame,shader);
        imageDrawer.setMatrix(projection.getMatrix());
    }


    @Override
    public void onDrawFrame() {
        super.onDrawFrame();
        float left = projection.getViewPortWidth()/3;
        float top = projection.getViewPortHeight()/2;

        if(textToPrint==null)
            return;

        imageDrawer.print(textToPrint,left,top,40);
    }



    @Test
    public void testAnimation(){





        textToPrint = "Hola mundo 0\nHola mundo 1\n fin";
        waitTouchSeconds(60*60);

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
