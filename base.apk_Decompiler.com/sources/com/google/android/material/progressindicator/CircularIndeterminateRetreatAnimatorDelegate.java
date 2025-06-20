package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.Property;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat$AnimationCallback;
import com.google.android.material.R$attr;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.math.MathUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.progressindicator.DrawingDelegate;

final class CircularIndeterminateRetreatAnimatorDelegate extends IndeterminateAnimatorDelegate {
    private static final Property ANIMATION_FRACTION;
    private static final Property COMPLETE_END_FRACTION;
    private static final TimeInterpolator DEFAULT_INTERPOLATOR = AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
    /* access modifiers changed from: private */
    public static final int[] DELAY_SPINS_IN_MS = {0, 1500, 3000, 4500};
    private static final float[] END_FRACTION_RANGE = {0.1f, 0.87f};
    private float animationFraction;
    private ObjectAnimator animator;
    Animatable2Compat$AnimationCallback animatorCompleteCallback = null;
    /* access modifiers changed from: private */
    public final BaseProgressIndicatorSpec baseSpec;
    private ObjectAnimator completeEndAnimator;
    private float completeEndFraction;
    /* access modifiers changed from: private */
    public int indicatorColorIndexOffset = 0;
    private final TimeInterpolator standardInterpolator;

    static {
        Class<Float> cls = Float.class;
        ANIMATION_FRACTION = new Property(cls, "animationFraction") {
            public Float get(CircularIndeterminateRetreatAnimatorDelegate circularIndeterminateRetreatAnimatorDelegate) {
                return Float.valueOf(circularIndeterminateRetreatAnimatorDelegate.getAnimationFraction());
            }

            public void set(CircularIndeterminateRetreatAnimatorDelegate circularIndeterminateRetreatAnimatorDelegate, Float f) {
                circularIndeterminateRetreatAnimatorDelegate.setAnimationFraction(f.floatValue());
            }
        };
        COMPLETE_END_FRACTION = new Property(cls, "completeEndFraction") {
            public Float get(CircularIndeterminateRetreatAnimatorDelegate circularIndeterminateRetreatAnimatorDelegate) {
                return Float.valueOf(circularIndeterminateRetreatAnimatorDelegate.getCompleteEndFraction());
            }

            public void set(CircularIndeterminateRetreatAnimatorDelegate circularIndeterminateRetreatAnimatorDelegate, Float f) {
                circularIndeterminateRetreatAnimatorDelegate.setCompleteEndFraction(f.floatValue());
            }
        };
    }

    public CircularIndeterminateRetreatAnimatorDelegate(Context context, CircularProgressIndicatorSpec circularProgressIndicatorSpec) {
        super(1);
        this.baseSpec = circularProgressIndicatorSpec;
        this.standardInterpolator = MotionUtils.resolveThemeInterpolator(context, R$attr.motionEasingStandardInterpolator, DEFAULT_INTERPOLATOR);
    }

    /* access modifiers changed from: package-private */
    public void startAnimator() {
        maybeInitializeAnimators();
        resetPropertiesForNewStart();
        this.animator.start();
    }

