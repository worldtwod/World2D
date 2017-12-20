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


import com.titicolab.nanux.graphics.drawer.Drawer;
import com.titicolab.puppet.draw.Geometry;
import com.titicolab.puppet.model.BaseModel;
import com.titicolab.puppet.model.ModelBuffer;

/**
 * Created by campino on 19/06/2016.
 *
 */
public  class DrawerGeometry extends BaseDrawer<Geometry> implements Drawer.Brush<Geometry> {

    private static final int   MAX_FLOAT_PER_MODEL = 4 * 2;
    private static final int   MAX_SHORT_PER_INDEX = 4;

    private static final  int  MAX_BYTE_MODEL = MAX_FLOAT_PER_MODEL*BaseModel.BYTES_PER_FLOAT;
    private static  final int  MAX_BYTE_INDEX = MAX_SHORT_PER_INDEX* BaseModel.BYTES_PER_SHORT;


    private float mColor[];
    private float mLineWidth;

    public DrawerGeometry(int size, GeometryShaderProgram program) {
        super(size, program);

        mModelBuffer = new ModelBuffer(size,MAX_BYTE_MODEL,MAX_BYTE_INDEX);

        mColor = new float[4];
        mColor[0]=1;
        mColor[1]=1;
        mColor[2]=1;
        mColor[3]=1;
        mLineWidth =1.0f;
        mStatus = STATUS_END;
    }


    @Override
    public void begin(float[] matrix){
        if(mStatus==STATUS_BEGIN)
            throw  new IllegalStateException("The drawer was set to STATUS_BEGIN");

        mStatus =STATUS_BEGIN;
        getShaderProgram().use();
        getShaderProgram().binMatrix(matrix);
        getShaderProgram().binColor(mColor);
        getShaderProgram().binLineWidth(mLineWidth);
        reset();
        resetDrawOperations();
    }



    /*@Override
    public void add(Geometry geometry){
        super.add(geometry);
        mModelBuffer.add(geometry.getDrawModel());
    }
    @Override
    protected void draw(){
        if(isEmpty())return;
        getShaderProgram().binAttributes(mModelBuffer.getVertexBuffer());
        getShaderProgram().draw(mModelBuffer.getIndexBuffer(), mModelBuffer.size());
        reset();
        mModelBuffer.reset();
    }*/


    @Override
    public void add(Geometry geometry){
        if(!isEmpty() && (
                getCurrent().getDrawModel().getDrawMode()!=geometry.getDrawModel().getDrawMode()
             || getCurrent().getDrawModel().getIndexPerModel()!= geometry.getDrawModel().getIndexPerModel()
        )){
            draw();
        }

        super.add(geometry);
        mModelBuffer.add(geometry.getDrawModel());
    }

    @Override
    protected void draw(){
        if(isEmpty())return;

        getShaderProgram().binGeometryModel( getCurrent().getDrawModel());
        getShaderProgram().binAttributes(mModelBuffer.getVertexBuffer());
        getShaderProgram().draw(mModelBuffer.getIndexBuffer(), mModelBuffer.size());
        reset();
        mModelBuffer.reset();
        addDrawOperation();
    }




    @Override
    public void setColor(float r, float g, float b, float a){
        mColor[0]=r;
        mColor[1]=g;
        mColor[2]=b;
        mColor[3]=a;
    }


    @Override
    public void setBrushSize(float lineWidth) {
        mLineWidth = lineWidth;
    }

    protected GeometryShaderProgram getShaderProgram(){
        return (GeometryShaderProgram) super.getShaderProgram();
    }
}
