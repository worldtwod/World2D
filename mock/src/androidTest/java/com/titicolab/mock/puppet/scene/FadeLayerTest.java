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

import com.titicolab.mock.cases.puppet.SceneTestCase;
import com.titicolab.nanux.util.FlagSync;
import com.titicolab.puppet.objects.base.Transition;
import com.titicolab.puppet.objects.base.TransitionLayer;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by campino on 20/12/2017.
 *
 */

public class FadeLayerTest extends SceneTestCase implements Transition.OnFullIn, Transition.OnFullOut {


    private FlagSync mFadeIn;
    private FlagSync mFadeOut;


    @Before
    public void before(){
        mFadeIn = new FlagSync();
        mFadeOut = new FlagSync();
    }

    @Test
    public void fadeLayerTest(){


        syncPlay(MockLayerFade.class);

        MockLayerFade fade= (MockLayerFade) getScene().findLayer(1);

        fade.setColor(1,1,0,1);

        fade.in(this);

        waitTouchSeconds(60*10);

        fade.out(this);

        waitTouchSeconds(60*10);
    }


    @Override
    public void onFullIn() {
        mFadeIn.assertFlag();
    }

    @Override
    public void onFullOut() {
        mFadeOut.assertFlag();
    }



    public  static class MockLayerFade extends TransitionLayer.Sliding {

    }
}
