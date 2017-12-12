package com.titicolab.nanux.graphics.math;

/**
 * Created by campino on 23/05/2016.
 *
 */
public abstract class Projection {
    float[] mProjectionMatrix = new float[16];
    protected float mViewPortWidth;
    protected float mViewPortHeight;

    Projection() {
    }

    protected float[] getMatrix() {
        return mProjectionMatrix;
    }

    protected void setPosition(float x, float y) {

    }

    public float getViewPortWidth() {
        return mViewPortWidth;
    }

    public float getViewPortHeight() {
        return mViewPortHeight;
    }
}
