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
import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.nanux.animation.ClipMap;
import com.titicolab.puppet.objects.Tile;

/**
 * Created by campino on 27/12/2017.
 *
 */

public class MockGround extends Tile {

    @Override
    public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
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

    @Override
    protected void onCreated() {
        super.onCreated();
        int indexClip = getAnimator().getIndexClip();
        switch (indexClip) {
            case 0:
                getCollisionManager()
                        .setUpCollisionAreas(false, false, false, false);
                break;
            case 1:
                getCollisionManager()
                        .setUpCollisionAreas(false, false, true, true);
                break;
            case 2:
                getCollisionManager()
                        .setUpCollisionAreas(true, false, true, true);
                break;
            case 3:
                getCollisionManager()
                        .setUpCollisionAreas(true, false, false, true);
                break;
        }
    }
}