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
 * Created by campino on 20/06/2016.
 *
 */
public  class GeometryModel extends BaseModel{

    public static final int vertexPerModel  =  4;

    public static final int positionSize    =  2;

    public static final int indexPerModel   =  8;


    public static final int floatPerVertex    = positionSize;
    public static final int floatPerModel     = vertexPerModel *floatPerVertex;


    public static final int stride             = floatPerVertex*BYTES_PER_FLOAT;
    public static final int positionOffset     = 0;
    public static final int colorOffset        = positionSize ;


    private static final short[] sVertexIndex = new short[indexPerModel];

    public static  final int  bytesPerIndexModel= indexPerModel* BYTES_PER_SHORT;
    public static final  int  bytesPerVertexModel = floatPerModel*BYTES_PER_FLOAT;



    public GeometryModel(float scalePixel){
        super(scalePixel);
       mVertexModel = new float[floatPerModel];
    }



    public static String metrics(){
        StringBuffer strBuffer= new StringBuffer();
        strBuffer.append("       ImageModel Metrics\n");
        strBuffer.append("\nVertexPerModel: " + vertexPerModel);
        strBuffer.append("\n floatPerModel: " + floatPerModel);
        strBuffer.append("\n bytesPerModel: " + bytesPerVertexModel);
        strBuffer.append("\n");
        strBuffer.append("\nIndexPerModel: " + indexPerModel);
        strBuffer.append("\n bytesPerIndexModel: " + bytesPerIndexModel);
        return strBuffer.toString();
    }

    @Override
    public  short[] getIndex(int offset){
        int index = 0;
        short offsetModel = (short) (offset*vertexPerModel);
        sVertexIndex[index++]= (short) (0 + offsetModel);
        sVertexIndex[index++]= (short) (1 + offsetModel);
        sVertexIndex[index++]= (short) (1 + offsetModel);
        sVertexIndex[index++]= (short) (2 + offsetModel);
        sVertexIndex[index++]= (short) (2 + offsetModel);
        sVertexIndex[index++]= (short) (3 + offsetModel);

        sVertexIndex[index++]= (short) (3 + offsetModel);
        sVertexIndex[index++]= (short) (0 + offsetModel);

        return sVertexIndex;
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
        mVertexModel[index++]=vx-sWidth/2;
        mVertexModel[index++]=vy+sHeight/2;

        // v1
        mVertexModel[index++]=vx+sWidth/2;
        mVertexModel[index++]=vy+sHeight/2;

        // v2
        mVertexModel[index++]=vx+sWidth/2;
        mVertexModel[index++]=vy-sHeight/2;

        //V3
        mVertexModel[index++]=vx-sWidth/2;
        mVertexModel[index++]=vy-sHeight/2;

    }
}
