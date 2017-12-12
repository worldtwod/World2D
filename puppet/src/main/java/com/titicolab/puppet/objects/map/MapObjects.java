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

package com.titicolab.puppet.objects.map;


import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.puppet.json.JsonHelper;
import com.titicolab.puppet.objects.base.Animated;

/**
 * Created by campino on 11/01/2017.
 *
 */

public class MapObjects implements JsonHelper.Serializable {

    FlexibleList<MapItem> mListMap;
    String mName;

    MapObjects() {
    }

    @Override
    public String toJson() {
        return null;
    }

    public String getName() {
        return mName;
    }

    public FlexibleList<MapItem> getList() {
        return mListMap;
    }



    public static  class Builder{

        private  String name;
        private  FlexibleList<MapItem> list;

        public   Builder(){
            list = new FlexibleList<>(10);
        }

        public Builder item(Class<? extends Animated> clazz,int id, Animated.ParamsAnimation params){
            list.add(new MapItem(clazz,id,params));
            return this;
        }

        public Builder item(MapItem item){
            list.add(item);
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public MapObjects build(){
            MapObjects map = new MapObjects();
            map.mName = name;
            map.mListMap = list;
            return map;
        }
    }



}
