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
import com.titicolab.puppet.collision.CollisionManager;
import com.titicolab.puppet.objects.Actor;

/**
 * Created by campino on 27/12/2017.
 *
 */

public class MockActor extends Actor {

    public static final int ROBOT_STOP    = 1002;
    static final int ROBOT_WALKING = 1003;
    private static final int ROBOT_JUMPING_UP = 1004;
    private static final int ROBOT_JUMPING_DOWN = 1005;

    @Override
    public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
        return builder
                .sequence("Boxelbot")
                .resources(R.drawable.player_boxelbot)
                .clip(ROBOT_STOP).grid(8,4).cells(0)
                .clip(ROBOT_WALKING).grid(8,4).cells(0,7)
                .clip(ROBOT_JUMPING_UP).grid(8,4).cells(8,12)
                .clip(ROBOT_JUMPING_DOWN).grid(8,4).cells(12,25)
                .build();
    }

    @Override
    protected void onCreated() {
        setCollisionManager(new CollisionManager(this));
        getCollisionArea().setRelativeMargin(0.30f,0.20f,0.30f,0);
        getAnimator().setSpeed(10);
        getAnimator().playAndRepeat(ROBOT_WALKING);

    }
}