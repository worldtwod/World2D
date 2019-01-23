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

import androidx.test.runner.AndroidJUnit4;

import com.titicolab.mock.R;
import com.titicolab.mock.cases.opengl.ImageDrawerTestCase;
import com.titicolab.nanux.core.GraphicContext;
import com.titicolab.nanux.core.RunnableTask;
import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.DrawerImage;
import com.titicolab.nanux.graphics.draw.Image;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by campino on 15/11/2016.
 *
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class RatioProjectionTest extends ImageDrawerTestCase {


    private ProjectionUi projectionCircle;
    private ProjectionUi projectionRectangle;
    private Image imageCircle;
    private Image imageViewPort;


    @Override
    public void onSurfaceCreated(GraphicContext game, GPUInfo eglConfig) {
        super.onSurfaceCreated(game, eglConfig);
        injectMocks();
    }

    @Override
    protected void onDrawImage(DrawerImage imageDrawer) {

        imageCircle.updateRender();
        imageViewPort.updateRender();

        //imageDrawer.setProjection(projectionCircle);
        imageDrawer.begin(projectionCircle.getMatrix());
        imageDrawer.add(imageCircle);
        imageDrawer.end();

        //imageDrawer.setProjection(projectionRectangle);
        imageDrawer.begin(projectionRectangle.getMatrix());
        imageDrawer.add(imageViewPort);
        imageDrawer.end();
    }


    @Test
    public void aTestRatio(){

        log.debug("projectionCircle expands to reference view port");

        assertThat(waitTouchSeconds(60*10),is(true));

        getRunnerTask().runAndWait(new RunnableTask() {
            @Override
            public void run() {
                imageCircle.setColor(1,0,0,1);
                projectionCircle.setViewport(1280,720,ProjectionUi.SCALE_HEIGHT);
            }
        });

        log.debug("projectionCircle match to width reference view port");
        assertThat(waitTouchSeconds(60*10),is(true));

        getRunnerTask().runAndWait(new RunnableTask() {
            @Override
            public void run() {
                imageCircle.setColor(0,1,0,1);
                projectionCircle.setViewport(1280,720,ProjectionUi.SCALE_WIDTH);
            }
        });

        log.debug("projectionCircle match to height reference view port");
        assertThat(waitTouchSeconds(60*10),is(true));
    }

    private void injectMocks() {
        projectionCircle = new ProjectionUi(mGraphicContext.getDisplayInfo());
        projectionRectangle = new ProjectionUi(mGraphicContext.getDisplayInfo());
        imageCircle = new Image(mTextureManager
                .getTexture(R.drawable.test_cricle_720));

        imageCircle.setPosition(
                getDisplayInfo().getReferenceWidth()/2,
                getDisplayInfo().getReferenceHeight()/2);
        imageViewPort = new Image(getTextureManager().
                getTexture(R.drawable.test_bg_1280x720));
        imageViewPort.setPositionLeftTop(0,getDisplayInfo().getReferenceHeight());
    }

}
