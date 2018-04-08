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

package com.titicolab.nanux.touch;

import com.titicolab.nanux.graphics.model.DrawableObject;
import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.nanux.objects.base.Animated;

/**
 * Created by campino on 15/01/2018.
 *
 */

public class TouchableObject  implements ObservableInput.InputListener{

    private static final long TIME_FOR_DOUBLE_CLICK = 500; // time in milli seconds




    public interface ClickListener {
        void onClickObject(Animated gameObject);
    }

    public interface DoubleClickListener {
        void onDoubleClickObject(Animated gameObject);
    }

    public interface TouchActionListener{
        void onDown(Animated gameObject);
        void onMove(Animated gameObject, boolean touching);
        void onUp(Animated gameObject, boolean touching);
    }


    /** The object that want to listen touches needs implement this interface, it define the
     * collision area and notify to listens when a touch even happen.
     */
    public interface TouchCollision {
        boolean checkHasTouch(ObservableInput.Event event);
    }

    private long mLastTimeClick;
    private boolean mFlagPress;
    private ClickListener mClickListener;
    private TouchActionListener mTouchActionListener;
    private DoubleClickListener mDoubleClickListener;
    final private Animated mParent;
    final private TouchCollision mTouchCollision;
    final private FlexibleList<Integer> mPointerIdList;



    public TouchableObject(Animated parent, TouchCollision touchCollision) {
        mParent = parent;
        mTouchCollision = touchCollision;
        mFlagPress = false;
        mPointerIdList = new FlexibleList<>(10);
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

    public boolean isPress() {
        return mFlagPress;
    }




    @Override
    public boolean onTouch(ObservableInput.Event event) {

        /*
        boolean processIt=false;

           int index =  mPointerIdList.indexOf(event.getPointerId());
           boolean isInTouch = mTouchCollision.checkHasTouch(event);

            if(event.isActionUp()){
                if(index>-1) {
                    mPointerIdList.add(event.getPointerId());
                    notifyActionUp(event);
                    processIt=true;
                }// else other pointer touch the object

            }else if(action == MotionEvent.ACTION_MOVE){
                //other pointer are move around this object
                if(mPointerId == event.getPointerId(index)){
                    processIt=true;
                    notifyAction(touchEvent,ACTION_MOVE,hasTouch);
                }
            }else if(action == MotionEvent.ACTION_POINTER_UP
                    || action== MotionEvent.ACTION_UP){
                if(mPointerId == event.getPointerId(index)){
                    processIt=true;
                    notifyAction(touchEvent,ACTION_UP,hasTouch);
                    mPointerId=NOT_POINTER;
                }
            }

            return processIt; */
        return false;
    }

    private void notifyActionUp(ObservableInput.Event event) {

    }

    @Override
    public boolean onKey(int keyEvent) {
        return false;
    }



    /*
    @Override
    public void onTouch(Event event, int action, boolean touched) {


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
            mDoubleClickListener.onDoubleClickObject(mParent);
        }
    }

    private void notifyClick() {
        if(mClickListener!=null){
            mClickListener.onClickObject(this);
        }
    }*/



    /**
     * Fast algorithm for check a point has collision with a circular area
     * @param image image with a rectangular model
     * @param r     radio of collision
     * @param px    coordinate x of the point
     * @param py    coordinate y of then point
     * @return
     */
    public  static boolean checkTouchByRadio(DrawableObject image, int r, float px, float py){
        return checkTouchByRadio(image.getX(),image.getY(),r,px,py);
    }

    public  static boolean checkTouchByRadio( float objectX,float objectY, int r,  float px, float py){
        float x1 = objectX;
        float y1 = objectY;
        //Fast Algorithm for distance compute
        int x = (int) Math.abs(px - x1);
        int y = (int) Math.abs(py - y1);
        int min;
        if (x < y) min = x;
        else min = y;
        int distance = Math.abs(x + y - (min >> 1) - (min >> 2) + (min >> 4));
        return distance < r;
    }


    public static  boolean checkTouchByBox(int boxX, int boxY, int boxWidth, int boxHeight,  int px, int py){
        boolean r = false;
        float xMin = boxX-boxWidth/2f;
        float xMax = boxX+boxWidth/2f;
        float yMin = boxY-boxHeight;
        float yMax = boxY + boxHeight/2f;
        if((xMin < (float) px && (float) px < xMax) &&
                (yMin < (float) py && (float) py < yMax)){
            r=true;
        }
        return r;
    }
}
