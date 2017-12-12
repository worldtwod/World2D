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

package com.titicolab.puppet.objects.map;

/**
 * Created by campino on 20/12/2016.
 *
 */

public class MapLayer extends MapObjects {

    private int tilesX;
    private int tilesY;
    private int tileWidth;
    private int tileHeight;

    private int offsetX;
    private int offsetY;


    public MapLayer() {
        super();
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



    public static  class Builder{
        private int tilesX;
        private int tilesY;
        private int tileWidth;
        private int tileHeight;
        private int offsetX;
        private int offsetY;


        public Builder setGridSize(int tilesX, int tilesY) {
            this.tilesX=tilesX;
            this.tilesY = tilesY;
            return this;
        }

        public Builder setTileSize(int width, int height) {
            this.tileHeight = height;
            this.tileWidth = width;
            return this;
        }

        public Builder setOffset(int offsetX, int offsetY) {
            this.offsetX=offsetX;
            this.offsetY = offsetY;
            return this;
        }
        


        public MapLayer build(){
            MapLayer mapLayer = new MapLayer();
            mapLayer.offsetX = offsetX;
            mapLayer.offsetY = offsetY;
            mapLayer.tileWidth = tileWidth;
            mapLayer.tileHeight = tileHeight;
            mapLayer.tilesX = tilesX;
            mapLayer.tilesY = tilesY;
            return  mapLayer;
        }

    }

}
