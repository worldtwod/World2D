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

import com.titicolab.nanux.objects.base.BaseLayer;
import com.titicolab.nanux.objects.base.Scene;
import com.titicolab.nanux.objects.factory.RequestCollection;
import com.titicolab.nanux.objects.factory.RequestLayersBuilder;
import com.titicolab.nanux.objects.map.MapItem;

import androidx.test.rule.ActivityTestRule;

/**
 * Created by campino on 10/11/2016.
 *
 */

@SuppressWarnings("WeakerAccess")
public class SceneTestRule  extends ActivityTestRule<SceneTestActivity>{


    public SceneTestRule() {
        super(SceneTestActivity.class, false, true);
    }

    @SuppressLint("VisibleForTests")
    public Scene syncPlay(Scene scene) {
        SceneTestActivity activity =  getActivity();
        activity.syncPlay(scene);
        scene.waitOnCreated(60);
        return scene;
    }

    public BaseLayer syncPlay(Class<? extends BaseLayer> clazz){
        DefaultScene scene = new DefaultScene(clazz);
        syncPlay(scene);
        return scene.findLayer(1);
    }


    /**
     * DefaultFade scene for hold the layer, it is useful when you only want test a layer
     */
    public class DefaultScene extends Scene{
        final Class<? extends BaseLayer> clazz;
        DefaultScene(Class<? extends BaseLayer> clazz) {
            this.clazz = clazz;
        }

        @Override
        protected RequestCollection.RequestList onLayersRequest(RequestLayersBuilder builder) {
            return builder
                    .objects(new MapItem(clazz,1,null))
                    .build();
        }

        @Override
        protected void onGroupLayersCreated() {
            super.onGroupLayersCreated();
        }

        @Override
        protected void updateLogic() {
            super.updateLogic();
        }

        @Override
        public void updateRender() {
            super.updateRender();
        }
    }
}
