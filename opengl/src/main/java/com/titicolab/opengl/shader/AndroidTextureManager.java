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

import androidx.annotation.NonNull;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

import android.util.SparseArray;

import com.titicolab.nanux.core.RunnableTask;
import com.titicolab.nanux.core.RunnerTask;
import com.titicolab.nanux.graphics.texture.TextureManager;
import com.titicolab.nanux.util.DisplayInfo;
import com.titicolab.opengl.util.LogHelper;

import java.util.Arrays;

import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLUtils.getEGLErrorString;
import static android.opengl.GLUtils.texImage2D;

/**
 * Created by campino on 12/05/2016.
 *
 */

public class AndroidTextureManager implements TextureManager{

    private static final int MAX_FILES = 100;
    private static final int RESOURCE_BITMAP = -10;
    private final RunnerTask mRunnerTask;
    private final LogHelper log;
    private DisplayInfo mDisplayInfo;
    private SparseArray<GLTexture> hashMap;
    private Context mContext;
    private boolean mLock;


    public AndroidTextureManager(@NonNull Context context,
                                 @NonNull RunnerTask runnerTask,
                                 @NonNull DisplayInfo displayInfo){
        //setUpLog("MainActivity");
        log = new LogHelper(this,"GameActivity");
        mDisplayInfo = displayInfo;
        hashMap = new SparseArray<>(MAX_FILES);
        mRunnerTask = runnerTask;
        mContext=context;
        //checkRunnerTaskUpdateThread(mRunnerTask);
    }


    public AndroidTextureManager(AndroidTextureManager textureManager) {
        log = new LogHelper(this, "GameActivity");
        hashMap = new SparseArray<>(MAX_FILES);
        mDisplayInfo = textureManager.getDisplayInfo();
        mRunnerTask = textureManager.getRunnerTask();
        mContext = textureManager.getContext();
        checkRunnerTaskUpdateThread(mRunnerTask);
    }



    public int size(){
        return hashMap.size();
    }



    private Bitmap decodeResources(Context context, int resourcesId){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inMutable = true;
        // Read in the resource
        Bitmap bitmap = BitmapFactory.decodeResource(
                context.getResources(),resourcesId, options);
        if (bitmap == null) {
            throw new RuntimeException("Can not decode the resource: " +
                       resourcesId);
        }
        return scaleBitmap(bitmap);
    }


    /** Scale the bit map related to mDisplayInfo.getScalePixel()
     *  it include the Big screen compensation
     * @param bitmap bit map to scale
     * @return bitmap scaled
     */
    private Bitmap scaleBitmap(Bitmap bitmap){
        Bitmap scaledBitmap;
        float scale = mDisplayInfo.getScalePixel();
        if(scale<1.0) {
            int scaledWidth = Math.round(bitmap.getWidth() * scale);
            int scaledHeight = Math.round(bitmap.getHeight() * scale);
            scaledBitmap= Bitmap.createScaledBitmap(
                    bitmap,scaledWidth,scaledHeight,true);
        }else
            scaledBitmap = bitmap;

        return scaledBitmap;
    }


    /**
     * Decode a resource and getBuildCommand a GLTexture from it.
     * @param resourcesId resources id
     * @return GLTexture with description width height id etc...
     */
      public GLTexture getTexture(int resourcesId){
        GLTexture texture;
        boolean hasResource = hashMap.indexOfKey(resourcesId)>=0;
        if(!hasResource){
            Bitmap bitmap = decodeResources(mContext,resourcesId);
            // it will be executed in gl Thread
            int glTextureId = genIdAndLoad(bitmap);
            texture = new GLTexture(glTextureId,
                    bitmap.getWidth(),
                    bitmap.getHeight(),
                    resourcesId, mDisplayInfo.getScalePixel());

            bitmap.recycle();
            hashMap.put(resourcesId,texture);
            log.info("New key and texture id: " + glTextureId);
        }else{
            texture = hashMap.get(resourcesId);
        }
        return texture;
    }

    /**
     * Decode a resource and getBuildCommand a GLTexture from it.
     * @param resourcesId resources id
     * @return GLTexture with description width height id etc...
     */
    public GLTexture getTexture(@NonNull Bitmap rawBitmap, int resourcesId){
        GLTexture texture;
        boolean hasResource = hashMap.indexOfKey(resourcesId)>=0;
        if(hasResource) {
            freeGLTexture(resourcesId);/*
            throw new RuntimeException("Already, there is a texture width this resourceId." +
                    " Change the  resourcesId or free the texture width freeGLTexture");
                    */
        }

        Bitmap bitmap = scaleBitmap(rawBitmap);

        // it will be executed in gl Thread
        int glTextureId = genIdAndLoad(bitmap);
        texture = new GLTexture(glTextureId,
                bitmap.getWidth(),
                bitmap.getHeight(),
                resourcesId, mDisplayInfo.getScalePixel());

        texture.setResourcesType(GLTexture.BITMAP_RESOURCE);
        hashMap.put(resourcesId,texture);
        //bitmap.recycle();

        log.info("New key and texture id: " + glTextureId);
        return texture;
    }


