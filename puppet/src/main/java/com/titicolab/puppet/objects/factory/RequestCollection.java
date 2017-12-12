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

package com.titicolab.puppet.objects.factory;

import com.titicolab.nanux.list.FlexibleList;


/**
 * Created by campino on 13/02/2017.
 *
 */

public class RequestCollection {

    private FlexibleList<RequestList> mList;

    RequestCollection() {
        mList = new FlexibleList<>(1);
    }

    void request(RequestObject book) {
        int index = findIndexOf(book.getType());
        if(index<0){
            RequestList listBook = new RequestList(1);
            listBook.add(book);
            mList.add(listBook);
        }else{
            mList.get(index).add(book);
        }
    }


    private int findIndexOf(Class<?> type){
        int typeIndex = -1;
        int size = mList.size();
        for (int i = 0; i < size; i++) {

            if(mList.get(i)==null  || mList.get(i).get(0)==null)
                break;
            RequestObject book = mList.get(i).get(0);
            if(book.getType() == type){
                typeIndex = i;
                break;
            }
        }
        return typeIndex;
    }

    public int size(){
        return mList.size();
    }

    public RequestList get(int index){
        return mList.get(index);
    }

    public static class RequestList extends FlexibleList<RequestObject> {
        RequestList(int size) {
            super(size);
        }
    }
}
