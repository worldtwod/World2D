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


import com.titicolab.nanux.graphics.drawer.Drawer;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.FlagSync;
import com.titicolab.puppet.draw.DrawTools;
import com.titicolab.puppet.draw.Image;
import com.titicolab.puppet.list.GameObjectCollection;
import com.titicolab.puppet.objects.base.HelperObjects;
import com.titicolab.puppet.objects.base.Layer;
import com.titicolab.puppet.world.map.MapLayer;

public  class WorldLayer extends Layer {

    private WorldObjectCollection mCollection;

    private final FlagSync        mFlagOnCreatedObjects;


    public WorldLayer() {
        mFlagOnCreatedObjects = new FlagSync();
    }


    @Override
    protected MapLayer onDefineMapObjects() {
        return (MapLayer) super.onDefineMapObjects();
    }

    @Override
    protected void onAttachObjects(GameObjectCollection collection) {
        mCollection = new WorldObjectCollection(collection);
        mCollection.sortLeftBottomToRightTop(getMapObjects().getTilesX());
        mFlagOnCreatedObjects.assertFlag();
    }

    @Override
    protected void onGroupObjectsCreated() {


    }

    @Override
    protected void updateLogic() {
        if(isUpdatable()) {
            HelperObjects.updateLogicObjects(mCollection.getTileList());
            HelperObjects.updateLogicObjects(mCollection.getSpriteList());
        }
    }

    @Override
    protected void updateRender() {
        if(isDrawable()) {
            HelperObjects.updateRenderObjects(mCollection.getTileList());
            HelperObjects.updateRenderObjects(mCollection.getSpriteList());
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
                HelperObjects.drawGameObjects(spriteDrawer,mCollection.getTileList());
                HelperObjects.drawGameObjects(spriteDrawer,mCollection.getSpriteList());
            spriteDrawer.end();
    }

    @Override
    protected boolean onTouch(ObservableInput.Event input) {
        return isTouchable()
                && HelperObjects.notifyInputEvent(input, mCollection.getInputObservers());
    }

    @Override
    public MapLayer getMapObjects() {
        return (MapLayer) super.getMapObjects();
    }
}
