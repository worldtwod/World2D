package com.titicolab.mock.cases;

import android.app.Activity;
import androidx.annotation.CallSuper;
import android.view.MotionEvent;

import com.titicolab.mock.MockActivity;
import com.titicolab.mock.rule.RenderTestRule;
import com.titicolab.nanux.core.GraphicContext;
import com.titicolab.nanux.core.ObservableRenderer;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.DisplayInfo;
import com.titicolab.nanux.util.FlagSync;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.puppeteer.GraphicActivity;
import com.titicolab.puppeteer.util.LogHelper;
import org.junit.Rule;

/**
 * Created by campino on 11/11/2016.
 *
 */

public class GraphicsTestCase implements ObservableRenderer.Renderer, ObservableInput.InputListener {

    @Rule
    public RenderTestRule<GraphicActivity> mRenderRule =
            RenderTestRule.getBuilder()
                    .setLaunchActivity(MockActivity.class)
                    .setObserverRender(this)
                    .setObserverInput(this)
                    .build();

    private FlagSync mFlagTouch = new FlagSync();

    final protected LogHelper log = new LogHelper(this,"GraphicActivity");

    protected GraphicContext mGraphicContext;

    @Override
    public void onSurfaceCreated(GraphicContext game, GPUInfo eglConfig) {
            mGraphicContext =game;
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        //log.debug(mGraphicContext.getDisplayInfo().toString());
    }

    @Override
    public void onDrawFrame() {

    }

    @CallSuper
    @Override
    public boolean onTouch(ObservableInput.Event event) {
        if(event.getAction()== MotionEvent.ACTION_UP)
            mFlagTouch.assertFlag();
        return false;
    }

    @Override
    public boolean onKey(int keyEvent) {
        return false;
    }


    public boolean waitTouchSeconds(int seconds){
        boolean result = mFlagTouch.waitSyncSeconds(seconds);
        mFlagTouch = new FlagSync();
        return result;
    }


    public GraphicContext getGameContext() {
        return mGraphicContext;
    }

    public DisplayInfo getDisplayInfo(){
        return mGraphicContext.getDisplayInfo();
    }

    public Activity getActivity(){
        return mRenderRule.getActivity();
    }
}
