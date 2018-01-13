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

package com.titicolab.nanux.list;


import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.nanux.objects.base.GameObject;

/**
 * Created by campino on 07/06/2016.
 *
 */
public class GameObjectList extends FlexibleList<GameObject>  {
    private int mAvailable;


    public GameObjectList(int capacity) {
        super(capacity);
        mAvailable =capacity;
    }

    @Override
    protected void doResize(int resize) {
        super.doResize(resize);
        mAvailable+=resize;
    }

    public GameObject getObject(){
        if((mAvailable -1)<0)
            throw new IllegalArgumentException
                    ("There are not more instances of this gameObject: " +
                            getClass().getCanonicalName());

        return get(--mAvailable);
    }


    public GameObject findById(int id){
        GameObject results = null;
        for (int i = 0; i < size(); i++) {
            if(get(i).getId()==id){
                results = get(i);
                break;
            }
        }
    return results;
    }


    public int getObjectsAvailable() {
        return mAvailable;
    }


}
