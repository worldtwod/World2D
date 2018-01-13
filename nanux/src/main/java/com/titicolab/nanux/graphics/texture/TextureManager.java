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

package com.titicolab.nanux.graphics.texture;


import com.titicolab.nanux.core.RunnerTask;

/**
 * Created by campino on 12/05/2016.
 *
 */

public interface TextureManager {



    public int size();


    /**
     * Decode a resource and getBuildCommand a GLTexture from it.
     * @param resourcesId resources id
     * @return GLTexture with description width height id etc...
     */
    public Texture getTexture(int resourcesId);

    public void freeGLTexture(int resourcesId);

    /** Delete all textures that manager has loaded in the GPU,
     * It does not release the hasMap
     ***/
    public void freeAllGLTextures();

    public void asyncRecoveryTexturesIds();

    public void recoverAllTextures();

    public boolean isLock();


    RunnerTask getRunnerTask();

}
