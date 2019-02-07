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


import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.puppeteer.view.GLGraphicView;


/**
 * Created by campino on 30/05/2016.
 *
 */
public class AndroidInput extends FlexibleList<ObservableInput.InputListener>
        implements View.OnTouchListener, View.OnKeyListener, ObservableInput {

    //private static final int RESIZE_FACTOR = 10;
    private AndroidInputEvent mInputEvent;
    private final GLGraphicView mGLGraphicView;
    private boolean mFlagStart;


    public AndroidInput(GLGraphicView glView) {
        mInputEvent = new AndroidInputEvent();
        mGLGraphicView = glView;
        mFlagStart=false;
    }

    @Override
    public void start(){
        mFlagStart=true;
        mGLGraphicView.setOnTouchListener(this);
    }

    public void stop(){
        mFlagStart=false;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mInputEvent.setAction(event.getAction());

        // Get the index of the pointer associated with the action.
        mInputEvent.setPointerId(event.getPointerId(event.getActionIndex()));
        mInputEvent.setPositionPixel(event.getX(),event.getY());
        notifyTouchEvent(mInputEvent);
        return true;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
       //TODO
        return false;
    }

    private void notifyTouchEvent(Event event){
        int index = size();
        if(mFlagStart)
        for (int i = 0; i <index ; i++) {
            get(i).onTouch(event);
        }
    }

}
