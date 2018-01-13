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

/**
 * Created by campino on 20/06/2016.
 *
 */
public abstract class GeometryModel extends BaseModel{

    public static final int GL_POINTS=1;
    public static final int GL_LINE_STRIP=2;
    public static final int GL_LINE_LOOP=3;
    public static final int GL_LINES=4;
    public static final int GL_TRIANGLE_STRIP=5;
    public static final int GL_TRIANGLE_FAN=6;
    public static final int GL_TRIANGLES=7;
    private final int mode;

    protected static final int  FLOAT_PER_VERTEX =  2;


    public GeometryModel(float scalePixel, int drawMode, int floatPerModel, int indexPerModel){
        super(scalePixel,floatPerModel,indexPerModel);
        mode = drawMode;
    }


    @Override
    public abstract short[] getIndex(int offset);



    public int getStride() {
        return FLOAT_PER_VERTEX*BaseModel.BYTES_PER_FLOAT;
    }

    public int getPositionSize() {
        return FLOAT_PER_VERTEX;
    }

    public int getIndexPerModel() {
        return mIndex.length;
    }

    public int getDrawMode(){
        return mode;
    }

    @Override
    public abstract void updateModel(DrawableObject drawable);


}
