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

package com.titicolab.opengl.shader;

import com.titicolab.nanux.graphics.textures.Texture;

/**
 * Created by campino on 17/05/2016.
 *
 */
public  class GLTexture  implements Texture{

    public static final int ACTIVITY_RESOURCES = -100;
    public static final int EXTERNAL_OES    = -100;
    public static final int BITMAP_RESOURCE = -101;

    private  int mTextureGlId;
    private final float mHeightSp;
    private final float mWidthSp;

    private final int resources;
    private final float mScalePixel;



    private  int resourcesType;


    /**
     * Object for hold a texture id
     * @param glId GL id of texture
     * @param width width
     * @param height height
     * @param resources resource of image for make the texture.
     * @param scalePixel
     */
    public GLTexture(int glId, float width, float height, int resources, float scalePixel) {
        mTextureGlId =glId;
        this.mWidthSp =width;
        this.mHeightSp =height;
        this.resources = resources;
        this.mScalePixel = scalePixel;
        resourcesType = ACTIVITY_RESOURCES;
    }

    public int getTextureId() {
        return mTextureGlId;
    }

    public int getWidth() {
        return Math.round(mWidthSp/mScalePixel);
    }

    public int getHeight() {
        return Math.round(mHeightSp/mScalePixel);
    }

    public int getResources() {
        return resources;
    }

    public void setGlTextureId(int glTextureId) {
        mTextureGlId = glTextureId;
    }

    public float getScalePixel() {
        return mScalePixel;
    }

    public int getResourcesType(){
        return resourcesType;
    }

    public void setResourcesType(int resourcesType) {
        this.resourcesType = resourcesType;
    }
}
