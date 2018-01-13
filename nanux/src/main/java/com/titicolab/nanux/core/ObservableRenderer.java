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

import com.titicolab.nanux.util.GPUInfo;
/**
 * Created by campino on 21/11/2017.
 *
 */

public interface ObservableRenderer {

    interface Renderer {
        void onSurfaceCreated(GameContext game, GPUInfo eglConfig);
        void onSurfaceChanged(int width, int height);
        void onDrawFrame();
    }

     void add(ObservableRenderer.Renderer listener);

     void start();
     void reStart();
     void stop();

}
