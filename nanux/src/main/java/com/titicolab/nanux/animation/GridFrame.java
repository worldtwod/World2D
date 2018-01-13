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

package com.titicolab.nanux.animation;


import com.titicolab.nanux.graphics.texture.Texture;

/**
 * Created by campino on 19/05/2016.
 *
 * Group of frames that share the same texture, they ara place in a cell array of columns and rows
 * Also it have the topSp-leftSp coordinates in the texture and it size
 *
 * the frames are create with the function .createFrames()
 *
 */
public class GridFrame extends GroupFrame {

    private float leftSp;
    private float topSp;

    protected float width;
    protected float height;

    private int    cells;
    private int    rows;
    private int    columns;


    public GridFrame(Texture texture){
        super(texture,1);
    }


    public GridFrame setLeftTop(float left, float top){
            this.leftSp =left;
            this.topSp =top;
        return  this;
    }

    public GridFrame setGridSize(float width, float height){
        this.width =width;
        this.height =height;
        return this;
    }

    public GridFrame setGridShape(int columns, int rows){
        return setGridShape(columns,rows,(columns*rows));
    }

    public GridFrame setGridShape(int columns, int rows, int cells){
        this.rows=rows;
        this.columns=columns;
        this.cells = cells;
        return this;
    }

    public GridFrame createFrames() {
        checkValues();
        int length = cells;
        for (int i = 0; i < length; i++) {
            Frame frame = createFrame(i);
            add(frame);
        }
        return this;
    }

    private Frame createFrame(int cell) {
        int columns = this.columns;
        int rows=this.rows;
        float textureW = this.getTexture().getWidth();
        float textureH = this.getTexture().getHeight();

        float uGridW =  this.width /textureW;
        float vGridH =  this.height /textureH;
        float uWidthFrame=uGridW/columns;
        float vHeightFrame=vGridH/rows;

        float uOffsetW =  this.leftSp /textureW;
        float vOffsetH =  this.topSp /textureH;

        int i =(cell %columns);
        int j = (cell /columns);

        float left = i*uWidthFrame + uOffsetW;
        float right= left + uWidthFrame;
        float top  = j*vHeightFrame + vOffsetH;
        float bottom = top + vHeightFrame;
        return new Frame(getTexture(),left,top,right,bottom);
    }

    private void checkValues() {
        if(width ==0 && height ==0)  throw  new IllegalArgumentException
                ("The grid need have any width or any height");
        if(getTexture()==null) throw new RuntimeException
                ("There is not a texture to create the frames");
        if(cells==0)  throw  new IllegalArgumentException
                ("The number of index must be great than zero");
        if(rows==0 && columns==0)  throw  new IllegalArgumentException
                ("The grid need have any rows or any columns");
        if(cells>rows*columns)  throw  new IllegalArgumentException
                ("The number of cell must be lest that rows*columns");
    }

    public int getColumns() {
        return columns;
    }
}
