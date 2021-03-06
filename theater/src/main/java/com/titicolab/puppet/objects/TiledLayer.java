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


import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.nanux.graphics.draw.Drawer;
import com.titicolab.nanux.list.FixList;
import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.FlagSync;
import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.graphics.draw.Image;
import com.titicolab.nanux.list.GameObjectCollection;
import com.titicolab.nanux.objects.base.Camera2D;
import com.titicolab.nanux.objects.base.HelperObjects;
import com.titicolab.nanux.objects.base.Layer;
import com.titicolab.nanux.objects.base.Scene;
import com.titicolab.puppet.map.MapLayer;
import com.titicolab.puppet.map.MapWorld;

public  class TiledLayer extends Layer {

    private WorldObjectCollection mCollection;

    private final FlagSync        mFlagOnCreatedObjects;

    private  Camera2D             mCamera;

    private TileWindow            mTileWindow;


    public TiledLayer() {
        mFlagOnCreatedObjects = new FlagSync();
    }


    /**
     * Try to get the map from this, otherwise check in the resources.
     * @return
     */
    @Override
    protected MapLayer onDefineMapObjects() {
        return getParameters()!=null?
                getParameters().getMapObjects(): new MapLayer.Builder().build();
    }


    @Override
    protected void onAttachScene(Scene scene) {
        super.onAttachScene(scene);
        mCamera = getScene().getCamera2D();
    }

    @Override
    protected void onAttachObjects(GameObjectCollection collection) {
        super.onAttachObjects(collection);
        onAttachFocusWindows();
        mCollection = new WorldObjectCollection(collection);
        mCollection.sortLeftBottomToRightTop(getMapObjects().getTilesX());
        notifyAttachLayer();
        mFlagOnCreatedObjects.assertFlag();
        updateParametersCamera(mCamera); // update size and position of every tile
        HelperObjects.notifyOnCreated(collection);
    }



