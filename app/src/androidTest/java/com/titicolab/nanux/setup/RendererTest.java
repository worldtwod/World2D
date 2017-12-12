package com.titicolab.nanux.setup;

import android.support.test.runner.AndroidJUnit4;

import com.titicolab.mock.rule.RenderTestRule;
import com.titicolab.nanux.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by campino on 21/11/2017.
 *
 */
@RunWith(AndroidJUnit4.class)
public class RendererTest {

    @Rule
    public RenderTestRule<MainActivity> mRenderRule =
            new RenderTestRule<>(MainActivity.class, render, event);


    @Before
    public void before(){
        mRenderRule.log("before");
    }


    @Test
    public void setUpTest(){
        mRenderRule.log("setUpTest");
    }

    @After
    public void after(){
        mRenderRule.log("after");
    }


}
