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

package com.titicolab.mock.nanux.scene;

import com.titicolab.mock.rule.SceneTestRule;
import com.titicolab.nanux.objects.base.Transition;
import com.titicolab.nanux.objects.base.TransitionLayer;
import com.titicolab.nanux.util.FlagSync;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by campino on 20/12/2017.
 * Example for test a new transitions implementation.
 */

public class FadeLayerTest implements Transition.OnFullIn, Transition.OnFullOut{

    @Rule
    public SceneTestRule ruleScene = new SceneTestRule();
    private FlagSync mFadeIn;
    private FlagSync mFadeOut;

    @Before
    public void before(){
        mFadeIn = new FlagSync();
        mFadeOut = new FlagSync();
    }

    @Test
    public void fadeLayerTest(){
        MockLayerFade fade = (MockLayerFade) ruleScene.syncPlay(MockLayerFade.class);
        fade.setColor(1,1,0,1);
        fade.in(this);
        Assert.assertTrue(mFadeIn.waitSyncSeconds(20));
        fade.out(this);
        Assert.assertTrue(mFadeOut.waitSyncSeconds(20));
    }

    @Override
    public void onFullIn() {
        mFadeIn.assertFlag();
    }

    @Override
    public void onFullOut() {
        mFadeOut.assertFlag();
    }

    @SuppressWarnings("WeakerAccess")
    public  static class MockLayerFade extends TransitionLayer.Sliding {

    }
}
