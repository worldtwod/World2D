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
public  class TriangleModel extends GeometryModel{

    private  static final int VERTEX_PER_MODEL =  3;
    private  static final int INDEX_PER_MODEL  =  3;



    public TriangleModel(float scalePixel){
        super(scalePixel, GL_LINE_LOOP,
                VERTEX_PER_MODEL*FLOAT_PER_VERTEX, INDEX_PER_MODEL);
    }


    @Override
    public  short[] getIndex(int offset){
        int index = 0;
        short offsetModel = (short) (offset* VERTEX_PER_MODEL);
        mIndex[index++]= (short) (0 + offsetModel);
        mIndex[index++]= (short) (1 + offsetModel);
        mIndex[index]= (short) (2 + offsetModel);
        return mIndex;
    }


    @Override
    public void updateModel(DrawableObject drawable){
        int index=0;
        float sWidth= drawable.width * drawable.scale *mScalePixel;
        float sHeight= drawable.height * drawable.scale*mScalePixel;
        float vx = drawable.x*mScalePixel;
        float vy = drawable.y*mScalePixel;

        // Triangle 1
        // v0
        mVertex[index++]=vx-sWidth/2;
        mVertex[index++]=vy+sHeight/2;

        // v1
        mVertex[index++]=vx+sWidth/2;
        mVertex[index++]=vy-sHeight/2;

        // v1
        mVertex[index++]=vx-sWidth/2;
        mVertex[index++]=vy-sHeight/2;

    }


    
}
