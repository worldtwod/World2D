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

package com.titicolab.nanux.objects.base;


import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.list.GameObjectList;
import com.titicolab.nanux.objects.factory.Parameters;
import com.titicolab.nanux.objects.factory.RequestCollection;
import com.titicolab.nanux.objects.factory.RequestLayersBuilder;
import com.titicolab.nanux.objects.factory.RequestObject;
import com.titicolab.nanux.objects.map.MapGroupLayers;
import com.titicolab.nanux.touch.ObservableInput;

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
    public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
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
    public void updateRender() {
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
