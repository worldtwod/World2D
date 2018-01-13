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

package com.titicolab.app;

import android.support.test.runner.AndroidJUnit4;

import com.titicolab.app.objects.ExWorld2D;
import com.titicolab.mock.cases.world.World2DTestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by campino on 24/01/2017.
 *
 */


@RunWith(AndroidJUnit4.class)
public class ExWorldTest extends World2DTestCase {




    @Test
    public void mapLayer(){


        syncPlay(new ExWorld2D());
        setWorldBoundary(true);


        waitTouchSeconds(60*60);

        waitTouchSeconds(60*60);
        waitTouchSeconds(60*60);
        waitTouchSeconds(60*60);
        waitTouchSeconds(60*60);
        waitTouchSeconds(60*60);

    }




}
