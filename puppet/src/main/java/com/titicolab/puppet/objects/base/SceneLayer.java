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
import com.titicolab.puppet.draw.DrawTools;
import com.titicolab.puppet.draw.Image;
import com.titicolab.puppet.list.GameObjectCollection;
import com.titicolab.puppet.objects.map.MapObjects;

/**
 * Created by campino on 05/01/2017.
 *
 */

public  class SceneLayer extends Layer{


    private MapObjects mMapObjects;
    private GameObjectCollection mObjectCollection;

    private FlexibleList<UiObject> uiRenderList;
    private FlexibleList<Animated> worldRenderList;


    public SceneLayer(){
        setUpdatable(true);
        setDrawable(true);
        setTouchable(true);
    }

    @Override
    protected void onAttachObjects(GameObjectCollection collection) {
            mObjectCollection = collection;
            loadListObjects(collection);
    }

    @Override
    protected void onGroupObjectsCreated() {

    }

    @Override
    protected void updateLogic() {
        if(isUpdatable()) {
            HelperObjects.updateLogicObjects(uiRenderList);
            HelperObjects.updateLogicObjects(worldRenderList);
        }
    }

    @Override
    protected void updateRender() {
        if(isDrawable()) {
            HelperObjects.updateRenderObjects(uiRenderList);
            HelperObjects.updateRenderObjects(worldRenderList);
        }
    }

    @Override
    protected void onDraw(DrawTools drawer) {
        if (isDrawable()) {
            onDrawGameObjects(drawer.images);
            onDrawUiObjects(drawer.ui);
            onDrawText(drawer.text);
        }
    }

    private void onDrawGameObjects(Drawer<Image> spriteDrawer) {
        if(worldRenderList.size()>0) {
            spriteDrawer.begin(getCamera2D().getProjection().getMatrix());
            HelperObjects.drawGameObjects(spriteDrawer,worldRenderList);
            spriteDrawer.end();
        }
    }

    private void onDrawUiObjects(Drawer<Image> uiDrawer){
        if(uiRenderList.size()>0){
            uiDrawer.begin(getCameraUi().getProjection().getMatrix());
            HelperObjects.drawGameObjects(uiDrawer,uiRenderList);
            uiDrawer.end();
        }
    }


    private void onDrawText(Drawer drawerText) {

       //TODO
    }

    @Override
    protected boolean onTouch(ObservableInput.Event input) {
        return isTouchable()
                && HelperObjects.notifyInputEvent(input, uiRenderList)
                | HelperObjects.notifyInputEvent(input, worldRenderList);
    }


    private  void loadListObjects(GameObjectCollection objectCollection) {
        uiRenderList = new FlexibleList<>(100);
        worldRenderList = new FlexibleList<>(100);
        if( objectCollection==null) return;

        int sizeTypes = objectCollection.size();
        for (int i = 0; i < sizeTypes; i++) {
            int sizeObject =objectCollection.get(i).size();
            for (int item = 0; item < sizeObject; item++) {
                if(UiObject.class.isAssignableFrom(objectCollection.get(i).get(item).getClass()))
                    uiRenderList.add((UiObject) objectCollection.get(i).get(item));
                else {
                    worldRenderList.add((Animated) objectCollection.get(i).get(item));
                }
            }
        }
    }


    public GameObjectCollection getObjectCollection() {
        return mObjectCollection;
    }

    public <T>   T findById(Class<T> clazz, int id){
        return mObjectCollection.findById(clazz,id);
    }


}
