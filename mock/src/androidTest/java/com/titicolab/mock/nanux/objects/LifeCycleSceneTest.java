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

import com.titicolab.mock.cases.puppet.SceneTestCase;
import com.titicolab.mock.tools.TestScene;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by campino on 12/12/2017.
 *
 */

public class LifeCycleSceneTest extends SceneTestCase{




    @Test
    public void sceneTest(){
        TestScene scene = new TestScene();
        syncPlay(scene);
        Assert.assertEquals(1,scene.onAttachParams.getResults().intValue());
        Assert.assertEquals(2,scene.onDefineMapGroupLayers.getResults().intValue());
        Assert.assertEquals(3,scene.onDefineCameras.getResults().intValue());
        Assert.assertEquals(4,scene.onLayersRequest.getResults().intValue());
        Assert.assertEquals(5,scene.onAttachLayers.getResults().intValue());
        Assert.assertEquals(6,scene.onGroupLayersCreated.getResults().intValue());

    }







}
