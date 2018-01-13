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

package com.titicolab.nanux.animation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AnimationMapTest {




    @Test
    public void compileOk() throws Exception {
        AnimationMap map = AnimationMap.getBuilder()
                .sequence("1")
                  .resources(2)
                      .clip(3)
                        .area(new ClipMap.Area(1f,2f,100f,100f))
                      .clip(4)
                        .grid(new ClipMap.Grid(4,5))
                      .clip(5)
                        //.grid(new ClipMap.Grid(4,3))
                .build();


        assertEquals("1", map.getKey());
        assertEquals(2, map.getResources());
        assertEquals(3, map.size());

        assertEquals(3, map.getListClipMaps().size());

    }


    /*
    @Test(expected = RuntimeException.class)
    public void errorGrid(){
        try {
            AnimationMap.getBuilder()
                    .sequence("123").resources(7889)
                        .clip(654)
                            .area(new ClipMap.Area(1f, 2f, 100f, 100f))
                        .clip(1)
                            .grid(new ClipMap.Grid(4, 5))
                        .clip(2)
                    .build();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw e;
        }

   }
    */







}