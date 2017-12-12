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

package com.titicolab.puppet.draw;


import com.titicolab.nanux.graphics.textures.Texture;
import com.titicolab.puppet.model.DrawableObject;
import com.titicolab.puppet.model.ImageModel;
import com.titicolab.puppet.model.UvCoordinates;


/**
 * Created by campino on 17/05/2016.
 *
 */
public class Image extends DrawableObject {


    private int pivot;


    public Image(Texture texture) {
        super(new ImageModel(new UvCoordinates(texture)));
        reset();
        //GLTexture texture = getDrawModel().getUvCoordinates().getTexture();
        width =texture.getWidth();
        height =texture.getHeight();
        pivot =0;
    }


    public Image(UvCoordinates uvCoor) {
        super(new ImageModel(uvCoor));
        reset();
        Texture texture = getDrawModel().getTexture();
        float uvW = Math.abs(uvCoor.getLeft() - uvCoor.getRight());
        float uvH = Math.abs(uvCoor.getBottom() -uvCoor.getTop());
        width =  uvW*texture.getWidth();
        height =  uvH*texture.getHeight();
        pivot =0;
    }




    @Override
    protected void reset(){
        super.reset();
        color = new float[4];
        setColor(1f,1f,1f,1f);
    }

    @Override
    public int getTextureId(){
        return getDrawModel().getTexture().getTextureId();
    }


    /** Set RGBA color component,  1 more color 0 no color
     *
     * @param r red
     * @param g green
     * @param b blue
     * @param a apha
     */
    public void setColor(float r, float g, float b, float a){
        color[0]=r;
        color[1]=g;
        color[2]=b;
        color[3]=a;
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void setScale(float scale){
        if(pivot ==1) {
            float xLeft = x - width * this.scale / 2f;
            float yTop = y + height * this.scale / 2f;
            this.scale = scale;
            setPositionLeftTop(xLeft,yTop);
        }

        this.scale = scale;
    }


    /**
     * Set the position of top-left cornell of the image.
     * it make  setPosition(leftSp+width*scale/2,topSp-height*scale/2)
     * set first scale to getBuildCommand the right position center
     *
     * @param xLeft x coordinated  wanted for left cornell
     * @param yTop  y coordinated  wanted for top cornell
     */
    public void setPositionLeftTop(float xLeft, float yTop) {
        setPosition(xLeft+ width *scale/2,yTop- height *scale/2);
        pivot = 1;
    }

    @Override
    public void setPosition(float x, float y){
        super.setPosition(x,y);
        pivot =0;
    }

    @Override
    public ImageModel getDrawModel() {
        return (ImageModel) super.getDrawModel();
    }

    public void setUvCoordinates(UvCoordinates uvCoordinates){
        getDrawModel().setUvCoordinates(uvCoordinates);
    }
}
