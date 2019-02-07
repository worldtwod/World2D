package com.titicolab.nanux.objects.base;

import com.titicolab.nanux.touch.ObservableInput;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class UiObjectTest {

    @Mock
    private ObservableInput.Event mEvent;

    @Test
    public void checkIsTouchingFalseTest() {
        Mockito.when(mEvent.getUiX()).thenReturn(10f);
        Mockito.when(mEvent.getUiY()).thenReturn(100f);
        TestUiObject testUiObject = new TestUiObject();
        boolean results=testUiObject.checkIsTouching(mEvent);
        Assert.assertTrue(!results);
    }

    @Test
    public void checkIsTouchingTrueTest() {
        Mockito.when(mEvent.getUiX()).thenReturn(86f);
        Mockito.when(mEvent.getUiY()).thenReturn(210f);
        TestUiObject testUiObject = new TestUiObject();
        boolean results=testUiObject.checkIsTouching(mEvent);
        Assert.assertTrue(results);
    }

    /**
     * Mocking the a new UiObject
     */
    public static class TestUiObject extends UiObject{
        private static final float OBJECT_WIDTH = 30;
        private static final float OBJECT_HEIGHT = 20;
        private static final float OBJECT_X = 100;
        private static final float OBJECT_Y = 200;

        @Override
        public void onCreated() {
        }
        @Override
        public float getWidth() {
            return OBJECT_WIDTH;
        }
        @Override
        public float getHeight() {
            return OBJECT_HEIGHT;
        }
        @Override
        public float getX() {
            return OBJECT_X;
        }

        @Override
        public float getY() {
            return OBJECT_Y;
        }
    }

}