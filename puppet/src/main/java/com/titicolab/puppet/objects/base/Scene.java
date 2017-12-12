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
import com.titicolab.nanux.util.FlagSync;
import com.titicolab.puppet.animation.AnimationSheet;
import com.titicolab.puppet.draw.DrawTools;
import com.titicolab.puppet.list.GameObjectList;
import com.titicolab.puppet.objects.Camera2D;
import com.titicolab.puppet.objects.CameraUi;
import com.titicolab.puppet.objects.factory.Parameters;
import com.titicolab.puppet.objects.factory.RequestCollection;
import com.titicolab.puppet.objects.factory.RequestLayersBuilder;
import com.titicolab.puppet.objects.factory.RequestObject;
import com.titicolab.puppet.objects.map.MapGroupLayers;


/**
 * Created by campino on 02/06/2016.
 *
 */
public class Scene extends BaseObject<Scene.ParamsScene>
                        implements ObjectFactory.LayerFactory,
                                    GameObject.OnDraw,
                                        ObservableInput.InputListener,
                                            AnimationSheet.DefineAnimations {



    private SceneManager    mSceneManager;
    private DisplayInfo     mDisplayInfo;
    private MapGroupLayers  mMapGroupLayers;


    private GameObjectList mLayerList;
    private CameraUi mCameraUi;
    private Camera2D mCamera2D;
    private final FlagSync mFlagOnCreated;

    public Scene() {
        mFlagOnCreated = new FlagSync();
        setDrawable(true);
        setUpdatable(true);
        setTouchable(true);
    }


    /*** Instantiation ***************************************************************************/

    @Override
    public void onAttachParameters(RequestObject request) {
        mMapGroupLayers = onDefineMapGroupLayers();
    }


    public MapGroupLayers onDefineMapGroupLayers(){
        return getParameters()!=null ?  getParameters().mapObjects : new MapGroupLayers.Builder().build();
    }


    void onAttachSceneManager(SceneManager sceneManager){
        mSceneManager = sceneManager;
    }

    void onAttachDisplayInfo(DisplayInfo displayInfo) {
        mDisplayInfo =displayInfo;
        onDefineCameras(displayInfo);
    }

    protected void onDefineCameras(DisplayInfo displayInfo) {
        mCameraUi = new CameraUi(displayInfo);
        mCamera2D = new Camera2D(displayInfo);
    }



    /*** Factory ********************************************************************************/
    @Override
    public RequestCollection.RequestList onLayersRequest(RequestLayersBuilder builder) {
        return builder.object(mMapGroupLayers.getList()).build();
    }

    @Override
    public void onAttachLayers(GameObjectList layerList) {
            mLayerList=layerList;
    }

    @Override
    public void onGroupLayersCreated() {
        mFlagOnCreated.assertFlag();
    }



    @Override
    public void onStart() {
        int layers = mLayerList.size();
        for (int i = 0; i < layers; i++) {
            mLayerList.get(i).onStart();
        }
    }

    @Override
    public void onStop() {
        int layers = mLayerList.size();
        for (int i = 0; i < layers; i++) {
            mLayerList.get(i).onStop();
        }
    }

    @Override
    public void updateLogic() {
        mCameraUi.updateLogic();
        mCamera2D.updateLogic();

        int layers = mLayerList.size();
        for (int i = 0; i < layers; i++) {
            mLayerList.get(i).updateLogic();
        }


    }

    @Override
    public void updateRender() {
        mCameraUi.updateRender();
        mCamera2D.updateRender();
        int layers = mLayerList.size();
        for (int i = 0; i < layers; i++) {
            mLayerList.get(i).updateRender();
        }
    }


    @Override
    public void onDraw(DrawTools drawer) {
        int layers = mLayerList.size();
        for (int i = 0; i < layers; i++) {
            ((GameObject.OnDraw) mLayerList.get(i)).onDraw(drawer);
        }
    }


    public BaseLayer findLayer(int id) {
        return (BaseLayer) mLayerList.findById(id);
    }

    public GameObjectList getLayerList() {
        return mLayerList;
    }

    public CameraUi getCameraUi() {
        return mCameraUi;
    }

    public Camera2D getCamera2D() {
        return mCamera2D;
    }



    @Override
    public boolean onTouch(ObservableInput.Event event) {
        return false;
    }

    @Override
    public boolean onKey(int keyEvent) {
        return false;
    }


    @Override
    public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
        return null;
    }


    public static class ParamsScene extends Parameters {
        final MapGroupLayers mapObjects;
        public ParamsScene(MapGroupLayers mapObjects) {
            this.mapObjects = mapObjects;
        }
    }


    /**
     * User for synchronization to the onCreate event. it is block until the scene is full created
     * @param seconds time in seconds
     * @return  si was create successfully
     */
    public boolean waitOnCreated(int seconds){
        return mFlagOnCreated.waitSyncSeconds(seconds);
    }


}
