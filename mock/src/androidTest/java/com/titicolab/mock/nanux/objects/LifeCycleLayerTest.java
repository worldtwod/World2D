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

package com.titicolab.mock.nanux.objects;

import com.titicolab.mock.R;
import com.titicolab.mock.rule.SceneTestRule;
import com.titicolab.mock.tools.TestButton;
import com.titicolab.mock.tools.TestDigit;
import com.titicolab.mock.tools.TestLayer;
import com.titicolab.mock.tools.TestScene;
import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.objects.base.Animated;
import com.titicolab.nanux.objects.base.Layer;
import com.titicolab.nanux.objects.map.MapGroupLayers;
import com.titicolab.nanux.objects.map.MapItem;
import com.titicolab.nanux.objects.map.MapObjects;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by campino on 12/12/2017.
 *
 */

public class LifeCycleLayerTest {


    @Rule
    public SceneTestRule rule = new SceneTestRule();

    @Test
    public void sceneTest(){
        MockScene scene = new MockScene();
        rule.syncPlay(scene);
        Assert.assertEquals(1,scene.onAttachParams.getResults().intValue());
        Assert.assertEquals(2,scene.onDefineMapGroupLayers.getResults().intValue());
        Assert.assertEquals(3,scene.onDefineCameras.getResults().intValue());
        Assert.assertEquals(4,scene.onLayersRequest.getResults().intValue());
        Assert.assertEquals(5,scene.onAttachLayers.getResults().intValue());
        Assert.assertEquals(6,scene.onGroupLayersCreated.getResults().intValue());
    }

    public static class MockScene extends TestScene{

        @Override
        public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
            return builder.
                    sequence("digits")
                    .resources(R.drawable.test_map)
                    .clip(1)
                        .grid(8,4)
                        .cells(new int[]{0,1,2,3,4,5,6,7,8,9})
                    .clip(2)
                        .grid(8,4)
                        .cells(0)
                    .sequence("button")
                    .clip(2)
                        .grid(8,4)
                        .cells(new int[]{16,17,18,19,24,25,26,27})
                    .build();
        }

        @Override
        public MapGroupLayers onDefineMapGroupLayers() {
            super.onDefineMapGroupLayers();

            MapObjects mapLayer = new MapObjects.Builder()
                    .setName("MapTest")
                    .item(new MapItem(TestDigit.class,  100,
                            new Animated.ParamsAnimation("digits", 1)))
                    .item(new MapItem(TestDigit.class,  101,
                            new Animated.ParamsAnimation("digits", 2)))
                    .item(new MapItem(TestButton.class, 100,
                            new Animated.ParamsAnimation("button", 2)))
                    .build();

            return new MapGroupLayers.Builder()
                    .setName("MapLayer")
                    .layer(MockLayer.class,1,new Layer.ParamsLayer(mapLayer))
                    .build();
        }
    }


    public static class MockLayer extends TestLayer {

        TestButton button;
        TestDigit digit0;
        TestDigit digit1;

        @Override
        public void onGroupObjectsCreated() {
            super.onGroupObjectsCreated();

            button = findById(TestButton.class, 100);
            digit0 = findById(TestDigit.class, 100);
            digit1 = findById(TestDigit.class, 101);

            button.setPosition(400, 300);
            button.getAnimator().setSpeed(1);

            digit0.setPosition(700, 300);
            digit0.getAnimator().setSpeed(1);
            digit1.setPosition(850, 300);
            digit1.getAnimator().setSpeed(10);
        }

        @Override
        public void updateLogic() {
            super.updateLogic();
        }

        @Override
        public void updateRender() {
            super.updateRender();
        }

        @Override
        public void onDraw(DrawTools drawer) {
            super.onDraw(drawer);
        }
    }



}
