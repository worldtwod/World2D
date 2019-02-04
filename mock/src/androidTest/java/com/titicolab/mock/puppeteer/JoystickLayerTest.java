package com.titicolab.mock.puppeteer;

import com.titicolab.mock.rule.SceneTestRule;
import com.titicolab.puppeteer.ui.JoystickLayer;

import org.junit.Rule;
import org.junit.Test;

public class JoystickLayerTest {

    @Rule
    public SceneTestRule sceneTestRule = new SceneTestRule();

    @Test
    public void onGroupObjectsCreated() {
        JoystickLayer layer = (JoystickLayer) sceneTestRule.syncPlay(JoystickLayer.class);
    }
}