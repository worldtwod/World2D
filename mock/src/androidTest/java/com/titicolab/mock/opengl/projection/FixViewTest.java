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

import android.support.test.runner.AndroidJUnit4;

import com.titicolab.android.engine.AndroidGame;
import com.titicolab.mock.R;
import com.titicolab.mock.cases.opengl.ImageDrawerTestCase;
import com.titicolab.nanux.animation.GameContext;
import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.DrawerImage;
import com.titicolab.puppet.draw.Image;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 * Created by campino on 15/11/2016.
 *
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class FixViewTest extends ImageDrawerTestCase {


    private Image imageCircle;
    private ProjectionUi projectionCircle;


    @Override
    public void onSurfaceCreated(GameContext game, GPUInfo eglConfig) {
        super.onSurfaceCreated(game, eglConfig);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        super.onSurfaceChanged(width,height);
    }



    @Override
    protected void onDrawImage(DrawerImage imageDrawer) {

        if(projectionCircle==null)return;

        imageCircle.updateRender();
        imageDrawer.begin(projectionCircle.getMatrix());
        imageDrawer.add(imageCircle);
        imageDrawer.end();
    }


    @Test
    public void aTestRatio(){
        log.debug("projectionCircle expands to reference view port");

        imageCircle = new Image(mTextureManager
                .getTexture(R.drawable.test_cricle_720));

        imageCircle.setPosition(
                1280/2,
                720/2);




        mRenderRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((AndroidGame)mGameContext).getGLGameView().getHolder()
                        .setFixedSize(
                                mGameContext.getDisplayInfo().getFixWidth(),
                                mGameContext.getDisplayInfo().getFixHeight());
            }
        });


        ProjectionUi projection = new ProjectionUi(mGameContext.getDisplayInfo());
        //projection.setViewport(1280,720,ProjectionUi.SCALE_HEIGHT);

        projectionCircle = projection;

       waitTouchSeconds(60*5);

    }

}
