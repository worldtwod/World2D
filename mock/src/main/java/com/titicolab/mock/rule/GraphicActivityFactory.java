package com.titicolab.mock.rule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MotionEvent;

import com.titicolab.nanux.core.GraphicContext;
import com.titicolab.nanux.core.ObservableRenderer;
import com.titicolab.nanux.test.Monitor;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.FlagSync;
import com.titicolab.nanux.util.GPUInfo;
import com.titicolab.puppeteer.util.LogHelper;

import androidx.annotation.CallSuper;
import androidx.test.runner.intercepting.SingleActivityFactory;


@SuppressWarnings("FieldCanBeLocal")
public class GraphicActivityFactory extends
        SingleActivityFactory<GraphicTestActivity> implements
        Monitor.OnGraphicContextCreated, ObservableRenderer.Renderer, ObservableInput.InputListener {

    private  final ObservableRenderer.Renderer  mObserverRender;
    private  final ObservableInput.InputListener mObserverInput;
    private  final ObserverGraphicContext.ContextCreated mObserverContextCreated;
    private  final ObserverGraphicContext.SurfaceCreated mObserverSurfaceCreated;
    private  final ObserverGraphicContext.SurfaceChanged mObserverSurfaceChanged;
    private  final ObserverGraphicContext.DrawFrame      mObserverDrawFrame;

    private final LogHelper log;
    private FlagSync mFlagTouch;
    private final FlagSync mFlagStartDraw;
    private long mFrames;
    private GraphicContext mGraphicContext;
    private GraphicTestActivity mGraphicTestActivity;

    GraphicActivityFactory(ObservableRenderer.Renderer mObserverRender,
                           ObservableInput.InputListener mObserverInput,
                           ObserverGraphicContext.ContextCreated observerContextCreated,
                           ObserverGraphicContext.SurfaceCreated observerSurfaceCreated,
                           ObserverGraphicContext.SurfaceChanged observerSurfaceChanged,
                           ObserverGraphicContext.DrawFrame observerDrawFrame) {
        super(GraphicTestActivity.class);
        this.mObserverRender = mObserverRender;
        this.mObserverInput = mObserverInput;
        this.mObserverContextCreated = observerContextCreated;
        this.mObserverSurfaceCreated = observerSurfaceCreated;
        this.mObserverSurfaceChanged = observerSurfaceChanged;
        this.mObserverDrawFrame = observerDrawFrame;

        mFlagStartDraw = new FlagSync();
        log = new LogHelper(this,GraphicTestRule.TEST_LOG_LABEL);
        log.debug("GraphicActivityFactory()");
        mFlagTouch = new FlagSync();

    }

    FlagSync getFlagStartDraw() {
        return mFlagStartDraw;
    }

    GraphicContext getGraphicContext() {
        return mGraphicContext;
    }

    @SuppressLint("VisibleForTests")
    @Override
    protected GraphicTestActivity create(Intent intent) {
        mGraphicTestActivity = new GraphicTestActivity();
        mGraphicTestActivity.setGraphicContextMonitor(this);
        log.debug("create(), instantiate the GraphicTestActivity");
        return mGraphicTestActivity;
    }

    @Override
    public void onGraphicContextCreated(GraphicContext game) {
        log.debug("onGraphicContextCreated()");
        mGraphicContext = game;
        game.getObservableRenderer().add(this);
        game.getObservableInput().add(this);
        if(mObserverContextCreated!=null)
            mObserverContextCreated.onGraphicContextCreated(game);
    }

    /**               ObservableRenderer          **/
    @Override
    public void onSurfaceCreated(GraphicContext game, GPUInfo eglConfig) {
        log.debug("onSurfaceCreated()");
        if(mObserverRender!=null){
            mObserverRender.onSurfaceCreated(game,eglConfig);
        }

        if(mObserverSurfaceCreated!=null)
            mObserverSurfaceCreated.onSurfaceCreated(game,eglConfig);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        log.debug("onSurfaceChanged()");
        if(mObserverRender!=null){
            mObserverRender.onSurfaceChanged(width,height);
        }
        if(mObserverSurfaceChanged!=null){
            mObserverSurfaceChanged.onSurfaceChanged(width,height);
        }
    }

    @Override
    public void onDrawFrame() {
        if(mFrames%60==0) {
            log.debug("Frame 60");
            mFlagStartDraw.assertFlag();
        }
        if(mObserverDrawFrame!=null)
            mObserverDrawFrame.onDrawFrame(mGraphicTestActivity.getController().getDrawTools());

        mFrames++;
    }

    /**                ObservableInput          **/

    @Override
    public boolean onKey(int keyEvent) {
        return false;
    }

    @CallSuper
    @Override
    public boolean onTouch(ObservableInput.Event event) {
        if(event.getAction()== MotionEvent.ACTION_UP)
            mFlagTouch.assertFlag();
        return false;
    }

    public boolean waitTouchSeconds(int seconds){
        boolean result = mFlagTouch.waitSyncSeconds(seconds);
        mFlagTouch = new FlagSync();
        return result;
    }

}
