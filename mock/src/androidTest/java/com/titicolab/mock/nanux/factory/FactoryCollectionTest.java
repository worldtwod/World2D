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

package com.titicolab.mock.nanux.factory;

import com.titicolab.mock.R;
import com.titicolab.mock.cases.puppet.AnimationTestCase;
import com.titicolab.nanux.animation.AnimationBuilder;
import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.nanux.list.GameObjectCollection;
import com.titicolab.nanux.objects.base.Animated;
import com.titicolab.nanux.objects.base.ObjectFactory;
import com.titicolab.nanux.objects.factory.RequestObjectBuilder;
import com.titicolab.nanux.objects.factory.RequestCollection;
import com.titicolab.nanux.objects.map.MapItem;
import com.titicolab.nanux.objects.map.MapObjects;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by campino on 04/12/2017.
 *
 */

public class FactoryCollectionTest extends AnimationTestCase {
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
                .sequence("button")
                    .clip(1)
                        .area(4*128,128*2,90*4,90*2)
                        .grid(4,2)
                .build();

        map = new MapObjects.Builder()
                .setName("MapTest")
                .item(new MapItem(Digit.class,  100, new Animated.ParamsAnimation("digits",1)))
                .item(new MapItem(Digit.class,  101, new Animated.ParamsAnimation("digits",1)))
                .item(new MapItem(Button.class, 100, new Animated.ParamsAnimation("button",1)))
                .build();
       builder = new AnimationBuilder(getTextureManager(),sheet);

    }

    @Test
    public void test_factoryAnimatedList(){


        RequestCollection requestCollection = new RequestObjectBuilder()
                .object(map.getList())
                .build();

        ObjectFactory factory = new ObjectFactory(null, getTextureManager());

        GameObjectCollection results = factory
                .factoryAnimatedCollection(requestCollection, builder);


        Assert.assertEquals(2,results.size());
        Assert.assertEquals(2,results.getObjectsAvailable(Digit.class));
        Assert.assertEquals(1,results.getObjectsAvailable(Button.class));

        Digit digits = results.findById(Digit.class,100);
        Button button = results.findById(Button.class,100);

        digits.setLeftTop(100,500);
        digits.getAnimator().playAndStop(1);
        digits.getAnimator().setSpeed(1);
        addAnimated(digits);
        button.setLeftTop(600,500);
        button.getAnimator().playAndRepeat(12);
        button.getAnimator().setSpeed(1);
        addAnimated(button);
        waitTouchSeconds(60);
    }



    public static class Digit extends Animated {


    }


    public static class Button extends Animated {


    }
}
