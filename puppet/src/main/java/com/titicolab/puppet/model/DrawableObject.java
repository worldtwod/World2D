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

package com.titicolab.puppet.model;

/**
 * Created by campino on 18/05/2016.
 * Parent of any drawable object. its updateRenderObjects need be updated before to draw.
 * This make a update of model.
 */

public abstract class DrawableObject{
    public static final int BYTES_PER_FLOAT = 4;

    //Position pxg
    protected float x;
    protected float y;

    //Size  pxg
    protected float width;
    protected float height;

    protected   float   scale;
    protected   float   angle;
    protected   float[] color;



    private boolean reflectH;
    private boolean reflectV;

    private final DrawModel mDrawModel;


    protected DrawableObject(DrawModel drawModel) {
        mDrawModel = drawModel;
        reset();
    }

    /**
     * Override this object for update the particular model for a drawable child.
     */
    public void updateRender() {
       mDrawModel.updateModel(this);
    }


    /**
     * Initialize all variable to its sequence value, the scale will be 1
     */
    protected void reset(){
        y =0;
        x =0;
        width = 0;
        height =0;
        scale = 1;
        angle = 0;
    }

    /**
     * Set the central point of drawable, the x y are coordinates of game word
     * @param x word coordinate
     * @param y word coordinate
     */
    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getScaledHeight() {
        return width*scale;
    }

    public float getScaledWidth() {
        return height*scale;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getScale() {
        return scale;
    }

    public int getTextureId(){
        return 0;
    }

    public float[] getColor() {
        return color;
    }

    public void setReflectH(boolean reflectH) {
        this.reflectH = reflectH;
    }

    public void setReflectV(boolean reflectV) {
        this.reflectV = reflectV;
    }

    public boolean isReflectH() {
        return reflectH;
    }

    public boolean isReflectV() {
        return reflectV;
    }


    public DrawModel getDrawModel() {
        return mDrawModel;
    }


}
