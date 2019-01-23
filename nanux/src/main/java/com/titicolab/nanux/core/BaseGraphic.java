
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

import com.titicolab.nanux.graphics.texture.TextureManager;
import com.titicolab.nanux.objects.base.Scene;
import com.titicolab.nanux.test.Monitor;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.DisplayInfo;


public abstract class BaseGraphic implements GraphicContext {

	private Controller mController;

	private ObservableRenderer mObservableRenderer;

	private ObservableLifeCycle mObservableLifeCycle;

	private ObservableInput mObservableInput;

	private DisplayInfo mDisplayInfo;

	private Monitor.OnEngineCreated mMonitorEngineCreated;

	private TextureManager mTextureManager;

	public void connectObserver(Controller controller) {
		if(mMonitorEngineCreated!=null){
			//Add the external observer
			//The controller will be unconnected
			mMonitorEngineCreated.onEngineCreated(this);
		}else{
			//Add the controller just observer
			startController(controller);
		}

		mObservableLifeCycle.start();
	}

	private void startController(Controller controller){
		mController = controller;
		mObservableLifeCycle.add(mController);
		mObservableInput.add(mController);
		mObservableRenderer.add(mController);
	}

	public void setStartScene(Scene scene) {
		if(mMonitorEngineCreated==null)
			mController.setStartScene(scene);
	}

	@Override
	public ObservableRenderer getObservableRenderer() {
		return mObservableRenderer;
	}
	@Override
	public ObservableInput getObservableInput() {
		return mObservableInput;
	}
	@Override
	public ObservableLifeCycle getObservableLifeCycle() {
		return mObservableLifeCycle;
	}

	@Override
	public DisplayInfo getDisplayInfo() {
		return mDisplayInfo;
	}

	@Override
	public TextureManager getTextureManager() {
		return mTextureManager;
	}

	/********* Setters ********************************************/

	public void setObservableRenderer(ObservableRenderer observableRenderer) {
		this.mObservableRenderer = observableRenderer;
	}

	public void setObservableLifeCycle(ObservableLifeCycle observableLifeCycle) {
		this.mObservableLifeCycle = observableLifeCycle;
	}

	public void setObservableInput(ObservableInput observableInput) {
		this.mObservableInput =observableInput;
	}

	public void setDisplayInfo(DisplayInfo displayInfo) {
		this.mDisplayInfo = displayInfo;
	}


	public void setMonitorEngineCreated(Monitor.OnEngineCreated onEngineCreated) {
		this.mMonitorEngineCreated = onEngineCreated;
	}

	public void setTextureManager(TextureManager textureManager) {
		mTextureManager = textureManager;
	}

	public void setShowFPS(boolean showFPS) {
		if(mController!=null) {
			this.mController.showFPS(showFPS);
		}
	}
}
