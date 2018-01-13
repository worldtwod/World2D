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


/**
 * Created by campino on 15/06/2016.
 *
 */
public abstract class RunnableTask implements Runnable {
    private static final long POLLING_TIME = 10;
    private boolean mSyncFlat;
    private Exception mError=null;

    boolean waitSyncFlat(long timeout){
        int counter = (int) (timeout/POLLING_TIME);
        for (long i = 1; i < counter ; i++) {
            if(mSyncFlat)
                break;
            try {
                Thread.sleep(POLLING_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return mSyncFlat;
    }

    synchronized void finished() {
        mSyncFlat=true;
    }


    public void onError(Exception error) {
        mError = error;
    }

    public Exception getError() {
        return mError;
    }
}
