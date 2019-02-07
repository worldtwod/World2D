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
import com.titicolab.nanux.objects.Physics;
import com.titicolab.puppet.collision.CollisionArea;
import com.titicolab.puppet.collision.CollisionManager;
import com.titicolab.puppet.collision.CollisionObject;

/**
 * Created by campino on 16/01/2017.
 *
 */

public class Actor extends LayerObject implements CollisionObject {

    private final Physics body;
    private CollisionManager mCollision;

    public Actor() {
        body = new Physics();
    }

    @Override
    public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
        return super.onDefineAnimations(builder);
    }

    @Override
    protected void onCreated() {

    }

    @Override
    public boolean isCollisionEnable(){
        return  mCollision!=null && mCollision.getCollisionArea()!=null;
    }

    @Override
    protected void updateLogic() {
    }
    @Override
    protected void updateRender() {
        updateIjFromPosition((int)body.position.x,(int)body.position.y);
        super.setPosition(body.position.x,body.position.y);
        super.updateRender();
    }

    @Override
    public void setPosition(float x, float y) {
        body.setPosition(x,y);
        super.setPosition(x,y);
    }

    @Override
    public CollisionArea[] getCollisionAreas() {
        return mCollision.getCollisionAreas();
    }

    public CollisionArea getCollisionArea() {
        return mCollision.getCollisionArea();
    }

    public Physics getBody() {
        return body;
    }

    protected void setCollisionManager(CollisionManager collisionManager) {
        mCollision = collisionManager;
    }

    public CollisionManager getCollisionManager() {
        return mCollision;
    }


    public void onTileCollision(int collisionMask) {

    }

    public boolean onActorCollision(Actor actor) {
        return false;
    }
}
