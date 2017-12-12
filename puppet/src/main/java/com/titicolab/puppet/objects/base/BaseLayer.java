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

package com.titicolab.puppet.objects.base;

import com.titicolab.puppet.animation.AnimationSheet;
import com.titicolab.puppet.objects.factory.Parameters;

/**
 * Created by campino on 03/12/2017.
 *
 */

public abstract class BaseLayer<T extends Parameters> extends BaseObject<T>
                implements AnimationSheet.DefineAnimations, GameObject.OnDraw{

    private Scene mScene;
    private ObjectFactory.LayerFactory mLayerFactory;

    void onAttachScene(Scene scene){
        mScene=scene;
    }
    void onAttachLayerFactory(ObjectFactory.LayerFactory group) {
        mLayerFactory =group;
    }


    public Scene getScene() {
        return mScene;
    }


    @Override
    public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
        return mScene.onDefineAnimations(builder);
    }
}
