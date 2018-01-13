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

package com.titicolab.puppet;


import com.titicolab.nanux.list.GameObjectCollection;
import com.titicolab.nanux.list.GameObjectList;
import com.titicolab.puppet.objects.Actor;
import com.titicolab.puppet.objects.Tile;
import com.titicolab.puppet.objects.WorldObjectCollection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


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
        Assert.assertEquals(6,collection.getTileList().size());
        Assert.assertEquals(4,collection.getActorList().size());


        collection.sortLeftBottomToRightTop(6);

        Assert.assertEquals(0,((MockSprite)collection.getActorList().get(0)).cell);
        Assert.assertEquals(1,((MockSprite)collection.getActorList().get(1)).cell);
        Assert.assertEquals(2,((MockSprite)collection.getActorList().get(2)).cell);
        Assert.assertEquals(3,((MockSprite)collection.getActorList().get(3)).cell);

        Assert.assertEquals(0,((MockTile)collection.getTileList().get(0)).cell);
        Assert.assertEquals(1,((MockTile)collection.getTileList().get(1)).cell);
        Assert.assertEquals(2,((MockTile)collection.getTileList().get(2)).cell);
        Assert.assertEquals(3,((MockTile)collection.getTileList().get(3)).cell);
        Assert.assertEquals(4,((MockTile)collection.getTileList().get(4)).cell);
        Assert.assertEquals(5,((MockTile)collection.getTileList().get(5)).cell);


    }




    static class MockTile extends Tile {
        final int cell;
        MockTile(int i, int j, int cell) {
            setIj(i,j);
            this.cell=cell;
        }
    }

    static class MockSprite extends Actor {
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
