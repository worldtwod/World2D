package com.titicolab.nanux.animation;

import android.support.test.runner.AndroidJUnit4;

import com.titicolab.android.GameActivity;
import com.titicolab.nanux.util.ObjectSync;
import com.titicolab.mock.rule.RenderTestRule;
import com.titicolab.nanux.MainActivity;
import com.titicolab.nanux.util.GPUInfo;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by campino on 11/11/2016.
 *
 */

@RunWith(AndroidJUnit4.class)
public class RunnerTaskTest implements ObservableRenderer.Renderer {

    @Rule
    public RenderTestRule<GameActivity> mRenderRule =
            RenderTestRule.getBuilder()
                    .setLaunchActivity(MainActivity.class)
                    .setObserverRender(this)
                    .build();

    private RunnerTask mRunnerTask;
    private ObjectSync<String> result;


    @Override
    public void onSurfaceCreated(GameContext game, GPUInfo eglConfig) {
        mRunnerTask = new RunnerTask(Thread.currentThread());
    }

    @Override
    public void onSurfaceChanged(int width, int height) {

    }

    @Override
    public void onDrawFrame() {
        mRunnerTask.update();
    }


    @Test
    public void testRunAndWait(){
        result = new ObjectSync<>();
        mRunnerTask.runAndWait(new RunnableTask() {
            @Override
            public void run() {
                result.setResult(Thread.currentThread().getName());
            }
        });
        Assert.assertEquals(mRunnerTask.getRunnerThread().getName(),result.getResults());

        result = new ObjectSync<>();
        mRunnerTask.runAndWait(new RunnableTask() {
            @Override
            public void run() {
                result.setResult(Thread.currentThread().getName());
            }
        });
        Assert.assertEquals(mRunnerTask.getRunnerThread().getName(),result.getResults());
    }




}