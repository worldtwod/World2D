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


/**
 * Created by campino on 20/05/2016.
 *
 */
public interface DisplayInfo {
    public static float scalePixel = 0;
    public static final int REFERENCE_WIDTH_DEFAULT = 1280;
    public static final int REFERENCE_HEIGHT_DEFAULT = 720;

    int getScreenWidth();

    int getScreenHeight();


    float getReferenceHeight();

    float getReferenceWidth();



    int getFixWidth();

    int getFixHeight();

     float px2dp(float px);

     float dp2px(float dp);

     int getOrientation();

     float getAspectRatio();
     float getScalePixel();

}
