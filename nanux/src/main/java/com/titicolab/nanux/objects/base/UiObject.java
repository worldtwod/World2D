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

import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.touch.TouchManager;

/**
 * Created by campino on 21/06/2016.
 *
 */
public abstract class UiObject extends Animated  {

    private TouchManager mTouchManager;

    public UiObject(){
        super();
    }

    public abstract void onCreated();

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void updateLogic() {

    }

    @Override
    protected boolean onTouch(ObservableInput.Event input) {
          if(mTouchManager !=null)
            return mTouchManager.onTouch(input);
          return false;
    }

    @Override
    public boolean checkIsTouching(ObservableInput.Event input){
        boolean isTouchingInX = false;
        boolean isTouchingInY = false;

        float xLeft = getX() - getWidth()/2;
        float yBottom = getY() - getHeight()/2;

        float xi = input.getUiX();
        float yi = input.getUiY();

        if(xLeft< xi && xi<= (xLeft+getWidth())){
            isTouchingInX = true;
        }
        if(yBottom < yi && yi<= (yBottom+getHeight())){
            isTouchingInY = true;
        }
        return isTouchingInX && isTouchingInY;
    }

    protected void setTouchManager(TouchManager mTouchManager) {
        this.mTouchManager = mTouchManager;
    }
}
