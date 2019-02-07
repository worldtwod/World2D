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

package com.titicolab.mock.rule;


import android.annotation.SuppressLint;

import com.titicolab.nanux.animation.Animation;
import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.nanux.animation.Animator;
import com.titicolab.nanux.objects.base.Animated;
import com.titicolab.nanux.objects.base.Scene;
import com.titicolab.nanux.objects.base.SceneLayer;
import com.titicolab.nanux.objects.map.MapGroupLayers;
import com.titicolab.nanux.objects.map.MapItem;
import com.titicolab.nanux.objects.map.MapObjects;

import androidx.test.rule.ActivityTestRule;

/**
 * Created by campino on 10/11/2016.
 *
 */

@SuppressWarnings("WeakerAccess")
public class AnimationTestRule extends ActivityTestRule<SceneTestActivity>{

    private static Animation.DefineAnimationSheet sAnimationSheetBuilder;
    private static String sAnimationName;
    private static int sClipStart;

    private Animator mAnimator;

    public AnimationTestRule(Animation.DefineAnimationSheet defineAnimationSheet, String animation, int clipStar) {
        super(SceneTestActivity.class, false, true);
        sAnimationSheetBuilder = defineAnimationSheet;
        sAnimationName = animation;
        sClipStart = clipStar;
    }

    @SuppressLint("VisibleForTests")
    private void syncPlay() {
        SceneTestActivity activity =  getActivity();
        MockScene scene = new MockScene();
        activity.syncPlay(scene);
        scene.waitOnCreated(60);
        mAnimator =  scene.layer.animatedMockObject.getAnimator();
    }

    public Animator getAnimator() {
        if(mAnimator==null)
            syncPlay();
        return mAnimator;
    }

    private static AnimationSheet getAnimationSheet(AnimationSheet.Builder builder){
        return sAnimationSheetBuilder.onDefineAnimations(builder);
    }

    /**
     * Default scene for hold the layer, it is useful when you only want test a layer
     */
    public static class MockScene extends Scene{
        MockLayer layer;
        @Override
        public MapGroupLayers onDefineMapGroupLayers() {
            return new MapGroupLayers.Builder()
                    .setName("MockSceneAnimation")
                    .layer(MockLayer.class,1)
                    .build();
        }

        @Override
        public void onGroupLayersCreated() {
            layer = (MockLayer) findLayer(1);
        }
    }

    public static class MockLayer extends SceneLayer {
        AnimatedMockObject animatedMockObject;

        @Override
        public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
            return AnimationTestRule.getAnimationSheet(builder);
        }

        @Override
        protected MapObjects onDefineMapObjects() {
            return new MapObjects.Builder()
                    .setName("MapTest")
                    .item(new MapItem(AnimatedMockObject.class, 1000,
                            new Animated.ParamsAnimation(sAnimationName, sClipStart)))
                    .build();
        }

        @Override
        public void onGroupObjectsCreated() {
            animatedMockObject = findById(AnimatedMockObject.class,1000);
            animatedMockObject.setPosition(getCamera2D().getViewPortWidth()/2,
                    getCamera2D().getViewPortHeight()/2);
        }
    }

    public static class AnimatedMockObject extends Animated {

    }
}
