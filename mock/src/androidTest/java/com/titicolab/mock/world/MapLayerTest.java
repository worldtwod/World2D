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

package com.titicolab.mock.world;

import android.support.test.runner.AndroidJUnit4;

import com.titicolab.mock.R;
import com.titicolab.mock.cases.puppet.SceneTestCase;
import com.titicolab.puppet.animation.AnimationSheet;
import com.titicolab.puppet.objects.factory.RequestObject;
import com.titicolab.puppet.world.map.MapLayer;
import com.titicolab.puppet.world.objects.SpritePhysics;
import com.titicolab.puppet.world.objects.Tile;
import com.titicolab.puppet.world.objects.WorldLayer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by campino on 24/01/2017.
 *
 */


@RunWith(AndroidJUnit4.class)
public class MapLayerTest extends SceneTestCase {

    private static final int SEQUENCE_TILES = 1;
    private static final int SEQUENCE_SPRITE = 2;
    private static final int SEQUENCE_NEW_TILES = 3;


    private MapLayer mapLayer;



    @Before
    public void before(){

    }


    @Test
    public void mapLayer(){

    }




    public static class MockSprite extends SpritePhysics{
    }

    public static class MockTile extends Tile{

    }


    public static class MockWorldLayer extends WorldLayer {

        @Override
        public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
            return builder.
                    sequence("ground")
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
                    .setGridSize(16, 8)
                    .setTileSize(128, 128)
                    .item(MockTile.class, 1000,0,1,"tile",1)
                    .item(MockTile.class, 1001,1,1,"tile",2)
                    .item(MockTile.class, 1002,2,1,"tile",3)
                    .item(MockTile.class, 1003,3,1,"tile",4)
                    .item(MockTile.class, 1004,4,1,"tile",5)
                    .item(MockTile.class, 1005,5,1,"tile",6)
                    .item(MockTile.class, 1006,6,1,"tile",7)
                    .item(MockTile.class, 1007,7,1,"tile",8)
                    .item(MockTile.class, 1008,8,1,"tile",9)
                    .item(MockTile.class, 1009,9,1,"tile",10)
                    .item(MockTile.class, 1010,10,1,"tile",11)
                    .item(MockTile.class, 1011,11,1,"tile",12)
                    .item(MockTile.class, 1012,12,1,"tile",13)
                    .item(MockTile.class, 1013,13,1,"tile",14)
                    .item(MockTile.class, 1014,14,1,"tile",15)
                    .item(MockTile.class, 1015,15,1,"tile",16)

                    .item(MockSprite.class,2001,5,5,"sprite",1)
                    .build();

        }


    }


}
