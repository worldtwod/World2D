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

import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.graphics.draw.Drawer;
import com.titicolab.nanux.graphics.draw.Geometry;
import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.nanux.objects.base.HelperObjects;
import com.titicolab.puppet.collision.CollisionArea;
import com.titicolab.puppet.collision.CollisionEngine;
import com.titicolab.puppet.collision.CollisionObject;

/**
 * Created by campino on 25/12/2017.
 *
 */

public class WorldLayer extends TiledLayer {


    private boolean mFlatDrawTileCollision;
    private boolean mFlatDrawObjectCollision;

    @Override
    protected void updateLogic() {
        if(isUpdatable()) {
            getTileWindow().setPosition(getCamera2D().getX(),getCamera2D().getY());
            getTileWindow().updateFocusTiles(getCollection().getTileList());
            getTileWindow().updateFocusObjects(getCollection().getActorList());

            CollisionEngine.checkCollisionActorTile(getTileWindow().getTileList()
                    ,getCollection().getActorList());
            CollisionEngine.checkCollisionActorActor(getTileWindow().getActorList());

            HelperObjects.updateLogicObjects( getTileWindow().getTileList());
            HelperObjects.updateLogicObjects( getTileWindow().getActorList());
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    protected void onDraw(DrawTools drawer) {
        super.onDraw(drawer);

        drawer.geometry.setBrushSize(1.5f);

        if (isFlatDrawTileCollision()) {
            drawer.geometry.setColor(0,1,0,1);
            drawer.geometry.begin(getCamera2D().getMatrix());
            drawCollisions(getTileList(), drawer.geometry);
            drawer.geometry.end();

        }
        if (isFlatDrawObjectCollision()) {
            drawer.geometry.setColor(1,1,0,1);
            drawer.geometry.begin(getCamera2D().getMatrix());
            drawCollisions(getObjectList(), drawer.geometry);
            drawer.geometry.end();
        }

    }


    protected boolean isFlatDrawTileCollision() {
        return mFlatDrawTileCollision;
    }
    protected boolean isFlatDrawObjectCollision() {
        return mFlatDrawObjectCollision;
    }
    protected void setFlatDrawTileCollision(boolean flatDrawTileCollision) {
        this.mFlatDrawTileCollision = flatDrawTileCollision;
    }
    protected void setFlatDrawObjectCollision(boolean flatDrawObjectCollision) {
        this.mFlatDrawObjectCollision = flatDrawObjectCollision;
    }


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
    }


}
