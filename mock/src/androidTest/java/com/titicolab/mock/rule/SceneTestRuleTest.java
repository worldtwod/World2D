package com.titicolab.mock.rule;

import com.titicolab.mock.sample.SampleWorld2D;

import org.junit.Rule;
import org.junit.Test;

public class SceneTestRuleTest {

    @Rule
    public SceneTestRule rule = new SceneTestRule();

    @Test
    public void testStart(){
        rule.syncPlay(new SampleWorld2D());
    }
}