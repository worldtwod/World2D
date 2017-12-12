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

package com.titicolab.puppet.objects.base;

import com.titicolab.nanux.graphics.drawer.Drawer;
import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.puppet.animation.AnimationSheet;
import com.titicolab.puppet.draw.DrawTools;
import com.titicolab.puppet.draw.Image;
import com.titicolab.puppet.list.GameObjectCollection;
import com.titicolab.puppet.objects.Camera2D;
import com.titicolab.puppet.objects.CameraUi;
import com.titicolab.puppet.objects.factory.Parameters;
import com.titicolab.puppet.objects.factory.RequestCollection;
import com.titicolab.puppet.objects.factory.RequestObject;
import com.titicolab.puppet.objects.factory.RequestObjectBuilder;
import com.titicolab.puppet.objects.map.MapObjects;

/**
 * Created by campino on 05/01/2017.
 *
 */

public  class Layer extends BaseLayer<Layer.ParamsLayer> implements
                                        ObjectFactory.GameObjectFactory,
                                                GameObject.OnTouch{


    private MapObjects mMapObjects;
    private GameObjectCollection mObjectCollection;

    private FlexibleList<UiObject> uiRenderList;
    private FlexibleList<Animated> worldRenderList;


    public Layer(){
        setUpdatable(true);
        setDrawable(true);
        setTouchable(true);
    }


    @Override
    public void onAttachParameters(RequestObject request) {
        super.onAttachParameters(request);
        mMapObjects = onDefineMapObjects();
    }




    protected MapObjects onDefineMapObjects(){
        return getParameters()!=null? getParameters().mapObjects: new MapObjects.Builder().build();
    }


    @Override
    void onAttachScene(Scene scene) {
        super.onAttachScene(scene);
    }

    @Override
    void onAttachLayerFactory(ObjectFactory.LayerFactory group) {
        super.onAttachLayerFactory(group);
    }

    @Override
    public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
        return getScene().onDefineAnimations(builder);
    }

    @Override
    public RequestCollection onRequestObjects(RequestObjectBuilder builder) {
        return builder.object(mMapObjects.getList()).build();
    }

    @Override
    public void onAttachObjects(GameObjectCollection collection) {
            mObjectCollection = collection;
            loadListObjects(collection);

    }

    @Override
    public void onGroupObjectsCreated() {

    }


    @Override
    public void updateLogic() {
        if(isUpdatable()) {
            updateLogicObjects(uiRenderList);
            updateLogicObjects(worldRenderList);
        }
    }

    @Override
    public void updateRender() {
        if(isDrawable()) {
            updateRenderObjects(uiRenderList);
            updateRenderObjects(worldRenderList);
        }
    }

    @Override
    public void onDraw(DrawTools drawer) {
        if (isDrawable()) {
            onDrawGameObjects(drawer.images);
            onDrawUiObjects(drawer.ui);
            onDrawText(drawer.text);
        }
    }

    protected void onDrawGameObjects(Drawer<Image> spriteDrawer) {
        if(worldRenderList.size()>0) {
            spriteDrawer.begin(getCamera2D().getProjection().getMatrix());
            drawGameObjects(spriteDrawer,worldRenderList);
            spriteDrawer.end();
        }
    }

    protected void onDrawUiObjects(Drawer<Image> uiDrawer){
        if(uiRenderList.size()>0){
            uiDrawer.begin(getCameraUi().getProjection().getMatrix());
            drawGameObjects(uiDrawer,uiRenderList);
            uiDrawer.end();
        }
    }


    protected void onDrawText(Drawer drawerText) {

       //TODO
    }

    @Override
    public boolean onTouchEvent(ObservableInput.Event input) {
        return isTouchable()
                && notifyInputEvent(input, uiRenderList)
                | notifyInputEvent(input, worldRenderList);
    }


    private  void loadListObjects(GameObjectCollection objectCollection) {
        uiRenderList = new FlexibleList<>(100);
        worldRenderList = new FlexibleList<>(100);
        if( objectCollection==null) return;

        int sizeTypes = objectCollection.size();
        for (int i = 0; i < sizeTypes; i++) {
            int sizeObject =objectCollection.get(i).size();
            for (int item = 0; item < sizeObject; item++) {
                //objectCollection.get(i).get(item).setDrawable(true);
                //objectCollection.get(i).get(item).setUpdatable(true);

                if(UiObject.class.isAssignableFrom(objectCollection.get(i).get(item).getClass()))
                    uiRenderList.add((UiObject) objectCollection.get(i).get(item));
                else {
                    worldRenderList.add((Animated) objectCollection.get(i).get(item));
                }
            }
        }
    }


    static void drawGameObjects(Drawer<Image> drawer,FlexibleList<? extends Animated> gameObjectCollection) {
            int sizeObject = gameObjectCollection.size();
            for (int item = 0; item < sizeObject; item++) {
                if(gameObjectCollection.get(item).isDrawable())
                    drawer.add(gameObjectCollection.get(item).getImage());
            }
    }


    private static void updateLogicObjects(FlexibleList<? extends BaseObject> objectCollection) {
            int sizeObject =objectCollection.size();
            for (int item = 0; item < sizeObject; item++) {
                if(objectCollection.get(item).isUpdatable())
                    objectCollection.get(item).updateLogic();
            }
    }

    private static void updateRenderObjects(FlexibleList<? extends BaseObject> objectCollection){
            int sizeObject = objectCollection.size();
            for (int item = 0; item < sizeObject; item++) {
                if(objectCollection.get(item).isUpdatable())
                    objectCollection.get(item).updateRender();
            }
    }


    static boolean notifyInputEvent(ObservableInput.Event input,
                                    FlexibleList<? extends BaseObject> list) {
        boolean r = false;

            int sizeObject = list.size();
            for (int item = 0; item < sizeObject; item++) {
                if(list.get(item).isTouchable()) {
                    if(GameObject.OnTouch.class
                            .isAssignableFrom(list.get(item).getClass()))
                        r=notifyToGuiObject((GameObject.OnTouch)
                                list.get(item), input);
                }

        }
        return r;
    }

    private static boolean notifyToGuiObject(GameObject.OnTouch listener,
                                             ObservableInput.Event input) {
        return listener.onTouchEvent(input);
    }



    public GameObjectCollection getObjectCollection() {
        return mObjectCollection;
    }

    public <T>   T findById(Class<T> clazz, int id){
        return mObjectCollection.findById(clazz,id);
    }

    protected Camera2D getCamera2D(){
        return getScene().getCamera2D();
    }
    protected CameraUi getCameraUi(){
        return getScene().getCameraUi();
    }


    public static class ParamsLayer extends Parameters {
        final  MapObjects mapObjects;
        public ParamsLayer(MapObjects mapObjects) {
            this.mapObjects = mapObjects;
        }
    }


}
