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

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glValidateProgram;


public abstract class ShaderProgram  {
    // Uniform constants
    protected static final String U_COLOR = "u_Color";
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    // Attribute constants
    protected static final String A_POSITION =  "a_Position";
    protected static final String A_COLOR =     "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    // Shader mProgramId
    int mProgramId;

    private static boolean sDebug;
    private final String vertexShader;
    private final String fragmentShader;


    ShaderProgram(String vertexShader, String fragmentShader) {
       this.vertexShader= vertexShader;
       this.fragmentShader = fragmentShader;
    }

    protected void buildProgram(){
        mProgramId = buildProgram(vertexShader,fragmentShader);
    }

    private static int buildProgram(String vertexShaderSource, String fragmentShaderSource) {
        int program;
        // Compile the shader.
        int vertexShader = compileShader(GL_VERTEX_SHADER, vertexShaderSource);
        int fragmentShader = compileShader(GL_FRAGMENT_SHADER, fragmentShaderSource);
        // Link them into a shader mProgramId.
        program = linkProgram(vertexShader, fragmentShader);

			if (sDebug) {
			    validateProgram(program);
			}

        return program;
    }



    private static int compileShader(int type, String shaderCode) {
        final int[] compileStatus = new int[1];

        // crea y genera el id del nuevo objeto shader
        final int shaderObjectId = glCreateShader(type);

        if(shaderObjectId==0)
            throw new RuntimeException("No se pudo clear el shader:\n" + shaderCode);

        //updateRenderObjects  el shader en la GPU
        glShaderSource(shaderObjectId, shaderCode);

        //Compile el shader
        glCompileShader(shaderObjectId);

        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);

        if (compileStatus[0] == 0){
            glDeleteShader(shaderObjectId);
            throw new RuntimeException("No se pudo compilar el shader:\n" + shaderCode);
        }

        return shaderObjectId;
    }


    private static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        final int[] linkStatus = new int[1];
        final int programObjectId = glCreateProgram();

        if (programObjectId == 0){
            throw new RuntimeException("No se pudo clear el programa");
        }

        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);

        glLinkProgram(programObjectId);

        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);

        if (linkStatus[0] == 0) {
            // If it failed, delete the mProgramId object.
            glDeleteProgram(programObjectId);
            throw new RuntimeException("No se pudo enlazar el programa");
        }

        return programObjectId;
    }


    private static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);
        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        return validateStatus[0] != 0;
    }


    public static void setDebug(boolean sDebug) {
        ShaderProgram.sDebug = sDebug;
    }

    public int getProgramId() {
        return mProgramId;
    }

    public void use() {
        glUseProgram(mProgramId);
    }

    public abstract void binMatrix(float[] matrix);

    protected abstract void binTexture(int gLid);

    protected abstract void binAttributes(FloatBuffer vertexBuffer);

    protected abstract void draw(ShortBuffer indexBuffer, int size);
}