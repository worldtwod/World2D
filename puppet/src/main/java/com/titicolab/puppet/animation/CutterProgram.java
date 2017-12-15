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


import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.puppet.json.JsonHelper;

import java.util.HashMap;

/**
 * Created by campino on 15/12/2016.
 *
 */

public class CutterProgram implements JsonHelper.Serializable {

    private static final float WIDTH_ALL = -1;
    private static final float HEIGHT_ALL = -1;
    private static final int   DEFAULT_KEY = 2017;

    
    private HashMap<Integer,BuildCommand> mBuildCommandMap;

    private BuildCommand mCurrentSequence;


    public CutterProgram() {
        mBuildCommandMap = new HashMap<>();
    }


    /**
     * Number of sequence build commands available in this  cutterProgram
     * @return list size of sequence build commands
     */
    public int size() {
        return mBuildCommandMap.size();
    }

    /**
     * Get the sequence that was item with the id
     * @param key id of sequence
     * @return sequence mapped for id
     */
    public BuildCommand getBuildCommand(int key) {
        BuildCommand sequenceCommand = mBuildCommandMap.get(key);
        if(sequenceCommand==null)
            throw new RuntimeException("The sequence with sequenceId=" + key+ " does not exist");
        return sequenceCommand;
    }


    /************************************ define sequence *****************************************/
    
    public CutterProgram sequence(int resource, int key ){
        addCurrentSequence();
        mCurrentSequence = new BuildCommand(resource,key);
        return this;
    }


    /************************************ define Area *********************************************/
    public CutterProgram area() {
        mCurrentSequence.getCurrentClip().setArea(null);
        return this;
    }
    
    public CutterProgram area(int left, int top, int width, int height){
        return area(new Area(left,top,width,height));
    }
  
    private CutterProgram area(Area area){
        mCurrentSequence.getCurrentClip().setArea(area);
        grid();
        return this;
    }

    /******************************************* define grid *************************************/

    private CutterProgram grid(){
        mCurrentSequence.getCurrentClip().setGrid(null);
        mCurrentSequence.getCurrentClip().setCells(null);
        return this;
    }
    public CutterProgram grid(int columns, int rows, int cells){
        return grid(new Grid(columns,rows,cells));
    }
    private CutterProgram grid(Grid grid){
        mCurrentSequence.getCurrentClip().setGrid(grid);
        return this;
    }

    /******************************************** define clips **********************************/

    private CutterProgram cells(Cells cells) {
        mCurrentSequence.getCurrentClip().setCells(cells);
        return this;
    }

    public CutterProgram cells(int startCell, int endCell) {
        int[] cells = new int[endCell-startCell+1];
        for (int i = startCell,j=0; i <endCell+1 ; i++) {
            cells[j++]=i;
        }
        return cells(cells);
    }

    private CutterProgram cell(int startCell) {
        int[] cells = new int[1];
        cells[0]=startCell;
        return cells(cells);
    }


    public CutterProgram cells(int[] cells) {
        return cells(new Cells(cells));
    }

    public CutterProgram end(){
        addCurrentSequence();
        return this;
    }

    /******************************************** define clips **********************************/

    public CutterProgram clip(int key) {
        mCurrentSequence.addCurrentClip(key);
        return this;
    }


  
    /*

    @Deprecated
    public CutterProgram clip(Area area) {
        area(area);
        return clip();
    }

    @Deprecated
    public CutterProgram clip(Grid grid) {
        grid(grid);
        return clip();
    }*/




    private void addCurrentSequence() {
        if( mCurrentSequence ==null)
            return;

        mCurrentSequence.addCurrentClip();

        checkValidSequence();
        checkClipsSequence();
        mBuildCommandMap.put(mCurrentSequence.sequenceId,mCurrentSequence);
        mCurrentSequence.end();
    }
















    private void checkValidSequence() {
        if(mCurrentSequence==null)
            throw new RuntimeException("There are no any sequence");
    }

    private void checkClipsSequence() {
        if(mCurrentSequence.mListClips.size()==0){
            throw new RuntimeException("There sequence does not have any clips");
        }
    }

    public String toJson() {
        return JsonHelper.getJsonCutterProgramFrom(this);
    }

    public static class Cells {
        public final int index[];
        Cells(int[] cells) {
            this.index = cells;
        }
    }

    public static class Grid {
        final int columns;
        final int rows;
        final int cells;

        public Grid(int columns, int rows, int cells) {
            this.columns = columns;
            this.rows = rows;
            this.cells = cells;
        }
    }


    public static class Area {
        final int   left;
        final int   top;
        final float width;
        final float height;

        public Area(int left, int top, float width, float height) {
            this.left = left;
            this.top = top;
            this.width = width;
            this.height = height;
        }
    }


    /**
     * Basic Info for build a clip
     */
    static class ClipBuildCommand{
        private static final int TYPE_KNOWN     = 0;
        static final int TYPE_AREA      = 1;
        static final int TYPE_GRID      = 2;
        static final int TYPE_GRID_AREA = 3;

        Area  area;
        Grid  grid;
        Cells cells;
        
        int type;
        int id;

        private ClipBuildCommand() {
            this.area = null;
            this.grid = null;
            this.cells = null;
            this.type = TYPE_KNOWN;
        }

        boolean hasArea() {
            return area!=null;
        }
        boolean hasGrid() {
            return grid!=null;
        }
        boolean hasCells() {
            return cells!=null;
        }

