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

public class AnimationMap {

    private String    mKey;
    private int       mResources;
    private FlexibleList<ClipMap> mListMap;

    AnimationMap() {

    }

    public static Builder getBuilder() {
        return new Builder();
    }

    void setKey(String key) {
        this.mKey = key;
    }

    void setResources(int resources) {
        this.mResources = resources;
    }
    void setListMap(FlexibleList<ClipMap> mListMap) {
        this.mListMap = mListMap;
    }
    public String getKey() {
        return mKey;
    }
    public int getResources() {
        return mResources;
    }
    public int size() {
        return mListMap.size();
    }
    public FlexibleList<ClipMap> getListClipMaps() {
        return mListMap;
    }

    /**************************************** Builder ********************************************/
    public static class Builder{
        private static final int STATUS_IDLE = 1;
        private static final int STATUS_WAITING_RESOURCES = 2;
        private static final int STATUS_WAITING_START_CLIP = 3;
        private static final int STATUS_WAITING_ADD_CLIP = 4;



        private String key;
        private int resources;
        private ClipMap.Builder clipBuilder;
        private FlexibleList<ClipMap> listMap;
        private int mStatus;


        Builder() {
            mStatus = STATUS_IDLE;
        }

        public Builder sequence(String key){
            if(mStatus!=STATUS_IDLE){
                throw new RuntimeException("You must call to build() before start a new sequence");
            }
            this.key = key;
            listMap = new FlexibleList<>(10);
            mStatus=STATUS_WAITING_RESOURCES;
            return this;
        }

        public Builder resources(int resources){
            if(mStatus!=STATUS_WAITING_RESOURCES
                    && mStatus!= STATUS_WAITING_ADD_CLIP
                     && mStatus != STATUS_WAITING_START_CLIP){
                throw new RuntimeException("You can not change the resources in this stage");
            }

            if(mStatus== STATUS_WAITING_ADD_CLIP) {
                listMap.add(clipBuilder.build());
            }

            //start builder
            this.resources = resources;
            mStatus= STATUS_WAITING_START_CLIP;
            return this;
        }


        public Builder clip(int key ){
            if(mStatus!= STATUS_WAITING_START_CLIP && mStatus!= STATUS_WAITING_ADD_CLIP) {
                throw new RuntimeException("You must call to setResources() before start a new clip");
            }
            if(mStatus== STATUS_WAITING_ADD_CLIP) {
                listMap.add(clipBuilder.build());
            }

            clipBuilder = ClipMap.getBuilder();
            clipBuilder.clip(key,resources);
            mStatus= STATUS_WAITING_ADD_CLIP;
            return this;
        }




        public Builder area(ClipMap.Area area){
            if(mStatus!= STATUS_WAITING_ADD_CLIP) {
                throw new RuntimeException("You must call to clip(mKey) before item area");
            }
            clipBuilder.area(area);
            return this;
        }


        public Builder grid(ClipMap.Grid grid){
            if(mStatus!= STATUS_WAITING_ADD_CLIP) {
                throw new RuntimeException("You must call to clip(mKey) before item grid");
            }
            clipBuilder.grid(grid);
            return this;
        }

        public Builder cells(ClipMap.Cells cells){
            if(mStatus!= STATUS_WAITING_ADD_CLIP) {
                throw new RuntimeException("You must call to clip(mKey) before item cells");
            }
            clipBuilder.cells(cells);
            return this;
        }


        public AnimationMap build() {

            if(mStatus== STATUS_WAITING_ADD_CLIP) {
                listMap.add(clipBuilder.build());
            }

            if(key==null || key.isEmpty()){
                throw new RuntimeException("The sequence must have a mKey, use sequence(mKey)");
            }else if(listMap.size()==0){
                throw new RuntimeException("There sequence does not have any clips, key: " + key);
            }else if(resources==0){
                throw new RuntimeException("The sequence does not have any resources, key " + key);
            }

            AnimationMap animationMap = new AnimationMap();
            animationMap.setKey(key);
            animationMap.setResources(resources);
            animationMap.setListMap(listMap);
            mStatus=STATUS_IDLE;
            return animationMap;
        }
    }





}
