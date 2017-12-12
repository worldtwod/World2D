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
import com.titicolab.puppet.animation.Frame;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class FrameTest {

    private Texture texture;


    @Before
    public void before(){
        texture = MockTexture.getMock();
    }

    @Test
    public void takeAllTexture() throws Exception {
        Frame frame = new Frame(texture);
        assertEquals(0,frame.getLeft(),0);
        assertEquals(1,frame.getRight(),0);
        assertEquals(0,frame.getTop(),0);
        assertEquals(1,frame.getBottom(),0);
        assertEquals(texture,frame.getTexture());
    }

    @Test
    public void takeConstructor() throws Exception {
        Frame frame = new Frame(texture,0.1f,0.2f,0.3f,0.4f);
        assertEquals(0.1f,frame.getLeft(),0);
        assertEquals(0.2f,frame.getTop(),0);
        assertEquals(0.3f,frame.getRight(),0);
        assertEquals(0.4f,frame.getBottom(),0);
        assertEquals(texture,frame.getTexture());
    }



}