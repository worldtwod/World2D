/*
 * Copyright  2017   Fredy Campiño
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.titicolab.puppeteer.ui;
import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.nanux.animation.ClipMap;
import com.titicolab.nanux.objects.base.Animated;
import com.titicolab.nanux.objects.base.SceneLayer;
import com.titicolab.nanux.objects.map.MapItem;
import com.titicolab.nanux.objects.map.MapObjects;
import com.titicolab.nanux.touch.TouchManager;
import com.titicolab.puppeteer.R;

/**
 * Created by campino on 12/12/2017.
 *
 */

public class JoystickLayer extends SceneLayer implements  TouchManager.ClickListener {

    private static final int BUTTON_UP = 1;
    private static final int BUTTON_DOWN = 2;
    private static final int BUTTON_LEFT = 1001;
    private static final int BUTTON_RIGHT = 1002;
    private static final int BUTTON_TOP = 1003;
    private static final int BUTTON_BOTTOM = 1004;

    private Button left;
    private Button right;
    private Button top;
    private Button bottom;

    public interface OnClickJoystick {
        void onLeft();
        void onRight();
        void onTop();
        void onBottom();
    }

    private OnClickJoystick onClickJoystickListener;

    @Override
    public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
        ClipMap.Area area = new ClipMap.Area(0,0,110*4,110*2);
        return builder
                .sequence("button")
                .resources(R.drawable.ui_map)
                .clip(BUTTON_UP).area(area).grid(4,2).cells(2)
                .clip(BUTTON_DOWN).area(area).grid(4,2).cells(0)
                .build();
    }

    @Override
    protected MapObjects onDefineMapObjects() {
        return  new MapObjects.Builder()
                .setName("ui_buttons")
                .item(new MapItem(Button.class,BUTTON_LEFT,new Animated.ParamsAnimation("button",BUTTON_UP)))
                .item(new MapItem(Button.class,BUTTON_RIGHT,new Animated.ParamsAnimation("button",BUTTON_UP)))
                .item(new MapItem(Button.class,BUTTON_TOP,new Animated.ParamsAnimation("button",BUTTON_UP)))
                .item(new MapItem(Button.class,BUTTON_BOTTOM,new Animated.ParamsAnimation("button",BUTTON_UP)))
                .build();
    }

    @Override
    protected void onGroupObjectsCreated() {
        super.onGroupObjectsCreated();
        left =  findById(Button.class,BUTTON_LEFT);
        right =  findById(Button.class,BUTTON_RIGHT);
        top =    findById(Button.class,BUTTON_TOP);
        bottom =  findById(Button.class,BUTTON_BOTTOM);

        float sizeButton = 110;
        float leftPosition = getCameraUi().getWidth()-sizeButton*2;
        float topPosition = sizeButton*3;

        top.setPosition(leftPosition + sizeButton/2,topPosition/2+sizeButton/2);
        left.setPosition(leftPosition ,sizeButton);
        right.setPosition(leftPosition+ sizeButton*1.5f,topPosition/2);
        bottom.setPosition(leftPosition +sizeButton,sizeButton/2);

        top.touchManager.setClickListener(this);
    }

    public void setOnClickJoystickListener(OnClickJoystick onClickJoystickListener){
        this.onClickJoystickListener = onClickJoystickListener;
    }

    @Override
    public void onClickObject(Touchable object) {
        Button button= (Button) object;
        if(button.getId()==BUTTON_LEFT){
            if(onClickJoystickListener !=null)
                onClickJoystickListener.onLeft();
        }else if(button.getId()==BUTTON_RIGHT){
            if(onClickJoystickListener !=null)
                onClickJoystickListener.onRight();
        }else if(button.getId()==BUTTON_TOP){
            if(onClickJoystickListener !=null)
                onClickJoystickListener.onTop();
        }else if (button.getId()==BUTTON_BOTTOM){
            if(onClickJoystickListener !=null)
                onClickJoystickListener.onBottom();
        }
    }

    public static class Button extends TouchableObject {
        @Override
        public void onDown(Touchable object) {
            getAnimator().playAndStop(BUTTON_DOWN);
        }

        @Override
        public void onMove(Touchable object, boolean touching) {
            getAnimator().playAndStop(touching?BUTTON_DOWN:BUTTON_UP);
        }

        @Override
        public void onUp(Touchable object, boolean touching) {
            getAnimator().playAndStop(BUTTON_UP);
        }
    }
}
