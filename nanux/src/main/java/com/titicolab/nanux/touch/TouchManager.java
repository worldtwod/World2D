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

import com.titicolab.nanux.objects.base.GameObject;

/**
 * Created by campino on 15/01/2018.
 *
 */
public interface TouchManager  extends ObservableInput.InputListener {
    void setClickListener(ClickListener clickListener);
    void setTouchActionListener(TouchActionListener touchActionListener);

    interface ClickListener {
        void onClickObject(GameObject.Touchable object);
    }

    interface DoubleClickListener {
        void onDoubleClickObject(GameObject.Touchable object);
    }
    interface TouchActionListener{
        void onDown(GameObject.Touchable object);
        void onMove(GameObject.Touchable object, boolean touching);
        void onUp(GameObject.Touchable object, boolean touching);
    }
}
