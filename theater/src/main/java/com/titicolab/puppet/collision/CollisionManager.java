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
import com.titicolab.puppet.objects.LayerObject;

/**
 * Created by campino on 29/01/2017.
 *
 */

public class CollisionManager {

    private final LayerObject mParent;
    CollisionArea[] mCollisionAreas;



    public CollisionManager(LayerObject layerObject) {
       checkSize(layerObject);
        mParent = layerObject;
        mCollisionAreas = new CollisionArea[1];
        mCollisionAreas[0] = new CollisionArea(layerObject);
    }



    public boolean hasCollisionArea() {
      return false;
    }

    public CollisionArea[] getCollisionAreas() {
        return mCollisionAreas;
    }

    public CollisionArea getCollisionArea() {
        return mCollisionAreas[0];
    }

    public LayerObject getParent() {
        return mParent;
    }




    public void solveCollision(Actor actor) {
         if(getCollisionArea().checkCollisionArea(actor.getCollisionArea())){
             boolean isSolved;
             if(Actor.class.isAssignableFrom(mParent.getClass())){
                 isSolved = ((Actor)mParent).onActorCollision(actor);
                 if(!isSolved)
                    actor.onActorCollision((Actor) mParent);
             }
         }
    }

    private void checkSize(LayerObject layerObject) {
        if(layerObject.getWidth()==0 || layerObject.getHeight()==0){
                throw  new RuntimeException("The size of layer object must be different of zero");
        }
    }

}
