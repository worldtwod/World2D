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


import com.titicolab.puppet.animation.Animation;
import com.titicolab.puppet.objects.base.Animated;

/**
 * Created by campino on 16/01/2017.
 *
 * Child of a TiledLayer
 *
 *
 */

public class LayerObject extends Animated {

    private int i;
    private int j;

    private TiledLayer mLayer;

    private boolean mFocused;

    @Override
    protected void onAttachAnimation(Animation animation) {
        super.onAttachAnimation(animation);
    }

    protected void onAttachLayer(TiledLayer layer){
        mLayer = layer;
        setPositionIj(getParameters().i,getParameters().j);
    }

    /**
     * set the position of object from parameters  i,j
     * @param i coordinate i  left as zero reference
     * @param j coordinate j  bottom as zero reference
     */
    public void setPositionIj(int i, int j){
        setIj(i,j);
        updateXYFromIj();
    }


    /**
     * Update the position from i, j coordinates and tile size, it is:
     */
    private void updateXYFromIj() {
        int tileWidth = mLayer.getTileWidth();
        int tileHeight = mLayer.getTileHeight();
        float x = tileWidth*i +tileWidth / 2;
        float y = tileHeight*j + tileHeight / 2;
        setPosition(x,y);
    }

    /**
     * set the position of object from parameters  i,j
     * @param i coordinate i  left as zero reference
     * @param j coordinate j  bottom as zero reference
     */
    protected void setIj(int i, int j){
        this.i = i;
        this.j = j;
    }

    protected int getI() {
        return i;
    }

    protected int getJ() {
        return j;
    }

    public boolean isFocused() {
        return mFocused;
    }

    public void setFocused(boolean focused) {
        this.mFocused = focused;
    }

    /******** Parameters **********************************************************************/


    public static class Params  extends ParamsAnimation{
        public final int i;
        public final int j;
        public Params(int i, int j, String animation, int startClip) {
            super(animation,startClip);
            this.i = i;
            this.j = j;
        }
    }

    @Override
    protected Params getParameters() {
        return (Params) super.getParameters();
    }


}
