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

package com.titicolab.mock.tools;

import com.titicolab.android.GameActivity;
import com.titicolab.nanux.util.DisplayInfo;
import com.titicolab.nanux.util.ObjectSync;
import com.titicolab.opengl.util.LogHelper;
import com.titicolab.puppet.animation.AnimationSheet;
import com.titicolab.puppet.list.GameObjectList;
import com.titicolab.puppet.objects.base.Scene;
import com.titicolab.puppet.objects.factory.RequestCollection;
import com.titicolab.puppet.objects.factory.RequestLayersBuilder;
import com.titicolab.puppet.objects.factory.RequestObject;
import com.titicolab.puppet.objects.map.MapGroupLayers;

/**
 * Created by campino on 12/12/2017.
 *
 */

public class TestScene  extends Scene {

    public final ObjectSync<Integer> onAttachParams;
    public final ObjectSync<Integer> onDefineAnimations;
    public final ObjectSync<Integer> onLayersRequest;
    public final ObjectSync<Integer> onAttachLayers;
    public final ObjectSync<Integer> onDefineMapGroupLayers;
    public final ObjectSync<Integer> onDefineCameras;
    public final ObjectSync<Integer> onGroupLayersCreated;
    private int counter;
    final LogHelper log;


    public TestScene() {
        super();

        counter = 1;
        onAttachParams = new ObjectSync<>();
        onDefineAnimations = new ObjectSync<>();
        onLayersRequest = new ObjectSync<>();
        onAttachLayers = new ObjectSync<>();
        onDefineMapGroupLayers = new ObjectSync<>();
        onDefineCameras = new ObjectSync<>();
        onGroupLayersCreated = new ObjectSync<>();
        log = new LogHelper(this,GameActivity.class.getSimpleName());
    }

    @Override
    public void onAttachParameters(RequestObject request) {
        log.debug("onAttachParameters " + counter);
        onAttachParams.setResult(counter++);
        super.onAttachParameters(request);

    }

    @Override
    public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
        log.debug("onDefineAnimations "+ counter);
        onDefineAnimations.setResult(counter++);
        AnimationSheet tem= super.onDefineAnimations(builder);
        return tem;
    }

    @Override
    public MapGroupLayers onDefineMapGroupLayers() {
        log.debug("onDefineMapGroupLayers "+ counter);
        onDefineMapGroupLayers.setResult(counter++);
        MapGroupLayers tem = super.onDefineMapGroupLayers();
        return tem;
    }

    @Override
    protected void onDefineCameras(DisplayInfo displayInfo) {
        log.debug("onDefineCameras " + counter);
         onDefineCameras.setResult(counter++);
        super.onDefineCameras(displayInfo);
    }

    @Override
    public RequestCollection.RequestList onLayersRequest(RequestLayersBuilder builder) {
        log.debug("onLayersRequest "+ counter);
        onLayersRequest.setResult(counter++);
        RequestCollection.RequestList tem = super.onLayersRequest(builder);
        return  tem;
    }

    @Override
    public void onAttachLayers(GameObjectList layerList) {
        log.debug("onAttachLayers "+ counter);
        onAttachLayers.setResult(counter++);
        super.onAttachLayers(layerList);
    }

    @Override
    public void onGroupLayersCreated() {
        log.debug("onGroupLayersCreated "+ counter);
        onGroupLayersCreated.setResult(counter++);
        super.onGroupLayersCreated();

    }

    @Override
    public void onStart() {
        log.debug("onStart ");
        super.onStart();

    }

    @Override
    public void onStop() {
        log.debug("onStop ");
        super.onStop();
    }


}
