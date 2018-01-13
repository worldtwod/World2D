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


import com.titicolab.nanux.animation.Animation;
import com.titicolab.nanux.animation.AnimationBuilder;
import com.titicolab.nanux.objects.base.Animated;
import com.titicolab.nanux.objects.factory.RequestObjectBuilder;
import com.titicolab.nanux.objects.factory.RequestCollection;
import com.titicolab.nanux.objects.map.MapItem;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by campino on 09/06/2016.
 *
 */
public class RequestObjectBuilderTest {


    @Test
    public void initCollection(){
        RequestCollection request = new RequestObjectBuilder()
                .objects(new MapItem(SpriteMock0.class, 1001, new Animated.ParamsAnimation("Animation",0)))
                .objects(new MapItem(SpriteMock1.class, 1002,new Animated.ParamsAnimation("Animation",0)))
                .objects(new MapItem(SpriteMock2.class, 1003, new Animated.ParamsAnimation("Animation",0)))
                .objects(new MapItem(SpriteMock2.class, 1004, new Animated.ParamsAnimation("Animation",0)))
                .build();

        Assert.assertEquals(3,request.size());
        Assert.assertEquals(SpriteMock0.class,request.get(0).get(0).getType());
        Assert.assertEquals(SpriteMock1.class,request.get(1).get(0).getType());
        Assert.assertEquals(SpriteMock2.class,request.get(2).get(1).getType());
    }



    @Test(expected =  RuntimeException.class)
    public void testErrorEqualsIds(){
        new RequestObjectBuilder()
                .objects(new MapItem(SpriteMock0.class, 1001,new Animated.ParamsAnimation("Animation",0)))
                .objects(new MapItem(SpriteMock1.class, 1002, new Animated.ParamsAnimation("Animation",0)))
                .objects(new MapItem(SpriteMock2.class, 1003,new Animated.ParamsAnimation("Animation",0)))
                .objects(new MapItem(SpriteMock2.class, 1003, new Animated.ParamsAnimation("Animation",0)))
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
