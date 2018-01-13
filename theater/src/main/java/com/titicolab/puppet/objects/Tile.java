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

package com.titicolab.puppet.objects;


import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.puppet.collision.CollisionArea;
import com.titicolab.puppet.collision.GroundCollisionManager;
import com.titicolab.puppet.collision.CollisionObject;

/**
 * Created by campino on 20/12/2016.
 *
 */

public class Tile extends LayerObject implements CollisionObject {



    private GroundCollisionManager mCollisionManager;

    @Override
    protected void onCreated() {
        mCollisionManager = new GroundCollisionManager(this);
    }


    @Override
    public boolean isCollisionEnable() {
        return mCollisionManager.hasCollisionArea();
    }

    @Override
    public CollisionArea[] getCollisionAreas() {
        return mCollisionManager.getCollisionAreas();
    }

    public GroundCollisionManager getCollisionManager() {
        return mCollisionManager;
    }

    @Override
    public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
        return super.onDefineAnimations(builder);
    }
}
