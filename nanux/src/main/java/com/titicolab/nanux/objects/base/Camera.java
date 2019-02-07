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
import com.titicolab.nanux.graphics.math.Projection;
import com.titicolab.nanux.graphics.math.ProjectionUi;

/**
 * Created by campino on 30/06/2016.
 *
 */
public class Camera  extends GameObject {

    private Projection mProjection;

    Camera(ProjectionUi projection) {
        mProjection = projection;
    }

    public void setPosition(float x, float y){

    }

    public Projection getProjection() {
        return mProjection;
    }
    public int  getViewPortWidth() {
        return (int) mProjection.getViewPortWidth();
    }
    public int getViewPortHeight() {
        return (int) mProjection.getViewPortHeight();
    }
    public float getWidth() {
        return getViewPortWidth();
    }
    public float getHeight() {
        return getViewPortHeight();
    }

    public float getX() {
        return 0;
    }
    public float getY() {
        return 0;
    }

    public  float pxToCameraX(float xPixels){
        return 0.0f;
    }
    public  float pxToCameraY(float yPixels){
        return 0.0f;
    }
    public float cameraXToPx(float x) {
        return 0.0f;
    }
    public float cameraYToPy(float y){
        return 0.0f;
    }

    @Override
    protected void updateLogic() {

    }

    @Override
    public void updateRender() {

    }

    public float[] getMatrix() {
        return mProjection.getMatrix();
    }

}
