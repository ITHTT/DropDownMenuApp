package com.htt.dropdownmenu.views.widgets.goodcar;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by HTT on 2016/4/26.
 */
public class PathAnimation extends Animation {

    private PathMeasure measure;
    private float[] pos = new float[2];

    public PathAnimation(Path path) {
        measure = new PathMeasure(path, false);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        measure.getPosTan(measure.getLength() * interpolatedTime, pos, null);
        t.getMatrix().setTranslate(pos[0], pos[1]);

    }
}
