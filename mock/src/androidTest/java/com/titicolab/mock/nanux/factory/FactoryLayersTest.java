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
import com.titicolab.mock.rule.GraphicTestRule;
import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.nanux.list.GameObjectList;
import com.titicolab.nanux.objects.base.Animated;
import com.titicolab.nanux.objects.base.ObjectFactory;
import com.titicolab.nanux.objects.base.Scene;
import com.titicolab.nanux.objects.base.SceneLayer;
import com.titicolab.nanux.objects.map.MapGroupLayers;
import com.titicolab.nanux.objects.map.MapItem;
import com.titicolab.nanux.objects.map.MapObjects;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by campino on 04/12/2017.
 *
 */
@Deprecated
public class FactoryLayersTest  {

    @Rule
    public GraphicTestRule graphicRule = new GraphicTestRule.Builder()
            .build();
    @Ignore
    @Test
    public void test_factoryObjects(){


        MockScene scene = new MockScene();
        ObjectFactory factory = new ObjectFactory(scene, graphicRule.getTextureManager());


        GameObjectList results = factory
                .factoryGroupLayer(scene);

        Assert.assertEquals(1,results.size());


        Assert.assertNotNull(scene.layer);
        Assert.assertNotNull(scene.layer.button);
        Assert.assertNotNull(scene.layer.digit);
    }




    public static class MockScene extends Scene{
        MockLayer layer;

        public MockScene() {
            onAttachParameters(null);
        }

        @Override
        public MapGroupLayers onDefineMapGroupLayers() {
            return new MapGroupLayers.Builder()
                    .setName("MockSceneDigit")
                    .layer(MockLayer.class,1,null)
                    .build();
        }


        @Override
        public void onGroupLayersCreated() {
            layer = (MockLayer) findLayer(1);
        }
    }


    public static class MockLayer extends SceneLayer {

        Button button;
        Digit digit;

        @Override
        public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
            return builder
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
        }


        @Override
        protected MapObjects onDefineMapObjects() {
            return new MapObjects.Builder()
                    .setName("MapTest")
                    .item(new MapItem(Digit.class, 100, new Animated.ParamsAnimation("digits", 1)))
                    .item(new MapItem(Digit.class, 101, new Animated.ParamsAnimation("digits", 1)))
                    .item(new MapItem(Button.class, 100, new Animated.ParamsAnimation("button", 1)))
                    .build();
        }

        @Override
        public void onGroupObjectsCreated() {
            button = findById(Button.class,100);
            digit =   findById(Digit.class,100);

        }
    }

    public static class Digit extends Animated {
    }
    public static class Button extends Animated {
    }

}
