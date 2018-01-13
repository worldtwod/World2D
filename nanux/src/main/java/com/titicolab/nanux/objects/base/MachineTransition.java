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

/**
 * Created by campino on 20/12/2017.
 *
 */

public class MachineTransition {

    private static final int STATUS_IDLE = 0;
    private static final int STATUS_UPDATING_IN = 1;
    private static final int STATUS_FULL_IN = 2;
    private static final int STATUS_UPDATING_OUT = 4;
    private static final int STATUS_FULL_OUT = 5;

    private static final float TRANSITION_DEFAULT_FRAMES_IN = 60;
    private static final float TRANSITION_DEFAULT_FRAMES_OUT = 60*2;

    private float mProgress;

    private Transition.OnFullIn mListenerFullIn;
    private Transition.OnFullOut mListenerFullOut;



    public  interface StatusTransitionListener {
        void onFullIn();
        void onFullOut();
    }



    private int   mStatus;
    private float mFramesIn;
    private float mFrameOut;
    private float mFramesCurrent;


    public MachineTransition() {
        mFramesIn = TRANSITION_DEFAULT_FRAMES_IN;
        mFrameOut = TRANSITION_DEFAULT_FRAMES_OUT;
        mStatus = STATUS_IDLE;
       // mStatusTransition = statusTransition;
    }

    public void setFramesInOut(long in, long out){
        mFramesIn = in;
        mFrameOut = out;
    }

    public float getProgress() {
        return mProgress;
    }

    float getFramesIn() {
        return mFramesIn;
    }

    float getFramesOut() {
        return mFrameOut;
    }

    public void reset(){
        mStatus = STATUS_IDLE;
        mFramesCurrent = 0;
        mProgress = 0;
    }


    public void in(Transition.OnFullIn listener){
        mListenerFullIn = listener;
        reset();
        mStatus = STATUS_UPDATING_IN;
    }

    public void out(Transition.OnFullOut listener){
        mListenerFullOut = listener;
        mFramesCurrent = 0;
        mStatus = STATUS_UPDATING_OUT;
        mProgress = 0;
    }


    public void updateLogic() {

        synchronized (this) {
            if (mStatus == STATUS_IDLE) {
                mStatus=STATUS_IDLE;
            } else if (mStatus == STATUS_UPDATING_IN) {
                updateIn();
            } else if (mStatus == STATUS_FULL_IN) {
                updateFullIn();
            } else if (mStatus == STATUS_UPDATING_OUT) {
                updateOut();
            } else if (mStatus == STATUS_FULL_OUT) {
                updateFullOut();
            }
        }
    }

    private void updateIn() {
        if(mFramesCurrent < mFramesIn) {
            mFramesCurrent++;
            mProgress = mFramesCurrent/mFramesIn;
        } else{
            if(mListenerFullIn !=null)
                mListenerFullIn.onFullIn();
                mStatus = STATUS_FULL_IN;
        }
    }

    private void updateFullIn() {
        mFramesCurrent = mFramesIn;
        mProgress=1;
    }

    private void updateFullOut() {
        mFramesCurrent = mFrameOut;
        mProgress=1;
    }

    private void updateOut() {
        if(mFramesCurrent < mFrameOut) {
            mFramesCurrent ++;
            mProgress = mFramesCurrent/mFrameOut;
        }else{
            if(mListenerFullOut !=null)
                mListenerFullOut.onFullOut();
            mStatus = STATUS_FULL_OUT;
        }
    }

    public boolean isFullIn(){
        return mStatus== STATUS_FULL_IN;
    }


    public boolean isFullOut(){
        return mStatus== STATUS_FULL_OUT;
    }

    public boolean isUpdatingIn(){
        return mStatus== STATUS_UPDATING_IN;
    }

    public boolean isUpdatingOut(){
        return mStatus== STATUS_UPDATING_OUT;
    }


}
