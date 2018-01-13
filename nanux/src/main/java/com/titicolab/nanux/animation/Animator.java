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

package com.titicolab.nanux.animation;

/**
 * Created by campino on 19/05/2016.
 *
 */


public class Animator {

    private static final int MAX_SPEED = 60;

    private int mStart;
    private int mCurrent;
    private int mStop;

    private int mCounterOut;
    private int mCounter;

    private Frame               mCurrentFrame;
    private Clip                mCurrentClip;

    private final Animation     mAnimation;
    private int                 mCurrentPlay=-1;
    private boolean             mPause;
    private boolean             mFlagPlayAndStop;



    public Animator(Animation animation) {
        mAnimation = animation;
        mCurrentPlay=-1;
        setSpeed(MAX_SPEED);
        if(mAnimation.size()>0){
            mCurrentFrame=null;
            playByIndex(0);
        }
    }

    public void setClip(Clip clip){
        mAnimation.add(clip);
        if(mCurrentClip == null){
            mCurrentFrame=null;
            playById(clip.getId());
        }
    }


    public int size(){
        return mAnimation.size();
    }

    public int capacity(){
        return mAnimation.capacity();
    }


    synchronized public void playAndStop(int id){
        mFlagPlayAndStop=true;
        play(id);
    }

    synchronized public void playAndRepeat(int id){
        mFlagPlayAndStop=false;
        play(id);
    }

    synchronized private void play(int id){
        int index = getAnimation().indexOf(id);
        if (index<0)
            throw new  RuntimeException
                    ("You are attempting play the clip [" + id + "]," +
                            " but it does not exist in the animation [ " + getAnimation().getKey()+ " ]");
        playByIndex(index);
    }

    synchronized public void playById(int id){
        playByIndex(mAnimation.indexOf(id));
    }

    synchronized public void playByIndex(int index){
        if(mCurrentPlay==index) return;

        if(index >= mAnimation.size()) throw new IllegalArgumentException
                   (  "The object with class " + this.getClass().getName() +
                        " is trying of playByIndex the clip " + index
                        + ". But, the sequence only have " +size()+ " clips");

        if(mAnimation.get(index)==null) throw new IllegalArgumentException
                ("Clip not found, illegal clip index: " + index);

        mCurrentPlay = index;
        mCurrentClip = mAnimation.get(index);
        mStart = 0;
        mCurrent =0;
        mStop  = mCurrentClip.size();

        if(mCurrentFrame ==null)
            mCurrentFrame = mCurrentClip.get(mCurrent);

    }



    synchronized public void updateFrame(){
        if(mPause)
            return;

        mCurrentFrame = mCurrentClip.get(mCurrent);

        //Play and stop
        if(mCurrent==mStop-1 && mFlagPlayAndStop)
            return;

        if(mCounter < (mCounterOut-1)){
            mCounter++;
        }else if((mCurrent+1)<mStop){
            mCurrent++;
            mCounter=0;
        }else {
            mCurrent=mStart;
            mCounter=0;
        }
    }


    /**
     * Speed of animation, by default it is 60 fps. the minimum is 1 fps
     * @param speed  in frames per second
     */
    synchronized public void setSpeed(int speed) {
        if(speed > MAX_SPEED) throw new IllegalArgumentException
                ("The speed must be less than: " + MAX_SPEED);
        mCounterOut = MAX_SPEED/speed;
    }

    synchronized public Frame getCurrentFrame(){
        return mCurrentFrame;
    }

    public Animation getAnimation() {
        return mAnimation;
    }
    public void setPause(boolean pause) {
        mPause = pause;
    }

    public int getIndexClip() {
        return mCurrentPlay;
    }


}
