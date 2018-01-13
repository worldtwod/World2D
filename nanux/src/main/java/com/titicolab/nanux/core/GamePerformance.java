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

package com.titicolab.nanux.core;

/**
 * Created by campino on 12/05/2016.
 *
 */
public class GamePerformance {

    private static final int  LENGTH_FILTER_FPS = 60;
    private static final long PROFILE_SAMPLE_TIME = 1000;
    private static final float MAX_TIME_STEP = 20;
    private int [] mTimeByFrame;

    private long mProfileTime;
    private int mProfileFrames;
    private int mFps;
    private long mLastTime;
    private float mAverageTimeFrame;

    public GamePerformance() {
        mTimeByFrame = new int[LENGTH_FILTER_FPS];
        mLastTime = System.currentTimeMillis();
    }


    public  void update(){
        long currentTime =  System.currentTimeMillis();
        long stepTime =  currentTime - mLastTime;
        mLastTime = currentTime;
        filteredFPS(stepTime);
    }



    private  void filteredFPS(long  timeStep){
        int length = mTimeByFrame.length;
        float acc = 0;

        for (int i = length-2; i >=0 ; i--) {
            mTimeByFrame[i+1]= mTimeByFrame[i];
            acc+= mTimeByFrame[i];
            //System.out.println("mTimeByFrame[i]: " + mTimeByFrame[i]);
        }

        mTimeByFrame[0]= (int) timeStep;
        acc+= mTimeByFrame[0];
        mAverageTimeFrame = acc/length;
    }

    public float getAverageFPS(){
        return (1000f/mAverageTimeFrame);
    }

    public float getAVRmTimeFrame() {
        return mAverageTimeFrame;
    }

    private int accFPS(long stepTime) {
        mProfileTime += stepTime;
        mProfileFrames++;
        if (mProfileTime > PROFILE_SAMPLE_TIME) {
            long averageFrameTime = mProfileTime / mProfileFrames;
            mFps = (int) (1000 / averageFrameTime);
            mProfileTime = 0;
            mProfileFrames = 0;
        }
        return mFps;
    }

    public float getAVRsTimePhysics() {
        float timeStep = mAverageTimeFrame < MAX_TIME_STEP ? mAverageTimeFrame : MAX_TIME_STEP;
        return timeStep/1000;
    }
}
