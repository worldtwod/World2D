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
import com.titicolab.nanux.core.GameContext;
import com.titicolab.nanux.core.RunnableTask;
import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.DrawerImage;
import com.titicolab.nanux.graphics.draw.Image;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by campino on 15/11/2016.
 *
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class ProjectionOthTest extends ImageDrawerTestCase {

    private Image imagePoint0;
    private Image imagePoint1;

    private ProjectionUi projectionCircle;


    @Override
    public void onSurfaceCreated(final GameContext game,GPUInfo eglConfig) {
        super.onSurfaceCreated(game, eglConfig);
       injectImages();
    }

    private void injectImages() {

        projectionCircle = new ProjectionUi(getDisplayInfo());
        imagePoint0 = new Image(getTextureManager()
                .getTexture(R.drawable.test_cricle_720));
        imagePoint0.setScale(0.1f);
        imagePoint0.setColor(1,0,0,1);

        imagePoint1 = new Image(getTextureManager().
                getTexture(R.drawable.test_cricle_720));
    }





    @Override
    protected void onDrawImage(DrawerImage imageDrawer) {
        imagePoint0.updateRender();
        imagePoint1.updateRender();

        imageDrawer.begin(projectionCircle.getMatrix());
        imageDrawer.add(imagePoint0);
        imageDrawer.add(imagePoint1);
        imageDrawer.end();
    }


    @Test
    public void aTestRatio(){
        log.debug("projectionCircle expands to reference view port");


        getRunnerTask().runAndWait(new RunnableTask() {
            @Override
            public void run() {
                //projectionCircle.setViewport(ProjectionUi.SCALE_HEIGHT);
                //projectionCircle.setViewport(ProjectionUi.SCALE_WIDTH);
                //projectionCircle.setScale(1.5f);
                projectionCircle.setViewport(1280,720,ProjectionUi.SCALE_WIDTH);
                projectionCircle.setPosition(
                        projectionCircle.getViewPortWidth(),
                        projectionCircle.getViewPortHeight()/2);

                imagePoint1.setPosition(
                        projectionCircle.getViewPortWidth()/2,
                        projectionCircle.getViewPortHeight()/2);
                imagePoint0.setPosition(0,0);

            }
        });



        assertThat(waitTouchSeconds(60*10),is(true));
    }

}
