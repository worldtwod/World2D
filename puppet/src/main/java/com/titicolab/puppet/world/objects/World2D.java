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

package com.titicolab.puppet.world.objects;


import com.titicolab.nanux.util.DisplayInfo;
import com.titicolab.puppet.objects.base.CameraUi;
import com.titicolab.puppet.objects.base.Scene;
import com.titicolab.puppet.world.map.MapWorld;

/**
 * Created by campino on 18/01/2017.
 *
 */

public abstract class World2D extends Scene {


    //private Rectangle worldBondary;
    // setLayer


    protected abstract MapWorld onDefineMapWorld(MapWorld.Builder builder);


    @Override
    protected MapWorld onDefineMapGroupLayers() {
        return onDefineMapWorld(new MapWorld.Builder());
    }


    @Override
    protected void onDefineCameras(DisplayInfo displayInfo) {
        setCameraUi(new CameraUi(displayInfo));
        CameraWorld2D cameraWorld = new CameraWorld2D(displayInfo);
        cameraWorld.setViewport(getMapWorld());
        setCamera2D(cameraWorld);
    }



    public MapWorld getMapWorld(){
        return (MapWorld) getMapGroupLayers();
    }

    @Override
    public CameraWorld2D getCamera2D() {
        return (CameraWorld2D) super.getCamera2D();
    }
}
