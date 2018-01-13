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

package com.campino.components.excomponent;


import com.campino.components.R;
import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.nanux.animation.ClipMap;
import com.titicolab.puppet.map.MapLayer;
import com.titicolab.puppet.objects.Tile;
import com.titicolab.puppet.objects.TiledLayer;

/**
 * Created by campino on 11/01/2018.
 *
 */

public class ExTiledLayer extends TiledLayer {


    private static final int MAX_TILES_X = 40;


    @Override
    public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
        ClipMap.Area area = new ClipMap.Area(0,0,84*5,84*3);
        return   builder
                .sequence("tile")
                .resources(R.drawable.test_tileset)
                .clip(0).area(area).grid(5,3).cells(0)
                .clip(1).area(area).grid(5,3).cells(1)
                .clip(2).area(area).grid(5,3).cells(2)
                .clip(3).area(area).grid(5,3).cells(3)
                .clip(4).area(area).grid(5,3).cells(4)
                .clip(5).area(area).grid(5,3).cells(5)
                .clip(6).area(area).grid(5,3).cells(6)
                .clip(7).area(area).grid(5,3).cells(7)
                .clip(8).area(area).grid(5,3).cells(8)
                .clip(9).area(area).grid(5,3).cells(9)
                .clip(10).area(area).grid(5,3).cells(10)
                .clip(11).area(area).grid(5,3).cells(11)
                .clip(12).area(area).grid(5,3).cells(12)
                .clip(13).area(area).grid(5,3).cells(13)
                .clip(14).area(area).grid(5,3).cells(14)
                .build();
    }


    @Override
    protected MapLayer onDefineMapObjects() {
        MapLayer.Builder builder = new MapLayer.Builder()
                .setName("MapLayer")
                .setOffset(0, 0)
                .setGridSize(MAX_TILES_X, 5)
                .setTileSize(84, 84);
        int i=0;
        int id=1000;
        builder.item(Tile.class, id++,i,1,"tile",1)
                .item(Tile.class, id++,i,0,"tile",6);

        for(i++; i<MAX_TILES_X-1; i++ ){
            builder.item(Tile.class, id++,i,1,"tile",2)
                    .item(Tile.class, id++,i,0,"tile",7);
        }

        builder.item(Tile.class, id++,i,1,"tile",3)
                .item(Tile.class, id,i,0,"tile",8);

        return builder.build();
    }



}
