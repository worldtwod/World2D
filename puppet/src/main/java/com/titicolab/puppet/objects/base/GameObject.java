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

package com.titicolab.puppet.objects.base;


import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.puppet.objects.factory.Parameters;
import com.titicolab.puppet.objects.factory.RequestObject;

/**
 * Created by campino on 02/06/2016.
 *
 */
public  abstract class GameObject<T extends Parameters> {

    private  boolean mUpdatable;
    private  boolean mDrawable;
    private  boolean mTouchable;


    private RequestObject mRequest;


    protected void onAttachParameters(RequestObject request) {
        mRequest = request;
    }

    public int getId(){
        return mRequest.getId();
    }

    @SuppressWarnings("unchecked")
    protected T getParameters(){
        return  mRequest!=null ? (T) mRequest.getParameters() : null;
    }


    protected void onStart() {

    }

    protected void onStop() {

    }

    protected void onDestroy() {

    }


    protected abstract void updateLogic();
    protected abstract void updateRender();


    protected boolean onTouch(ObservableInput.Event input) {
        return false;
    }


    public boolean isUpdatable() {
        return mUpdatable;
    }
    public boolean isTouchable(){
        return mTouchable;
    }
    public boolean isDrawable() {
        return mDrawable;
    }

    protected void setTouchable(boolean touchable) {
        mTouchable = touchable;
    }
    public void setUpdatable(boolean updatable) {
        mUpdatable = updatable;
    }
    public void setDrawable(boolean drawable) {
        mDrawable = drawable;
    }

}
