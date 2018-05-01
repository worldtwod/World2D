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


import com.titicolab.nanux.objects.base.Animated;
import com.titicolab.nanux.objects.base.UiObject;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



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
        Assert.assertNotNull(collection.getObject(MockTile0.class));
        Assert.assertNotNull(collection.getObject(MockTile0.class));
        Assert.assertNotNull(collection.getObject(MockTile1.class));
        Assert.assertThat(collection.getObjectsAvailable(MockTile0.class), Is.is(0));
        Assert.assertThat(collection.getObjectsAvailable(MockTile1.class), Is.is(2));
        Assert.assertThat(collection.getObjectsAvailable(MockSprite.class), Is.is(1));
        Assert.assertThat(collection.getCount(), Is.is(6));

    }

    @Test
    public void testObjectsAssignableFrom(){
        Assert.assertEquals(5,collection.sizeAssignableFrom(UiObject.class));
        Assert.assertEquals(1,collection.sizeAssignableFrom(MockSprite.class));
    }


    @Test(expected =  IllegalArgumentException.class)
    public void testObjectsNotAvailable(){
        Assert.assertNotNull(collection.getObject(MockSprite.class));
        Assert.assertNotNull(collection.getObject(MockSprite.class));
    }


    static class MockTile0 extends UiObject {
        @Override
        public void onCreated() {

        }
    }

    static class MockTile1 extends MockTile0 {

    }

    static class MockSprite extends Animated {

    }


}
