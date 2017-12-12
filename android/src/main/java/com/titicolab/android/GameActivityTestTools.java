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

package com.titicolab.android;

import com.titicolab.nanux.test.Monitor;

/**
 * Created by campino on 10/11/2016.
 *
 */

public class GameActivityTestTools {


    public static void setMonitor(Monitor.OnEngineCreated listener) {
        GameActivity.monitor=listener;
    }

    public static Monitor.OnEngineCreated getMonitor() {
        Monitor.OnEngineCreated monitor = GameActivity.monitor;
        //GameActivity.monitor=null;
        return monitor;
    }
}
