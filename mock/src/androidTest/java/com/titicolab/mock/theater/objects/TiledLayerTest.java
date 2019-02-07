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
package com.titicolab.mock.theater.objects;
import com.titicolab.mock.rule.SceneTestRule;
import com.titicolab.mock.tools.TestMap;
import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.nanux.objects.base.BaseLayer;
import com.titicolab.nanux.objects.factory.RequestObject;
import com.titicolab.puppet.map.MapLayer;
import com.titicolab.puppet.map.MapWorld;
import com.titicolab.puppet.objects.TiledLayer;
import com.titicolab.puppet.objects.World2D;

import org.junit.Rule;
import org.junit.Test;
/**
 * Created by campino on 24/01/2017.
 *
 */
@Deprecated
public class TiledLayerTest {
    @Rule
    public SceneTestRule sceneTestRule = new SceneTestRule();

    @Test
    public void mapLayer(){
        World world = new World();
        sceneTestRule.syncPlay(world);
        world.setDrawBoundary(true);
        world.setDrawCamera(true);

        for (int i =0; i <40; i++) {
            showInfo();
            moveCamera(i,3);
            waitTouchSeconds(1);
        }

        waitTouchSeconds(60*60);
    }

    private void waitTouchSeconds(int i) {
        //todo
    }

    void moveCamera(int i,int j){
        //((World)getWorld2D()).layer.sprite.setPositionIj(i,j);
       // todo getWorld2D().getCamera2D().setPositionIj(i,j);
    }

    void showInfo(){
        //todo
        /*log.debug("Camera i: " + (int)(getWorld2D().getCamera2D().getX()/128));
        log.debug("Camera j: " + (int)(getWorld2D().getCamera2D().getY()/128));
        log.debug("Camera width: " + getWorld2D().getCamera2D().getViewPortWidth()/128);
        log.debug("Camera height: " + getWorld2D().getCamera2D().getViewPortHeight()/128);*/
    }

    public static class  World extends World2D{
        MockTiledLayer layer;
        @Override
        protected MapWorld onDefineMapWorld(MapWorld.Builder builder) {
            return builder
                    .setName("World")
                    .setGridSize(40, 10)
                    .setTileSize(84, 84)
                    .setCameraSize(13,false)
                    .setFocusedWindowSize(15,false)
                    .layer(MockTiledLayer.class,1,null)
                    .build();
        }

        @Override
        protected void onGroupLayersCreated() {
            layer = (MockTiledLayer) findLayer(1);
        }
    }

    public static class MockTiledLayer extends TiledLayer {

        @Override
        public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
            return TestMap.getTileSet(builder);
        }

        @Override
        protected void onAttachParameters(RequestObject request) {
            super.onAttachParameters(request);
        }

        @Override
        protected MapLayer onDefineMapObjects() {
            return TestMap.getMockMapLayer();
        }

        @Override
        protected void onGroupObjectsCreated() {

        }
    }

}
