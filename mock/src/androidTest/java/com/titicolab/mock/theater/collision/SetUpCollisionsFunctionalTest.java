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

package com.titicolab.mock.theater.collision;

import androidx.test.runner.AndroidJUnit4;

import com.titicolab.mock.cases.world.World2DTestCase;
import com.titicolab.mock.tools.MockActor;
import com.titicolab.mock.tools.MockGround;
import com.titicolab.puppet.map.MapLayer;
import com.titicolab.puppet.map.MapWorld;
import com.titicolab.puppet.objects.World2D;
import com.titicolab.puppet.objects.WorldLayer;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by campino on 24/01/2017.
 *
 */


@RunWith(AndroidJUnit4.class)
public class SetUpCollisionsFunctionalTest extends World2DTestCase {


    @Test
    public void windowsFromWorld(){

        World world = new World();
        syncPlay(world);
        setWorldBoundary(true);
        waitTouchSeconds(60*60);
    }




    public static class  World extends World2D{

        @Override
        protected MapWorld onDefineMapWorld(MapWorld.Builder builder) {
            return   builder
                    .setName("World")
                    .setGridSize(16, 5)
                    .setTileSize(180, 180)
                    .setCameraSize(5,true)
                    .setFocusedWindowSize(7,true)
                    .layer(GroundTiledLayer.class,1,null)
                    .build();
        }
    }

    public static class GroundTiledLayer extends WorldLayer{

        @Override
        protected MapLayer onDefineMapObjects() {
            MapLayer.Builder builder = new MapLayer.Builder()
                    .setName("MapLayer")
                    .setGridSize(16, 5)
                    .setTileSize(180, 180);

            builder.item(MockGround.class,1000,0,3,"tile",0);
            builder.item(MockGround.class,1001,1,3,"tile",1);
            builder.item(MockGround.class,1002,2,3,"tile",2);
            builder.item(MockGround.class,1003,3,3,"tile",3);
            builder.item(MockActor.class,2000,5,2,"Boxelbot", MockActor.ROBOT_STOP);
            return builder.build();
        }

        @Override
        protected void onGroupObjectsCreated() {
            setFlatDrawObjectCollision(true);
            setFlatDrawTileCollision(true);
        }
    }


}
