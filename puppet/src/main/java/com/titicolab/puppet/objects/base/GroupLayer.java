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

public  class GroupLayer extends BaseLayer<GroupLayer.ParamsGroupLayer>
                        implements  ObjectFactory.LayerFactory {


    private MapGroupLayers mMapGroupLayers;
    private GameObjectList mLayerObjects;

    @Override
    public void onAttachParameters(RequestObject request) {
            super.onAttachParameters(request);
        mMapGroupLayers = onDefineMapGroupLayers();
    }


    protected MapGroupLayers onDefineMapGroupLayers(){
       return getParameters().mapObjects;
    }


    @Override
    void onAttachScene(Scene scene) {
        super.onAttachScene(scene);
    }

    @Override
    void onAttachLayerFactory(ObjectFactory.LayerFactory group) {
        super.onAttachLayerFactory(group);
    }

    @Override
    public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
        return null;
    }

    @Override
    public RequestCollection.RequestList onLayersRequest(RequestLayersBuilder builder) {
        return builder.object(mMapGroupLayers.getList()).build();
    }

    @Override
    public void onAttachLayers(GameObjectList layerList) {
        mLayerObjects =layerList;
    }

    @Override
    public void onGroupLayersCreated() {

    }


    @Override
    public void updateLogic() {

    }

    @Override
    public void updateRender() {

    }

    public GameObjectList getLayerObjects() {
        return mLayerObjects;
    }

    @Override
    public void onDraw(DrawTools drawer) {

    }


    public static class ParamsGroupLayer extends Parameters {
        final MapGroupLayers mapObjects;
        public ParamsGroupLayer(MapGroupLayers mapObjects) {
            this.mapObjects = mapObjects;
        }
    }
}
