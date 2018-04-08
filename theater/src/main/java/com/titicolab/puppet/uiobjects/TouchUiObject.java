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

package com.titicolab.puppet.uiobjects;


/**
 * Created by campino on 17/11/2016.
 *
 */
@Deprecated
public class TouchUiObject {

    /*extends
} UiObject implements TouchableObject.TouchShape{



    private static final long TIME_FOR_DOUBLE_CLICK = 500; // time in milli seconds
    private long mLastTimeClick;



    private boolean mFlagPress;
    private TouchableObject.ClickListener mClickListener;
    private TouchableObject.TouchActionListener mTouchActionListener;

    private TouchableObject.DoubleClickListener mDoubleClickListener;


    public TouchUiObject() {
        setTouchable(this);
    }


    public void setClickListener(TouchableObject.ClickListener touchListener) {
        this.mClickListener =touchListener;
    }

    public void setDoubleClickListener(TouchableObject.DoubleClickListener touchListener) {
        this.mDoubleClickListener =touchListener;
    }


    public void setTouchActionListener(TouchableObject.TouchActionListener touchActionListener) {
        this.mTouchActionListener = touchActionListener;
    }

    public boolean isPress() {
        return mFlagPress;
    }

    public void setFlagPress(boolean flagPress){
        this.mFlagPress = flagPress;
    }

    @Override
    public boolean checkHasTouch(float x, float y){
        int r = (int) (getImage().getWidth()/2f);
        return TouchableObject.checkTouchByRadio(getImage(),r,x,y);
    }

    @Override
    public void onTouchObject(TouchEvent event, int action, boolean touched) {


        if(action ==TouchableObject.ACTION_DOWN){
            mFlagPress=true;
            if(mTouchActionListener!=null)
                mTouchActionListener.onDown(this);
        }else if(action ==TouchableObject.ACTION_MOVE){
            if(mTouchActionListener!=null)
                mTouchActionListener.onMove(this,touched);
        }else  if(action ==TouchableObject.ACTION_UP){
            mFlagPress=false;
            boolean hasClick=false;
            long currentTime = System.currentTimeMillis();

            if(mTouchActionListener!=null)
                mTouchActionListener.onUp(this,touched);

            if(touched) {
                hasClick=true;
                mLastTimeClick =currentTime- mLastTimeClick;
            }

            if(hasClick && mLastTimeClick <TIME_FOR_DOUBLE_CLICK){
                notifyDoubleClick();
            }else{
                notifyClick();
            }

            mLastTimeClick = currentTime;

        }
    }

    private void notifyDoubleClick() {
        if(mDoubleClickListener!=null){
            mDoubleClickListener.onDoubleClickObject(this);
        }
    }

    private void notifyClick() {
        if(mClickListener!=null){
            mClickListener.onClickObject(this);
        }
    }
    */
}
