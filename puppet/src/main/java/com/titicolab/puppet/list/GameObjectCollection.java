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

package com.titicolab.puppet.list;

import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.puppet.objects.base.GameObject;


/**
 * Created by campino on 07/06/2016.
 *
 */
public class GameObjectCollection extends FlexibleList<GameObjectList> {

     public GameObjectCollection(int capacity) {
            super(capacity);
        }


    /**
     * Return and object from cache object collection
     * @param type
     * @return
     */
    public GameObject getObject(Class<?> type){
        int typeIndex = findIndexOf(type);
        if(typeIndex<0)throw new
                IllegalArgumentException("Object not found,  the class " +type.getSimpleName() + "is unknown," +
                " check that you request to RequestObjectBuilder");

        if(get(typeIndex).getObjectsAvailable()<=0) {
            IllegalArgumentException e = new
                    IllegalArgumentException("There are not Objects with class " +type.getSimpleName() +
                    " available, all objects of this type were assigned");
            e.printStackTrace();
            throw e;
        }

        return get(typeIndex).getObject();
    }


    private int findIndexOf(Class<?> type){
        int typeIndex = -1;
        int size = size();
        for (int i = 0; i < size; i++) {

            if(get(i)==null  || get(i).get(0)==null)
                break;

            GameObject object = get(i).get(0);

            if(object.getClass()== type){
                typeIndex = i;
                break;
            }
        }
        return typeIndex;
    }


    public int getObjectsAvailable(Class<?> classType) {
        int typeIndex = findIndexOf(classType);
        if(typeIndex<0)throw new
                IllegalArgumentException("Object " +classType.getSimpleName() +
                " not found");
        return get(typeIndex).getObjectsAvailable();
    }

    int getCount(){
        int count=0;
        for (int i = 0; i < size(); i++) {
             count+=get(i).size();
        }
        return count;
    }


    public  <B> B findById(Class<B> clazz, int id) {
        int typeIndex = findIndexOf(clazz);
        if(typeIndex<0)throw new
                IllegalArgumentException("The object with class " +clazz.getSimpleName() +
                " not found");
        return (B) get(typeIndex).findById(id);
    }


    public void addNew(GameObject gameObject){
        int typeIndex = findIndexOf(gameObject.getClass());
        if(typeIndex<0){
            GameObjectList list= new GameObjectList(1);
            list.add(gameObject);
            add(list);
        }else{
            get(typeIndex).add(gameObject);
        }
    }


  /* TODO
    public void remove(LayerObject gameObject) {

        int typeIndex = findIndexOf(gameObject.getClass());
        if(typeIndex>=0){
            get(typeIndex).remove(gameObject);
            if(get(typeIndex).size()==0)
                remove(get(typeIndex));
        }

    }  */

}
