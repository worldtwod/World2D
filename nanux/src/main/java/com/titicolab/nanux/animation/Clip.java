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

import com.titicolab.nanux.list.FixList;

/**
 * Created by campino on 19/05/2016.
 *
 */
public class Clip extends FixList<Frame> {

    private final GroupFrame mGroupFrame;
    private final int id;

    /**
     * Load the Clip with the sequence.
     * @param group group of frames
     * @param startCell start cell, inclusive
     * @param endCell  final cell, inclusive
     */
    Clip(GroupFrame group, int startCell, int endCell, int id) {
        super(group.size());
        if(endCell>=group.size())
            throw new IllegalArgumentException
                    ("Then max value for end cell is " + (group.size()-1));
        mGroupFrame = group;
        this.id = id;
        add(startCell,endCell);
    }

    Clip(GroupFrame group, int[] cells, int id) {
        super(group.size());
        this.id = id;
        if(cells.length>group.size())
            throw new IllegalArgumentException
                    ("Then max value to cells.length is " + (group.size()));
        mGroupFrame = group;
        for (int i = 0; i < cells.length; i++) {
            int cell = cells[i];
            if(cell>=group.size())
                throw new IllegalArgumentException
                        ("Then max value to cell in this group is " + (group.size()-1) + " but you are trying width " + cell);
            add(mGroupFrame.get(cell));
        }
    }

    Clip(GroupFrame groupFrame, int cell, int id) {
        super(1);
        if(cell>=groupFrame.size())
            throw new IllegalArgumentException
                    ("Then max value to cell is " + (groupFrame.size()-1));
        this.id = id;
        mGroupFrame = groupFrame;
        add(mGroupFrame.get(cell));
    }


    /**
     * Add a set of frames referenced.
     * @param startCell initial cell on GridFrame, inclusive
     * @param endCell   final cell on grid, inclusive
     */
    public void add(int startCell, int endCell){
        if(endCell>= mGroupFrame.size())
            throw new IllegalArgumentException
                    ("Then max value for end cell is" + (mGroupFrame.size()-1));

        for (int i = startCell; i <endCell+1 ; i++) {
            add(mGroupFrame.get(i));
        }
    }

    public void add(int cell){
        if(mGroupFrame ==null)
            throw new IllegalArgumentException
                    ("Cannot gameObject index because the mGroupFrame is null");

        add(mGroupFrame.get(cell));
    }


    public int getId() {
        return id;
    }


}
