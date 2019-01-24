
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

	/** This control the graphics loop */
	private final Controller mController;

	/** this observable will notify about render events */
	private  final ObservableRenderer mObservableRenderer;

	/** this observable will notify about  life cycle events: onPause() onStart().. */
	private  final ObservableLifeCycle mObservableLifeCycle;

	/** this observable will notify about input events, touch, click etc */
	private  final ObservableInput mObservableInput;

	private  final DisplayInfo mDisplayInfo;

	private  final Monitor.OnEngineCreated mMonitorEngineCreated;

	private  final TextureManager mTextureManager;

	protected BaseGraphic(Controller controller,
						  DisplayInfo displayInfo,
						  TextureManager textureManager,
						  ObservableRenderer observableRenderer,
						  ObservableLifeCycle observableLifeCycle,
						  ObservableInput observableInput,
						  Monitor.OnEngineCreated monitorEngineCreated) {

		this.mController = controller;
		this.mDisplayInfo = displayInfo;
		this.mTextureManager = textureManager;
		this.mObservableRenderer = observableRenderer;
		this.mObservableLifeCycle = observableLifeCycle;
		this.mObservableInput = observableInput;
		this.mMonitorEngineCreated = monitorEngineCreated;

		//Setup controller
		mObservableLifeCycle.add(mController);
		mObservableInput.add(mController);
		mObservableRenderer.add(mController);

		mObservableLifeCycle.start();
	}

	/**
	 * Start to render
	 */
	public void start(){
		mObservableRenderer.start();
	}

	public void syncPlay(Scene scene) {
		mController.getSceneManager().play(scene);
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


}
