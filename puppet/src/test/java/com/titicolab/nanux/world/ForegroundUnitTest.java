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

package com.titicolab.nanux.world;

import com.titicolab.puppet.world.objects.TileWindow;
import com.titicolab.puppet.world.objects.TiledLayer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by campino on 02/06/2016.
 *
 *
 */


public class ForegroundUnitTest {

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
    public void position_test() throws Exception {


        TileWindow tileWindow = new TileWindow();
        tileWindow.setTileSize(128,128);
        tileWindow.setLayerSize(16,4);
        tileWindow.setWindowSize(3,true,7/3);

        tileWindow.setPositionIj(1,1);


        tileWindow.u

        assertEquals(5, tileWindow.getI());
        assertEquals(1, tileWindow.getJ());
        assertEquals(3, tileWindow.getIStart());
        assertEquals(7, tileWindow.getIEnd());
        assertEquals(0, tileWindow.getJStart());
        assertEquals(2, tileWindow.getJEnd());


    }






}
