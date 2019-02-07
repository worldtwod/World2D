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

package com.titicolab.puppet.objects;


import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.graphics.draw.Rectangle;
import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.nanux.util.DisplayInfo;
import com.titicolab.nanux.objects.base.CameraUi;
import com.titicolab.nanux.objects.base.Scene;
import com.titicolab.puppet.map.MapWorld;

/**
 * Created by campino on 18/01/2017.
 *
 */

public abstract class World2D extends Scene {

    /** Size of rectangle line **/
    private static final float SIZE_BOUNDARY = 10;

    /** Rectangle for limit the world **/
    private Rectangle mWorldBoundary;

    protected abstract MapWorld onDefineMapWorld(MapWorld.Builder builder);

    @Override
    protected MapWorld onDefineMapGroupLayers() {
        return onDefineMapWorld(new MapWorld.Builder());
    }

    @Override
    protected void onDefineCameras(DisplayInfo displayInfo) {
        CameraUi cameraUi = new CameraUi(displayInfo);
        cameraUi.setViewport(
                (int)displayInfo.getReferenceWidth(),
                (int)displayInfo.getReferenceHeight(),
                ProjectionUi.SCALE_HEIGHT);
        setCameraUi(cameraUi);

        CameraWorld2D cameraWorld = new CameraWorld2D(displayInfo);
        cameraWorld.setViewport(getMapWorld());
        setCamera2D(cameraWorld);
    }

    @Override
    protected void onGroupLayersCreated() {

    }

    @Override
    public CameraWorld2D getCamera2D() {
        return (CameraWorld2D) super.getCamera2D();
    }

    @SuppressWarnings("WeakerAccess")
    public MapWorld getMapWorld(){
        return (MapWorld) getMapGroupLayers();
    }

    @Override
    protected void onDraw(DrawTools drawer) {
        super.onDraw(drawer);
        onDrawBoundary(drawer);
    }


    /** This option will draw the limit of world
     *  as rectangle
     * @param boundary tru to show
     */
    synchronized public void setDrawBoundary(boolean boundary){
        if(boundary){
            float width = getMapWorld().getWidth();
            float height = getMapWorld().getHeight();
            mWorldBoundary = new Rectangle(width, height,getDisplayInfo().getScalePixel());
            mWorldBoundary.setPosition(width / 2, height / 2);
        }else
            mWorldBoundary=null;
    }

    private void onDrawBoundary(DrawTools drawTools) {
        if(mWorldBoundary!=null) {
            mWorldBoundary.updateRender();
            drawTools.geometry.setBrushSize(SIZE_BOUNDARY);
            drawTools.geometry.begin(getCamera2D().getMatrix());
            drawTools.geometry.add(mWorldBoundary);
            drawTools.geometry.end();
        }
    }
}
