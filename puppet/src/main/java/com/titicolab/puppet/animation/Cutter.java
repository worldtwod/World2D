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

package com.titicolab.puppet.animation;

import com.titicolab.nanux.graphics.textures.Texture;
import com.titicolab.nanux.graphics.textures.TextureManager;

/**
 * Created by campino on 21/06/2016.
 *
 */
public class Cutter {

    //private final RunnerTask mRunner;
    private final TextureManager mTextureManager;
    private Animation mAnimation;
    private GridFrame mGridFrame;

    //New
    private float mWidth;
    private float mHeight;
    private float mTop;
    private float mLeft;
    private Texture mTexture;




    public Cutter(TextureManager textureManager) {
        mTextureManager = textureManager;
    }

    public Cutter reset(){
        mAnimation = null;
        Frame.setPadding(true);
        return this;
    }

    public Cutter setResources(int resource){
        mTexture = getTextureWithRunner(resource);
        mWidth = mTexture.getWidth();
        mHeight = mTexture.getHeight();
        setLeftTop(0,0);
        return this;
    }

    public Cutter setSize(float width, float height){
        checkWantedSize(width,height);
        mWidth = width;
        mHeight = height;
        return this;
    }

    public Cutter setLeftTop(float left, float top){
        checkLeftTop(left,top);
        mLeft = left;
        mTop = top;
        return this;
    }

    public Cutter makeGridGroup(int columns, int rows, int cells){
        checkGridShape(columns,rows,cells);
        checkMakeGrid();
        if(mAnimation ==null)
            mAnimation = new Animation();
        mGridFrame = new GridFrame(mTexture);
        mGridFrame.setLeftTop(mLeft,mTop);
        mGridFrame.setGridSize(mWidth,mHeight);
        mGridFrame.setGridShape(columns,rows,cells);
        mGridFrame.createFrames();
        return this;
    }



    /**
     * Area a rectangle from the texture resources
     * @param left  leftSp cornell
     * @param top   topSp cornell
     * @param width width cornell
     * @param height  height cornell
     * @return  the cutter, next you can use apply for getBuildCommand the sequence
     */
    public Cutter cutClipFrom(int left, int top, float width, float height, int id){
        setLeftTop(left,top);
        setSize(width,height);
        checkMakeGrid();

        if(mAnimation ==null)
            mAnimation = new Animation();

        mGridFrame = new GridFrame(mTexture);
        mGridFrame.setLeftTop(mLeft,mTop);
        mGridFrame.setGridSize(mWidth,mHeight);
        mGridFrame.setGridShape(1,1,1);
        mGridFrame.createFrames();
        cutClipFromGrid(0,id);
        return this;
    }
    /*public Cutter cutClipFrom(Area cutIn){
        return cutClipFrom(cutIn.left,cutIn.top,cutIn.width,cutIn.height);
    }*/

    /**
     * Area To clip from texture Resource, it is use all texture area
     * @return the cutter, next you can use apply for getBuildCommand the sequence
     */
    public Cutter cutClipAll(int id){
        checkMakeGrid();
        if(mAnimation ==null)
            mAnimation = new Animation();
        mGridFrame = new GridFrame(mTexture);
        mGridFrame.setLeftTop(mLeft,mTop);
        mGridFrame.setGridSize(mWidth,mHeight);
        mGridFrame.setGridShape(1,1,1);
        mGridFrame.createFrames();
        cutClipFromGrid(0,id);
        return this;
    }

    /**
     * Add a new Clip and initialize it with the frames from sequence to end
     * @param startCell sequence frame, inclusive
     * @param endCell   final frame, inclusive
     * @return the builder
     */
    public Cutter cutClipFromGrid(int startCell, int endCell, int id){
        checkMakeGrid();
        mAnimation.add(new Clip(mGridFrame,startCell,endCell,id));
        return this;
    }

    public Cutter cutClipFromGrid(int[] cells, int id){
        checkMakeGrid();
        mAnimation.add(new Clip(mGridFrame,cells,id));
        return this;
    }

    /**
     * Add un clip with only frame
     * @param cell frame that contain the clip
     * @return the builder
     */
    public Cutter cutClipFromGrid(int cell,int id){
        checkMakeGrid();
        mAnimation.add(new Clip(mGridFrame,cell,id));
        return this;
    }

