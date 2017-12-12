
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

package com.titicolab.android.engine;

import com.titicolab.android.GameActivity;
import com.titicolab.android.GameActivityTestTools;
import com.titicolab.android.util.LogHelper;
import com.titicolab.android.view.GLGameView;
import com.titicolab.nanux.animation.BaseGame;
import com.titicolab.nanux.animation.ObservableLifeCycle;
import com.titicolab.nanux.animation.RunnerTask;
import com.titicolab.opengl.shader.AndroidDrawToolsBuilder;
import com.titicolab.opengl.shader.AndroidTextureManager;
import com.titicolab.puppet.loop.Puppeteer;


public class AndroidGame  extends BaseGame implements ObservableLifeCycle.LifeCycle{




	private final GLGameView mGLGameView;

	private LogHelper log;


	public AndroidGame(GameActivity activity){

		log = new LogHelper(this);
		log.info("Constructor AndroidGame");

		setDisplayInfo(new AndroidDisplayMetrics(activity));

		mGLGameView = new GLGameView(activity);
		mGLGameView.setUpConfiguration();
		mGLGameView.setDebug(true);
		mGLGameView.getHolder().setFixedSize(
						getDisplayInfo().getFixWidth(),
						getDisplayInfo().getFixHeight());


		setObservableInput(new AndroidInput(mGLGameView));
		setObservableLifeCycle(new AndroidLifeCycle());
		setObservableRenderer(new AndroidRenderer(this,mGLGameView));
		setTextureManager(new AndroidTextureManager(activity, new RunnerTask(), getDisplayInfo()));


		setMonitorEngineCreated(GameActivityTestTools.getMonitor());
		Puppeteer controller = new Puppeteer(new AndroidDrawToolsBuilder(activity));
		connectObserver(controller);
		startObservable();
	}



	public GLGameView getGLGameView() {
		return mGLGameView;
	}

	@Override
	public AndroidLifeCycle getObservableLifeCycle() {
		return (AndroidLifeCycle) super.getObservableLifeCycle();
	}


	/************************************************* Life Cycle *********************************
	 **********************************************************************************************/

	@Override
	public void onStart() {
		getObservableLifeCycle().onStart();
	}

	@Override
	public void onRestart() {
		startObservable();
		getObservableLifeCycle().onRestart();
	}
	@Override
	public void onResume() {
		startObservable();
		getObservableLifeCycle().onResume();
		mGLGameView.onResume();
	}
	@Override
	public void onPause() {
		mGLGameView.onPause();
		getObservableLifeCycle().onPause();
		stopObservable();

	}

	@Override
	public void onStop() {
		getObservableLifeCycle().onStop();
		stopObservable();
	}

	@Override
	public void onDestroy() {
		getObservableLifeCycle().onDestroy();
	}

}
