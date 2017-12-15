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

package com.titicolab.puppet.draw;


import com.titicolab.puppet.model.DrawModel;
import com.titicolab.puppet.model.DrawableObject;

/**
 * Created by campino on 20/06/2016.
 *
 */
public class Geometry extends DrawableObject {

    public Geometry(DrawModel drawModel) {
        super(drawModel);
        color = new float[4];
    }

    public void setColor(float r, float g, float b, float a){
        color[0]=r;
        color[1]=g;
        color[2]=b;
        color[3]=a;
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

}
