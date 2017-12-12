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


import com.titicolab.puppet.objects.base.GameObject;
import com.titicolab.puppet.objects.factory.Parameters;
import com.titicolab.puppet.objects.factory.RequestObject;

/**
 * Created by campino on 19/12/2016.
 *
 */

public class MapItem implements RequestObject {

    private final  Class  <? extends GameObject> clazz;
    private final  int        id;
    private final  Parameters params;

    public MapItem(Class<? extends GameObject> clazz, int id, Parameters params){
        this.clazz=clazz;
        this.id = id;
        this.params= params;
    }

    public Parameters getParameters() {
        return params;
    }

    public Class<? extends GameObject> getType() {
        return clazz;
    }

    public int getId() {
        return id;
    }
}
