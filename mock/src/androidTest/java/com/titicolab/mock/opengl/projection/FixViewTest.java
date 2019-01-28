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
import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.graphics.draw.Image;
import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.puppeteer.GraphicActivity;

import org.junit.Rule;
import org.junit.Test;

/**
 * Created by campino on 15/11/2016.
 *
 */

public class FixViewTest implements ObserverGraphicContext.DrawFrame { //} extends ImageDrawerTestCase {

    @Rule
    public GraphicTestRule graphicRule = new GraphicTestRule.Builder()
            .setObserverDrawFrame(this)
            .build();

    private Image imageCircle;
    private ProjectionUi projectionCircle;


    @Override
    public void onDrawFrame(DrawTools drawTools) {

        if(projectionCircle==null)return;

        imageCircle.updateRender();
        drawTools.images.begin(projectionCircle.getMatrix());
        drawTools.images.add(imageCircle);
        drawTools.images.end();
    }



    @Test
    public void aTestRatio(){
        graphicRule.log.debug("projectionCircle expands to reference view port");

        imageCircle = new Image(graphicRule.getTextureManager()
                .getTexture(R.drawable.test_cricle_720));

        imageCircle.setPosition(1280/2,720/2);

        final GraphicActivity activity = graphicRule.getActivity();
        /*activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.getGLGameView().getHolder()
                        .setFixedSize(
                                graphicRule.getDisplayInfo().getFixWidth(),
                                graphicRule.getDisplayInfo().getFixHeight());
            }
        });*/

        ProjectionUi projection = new ProjectionUi(graphicRule.getDisplayInfo());
        projection.setViewport(1280,720,ProjectionUi.SCALE_WIDTH);
        projectionCircle = projection;

        graphicRule.waitTouchSeconds(20);

    }

}
