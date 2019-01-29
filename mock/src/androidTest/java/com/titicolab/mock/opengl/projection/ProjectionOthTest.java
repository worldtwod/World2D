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

public class ProjectionOthTest implements ObserverGraphicContext.DrawFrame,
        ObserverGraphicContext.SurfaceCreated {

    @Rule
    public GraphicTestRule graphicRule = new GraphicTestRule.Builder()
            .setObserverDrawFrame(this)
            .setObserverSurfaceCreated(this)
            .build();

    private Image imagePoint0;
    private Image imagePoint1;
    private ProjectionUi projectionCircle;

    @Override
    public void onSurfaceCreated(final GraphicContext game, GPUInfo eglConfig) {
       injectImages();
    }

    private void injectImages() {
        projectionCircle = new ProjectionUi(graphicRule.getDisplayInfo());
        imagePoint0 = new Image(graphicRule.getTextureManager()
                .getTexture(R.drawable.test_cricle_720));
        imagePoint0.setColor(1,0,0,1);
        imagePoint1 = new Image(graphicRule.getTextureManager().
                getTexture(R.drawable.test_cricle_720));
    }

    @Override
    public void onDrawFrame(DrawTools drawer) {
        DrawerImage imageDrawer = (DrawerImage) drawer.images;
        imagePoint0.updateRender();
        imagePoint1.updateRender();
        imageDrawer.begin(projectionCircle.getMatrix());
        imageDrawer.add(imagePoint0);
        imageDrawer.add(imagePoint1);
        imageDrawer.end();
    }


    @Test
    public void aTestRatio(){
        graphicRule.log.debug("projectionCircle expands to reference view port, check perfects circles");
        imagePoint0.setScale(0.5f);

        graphicRule.getRunnerTask().runAndWait(new RunnableTask() {
            @Override
            public void run() {
                projectionCircle.setViewport(1280,720,ProjectionUi.SCALE_WIDTH);
                projectionCircle.setPosition(
                        projectionCircle.getViewPortWidth()/2,
                        projectionCircle.getViewPortHeight()/2);

                imagePoint1.setPosition(
                        projectionCircle.getViewPortWidth()/2,
                        projectionCircle.getViewPortHeight()/2);
                imagePoint0.setPosition(0,0);

            }
        });
       graphicRule.waitTouchSeconds(20);
    }
}
