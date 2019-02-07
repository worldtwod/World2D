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


import com.titicolab.nanux.animation.Animation;
import com.titicolab.nanux.animation.AnimationBuilder;
import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.nanux.animation.Animator;
import com.titicolab.nanux.graphics.draw.Image;
import com.titicolab.nanux.objects.factory.Parameters;
import com.titicolab.nanux.objects.factory.RequestObject;
import com.titicolab.nanux.touch.ObservableInput;

/**
 * Created by campino on 15/02/2017.
 *
 */

public  class Animated   extends GameObject implements Animation.DefineAnimationSheet, GameObject.Touchable{

    private Image               mImage;
    private Animator            mAnimator;

    /**
     * By Default the animate object is drawable and updatable
     */
    public Animated() {
        setDrawable(true);
        setUpdatable(true);
    }

    /**
     * Called after instantiation
     * @param request initialization data
     */
    @Override
    protected void onAttachParameters(RequestObject request) {
        super.onAttachParameters(request);
    }

    /**
     * This method is called for the FactoryObject, it is only called one time when  hasCustomClips()
     *  return true, it means that all instances of this object carri the same Animation
     * @param builder use for build the animation for this object
     * @return The animation with the clips
     */
    protected Animation onBuildClips(AnimationBuilder builder) {
        return builder.build(getParameters().animation);
    }

    /**
     * Use if it is necessaries build a new animation for every instance of this object.
     * @return build a new animation for every instance
     */
    boolean   hasCustomClips(){
        return false;
    }

    /**
     * There are a animation ok for this object, the animator will be initialized here
     * @param animation Animation for this object
     */
    protected void  onAttachAnimation(Animation animation){
        mAnimator = new Animator(animation);
        mAnimator.playAndRepeat(getParameters().clipStart);
        mImage = new Image(mAnimator.getCurrentFrame());
    }

    /**
     * This object can define a AnimationSheet, when it is instanced this has a priority,
     * if it is null, then the object will use the AnimationSheet form layer or scene
     * @param builder the builder
     * @return the AnimationSheet
     */
    @Override
    public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
        return null;
    }

    /** Lifecycle onCreated()  **/
    protected void onCreated() {

    }
    /** Lifecycle onStart()  **/
    protected void onStart() {

    }
    /** Lifecycle onStop()  **/
    protected void onStop() {

    }
    /** Lifecycle onDestroy()   **/
    protected void onDestroy() {

    }

    /**  Update observers to updateLogic and   updateRender**/
    @Override
    protected void updateLogic() {
    }
    @Override
    protected void updateRender() {
        mImage.setUvCoordinates(mAnimator.getCurrentFrame());
        mAnimator.updateFrame();
        mImage.updateRender();
    }

    @Override
    public boolean checkIsTouching(ObservableInput.Event input){
      return false;
    }

    /***************************** getters setters ************************************************/

    protected  ParamsAnimation getParameters(){
        return (ParamsAnimation) super.getParameters();
    }
    public Image getImage() {
        return mImage;
    }
    public Animator getAnimator() {
        return mAnimator;
    }

    public float getWidth() {
        checkImageNotNull();
        return mImage.getWidth();
    }
    public float getHeight() {
        checkImageNotNull();
        return mImage.getHeight();
    }
    /**
     * The screen coordinates have (0,0) in (leftSp, bottom) corner.
     * @param left left
     * @param top  top
     */
    synchronized public void setLeftTop(float left, float top) {
        checkImageNotNull();
        mImage.setPositionLeftTop(left,top);
    }

    public void setPosition(float x, float y) {
        checkImageNotNull();
        mImage.setPosition(x,y);
    }

    public float getX() {
        return mImage.getX();
    }
    public float getY() {
        return mImage.getY();
    }
    public void setColor(float r, float g, float b, float alpha) {
        mImage.setColor(r, g, b, alpha);
    }

    public void setSize(float width, float height) {
        mImage.setSize((int)width,(int)height);
    }


    /**********************************************************************************************
     * Class para initialize el object Animator
     */
    public static class ParamsAnimation extends Parameters {
        final private String animation;
        final private int    clipStart;

        public ParamsAnimation(String animation, int clipStart) {
            this.animation = animation;
            this.clipStart = clipStart;
        }
    }

    private void checkImageNotNull(){
        if(mImage==null)throw  new
                ExceptionInInitializerError("First you must set" +
                " a image or animation to the object");
    }
}
