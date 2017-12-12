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

package com.titicolab.mock.puppet;

import android.support.test.runner.AndroidJUnit4;

import com.titicolab.mock.R;
import com.titicolab.mock.cases.puppet.ClipTestCase;
import com.titicolab.puppet.animation.Animation;
import com.titicolab.puppet.animation.AnimationBuilder;
import com.titicolab.puppet.animation.AnimationSheet;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by campino on 15/11/2016.
 *
 */
@RunWith(AndroidJUnit4.class)
public class AnimationFunctionalTest extends ClipTestCase {


    private static final int CLIP_ALL_TEXTURE = 1;
    private static final int CLIP_CELL_DIGITS = 2;


    @Test
    public void testFromGridResource(){

        AnimationSheet sheet = new AnimationSheet.Builder()
                .sequence("digits")
                .resources(R.drawable.test_map)
                .clip(CLIP_ALL_TEXTURE)
                .clip(CLIP_CELL_DIGITS)
                    .grid(8,4)
                    .cells(new int[]{0,1,2,3,4,5,6,7,8,9})
                .build();


        Animation animation
                = new AnimationBuilder(getTextureManager(),sheet).build("digits");


        showClip(animation.findClipById(CLIP_CELL_DIGITS));

        showClip(animation.findClipById(CLIP_ALL_TEXTURE));

    }



}
