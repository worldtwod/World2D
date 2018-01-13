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

package com.titicolab.opengl.shader;


import com.titicolab.nanux.graphics.draw.Drawer;
import com.titicolab.nanux.list.FixList;
import com.titicolab.nanux.graphics.model.ModelBuffer;

/**
 * Created by campino on 18/05/2016.
 *
 */
public abstract class BaseDrawer<T> extends FixList<T>  implements Drawer<T> {

    static final int STATUS_BEGIN = 1;
    static final int   STATUS_END = 0;

    final private ShaderProgram mProgram;

    ModelBuffer mModelBuffer;

    int mStatus;



    private int drawOperation;

    BaseDrawer(int size, ShaderProgram program) {
        super(size);
        if(program.getProgramId()==0)
            throw new RuntimeException("ShaderProgram needs be compiled, use ShaderProgram.buildProgram()");
        this.mProgram = program;
    }



    int getStatus() {
        return mStatus;
    }



    @Override
    public void begin(float[] matrix){
        if(mStatus==STATUS_BEGIN)
            throw  new IllegalStateException("The drawer already is STATUS_BEGIN");
        mStatus =STATUS_BEGIN;
        mProgram.use();
        mProgram.binMatrix(matrix);
        reset();
        resetDrawOperations();
    }


    protected abstract void draw();

    @Override
    public void end() {
        if(getStatus()!=STATUS_BEGIN)
            throw  new IllegalStateException("It need first setClip to STATUS_BEGIN");
        if(isEmpty()){
            mStatus =STATUS_END;
            return;
        }
        draw();
        mStatus =STATUS_END;
    }

    @Override
    public void add(T item) {
        super.add(item);
    }

    protected ShaderProgram getShaderProgram(){
        return mProgram;
    }


    public int getDrawOperation() {
        return drawOperation;
    }

    protected void resetDrawOperations() {
        drawOperation=0;
    }

    protected void addDrawOperation(){
        drawOperation++;
    }
}