        void setGrid(Grid grid) {
            this.grid = grid;
        }

        void setArea(Area area) {
            this.area = area;
        }

        void setCells(Cells cells) {
            this.cells = cells;
        }

        public void setId(int id) {
            this.id = id;
        }

        private void updateType(){
            ClipBuildCommand clip = this;
            if (!hasGrid() && hasArea()) {
                    type = TYPE_AREA;
            } else if (hasGrid() && !hasArea()) {
                    type = TYPE_GRID;
            } else if (clip.hasGrid() && clip.hasArea()) {
                    type = TYPE_GRID_AREA;
            }
        }
    }


    public static class BuildCommand implements JsonHelper.Serializable {
        final int resources;
        final int sequenceId;

        private final FlexibleList<ClipBuildCommand> mListClips;

        private ClipBuildCommand mCurrentBuildCommandClip;
        private ClipBuildCommand mLastCurrentBuildCommandClip;


        private BuildCommand(int resources, int sequenceId) {
            this.resources = resources;
            this.sequenceId = sequenceId;
            mListClips = new FlexibleList<>(1);
        }

        public FlexibleList<ClipBuildCommand> getListClips(){
            return mListClips;
        }

        ClipBuildCommand getCurrentClip(){
            return getCurrentClip(true);
        }

        ClipBuildCommand getCurrentClip(boolean hold) {
            if(mCurrentBuildCommandClip ==null) {
                mCurrentBuildCommandClip = new ClipBuildCommand();
                if(mLastCurrentBuildCommandClip !=null && hold) {
                    mCurrentBuildCommandClip.setArea(mLastCurrentBuildCommandClip.area);
                    mCurrentBuildCommandClip.setGrid(mLastCurrentBuildCommandClip.grid);
                    mCurrentBuildCommandClip.setCells(mLastCurrentBuildCommandClip.cells);
                }
            }
            return mCurrentBuildCommandClip;
        }

        private void addCurrentClip(int id) {
            getCurrentClip().setId(id);
            addCurrentClip();
        }

        private void addCurrentClip() {
            if(mCurrentBuildCommandClip ==null)
                return;
            checkValidClip();
            mCurrentBuildCommandClip.updateType();
            mListClips.add(mCurrentBuildCommandClip);
            mLastCurrentBuildCommandClip = mCurrentBuildCommandClip;
            mCurrentBuildCommandClip =null;
        }

        private void end(){
            mCurrentBuildCommandClip =null;
            mLastCurrentBuildCommandClip = null;
        }


        private void checkValidClip(){
            if(!mCurrentBuildCommandClip.hasCells()
                    && !mCurrentBuildCommandClip.hasGrid() && !mCurrentBuildCommandClip.hasArea()){
                throw new RuntimeException("The sequence does not have any: Area, grid, index");
            }else  if(mCurrentBuildCommandClip.hasCells()
                    && !mCurrentBuildCommandClip.hasGrid() && !mCurrentBuildCommandClip.hasArea()){
                throw new RuntimeException("The sequence does not have Area or grid");
            }else if(mCurrentBuildCommandClip.hasCells()
                    && !mCurrentBuildCommandClip.hasGrid() && mCurrentBuildCommandClip.hasArea()){
                throw new RuntimeException("The sequence does not have grid");
            }
        }

        @Override
        public String toJson() {
            return JsonHelper.getJsonFromBuildCommand(this);
        }

        public int getSequenceId() {
            return sequenceId;
        }
    }


    /*

    @Deprecated
    public Animator.Animation getAnimation(Cutter cutter, int sequenceId) {
        BuildCommand sequenceCommand = getBuildCommand(sequenceId);
        if(sequenceCommand==null)
            throw new RuntimeException("The sequence with sequenceId ="+ sequenceId+ " does not exist");
        return getAnimation(cutter,sequenceCommand);
    }

    @Deprecated
    private Animator.Animation getAnimation(Cutter cutter, BuildCommand sequenceCommand) {
        cutter.reset();
        cutter.setResources(sequenceCommand.resources);
        int size = sequenceCommand.getListClips().size();

        for (int i = 0; i < size; i++) {

            ClipMap clipCommand = sequenceCommand.getListClips().get(i);

            if (clipCommand.type == ClipMap.TYPE_CLIP_AREA) {
                Area area = clipCommand.area;
                cutter.cutClipFrom(area.left, area.top, area.width, area.height);
            } else if (clipCommand.type == ClipMap.TYPE_CLIP_GRID) {
                Grid grid = clipCommand.grid;
                cutter.makeGridGroup(grid.columns, grid.rows, grid.cells);
                if (clipCommand.hasCells())
                    cutter.cutClipFromGrid(clipCommand.cells.index);
                else
                    cutter.cutClipFromGrid(0, grid.cells - 1);

            } else if (clipCommand.type == ClipMap.TYPE_CLIP_GRID_CELLS) {
                Grid grid = clipCommand.grid;
                Area area = clipCommand.area;
                cutter.setViewport(area.width, area.height);
                cutter.setLeftTop(area.left, area.top);
                cutter.makeGridGroup(grid.columns, grid.rows, grid.cells);
                if (clipCommand.hasCells())
                    cutter.cutClipFromGrid(clipCommand.cells.index);
                else
                    cutter.cutClipFromGrid(0, grid.cells - 1);
            }else{
                throw new RuntimeException("clip build command known");
            }
        }

        return cutter.apply();
    }

*/

}
