package com.google.android.material.progressindicator;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import androidx.core.math.MathUtils;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat$AnimationCallback;
import com.google.android.material.R$attr;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.progressindicator.DrawingDelegate;

public final class DeterminateDrawable extends DrawableWithAnimatedVisibilityChange {
    private static final FloatPropertyCompat INDICATOR_LENGTH_IN_LEVEL = new FloatPropertyCompat("indicatorLevel") {
        public float getValue(DeterminateDrawable determinateDrawable) {
            return determinateDrawable.getIndicatorFraction() * 10000.0f;
        }

        public void setValue(DeterminateDrawable determinateDrawable, float f) {
            determinateDrawable.setIndicatorFraction(f / 10000.0f);
            determinateDrawable.maybeStartAmplitudeAnimator((int) f);
        }
    };
    private final DrawingDelegate.ActiveIndicator activeIndicator;
    private ValueAnimator amplitudeAnimator;
    private TimeInterpolator amplitudeInterpolator;
    private TimeInterpolator amplitudeOffInterpolator;
    private TimeInterpolator amplitudeOnInterpolator;
    private DrawingDelegate drawingDelegate;
    private final ValueAnimator phaseAnimator;
    private boolean skipAnimationOnLevelChange = false;
    private final SpringAnimation springAnimation;
    private final SpringForce springForce;
    private float targetAmplitudeFraction;

    private float getAmplitudeFractionFromLevel(int i) {
        float f = (float) i;
        return (f < 1000.0f || f > 9000.0f) ? 0.0f : 1.0f;
    }

    public /* bridge */ /* synthetic */ int getAlpha() {
        return super.getAlpha();
    }

    public /* bridge */ /* synthetic */ int getOpacity() {
        return super.getOpacity();
    }

    public /* bridge */ /* synthetic */ boolean hideNow() {
        return super.hideNow();
    }

    public /* bridge */ /* synthetic */ boolean isHiding() {
        return super.isHiding();
    }

    public /* bridge */ /* synthetic */ boolean isRunning() {
        return super.isRunning();
    }

    public /* bridge */ /* synthetic */ boolean isShowing() {
        return super.isShowing();
    }

    public /* bridge */ /* synthetic */ void registerAnimationCallback(Animatable2Compat$AnimationCallback animatable2Compat$AnimationCallback) {
        super.registerAnimationCallback(animatable2Compat$AnimationCallback);
    }

    public /* bridge */ /* synthetic */ void setAlpha(int i) {
        super.setAlpha(i);
    }

