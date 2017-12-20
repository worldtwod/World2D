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

/**
 * Created by campino on 29/11/2017.
 *
 */

public class ClipMap {

    private static final int TYPE_KNOWN     = 0;
    public static final int TYPE_CLIP_AREA = 1;
    public static final int TYPE_CLIP_GRID = 2;


    private Area area;
    private Grid grid;
    private Cells cells;
    private int type;
    private int key;
    private int resources;

    private ClipMap() {
        this.area = new Area();
        this.grid = null;
        this.cells = null;
        this.type = TYPE_KNOWN;
    }

    public static Builder getBuilder(){
        return new Builder();
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
    void setKey(int key) {
        this.key = key;
    }
    void setType(int type) {
        this.type = type;
    }
    void setResources(int resources) {
        this.resources = resources;
    }

    public int  getType() {
        return type;
    }
    public Cells getCells() {
        return cells;
    }

    public Grid getGrid() {
        return grid;
    }

    public Area getArea() {
        return area;
    }

    public int getResources() {
        return resources;
    }

    public int getKey() {
        return key;
    }


    /************************* Inner classes *****************************************************/

    public static class Cells {
        public final int index[];

        public Cells(int[] cells) {
            this.index = cells;
        }

        Cells(Grid grid){
            this.index = new int[grid.columns*grid.rows];
            for(int cell=0; cell<index.length; cell++){
               index[cell]=cell;
            }
        }

        Cells(int start, int end) {
            index = new int[end-start+1];
            for (int i = start,j=0; i <end+1 ; i++) {
                index[j++]=i;
            }
        }
    }

    public static class Grid {
        public final int columns;
        public final int rows;
        //final int cells;

        public Grid(int columns, int rows) {
            this.columns = columns;
            this.rows = rows;
            //this.cells = cells;
        }
    }

    public static class Area {
        public final float   left;
        public final float   top;
        public final float width;
        public final float height;

        public Area(){
            width =0;
            top = 0;
            left = 0;
            height = 0;
        }
        public Area(float left, float top, float width, float height) {
            this.left = left;
            this.top = top;
            this.width = width;
            this.height = height;
        }
        public boolean isAreaFromParent(){
            return left==0 && top==0 && height==0 && width==0;
        }
    }



    /********************************** Builder  ************************************************/


    public static class Builder {

        private static final int STATUS_IDLE = 1;
        private static final int STATUS_AREA = 2;
        private static final int STATUS_GRID = 3;


        Area area;
        Grid grid;
        Cells cells;
        int resources;

        private int mStatus;
        private int key;

        Builder() {
            mStatus = STATUS_IDLE;
        }


        public Builder clip(int key,int resources) {
            this.resources=resources;
            if (mStatus != STATUS_IDLE)
                throw new RuntimeException("You must call to build() before to star new clip");
            this.key = key;
            area = new Area();
            mStatus = STATUS_AREA;
            return this;
        }

        public Builder clip(int key) {
            clip(key,-1);
            return this;
        }


        public Builder area(Area area) {
            if (mStatus != STATUS_AREA)
                throw new RuntimeException("You must call to clip() before to define a new area");
            this.area=area;
            mStatus = STATUS_AREA;
            return this;
        }

        public Builder grid(Grid grid) {
            if (mStatus != STATUS_AREA )
                throw new RuntimeException("You must call to clip() or area() before to define a new grid");
            this.grid = grid;
            this.cells = new Cells(grid);
            mStatus = STATUS_GRID;
            return this;
        }

        public Builder cells(Cells cells) {
            if (mStatus != STATUS_GRID)
                throw new RuntimeException("You must call to reset()");
            this.cells = cells;
            mStatus = STATUS_GRID;
            return this;
        }

        public ClipMap build() {
            if (mStatus == STATUS_IDLE)
                throw new RuntimeException("You must call to clip() before define new clip");

            ClipMap command = new ClipMap();
            command.setKey(key);
            command.setArea(area);
            command.setGrid(grid);
            command.setCells(cells);
            command.setType(compileType());
            command.setResources(resources);
            mStatus = STATUS_IDLE;
            return command;
        }


        int compileType(){
            int type=0;
            if (mStatus == STATUS_AREA) {
                type = TYPE_CLIP_AREA;
            } else if (mStatus == STATUS_GRID) {
                type = TYPE_CLIP_GRID;
            }else{
                throw  new RuntimeException("Error compiling the ClipMap, the type is known");
            }
            return type;
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

    }

}

