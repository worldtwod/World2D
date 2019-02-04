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
import com.titicolab.nanux.objects.factory.Parameters;
import com.titicolab.nanux.objects.factory.RequestObject;

/**
 * Created by campino on 02/06/2016.
 * Every thing that live in World2D is GameObject
 */
public  abstract class GameObject<T extends Parameters> {

    /** The object observe the update event, the updateds methods will be called  **/
    private  boolean mUpdatable;
    /** The object will be drawled, it is updated  **/
    private  boolean mDrawable;
    /** The object can be touched for this object  **/
    private  boolean mTouchable;

    private RequestObject mRequest;


    public int getId(){
        return mRequest.getId();
    }


    public void setUpdatable(boolean updatable) {
        mUpdatable = updatable;
    }
    public void setDrawable(boolean drawable) {
        mDrawable = drawable;
    }

    protected void onAttachParameters(RequestObject request) {
        mRequest = request;
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

    public boolean isDrawable() {
        return mDrawable;
    }


    /** Input and touching observer and flags **/

    protected void setTouchable(boolean touchable) {
        mTouchable = touchable;
    }
    public boolean isTouchable(){
        return mTouchable;
    }
    public boolean  checkIsTouching(ObservableInput.Event input){
        return false;
    }
    protected boolean onTouch(ObservableInput.Event input) {
        return false;
    }

    public interface Touchable{
        boolean  checkIsTouching(ObservableInput.Event input);
        //boolean  onTouch(ObservableInput.Event input);
    }


    /** Update listeners and update flags **/
    public boolean isUpdatable() {
        return mUpdatable;
    }
    protected abstract void updateLogic();
    protected abstract void updateRender();




}
