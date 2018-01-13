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
 * Created by campino on 24/05/2016.
 *
 */
public class ImageModel extends BaseModel{


    private static final int vertexPerModel  =  4;

    public static final int positionSize    =  2;

    public static final int coordinatesSize =  2;

    public static final int colorSize       =  4; //4 before

    public static final int indexPerModel   =  6;


    private static final int floatPerVertex    = positionSize + coordinatesSize + colorSize;
    private static final int floatPerModel     = vertexPerModel *floatPerVertex;


    public static final int stride             = floatPerVertex*BYTES_PER_FLOAT;
    public static final int positionOffset     = 0;
    public static final int coordinatesOffset  = positionSize;
    public static final int colorOffset        = coordinatesSize +  positionSize ;


    public static  final int  bytesPerIndexModel= 6* BYTES_PER_SHORT;
    public static final  int  bytesPerVertexModel = floatPerModel*BYTES_PER_FLOAT;


    private UvCoordinates mUvCoordinates;


    public ImageModel(UvCoordinates uvCoordinates) {
        super(uvCoordinates.getTexture().getScalePixel(),floatPerModel,indexPerModel);
        mUvCoordinates = uvCoordinates;
    }




    @Override
    public  short[] getIndex(int offset){
        int index = 0;
        short offsetModel = (short) (offset*vertexPerModel);
        mIndex[index++]=  offsetModel;
        mIndex[index++]= (short) (1 + offsetModel);
        mIndex[index++]= (short) (2 + offsetModel);
        mIndex[index++]= offsetModel;
        mIndex[index++]= (short) (2 + offsetModel);
        mIndex[index]= (short) (3 + offsetModel);
        return mIndex;
    }




    @Override
    public void updateModel(DrawableObject drawable) {


        int index=0;
        float right =  drawable.isReflectH() ? mUvCoordinates.left :
                mUvCoordinates.right;
        float left =   drawable.isReflectH() ? mUvCoordinates.right :
                mUvCoordinates.left;

        float top  = drawable.isReflectV() ? mUvCoordinates.bottom :
                mUvCoordinates.top;

        float bottom =drawable.isReflectV()? mUvCoordinates.top:
                mUvCoordinates.bottom;

        float sWidth=drawable.width *mScalePixel*drawable.scale;
        float sHeight=drawable.height *mScalePixel *drawable.scale;
        float vx = drawable.x * mScalePixel;
        float vy = drawable.y * mScalePixel;

        float r = drawable.color[0];
        float g = drawable.color[1];
        float b = drawable.color[2];
        float a = drawable.color[3];


        mVertex[index++]=vx-sWidth/2;
        mVertex[index++]=vy+sHeight/2;
        mVertex[index++]=left;  // origin u69*
        mVertex[index++]=top;  // leftSp-topSp

        mVertex[index++]=r;  // position
        mVertex[index++]=g;  // g
        mVertex[index++]=b;  // b
        mVertex[index++]=a;  // a

        // v1
        mVertex[index++]=vx-sWidth/2;
        mVertex[index++]=vy-sHeight/2;
        mVertex[index++]=left;
        mVertex[index++]=bottom;  //leftSp-bottom

        mVertex[index++]=r;  // position
        mVertex[index++]=g;  // g
        mVertex[index++]=b;  // b
        mVertex[index++]=a;  // a

        // v2
        mVertex[index++]=vx+sWidth/2;
        mVertex[index++]=vy-sHeight/2;
        mVertex[index++]=right;
        mVertex[index++]=bottom;  //right-bottom

        mVertex[index++]=r;  // position
        mVertex[index++]=g;  // g
        mVertex[index++]=b;  // b
        mVertex[index++]=a;  // a

        //V3
        mVertex[index++]=vx+sWidth/2;
        mVertex[index++]=vy+sHeight/2;
        mVertex[index++]=right;
        mVertex[index++]=top;  //right-topSp

        mVertex[index++]=r;  // position
        mVertex[index++]=g;  // g
        mVertex[index++]=b;  // b
        mVertex[index]=a;    // a
    }


    public UvCoordinates getUvCoordinates() {
        return mUvCoordinates;
    }

    public void setUvCoordinates(UvCoordinates uvCoordinates){
        this.mUvCoordinates = uvCoordinates;
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

    public Texture getTexture() {
        return mUvCoordinates.getTexture();
    }



    /*
    @Override
    public void updateModel(Image drawable) {


        int index=0;
        float right =  drawable.isReflectH() ? (drawable).getUvCoor().left :
                (drawable).getUvCoor().right;
        float left =   drawable.isReflectH() ? ((Image)drawable).getUvCoor().right :
                ((Image)drawable).getUvCoor().left;

        float top  = drawable.isReflectV() ? ((Image)drawable).getUvCoor().bottom :
                ((Image)drawable).getUvCoor().top;

        float bottom =drawable.isReflectV()? ((Image)drawable).getUvCoor().top:
                ((Image)drawable).getUvCoor().bottom;

        float sWidth=drawable.width *mScalePixel*drawable.scale;
        float sHeight=drawable.height *mScalePixel *drawable.scale;
        float vx = drawable.x * mScalePixel;
        float vy = drawable.y * mScalePixel;

        float r = drawable.color[0];
        float g = drawable.color[1];
        float b = drawable.color[2];
        float a = drawable.color[3];




        mVertexModel[index++]=vx-sWidth/2;
        mVertexModel[index++]=vy+sHeight/2;
        mVertexModel[index++]=left;  // origin u69*
        mVertexModel[index++]=top;  // leftSp-topSp

        mVertexModel[index++]=r;  // position
        mVertexModel[index++]=g;  // g
        mVertexModel[index++]=b;  // b
        mVertexModel[index++]=a;  // a

        // v1
        mVertexModel[index++]=vx-sWidth/2;
        mVertexModel[index++]=vy-sHeight/2;
        mVertexModel[index++]=left;
        mVertexModel[index++]=bottom;  //leftSp-bottom

        mVertexModel[index++]=r;  // position
        mVertexModel[index++]=g;  // g
        mVertexModel[index++]=b;  // b
        mVertexModel[index++]=a;  // a

        // v2
        mVertexModel[index++]=vx+sWidth/2;
        mVertexModel[index++]=vy-sHeight/2;
        mVertexModel[index++]=right;
        mVertexModel[index++]=bottom;  //right-bottom

        mVertexModel[index++]=r;  // position
        mVertexModel[index++]=g;  // g
        mVertexModel[index++]=b;  // b
        mVertexModel[index++]=a;  // a

        //V3
        mVertexModel[index++]=vx+sWidth/2;
        mVertexModel[index++]=vy+sHeight/2;
        mVertexModel[index++]=right;
        mVertexModel[index++]=top;  //right-topSp

        mVertexModel[index++]=r;  // position
        mVertexModel[index++]=g;  // g
        mVertexModel[index++]=b;  // b
        mVertexModel[index++]=a;  // a
    }*/

}
