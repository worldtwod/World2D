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


import com.titicolab.puppet.animation.Animation;
import com.titicolab.puppet.animation.AnimationBuilder;
import com.titicolab.puppet.animation.Animator;
import com.titicolab.puppet.draw.Image;
import com.titicolab.puppet.objects.factory.Parameters;
import com.titicolab.puppet.objects.factory.RequestObject;

/**
 * Created by campino on 15/02/2017.
 *
 */

public  class Animated   extends GameObject {

    private Image               mImage;
    private Animator            mAnimator;


    public Animated() {
        setDrawable(true);
        setUpdatable(true);
    }

    /***
     * Called after instantiation
     * @param request initialization data
     */
    @Override
    protected void onAttachParameters(RequestObject request) {
        super.onAttachParameters(request);
    }

    /**
     * It method is called for the FactoryObject, it is only called one time when  hasCustomClips()
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
    protected boolean   hasCustomClips(){
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


    protected void onCreated() {

    }

    protected void onStart() {

    }

    protected void onStop() {

    }

    protected void onDestroy() {

    }


    @Override
    protected void updateLogic() {

    }

    @Override
    protected void updateRender() {
        mImage.setUvCoordinates(mAnimator.getCurrentFrame());
        mAnimator.updateFrame();
        mImage.updateRender();
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
        return mImage.getWidth();
    }

    public float getHeight() {
        return mImage.getHeight();
    }
    /**
     * The screen coordinates have (0,0) in (leftSp, bottom) corner.
     * @param left left
     * @param top  top
     */
    synchronized public void setLeftTop(float left, float top) {
        if(mImage==null)throw  new
                ExceptionInInitializerError("First you must set" +
                " a image or animation to the object");
        mImage.setPositionLeftTop(left,top);
    }

    public void setPosition(float x, float y) {
        if(mImage==null)throw  new
                ExceptionInInitializerError("First you must set" +
                " a image or animation to the object");
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
}
