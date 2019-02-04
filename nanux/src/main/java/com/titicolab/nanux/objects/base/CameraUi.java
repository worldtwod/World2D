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

import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.nanux.util.DisplayInfo;

/**
 * Created by campino on 30/06/2016.
 *
 */
public class CameraUi extends Camera{

    private final DisplayInfo mDisplayInfo;

    public CameraUi(DisplayInfo displayInfo) {
        super(new ProjectionUi(displayInfo));
        mDisplayInfo = displayInfo;
    }


    public void setViewport(int refWidth, int refHeight){
        getProjection().setViewport(refWidth,refHeight);
        getProjection().setPosition(
                getWidth()/2,
                getHeight()/2);
    }

    @Override
    public void setPosition(float x, float y){
        getProjection().setPosition(x,y);
    }


    public void setViewport(int refWidth, int refHeight, int screenExpand) {
        getProjection().setViewport(refWidth,refHeight,screenExpand);
    }

    @Override
    public ProjectionUi getProjection() {
        return (ProjectionUi) super.getProjection();
    }


    public float getWidth() {
        return getViewPortWidth();
    }

    public float getHeight() {
        return getViewPortHeight();
    }


    @Override
    public float getX() {
        return getProjection().getX();
    }

    @Override
    public float getY() {
        return getProjection().getY();
    }


    @Override
    public  float pxToCameraX(float xPixels){
        return xPixels* getProjection().getViewPortWidth()/mDisplayInfo.getScreenWidth();
    }

    @Override
    public  float pxToCameraY(float yPixels){
        float cameraHeight = getViewPortHeight();
        return cameraHeight - yPixels*cameraHeight /mDisplayInfo.getScreenHeight();
    }



    public  float pyToHeight(float yPixels){
        float cameraHeight = getViewPortHeight();
        return yPixels*cameraHeight /mDisplayInfo.getScreenHeight();
    }



    @Override
    public float cameraXToPx(float x) {
        float rate = ((float)getViewPortWidth() )/ mDisplayInfo.getScreenWidth();
        return x/ rate;
    }

    @Override
    public float cameraYToPy(float y){
        float screenHeight = mDisplayInfo.getScreenHeight();
        float rate = getProjection().getViewPortHeight() / screenHeight;
        return (getViewPortHeight()  - y)/rate;
    }



}
