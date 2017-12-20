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

package com.titicolab.mock.puppet.scene;

import com.titicolab.mock.R;
import com.titicolab.mock.cases.puppet.SceneManagerTestCase;
import com.titicolab.puppet.animation.AnimationSheet;
import com.titicolab.puppet.objects.base.Animated;
import com.titicolab.puppet.objects.base.Scene;
import com.titicolab.puppet.objects.base.SceneLayer;
import com.titicolab.puppet.objects.base.SceneManager;
import com.titicolab.puppet.objects.base.Transition;
import com.titicolab.puppet.objects.base.TransitionLayer;
import com.titicolab.puppet.objects.map.MapGroupLayers;
import com.titicolab.puppet.objects.map.MapItem;
import com.titicolab.puppet.objects.map.MapObjects;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by campino on 04/12/2017.
 *
 */

public class SceneManagerTransitionTest extends SceneManagerTestCase{


    @Test
    public void test_No_transition(){

        MockSceneDigit scene = new MockSceneDigit();
        SceneManager manager = getSceneManager();


        manager.play(scene);
        waitTouchSeconds(4);

        int w = scene.getCamera2D().getViewPortWidth()*3/7;
        int h = scene.getCamera2D().getViewPortHeight()/2;
        Assert.assertEquals(w,scene.layer.digit.getX(),0.1);
        Assert.assertEquals(h,scene.layer.digit.getY(),0.1);



        MockSceneButton sceneButton = new MockSceneButton();
        manager.play(sceneButton);
        waitTouchSeconds(4);
        w = scene.getCamera2D().getViewPortWidth()*5/7;
        Assert.assertEquals(w,sceneButton.layer.button.getX(),0.1);
        Assert.assertEquals(h,sceneButton.layer.button.getY(),0.1);

    }


    @Test
    public void test_transition(){

        MockSceneDigit scene = new MockSceneDigit();
        SceneManager manager = getSceneManager();
        Transition transition = new Transition();
        transition.setColor(1,1,0,1);
        getSceneManager().setTransition(transition);
        getSceneManager().setTransitionsEnable(true);


        manager.play(scene);
        waitTouchSeconds(4);

        int w = scene.getCamera2D().getViewPortWidth()*3/7;
        int h = scene.getCamera2D().getViewPortHeight()/2;
        Assert.assertEquals(w,scene.layer.digit.getX(),0.1);
        Assert.assertEquals(h,scene.layer.digit.getY(),0.1);

        manager.getTransition().setColor(1,0,0,1);

        MockSceneButton sceneButton = new MockSceneButton();
        manager.play(sceneButton);
        waitTouchSeconds(4);
        w = scene.getCamera2D().getViewPortWidth()*5/7;
        Assert.assertEquals(w,sceneButton.layer.button.getX(),0.1);
        Assert.assertEquals(h,sceneButton.layer.button.getY(),0.1);

        waitTouchSeconds(60*60);
    }


    @Test
    public void test_transition_slide(){

        MockSceneDigit scene = new MockSceneDigit();
        SceneManager manager = getSceneManager();
        Transition transition = new Transition(TransitionLayer.Sliding.class);
        transition.setColor(1,1,0,1);
        getSceneManager().setTransition(transition);
        getSceneManager().setTransitionsEnable(true);


        manager.play(scene);
        waitTouchSeconds(5);

        MockSceneButton sceneButton = new MockSceneButton();
        manager.play(sceneButton);
        waitTouchSeconds(10);

    }


    public static class MockSceneDigit extends Scene{
        MockLayerDigit layer;

        @Override
        public MapGroupLayers onDefineMapGroupLayers() {
            return new MapGroupLayers.Builder()
                    .setName("MockSceneDigit")
                    .layer(MockLayerDigit.class,1,null)
                    .build();
        }

        @Override
        public void onGroupLayersCreated() {
            layer = (MockLayerDigit) findLayer(1);
        }

    }

    public static class MockSceneButton extends Scene{
        MockLayerButton layer;
        @Override
        public MapGroupLayers onDefineMapGroupLayers() {
            return new MapGroupLayers.Builder()
                    .setName("MockSceneButton")
                    .layer(MockLayerButton.class,1,null)
                    .build();
        }

        @Override
        public void onGroupLayersCreated() {
            layer = (MockLayerButton) findLayer(1);
        }

    }

    public static class MockLayerDigit extends SceneLayer {
        Digit digit;
        private int x;

        @Override
        public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
            return builder
                    .sequence("digits")
                    .resources(R.drawable.test_map)
                    .clip(1)
                    .grid(8,4)
                    .cells(new int[]{0,1,2,3,4,5,6,7,8,9})
                    .build();
        }

        @Override
        protected MapObjects onDefineMapObjects() {
            return new MapObjects.Builder()
                    .setName("MapTest")
                    .item(new MapItem(Digit.class, 100, new Animated.ParamsAnimation("digits", 1)))
                    .build();
        }

        @Override
        public void onGroupObjectsCreated() {
            digit =   findById(Digit.class,100);
            digit.getAnimator().setSpeed(1);
            x=0;
        }

        @Override
        protected void updateLogic() {
            super.updateLogic();
            int w = getCamera2D().getViewPortWidth()*3/7;
            int h = getCamera2D().getViewPortHeight()/2;
            x = x<w? x+10:w;
            digit.setPosition(x,h);
        }
    }


    public static class MockLayerButton extends SceneLayer {
        Button button;
        private int x;

        @Override
        public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
            return builder
                    .sequence("button")
                    .resources(R.drawable.test_map)
                    .clip(1)
                    .area(512,256,330,220)
                    .grid(3,2)
                    .cells(new int[]{0,1,2})
                    .build();
        }

        @Override
        protected MapObjects onDefineMapObjects() {
            return new MapObjects.Builder()
                    .setName("MapTest")
                    .item(new MapItem(Button.class, 100,
                            new Animated.ParamsAnimation("button", 1)))
                    .build();
        }

        @Override
        public void onGroupObjectsCreated() {
            button =   findById(Button.class,100);
            button.getAnimator().setSpeed(1);
            x = getCamera2D().getViewPortWidth();
        }

        @Override
        protected void updateLogic() {
            super.updateLogic();
            int w = getCamera2D().getViewPortWidth()*5/7;
            int h = getCamera2D().getViewPortHeight();
            x = x>w? x-10:w;
            button.setPosition(x,h/2);
        }
    }

    public static class Digit extends Animated {
    }
    public static class Button extends Animated {
    }




}
