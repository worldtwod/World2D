package com.titicolab.mock.rule;

import com.titicolab.mock.R;
import com.titicolab.nanux.animation.Animation;
import com.titicolab.nanux.animation.AnimationSheet;

import org.junit.Rule;
import org.junit.Test;

public class AnimationTestRuleTest {


    @Rule
    public AnimationTestRule ruleAnimation =
            new AnimationTestRule(new MockAnimation(),"button", 4);

    @Test
    public void  animationTestRuleTest() {

        ruleAnimation.getAnimator().setSpeed(1);
        //ruleAnimation.getAnimator().playAndRepeat(2);
        //ruleAnimation.getAnimator().playAndRepeat(3);

    }


    public static class MockAnimation implements Animation.DefineAnimationSheet {
        @Override
        public AnimationSheet onDefineAnimations(AnimationSheet.Builder builder) {
            return builder
                    .sequence("digits")
                    .resources(R.drawable.test_map)
                    .clip(2)
                    .grid(8,4)
                    .cells(new int[]{0,1,2,3,4,5,6,7,8,9})
                    .clip(3)
                    .grid(8,4)
                    .cells(new int[]{16,17,16})
                    .sequence("button")
                    .clip(4)
                    .area(4*128,128*2,110*4,110*2)
                    .grid(4,2)
                    .cells(new int []{0,1,2})
                    .build();
        }
    }
}