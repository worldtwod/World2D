package com.titicolab.mock.cases;

import android.app.Activity;
import android.support.annotation.CallSuper;
import android.support.test.runner.AndroidJUnit4;
import android.view.MotionEvent;

import com.titicolab.puppeteer.GameActivity;
import com.titicolab.puppeteer.util.LogHelper;
import com.titicolab.mock.MockActivity;
import com.titicolab.mock.rule.RenderTestRule;
import com.titicolab.nanux.core.GameContext;
import com.titicolab.nanux.core.ObservableRenderer;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.DisplayInfo;
import com.titicolab.nanux.util.FlagSync;
import com.titicolab.nanux.util.GPUInfo;

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created by campino on 11/11/2016.
 *
 */
@RunWith(AndroidJUnit4.class)
public class GraphicsTestCase implements ObservableRenderer.Renderer, ObservableInput.InputListener {

    @Rule
    public RenderTestRule<GameActivity> mRenderRule =
            RenderTestRule.getBuilder()
                    .setLaunchActivity(MockActivity.class)
                    .setObserverRender(this)
                    .setObserverInput(this)
                    .build();

    private FlagSync mFlagTouch = new FlagSync();

    final protected LogHelper log = new LogHelper(this,"GameActivity");



    protected GameContext mGameContext;

    @Override
    public void onSurfaceCreated(GameContext game, GPUInfo eglConfig) {
            mGameContext=game;
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        //log.debug(mGameContext.getDisplayInfo().toString());
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


    public GameContext getGameContext() {
        return mGameContext;
    }

    public DisplayInfo getDisplayInfo(){
        return mGameContext.getDisplayInfo();
    }

    public Activity getActivity(){
        return mRenderRule.getActivity();
    }
}
