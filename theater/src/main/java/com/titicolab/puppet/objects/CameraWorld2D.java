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


import com.titicolab.nanux.util.DisplayInfo;
import com.titicolab.nanux.objects.base.Camera2D;
import com.titicolab.puppet.map.MapWorld;

/**
 * Created by campino on 18/01/2017.
 *
 */

@SuppressWarnings("WeakerAccess")
public class CameraWorld2D extends Camera2D {

    protected static final float K_BUILDER = 25.0f;
    protected static final float CITA_BUILDER = 0.8f;
    private Actor mCarrier;
    private MapWorld mMapWorld;
    private float setPointX;
    private float setPointY;
    private  float d;
    private  float k;

    public CameraWorld2D(DisplayInfo displayInfo) {
        super(displayInfo);
        setParameters(K_BUILDER,CITA_BUILDER);
    }


    @Override
    public void updateLogic() {

        if(mCarrier !=null){
            setPointX = mCarrier.getX();
            setPointY = mCarrier.getY();
        }

        body.force.x = -d * body.velocity.x - k * body.position.x + setPointX * k;
        body.force.y = -d * body.velocity.y - k * body.position.y + setPointY * k;
        body.integrator();
        body.position.x = correctionBoundariesX(body.position.x);
        body.position.y = correctionBoundariesY(body.position.y);

        getProjection().setPosition(
                body.position.x,
                body.position.y);
        getProjection().setScale(getZoom());
    }


    @Override
    public void setPosition(float x, float y) {
        setPointX = correctionBoundariesX(x);
        setPointY = correctionBoundariesY(y);
        mCarrier = null;
    }

    /**
     * Set the camera size taking as reference the MapWorld and @Link{ MapWorld.isCameraFixedHeight()}
     * @param mapWorld the map world
     */
    public void setViewport(MapWorld mapWorld) {
        this.mMapWorld = mapWorld;
        int tiles = mapWorld.getCameraSize();
        if(mapWorld.isCameraFixedHeight()){
            setViewportTilesY(tiles);
        }else{
            setViewportTilesX(tiles);
        }
    }

    private void setViewportTilesX(int tilesX){
        int width =tilesX*mMapWorld.getTileWidth();
        int height = (int) (width/getAspectRatio());
        super.setViewport(width,height,HOLD_WIDTH);
    }

    private void setViewportTilesY(int tilesY){
        int height = tilesY*mMapWorld.getTileHeight();
        int width = (int) (height*getAspectRatio());
        super.setViewport(width,height,HOLD_HEIGHT);
    }

    /**
     * Apply the correction to x then it will be inside of X camera boundaries
     * @param x  value a to do correction
     * @return corrected value
     */
    public float correctionBoundariesX(float x){
        float cameraWidthHaft = getViewPortWidth()/2;
        x=x<cameraWidthHaft?cameraWidthHaft:x;
        x = x>(mMapWorld.getWidth()-cameraWidthHaft)?
                mMapWorld.getWidth()-cameraWidthHaft:x;
        return x;
    }
    /**
     * Apply the correction to x then it will be inside of Y camera boundaries
     * @param y  value a to do correction
     * @return corrected value
     */
    public float correctionBoundariesY(float y){
        float cameraHeightHaft = getViewPortHeight()/2;
        y=y<cameraHeightHaft?cameraHeightHaft:y;
        y = y>mMapWorld.getHeight()-cameraHeightHaft?
                mMapWorld.getHeight()-cameraHeightHaft:y;
        return y;
    }

    /**
     * Set the actor that carry the camera, it is the camera will follow it actor
     * @param carrier  actor
     */
    public void setCarrier(Actor carrier) {
        this.mCarrier = carrier;
    }

    /**
     * Set the position in x, this are world2D camera coordinates
     * @param x  position
     */
    public void setSetPointX(float x){
        setPointX = x;
    }
    /**
     * Set the position in y, this are world2D camera coordinates
     * @param y  position
     */
    public void setSetPointY(float y){
        setPointY = y;
    }

    /**
     * Set the parameters for camera physics
     * @param k k
     * @param cita c
     */
    public void setParameters(float k, float cita) {
        this.k = k;
        this.d = (float) (2.0f * Math.sqrt(k) * cita);
    }

    /**
     * Set the position of object from parameters  i,j
     * @param i coordinate i  left as zero reference
     * @param j coordinate j  bottom as zero reference
     */
    public void setPositionIj(int i, int j){
        updateXYFromIj(i,j);
    }

    /**
     * Update the position from i, j coordinates and tile size, it is:
     */
    private void updateXYFromIj(int i, int j) {
        int tileWidth = mMapWorld.getTileWidth();
        int tileHeight = mMapWorld.getTileHeight();
        float x = tileWidth*i +  tileWidth / 2;
        float y = tileHeight*j + tileHeight/ 2;
        setPosition(x,y);
    }

    public int getI(){
        int tileWidth = mMapWorld.getTileWidth();
        return (int)getX()/tileWidth;
    }

    public int getJ(){
        int tileHeight = mMapWorld.getTileHeight();
        return (int)getY()/tileHeight;
    }

}

