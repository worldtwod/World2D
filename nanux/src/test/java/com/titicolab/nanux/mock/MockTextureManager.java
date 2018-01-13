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

package com.titicolab.nanux.mock;

import com.titicolab.nanux.core.RunnerTask;
import com.titicolab.nanux.graphics.texture.Texture;
import com.titicolab.nanux.graphics.texture.TextureManager;

/**
 * Created by campino on 04/12/2017.
 *
 */

public class MockTextureManager implements TextureManager {

    @Override
    public int size() {
        return 2;
    }

    @Override
    public Texture getTexture(int resourcesId) {
        return new MockTexture();
    }

    @Override
    public void freeGLTexture(int resourcesId) {

    }

    @Override
    public void freeAllGLTextures() {

    }

    @Override
    public void asyncRecoveryTexturesIds() {

    }

    @Override
    public void recoverAllTextures() {

    }

    @Override
    public boolean isLock() {
        return false;
    }

    @Override
    public RunnerTask getRunnerTask() {
        return null;
    }
}
