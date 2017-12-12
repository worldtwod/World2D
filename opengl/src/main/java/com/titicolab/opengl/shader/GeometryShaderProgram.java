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

import com.titicolab.puppet.model.BaseModel;
import com.titicolab.puppet.model.GeometryModel;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glLineWidth;
import static android.opengl.GLES20.glUniform4fv;
import static android.opengl.GLES20.glUniformMatrix4fv;


public class GeometryShaderProgram extends ShaderProgram {
    // Uniform locations
    private int uMatrixLocation;
    private int uColor;

    // Attribute locations
    private  int aPositionLocation;
    private  int aColorLocation;

    private int   vboIndex;
    private int   vboBuffer;

    public GeometryShaderProgram(String vertexShader, String fragmentShader) {
        super(vertexShader, fragmentShader);
    }


    @Override
    public void buildProgram(){
        super.buildProgram();
        setUpLocations();
    }

    private void setUpLocations(){

        // Pointer to projection matrix
        uMatrixLocation = glGetUniformLocation(mProgramId, U_MATRIX);

        uColor = glGetUniformLocation(mProgramId, U_COLOR);

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

    @Override
    public void binMatrix(float[] matrix) {
        // Pass the matrix into the shader mProgramId.
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

    void binLineWidth(float lineWidth) {
        glLineWidth(lineWidth);
    }

    void binColor(float[] color) {
        glUniform4fv(uColor,1, color, 0);
    }

    @Override
    public void binTexture(int gLid) {

    }

    @Override
    public void binAttributes(FloatBuffer vertexBuffer){
        vertexBuffer.position(0);
        //log.info(IOUtils.toString(vertexBuffer));
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,vboBuffer);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexBuffer.capacity() * BaseModel.BYTES_PER_FLOAT,
                vertexBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glVertexAttribPointer(aPositionLocation,
                GeometryModel.positionSize,
                GL_FLOAT,
                false,
                GeometryModel.stride,
                0);

        glEnableVertexAttribArray(aPositionLocation);

    }

    @Override
    public void draw(ShortBuffer indexBuffer,int index) {
        indexBuffer .position(0);

       //log.info(IOUtils.toString(indexBuffer));

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, vboIndex);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBuffer.capacity()
                * BaseModel.BYTES_PER_SHORT, indexBuffer, GLES20.GL_STATIC_DRAW);


        glDrawElements(GL_LINES,index* GeometryModel.indexPerModel, GLES20.GL_UNSIGNED_SHORT,0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
        // glBindTexture(GL_TEXTURE_2D, 0);

    }


}