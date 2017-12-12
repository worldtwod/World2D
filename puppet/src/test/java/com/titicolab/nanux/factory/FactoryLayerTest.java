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

package com.titicolab.nanux.factory;

import com.titicolab.puppet.animation.Animation;
import com.titicolab.puppet.animation.AnimationBuilder;
import com.titicolab.puppet.objects.base.Animated;
import com.titicolab.puppet.objects.factory.RequestObjectBuilder;
import com.titicolab.puppet.objects.factory.RequestCollection;
import com.titicolab.puppet.objects.map.MapItem;

import org.junit.Test;


public class FactoryLayerTest {

    @Test
    public void initCollection(){
        RequestCollection request = new RequestObjectBuilder()
                .objects(new MapItem(RequestObjectBuilderTest.SpriteMock0.class, 1001, "1"))
                .objects(new MapItem(RequestObjectBuilderTest.SpriteMock1.class, 1002, "2"))
                .objects(new MapItem(RequestObjectBuilderTest.SpriteMock2.class, 1003, "3"))
                .objects(new MapItem(RequestObjectBuilderTest.SpriteMock2.class, 1004, "3"))
                .build();





    }




    static class SpriteMock0 extends Animated {
        @Override
        public Animation onBuildClips(AnimationBuilder builder) {
            return null;
        }
    }

    static class SpriteMock1 extends Animated {
        @Override
        public Animation onBuildClips(AnimationBuilder builder) {
            return null;
        }
    }

    static class SpriteMock2 extends Animated {
        @Override
        public Animation  onBuildClips(AnimationBuilder builder) {
            return null;
        }
    }



}