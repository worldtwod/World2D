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
import com.titicolab.nanux.list.GameObjectCollection;

/**
 * Created by campino on 24/01/2017.
 *
 * Collection of LayerObjects, it is tiles and Actor
 *
 */

public class WorldObjectCollection {


    private FlexibleList<Tile>           mTileList;
    private FlexibleList<Actor>  mObjectList;
    private FlexibleList<LayerObject>    mInputObservers;
    private FlexibleList<LayerObject>    mAllList;


    public WorldObjectCollection(GameObjectCollection objectCollection) {
        loadListObjects(objectCollection);

    }


    private  void loadListObjects(GameObjectCollection objectCollection) {
        mInputObservers = new FlexibleList<>(100);
        mTileList = new FlexibleList<>
                (objectCollection.sizeAssignableFrom(Tile.class));
        mObjectList = new FlexibleList<>
                (objectCollection.sizeAssignableFrom(Actor.class));

        mAllList = new FlexibleList<>(mTileList.capacity()
                + mObjectList.capacity());


        int sizeTypes = objectCollection.size();
        for (int i = 0; i < sizeTypes; i++) {
            int sizeObject =objectCollection.get(i).size();
            for (int item = 0; item < sizeObject; item++) {

                LayerObject object = (LayerObject) objectCollection.get(i).get(item);
                mAllList.add(object);

                if(Tile.class.isAssignableFrom(object.getClass()))
                    mTileList.add((Tile)object);
                else {
                    mObjectList.add((Actor)object);
                }

                if(object.isTouchable()){
                    mInputObservers.add(object);
                }
            }
        }
    }



    public void sortLeftBottomToRightTop(int tilesX){
        sortList(mTileList,tilesX);
        sortList(mObjectList,tilesX);
    }


    @SuppressWarnings("unchecked")
    private static void sortList(FlexibleList list, int tilesX){
        int size =list.size();
        for (int minor = 0; minor < size-1; minor++) {
            for (int current = minor + 1; current < size; current++) {

                LayerObject minorObject = (LayerObject) list.get(minor);
                int minorCell = minorObject.getI()+minorObject.getJ()* tilesX;

                LayerObject currentObject = (LayerObject)list.get(current);
                int actualGrid = currentObject.getI()+ currentObject.getJ() * tilesX;

                if(minorObject.getI()>tilesX || currentObject.getI()>tilesX)
                    throw new RuntimeException("It found a object with i > tilesX," +
                            " the current argument tilesX=" + tilesX);

                if (minorCell > actualGrid) {
                    list.set(minor, currentObject);
                    list.set(current, minorObject);
                }
            }
        }
    }



    public LayerObject findById(int id){
        LayerObject object=null;
        int index = findIndexOf(mAllList,id);
        if(!(index<0))
            object = mAllList.get(index);
        return object;
    }





    private  static int findIndexOf(FlexibleList<?> list, int id) {
        int result = -1;
        int size =list.size();
        for (int k = 0; k < size; k++) {
            LayerObject object = (LayerObject) list.get(k);
            if(object.getId()== id){
                result = k; break;
            }
        }
        return result;
    }


    public FlexibleList<Tile> getTileList() {
        return mTileList;
    }

    public FlexibleList<Actor> getActorList() {
        return mObjectList;
    }

    public FlexibleList<LayerObject> getInputObservers() {
        return mInputObservers;
    }

    public FlexibleList<LayerObject> getLayerObjectList(){
        return mAllList;
    }


