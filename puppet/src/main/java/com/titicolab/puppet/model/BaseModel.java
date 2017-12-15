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
 * Created by campino on 24/05/2016.
 *
 */
public abstract class BaseModel implements DrawModel {
    public static final int BYTES_PER_FLOAT = 4;
    public static final int BYTES_PER_SHORT = 2;

    float[]     mVertexModel;
    float       mScalePixel=1;


    BaseModel(float scalePixel) {
        mScalePixel = scalePixel;
    }

    public float[] getVertex(){
        return mVertexModel;
    }
    public void setScalePixel(float scalePixel) {
        this.mScalePixel = scalePixel;
    }
}
