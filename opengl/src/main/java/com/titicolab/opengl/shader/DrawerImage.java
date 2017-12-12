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


import com.titicolab.nanux.graphics.drawer.Drawer;
import com.titicolab.puppet.draw.Image;
import com.titicolab.puppet.model.ImageModel;
import com.titicolab.puppet.model.ModelBuffer;

/**
 * Created by campino on 18/05/2016.
 *
 */
public class DrawerImage  extends BaseDrawer<Image> implements Drawer<Image> {



    public DrawerImage(int size, ImageShaderProgram program) {
        super(size,program);
        mModelBuffer = new ModelBuffer(size,
                ImageModel.bytesPerVertexModel,
                ImageModel.bytesPerIndexModel);
    }

    @Override
    public void add(Image image){
        if(size()>=capacity())
            throw new IllegalArgumentException
                    ("Cannot gameObject more GLImages per DrawerImage, current limit:" + capacity());
        if(!isEmpty() && (getCurrent().getTextureId()!=image.getTextureId())){
            draw();
        }
        super.add(image);
        mModelBuffer.add(image.getDrawModel());
    }

    @Override
    protected void draw(){
        if(isEmpty())return;
        getShaderProgram().binTexture(getCurrent().getTextureId());
        getShaderProgram().binAttributes(mModelBuffer.getVertexBuffer());
        getShaderProgram().draw(mModelBuffer.getIndexBuffer(), mModelBuffer.size());
        reset();
        mModelBuffer.reset();
    }


}
