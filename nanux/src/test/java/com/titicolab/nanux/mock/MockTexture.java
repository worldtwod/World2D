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

import com.titicolab.nanux.graphics.texture.Texture;


public class MockTexture implements Texture {
    private static final int ID = 1;
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 512;
    private static final int RESOURCES = 123456;
    private static final float SCALE_PIXEL = 1;

    final int   id;
    final int   resources;
    final float width;
    final float height;

    public MockTexture(int id, int resources, float width, float height) {
        this.id = id;
        this.resources = resources;
        this.width = width;
        this.height = height;
    }

    public MockTexture(){
        this.id = ID;
        this.resources = RESOURCES;
        this.width = WIDTH;
        this.height = HEIGHT;
    }

    public static Texture getMock(){
        return new MockTexture();
    }



    @Override
    public int getTextureId() {
        return id;
    }

    @Override
    public int getWidth() {
        return (int) width;
    }

    @Override
    public int getHeight() {
        return (int) height;
    }

    @Override
    public int getResources() {
        return resources;
    }

    @Override
    public float getScalePixel() {
        return SCALE_PIXEL;
    }

    @Override
    public int getResourcesType() {
        return 0;
    }
}