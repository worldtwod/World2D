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


import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.puppet.objects.Actor;
import com.titicolab.puppet.objects.Tile;

/**
 * Created by campino on 17/01/2017.
 */

public class CollisionEngine {

    public static final int COLLISION_AREA_LEFT = 0x01;
    public static final int COLLISION_AREA_TOP =  0x02;
    public static final int COLLISION_AREA_RIGHT = 0x04;
    public static final int COLLISION_AREA_BOTTOM = 0x08;



    public static void checkCollisionActorTile(FlexibleList<Tile> listTiles,
                                                FlexibleList<Actor> listActor) {
        int sizeTiles = listTiles.size();
        int sizeSprites = listActor.size();
        for (int j = 0; j < sizeSprites; j++) {
            Actor actor = listActor.get(j);
            if(actor.isCollisionEnable() && actor.isUpdatable()) {
                for (int i = 0; i < sizeTiles; i++) {
                    if(listTiles.get(i).isCollisionEnable())
                        listTiles.get(i).getCollisionManager().solveCollision(actor);
                }
            }
        }
    }


    public static void checkCollisionActorActor(FlexibleList<Actor> listActor) {

        int size = listActor.size();
        for (int j = 0; j < size; j++) {
            Actor actor = listActor.get(j);
            if(actor.isCollisionEnable() && actor.isUpdatable()) {
                for (int i = j+1; i < size; i++) {
                    Actor item = listActor.get(i);

                    if(item.isCollisionEnable() && item.isUpdatable())
                        item.getCollisionManager().solveCollision(actor);
                }
            }
        }
    }

}
