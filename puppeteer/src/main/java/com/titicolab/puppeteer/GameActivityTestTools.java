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

package com.titicolab.puppeteer;

import com.titicolab.nanux.test.Monitor;

/**
 * Created by campino on 10/11/2016.
 *
 */

@Deprecated
public class GameActivityTestTools {

    public static void setMonitor(Monitor.OnGraphicContextCreated listener) {
        //GraphicActivity.monitor=listener;
    }

    static Monitor.OnGraphicContextCreated getMonitor() {
       // Monitor.OnGraphicContextCreated monitor = GraphicActivity.monitor;
        //GraphicActivity.monitor=null;
        return null; //monitor;
    }
}
