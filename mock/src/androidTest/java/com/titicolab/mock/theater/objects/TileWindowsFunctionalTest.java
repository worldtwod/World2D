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

package com.titicolab.mock.theater.objects;

import com.titicolab.mock.rule.SceneTestRule;
import com.titicolab.mock.tools.TestMap;
import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.nanux.util.FlagSync;
import com.titicolab.puppet.map.MapLayer;
import com.titicolab.puppet.map.MapWorld;
import com.titicolab.puppet.objects.TiledLayer;
import com.titicolab.puppet.objects.World2D;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by campino on 24/01/2017.
 *
 */
@Deprecated
public class TileWindowsFunctionalTest{

    @Rule
    public SceneTestRule sceneTestRule = new SceneTestRule();
    static MapWorld.Builder builderWorld;
    private World2D world;

    @Before
    public void before(){

    }

    @Test
    public void windowsFromWorld(){
        builderWorld = new MapWorld.Builder()
                .setName("World")
                .setGridSize(40, 5)
                .setTileSize(180, 180)
                .setCameraSize(5,true)
                .setFocusedWindowSize(7,true)
                .layer(GroundTiledLayer.class,1,null)
                .layer(WorldTiledLayer.class,2,null);

        world = new World();
        sceneTestRule.syncPlay(world);
        world.setDrawBoundary(true);

        for (int i =0; i <40; i++) {
            moveCamera(i,3);
            waitTouchSeconds(1);
        }

        waitTouchSeconds(60);
    }

    private void waitTouchSeconds(int i) {
        new FlagSync().waitSyncSeconds(i);
    }


    private void moveCamera(int i, int j){
        world.getCamera2D().setPositionIj(i,j);
    }


    private void showInfo(){
        /*log.debug("Camera i: " + (int)(world.getCamera2D().getX()/128));
        log.debug("Camera j: " + (int)(world.getCamera2D().getY()/128));
        log.debug("Camera width: " + world.getCamera2D().getViewPortWidth()/128);
        log.debug("Camera height: " + world.getCamera2D().getViewPortHeight()/128);*/
    }


    public static class  World extends World2D{
        @Override
        protected MapWorld onDefineMapWorld(MapWorld.Builder builder) {
            return builderWorld.build();
        }
    }

    public static class GroundTiledLayer extends TiledLayer {
        @Override
        public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
            return TestMap.getTileSet(builder);
        }

        @Override
        protected MapLayer onDefineMapObjects() {
            return getMapGroundLayer();
        }
    }

    public static class WorldTiledLayer extends TiledLayer {
        @Override
        public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
            return TestMap.getNumbersTileSet(builder);
        }

        @Override
        protected MapLayer onDefineMapObjects() {
            return getMapWorldLayer();
        }
    }


    public static  MapLayer getMapGroundLayer(){

        int TILES_X = 20;
        MapLayer.Builder builder = new MapLayer.Builder()
                .setName("MapLayer")
                .setOffset(0, 0)
                .setGridSize(TILES_X, 5)
                .setTileSize(64, 64);

        int i=0; int id=1000; int j=1;
        builder.item(TestMap.MockTile.class, id++,i,j+1,"tile",1)
                .item(TestMap.MockTile.class, id++,i,j,"tile",6);
        for(i++; i<TILES_X-1; i++ ){
            builder.item(TestMap.MockTile.class, id++,i,j+1,"tile",2)
                    .item(TestMap.MockTile.class, id++,i,j,"tile",7);
        }
        builder.item(TestMap.MockTile.class, id++,i,j+1,"tile",3)
                .item(TestMap.MockTile.class, id,i,j,"tile",8);
        return builder.build();
    }

    public static MapLayer getMapWorldLayer(){
        MapLayer.Builder builder = new MapLayer.Builder()
                .setName("MapWorldLayer")
                .setGridSize(40, 5)
                .setTileSize(128, 128);

        int id=3000;
        for(int i=0; i<40; i++ ){
            builder.item(TestMap.MockTile.class, id++,i,0,"numbers",i);
        }

        return builder.build();
    }
}
