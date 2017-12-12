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

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.view.WindowManager;

import com.titicolab.nanux.util.DisplayInfo;

/**
 * Created by campino on 20/05/2016.
 *
 */
public class AndroidDisplayMetrics implements DisplayInfo {

    public static final int REFERENCE_WIDTH_DEFAULT = 1280;
    public static final int REFERENCE_HEIGHT_DEFAULT = 720;
    private static float    SCALE_SCREEN = 0.7f;

    private   int mScreenWidth;
    private   int mScreenHeight;
    private float mReferenceWidth;
    private float mReferenceHeight;

    private float mFixWidth;
    private float mFixHeight;

    private float mNavigationBarCorrection;


    private float mSpFactor = 1f;
    private float mDensity;
    private int   mDensityDpi;
    private float mAspectRatio;


    public AndroidDisplayMetrics(Context context){
        setUpScreenSize(context);
        setReferenceSize(REFERENCE_WIDTH_DEFAULT,REFERENCE_HEIGHT_DEFAULT);
        setFixPort();
    }



    private void setReferenceSize(float width, float height){
        mReferenceWidth=width;
        mReferenceHeight=height;
    }

    private void setUpScreenSize(Context context) {
        WindowManager windowsManager =  (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        android.util.DisplayMetrics displaymetrics = new android.util.DisplayMetrics();
        windowsManager.getDefaultDisplay().getMetrics(displaymetrics);
        int w = displaymetrics.widthPixels;
        int h = displaymetrics.heightPixels;
        mDensity = displaymetrics.density;
        mDensityDpi = displaymetrics.densityDpi;
        mNavigationBarCorrection=0;

        /**
         * It do the correction if there are a navigation bar, and it is in SYSTEM_UI_FLAG_IMMERSIVE.
         * Solo en Android 4.4 (API Level 19).*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Point realSize = new Point();
            windowsManager.getDefaultDisplay().getRealSize(realSize);
            mNavigationBarCorrection = realSize.y-h;
            w = realSize.x;
            h = realSize.y;
        }

        mScreenWidth =w;
        mScreenHeight =h;
        mAspectRatio  =mScreenWidth/(float)mScreenHeight;
    }


    private void setFixPort(){
        if ((mScreenWidth - mReferenceWidth) < mScreenWidth * (1.0 - SCALE_SCREEN)) {
           mSpFactor = SCALE_SCREEN * mScreenWidth / mReferenceWidth;
        }
        mFixWidth  = mReferenceWidth * mSpFactor;  //mReferenceWidth*mSpFactor;
        mFixHeight = mReferenceHeight* mSpFactor;
        //setViewport(mFixWidth,mFixHeight);
    }





    @Override
    public String toString() {
        StringBuffer buffer  = new StringBuffer();
        buffer.append("\n--- Android Screen Metrics ---------");
        buffer.append("\nScreen Width:  "  + mScreenWidth +"px\t"+ px2dp(mScreenWidth) + "dp");
        buffer.append("\nScreen Height: " + mScreenHeight+"px\t" + px2dp(mScreenHeight) + "dp");
        buffer.append("\nRatio:         " +   mAspectRatio);
        buffer.append("\nDensity:       " + mDensity);
        buffer.append("\nDpi:           " +     mDensityDpi);
        buffer.append("\nNavBar:        " +  mNavigationBarCorrection);

        buffer.append("\n\n--- BaseGame Screen Metrics -------------");
        buffer.append("\nFix Width:       " + getFixWidth());
        buffer.append("\nFix Height:      " + getFixHeight());
        buffer.append("\nReferenceWidth:  " + getReferenceWidth());
        buffer.append("\nReferenceHeight: " + getReferenceHeight());
        buffer.append("\nFactor Density:  " + getScalePixel()+ "\n");
        return buffer.toString();
    }



    /* Implementation display  */


    public int getScreenWidth() {
        return mScreenWidth;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }

    public float getReferenceHeight() {
        return mReferenceHeight;
    }

    public float getReferenceWidth() {
        return mReferenceWidth;
    }


    public  float getScalePixel() {
        return mSpFactor;
    }

    public int getFixWidth() {
        return (int) mFixWidth;
    }


    public int getFixHeight() {
        return (int) mFixHeight;
    }



    public float px2dp(float px){
        return px /( (float)mDensityDpi / android.util.DisplayMetrics.DENSITY_DEFAULT);
    }

    public float dp2px(float dp){
        return dp * ((float)mDensityDpi / android.util.DisplayMetrics.DENSITY_DEFAULT);
    }


    public  int getOrientation(){
        int orientation;
        if(getScreenWidth()< getScreenHeight()){
                orientation = Configuration.ORIENTATION_PORTRAIT;
        }else {
                orientation = Configuration.ORIENTATION_LANDSCAPE;
        }
        return orientation;
    }


    /**
     * Ratio of screen, such as: mScreenWidth/(float)mScreenHeight;
     * @return ScreenWidth/ScreenHeight
     */
    public float getAspectRatio() {
        return mAspectRatio;
    }

}
