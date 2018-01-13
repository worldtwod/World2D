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
import com.titicolab.nanux.animation.GridFrame;
import com.titicolab.nanux.graphics.draw.Image;

/**
 * Created by campino on 26/05/2016.
 *
 */
public class DrawerText extends DrawerImage implements Drawer.Text<Image> {

    public static final int FONT_CELLS = 256;
    public static final int FONT_ROWS = 16;
    public static final int FONT_COLUMNS = 16;
    public static final float FONT_WIDTH_SPACE = -0.3f;
    private final Image[]    mLetters;


    private final float  cellSize;

    private float left;
    private float top;
    private float size;


    private float wSpace=FONT_WIDTH_SPACE;


    private float[] matrix;


    public DrawerText(GridFrame gridFrame, ImageShaderProgram program) {
        super(gridFrame.size(), program);
        mLetters = new Image[gridFrame.size()];
        for (int i = 0; i < mLetters.length; i++) {
            mLetters[i] = new Image(gridFrame.get(i));
        }
        cellSize = gridFrame.getTexture().getWidth()/ gridFrame.getColumns();
    }

    /*public void begin(float left, float top, float size, float width){
        setStyle(left,top,size,width);
        begin(matrix);
    }*/



    @Override
    public void print(String text, float left, float top, float size){
        setStyle(left,top,size,wSpace);
        begin(matrix);
        draw(text);
        end();
    }

    @Override
    public void print(String text){
        if(getStatus()!=STATUS_BEGIN)
            throw  new IllegalStateException("It need first setClip to STATUS_BEGIN");
        draw(text);
    }

    @Override
    public void setStyle(float left, float top, float size, float widthSpace) {
        this.left = left;
        this.top = top;
        this.size = size;
        this.wSpace = widthSpace;
    }


    @Override
    public void end() {
        super.end();
    }







    private void draw(String text){

        int length = text.length();
        float hSpace = 0f;
        float height = size;
        float x = left + size/2;
        float xDelta = (size*wSpace)+size;
        float scale = size/cellSize;
        float y = top-height/2;
        float yDelta = (height*hSpace)+height;

        for (int i = 0; i < length; i++) {
            char ch = text.charAt(i);

            if(ch=='\n') {
                y -= yDelta;
                x= left + size/2;
                continue;
            }
            mLetters[ch].setPosition(x,y);
            mLetters[ch].setScale(scale);
            mLetters[ch].updateRender();

            add(mLetters[ch]);

            x+=xDelta;
            //y = top-height/2;
        }

        left = x -size/2;
        top  = y + height/2;
    }

    public void setMatrix(float[] matrix) {
        this.matrix = matrix;
    }
}
