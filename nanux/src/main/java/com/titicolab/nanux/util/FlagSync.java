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

package com.titicolab.nanux.util;


import static java.lang.Thread.sleep;

/**
 * Created by campino on 14/11/2016.
 *
 */

public class FlagSync {
    private static final long DEFAULT_TIMEOUT = 60000 * 10;
    private static final int MALI_SECOND_BY_MINUTE = 60000;
    private static final int MALI_SECOND_BY_SECOND =  1000;
    private boolean mFlag;

    public void assertFlag(){
        mFlag=true;
    }

    public boolean waitSync(){
        return waitSync(DEFAULT_TIMEOUT);
    }

    public boolean waitSyncSeconds(int seconds) {
        return waitSync(MALI_SECOND_BY_SECOND*seconds);
    }

    public boolean waitSyncMinutes(int minutes){
        return waitSync(MALI_SECOND_BY_MINUTE *minutes);
    }

    private boolean waitSync(long timeout){
        int counter = (int) (timeout/100);
        for (long i = 1; i < counter ; i++) {
            if(mFlag)
                break;
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean r = mFlag;
        mFlag =false;
        return r;
    }

    public static void delay(int sconds){
        new FlagSync().waitSyncSeconds(sconds);
    }

    public static void delayMiliSeconds(long mili){
        new FlagSync().waitSync(mili);
    }

    public void clear() {
        mFlag=false;
    }
}
