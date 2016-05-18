package com.carlos.capstone.transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;

import com.carlos.capstone.R;

/**
 * Created by Carlos on 12/02/2016.
 */
public class TransitionUtils {

    /**
     * Returns a modified enter transition that excludes the navigation bar and status
     * bar as targets during the animation. This ensures that the navigation bar and
     * status bar won't appear to "blink" as they fade in/out during the transition.
     */
    @TargetApi(19)
    public static Transition makeEnterTransition() {
        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(R.id.chart,true);
        return fade;
    }

    @TargetApi(21)
    public static Transition makeSharedElementEnterTransition(Context context) {
        TransitionSet set = new TransitionSet();
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);


        Transition changeBounds = new ChangeBounds();
        changeBounds.addTarget(R.id.tickerName);
        set.addTransition(changeBounds);

        Transition textSize = new TextSizeTransition();
        textSize.addTarget(R.id.tickerName);
        set.addTransition(textSize);

        return set;
    }
}
