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

package com.titicolab.mock.opengl.projection;

import com.titicolab.mock.R;
import com.titicolab.mock.rule.GraphicTestRule;
import com.titicolab.mock.rule.ObserverGraphicContext;
import com.titicolab.nanux.core.GraphicContext;
import com.titicolab.nanux.core.RunnableTask;
import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.graphics.draw.Image;
import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.DrawerImage;

import org.junit.Rule;
import org.junit.Test;

/**
 * Created by campino on 15/11/2016.
 *
 */

public class RatioProjectionTest  implements ObserverGraphicContext.DrawFrame,
        ObserverGraphicContext.SurfaceCreated {

    @Rule
    public GraphicTestRule graphicRule = new GraphicTestRule.Builder()
            .setObserverDrawFrame(this)
            .setObserverSurfaceCreated(this)
            .build();

    private ProjectionUi projectionCircle;
    private ProjectionUi projectionRectangle;
    private Image imageCircle;
    private Image imageViewPort;


    @Override
    public void onSurfaceCreated(GraphicContext game, GPUInfo eglConfig) {
        injectMocks();
    }

    @Override
    public void onDrawFrame(DrawTools drawTools) {


        imageCircle.updateRender();
        imageViewPort.updateRender();

        //imageDrawer.setProjection(projectionCircle);
        DrawerImage imageDrawer = (DrawerImage) drawTools.images;
        imageDrawer.begin(projectionCircle.getMatrix());
        imageDrawer.add(imageCircle);
        imageDrawer.end();

        //imageDrawer.setProjection(projectionRectangle);
        imageDrawer.begin(projectionRectangle.getMatrix());
        imageDrawer.add(imageViewPort);
        imageDrawer.end();
    }


    @Test
    public void projectionCircleRatioTest(){

        graphicRule.log.debug("projectionCircle expands to reference view port");

        graphicRule.waitTouchSeconds(20);

        graphicRule.getRunnerTask().runAndWait(new RunnableTask() {
            @Override
            public void run() {
                imageCircle.setColor(1,0,0,1);
                projectionCircle.setViewport(1280,720,ProjectionUi.SCALE_HEIGHT);
            }
        });

        graphicRule.log.debug("projectionCircle match to width reference view port");
        graphicRule.waitTouchSeconds(20);

        graphicRule.getRunnerTask().runAndWait(new RunnableTask() {
            @Override
            public void run() {
                imageCircle.setColor(0,1,0,1);
                projectionCircle.setViewport(1280,720,ProjectionUi.SCALE_WIDTH);
            }
        });

        graphicRule.log.debug("projectionCircle match to height reference view port");
        graphicRule.waitTouchSeconds(20);

    }

    private void injectMocks() {
        projectionCircle = new ProjectionUi(graphicRule.getDisplayInfo());
        projectionRectangle = new ProjectionUi(graphicRule.getDisplayInfo());
        imageCircle = new Image(graphicRule.getTextureManager()
                .getTexture(R.drawable.test_cricle_720));

        imageCircle.setPosition(
                graphicRule.getDisplayInfo().getReferenceWidth()/2,
                graphicRule.getDisplayInfo().getReferenceHeight()/2);
        imageViewPort = new Image(graphicRule.getTextureManager().
                getTexture(R.drawable.test_bg_1280x720));
        imageViewPort.setPositionLeftTop(0,graphicRule.getDisplayInfo().getReferenceHeight());
    }


}
