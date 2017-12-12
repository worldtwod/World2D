package com.titicolab.nanux.lifeCycle;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.titicolab.android.view.GLGameView;
import com.titicolab.mock.rule.RenderTestRule;
import com.titicolab.mock.tools.HelperTest;
import com.titicolab.nanux.MainActivity;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by campino on 10/11/2016.
 * follow the path according to
 * https://developer.android.com/training/basics/activity-lifecycle/starting.html
 */
@RunWith(AndroidJUnit4.class)
public class FinishActivityTest {

    @Rule
    public RenderTestRule<MainActivity> mRenderRule =
            new RenderTestRule<>(MainActivity.class, render, event);

    private UiDevice mDevice;


    @Before
    public void before(){
        mRenderRule.log("before test");
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }


    @Test
    public void setUpTest() throws RemoteException, UiObjectNotFoundException {
        mRenderRule.log("/**  It finish the activity, launch again  **/");
        //mRenderRule.getActivity().finish();
        mRenderRule.finishActivity();
        HelperTest.delaySeconds(3);

        mRenderRule.launchActivity(null);
        UiObject glSurface = mDevice.findObject(new UiSelector().className(GLGameView.class));
        Assert.assertNotNull(glSurface);
    }

    @After
    public void after(){
        mRenderRule.log("after test");
    }


}
