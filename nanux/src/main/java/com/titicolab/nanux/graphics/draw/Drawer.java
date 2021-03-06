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

package com.titicolab.nanux.graphics.draw;


/**
 * Created by campino on 18/05/2016.
 *
 */
public interface Drawer<T>{
    void begin(float[] matrix);
    void add(T item);
    void end();


    interface Brush<T> extends Drawer<T> {
        void setColor(float r, float g, float b, float a);
        void setBrushSize(float lineSize);
    }

    interface Text<T> extends Drawer<T> {
        void setMatrix(float[] matrix);
        void print(String text, float left, float top, float size);
        void print(String text);
        void setStyle(float left, float top, float size, float widthSpace);
    }
}
