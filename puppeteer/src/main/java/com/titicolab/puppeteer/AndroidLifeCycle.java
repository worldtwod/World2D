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

import com.titicolab.nanux.list.FlexibleList;
import com.titicolab.nanux.core.ObservableLifeCycle;

/**
 * Created by campino on 20/11/2017.
 *
 */

public class AndroidLifeCycle extends FlexibleList<ObservableLifeCycle.LifeCycle>
         implements ObservableLifeCycle, ObservableLifeCycle.LifeCycle {

    private boolean mFlagNotify;

    AndroidLifeCycle() {
        super(1);
        mFlagNotify = false;
    }

    @Override
    public void onStart() {
       notifyOnStart();
    }
    @Override
    public void onRestart() {
       notifyOnRestart();
    }
    @Override
    public void onResume() {
       notifyOnResume();
    }
    @Override
    public void onPause() {
       notifyOnPause();
    }
    @Override
    public void onStop() {
       notifyOnStop();
    }

    @Override
    public void onDestroy() {
       notifyOnDestroy();
    }


    /** Notify *****************/

    private void notifyOnStart() {
       if(mFlagNotify)
        for(int i=0; i<size(); i++){
            get(i).onStart();
        }
    }

    private void notifyOnRestart() {
        if(mFlagNotify)
        for(int i=0; i< size(); i++){
            get(i).onRestart();
        }
    }

    private void notifyOnResume() {
        if(mFlagNotify)
        for(int i=0; i< size(); i++){
            get(i).onResume();
        }
    }

    private void notifyOnPause() {
        if(mFlagNotify)
        for(int i=0; i< size(); i++){
            get(i).onPause();
        }
    }

    private void notifyOnStop() {
        if(mFlagNotify)
        for(int i=0; i< size(); i++){
            get(i).onStop();
        }
    }

    private void notifyOnDestroy() {
        if(mFlagNotify)
        for(int i=0; i< size(); i++){
            get(i).onDestroy();
        }
    }

    @Override
    public void start() {
        mFlagNotify=true;
    }

    @Override
    public void stop() {
        mFlagNotify=false;
    }
}
