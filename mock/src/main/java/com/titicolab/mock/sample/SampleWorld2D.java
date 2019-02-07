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

package com.titicolab.mock.sample;

import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.puppet.map.MapWorld;
import com.titicolab.puppet.objects.World2D;

/**
 * Created by campino on 11/01/2018.
 *
 */

public class SampleWorld2D extends World2D {

    private boolean flagTouchRight;
    private boolean flagTouchLeft;
    private int positionI;
    private int positionJ;

    @Override
    protected MapWorld onDefineMapWorld(MapWorld.Builder builder) {
        return builder
                .setName("World")
                .setGridSize(40, 10)
                .setTileSize(84, 84)
                .setCameraSize(5,true)
                .setFocusedWindowSize(9,true)
                .layer(SampleTiledLayer.class,1,null)
                .build();
    }


    @Override
    protected void onGroupLayersCreated() {
        super.onGroupLayersCreated();
        positionI=5; positionJ=2;
    }

    @Override
    protected void updateLogic() {
        if(flagTouchLeft){
            getCamera2D().setPositionIj(--positionI,positionJ);
            flagTouchLeft=false;
        }else  if(flagTouchRight){
            getCamera2D().setPositionIj(++positionI,positionJ);
            flagTouchRight=false;
        }
      super.updateLogic();
    }

    @Override
    protected boolean onTouch(ObservableInput.Event input) {
        if(input.isActionUp()) {
            if (input.getUiX() < (getCameraUi().getViewPortWidth() / 2)) {
                flagTouchLeft = true;
            } else {
                flagTouchRight = true;
            }
        }
        return true;
    }
}
