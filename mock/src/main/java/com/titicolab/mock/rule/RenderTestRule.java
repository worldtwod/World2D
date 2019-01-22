

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

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.test.rule.ActivityTestRule;

import com.titicolab.puppeteer.GameActivity;
import com.titicolab.puppeteer.GameActivityTestTools;
import com.titicolab.puppeteer.util.LogHelper;
import com.titicolab.nanux.core.GameContext;
import com.titicolab.nanux.core.ObservableLifeCycle;
import com.titicolab.nanux.core.ObservableRenderer;
import com.titicolab.nanux.test.Monitor;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.FlagSync;
import com.titicolab.nanux.util.GPUInfo;


/**
 * Created by campino on 10/11/2016.
 *
 */

public class RenderTestRule <M>
                extends ActivityTestRule implements
        Monitor.OnEngineCreated,
        ObservableRenderer.Renderer,
        ObservableInput.InputListener{

    public static final String TEST_LOG_LABEL = "SceneTestCase";
    private LogHelper log;
    private FlagSync mFlagStartDraw;
    private long mFrames;

    private ObservableRenderer.Renderer mObserverRender;
    private ObservableInput.InputListener mObserverInput;


    /************************** Builder Constructors  *****************************************************/
    public static  class Builder{
        private Class clazz;
        private ObservableRenderer.Renderer render;
        private ObservableInput.InputListener event;

        public Builder setLaunchActivity(Class<? extends GameActivity> clazz) {
            this.clazz = clazz;
            return this;
        }
        public Builder setObserverRender(ObservableRenderer.Renderer render) {
            this.render = render;
            return this;
        }

        public Builder setObserverInput(ObservableInput.InputListener event){
            this.event= event;
            return this;
        }

        public RenderTestRule<GameActivity> build(){
            if(clazz==null) throw new IllegalArgumentException("It need setLaunchActivity");
            return new RenderTestRule<GameActivity>(clazz,render,event);
        }
    }

    public static Builder getBuilder(){
        return new Builder();
    }

    /************************** Constructors  *****************************************************/

    public RenderTestRule(@NonNull Class<M> activityClass) {
        super(activityClass,true,true);
       iniRule(null, null);
    }

    public RenderTestRule(Class<M> activityClass,
                            ObservableRenderer.Renderer observerRender,
                                ObservableInput.InputListener event) {
        super(activityClass,true,true);
        iniRule(observerRender,event);
    }


    private void iniRule(ObservableRenderer.Renderer observerRender, ObservableInput.InputListener event){
        log = new LogHelper(this,TEST_LOG_LABEL);
        mFlagStartDraw = new FlagSync();
        mObserverRender = observerRender;
        mObserverInput = event;
    }



    public void log(String string){
        log.debug(string);
    }

    @CallSuper
    @Override
    public void onEngineCreated(GameContext game) {
        log.debug("\tonEngineCreated");
        game.getObservableRenderer().add(this);
        game.getObservableInput().add(this);
        game.getObservableLifeCycle().add(lifeCycle);

        if(mObserverRender!=null)
            game.getObservableRenderer().add(mObserverRender);

        if(mObserverInput !=null)
            game.getObservableInput().add(mObserverInput);

    }


    @Override
    protected void beforeActivityLaunched() {
        super.beforeActivityLaunched();
        log.debug("\tbeforeActivityLaunched");
        GameActivityTestTools.setMonitor(this);
    }

    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();
        log.debug("\tafterActivityLaunched");
        mFlagStartDraw.waitSyncSeconds(2);
    }

    @Override
    protected void afterActivityFinished() {
        super.afterActivityFinished();
        log.debug("\tafterActivityFinished");
    }


    @Override
    public void onSurfaceCreated(GameContext game, GPUInfo eglConfig) {
        log.debug("\tonSurfaceCreated");
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        log.debug("\tonSurfaceChanged");
    }

    @CallSuper
    @Override
    public void onDrawFrame() {
        if(mFrames%60==0) {
            log.debug("\tonDrawFrame " + mFrames);
            mFlagStartDraw.assertFlag();
        }
        mFrames++;
    }

    @Override
    public boolean onTouch(ObservableInput.Event event) {
        log.debug("\tonTouch");
        return false;
    }

    @Override
    public boolean onKey(int keyEvent) {
        log.debug("\tonKey");
        return false;
    }


    private ObservableLifeCycle.LifeCycle lifeCycle = new ObservableLifeCycle.LifeCycle() {

        @Override
        public void onStart() {
            log.debug("onStart");
        }

        @Override
        public void onRestart() {
            log.debug("onRestart");
        }

        @Override
        public void onResume() {
            log.debug("onResume");
        }

        @Override
        public void onPause() {
            log.debug("onPause");
        }

        @Override
        public void onStop() {
            log.debug("onStop");
        }

        @Override
        public void onDestroy() {
            log.debug("onDestroy");
        }
    };


}
