package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat$AnimationCallback;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import com.google.android.material.R$drawable;

public final class IndeterminateDrawable extends DrawableWithAnimatedVisibilityChange {
    private IndeterminateAnimatorDelegate animatorDelegate;
    private DrawingDelegate drawingDelegate;
    private Drawable staticDummyDrawable;

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

    IndeterminateDrawable(Context context, BaseProgressIndicatorSpec baseProgressIndicatorSpec, DrawingDelegate drawingDelegate2, IndeterminateAnimatorDelegate indeterminateAnimatorDelegate) {
        super(context, baseProgressIndicatorSpec);
        setDrawingDelegate(drawingDelegate2);
        setAnimatorDelegate(indeterminateAnimatorDelegate);
    }

    static IndeterminateDrawable createLinearDrawable(Context context, LinearProgressIndicatorSpec linearProgressIndicatorSpec, LinearDrawingDelegate linearDrawingDelegate) {
        IndeterminateAnimatorDelegate indeterminateAnimatorDelegate;
        if (linearProgressIndicatorSpec.indeterminateAnimationType == 0) {
            indeterminateAnimatorDelegate = new LinearIndeterminateContiguousAnimatorDelegate(linearProgressIndicatorSpec);
        } else {
            indeterminateAnimatorDelegate = new LinearIndeterminateDisjointAnimatorDelegate(context, linearProgressIndicatorSpec);
        }
        return new IndeterminateDrawable(context, linearProgressIndicatorSpec, linearDrawingDelegate, indeterminateAnimatorDelegate);
    }

    static IndeterminateDrawable createCircularDrawable(Context context, CircularProgressIndicatorSpec circularProgressIndicatorSpec, CircularDrawingDelegate circularDrawingDelegate) {
        IndeterminateAnimatorDelegate indeterminateAnimatorDelegate;
        if (circularProgressIndicatorSpec.indeterminateAnimationType == 1) {
            indeterminateAnimatorDelegate = new CircularIndeterminateRetreatAnimatorDelegate(context, circularProgressIndicatorSpec);
        } else {
            indeterminateAnimatorDelegate = new CircularIndeterminateAdvanceAnimatorDelegate(circularProgressIndicatorSpec);
        }
        IndeterminateDrawable indeterminateDrawable = new IndeterminateDrawable(context, circularProgressIndicatorSpec, circularDrawingDelegate, indeterminateAnimatorDelegate);
        indeterminateDrawable.setStaticDummyDrawable(VectorDrawableCompat.create(context.getResources(), R$drawable.indeterminate_static, (Resources.Theme) null));
        return indeterminateDrawable;
    }

    /* access modifiers changed from: package-private */
    public boolean setVisibleInternal(boolean z, boolean z2, boolean z3) {
        Drawable drawable;
        boolean visibleInternal = super.setVisibleInternal(z, z2, z3);
        if (isSystemAnimatorDisabled() && (drawable = this.staticDummyDrawable) != null) {
            return drawable.setVisible(z, z2);
        }
        if (!isRunning()) {
            this.animatorDelegate.cancelAnimatorImmediately();
        }
        if (!z || !z3) {
            return visibleInternal;
        }
        this.animatorDelegate.startAnimator();
        return visibleInternal;
    }

    public int getIntrinsicWidth() {
        return this.drawingDelegate.getPreferredWidth();
    }

