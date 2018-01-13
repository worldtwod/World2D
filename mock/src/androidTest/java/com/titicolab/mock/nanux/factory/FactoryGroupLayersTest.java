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
import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.nanux.objects.base.Animated;
import com.titicolab.nanux.objects.base.GroupLayer;
import com.titicolab.nanux.objects.base.Layer;
import com.titicolab.nanux.objects.base.ObjectFactory;
import com.titicolab.nanux.objects.base.Scene;
import com.titicolab.nanux.objects.base.SceneLayer;
import com.titicolab.nanux.objects.factory.RequestCollection;
import com.titicolab.nanux.objects.factory.RequestLayersBuilder;
import com.titicolab.nanux.objects.map.MapGroupLayers;
import com.titicolab.nanux.objects.map.MapItem;
import com.titicolab.nanux.objects.map.MapObjects;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by campino on 04/12/2017.
 *
 */

public class FactoryGroupLayersTest extends AnimationTestCase {
    private AnimationSheet sheet;
    private static MapGroupLayers mapMockGroup;
    private static MapGroupLayers mapMockScene;


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


        MapObjects mapObjects0 = new MapObjects.Builder()
                .setName("MapTest")
                .item(new MapItem(Digit.class, 100, new Animated.ParamsAnimation("digits", 1)))
                .item(new MapItem(Digit.class, 101, new Animated.ParamsAnimation("digits", 1)))
                .item(new MapItem(Button.class, 100, new Animated.ParamsAnimation("button", 1)))
                .build();

        MapObjects mapObjects1 = new MapObjects.Builder()
                .setName("MapTest")
                .item(new MapItem(Button.class, 100, new Animated.ParamsAnimation("button", 1)))
                .item(new MapItem(Button.class, 101, new Animated.ParamsAnimation("button", 1)))
                .item(new MapItem(Button.class, 102, new Animated.ParamsAnimation("button", 1)))
                .build();


        MapGroupLayers mapMockGroupLayer = new MapGroupLayers.Builder()
                .setName("MapMockGroupLayer")
                .layer(MockLayer0.class, 1, new Layer.ParamsLayer(mapObjects0))
                .layer(MockLayer1.class, 2, new Layer.ParamsLayer(mapObjects1))
                .layer(MockLayer1.class, 3, new Layer.ParamsLayer(mapObjects1))
                .build();

        mapMockGroup = new MapGroupLayers.Builder()
                .setName("MapMockGroup")
                .layer(MockLayer0.class,1,new Layer.ParamsLayer(mapObjects0))
                .layer(MockGroup.class,2,new GroupLayer.ParamsGroupLayer(mapMockGroupLayer))
                .build();


        mapMockScene = new MapGroupLayers.Builder()
                .setName("MapMockScene")
                .layer(MockGroup.class,1,new GroupLayer.ParamsGroupLayer(mapMockGroup))
                .layer(MockLayer1.class,2,new Layer.ParamsLayer(mapObjects1))
                .build();
    }

    @Test
    public void test_factoryObjects(){

        ObjectFactory factory = new ObjectFactory(null, getTextureManager());

        MockScene mockScene  = new MockScene();
        factory.factoryGroupLayer(mockScene);


        Assert.assertEquals(2,mockScene.getLayerList().size());
        MockGroup  group = (MockGroup) mockScene.findLayer(1);
        MockLayer1 layer = (MockLayer1) mockScene.findLayer(2);

        //group.onAttachLayers(resu

        Assert.assertEquals(1,layer.getObjectCollection().size());
        Assert.assertNotNull(layer.getObjectCollection().findById(Button.class,100));
        Assert.assertNotNull(layer.getObjectCollection().findById(Button.class,101));
        Assert.assertNotNull(layer.getObjectCollection().findById(Button.class,102));


        Assert.assertEquals(2,group.getLayerObjects().size());


        MockLayer0 layerOfGroup = (MockLayer0) group.getLayerObjects().findById(1);
        MockGroup  mockGroup    = (MockGroup) group.getLayerObjects().findById(2);


        Assert.assertEquals(2,layerOfGroup.getObjectCollection().size());
        Assert.assertNotNull(layerOfGroup.getObjectCollection().findById(Digit.class,100));
        Assert.assertNotNull(layerOfGroup.getObjectCollection().findById(Digit.class,101));
        Assert.assertNotNull(layerOfGroup.getObjectCollection().findById(Button.class,100));

        Assert.assertEquals(3,mockGroup.getLayerObjects().size());
        Assert.assertEquals(true,
                mockGroup.getLayerObjects().findById(1).getClass().isAssignableFrom(MockLayer0.class));
        Assert.assertEquals(true,
                mockGroup.getLayerObjects().findById(2).getClass().isAssignableFrom(MockLayer1.class));
        Assert.assertEquals(true,
                mockGroup.getLayerObjects().findById(3).getClass().isAssignableFrom(MockLayer1.class));


    }



    public static class Digit extends Animated {
    }
    public static class Button extends Animated {
    }
    public static class MockLayer0 extends SceneLayer {
    }
    public static class MockLayer1 extends SceneLayer{
    }


    public static class MockScene extends Scene {
        @Override
        public RequestCollection.RequestList onLayersRequest(RequestLayersBuilder builder) {
            return   builder.object(mapMockScene.getList())
                    .build();
        }
    }

    public static class MockGroup extends GroupLayer{

    }

}
