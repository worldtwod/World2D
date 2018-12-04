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
import com.titicolab.nanux.util.ObjectSync;
import com.titicolab.puppet.collision.CollisionEngine;
import com.titicolab.puppet.map.MapLayer;
import com.titicolab.puppet.map.MapWorld;
import com.titicolab.puppet.objects.World2D;
import com.titicolab.puppet.objects.WorldLayer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by campino on 24/01/2017.
 *
 */


@RunWith(AndroidJUnit4.class)
public class SolveCollisionFunctionalTest extends World2DTestCase {




    @Test
    public void collisionTop(){
        World world = new World();
        syncPlay(world);
        setWorldBoundary(true);
        // TOP
        final MockGround tile = world.layer.findTile(1000);
        final TestActor actor = (TestActor) world.layer.findById(2000);


        actor.setPosition(tile.getX(),
                        tile.getY()+tile.getHeight()/2
                                + actor.getHeight()/2
                                - tile.getHeight()*0.1f-5);

        actor.maskCollision.waitResults(3);

        Assert.assertNotNull(actor.maskCollision.getResults());
        Assert.assertEquals(CollisionEngine.COLLISION_AREA_TOP,
                actor.maskCollision.getResults().intValue());
    }


    @Test
    public void collisionLeft(){
        World world = new World();
        syncPlay(world);
        setWorldBoundary(true);
        // TOP
        final MockGround tile = world.layer.findTile(1001);
        final TestActor actor = (TestActor) world.layer.findById(2000);

        actor.setPosition(tile.getX()
                        - tile.getWidth()*0.3f
                        -actor.getCollisionManager().getCollisionArea().getWidth()/2,
                        tile.getY()-10);

        actor.maskCollision.waitResults(3);

        Assert.assertNotNull(actor.maskCollision.getResults());
        Assert.assertEquals(CollisionEngine.COLLISION_AREA_LEFT,
                actor.maskCollision.getResults().intValue());
    }


    @Test
    public void collisionRight(){
        World world = new World();
        syncPlay(world);
        setWorldBoundary(true);
        // TOP
        MockGround tile = world.layer.findTile(1002);
        TestActor actor = (TestActor) world.layer.findById(2000);

        actor.setPosition(tile.getX() + tile.getWidth()*0.7f -5
                        -actor.getCollisionManager().getCollisionArea().getWidth()/2,
                tile.getY()-10);

        actor.maskCollision.waitResults(3);
        Assert.assertNotNull(actor.maskCollision.getResults());
        Assert.assertEquals(CollisionEngine.COLLISION_AREA_RIGHT,
                actor.maskCollision.getResults().intValue());
    }



    public static class  World extends World2D{
        GroundTiledLayer layer;
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

        @Override
        protected void onGroupLayersCreated() {
           layer = (GroundTiledLayer) findLayer(1);
        }
    }

    public static class GroundTiledLayer extends WorldLayer{
        private MockActor actor;

        @Override
        protected MapLayer onDefineMapObjects() {
            MapLayer.Builder builder = new MapLayer.Builder()
                    .setName("MapLayer")
                    .setGridSize(16, 5)
                    .setTileSize(180, 180);


            builder.item(TestActor.class,2000,4,2,"Boxelbot", MockActor.ROBOT_STOP);
            builder.item(MockGround.class,1000,4,1,"tile",0);
            builder.item(MockGround.class,1001,2,1,"tile",1);
            builder.item(MockGround.class,1002,6,1,"tile",3);

            return builder.build();
        }

        MockGround findTile(int id){
            return (MockGround) findById(id);
        }

        @Override
        protected void onGroupObjectsCreated() {
            setFlatDrawObjectCollision(true);
            setFlatDrawTileCollision(true);
            actor = (MockActor) findById(2000);
        }
    }



    public static class TestActor extends MockActor{
        final ObjectSync<Integer> maskCollision= new ObjectSync<>();
        @Override
        public void onTileCollision(int collisionMask) {
                maskCollision.setResult(collisionMask);
        }
    }


}
