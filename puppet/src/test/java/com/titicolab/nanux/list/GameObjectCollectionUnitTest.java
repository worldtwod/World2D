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

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by campino on 09/06/2016.
 *
 *
 */
public class GameObjectCollectionUnitTest {
    GameObjectCollection collection;

    @Before
    public void initCollection(){

        collection = new GameObjectCollection(3);

        GameObjectList gameObjectList0 = new GameObjectList(2);
        gameObjectList0.add(new MockTile0());
        gameObjectList0.add(new MockTile0());

        GameObjectList gameObjectList1 = new GameObjectList(3);
        gameObjectList1.add(new MockTile1());
        gameObjectList1.add(new MockTile1());
        gameObjectList1.add(new MockTile1());

        GameObjectList gameObjectList2 = new GameObjectList(1);
        gameObjectList2.add(new MockSprite());

        collection.add(gameObjectList0);
        collection.add(gameObjectList1);
        collection.add(gameObjectList2);
    }


    @Test
    public void testObjectsAvailable(){
            // assert find object
        assertNotNull(collection.getObject(MockTile0.class));
        assertNotNull(collection.getObject(MockTile0.class));
        assertNotNull(collection.getObject(MockTile1.class));
        assertThat(collection.getObjectsAvailable(MockTile0.class), is(0));
        assertThat(collection.getObjectsAvailable(MockTile1.class), is(2));
        assertThat(collection.getObjectsAvailable(MockSprite.class), is(1));
        assertThat(collection.getCount(),is(6));

    }

    @Test
    public void testObjectsAssignableFrom(){
        assertEquals(5,collection.sizeAssignableFrom(Tile.class));
        assertEquals(1,collection.sizeAssignableFrom(SpritePhysics.class));
    }


    @Test(expected =  IllegalArgumentException.class)
    public void testObjectsNotAvailable(){
        assertNotNull(collection.getObject(MockSprite.class));
        assertNotNull(collection.getObject(MockSprite.class));
    }


    static class MockTile0 extends Tile {

    }

    static class MockTile1 extends MockTile0 {

    }

    static class MockSprite extends SpritePhysics{

    }


}
