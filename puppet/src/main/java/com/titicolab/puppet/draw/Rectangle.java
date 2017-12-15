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


import com.titicolab.puppet.model.GeometryModel;

/**
 * Created by campino on 20/06/2016.
 *
 */
public class Rectangle extends Geometry {

    public Rectangle(float width, float height) {
        super(new GeometryModel(1));
        this.width =(int)width;
        this.height =(int)height;
    }
    public Rectangle(float width, float height, float scalePixel) {
        super(new GeometryModel(scalePixel));
        this.width =(int)width;
        this.height =(int)height;
    }

}
