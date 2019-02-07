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

package com.titicolab.nanux.objects.base;

import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.graphics.draw.Drawer;
import com.titicolab.nanux.graphics.draw.Image;
import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.nanux.list.GameObjectCollection;
import com.titicolab.nanux.list.GameObjectList;
import com.titicolab.nanux.touch.ObservableInput;


/**
 * Created by campino on 12/12/2017.
 *
 */

public class HelperObjects {

    public static void drawGameObjects(Drawer<Image> drawer, FlexibleList<? extends Animated> gameObjectCollection) {
        int sizeObject = gameObjectCollection.size();
        for (int item = 0; item < sizeObject; item++) {
            if(gameObjectCollection.get(item).isDrawable())
                drawer.add(gameObjectCollection.get(item).getImage());
        }
    }


    public static void updateLogicObjects(FlexibleList<? extends GameObject> objectCollection) {
        int sizeObject =objectCollection.size();
        for (int item = 0; item < sizeObject; item++) {
            if(objectCollection.get(item).isUpdatable())
                objectCollection.get(item).updateLogic();
        }
    }

    public static void updateRenderObjects(FlexibleList<? extends GameObject> objectCollection){
        int sizeObject = objectCollection.size();
        for (int item = 0; item < sizeObject; item++) {
            if(objectCollection.get(item).isUpdatable())
                objectCollection.get(item).updateRender();
        }
    }

    public static boolean notifyInputEvent(ObservableInput.Event input,
                                    FlexibleList<? extends GameObject> list) {
        boolean isProcessed = false;
        int sizeObject = list.size();
        for (int item = 0; item < sizeObject; item++) {
            if(list.get(item).isTouchable()) {
                isProcessed =  notifyToGuiObject(list.get(item), input);
                if(isProcessed)break;
            }
        }
        return isProcessed;
    }

    private static boolean notifyToGuiObject(GameObject listener,
                                             ObservableInput.Event input) {
        return listener.onTouch(input);
    }

    static void updateLogicLayers(GameObjectList list){
        int layers = list.size();
        for (int i = 0; i < layers; i++) {
            list.get(i).updateLogic();
        }
    }

    static void updateRenderLayers(GameObjectList list){
        int layers = list.size();
        for (int i = 0; i < layers; i++) {
            list.get(i).updateRender();
        }
    }


    static void onDrawLayers(GameObjectList list, DrawTools drawer){
        int layers = list.size();
        for (int i = 0; i < layers; i++) {
            ((BaseLayer) list.get(i)).onDraw(drawer);
        }
    }

    static boolean notifyInputEvent(ObservableInput.Event input,
                                    GameObjectList list) {
        boolean r = false;
        int sizeObject = list.size();
        for (int item = 0; item < sizeObject; item++) {
            if(list.get(item).isTouchable()) {
                r=notifyToGuiObject(list.get(item), input);
            }
        }
        return r;
    }



    /**************** onCreate, Start and Stop objects *******************************************/

    public static void notifyOnCreated(GameObjectCollection collection){
        int sizeTypes = collection.size();
        for (int i = 0; i < sizeTypes; i++) {
            int sizeObject =collection.get(i).size();
            for (int item = 0; item < sizeObject; item++) {
                ((Animated)collection.get(i).get(item)).onCreated();
            }
        }
    }

    static void notifyOnStart(GameObjectCollection mObjectCollection){
        int sizeTypes = mObjectCollection.size();
        for (int i = 0; i < sizeTypes; i++) {
            int sizeObject =mObjectCollection.get(i).size();
            for (int item = 0; item < sizeObject; item++) {
                mObjectCollection.get(i).get(item).onStart();
            }
        }
    }

    static void notifyOnStop(GameObjectCollection mObjectCollection){
        int sizeTypes = mObjectCollection.size();
        for (int i = 0; i < sizeTypes; i++) {
            int sizeObject =mObjectCollection.get(i).size();
            for (int item = 0; item < sizeObject; item++) {
                mObjectCollection.get(i).get(item).onStop();
            }
        }
    }


    /*
    public static void drawCollisions(FlexibleList list,
                                      Drawer.Brush<Geometry> geometry) {
        int size = list.size();
        for (int i = 0; i < size; i++){
            CollisionObject collisionObject = (CollisionObject) list.get(i);
            if(collisionObject.isCollisionEnable()){
                CollisionArea[] arrayArea = collisionObject.getCollisionAreas();
                for (int item=0; item<arrayArea.length; item++) {
                    if(arrayArea[item]!=null) {
                        arrayArea[item].updateRender();
                        geometry.add(arrayArea[item].getDrawable());
                    }
                }
            }
        }
    }*/

}
