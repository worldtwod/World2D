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

package com.titicolab.puppeteer;


import android.view.MotionEvent;

import com.titicolab.nanux.touch.ObservableInput;

/**
 * Created by campino on 27/06/2016.
 *
 */
public class AndroidInputEvent implements ObservableInput.Event {
    //private final AndroidDisplayMetrics mScreen;
    private int   action;
    private float pixelX;
    private float pixelY;
    private float uiY;
    private float uiX;

    private float twodX;
    private float twodY;
    private int mPointerId;

    void setAction(int action) {
        this.action = action &  MotionEvent.ACTION_MASK;
    }

    void setPositionPixel(float pixelX, float pixelY) {
        this.pixelX = pixelX;
        this.pixelY = pixelY;
    }

    /**
     * @return  X coordinate from ui
     */
    public float getUiX() {
        return uiX;
    }

    /**
     * @return Y coordinates from ui
     */
    public float getUiY(){
        return  uiY;
    }

    @Override
    public float get2dX() {
        return twodX;
    }

    @Override
    public float get2dY() {
        return twodX;
    }

    @Override
    public void setPositionUi(float uiX, float uiY) {
        this.uiX = uiX;
        this.uiY = uiY;
    }

    @Override
    public void setPosition2D(float twodX, float twodY) {
        this.twodX = twodX;
        this.twodY = twodY;
    }

    @Override
    public void setPointerId(int pointerId) {
        mPointerId = pointerId;
    }

    public int getAction(){
        return action;
    }

    private static String actionToString(int action){
        switch (action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: return "ACTION_DOWN";
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: return "ACTION_UP";
            case MotionEvent.ACTION_MOVE: return "ACTION_MOVE";
        }
        return "ACTION_UNKNOWN";
    }

    public float getPixelX() {
        return pixelX;
    }
    public float getPixelY() {
        return pixelY;
    }


    @Override
    public boolean isActionUp() {
        return  action == MotionEvent.ACTION_UP;
    }
    @Override
    public boolean isActionDown() {
        return action == MotionEvent.ACTION_DOWN;
    }
    @Override
    public boolean isActionMove() {
        return action == MotionEvent.ACTION_MOVE;
    }

    @Override
    public int getPointerId() {
        return mPointerId;
    }


}