    public Animation apply(){
        checkMakeGrid();
        if(mAnimation.size()<1)throw new RuntimeException
                ("before of build, it need tile some clip. " +
                        "use  clip() function for do it");
        Animation animation = mAnimation;
        mGridFrame=null;
        return animation;
    }

    private void checkLeftTop(float left, float top) {
        if((left<0 || top<0))
            throw  new IllegalArgumentException
                ("Left and topSp needs be great or equals to zero");
    }


    private void checkWantedSize(float widthPx, float heightPx) {
        if(widthPx<=0)  throw  new IllegalArgumentException
                ("The widthPx must be great than zero");
        if(heightPx<=0)  throw  new IllegalArgumentException
                ("The heightPx must be great than zero");

        if(widthPx>mTexture.getWidth())  throw  new IllegalArgumentException
                ("The cutter-width wanted is great than texture-width: " +
                        "you want " + widthPx + ", but texture is " + mTexture.getWidth());
        if(heightPx>mTexture.getHeight())  throw  new IllegalArgumentException
                ("The cutter-height is great than texture-height: " +
                        "you want " + heightPx + ", but texture is " + mTexture.getHeight());
    }

    private void checkGridShape(int columns, int rows, int cells) {
        if(cells==0)  throw  new IllegalArgumentException
                ("The number of index must be great than zero");
        if(rows==0 && columns==0)  throw  new IllegalArgumentException
                ("The grid need have any rows or any columns");
        if(cells>rows*columns)  throw  new IllegalArgumentException
                ("The number of cell must be lest that rows*columns");
    }

    private void checkMakeGrid() {
        if(mTexture == null) throw new RuntimeException
                ("Before of to tile, It needs set the resources," +
                        " use .setResources()");
    }


    private Texture getTextureWithRunner(final int resource) {
        Texture texture  = mTextureManager.getTexture(resource);
        if (texture == null) throw new RuntimeException
                ("Error request mTexture the mTexture");
        return  texture;
    }

    public GridFrame getGridFrame(){
        return mGridFrame;
    }


    public Animation apply(CutterProgram.BuildCommand sequenceCommand) {
        return apply(this,sequenceCommand);
    }

    /*******************************************************************************************
     * *****************************************************************************************
     * *****************************************************************************************/

    protected static Animation apply(Cutter cutter, CutterProgram.BuildCommand sequenceCommand) {
        cutter.reset();
        cutter.setResources(sequenceCommand.resources);
        int size = sequenceCommand.getListClips().size();

        for (int i = 0; i < size; i++) {

            CutterProgram.ClipBuildCommand clipCommand = sequenceCommand.getListClips().get(i);

            if (clipCommand.type == CutterProgram.ClipBuildCommand.TYPE_AREA) {
                CutterProgram.Area area = clipCommand.area;
                cutter.cutClipFrom(area.left, area.top, area.width, area.height, clipCommand.id);

            } else if (clipCommand.type == CutterProgram.ClipBuildCommand.TYPE_GRID) {
                CutterProgram.Grid grid = clipCommand.grid;
                cutter.makeGridGroup(grid.columns, grid.rows, grid.cells);
                if (clipCommand.hasCells())
                    cutter.cutClipFromGrid(clipCommand.cells.index,clipCommand.id);
                else
                    cutter.cutClipFromGrid(0, grid.cells - 1,clipCommand.id);

            } else if (clipCommand.type == CutterProgram.ClipBuildCommand.TYPE_GRID_AREA) {
                CutterProgram.Grid grid = clipCommand.grid;
                CutterProgram.Area area = clipCommand.area;
                cutter.setSize(area.width, area.height);
                cutter.setLeftTop(area.left, area.top);
                cutter.makeGridGroup(grid.columns, grid.rows, grid.cells);

                if (clipCommand.hasCells())
                    cutter.cutClipFromGrid(clipCommand.cells.index,clipCommand.id);
                else
                    cutter.cutClipFromGrid(0, grid.cells - 1, clipCommand.id);

            }else{
                throw new RuntimeException("clip build command known");
            }
        }

        Animation animation = cutter.apply();
        return animation;
    }


}
