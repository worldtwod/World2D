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
 * Created by campino on 13/01/2018.
 *
 */

@Deprecated
public class Button {


    /*extends UiObject {
    private boolean mFlagPress;

    @Override
    public void onCreated(){
     setTouchable(true);
    }


    @Override
    protected boolean onTouch(ObservableInput.Event input) {
        int action = input.getAction();

        if(input.isActionUp()){
            mFlagPress=true;
        }else if(input.isActionMove()){
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
    */
}
