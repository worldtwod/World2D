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

package com.titicolab.puppet.world.map;

import com.titicolab.puppet.objects.map.MapGroupLayers;

/**
 * Created by campino on 18/01/2017.
 *
 */

public class MapWorld extends MapGroupLayers {

    private int     cameraSize;
    private boolean fixCameraHeight;
    private int     tilesX;
    private int     tilesY;
    private int     tileHeight;
    private int     tileWidth;
    private int     foregroundSize;
    private boolean fixForegroundHeight;

    public int     getCameraSize() {
        return cameraSize;
    }
    public boolean isCameraFixedHeight() {
        return fixCameraHeight;
    }
    public boolean isForegroundFixedHeight() {
        return fixForegroundHeight;
    }
    public int getForegroundSize() {
        return foregroundSize;
    }
    public int getTilesX() {
        return tilesX;
    }
    public int getTilesY() {
        return tilesY;
    }
    public int getTileWidth() {
        return tileWidth;
    }
    public int getTileHeight() {
        return tileHeight;
    }
    public int getWidth() {
        return tileWidth*tilesX;
    }
    public int getHeight() {
        return tileHeight*tilesY;
    }


}
