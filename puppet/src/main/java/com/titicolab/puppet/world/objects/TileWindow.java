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

package com.titicolab.puppet.world.objects;


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
    
    private int layerTilesX;
    private int layerTilesY;
    private int tileWidth;
    private int tileHeight;

    public TileWindow() {

        
    }


    public void setLayerSize(int tilesX, int tilesY){
            layerTilesX =tilesX;
            layerTilesY =tilesY;
    }

    public void setTileSize(int width, int height){
        tileWidth = width;
        tileHeight = height;
    }

    /**
     * Size in tiles, the size must be a odd number.
     * @param tileX number of tiles in x axis
     * @param tilesY number of tiles in y axis
     */
    public void setWindowSize(int tileX, int tilesY) {
        checkOdd(tileX,tilesY);
        tilesX = tileX;
        this.tilesY = tilesY;
    }


    public void setWindowSize(int size, boolean fixHeight, float cameraRatio){

        checkParameters(size,cameraRatio);

        int tilesX;
        int tilesY;

        if(fixHeight){
            tilesY = size;
            tilesX = (int) (size*cameraRatio);
        }else{
            tilesY = (int) (size*cameraRatio);
            tilesX = size;
        }


        //if the size result no odd
        tilesX = tilesX%2==0? tilesX+1: tilesX;
        tilesY = tilesY%2==0? tilesY+1: tilesY;

        setWindowSize(tilesX,tilesY);
    }


    
    protected void updateWindows(int x, int y){
        setPosition(x,y);
        updateWindows();
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

        if(iEnd>= layerTilesX){
            iStart = layerTilesX - tilesX;
            iEnd = layerTilesX -1;
        }
        if(jEnd>= layerTilesY){
            jStart = layerTilesY - tilesY;
            jEnd = layerTilesY -1;
        }
    }
    

    private boolean isInsideWindow(LayerObject layerObject) {
        boolean vertical = iStart <= layerObject.getI() && layerObject.getI() <= iEnd;
        boolean horizontal = jStart <= layerObject.getJ() && layerObject.getJ() <= jEnd;
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


    public int getIStart() {
        return iStart;
    }

    public int getJStart() {
        return jStart;
    }

    public int getIEnd() {
        return iEnd;
    }

    public int getJEnd() {
        return jEnd;
    }

    protected void setPosition(int x, int y){
        i = x/tileWidth;
        j = y/tileHeight;
    }

    public void setPositionIj(int i, int j) {
        this.i=i;
        this.j=j;
    }
}