    /*
    @SuppressWarnings("unchecked")
    public void notifyCollectionAttachMapItem(MapObjects map) {
        mMapLayer = (MapLayer) map;
        int size = mMapLayer.size();
        int tileSize = mMapLayer.getNumberObjects(Tile.class);
        int spritesSize = mMapLayer.getNumberObjects(Actor.class);
        mTileList = new FlexibleList<>(tileSize);
        mObjectList = new FlexibleList<>(spritesSize);
        for (int i = 0; i < size; i++) {
            MapItem mapItem = mMapLayer.get(i);
            notifyAttachAttachMapItem(mapItem);
        }
    }


    private LayerObject notifyAttachAttachMapItem(MapItem mapItem) {
        LayerObject layerObject = (LayerObject) mObjectManager.getGameObjectCollection()
                .findByClassAndId(mapItem.clazz, mapItem.id);

        if(mapItem.isAssignableFrom(Tile.class)) {
            Tile tile = (Tile) layerObject;
            if (tile != null) {
                tile.onAttachMapItem(mapItem);
                mTileList.add(tile);
            }
        }else if(mapItem.isAssignableFrom(Actor.class)){
            Actor sprite = (Actor) layerObject;
            if(sprite!=null) {
                sprite.onAttachMapItem(mapItem);
                mObjectList.add(sprite);
            }
        }else{
            throw new RuntimeException("The the item in the map is not a GroupUiObject class");
        }

        return layerObject;
    }




    void notifyCollectionLayerCreated(Layer layer){
        sortTiles();
        sortSpritesPhysics();
        layerCreated(mTileList,layer);
        layerCreated(mObjectList,layer);
    }


    void notifyOnAttachCollision(TiledLayer tiledLayer) {
        attachCollision(mTileList);
        attachCollision(mObjectList);
    }


    private void attachCollision(FixList<? extends LayerObject> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            list.get(i).onAttachCollisionManager();
        }
    }


    private static void layerCreated(FixList<? extends Layer.ItemLayer> list, Layer layer){
        int size = list.size();
        for (int i = 0; i < size; i++) {
            list.get(i).onLayerCreated(layer);
        }
    }


    private void sortSpritesPhysics(){
        int size = mObjectList.size();
        for (int current = 0; current < size; current++)
            for (int item = current + 1; item < size; item++) {
                if(mObjectList.get(current).getCellId()>mObjectList.get(item).getCellId()){
                    Actor tem = mObjectList.get(current);
                    mObjectList.set(current,mObjectList.get(item));
                    mObjectList.set(item,tem);
                }
            }
    }

    private void sortTiles(){
        int size = mTileList.size();
        for (int current = 0; current < size; current++)
            for (int item = current + 1; item < size; item++) {
                if(mTileList.get(current).getCellId()>mTileList.get(item).getCellId()){
                    Tile tem =  mTileList.get(current);
                    mTileList.set(current,mTileList.get(item));
                    mTileList.set(item,tem);
                }
            }
    }



    public FixList<Tile> getListTiles() {
        return mTileList;
    }

    public FixList<Actor> getListSpritesPhysics() {
        return mObjectList;
    }



    Actor findSpriteById(Class<? extends Actor> spriteClass, int id) {
        int size = mObjectList.size();
        for (int k = 0; k < size; k++) {
            Actor sprite = mObjectList.get(k);
            if(sprite.getClass().equals(spriteClass)
                    && sprite.getId()== id){
                return sprite;
            }
        }
        return null;
    }


    Actor findSpriteByIj(Class<? extends Actor> spriteClass, int i, int j) {
        int size = mObjectList.size();
        for (int k = 0; k < size; k++) {
            Actor sprite = mObjectList.get(k);
            if(sprite.getClass().equals(spriteClass)
                    && sprite.getI()==i
                    && sprite.getJ()==j){
                return sprite;
            }
        }
        return null;
    }

    private LayerObject findLayerObjectByIJ(FixList<? extends LayerObject> list, int i, int j) {
        int size = list.size();
        for (int k = 0; k < size; k++) {
            LayerObject layerObject = list.get(k);
            if(layerObject.getI()==i
                    && layerObject.getJ()==j){
                return layerObject;
            }
        }
        return null;
    }


    private boolean hasSameSequence(Tile tileA, Tile tileB){
        return (tileA!=null && tileA.getSequenceId()== tileB.getSequenceId());
    }

    Tile findTileByIJ(int i, int j) {
        return (Tile) findLayerObjectByIJ(mTileList,i,j);
    }

    private Actor findSpritePhysicsByIJ(int i, int j) {
        return (Actor) findLayerObjectByIJ(mObjectList,i,j);
    }

    Tile findTileById(Class<? extends Tile> tileClass, int id) {
        int size =  mTileList.size();
        for (int k = 0; k < size; k++) {
            Tile tile =  mTileList.get(k);
            if(tile.getClass().equals(tileClass)
                    && tile.getId()== id){
                return tile;
            }
        }
        return null;
    }

    void compileAnimationClip(){
        sortTiles();
        int sizeTiles =getListTiles().size();
        for (int i = 0; i < sizeTiles; i++) {
            Tile tile = getListTiles().get(i);
            boolean left = hasNeighborSequence(getNeighborLeft(tile),tile);
            boolean top = hasNeighborSequence(getNeighborTop(tile),tile);
            boolean right = hasNeighborSequence(getNeighborRight(tile) ,tile);
            boolean bottom = hasNeighborSequence(getNeighborBottom(tile),tile);
            tile.setUpAnimationClip(left, top, right, bottom);
        }
    }






    void compileCollisions(){
        sortTiles();
        int sizeTiles =getListTiles().size();
        for (int i = 0; i < sizeTiles; i++) {
            Tile tile = getListTiles().get(i);
            boolean left = getNeighborLeft(tile) != null;
            boolean top = getNeighborTop(tile) != null;
            boolean right = getNeighborRight(tile) != null;
            boolean bottom = getNeighborBottom(tile) != null;
            tile.setUpCollisionAreas(left, top, right, bottom);
        }
    }

    private boolean hasNeighborSequence(Tile tileN, Tile tileB){
       return tileN!= null && hasSameSequence(tileN,tileB);
    }

    private Tile getNeighborLeft(Tile tile){
        if(tile.getI()<=0)
            return null;
        int i = tile.getI() - 1;
        int j = tile.getJ();
        return findTileByIJ(i,j); ///hasSameSequence(findTileByIJ(i,j),tile);
    }

    private Tile getNeighborTop(Tile tile){
        if(tile.getJ()>= mMapLayer.getTilesY()-1)
            return null;
        int i = tile.getI();
        int j = tile.getJ()+1;
        return findTileByIJ(i,j);
    }

    private Tile getNeighborRight(Tile tile){
        if(tile.getI()>= mMapLayer.getTilesX()-1)
            return null;
        int i = tile.getI() + 1;
        int j = tile.getJ();
        return findTileByIJ(i,j); //0findNeigborTileByIJ(tile,i,j);
    }

    private Tile getNeighborBottom(Tile tile){
        if(tile.getJ()<=0)
            return null;
        int i = tile.getI();
        int j = tile.getJ()-1;
        return findTileByIJ(i,j); //
    }

    public Tile getNeigbothLeftTop(Tile tile){
        if(tile.getI()<=0)
            return null;
        if(tile.getJ()>= mMapLayer.getTilesY()-1)
            return null;
        int i = tile.getI() - 1;
        int j = tile.getJ()+1;
        return findTileByIJ(i,j); //
    }


    public Tile getNeigbothToRight(Tile tile){
        if(tile.getJ()>= mMapLayer.getTilesY()-1)
            return null;
        if(tile.getI()>= mMapLayer.getTilesX()-1)
            return null;
        int i = tile.getI()+1;
        int j = tile.getJ()+1;
        return findTileByIJ(i,j); //
    }

    private Tile getNeigbothRightBottom(Tile tile){
        if(tile.getI()>= mMapLayer.getTilesX()-1)
            return null;
        if(tile.getJ()<=0)
            return null;

        int i = tile.getI() + 1;
        int j = tile.getJ()-1;
        return findTileByIJ(i,j); //
    }

    public Tile getNeigbothBottomTop(Tile tile){
        if(tile.getJ()<=0)
            return null;
        if(tile.getI()<=0)
            return null;
        int i = tile.getI()-1;
        int j = tile.getJ()-1;
        return findTileByIJ(i,j); //
    }


    public void addNewLayerObject(Layer layer, MapItem mapItem, LayerObject layerObject) {
        mObjectManager.addNew(layerObject);
        mMapLayer.add(mapItem);
        notifyAttachAttachMapItem(mapItem);
        if(layer!=null)
            layerObject.onLayerCreated(layer);
        layerObject.onAttachCollisionManager();
    }

    public boolean removeSpritePhysics(Class<? extends Actor> clazz, int id) {
        boolean removed = false;
        Actor layerObject;
        layerObject = findSpriteById(clazz,id);
        if(layerObject!=null) {
            mObjectList.remove(layerObject);
            mObjectManager.remove(layerObject);
            mMapLayer.remove(layerObject.getMapItem());
            removed = true;
        }
        return removed;
    }


    public boolean removeLayerObject(int i, int j) {
        boolean removed = false;
        LayerObject layerObject;
        layerObject = findTileByIJ(i,j);
        if(layerObject==null)
            layerObject = findSpritePhysicsByIJ(i,j);

        if(layerObject!=null) {
            if(layerObject.getClass().isAssignableFrom(Tile.class)) {
                mTileList.remove((Tile) layerObject);
            }else if(layerObject.getClass().isAssignableFrom(Actor.class)){
                mObjectList.remove((Actor) layerObject);
            }else{
                throw new RuntimeException("The layerObject is no available for remove");
            }
            mObjectManager.remove(layerObject);
            mMapLayer.remove(layerObject.getMapItem());
            removed=true;
        }

        return removed;
    }

  */



}
