

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

package com.titicolab.mock.rule;

import com.titicolab.nanux.core.GraphicContext;
import com.titicolab.nanux.core.ObservableLifeCycle;
import com.titicolab.nanux.core.ObservableRenderer;
import com.titicolab.nanux.test.Monitor;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.FlagSync;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.puppeteer.GameActivityTestTools;
import com.titicolab.puppeteer.GraphicActivity;
import com.titicolab.puppeteer.util.LogHelper;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.test.rule.ActivityTestRule;


/**
 * Created by campino on 10/11/2016.
 *
 */

public class BaseTestRule  extends ActivityTestRule {

    public static final String TEST_LOG_LABEL = "SceneTestCase";
    private LogHelper log;
    private FlagSync  mFlagStartDraw;
    private long      mFrames;

    public BaseTestRule(Class activityClass, boolean initialTouchMode, boolean launchActivity) {
        super(activityClass, initialTouchMode, launchActivity);
    }

    public void log(String string){
        log.debug(string);
    }

}
