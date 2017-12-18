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
import com.titicolab.puppet.objects.factory.Parameters;
import com.titicolab.puppet.objects.factory.RequestCollection;
import com.titicolab.puppet.objects.factory.RequestLayersBuilder;
import com.titicolab.puppet.objects.factory.RequestObject;
import com.titicolab.puppet.objects.map.MapGroupLayers;


/**
 * Created by campino on 02/06/2016.
 *
 */
public class Scene extends BaseGroupLayer<Scene.ParamsScene>{



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
    protected void onAttachParameters(RequestObject request) {
        mMapGroupLayers = onDefineMapGroupLayers();
    }


    protected MapGroupLayers onDefineMapGroupLayers(){
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


    protected AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
        return null;
    }


    /*** Factory ********************************************************************************/
    @Override
    protected RequestCollection.RequestList onLayersRequest(RequestLayersBuilder builder) {
        return builder.object(mMapGroupLayers.getList()).build();
    }

    @Override
    protected void onAttachLayers(GameObjectList layerList) {
        mLayerList=layerList;
        mFlagOnCreated.assertFlag();
    }

    @Override
    protected void onGroupLayersCreated(){

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
        mCameraUi.updateLogic();
        mCamera2D.updateLogic();
        HelperObjects.updateLogicLayers(mLayerList);

    }

    @Override
    public void updateRender() {
        mCameraUi.updateRender();
        mCamera2D.updateRender();
        HelperObjects.updateRenderLayers(mLayerList);
    }


    @Override
    protected void onDraw(DrawTools drawer) {
        HelperObjects.onDrawLayers(mLayerList,drawer);
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

    public MapGroupLayers getMapGroupLayers() {
        return mMapGroupLayers;
    }


    @Override
    protected boolean onTouch(ObservableInput.Event event) {
        return HelperObjects.notifyInputEvent(event,mLayerList);
    }


    public boolean onKey(int keyEvent) {
        return false;
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



    protected void setCameraUi(CameraUi cameraUi) {
        this.mCameraUi = cameraUi;
    }

    protected void setCamera2D(Camera2D camera2D) {
        this.mCamera2D = camera2D;
    }
}
