package com.google.android.material.progressindicator;

import androidx.core.math.MathUtils;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat$AnimationCallback;
import com.google.android.material.progressindicator.DrawingDelegate;
import java.util.ArrayList;
import java.util.List;

abstract class IndeterminateAnimatorDelegate {
    protected final List activeIndicators = new ArrayList();
    protected IndeterminateDrawable drawable;

    /* access modifiers changed from: package-private */
    public abstract void cancelAnimatorImmediately();

    public abstract void registerAnimatorsCompleteCallback(Animatable2Compat$AnimationCallback animatable2Compat$AnimationCallback);

    /* access modifiers changed from: package-private */
    public abstract void requestCancelAnimatorAfterCurrentCycle();

    /* access modifiers changed from: package-private */
    public abstract void resetPropertiesForNewStart();

    /* access modifiers changed from: package-private */
    public abstract void setAnimationFraction(float f);

    /* access modifiers changed from: package-private */
    public abstract void startAnimator();

    public abstract void unregisterAnimatorsCompleteCallback();

    protected IndeterminateAnimatorDelegate(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            this.activeIndicators.add(new DrawingDelegate.ActiveIndicator());
        }
    }

    /* access modifiers changed from: protected */
    public void registerDrawable(IndeterminateDrawable indeterminateDrawable) {
        this.drawable = indeterminateDrawable;
    }

    /* access modifiers changed from: protected */
    public float getFractionInRange(int i, int i2, int i3) {
        return MathUtils.clamp(((float) (i - i2)) / ((float) i3), 0.0f, 1.0f);
    }
}
