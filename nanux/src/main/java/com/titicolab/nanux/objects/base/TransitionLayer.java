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

package com.titicolab.nanux.objects.base;

import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.graphics.draw.Geometry;
import com.titicolab.nanux.graphics.model.RectModel;

/**
 * Created by campino on 20/12/2017.
 *
 */

public class TransitionLayer extends SceneLayer implements Transition.TransitionTrigger{

    Geometry mRectFade;
    MachineTransition mMsf;
    private static final int RED = 0;
    private static final int GREEN = 1;
    private static final int BLUE = 2;
    private static final int ALPHA = 3;

    float[] color = new float[]{1,1,1,1};

    @Override
    protected void onGroupObjectsCreated() {
        mRectFade = new Geometry(new RectModel(getDisplayInfo().getScalePixel(),
                true));
        mRectFade.setSize(getCameraUi().getViewPortWidth(),
                getCameraUi().getViewPortHeight());
        mRectFade.setPosition(getCameraUi().getX(), getCameraUi().getY());
        mMsf = new MachineTransition();

        if(Transition.class.isAssignableFrom(getScene().getClass())){
            color = ((Transition)getScene()).getColor();
        }
    }

    @Override
    protected void updateRender() {
        mRectFade.updateRender();
    }

    @Override
    public void in(Transition.OnFullIn listener) {
        mMsf.in(listener);
    }

    @Override
    public void out(Transition.OnFullOut listener) {
        mMsf.out(listener);
    }


    @Override
    protected void updateLogic(){
        mMsf.updateLogic();
    }

    public void setColor(int r, int g, int b, int a) {
        color[RED] = r;
        color[GREEN] = g;
        color[BLUE] = b;
        color[ALPHA] = a;
    }

    public void setFramesInOut(long in, long out){
        mMsf.setFramesInOut(in,out);
    }


    public static class Fade extends TransitionLayer{
        private float alphaFactor= 0.0f;

        @Override
        protected void onGroupObjectsCreated() {
            super.onGroupObjectsCreated();
        }

        @Override
        protected void updateLogic() {
            super.updateLogic();

            if (mMsf.isUpdatingIn()) {
                alphaFactor = mMsf.getProgress();
            } else if (mMsf.isUpdatingOut()) {
                alphaFactor =  1 - mMsf.getProgress();
            } else if (mMsf.isFullIn()) {
                alphaFactor = 1;
            } else if (mMsf.isFullOut()) {
                alphaFactor = 0.0f;
            }
        }

        @Override
        public void onDraw(DrawTools drawer) {
            drawer.geometry.setColor(color[RED], color[GREEN], color[BLUE], alphaFactor * color[ALPHA]);
            drawer.geometry.begin(getCameraUi().getMatrix());
            drawer.geometry.add(mRectFade);
            drawer.geometry.end();
        }


    }


    public static class Sliding extends TransitionLayer{

        private float factor;
        private float xFull;
        private int xOffset;



        private boolean back;

        @Override
        protected void onGroupObjectsCreated() {
            super.onGroupObjectsCreated();
            xFull =getCameraUi().getViewPortWidth();
            xOffset = -getCameraUi().getViewPortWidth()/2;
            factor = 0.0f;
            mMsf.setFramesInOut(20,20);
            back = true;
        }

        @Override
        protected void updateLogic() {
            super.updateLogic();

            if (mMsf.isUpdatingIn()) {
                factor = mMsf.getProgress();
            } else if (mMsf.isUpdatingOut()) {
                factor = back? 1 - mMsf.getProgress(): 1+mMsf.getProgress();
            } else if (mMsf.isFullIn()) {
                factor = 1;
            } else if (mMsf.isFullOut()) {
                factor = 0.0f;
            }

            mRectFade.setPosition(xFull*factor + xOffset,getCameraUi().getY());
        }

        @Override
        public void onDraw(DrawTools drawer) {
            drawer.geometry.setColor(color[RED], color[GREEN], color[BLUE],color[ALPHA]);
            drawer.geometry.begin(getCameraUi().getMatrix());
            drawer.geometry.add(mRectFade);
            drawer.geometry.end();
        }

        public void setBack(boolean back) {
            this.back = back;
        }

    }
}