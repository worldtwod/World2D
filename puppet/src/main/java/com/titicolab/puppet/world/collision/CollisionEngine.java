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

package com.titicolab.puppet.world.collision;

import com.titicolab.puppet.world.objects.WorldLayer;

/**
 * Created by campino on 17/01/2017.
 *
 */

public class CollisionEngine {

    private void checkCollisionWithSprites(WorldLayer layer){

    }

    public static boolean checkBoxCollision(CollisionArea boxA, CollisionArea boxB){
       boolean r = false;
         /*float xMin = boxA.getX()-boxA.width/2 - boxB.width/2;
        float xMax = boxA.getX()+boxA.width/2 + boxB.width/2;
        float yMin = boxA.getY()-boxA.height/2 - boxB.height/2;
        float yMax = boxA.getY()+ boxA.height/2 + boxB.height/2;
        float x = boxB.getX();
        float y = boxB.getY();

        if((xMin < x && x < xMax) &&
                (yMin < x && y < yMax)){
            r=true;
        }*/
        return r;

    }


}
