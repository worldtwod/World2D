
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

package com.titicolab.nanux.animation;

import com.titicolab.nanux.graphics.textures.TextureManager;
import com.titicolab.nanux.test.Monitor;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.DisplayInfo;


public abstract class BaseGame implements GameContext {


	private Controller mController;

	private ObservableRenderer mObservableRenderer;

	private ObservableLifeCycle mObservableLifeCycle;

	private ObservableInput mObservableInput;

	private DisplayInfo mDisplayInfo;

	private Monitor.OnEngineCreated mMonitorEngineCreated;

	private TextureManager mTextureManager;


	protected void connectObserver(Controller controller) {
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


	protected void startObservable() {
		mObservableRenderer.start();
		mObservableInput.start();
	}

	protected void stopObservable() {
		mObservableRenderer.stop();
		mObservableInput.stop();
	}

	protected void startController(Controller controller){
		mController = controller;
		mObservableLifeCycle.add(mController);
		mObservableInput.add(mController);
		mObservableRenderer.add(mController);
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

	protected void setObservableRenderer(ObservableRenderer observableRenderer) {
		this.mObservableRenderer = observableRenderer;
	}

	protected void setObservableLifeCycle(ObservableLifeCycle observableLifeCycle) {
		this.mObservableLifeCycle = observableLifeCycle;
	}

	protected void setObservableInput(ObservableInput observableInput) {
		this.mObservableInput =observableInput;
	}

	protected void setDisplayInfo(DisplayInfo displayInfo) {
		this.mDisplayInfo = displayInfo;
	}


	protected void setMonitorEngineCreated(Monitor.OnEngineCreated onEngineCreated) {
		this.mMonitorEngineCreated = onEngineCreated;
	}

	protected void setTextureManager(TextureManager textureManager) {
		mTextureManager = textureManager;
	}
}
