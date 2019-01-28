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
import com.titicolab.nanux.core.ObservableRenderer;
import com.titicolab.nanux.core.RunnerTask;
import com.titicolab.nanux.graphics.texture.TextureManager;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.DisplayInfo;
import com.titicolab.puppeteer.util.LogHelper;

import androidx.test.rule.ActivityTestRule;

/**
 * Created by campino on 10/11/2016.
 *
 */

public class GraphicTestRule extends ActivityTestRule<GraphicTestActivity> {

    static final String TEST_LOG_LABEL = "GraphicTestRule";
    private final GraphicActivityFactory graphicObserverEvents;
    public final LogHelper log;

    @SuppressWarnings("WeakerAccess")
    GraphicTestRule(GraphicActivityFactory factory,
                    boolean initialTouchMode,
                    boolean launchActivity) {
        super(factory,initialTouchMode, launchActivity);
        this.graphicObserverEvents = factory;
        log = new LogHelper(this,TEST_LOG_LABEL);
        log.debug("GraphicTestRule()");
    }


    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();
        log.debug("afterActivityLaunched()");
        // synchronize activity and test
        graphicObserverEvents.getFlagStartDraw().waitSyncSeconds(2);
    }

    @Override
    protected void afterActivityFinished() {
        super.afterActivityFinished();
        log.debug("afterActivityFinished()");
    }


    public GraphicContext getGraphicContext() {
        return graphicObserverEvents.getGraphicContext();
    }

    public TextureManager getTextureManager() {
        return getGraphicContext().getTextureManager();
    }

    public DisplayInfo getDisplayInfo() {
        return getGraphicContext().getDisplayInfo();
    }

    public void waitTouchSeconds(int seconds) {
        graphicObserverEvents.waitTouchSeconds(seconds);
    }

    public RunnerTask getRunnerTask(){
        return getTextureManager().getRunnerTask();
    }


    public static  class Builder{
        private ObservableRenderer.Renderer render;
        private ObservableInput.InputListener event;

        private boolean initialTouchMode = false;
        private boolean startActivity  = true;
        private ObserverGraphicContext.ContextCreated observerContextCreated;
        private ObserverGraphicContext.SurfaceCreated observerSurfaceCreated;
        private ObserverGraphicContext.SurfaceChanged observerSurfaceChanged;
        private ObserverGraphicContext.DrawFrame observerDrawFrame;

        public Builder setObserverRender(ObservableRenderer.Renderer render) {
            this.render = render;
            return this;
        }

        public Builder setObserverInput(ObservableInput.InputListener event){
            this.event= event;
            return this;
        }

        public Builder setStartActivity(boolean startActivity) {
            this.startActivity = startActivity;
            return this;
        }
        public Builder setInitialTouchMode(boolean initialTouchMode) {
            this.initialTouchMode = initialTouchMode;
            return this;
        }

        public Builder setObserverContextCreated(
                ObserverGraphicContext.ContextCreated observerContextCreated) {
            this.observerContextCreated = observerContextCreated;
            return this;
        }

        public Builder setObserverSurfaceCreated(
                ObserverGraphicContext.SurfaceCreated observerSurfaceCreated) {
            this.observerSurfaceCreated = observerSurfaceCreated;
            return this;
        }

        public Builder setObserverSurfaceChanged(
                ObserverGraphicContext.SurfaceChanged observerSurfaceChanged) {
            this.observerSurfaceChanged = observerSurfaceChanged;
            return this;
        }

        public Builder setObserverDrawFrame(
                ObserverGraphicContext.DrawFrame observerDrawFrame) {
            this.observerDrawFrame = observerDrawFrame;
            return this;
        }

        public GraphicTestRule build(){
            GraphicActivityFactory factory =
                    new GraphicActivityFactory(render, event,
                            observerContextCreated,
                            observerSurfaceCreated,
                            observerSurfaceChanged,
                            observerDrawFrame);
            return new GraphicTestRule(factory,initialTouchMode,startActivity);
        }
    }

}
