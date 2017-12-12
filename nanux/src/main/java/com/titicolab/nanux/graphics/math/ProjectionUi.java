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

package com.titicolab.nanux.graphics.math;

import com.titicolab.nanux.util.DisplayInfo;

/**
 * Created by campino on 23/05/2016.
 *
 */
public class ProjectionUi extends Projection {

    public static final int SCREEN_EXPAND = 0;
    public static final int SCALE_HEIGHT = 1;
    public static final int SCALE_WIDTH = 2;
    private final float mScalePixel;

    private float mPositionX;
    private float mPositionY;

    private float mScale;
    private float mNear;
    private float mFar;
    private float mRatio;
    
    private int mTypeScaler;
    //private float mRefWidth;
    //private float mRefHeight;


    public ProjectionUi(DisplayInfo displayInfo){
        mScalePixel = displayInfo.getScalePixel();
        float ratio = displayInfo.getAspectRatio();
        float refWidth = displayInfo.getReferenceWidth();
        float refHeight = displayInfo.getReferenceHeight();
        iniProjection(refWidth,refHeight,ratio);
    }

    @Deprecated
    public ProjectionUi(float refWidth, float refHeight, float screenRatio){
       iniProjection(refWidth,refHeight,screenRatio);
        mScalePixel = 1;
    }

    private void iniProjection(float refWidth,float refHeight, float screenRatio){
        mRatio = screenRatio;
        mScale =1;
        mNear=  -1f;
        mFar =   1f;
        setViewport(refWidth,refHeight,SCREEN_EXPAND);
        //setPosition(mViewPortWidth/2,mViewPortHeight/2);
    }


    public void setViewport(int refWidth, int refHeight) {
        setViewport(refWidth,refHeight,mTypeScaler);
    }

    /**
     * Set the viewport windows and center the camera in the central point.
     * Whether the screen does no match to that width-height then, it resize the
     * view port according to typeScale.
     *
     * @param width with wanted
     * @param height height wanted
     * @param typeScaler  type of scale to apply
     */
    public void setViewport(float width, float height, int typeScaler){
        checkType(typeScaler);
        mTypeScaler=typeScaler;

        if(mTypeScaler == SCREEN_EXPAND) {
            setViewPortSize(width, height);
        }else if(mTypeScaler == SCALE_HEIGHT){
            setViewPortSize(width, width /mRatio);
        }else if(mTypeScaler == SCALE_WIDTH){
            setViewPortSize(height *mRatio, height);
        }

        setPosition(mViewPortWidth/2,mViewPortHeight/2);
        //updateMatrix();
    }

    @Override
    public void setPosition(float x, float y) {
        mPositionX=x*mScalePixel;
        mPositionY =y*mScalePixel;
        updateMatrix();
    }

    public void setScale(float scale){
        mScale =scale;
        updateMatrix();
    }

    private void setViewPortSize(float width, float height){
        mViewPortWidth = width;
        mViewPortHeight = height;
    }

    private void checkType(int typeScaler) {
        if(typeScaler!= SCREEN_EXPAND
                && typeScaler!= SCALE_WIDTH
                && typeScaler!= SCALE_HEIGHT)
            throw new RuntimeException("typeScaler is unknown");
    }

    private void updateMatrix(){
        float scaleWorld= mScalePixel;
        float width = mViewPortWidth * scaleWorld* mScale;
        float height = mViewPortHeight* scaleWorld * mScale;


        float xSp = mPositionX;
        float ySp = mPositionY;

        float left = xSp- width/2;
        float right = xSp + width/2;
        float top = ySp +  height/2;
        float bottom =ySp- height/2;
        Math.orthoM(mProjectionMatrix, 0, left, right, bottom, top, mNear, mFar);
    }

    @Override
    public String toString() {
        return "\n--- Projection Matrix ---------" +
                "\nVpWidth:  " + mViewPortWidth +
                "\nVpHeight: " + mViewPortHeight +
                "\nPositionX: " + mPositionX +
                "\nPositionY: " + mPositionY;
    }

    public float getX() {
        return mPositionX;
    }

    public float getY() {
        return mPositionY;
    }

    public float getScale() {
        return mScale;
    }


    public float[] getMatrix() {
       return  super.getMatrix();
    }




}
