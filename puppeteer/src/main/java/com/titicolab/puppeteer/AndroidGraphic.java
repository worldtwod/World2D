
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

import com.titicolab.nanux.core.BaseGraphic;
import com.titicolab.nanux.core.Controller;
import com.titicolab.nanux.core.ObservableLifeCycle;
import com.titicolab.nanux.core.ObservableRenderer;
import com.titicolab.nanux.graphics.texture.TextureManager;
import com.titicolab.nanux.test.Monitor;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.DisplayInfo;
import com.titicolab.puppeteer.view.GLGraphicView;


/**
 *
 * Android implementation of Game context, it inject and deal with all dependencies
 * that need android framework classes: Just GLSurfaceView, ScreenMetrics.
 *
 * Created by campino on 10/11/2016.
 */
public class AndroidGraphic extends BaseGraphic implements ObservableLifeCycle.LifeCycle{

	/** GLSurfaceView needed for GL Context  **/
	private GLGraphicView mGLGraphicView;

	AndroidGraphic(Controller controller,
				   DisplayInfo displayInfo,
				   TextureManager textureManager,
				   ObservableRenderer observableRenderer,
				   ObservableLifeCycle observableLifeCycle,
				   ObservableInput observableInput,
				   Monitor.OnGraphicContextCreated monitorEngineCreated,
				   GLGraphicView glGraphicView){
		super(controller,
				displayInfo,
				textureManager,
				observableRenderer,
				observableLifeCycle,
				observableInput,
				monitorEngineCreated);

		mGLGraphicView = glGraphicView;
	}

	/**
	 * Provide the ContentView for the GraphicActivity
	 * @return A GLGameView
	 */
	GLGraphicView getGLGameView() {
		return mGLGraphicView;
	}

	@Override
	public void onStart() {
		getObservableLifeCycle().onStart();
	}

	@Override
	public void onRestart() {
		getObservableRenderer().reStart();
		getObservableLifeCycle().onRestart();
	}

	@Override
	public void onResume() {
		getObservableInput().start();
		getObservableLifeCycle().onResume();
		mGLGraphicView.onResume();
	}

	@Override
	public void onPause() {
		mGLGraphicView.onPause();
		getObservableLifeCycle().onPause();
		getObservableInput().stop();
	}

	@Override
	public void onStop() {
		getObservableLifeCycle().onStop();
	}

	@Override
	public void onDestroy() {
		getObservableRenderer().stop();
		getObservableLifeCycle().onDestroy();
	}

	@Override
	public AndroidLifeCycle getObservableLifeCycle() {
		return (AndroidLifeCycle) super.getObservableLifeCycle();
	}

}
