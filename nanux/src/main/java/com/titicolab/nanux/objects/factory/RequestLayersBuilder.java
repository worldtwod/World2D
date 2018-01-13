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

package com.titicolab.nanux.objects.factory;

import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.nanux.objects.map.MapItem;

/**
 * Created by campino on 07/06/2016.
 *
 */
public class RequestLayersBuilder {

    private FlexibleList<MapItem>  mList;

    public RequestLayersBuilder() {
        mList = new FlexibleList<>(1);
    }

    public RequestLayersBuilder object(FlexibleList<MapItem> list){
        int size = list.size();
        for (int i = 0; i <size; i++) {
            objects(list.get(i));
        }
        return this;
    }

    public RequestLayersBuilder objects(MapItem item){
        mList.add(item);
        return this;
    }

    public RequestCollection.RequestList build() {
        checkIds(mList);
        RequestCollection.RequestList requestList = new RequestCollection.RequestList(mList.size());
        int size = mList.size();
        for (int i = 0; i <size; i++){
            MapItem item = mList.get(i);
            requestList.add(item);
        }
     return requestList;
    }



    private void checkIds(FlexibleList<MapItem> list){
        int size = list.size();
        for (int i = 0; i <size; i++) {
            MapItem item = list.get(i);
            if(checkEquals(list,i+1,item)){
                throw new RuntimeException("There is a layer with the same id: "
                        + item.getType() + " " + item.getId());
            }
        }
    }

    private boolean checkEquals(FlexibleList<MapItem> list, int start, MapItem testItem) {
        boolean result = false;
        int size = list.size();
        for (int i = start; i <size; i++) {
            MapItem item = list.get(i);
            if(item.getId()==testItem.getId()){
                result=true;
                break;
            }
        }
        return result;
    }
}