    private void maybeInitializeAnimators() {
        if (this.animator == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[]{0.0f, 1.0f});
            this.animator = ofFloat;
            ofFloat.setDuration((long) (this.baseSpec.indeterminateAnimatorDurationScale * 6000.0f));
            this.animator.setInterpolator((TimeInterpolator) null);
            this.animator.setRepeatCount(-1);
            this.animator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationRepeat(Animator animator) {
                    super.onAnimationRepeat(animator);
                    CircularIndeterminateRetreatAnimatorDelegate circularIndeterminateRetreatAnimatorDelegate = CircularIndeterminateRetreatAnimatorDelegate.this;
                    int unused = circularIndeterminateRetreatAnimatorDelegate.indicatorColorIndexOffset = (circularIndeterminateRetreatAnimatorDelegate.indicatorColorIndexOffset + CircularIndeterminateRetreatAnimatorDelegate.DELAY_SPINS_IN_MS.length) % CircularIndeterminateRetreatAnimatorDelegate.this.baseSpec.indicatorColors.length;
                }
            });
        }
        if (this.completeEndAnimator == null) {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, COMPLETE_END_FRACTION, new float[]{0.0f, 1.0f});
            this.completeEndAnimator = ofFloat2;
            ofFloat2.setDuration((long) (this.baseSpec.indeterminateAnimatorDurationScale * 500.0f));
            this.completeEndAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    CircularIndeterminateRetreatAnimatorDelegate.this.cancelAnimatorImmediately();
                    CircularIndeterminateRetreatAnimatorDelegate circularIndeterminateRetreatAnimatorDelegate = CircularIndeterminateRetreatAnimatorDelegate.this;
                    Animatable2Compat$AnimationCallback animatable2Compat$AnimationCallback = circularIndeterminateRetreatAnimatorDelegate.animatorCompleteCallback;
                    if (animatable2Compat$AnimationCallback != null) {
                        animatable2Compat$AnimationCallback.onAnimationEnd(circularIndeterminateRetreatAnimatorDelegate.drawable);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public void cancelAnimatorImmediately() {
        ObjectAnimator objectAnimator = this.animator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    /* access modifiers changed from: package-private */
    public void requestCancelAnimatorAfterCurrentCycle() {
        ObjectAnimator objectAnimator = this.completeEndAnimator;
        if (objectAnimator != null && !objectAnimator.isRunning()) {
            if (this.drawable.isVisible()) {
                this.completeEndAnimator.start();
            } else {
                cancelAnimatorImmediately();
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
        DrawingDelegate.ActiveIndicator activeIndicator = (DrawingDelegate.ActiveIndicator) this.activeIndicators.get(0);
        float f = this.animationFraction * 1080.0f;
        float f2 = 0.0f;
        for (int fractionInRange : DELAY_SPINS_IN_MS) {
            f2 += this.standardInterpolator.getInterpolation(getFractionInRange(i, fractionInRange, 500)) * 90.0f;
        }
        activeIndicator.rotationDegree = f + f2;
        float interpolation = this.standardInterpolator.getInterpolation(getFractionInRange(i, 0, 3000)) - this.standardInterpolator.getInterpolation(getFractionInRange(i, 3000, 3000));
        activeIndicator.startFraction = 0.0f;
        float[] fArr = END_FRACTION_RANGE;
        float lerp = MathUtils.lerp(fArr[0], fArr[1], interpolation);
        activeIndicator.endFraction = lerp;
        float f3 = this.completeEndFraction;
        if (f3 > 0.0f) {
            activeIndicator.endFraction = lerp * (1.0f - f3);
        }
    }

    private void maybeUpdateSegmentColors(int i) {
        int i2 = 0;
        while (true) {
            int[] iArr = DELAY_SPINS_IN_MS;
            if (i2 < iArr.length) {
                float fractionInRange = getFractionInRange(i, iArr[i2], 100);
                if (fractionInRange < 0.0f || fractionInRange > 1.0f) {
                    i2++;
                } else {
                    int i3 = i2 + this.indicatorColorIndexOffset;
                    int[] iArr2 = this.baseSpec.indicatorColors;
                    int length = i3 % iArr2.length;
                    ((DrawingDelegate.ActiveIndicator) this.activeIndicators.get(0)).color = ArgbEvaluatorCompat.getInstance().evaluate(this.standardInterpolator.getInterpolation(fractionInRange), Integer.valueOf(iArr2[length]), Integer.valueOf(iArr2[(length + 1) % iArr2.length])).intValue();
                    return;
                }
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void resetPropertiesForNewStart() {
        this.indicatorColorIndexOffset = 0;
        ((DrawingDelegate.ActiveIndicator) this.activeIndicators.get(0)).color = this.baseSpec.indicatorColors[0];
        this.completeEndFraction = 0.0f;
    }

    /* access modifiers changed from: private */
    public float getAnimationFraction() {
        return this.animationFraction;
    }

    /* access modifiers changed from: package-private */
    public void setAnimationFraction(float f) {
        this.animationFraction = f;
        int i = (int) (f * 6000.0f);
        updateSegmentPositions(i);
        maybeUpdateSegmentColors(i);
        this.drawable.invalidateSelf();
    }

    /* access modifiers changed from: private */
    public float getCompleteEndFraction() {
        return this.completeEndFraction;
    }

    /* access modifiers changed from: private */
    public void setCompleteEndFraction(float f) {
        this.completeEndFraction = f;
    }
}
