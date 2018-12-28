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

import androidx.annotation.CallSuper;
import androidx.test.runner.AndroidJUnit4;

import com.titicolab.mock.cases.opengl.ImageDrawerTestCase;
import com.titicolab.nanux.core.GameContext;
import com.titicolab.nanux.core.RunnableTask;
import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.opengl.shader.DrawerImage;
import com.titicolab.nanux.animation.Clip;
import com.titicolab.nanux.animation.Frame;
import com.titicolab.nanux.graphics.draw.Image;

import org.junit.runner.RunWith;

/**
 * Created by campino on 15/11/2016.
 *
 */
@RunWith(AndroidJUnit4.class)
public class ClipTestCase extends ImageDrawerTestCase{

    private FlexibleList<Image> mImagesTest;
    private ProjectionUi projection;
    private float scale;


    @CallSuper
    @Override
    public void onSurfaceCreated(GameContext game, GPUInfo eglConfig) {
        super.onSurfaceCreated(game, eglConfig);
        mImagesTest = new FlexibleList<>(100);
        projection= new ProjectionUi(getDisplayInfo());
        projection.setViewport(1280,720, ProjectionUi.SCALE_HEIGHT);
    }

    @Override
    protected void onDrawImage(DrawerImage imageDrawer) {
        updateImagesForRender();
        drawImages(imageDrawer);
    }


    protected void showClip(Clip clip){
        clear();
        Frame tem = clip.get(0);
        Image imageBackground = new Image(getTextureManager()
                .getTexture(tem.getTexture().getResources()));
        imageBackground.setPositionLeftTop((projection.getViewPortWidth()-imageBackground.getWidth())/2
                , projection.getViewPortHeight());


        Image image=null;
        for (int i = 0; i < clip.size() ; i++) {


            imageBackground.setColor(1,1,1,0.2f);
            addImage(imageBackground);

            Frame frame = clip.get(i);
            image = new Image(frame);
            //image.setColor(0,1,0,1);
            image.setPosition((projection.getViewPortWidth()-image.getWidth())/2,
                    (projection.getViewPortHeight()-image.getHeight())/2);
            image.setScale(scale);
            addImage(image);

            waitTouchSeconds(1);
        }


        assert image != null;

        waitTouchSeconds(60);
    }

    private void updateImagesForRender() {
        int size = mImagesTest.size();
        for (int i = 0; i < size; i++) {
            mImagesTest.get(i).updateRender();
        }
    }

    synchronized private void drawImages(DrawerImage imageDrawer){
        int size = mImagesTest.size();
        imageDrawer.begin(projection.getMatrix());
        for (int i = 0; i < size; i++) {
            imageDrawer.add(mImagesTest.get(i));
        }
        imageDrawer.end();
    }

    private void addImage(final Image image){
        getRunnerTask().queueTask(new RunnableTask() {
            @Override
            public void run() {
                mImagesTest.add(image);
            }
        });
    }

    private void clear() {
        getRunnerTask().queueTask(new RunnableTask() {
            @Override
            public void run() {
                mImagesTest = new FlexibleList<>(10);
            }
        });
    }


    public void setScale(float scale) {
        this.scale = scale;
    }
}
