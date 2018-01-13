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

package com.titicolab.nanux.graphics.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


/**
 * Created by campino on 18/05/2016
 *
 */
public class ModelBuffer {

    private final FloatBuffer floatVertexBuffer;
    private final ShortBuffer shortIndexBuffer;
    private int counter;

    public ModelBuffer(int size, int bytesPerVertexModel, int bytesPerIndexModel) {

        floatVertexBuffer = ByteBuffer
                .allocateDirect(size* bytesPerVertexModel)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        shortIndexBuffer = ByteBuffer
                .allocateDirect(size* bytesPerIndexModel)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer();

        reset();
    }

    public void reset(){
        floatVertexBuffer.position(0);
        shortIndexBuffer.position(0);
        counter=0;
    }

    public void add(DrawModel model) {
        short arrayIndex[] = model.getIndex(counter++);
        float arrayModel[]=  model.getVertex();
        shortIndexBuffer.put(arrayIndex);
        floatVertexBuffer.put(arrayModel);
    }

    public FloatBuffer getVertexBuffer() {
        return floatVertexBuffer;
    }

    public ShortBuffer getIndexBuffer() {
        return shortIndexBuffer;
    }

    public int size() {
        return counter;
    }
}
