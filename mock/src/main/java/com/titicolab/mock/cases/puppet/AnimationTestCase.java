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

package com.titicolab.mock.cases.puppet;

import android.support.annotation.CallSuper;
import android.support.test.runner.AndroidJUnit4;

import com.titicolab.mock.cases.opengl.ImageDrawerTestCase;
import com.titicolab.nanux.animation.GameContext;
import com.titicolab.nanux.animation.RunnableTask;
import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.DrawerImage;
import com.titicolab.puppet.objects.base.Animated;
import com.titicolab.puppet.objects.base.HelperObjects;

import org.junit.runner.RunWith;

/**
 * Created by campino on 15/11/2016.
 *
 */
@RunWith(AndroidJUnit4.class)
public class AnimationTestCase extends ImageDrawerTestCase{

    private FlexibleList<Animated> mAnimatedObjects;
    private ProjectionUi projection;


    @CallSuper
    @Override
    public void onSurfaceCreated(GameContext game, GPUInfo eglConfig) {
        super.onSurfaceCreated(game, eglConfig);
        mAnimatedObjects = new FlexibleList<>(100);
        projection= new ProjectionUi(getDisplayInfo());
        projection.setViewport(1280,720, ProjectionUi.SCALE_HEIGHT);
    }

    @Override
    protected void onDrawImage(DrawerImage imageDrawer) {
        updateForRender();
        drawImages(imageDrawer);
    }


    private void updateForRender() {
        HelperObjects.updateRenderObjects(mAnimatedObjects);
    }

    synchronized private void drawImages(DrawerImage imageDrawer){
        imageDrawer.begin(projection.getMatrix());
        HelperObjects.drawGameObjects(imageDrawer,mAnimatedObjects);
        imageDrawer.end();
    }

    protected void addAnimated(final Animated animated){
        getRunnerTask().queueTask(new RunnableTask() {
            @Override
            public void run() {
                mAnimatedObjects.add(animated);
            }
        });
    }

    private void clear() {
        getRunnerTask().queueTask(new RunnableTask() {
            @Override
            public void run() {
                mAnimatedObjects = new FlexibleList<>(10);
            }
        });
    }

}