    public /* bridge */ /* synthetic */ void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
    }

    public /* bridge */ /* synthetic */ boolean setVisible(boolean z, boolean z2) {
        return super.setVisible(z, z2);
    }

    public /* bridge */ /* synthetic */ boolean setVisible(boolean z, boolean z2, boolean z3) {
        return super.setVisible(z, z2, z3);
    }

    public /* bridge */ /* synthetic */ void start() {
        super.start();
    }

    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    public /* bridge */ /* synthetic */ boolean unregisterAnimationCallback(Animatable2Compat$AnimationCallback animatable2Compat$AnimationCallback) {
        return super.unregisterAnimationCallback(animatable2Compat$AnimationCallback);
    }

    DeterminateDrawable(Context context, BaseProgressIndicatorSpec baseProgressIndicatorSpec, DrawingDelegate drawingDelegate2) {
        super(context, baseProgressIndicatorSpec);
        setDrawingDelegate(drawingDelegate2);
        DrawingDelegate.ActiveIndicator activeIndicator2 = new DrawingDelegate.ActiveIndicator();
        this.activeIndicator = activeIndicator2;
        activeIndicator2.isDeterminate = true;
        SpringForce springForce2 = new SpringForce();
        this.springForce = springForce2;
        springForce2.setDampingRatio(1.0f);
        springForce2.setStiffness(50.0f);
        SpringAnimation springAnimation2 = new SpringAnimation(this, INDICATOR_LENGTH_IN_LEVEL);
        this.springAnimation = springAnimation2;
        springAnimation2.setSpring(springForce2);
        ValueAnimator valueAnimator = new ValueAnimator();
        this.phaseAnimator = valueAnimator;
        valueAnimator.setDuration(1000);
        valueAnimator.setFloatValues(new float[]{0.0f, 1.0f});
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new DeterminateDrawable$$ExternalSyntheticLambda0(this, baseProgressIndicatorSpec));
        if (baseProgressIndicatorSpec.hasWavyEffect(true) && baseProgressIndicatorSpec.waveSpeed != 0) {
            valueAnimator.start();
        }
        setGrowFraction(1.0f);
    }

    public static /* synthetic */ void $r8$lambda$TMiBORtMOk6krNAudyul128n9lo(DeterminateDrawable determinateDrawable, BaseProgressIndicatorSpec baseProgressIndicatorSpec, ValueAnimator valueAnimator) {
        determinateDrawable.getClass();
        if (baseProgressIndicatorSpec.hasWavyEffect(true) && baseProgressIndicatorSpec.waveSpeed != 0 && determinateDrawable.isVisible()) {
            determinateDrawable.invalidateSelf();
        }
    }

    private void maybeInitializeAmplitudeAnimator() {
        if (this.amplitudeAnimator == null) {
            Context context = this.context;
            int i = R$attr.motionEasingStandardInterpolator;
            TimeInterpolator timeInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
            this.amplitudeOnInterpolator = MotionUtils.resolveThemeInterpolator(context, i, timeInterpolator);
            this.amplitudeOffInterpolator = MotionUtils.resolveThemeInterpolator(this.context, R$attr.motionEasingEmphasizedAccelerateInterpolator, timeInterpolator);
            ValueAnimator valueAnimator = new ValueAnimator();
            this.amplitudeAnimator = valueAnimator;
            valueAnimator.setDuration(500);
            this.amplitudeAnimator.setFloatValues(new float[]{0.0f, 1.0f});
            this.amplitudeAnimator.setInterpolator((TimeInterpolator) null);
            this.amplitudeAnimator.addUpdateListener(new DeterminateDrawable$$ExternalSyntheticLambda1(this));
        }
    }

    static DeterminateDrawable createLinearDrawable(Context context, LinearProgressIndicatorSpec linearProgressIndicatorSpec, LinearDrawingDelegate linearDrawingDelegate) {
        return new DeterminateDrawable(context, linearProgressIndicatorSpec, linearDrawingDelegate);
    }

    static DeterminateDrawable createCircularDrawable(Context context, CircularProgressIndicatorSpec circularProgressIndicatorSpec, CircularDrawingDelegate circularDrawingDelegate) {
        return new DeterminateDrawable(context, circularProgressIndicatorSpec, circularDrawingDelegate);
    }

    /* access modifiers changed from: package-private */
    public boolean setVisibleInternal(boolean z, boolean z2, boolean z3) {
        boolean visibleInternal = super.setVisibleInternal(z, z2, z3);
        float systemAnimatorDurationScale = this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(this.context.getContentResolver());
        if (systemAnimatorDurationScale == 0.0f) {
            this.skipAnimationOnLevelChange = true;
            return visibleInternal;
        }
        this.skipAnimationOnLevelChange = false;
        this.springForce.setStiffness(50.0f / systemAnimatorDurationScale);
        return visibleInternal;
    }

    public void jumpToCurrentState() {
        this.springAnimation.skipToEnd();
        setIndicatorFraction(((float) getLevel()) / 10000.0f);
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int i) {
        float amplitudeFractionFromLevel = getAmplitudeFractionFromLevel(i);
        if (this.skipAnimationOnLevelChange) {
            this.springAnimation.skipToEnd();
            setIndicatorFraction(((float) i) / 10000.0f);
            setAmplitudeFraction(amplitudeFractionFromLevel);
            return true;
        }
        this.springAnimation.setStartValue(getIndicatorFraction() * 10000.0f);
        this.springAnimation.animateToFinalPosition((float) i);
        return true;
    }

    public int getIntrinsicWidth() {
        return this.drawingDelegate.getPreferredWidth();
    }

    public int getIntrinsicHeight() {
        return this.drawingDelegate.getPreferredHeight();
    }

    /* access modifiers changed from: package-private */
    public void setLevelByFraction(float f) {
        setLevel((int) (f * 10000.0f));
    }

    /* access modifiers changed from: private */
    public void maybeStartAmplitudeAnimator(int i) {
        if (this.baseSpec.hasWavyEffect(true)) {
            maybeInitializeAmplitudeAnimator();
            float amplitudeFractionFromLevel = getAmplitudeFractionFromLevel(i);
            if (amplitudeFractionFromLevel != this.targetAmplitudeFraction) {
                if (this.amplitudeAnimator.isRunning()) {
                    this.amplitudeAnimator.cancel();
                }
                this.targetAmplitudeFraction = amplitudeFractionFromLevel;
                if (amplitudeFractionFromLevel == 1.0f) {
                    this.amplitudeInterpolator = this.amplitudeOnInterpolator;
                    this.amplitudeAnimator.start();
                    return;
                }
                this.amplitudeInterpolator = this.amplitudeOffInterpolator;
                this.amplitudeAnimator.reverse();
            } else if (!this.amplitudeAnimator.isRunning()) {
                setAmplitudeFraction(amplitudeFractionFromLevel);
            }
        }
    }

    public void draw(Canvas canvas) {
        if (!getBounds().isEmpty() && isVisible() && canvas.getClipBounds(this.clipBounds)) {
            canvas.save();
            this.drawingDelegate.validateSpecAndAdjustCanvas(canvas, getBounds(), getGrowFraction(), isShowing(), isHiding());
            this.activeIndicator.phaseFraction = getPhaseFraction();
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setAntiAlias(true);
            DrawingDelegate.ActiveIndicator activeIndicator2 = this.activeIndicator;
            BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.baseSpec;
            activeIndicator2.color = baseProgressIndicatorSpec.indicatorColors[0];
            int i = baseProgressIndicatorSpec.indicatorTrackGapSize;
            if (i > 0) {
                if (!(this.drawingDelegate instanceof LinearDrawingDelegate)) {
                    i = (int) ((((float) i) * MathUtils.clamp(getIndicatorFraction(), 0.0f, 0.01f)) / 0.01f);
                }
                this.drawingDelegate.fillTrack(canvas, this.paint, getIndicatorFraction(), 1.0f, this.baseSpec.trackColor, getAlpha(), i);
            } else {
                this.drawingDelegate.fillTrack(canvas, this.paint, 0.0f, 1.0f, baseProgressIndicatorSpec.trackColor, getAlpha(), 0);
            }
            this.drawingDelegate.fillIndicator(canvas, this.paint, this.activeIndicator, getAlpha());
            this.drawingDelegate.drawStopIndicator(canvas, this.paint, this.baseSpec.indicatorColors[0], getAlpha());
            canvas.restore();
        }
    }

    /* access modifiers changed from: private */
    public float getIndicatorFraction() {
        return this.activeIndicator.endFraction;
    }

    /* access modifiers changed from: private */
    public void setIndicatorFraction(float f) {
        this.activeIndicator.endFraction = f;
        invalidateSelf();
    }

    private void setAmplitudeFraction(float f) {
        this.activeIndicator.amplitudeFraction = f;
        invalidateSelf();
    }

    /* access modifiers changed from: package-private */
    public DrawingDelegate getDrawingDelegate() {
        return this.drawingDelegate;
    }

    /* access modifiers changed from: package-private */
    public void setDrawingDelegate(DrawingDelegate drawingDelegate2) {
        this.drawingDelegate = drawingDelegate2;
    }
}
