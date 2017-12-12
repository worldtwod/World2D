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

package com.titicolab.android.util;

import android.util.Log;

public class LogHelper {

	private String mClassDebugName; 
	private boolean mTesting=false;
	private boolean mEnable =true;
	private String mDebugTag="MainActivity";
	

	public LogHelper(Object object, String tag){
		mClassDebugName=object.getClass().getSimpleName();
		mDebugTag = tag;
	}

	public LogHelper(Object object){
		mClassDebugName=object.getClass().getSimpleName();
	}

	
	public boolean isTesting() {
		return mTesting;
	}
	
	public void setTesting(boolean mTesting) {
		this.mTesting = mTesting;
	}
	
	public boolean isDebuging() {
		return mEnable;
	}
	
	public void setEnableDebug(boolean debug) {
		this.mEnable = debug;
	}
	
	public String getDebugTag() {
		return mDebugTag;
	}
	
	public void debug(String str){
		if(mEnable)
			Log.d(mDebugTag,mClassDebugName + " >_" + str);
	}
	
	public void error(String str){
		if(mEnable)
			Log.e(mDebugTag,mClassDebugName + " >_" + str);
	}
	public void error(String str, Throwable tr){
		if(mEnable)
			Log.e(mDebugTag,mClassDebugName + " >_" + str,tr);
	}
	
	public void info(String str){
		if(mEnable)
			Log.i(mDebugTag,mClassDebugName + " >_" + str);
	}

	public void setTag(String tag) {
		mDebugTag=tag;
	}
}
