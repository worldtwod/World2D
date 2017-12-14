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

package com.titicolab.mock.puppet.factory;

import com.titicolab.mock.R;
import com.titicolab.mock.cases.puppet.AnimationTestCase;
import com.titicolab.puppet.animation.AnimationBuilder;
import com.titicolab.puppet.animation.AnimationSheet;
import com.titicolab.puppet.list.GameObjectList;
import com.titicolab.puppet.objects.base.Animated;
import com.titicolab.puppet.objects.base.ObjectFactory;
import com.titicolab.puppet.objects.factory.RequestObjectBuilder;
import com.titicolab.puppet.objects.factory.RequestCollection;
import com.titicolab.puppet.objects.map.MapItem;
import com.titicolab.puppet.objects.map.MapObjects;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by campino on 04/12/2017.
 *
 */

public class FactoryListTest extends AnimationTestCase {
    private AnimationSheet sheet;
    private MapObjects map;
    private AnimationBuilder builder;


    @Before
    public void before(){
        sheet = new AnimationSheet.Builder()
                .sequence("digits")
                .resources(R.drawable.test_map)
                    .clip(1)
                        .grid(8,4)
                          .cells(new int[]{0,1,2,3,4,5,6,7,8,9})
                    .clip(2)
                         .grid(8,4)
                           .cells(new int[]{16,17,18,19,24,25,26,27})
                .build();

        map = new MapObjects.Builder()
                .setName("MapTest")
                .item(new MapItem(Digit.class, 100, new Animated.ParamsAnimation("digits",1)))
                .item(new MapItem(Digit.class, 101, new Animated.ParamsAnimation("digits",2)))
                .build();
       builder = new AnimationBuilder(getTextureManager(),sheet);

    }

    @Test
    public void test_factoryAnimatedList(){


        RequestCollection requestCollection = new RequestObjectBuilder()
                .object(map.getList())
                .build();

        ObjectFactory factory = new ObjectFactory(null, getTextureManager());

        GameObjectList results = factory.factoryAnimatedList(requestCollection.get(0), builder);


        Assert.assertEquals(2,results.size());

        Digit digits = (Digit) results.findById(100);
        Digit numbers = (Digit) results.findById(101);

        digits.setLeftTop(100,500);
        digits.getAnimator().playAndStop(1);
        digits.getAnimator().setSpeed(1);
        addAnimated(digits);
        numbers.setLeftTop(600,500);
        numbers.getAnimator().playAndRepeat(2);
        numbers.getAnimator().setSpeed(1);
        addAnimated(numbers);
        waitTouchSeconds(60);


    }



    public static class Digit extends Animated {




    }


}