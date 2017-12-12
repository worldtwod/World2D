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
import com.titicolab.puppet.animation.AnimationSheet;
import com.titicolab.puppet.draw.DrawTools;
import com.titicolab.puppet.list.GameObjectList;
import com.titicolab.puppet.objects.factory.Parameters;
import com.titicolab.puppet.objects.factory.RequestCollection;
import com.titicolab.puppet.objects.factory.RequestLayersBuilder;
import com.titicolab.puppet.objects.factory.RequestObject;
import com.titicolab.puppet.objects.map.MapGroupLayers;

/**
 * Created by campino on 30/11/2017.
 *
 */

public  class GroupLayer extends BaseGroupLayer<GroupLayer.ParamsGroupLayer>{


    private MapGroupLayers mMapGroupLayers;
    private GameObjectList mLayerList;

    private Scene mScene;
    private BaseGroupLayer mGroupLayers;

    @Override
    protected void onAttachParameters(RequestObject request) {
            super.onAttachParameters(request);
        mMapGroupLayers = onDefineMapGroupLayers();
    }

    protected MapGroupLayers onDefineMapGroupLayers(){
        return getParameters().mapObjects;
    }



    void onAttachScene(Scene scene) {
       mScene = scene;
    }


    void onAttachGroupLayers(BaseGroupLayer group) {
      mGroupLayers = group;
    }


    @Override
    protected AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
        return mScene.onDefineAnimations(builder);
    }

    @Override
    protected RequestCollection.RequestList onLayersRequest(RequestLayersBuilder builder) {
        return builder.object(mMapGroupLayers.getList()).build();
    }

    @Override
    protected void onAttachLayers(GameObjectList layerList) {
        mLayerList =layerList;
    }


    @Override
    protected void onGroupLayersCreated() {

    }


    @Override
    protected void onStart() {
        int layers = mLayerList.size();
        for (int i = 0; i < layers; i++) {
            mLayerList.get(i).onStart();
        }
    }

    @Override
    protected void onStop() {
        int layers = mLayerList.size();
        for (int i = 0; i < layers; i++) {
            mLayerList.get(i).onStop();
        }
    }



    @Override
    protected void updateLogic() {
        HelperObjects.updateLogicLayers(mLayerList);
    }

    @Override
    protected void updateRender() {
        HelperObjects.updateRenderLayers(mLayerList);
    }


    @Override
    protected void onDraw(DrawTools drawer) {
        HelperObjects.onDrawLayers(mLayerList,drawer);
    }


    @Override
    protected boolean onTouch(ObservableInput.Event event) {
        return HelperObjects.notifyInputEvent(event,mLayerList);
    }



    public GameObjectList getLayerObjects() {
        return mLayerList;
    }

    public static class ParamsGroupLayer extends Parameters {
        final MapGroupLayers mapObjects;
        public ParamsGroupLayer(MapGroupLayers mapObjects) {
            this.mapObjects = mapObjects;
        }
    }
}
