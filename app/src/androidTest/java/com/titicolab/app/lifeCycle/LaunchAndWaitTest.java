package com.titicolab.app.lifeCycle;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.titicolab.mock.rule.RenderTestRule;
import com.titicolab.mock.tools.HelperTest;
import com.titicolab.app.MainActivity;

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
public class LaunchAndWaitTest {

    @Rule
    public RenderTestRule<MainActivity> mRenderRule =
            new RenderTestRule<>(MainActivity.class);

    private UiDevice mDevice;


    @Before
    public void before(){
        mRenderRule.log("before test");
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }


    @Test
    public void setUpTest() throws RemoteException, UiObjectNotFoundException {
        mRenderRule.log("/**  rotate de mobile, for check lifeCycle**/");
        HelperTest.delaySeconds(10);
    }

    @After
    public void after(){
        mRenderRule.log("after test");
    }


}
