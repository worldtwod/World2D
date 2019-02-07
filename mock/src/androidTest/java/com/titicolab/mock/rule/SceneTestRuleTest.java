package com.titicolab.mock.rule;

import com.titicolab.mock.sample.SampleWorld2D;

import org.junit.Rule;
import org.junit.Test;

public class SceneTestRuleTest {

    @Rule
    public SceneTestRule sceneTestRule = new SceneTestRule();

    @Test
    public void testStart(){
        sceneTestRule.syncPlay(new SampleWorld2D());
    }
}