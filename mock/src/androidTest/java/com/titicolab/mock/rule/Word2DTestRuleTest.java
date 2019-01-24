package com.titicolab.mock.rule;

import org.junit.Rule;
import org.junit.Test;

public class Word2DTestRuleTest {

    @Rule
    public Word2DTestRule<Word2DTestRule.World> rule = new Word2DTestRule<>();

    @Test
    public void testStart(){
        rule.launchActivity(null);

    }

}