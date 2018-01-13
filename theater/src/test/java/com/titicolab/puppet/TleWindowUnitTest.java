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

import com.titicolab.puppet.objects.LayerObject;
import com.titicolab.puppet.objects.TileWindow;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by campino on 02/06/2016.
 *
 *
 */


public class TleWindowUnitTest {

    private static final int TILE_WIDTH = 128;
    private static final int TILE_HEIGHT = 128;

    /**

    The TiledLayer has 5 x 10 tiles

    --------------------
    4
    3
    2
    1
    0 1 2 3 4 5 6 7 8 9

 *
 */




    @Test
    public void fixHeight() throws Exception {

        TileWindow tileWindow = new TileWindow();
        tileWindow.setTileSize(128,128);
        tileWindow.setWorldSize(10,5);
        tileWindow.setWindowSize(3,true,5/3f);
        assertEquals(5, tileWindow.getTilesX());
        assertEquals(3, tileWindow.getTilesY());

        tileWindow.setPositionIj(2,1);


        assertEquals(true, tileWindow.isFocused(new MockTile(0,0)));
        assertEquals(true, tileWindow.isFocused(new MockTile(0,1)));
        assertEquals(true, tileWindow.isFocused(new MockTile(0,2)));
        assertEquals(false, tileWindow.isFocused(new MockTile(0,3)));

        assertEquals(true, tileWindow.isFocused(new MockTile(1,0)));
        assertEquals(true, tileWindow.isFocused(new MockTile(1,1)));
        assertEquals(true, tileWindow.isFocused(new MockTile(1,2)));
        assertEquals(false, tileWindow.isFocused(new MockTile(1,3)));


        assertEquals(true, tileWindow.isFocused(new MockTile(2,0)));
        assertEquals(true, tileWindow.isFocused(new MockTile(2,1)));
        assertEquals(true, tileWindow.isFocused(new MockTile(2,2)));
        assertEquals(false, tileWindow.isFocused(new MockTile(2,3)));

        assertEquals(true, tileWindow.isFocused(new MockTile(3,0)));
        assertEquals(true, tileWindow.isFocused(new MockTile(3,1)));
        assertEquals(true, tileWindow.isFocused(new MockTile(3,2)));
        assertEquals(false, tileWindow.isFocused(new MockTile(3,3)));

        assertEquals(true, tileWindow.isFocused(new MockTile(4,0)));
        assertEquals(true, tileWindow.isFocused(new MockTile(4,1)));
        assertEquals(true, tileWindow.isFocused(new MockTile(4,2)));
        assertEquals(false, tileWindow.isFocused(new MockTile(4,3)));
        assertEquals(false, tileWindow.isFocused(new MockTile(4,4)));

        assertEquals(false, tileWindow.isFocused(new MockTile(5,0)));
        assertEquals(false, tileWindow.isFocused(new MockTile(5,1)));
        assertEquals(false, tileWindow.isFocused(new MockTile(5,2)));
        assertEquals(false, tileWindow.isFocused(new MockTile(5,3)));
        assertEquals(false, tileWindow.isFocused(new MockTile(5,4)));

    }


    @Test
    public void fixWidth() throws Exception {

        TileWindow tileWindow = new TileWindow();
        tileWindow.setTileSize(128,128);
        tileWindow.setWorldSize(10,5);
        tileWindow.setWindowSize(5,false,5/3f);
        assertEquals(5, tileWindow.getTilesX());
        assertEquals(3, tileWindow.getTilesY());

        tileWindow.setPositionIj(2,1);


        assertEquals(true, tileWindow.isFocused(new MockTile(0,0)));
        assertEquals(true, tileWindow.isFocused(new MockTile(0,1)));
        assertEquals(true, tileWindow.isFocused(new MockTile(0,2)));
        assertEquals(false, tileWindow.isFocused(new MockTile(0,3)));

        assertEquals(true, tileWindow.isFocused(new MockTile(1,0)));
        assertEquals(true, tileWindow.isFocused(new MockTile(1,1)));
        assertEquals(true, tileWindow.isFocused(new MockTile(1,2)));
        assertEquals(false, tileWindow.isFocused(new MockTile(1,3)));


        assertEquals(true, tileWindow.isFocused(new MockTile(2,0)));
        assertEquals(true, tileWindow.isFocused(new MockTile(2,1)));
        assertEquals(true, tileWindow.isFocused(new MockTile(2,2)));
        assertEquals(false, tileWindow.isFocused(new MockTile(2,3)));

        assertEquals(true, tileWindow.isFocused(new MockTile(3,0)));
        assertEquals(true, tileWindow.isFocused(new MockTile(3,1)));
        assertEquals(true, tileWindow.isFocused(new MockTile(3,2)));
        assertEquals(false, tileWindow.isFocused(new MockTile(3,3)));

        assertEquals(true, tileWindow.isFocused(new MockTile(4,0)));
        assertEquals(true, tileWindow.isFocused(new MockTile(4,1)));
        assertEquals(true, tileWindow.isFocused(new MockTile(4,2)));
        assertEquals(false, tileWindow.isFocused(new MockTile(4,3)));
        assertEquals(false, tileWindow.isFocused(new MockTile(4,4)));

        assertEquals(false, tileWindow.isFocused(new MockTile(5,0)));
        assertEquals(false, tileWindow.isFocused(new MockTile(5,1)));
        assertEquals(false, tileWindow.isFocused(new MockTile(5,2)));
        assertEquals(false, tileWindow.isFocused(new MockTile(5,3)));
        assertEquals(false, tileWindow.isFocused(new MockTile(5,4)));

    }




    private  static class MockTile extends LayerObject {
        public MockTile(int i, int j) {
                setIj(i,j);
        }
    }






}
