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

/**
 * Created by campino on 29/11/2017.
 *
 */

public class AnimationSheet {

    private FlexibleList<AnimationMap> mListAnimationMap;

    
    public interface   DefineAnimations {
        AnimationSheet onDefineAnimations(Builder builder);
    }


    AnimationSheet() {

    }

    public static Builder getBuilder() {
        return new Builder();
    }

    void setListSequence(FlexibleList<AnimationMap> listSequence) {
        this.mListAnimationMap = listSequence;
    }

    public FlexibleList<AnimationMap> getListAnimationMaps() {
        return mListAnimationMap;
    }

    public int size() {
        return mListAnimationMap.size();
    }

    AnimationMap findByKey(String key) {
        int size = mListAnimationMap.size();
        AnimationMap results = null;
        for (int i = 0; i <size; i++) {
            AnimationMap animationMap = mListAnimationMap.get(i);
            if(animationMap.getKey().equals(key)){
                results = animationMap;
                break;
            }
        }
     return results;
    }


    /********************************************* Builder ****************************************/

    public static class Builder{

        private static final int STATUS_IDLE = 1;
        private static final int STATUS_WAITING_START_RESOURCES = 3;
        private static final int STATUS_WAITING_ADD_SEQUENCE   = 4;

        private  FlexibleList<AnimationMap>   listSequenceMap;
        private  AnimationMap.Builder animationBuilder;
        private  int resources;

        private int mStatus;

        public Builder() {
            mStatus=STATUS_IDLE;
            listSequenceMap = new FlexibleList<>(1);
        }

        /******************************************************************************************
         * Set the reset of a sequence
         * @param key  key identification of that sequence
         * @return The builder
         */
        public Builder sequence(String key) {
            if(mStatus!= STATUS_IDLE && mStatus!=STATUS_WAITING_ADD_SEQUENCE)
                throw new RuntimeException("You must call to build() before reset a new sequence");

            if(mStatus==STATUS_WAITING_ADD_SEQUENCE){
                listSequenceMap.add(animationBuilder.build());
                animationBuilder = AnimationMap.getBuilder();
                animationBuilder.sequence(key);
                animationBuilder.resources(resources);
                mStatus = STATUS_WAITING_ADD_SEQUENCE;
            }else{
                animationBuilder = AnimationMap.getBuilder();
                animationBuilder.sequence(key);
                mStatus = STATUS_WAITING_START_RESOURCES;
            }

            return this;
        }

        /******************************************************************************************
         * Set the resources that attach to clips
         * @param resources resources
         * @return The Builder
         */
        public Builder resources(int resources){
            if(mStatus!= STATUS_WAITING_START_RESOURCES && mStatus!=STATUS_WAITING_ADD_SEQUENCE)
                throw new RuntimeException("You must call to build() before reset a new sequence");

            this.resources =resources;
            animationBuilder.resources(resources);
            mStatus = STATUS_WAITING_ADD_SEQUENCE;
            return this;
        }

        /******************************************************************************************
         * Start the clip definition,
         */
        public Builder clip(int key){
            if(mStatus!=STATUS_WAITING_ADD_SEQUENCE)
                throw new RuntimeException("The sequence must have resources, use resources(rsc)");
            animationBuilder.clip(key);
            return this;
        }

        /******************************************************************************************
         * Start the area definition,
         */
        public Builder area(ClipMap.Area area){
            if(mStatus!=STATUS_WAITING_ADD_SEQUENCE)
                throw new RuntimeException("The sequence must have resources, use resources(rsc)");
            animationBuilder.area(area);
            return this;
        }
        public Builder area(float left, float top, float width, float height){
            return area(new ClipMap.Area(left,top,width,height));
        }
        /******************************************************************************************
         * Start the grid definitions.
         */
        public Builder grid(ClipMap.Grid grid){
            if(mStatus!=STATUS_WAITING_ADD_SEQUENCE)
                throw new RuntimeException("The sequence must have resources, use resources(rsc)");

            animationBuilder.grid(grid);
            return this;
        }
        public Builder grid(int columns, int rows) {
            return grid(new ClipMap.Grid(columns,rows));
        }
        /******************************************************************************************
         * Start the cell definitions.
         */
        public Builder cells(ClipMap.Cells cells){
            if(mStatus!=STATUS_WAITING_ADD_SEQUENCE)
                throw new RuntimeException("The sequence must have resources, use resources(rsc)");

            animationBuilder.cells(cells);
            return this;
        }

        public Builder cells(int[] cells) {
            return cells(new ClipMap.Cells(cells));
        }

        public Builder cells(int cell) {
            return cells(new ClipMap.Cells(new int[]{cell}));
        }

        public Builder cells(int start, int end) {
            return cells(new ClipMap.Cells(start,end));
        }



        /*******************************************************************************************
         * Build the AnimationSheet
         * @return AnimationSheet with a list of sequences
         */
        public AnimationSheet build(){

            listSequenceMap.add(animationBuilder.build());

            if(listSequenceMap.size()==0){
                throw new RuntimeException("There are not any animation");
            }else if(mStatus!=STATUS_WAITING_ADD_SEQUENCE){
                throw new RuntimeException("The sequence must have resources, use resources(rsc)");
            }

            AnimationSheet animationSheet = new AnimationSheet();
            animationSheet.setListSequence(listSequenceMap);
            mStatus = STATUS_IDLE;
            return  animationSheet;
        }

    }
}
