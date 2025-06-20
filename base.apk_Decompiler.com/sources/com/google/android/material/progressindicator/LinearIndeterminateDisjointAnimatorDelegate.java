package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.Property;
import android.view.animation.Interpolator;
import androidx.core.math.MathUtils;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat$AnimationCallback;
import androidx.vectordrawable.graphics.drawable.AnimationUtilsCompat;
import com.google.android.material.R$anim;
import com.google.android.material.progressindicator.DrawingDelegate;

final class LinearIndeterminateDisjointAnimatorDelegate extends IndeterminateAnimatorDelegate {
    private static final Property ANIMATION_FRACTION = new Property(Float.class, "animationFraction") {
        public Float get(LinearIndeterminateDisjointAnimatorDelegate linearIndeterminateDisjointAnimatorDelegate) {
            return Float.valueOf(linearIndeterminateDisjointAnimatorDelegate.getAnimationFraction());
        }

        public void set(LinearIndeterminateDisjointAnimatorDelegate linearIndeterminateDisjointAnimatorDelegate, Float f) {
            linearIndeterminateDisjointAnimatorDelegate.setAnimationFraction(f.floatValue());
        }
    };
    private static final int[] DELAY_TO_MOVE_SEGMENT_ENDS = {1267, 1000, 333, 0};
    private static final int[] DURATION_TO_MOVE_SEGMENT_ENDS = {533, 567, 850, 750};
    private float animationFraction;
    private ObjectAnimator animator;
    Animatable2Compat$AnimationCallback animatorCompleteCallback = null;
    /* access modifiers changed from: private */
    public final BaseProgressIndicatorSpec baseSpec;
    private ObjectAnimator completeEndAnimator;
    /* access modifiers changed from: private */
    public boolean dirtyColors;
    /* access modifiers changed from: private */
    public int indicatorColorIndex = 0;
    private final Interpolator[] interpolatorArray;

    public LinearIndeterminateDisjointAnimatorDelegate(Context context, LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        super(2);
        this.baseSpec = linearProgressIndicatorSpec;
        this.interpolatorArray = new Interpolator[]{AnimationUtilsCompat.loadInterpolator(context, R$anim.linear_indeterminate_line1_head_interpolator), AnimationUtilsCompat.loadInterpolator(context, R$anim.linear_indeterminate_line1_tail_interpolator), AnimationUtilsCompat.loadInterpolator(context, R$anim.linear_indeterminate_line2_head_interpolator), AnimationUtilsCompat.loadInterpolator(context, R$anim.linear_indeterminate_line2_tail_interpolator)};
    }

    public void startAnimator() {
        maybeInitializeAnimators();
        resetPropertiesForNewStart();
        this.animator.start();
    }

    private void maybeInitializeAnimators() {
        if (this.animator == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[]{0.0f, 1.0f});
            this.animator = ofFloat;
            ofFloat.setDuration((long) (this.baseSpec.indeterminateAnimatorDurationScale * 1800.0f));
            this.animator.setInterpolator((TimeInterpolator) null);
            this.animator.setRepeatCount(-1);
            this.animator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationRepeat(Animator animator) {
                    super.onAnimationRepeat(animator);
                    LinearIndeterminateDisjointAnimatorDelegate linearIndeterminateDisjointAnimatorDelegate = LinearIndeterminateDisjointAnimatorDelegate.this;
                    int unused = linearIndeterminateDisjointAnimatorDelegate.indicatorColorIndex = (linearIndeterminateDisjointAnimatorDelegate.indicatorColorIndex + 1) % LinearIndeterminateDisjointAnimatorDelegate.this.baseSpec.indicatorColors.length;
                    boolean unused2 = LinearIndeterminateDisjointAnimatorDelegate.this.dirtyColors = true;
                }
            });
        }
        if (this.completeEndAnimator == null) {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[]{1.0f});
            this.completeEndAnimator = ofFloat2;
            ofFloat2.setDuration((long) (this.baseSpec.indeterminateAnimatorDurationScale * 1800.0f));
            this.completeEndAnimator.setInterpolator((TimeInterpolator) null);
            this.completeEndAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    LinearIndeterminateDisjointAnimatorDelegate.this.cancelAnimatorImmediately();
                    LinearIndeterminateDisjointAnimatorDelegate linearIndeterminateDisjointAnimatorDelegate = LinearIndeterminateDisjointAnimatorDelegate.this;
                    Animatable2Compat$AnimationCallback animatable2Compat$AnimationCallback = linearIndeterminateDisjointAnimatorDelegate.animatorCompleteCallback;
                    if (animatable2Compat$AnimationCallback != null) {
                        animatable2Compat$AnimationCallback.onAnimationEnd(linearIndeterminateDisjointAnimatorDelegate.drawable);
                    }
                }
            });
        }
    }

    public void cancelAnimatorImmediately() {
        ObjectAnimator objectAnimator = this.animator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    public void requestCancelAnimatorAfterCurrentCycle() {
        ObjectAnimator objectAnimator = this.completeEndAnimator;
        if (objectAnimator != null && !objectAnimator.isRunning()) {
            cancelAnimatorImmediately();
            if (this.drawable.isVisible()) {
                this.completeEndAnimator.setFloatValues(new float[]{this.animationFraction, 1.0f});
                this.completeEndAnimator.setDuration((long) ((1.0f - this.animationFraction) * 1800.0f));
                this.completeEndAnimator.start();
            }
        }
    }

    public void registerAnimatorsCompleteCallback(Animatable2Compat$AnimationCallback animatable2Compat$AnimationCallback) {
        this.animatorCompleteCallback = animatable2Compat$AnimationCallback;
    }

    public void unregisterAnimatorsCompleteCallback() {
        this.animatorCompleteCallback = null;
    }

    private void updateSegmentPositions(int i) {
        for (int i2 = 0; i2 < this.activeIndicators.size(); i2++) {
            DrawingDelegate.ActiveIndicator activeIndicator = (DrawingDelegate.ActiveIndicator) this.activeIndicators.get(i2);
            int[] iArr = DELAY_TO_MOVE_SEGMENT_ENDS;
            int i3 = i2 * 2;
            int i4 = iArr[i3];
            int[] iArr2 = DURATION_TO_MOVE_SEGMENT_ENDS;
            activeIndicator.startFraction = MathUtils.clamp(this.interpolatorArray[i3].getInterpolation(getFractionInRange(i, i4, iArr2[i3])), 0.0f, 1.0f);
            int i5 = i3 + 1;
            activeIndicator.endFraction = MathUtils.clamp(this.interpolatorArray[i5].getInterpolation(getFractionInRange(i, iArr[i5], iArr2[i5])), 0.0f, 1.0f);
        }
    }

    private void maybeUpdateSegmentColors() {
        if (this.dirtyColors) {
            for (DrawingDelegate.ActiveIndicator activeIndicator : this.activeIndicators) {
                activeIndicator.color = this.baseSpec.indicatorColors[this.indicatorColorIndex];
            }
            this.dirtyColors = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void resetPropertiesForNewStart() {
        this.indicatorColorIndex = 0;
        for (DrawingDelegate.ActiveIndicator activeIndicator : this.activeIndicators) {
            activeIndicator.color = this.baseSpec.indicatorColors[0];
        }
    }

    /* access modifiers changed from: private */
    public float getAnimationFraction() {
        return this.animationFraction;
    }

    /* access modifiers changed from: package-private */
    public void setAnimationFraction(float f) {
        this.animationFraction = f;
        updateSegmentPositions((int) (f * 1800.0f));
        maybeUpdateSegmentColors();
        this.drawable.invalidateSelf();
    }
}
