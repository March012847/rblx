package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.appcompat.R$attr;
import com.google.android.material.R$dimen;
import com.google.android.material.R$styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;

public abstract class BaseProgressIndicatorSpec {
    public int hideAnimationBehavior;
    public float indeterminateAnimatorDurationScale;
    public int[] indicatorColors = new int[0];
    public int indicatorTrackGapSize;
    public int showAnimationBehavior;
    public int trackColor;
    public int trackCornerRadius;
    public float trackCornerRadiusFraction;
    public int trackThickness;
    public boolean useRelativeTrackCornerRadius;
    public int waveAmplitude;
    public int waveSpeed;
    public int wavelengthDeterminate;
    public int wavelengthIndeterminate;

    protected BaseProgressIndicatorSpec(Context context, AttributeSet attributeSet, int i, int i2) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.mtrl_progress_track_thickness);
        Context context2 = context;
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.BaseProgressIndicator, i, i2, new int[0]);
        this.trackThickness = MaterialResources.getDimensionPixelSize(context2, obtainStyledAttributes, R$styleable.BaseProgressIndicator_trackThickness, dimensionPixelSize);
        TypedValue peekValue = obtainStyledAttributes.peekValue(R$styleable.BaseProgressIndicator_trackCornerRadius);
        if (peekValue != null) {
            int i3 = peekValue.type;
            if (i3 == 5) {
                this.trackCornerRadius = Math.min(TypedValue.complexToDimensionPixelSize(peekValue.data, obtainStyledAttributes.getResources().getDisplayMetrics()), this.trackThickness / 2);
                this.useRelativeTrackCornerRadius = false;
            } else if (i3 == 6) {
                this.trackCornerRadiusFraction = Math.min(peekValue.getFraction(1.0f, 1.0f), 0.5f);
                this.useRelativeTrackCornerRadius = true;
            }
        }
        this.showAnimationBehavior = obtainStyledAttributes.getInt(R$styleable.BaseProgressIndicator_showAnimationBehavior, 0);
        this.hideAnimationBehavior = obtainStyledAttributes.getInt(R$styleable.BaseProgressIndicator_hideAnimationBehavior, 0);
        this.indicatorTrackGapSize = obtainStyledAttributes.getDimensionPixelSize(R$styleable.BaseProgressIndicator_indicatorTrackGapSize, 0);
        int abs = Math.abs(obtainStyledAttributes.getDimensionPixelSize(R$styleable.BaseProgressIndicator_wavelength, 0));
        this.wavelengthDeterminate = Math.abs(obtainStyledAttributes.getDimensionPixelSize(R$styleable.BaseProgressIndicator_wavelengthDeterminate, abs));
        this.wavelengthIndeterminate = Math.abs(obtainStyledAttributes.getDimensionPixelSize(R$styleable.BaseProgressIndicator_wavelengthIndeterminate, abs));
        this.waveAmplitude = Math.abs(obtainStyledAttributes.getDimensionPixelSize(R$styleable.BaseProgressIndicator_waveAmplitude, 0));
        this.waveSpeed = obtainStyledAttributes.getDimensionPixelSize(R$styleable.BaseProgressIndicator_waveSpeed, 0);
        this.indeterminateAnimatorDurationScale = obtainStyledAttributes.getFloat(R$styleable.BaseProgressIndicator_indeterminateAnimatorDurationScale, 1.0f);
        loadIndicatorColors(context2, obtainStyledAttributes);
        loadTrackColor(context2, obtainStyledAttributes);
        obtainStyledAttributes.recycle();
    }

    private void loadIndicatorColors(Context context, TypedArray typedArray) {
        int i = R$styleable.BaseProgressIndicator_indicatorColor;
        if (!typedArray.hasValue(i)) {
            this.indicatorColors = new int[]{MaterialColors.getColor(context, R$attr.colorPrimary, -1)};
        } else if (typedArray.peekValue(i).type != 1) {
            this.indicatorColors = new int[]{typedArray.getColor(i, -1)};
        } else {
            int[] intArray = context.getResources().getIntArray(typedArray.getResourceId(i, -1));
            this.indicatorColors = intArray;
            if (intArray.length == 0) {
                throw new IllegalArgumentException("indicatorColors cannot be empty when indicatorColor is not used.");
            }
        }
    }

    private void loadTrackColor(Context context, TypedArray typedArray) {
        int i = R$styleable.BaseProgressIndicator_trackColor;
        if (typedArray.hasValue(i)) {
            this.trackColor = typedArray.getColor(i, -1);
            return;
        }
        this.trackColor = this.indicatorColors[0];
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{16842803});
        float f = obtainStyledAttributes.getFloat(0, 0.2f);
        obtainStyledAttributes.recycle();
        this.trackColor = MaterialColors.compositeARGBWithAlpha(this.trackColor, (int) (f * 255.0f));
    }

    public boolean isShowAnimationEnabled() {
        return this.showAnimationBehavior != 0;
    }

    public boolean isHideAnimationEnabled() {
        return this.hideAnimationBehavior != 0;
    }

    public boolean hasWavyEffect(boolean z) {
        if (this.waveAmplitude <= 0) {
            return false;
        }
        if (z || this.wavelengthIndeterminate <= 0) {
            return z && this.wavelengthDeterminate > 0;
        }
        return true;
    }

    public int getTrackCornerRadiusInPx() {
        if (this.useRelativeTrackCornerRadius) {
            return (int) (((float) this.trackThickness) * this.trackCornerRadiusFraction);
        }
        return this.trackCornerRadius;
    }

    public boolean useStrokeCap() {
        return this.useRelativeTrackCornerRadius && this.trackCornerRadiusFraction == 0.5f;
    }

    /* access modifiers changed from: package-private */
    public void validateSpec() {
        if (this.indicatorTrackGapSize < 0) {
            throw new IllegalArgumentException("indicatorTrackGapSize must be >= 0.");
        }
    }
}
