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

package com.titicolab.nanux.list;


import com.titicolab.puppet.list.GameObjectCollection;
import com.titicolab.puppet.list.GameObjectList;
import com.titicolab.puppet.world.objects.SpritePhysics;
import com.titicolab.puppet.world.objects.Tile;
import com.titicolab.puppet.world.objects.WorldObjectCollection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by campino on 09/06/2016.
 *
 *
 */
public class LayerObjectCollectionUnitTest {
    GameObjectCollection objectCollection;

    @Before
    public void initCollection(){

        objectCollection = new GameObjectCollection(3);

        GameObjectList tiles = new GameObjectList(2);

        tiles.add(new MockTile(6,2,5));
        tiles.add(new MockTile(0,0,0));
        tiles.add(new MockTile(6,0,1));
        tiles.add(new MockTile(6,1,3));
        tiles.add(new MockTile(0,1,2));
        tiles.add(new MockTile(0,2,4));



        GameObjectList sprites = new GameObjectList(3);
        sprites.add(new MockSprite(1,0,0));
        sprites.add(new MockSpriteFoo(6,3,3));
        sprites.add(new MockSprite(5,2,2));
        sprites.add(new MockSprite(3,1,1));

        objectCollection.add(tiles);
        objectCollection.add(sprites);

    }


    @Test
    public void testObjectsAvailable(){
            // assert find object


        WorldObjectCollection collection = new WorldObjectCollection(objectCollection);

        //Constructor test
        assertEquals(6,collection.getTileList().size());
        assertEquals(4,collection.getSpriteList().size());


        collection.sortLeftBottomToRightTop(6);

        assertEquals(0,((MockSprite)collection.getSpriteList().get(0)).cell);
        assertEquals(1,((MockSprite)collection.getSpriteList().get(1)).cell);
        assertEquals(2,((MockSprite)collection.getSpriteList().get(2)).cell);
        assertEquals(3,((MockSprite)collection.getSpriteList().get(3)).cell);

        assertEquals(0,((MockTile)collection.getTileList().get(0)).cell);
        assertEquals(1,((MockTile)collection.getTileList().get(1)).cell);
        assertEquals(2,((MockTile)collection.getTileList().get(2)).cell);
        assertEquals(3,((MockTile)collection.getTileList().get(3)).cell);
        assertEquals(4,((MockTile)collection.getTileList().get(4)).cell);
        assertEquals(5,((MockTile)collection.getTileList().get(5)).cell);


    }




    static class MockTile extends Tile {
        final int cell;
        MockTile(int i, int j, int cell) {
            setIj(i,j);
            this.cell=cell;
        }
    }

    static class MockSprite extends SpritePhysics{
        final int cell;
        MockSprite(int i, int j,int cell){
               setIj(i,j);
            this.cell=cell;
        }
    }

    static class MockSpriteFoo extends MockSprite{
        MockSpriteFoo(int i, int j,int cell) {
            super(i, j,cell);
        }
    }

}
