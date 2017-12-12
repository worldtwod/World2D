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
 * Created by campino on 30/11/2017.
 *
 */

public class Animation extends FlexibleList<Clip> {

    private String key;

    public Animation(int size) {
        super(size);
    }

    public Animation() {
        super(1);
    }

    public int indexOf(int id){
        int size = size(), indexClip = -1;
        for (int i = 0; i < size; i++) {
            if(get(i).getId()==id) {
                indexClip = i;
                break;
            }
        }
        return indexClip;
    }

    public Clip findClipById(int id){
        int indexClip = indexOf(id);
        return indexClip<0? null: get(indexClip);
    }

    void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
