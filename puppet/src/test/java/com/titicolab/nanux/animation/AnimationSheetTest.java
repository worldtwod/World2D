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

import com.titicolab.puppet.animation.ClipMap;
import com.titicolab.puppet.animation.AnimationMap;
import com.titicolab.puppet.animation.AnimationSheet;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class AnimationSheetTest {




    @Test
    public void compileOk1() throws Exception {
        AnimationSheet sheet = AnimationSheet.getBuilder()
                .sequence("1000")
                    .resources(2000)
                    .clip(3001)
                    .clip(3002)
                    .area(new ClipMap.Area(1f,2f,100f,100f))
                    .clip(3003)
                    .grid(new ClipMap.Grid(4,5))
                .sequence("1001")
                    .resources(2001)
                    .clip(3004)
                    .grid(new ClipMap.Grid(4,4))
                    .cells(new ClipMap.Cells(new int[]{0,1,2}))
                .build();

        assertEquals(2, sheet.size());

        AnimationMap seq0 = sheet.getListAnimationMaps().get(0);
        assertEquals("1000",seq0.getKey());
        assertEquals(3,seq0.size());

        AnimationMap seq1 = sheet.getListAnimationMaps().get(1);
        assertEquals("1001",seq1.getKey());
        assertEquals(1,seq1.size());

        ClipMap clip3004 = seq1.getListClipMaps().get(0);

        assertEquals(ClipMap.TYPE_CLIP_GRID,clip3004.getType());
        assertArrayEquals(new int[]{0,1,2},clip3004.getCells().index);
        assertEquals(4,clip3004.getGrid().columns);
        assertEquals(4,clip3004.getGrid().rows);
        assertEquals(true,clip3004.getArea().isAreaFromParent());
        assertEquals(2001,clip3004.getResources());

    }

    @Test
    public void compileOk2() throws Exception {
        AnimationSheet sheet = AnimationSheet.getBuilder()
                .sequence("1000")
                    .resources(2000)
                    .clip(3001)
                        .area(new ClipMap.Area(1f,2f,100f,100f))
                .sequence("1001")
                    .clip(3004)
                        .grid(new ClipMap.Grid(4,4))
                        .cells(new ClipMap.Cells(new int[]{0,1,2}))
                .build();

        assertEquals(2, sheet.size());

        AnimationMap seq1001 = sheet.getListAnimationMaps().get(1);
        assertEquals("1001",seq1001.getKey());
        assertEquals(1,seq1001.size());

        ClipMap clip3004 = seq1001.getListClipMaps().get(0);

        assertEquals(ClipMap.TYPE_CLIP_GRID,clip3004.getType());

        assertEquals(4,clip3004.getGrid().columns);
        assertEquals(4,clip3004.getGrid().rows);
        assertEquals(true,clip3004.getArea().isAreaFromParent());
        assertEquals(2000,clip3004.getResources());


    }


    @Test
    public void compileOk3() throws Exception {
        AnimationSheet sheet = AnimationSheet.getBuilder()
                .sequence("1000")
                    .resources(1234)
                    .clip(3004)
                        .grid(new ClipMap.Grid(4,4))
                            .cells(new ClipMap.Cells(new int[]{0,1,2}))
                .build();

        assertEquals(1, sheet.size());
    }


    @Test(expected = RuntimeException.class)
    public void errorGrid(){

        try {
            AnimationSheet.getBuilder()
                    .sequence("1000")
                    .resources(2000)
                        .clip(3001)
                        .clip(3002)
                            .area(new ClipMap.Area(1f,2f,100f,100f))
                        .clip(3003)
                            .grid(new ClipMap.Grid(4,5))
                    .sequence("1001")
                    .build();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw e;
        }
    }





}