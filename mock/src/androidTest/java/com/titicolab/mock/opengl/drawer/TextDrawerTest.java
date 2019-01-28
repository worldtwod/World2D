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
import com.titicolab.nanux.animation.GridFrame;
import com.titicolab.nanux.core.GraphicContext;
import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.nanux.graphics.texture.Texture;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.DrawerText;
import com.titicolab.opengl.shader.ImageShaderProgram;
import com.titicolab.opengl.util.TextResourceReader;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.platform.app.InstrumentationRegistry;


/**
 * Created by campino on 15/11/2016.
 * The way that A TextDrawerTest work
 *
 */
public class TextDrawerTest implements ObserverGraphicContext.SurfaceCreated,
        ObserverGraphicContext.DrawFrame {

    @Rule
    public GraphicTestRule graphicRule = new GraphicTestRule.Builder()
            .setObserverSurfaceCreated(this)
            .setObserverDrawFrame(this)
            .build();

    private DrawerText imageDrawer;
    private ProjectionUi projection;
    private String textToPrint;


    @Override
    public void onSurfaceCreated(GraphicContext game, GPUInfo eglConfig) {

        projection= new ProjectionUi(game.getDisplayInfo());
        projection.setViewport(1280,720,ProjectionUi.SCALE_HEIGHT);
        Texture textureFont = graphicRule.getTextureManager().getTexture(R.drawable.lucida_console);

        GridFrame gridFrame = new GridFrame(textureFont);
        gridFrame.setGridShape(DrawerText.FONT_COLUMNS,DrawerText.FONT_ROWS)
                 .setGridSize(512,512)
                 .createFrames();

        ImageShaderProgram shader = getMockProgram();
        shader.buildProgram();

        imageDrawer = new DrawerText(gridFrame, shader);
        imageDrawer.setMatrix(projection.getMatrix());
    }


    @Override
    public void onDrawFrame(DrawTools drawTools) {
        float left = projection.getViewPortWidth()/3;
        float top = projection.getViewPortHeight()/2;
        if(textToPrint==null)
            return;
        imageDrawer.print(textToPrint,left,top,40);
    }

    private ImageShaderProgram getMockProgram() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String vertexSh =  TextResourceReader.readTextFileFromResource(
                appContext, R.raw.image_vertex);
        String fragmentSh = TextResourceReader.readTextFileFromResource(
                appContext, R.raw.image_fragment);
        return  new ImageShaderProgram(vertexSh,fragmentSh);
    }


    /**
     * This is a assisted test, check in the screen that appear Hello World2D and End
     */
    @Test
    public void testAnimation(){
        textToPrint = "Hello World2D";
        graphicRule.waitTouchSeconds(2);
        textToPrint = "End";
        graphicRule.waitTouchSeconds(2);
    }

}
