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

package com.titicolab.nanux.touch;

/**
 * Created by campino on 21/11/2017.
 *
 */

public interface ObservableInput {

     void start();
     void stop();
     void add(InputListener listener);
     void remove(InputListener listener);

     interface InputListener {
         boolean onTouch (Event event);
         boolean onKey(int keyEvent);
    }

     interface Event {
        int getAction();
        /**
         * @return  X coordinate from ui
         */
        float getUiX();

        /**
         * @return Y coordinates from ui
         */
        float getUiY();
        float getPixelX();
        float getPixelY();

    }
}
