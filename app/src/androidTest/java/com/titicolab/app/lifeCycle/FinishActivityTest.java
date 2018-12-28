package com.titicolab.app.lifeCycle;

import android.os.RemoteException;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.titicolab.app.MainActivity;
import com.titicolab.mock.rule.RenderTestRule;
import com.titicolab.mock.tools.HelperTest;
import com.titicolab.puppeteer.view.GLGameView;

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
            new RenderTestRule<>(MainActivity.class);

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
