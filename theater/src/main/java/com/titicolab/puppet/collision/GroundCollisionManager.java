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

package com.titicolab.puppet.collision;


import com.titicolab.puppet.objects.Actor;
import com.titicolab.puppet.objects.Tile;

/**
 * Created by campino on 29/01/2017.
 *
 */

public class GroundCollisionManager extends CollisionManager {

    private static final int AREA_LEFT = 0;
    private static final int AREA_TOP =  1;
    private static final int AREA_RIGHT = 2;
    private static final int AREA_BOTTOM = 3;


    public GroundCollisionManager(Tile tile) {
        super(tile);
        mCollisionAreas = new CollisionArea[4];
    }

    @Override
    public boolean hasCollisionArea() {
        return mCollisionAreas!=null &&
                (mCollisionAreas[AREA_LEFT] !=null  ||
                mCollisionAreas[AREA_TOP] !=null  ||
                mCollisionAreas[AREA_RIGHT] !=null  ||
                mCollisionAreas[AREA_BOTTOM] !=null);
    }


    synchronized public void setUpCollisionAreas(boolean hasLeft,
                                                 boolean hasTop,
                                                 boolean hasRight,
                                                 boolean hasBottom){
         clearCollisionAreas();

        if(!hasTop){
            CollisionArea collisionArea = new CollisionArea(getParent());
            collisionArea.setRelativeMargin(0.0f,0.1f,0.0f,0.7f);
            collisionArea.setId(AREA_TOP);
            mCollisionAreas[AREA_TOP] = collisionArea;
        }

        if(!hasLeft){
            CollisionArea collisionArea = new CollisionArea(getParent());
            collisionArea.setId(AREA_LEFT);

            if(!hasTop && hasBottom)
                collisionArea.setRelativeMargin(0.1f,0.3f,0.7f,0.0f);
            if( hasTop && hasBottom)
                collisionArea.setRelativeMargin(0.1f,0.0f,0.7f,0.0f);
            else if( hasTop && !hasBottom)
                collisionArea.setRelativeMargin(0.1f,0.0f,0.7f,0.2f);
            else if( !hasTop && !hasBottom)
                collisionArea.setRelativeMargin(0.1f,0.3f,0.7f,0.2f);

            mCollisionAreas[AREA_LEFT] = collisionArea;
        }

        if(!hasRight){
            CollisionArea collisionArea = new CollisionArea(getParent());
            collisionArea.setId(AREA_RIGHT);
            if(!hasTop && hasBottom)
                collisionArea.setRelativeMargin(0.7f,0.3f,0.1f,0.0f);
            else if(hasTop && hasBottom)
                collisionArea.setRelativeMargin(0.7f,0.0f,0.1f,0.0f);
            else if(hasTop)
                collisionArea.setRelativeMargin(0.7f,0.0f,0.1f,0.2f);
            else collisionArea.setRelativeMargin(0.7f,0.3f,0.1f,0.2f);

            mCollisionAreas[AREA_RIGHT] = collisionArea;
        }

        if(!hasBottom){
            CollisionArea collisionArea = new CollisionArea(getParent());
            collisionArea.setId(AREA_BOTTOM);
            collisionArea.setRelativeMargin(0.0f,0.8f,0.0f,0.0f);
            mCollisionAreas[AREA_BOTTOM] = collisionArea;
        }

    }

    private void clearCollisionAreas() {
        mCollisionAreas[AREA_LEFT] =null;
        mCollisionAreas[AREA_TOP]  =null;
        mCollisionAreas[AREA_RIGHT] =null;
        mCollisionAreas[AREA_BOTTOM] =null;
    }


    public void solveCollision(Actor actor) {
        int collisionMask = 0;
        boolean topCollision = checkCollisionTop(actor);
        boolean bottomCollision = checkCollisionBottom(actor);
        boolean leftCollision = checkCollisionLeft(actor);
        boolean rightCollision = checkCollisionRight(actor);

        // There are any collision
        if( !leftCollision && !topCollision && !rightCollision && !bottomCollision) {
            return;
        }

        // There are collision with the bottom area
        if(bottomCollision){ //!leftCollision && !topCollision && !rightCollision  &&  bottomCollision){
            setBottomArea(actor);
            collisionMask |= CollisionEngine.COLLISION_AREA_BOTTOM;
        }

        //There are collision with top area
        if(topCollision) {// !leftCollision && topCollision && !rightCollision  && !bottomCollision){
            setTopArea(actor);
            collisionMask |= CollisionEngine.COLLISION_AREA_TOP;
        }

        if( leftCollision){ // leftCollision && !topCollision && !rightCollision  && !bottomCollision){
            setLeftArea(actor);
            collisionMask |= CollisionEngine.COLLISION_AREA_LEFT;
        }
        if(rightCollision){ // leftCollision && !topCollision && !rightCollision  && !bottomCollision){
            setRightArea(actor);
            collisionMask |= CollisionEngine.COLLISION_AREA_RIGHT;
        }

        if(collisionMask!=0)
            actor.onTileCollision(collisionMask);
    }



    private boolean checkCollisionLeft(Actor sprite) {
        CollisionArea areaLeft = mCollisionAreas[AREA_LEFT];
        return areaLeft != null && areaLeft.checkCollisionArea(sprite.getCollisionArea());
    }

    private boolean checkCollisionTop(Actor sprite) {
        CollisionArea areaTop = mCollisionAreas[AREA_TOP];
        return areaTop != null && areaTop.checkCollisionArea(sprite.getCollisionArea());
    }

    private boolean checkCollisionRight(Actor sprite) {
        CollisionArea areaRight = mCollisionAreas[AREA_RIGHT];
        return areaRight != null && areaRight.checkCollisionArea(sprite.getCollisionArea());
    }

    private boolean checkCollisionBottom(Actor sprite) {
        CollisionArea areaBottom = mCollisionAreas[AREA_BOTTOM];
        return areaBottom != null && areaBottom.checkCollisionArea(sprite.getCollisionArea());
    }

    private void setBottomArea(Actor sprite){
        mCollisionAreas[AREA_BOTTOM].setBottomCorrection(sprite);
    }

    private void setTopArea(Actor sprite){
        mCollisionAreas[AREA_TOP].setTopCorrection(sprite);
    }

    private void setLeftArea(Actor sprite){
        mCollisionAreas[AREA_LEFT].setLeftCorrection(sprite);
    }


    private void setRightArea(Actor sprite) {
        mCollisionAreas[AREA_RIGHT].setRightCorrection(sprite);
    }

    public boolean hasAreaLeft() {
        return mCollisionAreas[AREA_LEFT]!=null;
    }
    public boolean hasAreaRight() {
        return mCollisionAreas[AREA_RIGHT]!=null;
    }
    public boolean hasAreaTop() {
        return mCollisionAreas[AREA_TOP]!=null;
    }
    public boolean hasAreaBottom() {
        return mCollisionAreas[AREA_BOTTOM]!=null;
    }

}
