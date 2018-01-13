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


import com.titicolab.nanux.graphics.math.ProjectionUi;
import com.titicolab.nanux.util.DisplayInfo;
import com.titicolab.nanux.objects.Physics;

/**
 * Created by campino on 24/11/2016.
 *
 */

public class Camera2D extends Camera {

    public static final int HOLD_WIDTH = ProjectionUi.SCALE_HEIGHT;
    public static final int HOLD_HEIGHT = ProjectionUi.SCALE_WIDTH;

    protected final Physics body;
    private final DisplayInfo mDisplayInfo;



    private float zoom;
    private float ratio;


    public Camera2D(DisplayInfo displayInfo) {
        super(new ProjectionUi(displayInfo));
        body = new Physics();
        setViewport(1280, 720, ProjectionUi.SCALE_HEIGHT);
        zoom = 1;
        ratio = displayInfo.getAspectRatio();
        mDisplayInfo = displayInfo;
    }


    @Override
    public void updateLogic() {
        getProjection().setScale(zoom);
    }

    public void setViewport(int refWidth, int refHeight, int scaleType) {
        getProjection().setViewport(refWidth, refHeight, scaleType);
        body.setPosition(getProjection().getX(), getProjection().getY());
    }

    @Override
    public ProjectionUi getProjection() {
        return (ProjectionUi) super.getProjection();
    }

    @Override
    public void setPosition(float x, float y) {
        body.setPosition(x, y);
        getProjection().setPosition(
                body.position.x,
                body.position.y);
    }

    public void addPosition(float x, float y) {
        body.addPosition(x, y);
    }

    public void zoom(float zoom) {
        zoom *= -1;
        if (this.zoom + zoom > 1.5f || this.zoom + zoom < 0.5f) {
            return;
        }
        this.zoom += zoom;
    }

    public float getZoom() {
        return zoom;
    }

    public float getX() {
        return body.position.x;
    }


    public float getY() {
        return body.position.y;
    }

    @Override
    public float getWidth() {
        return (int) (super.getViewPortWidth() * getProjection().getScale());
    }

    @Override
    public float getHeight() {
        return (int) (super.getViewPortHeight() * getProjection().getScale());
    }

    /**
     * The ratio Ratio of display
     * @return aspect ratio display
     */
    public float getAspectRatio() {
        return ratio;
    }

    @Override
    public float pxToCameraX(float xPixels) {
        float rate = getProjection().getViewPortWidth() / mDisplayInfo.getScreenWidth();
        return xPixels * rate + (getX() - getProjection().getViewPortWidth() / 2);
    }

    @Override
    public float pxToCameraY(float yPixels) {
        float screenH = mDisplayInfo.getScreenHeight();
        float rate = getProjection().getViewPortHeight() / screenH;
        return (screenH - yPixels) * rate + getY() - getProjection().getViewPortHeight() / 2;
    }

    public float cameraXToPx(float x) {
        float rate = getProjection().getViewPortWidth() / mDisplayInfo.getScreenWidth();
        return (x - getX() + getViewPortWidth()/2) / rate;
    }

    public float cameraYToPy(float y){
        float screenHeight = mDisplayInfo.getScreenHeight();
        float rate = getProjection().getViewPortHeight() / screenHeight;
        return screenHeight - ((y - getY() + getViewPortHeight()/2) / rate);
    }


    public DisplayInfo getDisplayInfo() {
        return mDisplayInfo;
    }
}


