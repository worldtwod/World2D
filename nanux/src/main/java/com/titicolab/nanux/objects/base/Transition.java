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

package com.titicolab.nanux.objects.base;


import com.titicolab.nanux.objects.map.MapItem;
import com.titicolab.nanux.objects.factory.RequestCollection;
import com.titicolab.nanux.objects.factory.RequestLayersBuilder;

/**
 * Created by campino on 12/06/2016.
 *
 */
public class Transition extends Scene {

    private Class<? extends BaseLayer> clazz;

    private static final int RED = 0;
    private static final int GREEN = 1;
    private static final int BLUE = 2;
    private static final int ALPHA = 3;

    private  float[] color;

    public float[] getColor() {
        return color;
    }


    public interface TransitionTrigger {
        void in(OnFullIn listener);
        void out(OnFullOut listener);
    }

    public  interface OnFullIn {
        void onFullIn();
    }

    public  interface OnFullOut {
        void onFullOut();
    }

    private TransitionTrigger mTransitionTrigger;



    public Transition(Class<? extends BaseLayer> clazz){
            super();
            if(!TransitionTrigger.class.isAssignableFrom(clazz))
                throw  new RuntimeException("The clazz must be a " +
                        "Layer that implements TransitionTrigger");

        this.clazz = clazz;
        color = new float[]{1,1,1,1};
    }

    public Transition(){
       this(TransitionLayer.Fade.class);
    }

    @Override
    protected RequestCollection.RequestList onLayersRequest(RequestLayersBuilder builder) {
        return builder
                .objects(new MapItem(clazz,1,null))
                .build();
    }

    @Override
    public void onGroupLayersCreated() {
        mTransitionTrigger = (TransitionTrigger) findLayer(1);

    }


    @Override
    protected void updateLogic() {
        super.updateLogic();
    }


    @Override
    public void updateRender() {
        super.updateRender();
    }

    public void in(OnFullIn listener){
        mTransitionTrigger.in(listener);
    }

    public void out(OnFullOut listener){
        mTransitionTrigger.out(listener);
    }


    public void setColor(float r, float g, float b, float a) {
        color[RED] = r;
        color[GREEN] = g;
        color[BLUE] = b;
        color[ALPHA] = a;
    }


}
