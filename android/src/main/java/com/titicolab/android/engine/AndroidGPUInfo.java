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

package com.titicolab.android.engine;

import android.opengl.GLES20;

import com.titicolab.nanux.util.GPUInfo;

import javax.microedition.khronos.egl.EGLConfig;

import static android.opengl.GLES20.glGetIntegerv;

/**
 * Created by campino on 19/05/2016.
 *
 */
public class AndroidGPUInfo implements GPUInfo {

    private int  maxTextureSideSize;
    private  int maxTextureUnits;
    private  int maxRenderBufferSize;
    private int  maxVertexAttribs;

    public AndroidGPUInfo(EGLConfig config) {
        if(config==null)
            throw new IllegalAccessError("There are not gl context");
        updateInfo();
    }

    private void updateInfo(){
        int[] textureUnits = new int[1];
        int[] textureSize = new int[1];
        int[] renderBufferSize = new int[1];
        int[] tem=new int[1];

        glGetIntegerv(GLES20.GL_MAX_TEXTURE_IMAGE_UNITS,textureUnits,0);
        glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE,textureSize,0);
        glGetIntegerv(GLES20.GL_MAX_RENDERBUFFER_SIZE,renderBufferSize,0);
        maxTextureUnits=textureUnits[0];
        maxTextureSideSize= textureSize[0];
        maxRenderBufferSize=renderBufferSize[0];
        glGetIntegerv(GLES20.GL_MAX_VERTEX_ATTRIBS,tem,0);
        maxVertexAttribs=tem[0];
    }

    public  int getMaxTextureSideSize() {
        return maxTextureSideSize;
    }

    public  int getMaxTextureUnits() {
        return maxTextureUnits;
    }

    public  int getMaxRenderBufferSize() {
        return maxRenderBufferSize;
    }

    public int getMaxVertexAttribs() {
        return maxVertexAttribs;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n---  Android GPU Metrics ---------");
        buffer.append("\nMaxTextureSideSize:  " + getMaxTextureSideSize());
        buffer.append("\nMaxMaxTextureUnits:  " + getMaxTextureUnits());
        buffer.append("\nMaxRenderBufferSize: " + getMaxRenderBufferSize());
        buffer.append("\nMaxVertexAttribs:    " + getMaxVertexAttribs());
        buffer.append("\n");

        return  buffer.toString();
    }

}
