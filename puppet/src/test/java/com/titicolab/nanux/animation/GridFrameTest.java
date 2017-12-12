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

import com.titicolab.nanux.graphics.textures.Texture;
import com.titicolab.nanux.mock.MockTexture;
import com.titicolab.puppet.animation.GridFrame;

import org.junit.Before;
import org.junit.Test;


public class GridFrameTest {
   
    private Texture texture;


    @Before
    public void before(){
        texture = MockTexture.getMock();
    }

    @Test
    public void takeAllTexture() throws Exception {
        GridFrame frame = new GridFrame(texture);

    }




}