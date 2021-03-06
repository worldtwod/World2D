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

import com.titicolab.mock.R;
import com.titicolab.mock.rule.SceneTestRule;
import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.nanux.objects.factory.RequestObject;
import com.titicolab.puppet.map.MapLayer;
import com.titicolab.puppet.map.MapWorld;
import com.titicolab.puppet.objects.Actor;
import com.titicolab.puppet.objects.Tile;
import com.titicolab.puppet.objects.TiledLayer;
import com.titicolab.puppet.objects.World2D;
import com.titicolab.puppeteer.ui.JoystickLayer;

import org.junit.Rule;
import org.junit.Test;

/**
 * Created by campino on 24/01/2017.
 *
 */
public class MapTiledLayerTest {

    @Rule
    public SceneTestRule sceneTestRule = new SceneTestRule();

    @Test
    public void mapLayer(){
        World world2D = new World();
        sceneTestRule.syncPlay(world2D);
        world2D.setDrawBoundary(true);
        world2D.setDrawCamera(true);

    }

    public static class  World extends World2D implements JoystickLayer.OnClickJoystick {
        MockTiledLayer layer;
        @Override
        protected MapWorld onDefineMapWorld(MapWorld.Builder builder) {
            return builder
                    .setName("World")
                    .setGridSize(35, 8)
                    .setTileSize(128, 128)
                    .setCameraSize(7,false)
                    .setFocusedWindowSize(9,false)
                    .layer(MockTiledLayer.class,1,null)
                    .layer(JoystickLayer.class,2,null)
                    .build();
        }

        @Override
        protected void onGroupLayersCreated() {
            layer = (MockTiledLayer) findLayer(1);
            JoystickLayer joystickLayer = (JoystickLayer) findLayer(2);
            joystickLayer.setOnClickJoystickListener(this);
        }

        @Override
        public void onLeft() {
            /* int i = getCamera2D().getI();
            int j = getCamera2D().getJ();
            getCamera2D().setPositionIj(--i,j); */
            layer.sprite.goLeft();
        }

        @Override
        public void onRight() {
            /*int i = getCamera2D().getI();
            int j = getCamera2D().getJ();
            getCamera2D().setPositionIj(++i,j); */
            layer.sprite.goRight();
        }

        @Override
        public void onTop() {
             int i = getCamera2D().getI();
             int j = getCamera2D().getJ();
             getCamera2D().setPositionIj(i-1,j);
        }

        @Override
        public void onBottom(){
            int i = getCamera2D().getI();
            int j = getCamera2D().getJ();
            getCamera2D().setPositionIj(i+1,j);
        }
    }


    public static class MockTiledLayer extends TiledLayer {
        MockSprite sprite;

        @Override
        public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
            return builder.
                    sequence("tile")
                    .resources(R.drawable.test_grid)
                        .clip(1).grid(8,5).cells(0)
                        .clip(2).grid(8,5).cells(1)
                        .clip(3).grid(8,5).cells(2)
                        .clip(4).grid(8,5).cells(3)
                        .clip(5).grid(8,5).cells(4)
                        .clip(6).grid(8,5).cells(5)
                        .clip(7).grid(8,5).cells(6)
                        .clip(8).grid(8,5).cells(7)
                        .clip(9).grid(8,5).cells(8)
                        .clip(10).grid(8,5).cells(9)
                        .clip(11).grid(8,5).cells(10)
                        .clip(12).grid(8,5).cells(11)
                        .clip(13).grid(8,5).cells(12)
                        .clip(14).grid(8,5).cells(13)
                        .clip(15).grid(8,5).cells(14)
                        .clip(16).grid(8,5).cells(15)
                    .sequence("sprite")
                        .clip(1)
                            .grid(8,5)
                            .cells(new int[]{7,15,23,31,39})
                    .build();
        }

        @Override
        protected void onAttachParameters(RequestObject request) {
            super.onAttachParameters(request);
        }

        @Override
        protected MapLayer onDefineMapObjects() {
            return  new MapLayer.Builder()
                    .setName("MapLayer")
                    .setOffset(0,0)
                    .setGridSize(16, 5)
                    .setTileSize(128, 128)
                    .item(MockTile.class, 1000,0,2,"tile",1)
                    .item(MockTile.class, 1001,1,2,"tile",2)
                    .item(MockTile.class, 1002,2,2,"tile",3)
                    .item(MockTile.class, 1003,3,2,"tile",4)
                    .item(MockTile.class, 1004,4,2,"tile",5)
                    .item(MockTile.class, 1005,5,2,"tile",6)
                    .item(MockTile.class, 1006,6,2,"tile",7)
                    .item(MockTile.class, 1007,7,2,"tile",8)
                    .item(MockTile.class, 1008,8,2,"tile",9)
                    .item(MockTile.class, 1009,9,2,"tile",10)
                    .item(MockTile.class, 1010,10,2,"tile",11)
                    .item(MockTile.class, 1011,11,2,"tile",12)
                    .item(MockTile.class, 1012,12,2,"tile",13)
                    .item(MockTile.class, 1013,13,2,"tile",14)
                    .item(MockTile.class, 1014,14,2,"tile",15)
                    .item(MockTile.class, 1015,15,2,"tile",16)

                    .item(MockTile.class, 1016,15,0,"tile",1)
                    .item(MockTile.class, 1017,15,1,"tile",2)

                    .item(MockTile.class, 1019,15,3,"tile",3)
                    .item(MockTile.class, 1020,15,4,"tile",4)

                    .item(MockSprite.class,2001,0,1,"sprite",1)
                    .build();
        }

        @Override
        protected void onGroupObjectsCreated() {
            sprite = (MockSprite) findById(2001);
            sprite.getAnimator().setSpeed(1);

        }
    }

    @SuppressWarnings("WeakerAccess")
    public static class MockSprite extends Actor {
        private boolean goLeft;
        private boolean goRight;
        @Override
        protected synchronized void updateLogic() {

            if(goLeft) {
                setPositionIj(getI() - 1, getJ());
                goLeft=false;
            }else if(goRight){
                setPositionIj(getI()+1,getJ());
                goRight=false;
            }

        }

        public void goLeft(){
            goLeft=true;
        }

        public void goRight(){
            goRight=true;
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static class MockTile extends Tile{
    }
}
