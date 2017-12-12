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

public class Physics {

    public static float time=18/1000;
    public final Vector position;
    public final Vector velocity;
    public final Vector force;
    public float mass;



    public static class Vector{
        public float x;
        public float y;
    }

    public Physics() {
        force = new Vector();
        position = new Vector();
        velocity = new Vector();
        mass = 1;
    }

    private void reset(float m){
        force.x=0;force.y=0;
        position.x=0; position.y=0;
        velocity.x=0; velocity.y=0;
        mass=m;
    }

    public void setPosition(float x, float y){
        position.x=x;
        position.y=y;
    }

    public void addPosition(float x, float y){
        position.x+=x;
        position.y+=y;
    }

    public void setVelocity(float x, float y){
        velocity.x=x;
        velocity.y=y;
    }

    public void setForce(float x, float y){
        force.x=x;
        force.y=y;
    }

    public static void integrator(Physics particle) {
        particle.velocity.x = particle.velocity.x + particle.force.x*Physics.time / particle.mass;
        particle.velocity.y = particle.velocity.y + particle.force.y * Physics.time / particle.mass;
        particle.position.x = particle.position.x + particle.velocity.x * Physics.time;
        particle.position.y = particle.position.y + particle.velocity.y * Physics.time;
    }
    public void integratorX() {
        this.velocity.x = this.velocity.x + this.force.x*Physics.time / this.mass;
        this.position.x = this.position.x + this.velocity.x * Physics.time;
    }

    public void integrator() {
        this.velocity.x = this.velocity.x + this.force.x*Physics.time / this.mass;
        this.position.x = this.position.x + this.velocity.x * Physics.time;

        velocity.y = velocity.y + force.y * Physics.time /mass;
        position.y = position.y + velocity.y * Physics.time;
    }


}
