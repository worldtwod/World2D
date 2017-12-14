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

package com.titicolab.puppet.world.collision;


import com.titicolab.puppet.draw.Rectangle;
import com.titicolab.puppet.world.objects.LayerObject;

/**
 * Created by campino on 16/01/2017.
 *
 */

public class CollisionArea{

    private  int id;
    private Rectangle draw;
    private LayerObject parent;
    private int width;
    private int height;
    private float offsetX;
    private float offsetY;



    CollisionArea(LayerObject layerObject) {
        this.width = (int) layerObject.getWidth();
        this.height = (int) layerObject.getHeight();
        this.parent = layerObject;
        draw = new Rectangle(width,height);
        draw.setPosition(parent.getX(),parent.getY());
        offsetX = 0;
        offsetY = 0;
    }



    public void setRelativeMargin(float left, float top, float right, float bottom) {
        float parentW= parent.getWidth();
        float parentH= parent.getHeight();
        width = (int) (parentW -  parentW*left - parentW*right);
        offsetX =parentW/2- parentW*right - width/2 ;

        height = (int) (parentH -  parentH*top - parentH*bottom);
        offsetY =parentH/2 - parentH*top - height/2 ;
    }

    public void updateRender(){
        draw.setSize(width,height);
        draw.setPosition(getX(),getY());
        draw.updateRender();
    }

    /********************************* look for collision ****************************************/

    boolean checkBoxCollision(CollisionArea boxB){
        boolean r = false;
        float xMin = getX()-width/2 - boxB.width/2;
        float xMax = getX()+width/2 + boxB.width/2;
        float yMin = getY()-height/2 - boxB.height/2;
        float yMax = getY()+ height/2 + boxB.height/2;
        float x = boxB.getX();
        float y = boxB.getY();

        if((xMin < x && x < xMax) &&
                (yMin < y && y < yMax)){
            r=true;
        }
        return r;
    }



    /************************************* Getters and setters ************************************/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getX() {
        return parent.getX() + offsetX;
    }

    public float getY() {
        return parent.getY() + offsetY;
    }

    public void setColor(int r, int g, int b, int a) {
        draw.setColor(r,g,b,a);
    }

    private float getOffsetY() {
        return offsetY;
    }

    private float getOffsetX() {
        return offsetX;
    }

    public Rectangle getDrawable() {
        return draw;
    }

}
