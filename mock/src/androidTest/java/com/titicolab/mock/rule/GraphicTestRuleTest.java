package com.titicolab.mock.rule;

import com.titicolab.nanux.core.GraphicContext;
import com.titicolab.nanux.core.ObservableRenderer;
import com.titicolab.nanux.util.FlagSync;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.puppeteer.util.LogHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


public class GraphicTestRuleTest implements ObservableRenderer.Renderer {

    private FlagSync flagDraw = new FlagSync();

    private LogHelper log;

    @Rule
    public GraphicTestRule rule = new GraphicTestRule.Builder()
            .setStartActivity(true)
            .setObserverRender(this)
            .build();

    @Before
    public void before(){
        log = new LogHelper(this,GraphicTestRule.TEST_LOG_LABEL);
        log.debug("before start test");
    }

    @Test
    public void observableTestRuleStartTest(){
        log.debug("observableTestRuleStartTest()");
        Assert.assertTrue(flagDraw.waitSyncMinutes(5000));
        Assert.assertNotNull(rule.getGraphicContext());
    }

    @Override
    public void onSurfaceCreated(GraphicContext game, GPUInfo eglConfig) {

    }

    @Override
    public void onSurfaceChanged(int width, int height) {

    }

    @Override
    public void onDrawFrame() {
            flagDraw.assertFlag();
    }
}