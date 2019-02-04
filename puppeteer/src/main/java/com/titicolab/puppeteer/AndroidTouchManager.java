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

import com.titicolab.nanux.objects.base.GameObject;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.touch.TouchManager;

/**
 * Created by campino on 15/01/2018.
 *
 */

public class AndroidTouchManager implements TouchManager {

    private static final int NOT_POINTER = -1;
    private static final int DOWN_POINTER = 1;
    private static final int MOVING_IN_POINTER  = 2;
    private static final int MOVING_OUT_POINTER = 3;
    private static final long TIME_DOUBLE_CLICK = 500;

    private final GameObject.Touchable mTouchable;

    private int   mPointerId;
    private int   mStatus;

    private long mLastTimeClick;
    private boolean mFlagDown;
    private ClickListener mClickListener;
    private TouchActionListener mTouchActionListener;
    private DoubleClickListener mDoubleClickListener;

    public AndroidTouchManager(GameObject.Touchable touchable) {
        mTouchable=touchable;
        mStatus = NOT_POINTER;
        mPointerId = NOT_POINTER;
        mFlagDown = false;
        mLastTimeClick = 0;
    }

    public void setClickListener(ClickListener touchListener) {
        this.mClickListener =touchListener;
    }
    public void setDoubleClickListener(DoubleClickListener touchListener) {
        this.mDoubleClickListener =touchListener;
    }
    public void setTouchActionListener(TouchActionListener touchActionListener) {
        this.mTouchActionListener = touchActionListener;
    }

    public boolean isDown() {
        return mFlagDown;
    }

    @Override
    public boolean onKey(int keyEvent) {
        return false;
    }

    @Override
    public boolean onTouch(ObservableInput.Event event) {

        boolean processIt=false;
        boolean isInTouch = mTouchable.checkIsTouching(event);

        if(event.isActionDown()){
               //new pointer and down event
              if(mStatus==NOT_POINTER && isInTouch) {
                    notifyActionDown();
                    mPointerId = event.getPointerId();
                    mStatus = DOWN_POINTER;
                    processIt=true;
                    mFlagDown = true;
                }// else other pointer touch the object

        }else if(event.isActionMove()){
                //other pointer are move around this object
                if(mPointerId == event.getPointerId()){
                    processIt=true;
                    notifyActionMove(isInTouch);
                    mStatus = isInTouch? MOVING_IN_POINTER:MOVING_OUT_POINTER;
                }

        }else if(event.isActionUp()){
                if(mPointerId == event.getPointerId()){
                    processIt=true;
                    notifyActionUp(isInTouch);
                    mStatus = NOT_POINTER;
                    mPointerId = NOT_POINTER;
                    mFlagDown = false;
                    long currentTimeClick = System.currentTimeMillis();
                    long difference = currentTimeClick-mLastTimeClick;
                    mLastTimeClick = currentTimeClick;
                    if(difference>TIME_DOUBLE_CLICK)
                        notifyOnClick();
                    else
                        notifyDoubleClick();
                }
        }

        return processIt;
    }

    private void notifyDoubleClick() {
        if(mDoubleClickListener!=null)
            mDoubleClickListener.onDoubleClickObject(mTouchable);
    }

    private void notifyOnClick() {
        if(mClickListener!=null){
            mClickListener.onClickObject(mTouchable);
        }
    }

    private void notifyActionUp(boolean isInTouch) {
        if(mTouchActionListener!=null)
            mTouchActionListener.onUp(mTouchable,isInTouch);
    }

    private void notifyActionMove(boolean isInTouch) {
        if(mTouchActionListener!=null)
            mTouchActionListener.onMove(mTouchable,isInTouch);
    }

    private void notifyActionDown() {
        if(mTouchActionListener!=null)
            mTouchActionListener.onDown(mTouchable);
    }
}
