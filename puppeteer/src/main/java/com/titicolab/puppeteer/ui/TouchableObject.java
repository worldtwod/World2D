package com.titicolab.puppeteer.ui;

import com.titicolab.nanux.objects.base.UiObject;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.touch.TouchManager;
import com.titicolab.puppeteer.AndroidTouchManager;

public class TouchableObject extends UiObject implements TouchManager.TouchActionListener {
    public final TouchManager touchManager;

    public TouchableObject() {
        super();
        touchManager =  new AndroidTouchManager(this);
        setTouchable(true);
        setTouchManager(touchManager);
        touchManager.setTouchActionListener(this);
    }

    @Override
    public void onCreated(){
    }

    @Override
    protected boolean onTouch(ObservableInput.Event input) {
        return touchManager.onTouch(input);
    }

    @Override
    public void onDown(Touchable object) {

    }
    @Override
    public void onMove(Touchable object, boolean touching) {

    }
    @Override
    public void onUp(Touchable object, boolean touching) {

    }
}
