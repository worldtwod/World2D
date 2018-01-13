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

package com.titicolab.nanux.graphics.model;

import com.titicolab.nanux.graphics.texture.Texture;

/**
 * Created by campino on 25/05/2016.
 * The Frames have the basic render information:
 *
 *  Texture reference
 *  Uv coordinates
 *  Uv size
 *  FrameGroup: Normally the frames are set in a groupFrame
 *
 */
public class UvCoordinates {
    private static final int FRAME_COORDINATES_SIZE = 4;

    // UV coordinates



    final float left;
    final float right;
    final float top;
    final float bottom;
    //UV size
   // public final int width;
    //public final int height;

    private static  boolean padding;

    private Texture mTexture;

    /**
     * Constructor for build a frame from a mTexture, it will create his own group
     * @param texture a GL mTexture reference
     */
    public UvCoordinates(Texture texture){
        if(texture==null)
            throw new  IllegalArgumentException("The mTexture can not be null");
        left =0;
        top  = 0;
        right=1;
        bottom =1;
        mTexture = texture;
       // height = texture.getHeight();
       // width = texture.getWidth();
    }

    public UvCoordinates(Texture texture, float left, float top,float right, float bottom){
        if(texture==null)
            throw new  IllegalArgumentException("The mTexture can not be null");
        this.left =left;
        this.top  =top;
        this.right=right;
        this.bottom=bottom;
        mTexture = texture;
        // height = texture.getHeight();
        // width = texture.getWidth();
    }



    public static void setPadding(boolean padding) {
        UvCoordinates.padding = padding;
    }

    public Texture getTexture() {
        return mTexture;
    }

    public float getScalePixel() {
        return mTexture.getScalePixel();
    }


    public float getLeft() {
        return left;
    }

    public float getRight() {
        return right;
    }

    public float getTop() {
        return top;
    }

    public float getBottom() {
        return bottom;
    }
}