    private int genIdAndLoad(final Bitmap bitmap) {
        final int[] glTextureId = new int[1];
        mRunnerTask.runAndWait(new RunnableTask() {
            @Override
            public void run() {
                int textureId = genGLTextureId();
                loadGLTexture(textureId,bitmap);
                glTextureId[0] = textureId;
            }
        });
        return glTextureId[0];
    }


    /**
     * This method need be called in GLThread, otherwise it does not getBuildCommand a GLTextureId
     * Generates a texture ID from GL, it is glGenTextures()
     * @return a valid GL texture ID
     */
    private int genGLTextureId(){
        final int[] textureIds = new int[1];
        glGenTextures(1, textureIds, 0);
        if (textureIds[0] == 0) {
            String error = getGLError("Cannot generate the texture id," +
                    " check that you are into GL context thread. " +
                    "The return textureId: " + textureIds[0]);
            throw new RuntimeException(error);
        }
        return textureIds[0];
    }


    /**
     * This method need be called in GLThread. it loads a bitmap to GUP memory
     *
     * @param textureId the textureId got with genIdAndLoad()
     * @param bitmap bitmap of texture
     */
    private void loadGLTexture(int textureId, Bitmap bitmap) {


        // Bind to the texture in OpenGL
        glBindTexture(GL_TEXTURE_2D, textureId);

        // Set filtering: a default must be setClip, or the texture will be
        // black.
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        getGLError("GL_TEXTURE_MIN_FILTER");
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        // Load the bitmap into the bound texture.
        texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

        // Note: Following code may cause an error to be reported in the
        // ADB log as follows: E/IMGSRV(20095): :0: HardwareMipGen:
        // Failed to generate texture mipmap levels (error=3)
        // No OpenGL error will be encountered (glGetError() will return
        // 0). If this happens, just squash the source image to be
        // square. It will look the same because of texture coordinates,
        // and mipmap generation will work.
        glGenerateMipmap(GL_TEXTURE_2D);

        // Unbind from the texture.
        glBindTexture(GL_TEXTURE_2D, 0);
    }


    public void freeGLTexture(int resourcesId){
        int size = hashMap.size();
        GLTexture glTexture = null;
        if(size>0) {
            glTexture = hashMap.get(resourcesId);
            if( glTexture==null)
                return;
            hashMap.remove(resourcesId);
        }

        int textureId[] = new int[1];
        textureId[0]= glTexture.getTextureId();
        glTexture.setGlTextureId(-1);
        freeGLTextures(1,textureId);
    }


    /** Delete all textures that manager has loaded in the GPU,
     * It does not release the hasMap
     ***/
    public void freeAllGLTextures(){
        int size = hashMap.size();
        if(size==0) return;

        mLock=true;

        int textureId[] = new int[size];
        for(int i=0; i<size; i++){
            int key = hashMap.keyAt(i);
            GLTexture glTexture = hashMap.get(key);
            textureId[i]= glTexture.getTextureId();
            glTexture.setGlTextureId(-1);
        }

        freeGLTextures(size,textureId);
    }

    /** Free all textures from textureId, this operation will
     * be executed in GL thread through runnerTask **/
    private void freeGLTextures(final int size, final int[] textureId) {
        mRunnerTask.queueTask(new RunnableTask() {
            @Override
            public void run() {
                glDeleteTextures(size,textureId,0);
                log.debug("glDeleteTextures " + Arrays.toString(textureId));
            }
        });
    }


    public void asyncRecoveryTexturesIds(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                recoverAllTextures();
            }
        }).start();
    }


    public void recoverAllTextures(){
        int size = hashMap.size();
        int textureId[] = new int[size];

        for(int i=0; i<size; i++){
            int key = hashMap.keyAt(i);
            GLTexture glTexture = hashMap.get(key);
            Bitmap bitmap = decodeResources(mContext,glTexture.getResources());
            int glTextureId = genIdAndLoad(bitmap);
            textureId[i]=glTextureId;
            glTexture.setGlTextureId(glTextureId);
            bitmap.recycle();
        }

        log.debug("recovered Textures ids" + Arrays.toString(textureId));
        mLock=false;
    }





    private void checkRunnerTaskUpdateThread(RunnerTask runnerTask) {
        try{
            runnerTask.getRunnerThread().getName();
        }catch (RuntimeException e){
            throw new RuntimeException("The name of update thread is null, " +
                    "it needs be set before");
        }
    }


    private String getGLError(String op) {
        int error;
        StringBuilder errorLog= new StringBuilder(op + ": \n");
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            errorLog.append("glError ")
                    .append(error)
                    .append(" :")
                    .append(getEGLErrorString(error))
                    .append("\n");
        }
        return errorLog.toString();
    }

    private Context getContext() {
        return mContext;
    }

    public RunnerTask getRunnerTask() {
        return mRunnerTask;
    }


    public boolean isLock() {
        return mLock;
    }

    public DisplayInfo getDisplayInfo() {
        return mDisplayInfo;
    }
}
