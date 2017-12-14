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
import com.titicolab.nanux.util.ObjectSync;
import com.titicolab.opengl.util.LogHelper;
import com.titicolab.puppet.animation.AnimationSheet;
import com.titicolab.puppet.draw.DrawTools;
import com.titicolab.puppet.list.GameObjectCollection;
import com.titicolab.puppet.objects.base.SceneLayer;
import com.titicolab.puppet.objects.factory.RequestCollection;
import com.titicolab.puppet.objects.factory.RequestObject;
import com.titicolab.puppet.objects.factory.RequestObjectBuilder;
import com.titicolab.puppet.objects.map.MapObjects;

/**
 * Created by campino on 12/12/2017.
 *
 */

public class TestLayer extends SceneLayer {

    final LogHelper log;

    public final ObjectSync<Integer> onAttachParameters;
    public final ObjectSync<Integer> onDefineMapObjects;
    public final ObjectSync<Integer> onDefineAnimations;
    public final ObjectSync<Integer> onRequestObjects;
    public final ObjectSync<Integer> onAttachObjects;
    public final ObjectSync<Integer> onGroupObjectsCreated;
    private int counter;

    public TestLayer() {
        log = new LogHelper(this, GameActivity.class.getSimpleName());
        counter=1;
        onAttachParameters = new ObjectSync<>();
        onDefineMapObjects= new ObjectSync<>();
        onDefineAnimations= new ObjectSync<>();
        onRequestObjects= new ObjectSync<>();
        onAttachObjects= new ObjectSync<>();
        onGroupObjectsCreated= new ObjectSync<>();
    }


    @Override
    protected  void onAttachParameters(RequestObject request) {
        super.onAttachParameters(request);
        log.debug(counter + " onAttachParameters " + getId());
        onAttachObjects.setResult(counter++);

    }

    @Override
    protected MapObjects onDefineMapObjects() {
        log.debug(counter + " onDefineMapObjects " + getId() );
        onDefineMapObjects.setResult(counter++);
        return super.onDefineMapObjects();
    }

    @Override
    protected  AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
        log.debug(counter + " onDefineAnimations " + getId() );
        onDefineAnimations.setResult(counter++);
        return super.onDefineAnimations(builder);
    }

    @Override
    protected  RequestCollection onRequestObjects(RequestObjectBuilder builder) {
        log.debug(counter + " onRequestObjects " + getId() );
        onRequestObjects.setResult(counter++);
        return super.onRequestObjects(builder);
    }

    @Override
    protected  void onAttachObjects(GameObjectCollection collection) {
        log.debug(counter + " onAttachObjects " + getId() );
        onAttachObjects.setResult(counter++);
        super.onAttachObjects(collection);

    }

    @Override
    protected  void onGroupObjectsCreated() {
        log.debug(counter + " onGroupObjectsCreated " + getId() );
        onGroupObjectsCreated.setResult(counter++);
        super.onGroupObjectsCreated();

    }


    @Override
    protected  void onStart() {
        log.debug("onStart " + getId() );
        super.onStart();
    }

    @Override
    protected  void onStop() {
        log.debug("onStop " + getId() );
        super.onStop();
    }

    @Override
    protected  void onDestroy() {
        log.debug("onDestroy " + getId() );
        super.onDestroy();
    }




    @Override
    protected  void updateLogic() {
        super.updateLogic();
    }

    @Override
    protected  void updateRender() {
        super.updateRender();
    }

    @Override
    protected  void onDraw(DrawTools drawer) {
        super.onDraw(drawer);
    }
}
