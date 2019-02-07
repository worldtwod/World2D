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

package com.titicolab.mock.tools;

import com.titicolab.mock.R;
import com.titicolab.nanux.animation.Animation;
import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.nanux.animation.ClipMap;
import com.titicolab.puppet.map.MapLayer;
import com.titicolab.puppet.objects.Tile;

/**
 * Created by campino on 18/12/2017.
 *
 */

public class TestMap {

    public static AnimationSheet getTileSet(AnimationSheet.Builder builder) {
        ClipMap.Area area = new ClipMap.Area(0,0,84*5,84*3);
        return builder.
                sequence("tile")
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


    public static AnimationSheet getNumbersTileSet(AnimationSheet.Builder builder) {
        builder.sequence("numbers")
                .resources(R.drawable.test_grid);
        for (int i = 0; i < 40; i++)
                builder.clip(i).grid(8,5).cells(i);
        return builder.build();
    }


   public static MapLayer getMockMapLayer() {

        MapLayer.Builder builder = new MapLayer.Builder()
                .setName("MapLayer")
                .setOffset(0, 0)
                .setGridSize(40, 5)
                .setTileSize(84, 84);

        int i=0;
        int id=1000;
        int ii=30;

        builder.item(MockTile.class, id++,ii-1,3,"tile",1);
        builder.item(MockTile.class, id++,ii-1,2,"tile",6);

        builder.item(MockTile.class, id++,ii,4,"tile",1)
                .item(MockTile.class, id++,ii,3,"tile",11)
                .item(MockTile.class, id++,ii,2,"tile",7);

        builder.item(MockTile.class, id++,ii+1,4,"tile",3)
                .item(MockTile.class, id++,ii+1,3,"tile",13)
                .item(MockTile.class, id++,ii+1,2,"tile",7);

        builder.item(MockTile.class, id++,ii+2,3,"tile",2);
        builder.item(MockTile.class, id++,ii+2,2,"tile",7);

        builder.item(MockTile.class, id++,ii+3,3,"tile",3);
        builder.item(MockTile.class, id++,ii+3,2,"tile",8);

        builder.item(MockTile.class, id++,i,1,"tile",1)
                .item(MockTile.class, id++,i,0,"tile",6);
        for(i++; i<18; i++ ){
            builder.item(MockTile.class, id++,i,1,"tile",2)
                    .item(MockTile.class, id++,i,0,"tile",7);
        }

        builder.item(MockTile.class, id++,i,1,"tile",3)
                .item(MockTile.class, id++,i,0,"tile",8);

        i+=3;

        builder.item(MockTile.class, id++,i,1,"tile",1)
                .item(MockTile.class, id++,i,0,"tile",6);
        for(i++; i<39; i++ ){
            builder.item(MockTile.class, id++,i,1,"tile",2)
                    .item(MockTile.class, id++,i,0,"tile",7);
        }

        builder.item(MockTile.class, id++,i,1,"tile",3)
                .item(MockTile.class, id++,i,0,"tile",8);


        return builder.build();
    }


    public static class MockTile extends Tile {

    }

    public static class NumberTile extends Tile {

        @Override
        protected void onAttachAnimation(Animation animation) {
            super.onAttachAnimation(animation);
        }
    }

}
