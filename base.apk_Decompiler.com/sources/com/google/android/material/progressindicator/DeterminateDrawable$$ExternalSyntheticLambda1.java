package com.google.android.material.progressindicator;

import android.animation.ValueAnimator;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DeterminateDrawable$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ DeterminateDrawable f$0;

    public /* synthetic */ DeterminateDrawable$$ExternalSyntheticLambda1(DeterminateDrawable determinateDrawable) {
        this.f$0 = determinateDrawable;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.activeIndicator.amplitudeFraction = this.f$0.amplitudeInterpolator.getInterpolation(this.f$0.amplitudeAnimator.getAnimatedFraction());
    }
}