    @Override
    public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
        return super.onDefineAnimations(builder);
    }


    @Override
    protected void onGroupObjectsCreated() {


    }

    @Override
    protected void updateLogic() {
        if(isUpdatable()) {
            mTileWindow.setPosition(mCamera.getX(),mCamera.getY());
            mTileWindow.updateFocusTiles(mCollection.getTileList());
            mTileWindow.updateFocusObjects(mCollection.getActorList());
            HelperObjects.updateLogicObjects( mTileWindow.getTileList());
            HelperObjects.updateLogicObjects( mTileWindow.getActorList());
        }
    }

    @Override
    public void updateRender() {
        if(isDrawable()) {
            updateParametersCamera(mCamera);
            HelperObjects.updateRenderObjects(mTileWindow.getTileList());
            HelperObjects.updateRenderObjects(mTileWindow.getActorList());
        }
    }

    @Override
    protected void onDraw(DrawTools drawer) {
        if (isDrawable()) {
            onDrawGameObjects(drawer.images);
        }
    }

    private void onDrawGameObjects(Drawer<Image> spriteDrawer) {
            spriteDrawer.begin(getCamera2D().getProjection().getMatrix());
                HelperObjects.drawGameObjects(spriteDrawer,mTileWindow.getTileList());
                HelperObjects.drawGameObjects(spriteDrawer,mTileWindow.getActorList());
            spriteDrawer.end();
    }


    @Override
    protected boolean onTouch(ObservableInput.Event input) {
        return isTouchable()
                && HelperObjects.notifyInputEvent(input, mCollection.getInputObservers());
    }




    private void updateParametersCamera(Camera2D camera2D){
        float cameraW = camera2D.getWidth();
        float cameraH = camera2D.getHeight();
        float widthLayer = getMapObjects().getWidth();
        float heightLayer = getMapObjects().getHeight();
        float worldWidth = getWorld().getMapWorld().getWidth();
        float worldHeight = getWorld().getMapWorld().getHeight();

        float deltaLayerW = worldWidth - widthLayer;
        float deltaCameraW = worldWidth - cameraW;
        float slopeW = deltaCameraW>0 ?deltaLayerW / deltaCameraW:0;

        float deltaLayerH = worldHeight - heightLayer;
        float deltaCameraH = worldHeight - cameraH;
        float slopeH = (deltaCameraH>0)?deltaLayerH/deltaCameraH : 0;
        //mSlopeH = (mSlopeH> 1.0) ? 1: mSlopeH;

        float kW = (widthLayer - slopeW*cameraW)/2f;
        float kH = (heightLayer - slopeH*cameraH)/2f;

        float x = slopeW *camera2D.getX() + kW + getMapObjects().getOffsetX();
        float y = slopeH * camera2D.getY() + kH + getMapObjects().getTilesY();

        setPositionTiles(x,y);
    }



    private void setPositionTiles(float x, float y) {
        float width = getMapObjects().getWidth();
        float height =getMapObjects().getHeight();
        float tileWidth = getMapObjects().getTileWidth();
        float tileHeight = getMapObjects().getTileHeight();

        float offsetTileW = (x-width/2) + tileWidth/2;
        float offsetTileH = (y-height/2) + tileHeight/2;
        FixList<Tile> listTiles = mCollection.getTileList();
        for (int i = 0; i < listTiles.size(); i++){
            int ci = listTiles.get(i).getI();
            int cj = listTiles.get(i).getJ();
            listTiles.get(i).setPosition(offsetTileW+ ci*tileWidth,
                    offsetTileH+cj*tileHeight);
            listTiles.get(i).setSize(tileWidth,tileHeight);
        }
    }

    private void notifyAttachLayer() {
        int size = mCollection.getLayerObjectList().size();
        for (int i = 0; i < size; i++) {
            mCollection.getLayerObjectList().get(i).onAttachLayer(this);
        }
    }


    private void onAttachFocusWindows() {
        mTileWindow = new TileWindow();
        MapWorld mapWorld = getWorld().getMapWorld();

        if( mapWorld!=null && mapWorld.getFocusedWindowSize()!=0) {
            mTileWindow.setWindowSize( mapWorld.getFocusedWindowSize()
                    ,mapWorld.isForegroundFixedHeight()
                    ,getCamera2D().getAspectRatio());
            mTileWindow.setWorldSize(mapWorld.getTilesX(),
                    mapWorld.getTilesY());
            mTileWindow.setTileSize(mapWorld.getTileWidth()
                    ,mapWorld.getTileHeight());

        }else {
            int tilesX = getMapLayer().getTilesX();
            int tilesY = getMapLayer().getTilesY();
            tilesX = tilesX%2==0? tilesX-1: tilesX;
            tilesY = tilesY%2==0? tilesY-1: tilesY;
            mTileWindow.setWindowSize(tilesX,tilesY);
            mTileWindow.setWorldSize(getMapLayer().getTilesX()
                    ,getMapLayer().getTilesY());
            mTileWindow.setTileSize(getMapLayer().getTileWidth()
                    ,getMapLayer().getTileHeight());

        }
    }


    public LayerObject findById(int id){
        return mCollection.findById(id);
    }

    @Override
    public MapLayer getMapObjects() {
        return (MapLayer) super.getMapObjects();
    }

    public World2D getWorld(){
        return (World2D) getScene();
    }


    public MapLayer getMapLayer() {
        return (MapLayer) super.getMapObjects();
    }

    protected WorldObjectCollection getCollection() {
        return mCollection;
    }

    protected TileWindow getTileWindow() {
        return mTileWindow;
    }

    @Override
    protected ParamsTLayer getParameters() {
        return (ParamsTLayer) super.getParameters();
    }

    public int getTileWidth() {
        return getMapLayer().getTileWidth();
    }

    public int getTileHeight(){
        return  getMapLayer().getTileHeight();
    }

    protected FlexibleList<? extends Tile> getTileList() {
        return mTileWindow.getTileList();
    }

    protected FlexibleList<? extends Actor> getObjectList() {
        return mTileWindow.getActorList();
    }

    public static class ParamsTLayer extends Layer.ParamsLayer {
        public ParamsTLayer(MapLayer maplayer) {
           super(maplayer);
        }
        @Override
        public MapLayer getMapObjects(){
            return (MapLayer) super.getMapObjects();
        }
    }

}
