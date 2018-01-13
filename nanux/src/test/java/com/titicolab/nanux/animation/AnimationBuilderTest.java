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


import com.titicolab.nanux.mock.MockTextureManager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by campino on 04/12/2017.
 *
 */

public class AnimationBuilderTest {





    @Test
    public void compileOk() throws Exception {
        AnimationSheet sheet= AnimationSheet.getBuilder()
                .sequence("one")
                 .resources(2000)
                    .clip(1)
                    .clip(2)
                        .area(1f,2f,100f,100f)
                    .clip(3)
                        .grid(4,5)
                .sequence("two")
                    .resources(2001)
                     .clip(1)
                        .grid(new ClipMap.Grid(4,4))
                            .cells(new ClipMap.Cells(new int[]{0,1,2}))
                .build();

        assertEquals(2,sheet.size());

        Animation animation = new AnimationBuilder(new MockTextureManager(),sheet)
                                .build("one");


        assertEquals(3,animation.size());
        assertEquals(1, animation.findClipById(1).size());
        assertEquals(1, animation.findClipById(2).size());
        assertEquals(20, animation.findClipById(3).size());


    }
}
