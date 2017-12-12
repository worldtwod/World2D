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

package com.titicolab.nanux.map;

import com.titicolab.nanux.mock.MockAnimated;
import com.titicolab.puppet.objects.map.MapItem;
import com.titicolab.puppet.objects.map.MapObjects;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by campino on 03/12/2017.
 *
 */

public class MapObjectsTest {





    @Test
    public void mapLayerTest() {


        MapObjects mapLayer = new MapObjects.Builder()
                .setName("Collection")
                .item(new MapItem(MockAnimated.class, 100, "params"))
                .item(new MapItem(MockAnimated.class, 101, "params"))
                .build();

        Assert.assertEquals(mapLayer.getName(), "Collection");
        Assert.assertEquals(2, mapLayer.getList().size());
    }


}