    public int getIntrinsicHeight() {
        return this.drawingDelegate.getPreferredHeight();
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x0106  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void draw(android.graphics.Canvas r14) {
        /*
            r13 = this;
            android.graphics.Rect r0 = r13.getBounds()
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x014e
            boolean r0 = r13.isVisible()
            if (r0 == 0) goto L_0x014e
            android.graphics.Rect r0 = r13.clipBounds
            boolean r0 = r14.getClipBounds(r0)
            if (r0 != 0) goto L_0x001a
            goto L_0x014e
        L_0x001a:
            boolean r0 = r13.isSystemAnimatorDisabled()
            r8 = 0
            if (r0 == 0) goto L_0x003d
            android.graphics.drawable.Drawable r0 = r13.staticDummyDrawable
            if (r0 == 0) goto L_0x003d
            android.graphics.Rect r2 = r13.getBounds()
            r0.setBounds(r2)
            android.graphics.drawable.Drawable r0 = r13.staticDummyDrawable
            com.google.android.material.progressindicator.BaseProgressIndicatorSpec r2 = r13.baseSpec
            int[] r2 = r2.indicatorColors
            r2 = r2[r8]
            r0.setTint(r2)
            android.graphics.drawable.Drawable r13 = r13.staticDummyDrawable
            r13.draw(r14)
            return
        L_0x003d:
            r14.save()
            com.google.android.material.progressindicator.DrawingDelegate r0 = r13.drawingDelegate
            android.graphics.Rect r2 = r13.getBounds()
            float r3 = r13.getGrowFraction()
            boolean r4 = r13.isShowing()
            boolean r5 = r13.isHiding()
            r1 = r14
            r0.validateSpecAndAdjustCanvas(r1, r2, r3, r4, r5)
            com.google.android.material.progressindicator.BaseProgressIndicatorSpec r0 = r13.baseSpec
            int r9 = r0.indicatorTrackGapSize
            int r6 = r13.getAlpha()
            com.google.android.material.progressindicator.BaseProgressIndicatorSpec r0 = r13.baseSpec
            boolean r1 = r0 instanceof com.google.android.material.progressindicator.LinearProgressIndicatorSpec
            r2 = 1
            if (r1 != 0) goto L_0x0073
            boolean r1 = r0 instanceof com.google.android.material.progressindicator.CircularProgressIndicatorSpec
            if (r1 == 0) goto L_0x0071
            r1 = r0
            com.google.android.material.progressindicator.CircularProgressIndicatorSpec r1 = (com.google.android.material.progressindicator.CircularProgressIndicatorSpec) r1
            boolean r1 = r1.indeterminateTrackVisible
            if (r1 == 0) goto L_0x0071
            goto L_0x0073
        L_0x0071:
            r10 = r8
            goto L_0x0074
        L_0x0073:
            r10 = r2
        L_0x0074:
            if (r10 == 0) goto L_0x0080
            if (r9 != 0) goto L_0x0080
            boolean r0 = r0.hasWavyEffect(r8)
            if (r0 != 0) goto L_0x0080
            r11 = r2
            goto L_0x0081
        L_0x0080:
            r11 = r8
        L_0x0081:
            if (r11 == 0) goto L_0x0096
            com.google.android.material.progressindicator.DrawingDelegate r0 = r13.drawingDelegate
            android.graphics.Paint r2 = r13.paint
            com.google.android.material.progressindicator.BaseProgressIndicatorSpec r1 = r13.baseSpec
            int r5 = r1.trackColor
            r7 = 0
            r3 = 0
            r4 = 1065353216(0x3f800000, float:1.0)
            r1 = r14
            r0.fillTrack(r1, r2, r3, r4, r5, r6, r7)
        L_0x0093:
            r7 = r9
            goto L_0x00fc
        L_0x0096:
            if (r10 == 0) goto L_0x0093
            com.google.android.material.progressindicator.IndeterminateAnimatorDelegate r0 = r13.animatorDelegate
            java.util.List r0 = r0.activeIndicators
            java.lang.Object r0 = r0.get(r8)
            com.google.android.material.progressindicator.DrawingDelegate$ActiveIndicator r0 = (com.google.android.material.progressindicator.DrawingDelegate.ActiveIndicator) r0
            com.google.android.material.progressindicator.IndeterminateAnimatorDelegate r1 = r13.animatorDelegate
            java.util.List r1 = r1.activeIndicators
            int r3 = r1.size()
            int r3 = r3 - r2
            java.lang.Object r1 = r1.get(r3)
            r12 = r1
            com.google.android.material.progressindicator.DrawingDelegate$ActiveIndicator r12 = (com.google.android.material.progressindicator.DrawingDelegate.ActiveIndicator) r12
            com.google.android.material.progressindicator.DrawingDelegate r1 = r13.drawingDelegate
            boolean r2 = r1 instanceof com.google.android.material.progressindicator.LinearDrawingDelegate
            if (r2 == 0) goto L_0x00d8
            android.graphics.Paint r2 = r13.paint
            float r4 = r0.startFraction
            com.google.android.material.progressindicator.BaseProgressIndicatorSpec r0 = r13.baseSpec
            int r5 = r0.trackColor
            r3 = 0
            r0 = r1
            r7 = r9
            r1 = r14
            r0.fillTrack(r1, r2, r3, r4, r5, r6, r7)
            com.google.android.material.progressindicator.DrawingDelegate r0 = r13.drawingDelegate
            android.graphics.Paint r2 = r13.paint
            float r3 = r12.endFraction
            com.google.android.material.progressindicator.BaseProgressIndicatorSpec r1 = r13.baseSpec
            int r5 = r1.trackColor
            r4 = 1065353216(0x3f800000, float:1.0)
            r1 = r14
            r0.fillTrack(r1, r2, r3, r4, r5, r6, r7)
            goto L_0x00fc
        L_0x00d8:
            r7 = r9
            r14.save()
            float r2 = r12.rotationDegree
            r14.rotate(r2)
            com.google.android.material.progressindicator.DrawingDelegate r2 = r13.drawingDelegate
            r3 = r2
            android.graphics.Paint r2 = r13.paint
            float r4 = r12.endFraction
            float r0 = r0.startFraction
            r5 = 1065353216(0x3f800000, float:1.0)
            float r0 = r0 + r5
            com.google.android.material.progressindicator.BaseProgressIndicatorSpec r5 = r13.baseSpec
            int r5 = r5.trackColor
            r1 = r4
            r4 = r0
            r0 = r3
            r3 = r1
            r1 = r14
            r0.fillTrack(r1, r2, r3, r4, r5, r6, r7)
            r14.restore()
        L_0x00fc:
            com.google.android.material.progressindicator.IndeterminateAnimatorDelegate r0 = r13.animatorDelegate
            java.util.List r0 = r0.activeIndicators
            int r0 = r0.size()
            if (r8 >= r0) goto L_0x014b
            com.google.android.material.progressindicator.IndeterminateAnimatorDelegate r0 = r13.animatorDelegate
            java.util.List r0 = r0.activeIndicators
            java.lang.Object r0 = r0.get(r8)
            com.google.android.material.progressindicator.DrawingDelegate$ActiveIndicator r0 = (com.google.android.material.progressindicator.DrawingDelegate.ActiveIndicator) r0
            float r2 = r13.getPhaseFraction()
            r0.phaseFraction = r2
            com.google.android.material.progressindicator.DrawingDelegate r2 = r13.drawingDelegate
            android.graphics.Paint r3 = r13.paint
            int r4 = r13.getAlpha()
            r2.fillIndicator(r14, r3, r0, r4)
            if (r8 <= 0) goto L_0x0148
            if (r11 != 0) goto L_0x0148
            if (r10 == 0) goto L_0x0148
            com.google.android.material.progressindicator.IndeterminateAnimatorDelegate r2 = r13.animatorDelegate
            java.util.List r2 = r2.activeIndicators
            int r3 = r8 + -1
            java.lang.Object r2 = r2.get(r3)
            com.google.android.material.progressindicator.DrawingDelegate$ActiveIndicator r2 = (com.google.android.material.progressindicator.DrawingDelegate.ActiveIndicator) r2
            com.google.android.material.progressindicator.DrawingDelegate r3 = r13.drawingDelegate
            android.graphics.Paint r4 = r13.paint
            float r2 = r2.endFraction
            float r0 = r0.startFraction
            com.google.android.material.progressindicator.BaseProgressIndicatorSpec r5 = r13.baseSpec
            int r5 = r5.trackColor
            r1 = r4
            r4 = r0
            r0 = r3
            r3 = r2
            r2 = r1
            r1 = r14
            r0.fillTrack(r1, r2, r3, r4, r5, r6, r7)
        L_0x0148:
            int r8 = r8 + 1
            goto L_0x00fc
        L_0x014b:
            r14.restore()
        L_0x014e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.progressindicator.IndeterminateDrawable.draw(android.graphics.Canvas):void");
    }

    private boolean isSystemAnimatorDisabled() {
        AnimatorDurationScaleProvider animatorDurationScaleProvider = this.animatorDurationScaleProvider;
        if (animatorDurationScaleProvider == null || animatorDurationScaleProvider.getSystemAnimatorDurationScale(this.context.getContentResolver()) != 0.0f) {
            return false;
        }
        return true;
    }

    public void setStaticDummyDrawable(Drawable drawable) {
        this.staticDummyDrawable = drawable;
    }

    /* access modifiers changed from: package-private */
    public IndeterminateAnimatorDelegate getAnimatorDelegate() {
        return this.animatorDelegate;
    }

    /* access modifiers changed from: package-private */
    public void setAnimatorDelegate(IndeterminateAnimatorDelegate indeterminateAnimatorDelegate) {
        this.animatorDelegate = indeterminateAnimatorDelegate;
        indeterminateAnimatorDelegate.registerDrawable(this);
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
