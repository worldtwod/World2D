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

package com.titicolab.puppet.objects;


import com.titicolab.nanux.list.FlexibleList;

/**
 * Created by campino on 18/01/2017.
 *
 */

public class TileWindow {
    
    private int tilesX;
    private int tilesY;
    
    private int iStart;
    private int jStart;
    private int iEnd;
    private int jEnd;
    private int i;
    private int j;
    
    private int worldTilesX;
    private int worldTilesY;
    private int tileWidth;
    private int tileHeight;
    private FlexibleList<Tile> mListTile;
    private FlexibleList<Actor> mListActor;

    public TileWindow() {

    }


    public void setWorldSize(int tilesX, int tilesY){
            worldTilesX =tilesX;
            worldTilesY =tilesY;
    }

    public void setTileSize(int width, int height){
        tileWidth = width;
        tileHeight = height;
    }

    /**
     * Size in tiles, the size must be a odd number.
     * @param tileX number of tiles in x axis
     * @param tileY number of tiles in y axis
     */
    public void setWindowSize(int tileX, int tileY) {
        checkOdd(tileX,tileY);
        this.tilesX = tileX;
        this.tilesY = tileY;
        setupLists();
    }

    private void setupLists() {
        mListTile = new FlexibleList<>(tilesX*tilesY);
        mListActor = new FlexibleList<>(tilesX*tilesY);
    }


    public void setWindowSize(int size, boolean fixHeight, float cameraRatio){

        checkParameters(size,cameraRatio);

        int tilesX;
        int tilesY;

        if(fixHeight){
            tilesY = size;
            tilesX = (int) (size*cameraRatio);
        }else{
            tilesY = (int) (size/cameraRatio);
            tilesX = size;
        }


        //if the size result no odd
        tilesX = tilesX%2==0? tilesX+1: tilesX;
        tilesY = tilesY%2==0? tilesY+1: tilesY;

        setWindowSize(tilesX,tilesY);
    }


    private void updateWindows(){

        iStart = i - tilesX /2;
        jStart = j - tilesY /2;
        iEnd =i + tilesX /2;
        jEnd = j + tilesY /2;


        if(iStart<0){
            iStart=0;
            iEnd = tilesX -1;
        }
        if(jStart<0){
            jStart=0;
            jEnd = tilesY -1;
        }

        if(iEnd>= worldTilesX){
            iStart = worldTilesX - tilesX;
            iEnd = worldTilesX -1;
        }
        if(jEnd>= worldTilesY){
            jStart = worldTilesY - tilesY;
            jEnd = worldTilesY -1;
        }
    }
    

    public boolean isFocused(LayerObject layerObject) {
        int objectI = (int) ((layerObject.getX()/tileWidth));
        int objectJ = (int) ((layerObject.getY()/tileHeight));

        boolean vertical = iStart <= objectI && objectI <= iEnd;
        boolean horizontal = jStart <= objectJ && objectJ <= jEnd;
        return vertical && horizontal;
    }

    private void checkOdd(int tilesX, int tilesY) {
        if(tilesX%2==0) throw new RuntimeException("The tiles in X must be a odd number");
        if(tilesY%2==0) throw new RuntimeException("The tiles in Y must be a odd number");
    }

    private void checkParameters(int size, float cameraRatio) {
        if(size==0) throw new RuntimeException("The size must be different to zero");
        if(cameraRatio==0) throw new RuntimeException("The cameraRatio must be different to zero");
    }


    public void setPosition(float x, float y){
        checkTileSize();
        i = (int) (x/tileWidth);
        j = (int) (y/tileHeight);
        updateWindows();
    }

    public void setPositionIj(int i, int j) {
        this.i=i;
        this.j=j;
        checkTileSize();
        updateWindows();
    }

    private void checkTileSize() {
        if(tileWidth==0) throw new RuntimeException("The tilesWidth can not be zero");
        if(tileHeight==0) throw new RuntimeException("The tilesHeight can not be zero");
    }

    public int getTilesX() {
        return tilesX;
    }

    public int getTilesY() {
        return tilesY;
    }


   void updateFocusTiles(FlexibleList<Tile> objectCollection){
        int sizeObject =objectCollection.size();
        mListTile.reset();

        for (int item = 0; item < sizeObject; item++) {
            boolean isFocused = isFocused(objectCollection.get(item));
            objectCollection.get(item).setUpdatable(isFocused );
            objectCollection.get(item).setDrawable(isFocused );
            mListTile.add(objectCollection.get(item));
        }
    }

    void updateFocusObjects(FlexibleList<Actor> objectCollection){
        int sizeObject =objectCollection.size();
        mListActor.reset();
        for (int item = 0; item < sizeObject; item++) {
            boolean isFocused = isFocused(objectCollection.get(item));
            objectCollection.get(item).setUpdatable(isFocused );
            objectCollection.get(item).setDrawable(isFocused );
            mListActor.add(objectCollection.get(item));
        }
    }

    public FlexibleList<Tile> getTileList() {
        return mListTile;
    }

    public FlexibleList<Actor> getActorList() {
        return mListActor;
    }
}
