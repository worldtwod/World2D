package com.titicolab.mock.puppeteer;

import com.titicolab.mock.rule.GraphicTestRule;
import com.titicolab.mock.utils.ActionHelper;
import com.titicolab.nanux.objects.base.GameObject;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.touch.TouchManager;
import com.titicolab.nanux.util.FlagSync;
import com.titicolab.puppeteer.AndroidTouchManager;
import com.titicolab.puppeteer.util.LogHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;


/**
 * Functional test
 * This test te event in TouchManager implementation, not it need do click in scree,
 * with x pixel<500
 */
public class TouchManagerTest implements ObservableInput.InputListener,
        TouchManager.TouchActionListener{

    @Rule
    public GraphicTestRule rule = new GraphicTestRule.Builder()
            .setObserverInput(this)
            .build();

    private FlagSync onUpFlag = new FlagSync();
    private FlagSync onMoveFlag= new FlagSync();
    private FlagSync onDownFlag= new FlagSync();
    private AndroidTouchManager touchManagerTest;
    private LogHelper log = new LogHelper(this,"LogTest");
    private ObservableInput.Event touchEvent;


    @Before
    public void before(){
        touchManagerTest = new AndroidTouchManager(new MockTouchableTrue());
    }

    @Override
    public boolean onTouch(ObservableInput.Event event) {
        touchEvent = event;
        logEvent(touchEvent);
        touchManagerTest.onTouch(event);
        return false;
    }

    @Override
    public boolean onKey(int keyEvent) {
        return false;
    }

    @Test
    public void setTouchActionListenerTest(){
        touchManagerTest.setTouchActionListener(this);
        onView(withContentDescription("graphic_surface")).
                perform(ActionHelper.clickXY(50,200)).
                check(matches(isDisplayed()));
        Assert.assertTrue(onUpFlag.waitSyncSeconds(10));
        Assert.assertTrue(onDownFlag.waitSyncSeconds(10));
    }

    @Test
    public void setClickListenerTest(){
        final FlagSync onClick = new FlagSync();
        touchManagerTest.setClickListener(new TouchManager.ClickListener() {
            @Override
            public void onClickObject(GameObject.Touchable object) {
                log.debug("onClickObject");
                onClick.assertFlag();
            }
        });
        onView(withContentDescription("graphic_surface")).
                perform(ActionHelper.clickXY(50,200)).
                check(matches(isDisplayed()));
        Assert.assertTrue(onClick.waitSyncSeconds(10));
    }


    @Test
    public void setDoubleClickListenerTest(){
        final FlagSync onClick = new FlagSync();
        touchManagerTest.setDoubleClickListener(new TouchManager.DoubleClickListener() {
            @Override
            public void onDoubleClickObject(GameObject.Touchable object) {
                log.debug("onDoubleClickObject");
                onClick.assertFlag();
            }
        });
        onView(withContentDescription("graphic_surface")).
                perform(ActionHelper.clickXY(50,200)).
                check(matches(isDisplayed()));
        onView(withContentDescription("graphic_surface")).
                perform(ActionHelper.clickXY(50,200)).
                check(matches(isDisplayed()));
        Assert.assertTrue(onClick.waitSyncSeconds(10));
    }


    @Override
    public void onDown(GameObject.Touchable object) {
        log.debug("onDown");
        onDownFlag.assertFlag();
    }

    @Override
    public void onMove(GameObject.Touchable object, boolean touching){
        log.debug("onMove");
        onMoveFlag.assertFlag();
    }

    @Override
    public void onUp(GameObject.Touchable object, boolean touching) {
        log.debug("onUp");
        onUpFlag.assertFlag();
    }



    public static class  MockTouchableTrue implements GameObject.Touchable{
        @Override
        public boolean checkIsTouching(ObservableInput.Event input) {
            return input.getPixelX()<500;
        }
    }

    private void logEvent(ObservableInput.Event event) {
        String action   = "Action:" + event.getActionName();
        String pointer  = " Pointer:" + event.getPointerId();
        String position = " pxX:" + event.getPixelX()+ " pxY:" + event.getPixelY();
        log.debug(action+pointer+position);
    }

}
