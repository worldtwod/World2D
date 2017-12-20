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

import android.opengl.GLES20;

import com.titicolab.puppet.model.ImageModel;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glVertexAttribPointer;


public class ImageShaderProgram extends ShaderProgram {
    // Uniform locations
    private int uMatrixLocation;



    private int uTextureUnitLocation;

    // Attribute locations
    private  int aPositionLocation;
    private  int aCoordinatesLocation;
    private  int aColorLocation;

    private int   vboIndex;
    private int   vboBuffer;


    public ImageShaderProgram(String slVertex, String slFragment) {
        super(slVertex,slFragment);
    }


    @Override
    public void buildProgram(){
        super.buildProgram();
        setUpLocations();
    }


    private void setUpLocations(){
        uTextureUnitLocation = glGetUniformLocation(mProgramId, U_TEXTURE_UNIT);

        // Pointer to projection matrix
        uMatrixLocation = glGetUniformLocation(mProgramId, U_MATRIX);

        //pointer to send UV coordinates.
        aCoordinatesLocation =glGetAttribLocation(mProgramId, A_TEXTURE_COORDINATES);

        //Pointer to position
        aPositionLocation = glGetAttribLocation(mProgramId, A_POSITION);

        //pointer to send UV coordinates.
        aColorLocation =glGetAttribLocation(mProgramId, A_COLOR);

        int[] ivo = new int[1];
        GLES20.glGenBuffers(1,ivo, 0);
        vboIndex = ivo[0];

        int[] vbo = new int[1];
        GLES20.glGenBuffers(1,vbo, 0);
        vboBuffer = vbo[0];

    }


    public void binMatrix(float[] matrix) {
        // Pass the matrix into the shader mProgramId.
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

    /*
    public void setAttributePosition(FloatBuffer floatBuffer){
        floatBuffer.position(ImageModel.positionOffset);
        glVertexAttribPointer(aPositionLocation,
                ImageModel.positionSize,
                GL_FLOAT,
                false,
                ImageModel.stride,
                floatBuffer);
        glEnableVertexAttribArray(aPositionLocation);
    }

    public void setAttributeCoordinates(FloatBuffer floatBuffer){
        floatBuffer.position(ImageModel.coordinatesOffset);
        glVertexAttribPointer(aCoordinatesLocation,
                ImageModel.coordinatesSize
                ,GL_FLOAT,
                 false,
                ImageModel.stride,
                floatBuffer);
        glEnableVertexAttribArray(aCoordinatesLocation);
    }

    public void setAttributeColor(FloatBuffer floatBuffer){
        floatBuffer.position(ImageModel.colorOffset);
        glVertexAttribPointer(aColorLocation,
                ImageModel.colorSize,
                GL_FLOAT,
                false,
                ImageModel.stride,
                floatBuffer);
        glEnableVertexAttribArray(aColorLocation);
    }*/


    public void draw(ShortBuffer indexBuffer,int index) {
        indexBuffer .position(0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, vboIndex);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBuffer.capacity()
                * ImageModel.BYTES_PER_SHORT, indexBuffer, GLES20.GL_STATIC_DRAW);

        glDrawElements(GL_TRIANGLES,index*ImageModel.indexPerModel, GLES20.GL_UNSIGNED_SHORT,0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
       // glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void binTexture(int textureId) {
        // Set the active texture unit to texture unit 0.
        glActiveTexture(GL_TEXTURE0);

        // Bind the texture to this unit.
        glBindTexture(GL_TEXTURE_2D, textureId);

        // Tell the texture uniform sampler to use this texture in the shader by
        // telling it to read from texture unit 0.
        glUniform1i(uTextureUnitLocation, 0);

    }

    public void binAttributes(FloatBuffer vertexBuffer){
        vertexBuffer.position(0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,vboBuffer);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexBuffer.capacity() * ImageModel.BYTES_PER_FLOAT,
                vertexBuffer, GLES20.GL_STATIC_DRAW);

        glVertexAttribPointer(aPositionLocation,
                ImageModel.positionSize,
                GL_FLOAT,
                false,
                ImageModel.stride,
                0);

        glEnableVertexAttribArray(aPositionLocation);


        glVertexAttribPointer(aCoordinatesLocation,
                ImageModel.coordinatesSize
                ,GL_FLOAT,
                false,
                ImageModel.stride,
                ImageModel.coordinatesOffset* ImageModel.BYTES_PER_FLOAT);
        glEnableVertexAttribArray(aCoordinatesLocation);


        glVertexAttribPointer(aColorLocation,
                ImageModel.colorSize,
                GL_FLOAT,
                false,
                ImageModel.stride,
                ImageModel.colorOffset* ImageModel.BYTES_PER_FLOAT);
        glEnableVertexAttribArray(aColorLocation);

    }


     int getUTextureUnitLocation() {
        return uTextureUnitLocation;
    }


}