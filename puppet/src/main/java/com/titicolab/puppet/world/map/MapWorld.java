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

import com.titicolab.puppet.objects.base.BaseLayer;
import com.titicolab.puppet.objects.base.GroupLayer;
import com.titicolab.puppet.objects.base.Layer;
import com.titicolab.puppet.objects.factory.Parameters;
import com.titicolab.puppet.objects.map.MapGroupLayers;
import com.titicolab.puppet.objects.map.MapItem;


/**
 * Created by campino on 18/01/2017.
 *
 */

public class MapWorld extends MapGroupLayers {

    private int tilesX;
    private int tilesY;
    private int tileWidth;
    private int tileHeight;

    private int     cameraSize;
    private boolean fixCameraHeight;
    private int     foregroundSize;
    private boolean fixForegroundHeight;

    public int      getCameraSize() {
        return cameraSize;
    }
    public boolean  isCameraFixedHeight() {
        return fixCameraHeight;
    }
    public boolean  isForegroundFixedHeight() {
        return fixForegroundHeight;
    }
    public int getFocusedWindowSize() {
        return foregroundSize;
    }

    public int getWidth() {
        return tileWidth*tilesX;
    }
    public int getHeight() {
        return tileHeight*tilesY;
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


    protected void  setGridSize(int tilesX, int tilesY) {
        this.tilesX=tilesX;
        this.tilesY = tilesY;
    }
    protected void  setTileSize(int width, int height) {
        this.tileHeight = height;
        this.tileWidth = width;
    }




    public static  class Builder  extends MapGroupLayers.Builder{
        int tilesX;
        int tilesY;
        int tileWidth;
        int tileHeight;

        private int     cameraSize;
        private boolean fixCameraHeight;
        private int     foregroundSize;
        private boolean fixForegroundHeight;



        public Builder() {
            super();
            fixCameraHeight=true;
            fixForegroundHeight=true;
        }


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


        @Override
        public Builder layer(Class<? extends BaseLayer> clazz, int id) {
            return (Builder) super.layer(clazz, id);
        }

        @Override
        public Builder setName(String name) {
            return (Builder) super.setName(name);
        }

        public Builder setCameraSize(int tiles, boolean fixHeight){
            this.cameraSize=tiles;
            this.fixCameraHeight = fixHeight;
            return this;
        }



        public Builder setFocusedWindowSize(int tiles, boolean fixHeight) {
            this.foregroundSize = tiles;
            this.fixForegroundHeight = fixHeight;
            return this;
        }

        @Override
        public Builder layer(Class<? extends BaseLayer> clazz, int id, Parameters params){
            checkBaseLayerParams(clazz,id,params);
            getList().add(new MapItem(clazz,id,params));
            return this;
        }



        @Override
        public MapWorld build(){
            checkParameters();
            MapWorld mapWord = new MapWorld();
            mapWord.setName(getName());
            mapWord.setList(getList());
            mapWord.setTileSize(tileWidth,tileHeight);
            mapWord.setGridSize(tilesX,tilesY);
            mapWord.fixCameraHeight = fixCameraHeight;
            mapWord.fixForegroundHeight = fixForegroundHeight;
            mapWord.cameraSize = cameraSize;
            mapWord.foregroundSize = foregroundSize;
            return  mapWord;
        }


        void checkParameters(){
            if(getName() ==null)
                throw new RuntimeException("The map needs a name, set it with setName()");
            if(tilesX==0 || tilesY==0)
                throw new RuntimeException("The map needs a size, set it with setTileSize()");
            if(tileHeight==0 || tileWidth==0){
                throw new RuntimeException("The map needs a grid, set it with setWindowSize()");
            }
        }

        private void checkBaseLayerParams(Class<? extends BaseLayer> clazz,int id, Parameters params) {
                if(clazz.isAssignableFrom(Layer.class) &&
                        !Layer.ParamsLayer.class.isAssignableFrom(params.getClass())){
                    throw new RuntimeException("The layer width id [" + id + "]" +
                            " needs parameters type Layer.ParamsLayer ");
                }else if(clazz.isAssignableFrom(GroupLayer.class) &&
                        !GroupLayer.ParamsGroupLayer.class.isAssignableFrom(params.getClass())){
                    throw new RuntimeException("The GroupLayer width id [" + id + "]" +
                            " needs parameters type Layer.ParamsGroupLayer ");
                }
        }
    }
}
