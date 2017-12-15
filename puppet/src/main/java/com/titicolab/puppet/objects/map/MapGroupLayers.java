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
import com.titicolab.puppet.objects.base.BaseLayer;
import com.titicolab.puppet.objects.factory.Parameters;

/**
 * Created by campino on 22/03/2017.
 *
 */

public class MapGroupLayers extends MapObjects{






    public static  class Builder{



        private String name;
        private FlexibleList<MapItem> list;

        public   Builder(){
            list = new FlexibleList<>(10);
        }

        public String getName() {
            return name;
        }

        public FlexibleList<MapItem> getList() {
            return list;
        }

        public MapGroupLayers.Builder layer(Class<? extends BaseLayer> clazz, int id, Parameters params){
            list.add(new MapItem(clazz,id,params));
            return this;
        }

        public MapGroupLayers.Builder layer(Class<? extends BaseLayer> clazz, int id){
            return layer(clazz,id,null);
        }


        public MapGroupLayers.Builder setName(String name) {
            this.name = name;
            return this;
        }

        public MapGroupLayers build(){
            MapGroupLayers map = new MapGroupLayers();
            map.mName = name;
            map.mListMap = list;
            return map;
        }
    }

}
