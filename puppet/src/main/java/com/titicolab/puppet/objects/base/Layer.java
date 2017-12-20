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

import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.DisplayInfo;
import com.titicolab.puppet.animation.AnimationSheet;
import com.titicolab.puppet.draw.DrawTools;
import com.titicolab.puppet.list.GameObjectCollection;
import com.titicolab.puppet.objects.factory.Parameters;
import com.titicolab.puppet.objects.factory.RequestCollection;
import com.titicolab.puppet.objects.factory.RequestObject;
import com.titicolab.puppet.objects.factory.RequestObjectBuilder;
import com.titicolab.puppet.objects.map.MapObjects;

/**
 * Created by campino on 05/01/2017.
 *
 */

public abstract class Layer extends BaseLayer<Layer.ParamsLayer>{


    private MapObjects mMapObjects;
    private Scene mScene;
    private BaseGroupLayer mGroupLayers;

    public Layer(){
        setUpdatable(true);
        setDrawable(true);
        setTouchable(true);
    }


    @Override
    protected void onAttachParameters(RequestObject request) {
        super.onAttachParameters(request);
        mMapObjects = onDefineMapObjects();
    }


    protected MapObjects onDefineMapObjects(){
        return getParameters()!=null?
                getParameters().mapObjects: new MapObjects.Builder().build();
    }


    protected void onAttachScene(Scene scene) {
       mScene = scene;
    }

    void onAttachGroupLayers(BaseGroupLayer group) {
        mGroupLayers = group;
    }

    @Override
    protected AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
        return mScene.onDefineAnimations(builder);
    }


    protected RequestCollection onRequestObjects(RequestObjectBuilder builder) {
        return builder.object(mMapObjects.getList()).build();
    }

    protected abstract void onAttachObjects(GameObjectCollection collection);

    protected abstract void onGroupObjectsCreated();

    protected abstract void updateLogic();


    protected abstract void updateRender();

    protected abstract void onDraw(DrawTools drawer);

    protected abstract boolean onTouch(ObservableInput.Event input);


    public Camera2D getCamera2D(){
        return mScene.getCamera2D();
    }
    public CameraUi getCameraUi(){
        return mScene.getCameraUi();
    }
    public Scene getScene() {
        return mScene;
    }
    public MapObjects getMapObjects(){
        return mMapObjects;
    }
    public DisplayInfo getDisplayInfo() {
        return mScene.getDisplayInfo();
    }

    public static class ParamsLayer extends Parameters {
        protected final  MapObjects mapObjects;
        public ParamsLayer(MapObjects mapObjects) {
            this.mapObjects = mapObjects;
        }

        public MapObjects getMapObjects(){
            return  this.mapObjects;
        }

    }

}
