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

import com.titicolab.nanux.animation.ClipMap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ClipMapTest {


    @Test
    public void compileOk() throws Exception {
        ClipMap command = null;

        command = ClipMap.getBuilder()
                .clip(1)
                .area(new ClipMap.Area(1f,2f,100f,100f))
                .build();

        assertEquals(ClipMap.TYPE_CLIP_AREA, command.getType());

        command =  ClipMap.getBuilder()
                .clip(1)
                    .area(new ClipMap.Area(1f,2f,100f,100f))
                    .grid(new ClipMap.Grid(4,4))
                .build();

        assertEquals(ClipMap.TYPE_CLIP_GRID, command.getType());

        command = ClipMap.getBuilder()
                .clip(1)
                    .area(new ClipMap.Area(1f,2f,100f,100f))
                    .grid(new ClipMap.Grid(4,4))
                    .cells(new ClipMap.Cells(new int[]{1,2,2,1}))
                .build();

        assertEquals(ClipMap.TYPE_CLIP_GRID, command.getType());

        command =  ClipMap.getBuilder()
                .clip(1)
                    .grid(new ClipMap.Grid(4,4))
                    .cells(new ClipMap.Cells(new int[]{1,2,2,1}))
                .build();

        assertEquals(ClipMap.TYPE_CLIP_GRID, command.getType());

    }

    @Test(expected = RuntimeException.class)
    public void errorGrid(){
        ClipMap.getBuilder()
                .cells(new ClipMap.Cells(new int[]{1,2,2,1}))
                .clip(1);
    }





}