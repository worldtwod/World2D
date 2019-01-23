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

package com.titicolab.nanux.core;


import com.titicolab.nanux.objects.base.Scene;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.GPUInfo;

/**
 * Created by campino on 11/12/2017.
 *
 */

public abstract class Controller implements ObservableLifeCycle.LifeCycle,
        ObservableInput.InputListener,
        ObservableRenderer.Renderer {


    public synchronized void setStartScene(Scene startScene) {

    }


    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public boolean onTouch(ObservableInput.Event event) {
        return false;
    }

    @Override
    public boolean onKey(int keyEvent) {
        return false;
    }

    @Override
    public void onSurfaceCreated(GraphicContext game, GPUInfo eglConfig) {

    }

    @Override
    public void onSurfaceChanged(int width, int height) {

    }

    @Override
    public void onDrawFrame() {

    }


    abstract void showFPS(boolean showFPS);

}
