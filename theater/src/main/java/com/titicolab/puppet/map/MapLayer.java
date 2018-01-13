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

package com.titicolab.puppet.map;

import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.nanux.objects.map.MapItem;
import com.titicolab.nanux.objects.map.MapObjects;
import com.titicolab.puppet.objects.LayerObject;

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

    MapLayer() {
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
    public int getOffsetX() {
        return offsetX;
    }
    public int getOffsetY() {
        return offsetY;
    }

    protected void  setGridSize(int tilesX, int tilesY) {
        this.tilesX=tilesX;
        this.tilesY = tilesY;
    }

    protected void  setTileSize(int width, int height) {
        this.tileHeight = height;
        this.tileWidth = width;
    }

    protected void  setOffset(int offsetX, int offsetY) {
        this.offsetX=offsetX;
        this.offsetY = offsetY;
    }


    static  class BuilderMapTiledLayer{

    }



    public static  class Builder {

        String name;
        FlexibleList<MapItem> list;

        int tilesX;
        int tilesY;
        int tileWidth;
        int tileHeight;
        int offsetX;
        int offsetY;

        public Builder(){
            list = new FlexibleList<>(10);
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
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

        public Builder setOffset(int offsetX, int offsetY) {
            this.offsetX=offsetX;
            this.offsetY = offsetY;
            return this;
        }


        void checkParameters(){
            if(name==null)
                throw new RuntimeException("The map needs a name, set it with setName");
            if(tilesX==0 || tilesY==0)
                throw new RuntimeException("Before of addNew items to MapLayer you must  setTileSize()");
            if(tileHeight==0 || tileWidth==0){
                throw new RuntimeException("Before of addNew items to MapLayer you must  setWindowSize()");
            }
        }
        public Builder item(Class<? extends LayerObject> clazz,
                            int id, int i, int j, String animation, int startClip) {
            list.add(new MapItem(clazz,id,new LayerObject.Params(i,j,animation,startClip)));
            return this;
        }


        public MapLayer build(){
            checkParameters();
            checkCoordinates();
            checkIds();

            MapLayer mapLayer = new MapLayer();
            mapLayer.setName(name);
            mapLayer.setList(list);
            mapLayer.offsetX = offsetX;
            mapLayer.offsetY = offsetY;
            mapLayer.tileWidth = tileWidth;
            mapLayer.tileHeight = tileHeight;
            mapLayer.tilesX = tilesX;
            mapLayer.tilesY = tilesY;
            return  mapLayer;
        }

        private void checkCoordinates() {
            for (int i = 0; i < list.size(); i++) {
                LayerObject.Params params= (LayerObject.Params)
                        list.get(i).getParameters();
                checkCoordinates(list.get(i).getId(),params.i,params.j);
            }
        }



        private void checkCoordinates(int id, int i, int j) {
            if(i>=tilesX)
                throw new RuntimeException("Error item id=["+ id +"]" +
                        "The coordinate i musts less then: " + tilesX
                        + ". You are trying to tile in: " + i);
            if(j>=tilesY)
                throw new RuntimeException("Error item id=["+ id +"]" +
                        "The coordinate j musts less then: "
                        + tilesY + ". You are trying to tile in: " + j);
        }



        protected void checkIds(){
            int[] equals= getEqualsIds();
            if(equals!=null){
                throw new RuntimeException("Error in the MapWorld, there are items width the same id, " +
                        "type [" + list.get(equals[0]).getType().getSimpleName() + "] " +
                        "id [" + equals[1] + "]");
            }
        }

        private int[] getEqualsIds() {
            int[] result = null;
            for (int i = 0; i < list.size(); i++) {
                MapItem item=list.get(i);
                for (int k = i+1; k < list.size(); k++) {
                    if(item.getId()==list.get(k).getId()){
                        result= new int[]{k,item.getId()};
                        break;
                    }
                }
            }
            return result;
        }
    }

}
