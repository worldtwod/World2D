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

package com.titicolab.puppet.objects.base;


import com.titicolab.puppet.animation.Animation;
import com.titicolab.puppet.animation.AnimationBuilder;
import com.titicolab.puppet.draw.DrawTools;

/**
 * Created by campino on 12/06/2016.
 *
 */
public class Transition extends Scene {

    private static final int STATUS_FINISHED = 0;
    private static final int STATUS_IN       =  1;
    private static final int STATUS_FULL     = 2;
    private static final int STATUS_OUT      = 4;
    private static final int STATUS_WAITING_OUT      = 3;


    private static final float TRANSITION_DEFAULT_TIME_IN = 1000;

    private int mStatus;
    private Fade mFade;
    private float mTimeTransition;
    private float mTimeCurrent;
    private OnTransitionIn mListenerIn;
    private OnTransitionOut mListenerOut;

    private float r=0;
    private float g=1;
    private float b=0;




    public  interface OnTransitionIn{
        void onFullIn();
    }

    public  interface OnTransitionOut{
        void onFullOut();
    }

    public Transition(){
        super();
    }

    public void in(OnTransitionIn listener){
        mListenerIn = listener;
        mStatus = STATUS_IN;
        mTimeCurrent = 0;
        mFade.setColor(r,g,b,0);
    }

    public void out(OnTransitionOut listener){
        mListenerOut=listener;
        mStatus = STATUS_OUT;
    }

    public void setColor(float r, float g, float b){
        this.r=r;
        this.g=g;
        this.b=b;
    }


    @Override
    public void onGroupLayersCreated() {

    }




   /* @Override
    public GameObjectRequest onObjectRequest() {
        return new GameObjectRequest()
                .uiObject(Fade.class,1);
    }*/


    /*@Override
    public void onObjectsCreated() {
        //super.onSceneObjectCreated(gameTool);
        mFade = (Fade) getUiObject(Fade.class);
        CameraUi cameraUi = getCameraUi();
        cameraUi.setViewport(1280,720, ProjectionUi.SCREEN_EXPAND);
        cameraUi.setPosition(1280/2,720/2);
        mFade.setSizeFade(1280,720);
        mFade.setColor(r,g,b,0);
        mFade.getAnimation().playByIndex(0);
        mFade.setPosition(1280/2,720/2);
        mStatus = STATUS_FINISHED;
        mTimeTransition= TRANSITION_DEFAULT_TIME_IN;
        mTimeCurrent = 0;
    }*/



    @Override
    public void updateLogic() {

        /*synchronized (this) {
            if (mStatus == STATUS_IN) {
                updateIn(15);
            } else if (mStatus == STATUS_FULL) {
                updateFull();
            } else if (mStatus == STATUS_WAITING_OUT) {
                // waiting command of go out
                mFade.setColor(r, g, b, 1);
            } else if (mStatus == STATUS_OUT) {
                updateOut(15);
            }
        }*/
    }

    @Override
    public void updateRender() {
       // mFade.updateRender();
    }


    @Override
    public void onDraw(DrawTools drawer) {
       // drawer.ui.setProjection(getCameraUi().getProjection());
       // drawer.ui.begin();
       // drawer.ui.add(mFade.getImage());
       // drawer.ui.end();
    }




    private void updateIn(float averageTimeStep) {
        float alpha = mTimeCurrent/mTimeTransition;
        mFade.setColor(r,g,b,alpha);
        //updateFrame the mStatus
        if(alpha<1 && mTimeCurrent < mTimeTransition)
            mTimeCurrent+=  averageTimeStep;
        else{
            mStatus = STATUS_FULL;
        }
    }

    private void updateFull() {
        mTimeCurrent = mTimeTransition;
        mStatus = STATUS_WAITING_OUT;
        if(mListenerIn !=null)
            mListenerIn.onFullIn();
    }

    private void updateOut(float averageTimeStep) {
        float alpha = mTimeCurrent/mTimeTransition;
        mFade.setColor(r,g,b,alpha);
        //updateFrame the mStatus
        if(mTimeCurrent > 0  && alpha>0)
            mTimeCurrent-=  averageTimeStep;
        else{
            if(mListenerOut !=null)
                mListenerOut.onFullOut();
            mTimeCurrent = 0;
            mStatus = STATUS_FINISHED;
        }
    }




    public static class Fade extends UiObject {
         int widthFade;
         int heightFade;

        @Override
        public Animation onBuildClips(AnimationBuilder builder) {
            //TODO
            return super.onBuildClips(builder);
        }

        @Override
        public void onAttachAnimation(Animation sequence) {
            super.onAttachAnimation(sequence);
        }

        @Override
        public boolean hasCustomClips() {
            return false;
        }

        @Override
        public void onCreated() {
            super.onCreated();
        }

        void setSizeFade(int width, int height){
            this.widthFade =width;
            this.heightFade =height;
        }

        @Override
        public void updateRender() {
            getImage().setUvCoordinates(getAnimator().getCurrentFrame());
            getImage().setSize(widthFade, heightFade);
            getAnimator().updateFrame();
            getImage().updateRender();
        }



    }


}
