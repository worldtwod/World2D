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

package com.titicolab.puppeteer.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.titicolab.nanux.screen.GraphicView;

/**
 * Created by campino on 27/05/2016.
 *
 */
public class GLGraphicView extends GLSurfaceView implements GraphicView {

    public GLGraphicView(Context context) {
        super(context);
    }

    public GLGraphicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override public void onPause() {
        super.onPause();
    }


    @Override public void onResume() {
        super.onResume();
    }


    @Override public void setUpConfiguration(){
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
        setContentDescription("game_surface");
    }

    @Override public void setDebug(boolean flagDebug){
        if(flagDebug)
            setDebugFlags(GLSurfaceView.DEBUG_CHECK_GL_ERROR |
                    GLSurfaceView.DEBUG_LOG_GL_CALLS);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
