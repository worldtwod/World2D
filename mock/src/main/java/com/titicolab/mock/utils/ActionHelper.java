package com.titicolab.mock.utils;

import android.app.Activity;
import android.view.View;

import com.titicolab.nanux.util.FlagSync;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

public class ActionHelper {

    public static ViewAction clickXY(final int x, final int y){
        return new GeneralClickAction(
                Tap.SINGLE,
                new CoordinatesProvider() {
                    @Override
                    public float[] calculateCoordinates(View view) {
                        final int[] screenPos = new int[2];
                        view.getLocationOnScreen(screenPos);
                        final float screenX = screenPos[0] + x;
                        final float screenY = screenPos[1] + y;
                        return new float[]{screenX, screenY};
                    }
                },
                Press.FINGER,0,0);
    }

    public static void screenOff(final Activity activity){
        final FlagSync event = new FlagSync();
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                getInstrumentation().callActivityOnPause(activity);
                getInstrumentation().callActivityOnStop(activity);
                event.assertFlag();
            }
        });
        event.waitSyncSeconds(2);
    }

    public static void screenOn(final Activity activity){
        final FlagSync event = new FlagSync();
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                getInstrumentation().callActivityOnRestart(activity);
                event.assertFlag();
            }
        });
        event.waitSyncSeconds(2);
    }
}
