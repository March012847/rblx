package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import com.google.android.material.R$attr;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.ThemeEnforcement;

public final class LinearProgressIndicatorSpec extends BaseProgressIndicatorSpec {
    boolean drawHorizontallyInverse;
    public boolean hasInnerCornerRadius;
    public int indeterminateAnimationType;
    public int indicatorDirection;
    public int trackInnerCornerRadius;
    public float trackInnerCornerRadiusFraction;
    public Integer trackStopIndicatorPadding;
    public int trackStopIndicatorSize;
    public boolean useRelativeTrackInnerCornerRadius;

    public LinearProgressIndicatorSpec(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.linearProgressIndicatorStyle);
    }

    public LinearProgressIndicatorSpec(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, LinearProgressIndicator.DEF_STYLE_RES);
    }

    public LinearProgressIndicatorSpec(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        boolean z = false;
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R$styleable.LinearProgressIndicator, R$attr.linearProgressIndicatorStyle, LinearProgressIndicator.DEF_STYLE_RES, new int[0]);
        this.indeterminateAnimationType = obtainStyledAttributes.getInt(R$styleable.LinearProgressIndicator_indeterminateAnimationType, 1);
        this.indicatorDirection = obtainStyledAttributes.getInt(R$styleable.LinearProgressIndicator_indicatorDirectionLinear, 0);
        this.trackStopIndicatorSize = Math.min(obtainStyledAttributes.getDimensionPixelSize(R$styleable.LinearProgressIndicator_trackStopIndicatorSize, 0), this.trackThickness);
        int i3 = R$styleable.LinearProgressIndicator_trackStopIndicatorPadding;
        if (obtainStyledAttributes.hasValue(i3)) {
            this.trackStopIndicatorPadding = Integer.valueOf(obtainStyledAttributes.getDimensionPixelSize(i3, 0));
        }
        TypedValue peekValue = obtainStyledAttributes.peekValue(R$styleable.LinearProgressIndicator_trackInnerCornerRadius);
        if (peekValue != null) {
            int i4 = peekValue.type;
            if (i4 == 5) {
                this.trackInnerCornerRadius = Math.min(TypedValue.complexToDimensionPixelSize(peekValue.data, obtainStyledAttributes.getResources().getDisplayMetrics()), this.trackThickness / 2);
                this.useRelativeTrackInnerCornerRadius = false;
                this.hasInnerCornerRadius = true;
            } else if (i4 == 6) {
                this.trackInnerCornerRadiusFraction = Math.min(peekValue.getFraction(1.0f, 1.0f), 0.5f);
                this.useRelativeTrackInnerCornerRadius = true;
                this.hasInnerCornerRadius = true;
            }
        }
        obtainStyledAttributes.recycle();
        validateSpec();
        this.drawHorizontallyInverse = this.indicatorDirection == 1 ? true : z;
    }

    public int getTrackInnerCornerRadiusInPx() {
        if (!this.hasInnerCornerRadius) {
            return getTrackCornerRadiusInPx();
        }
        if (this.useRelativeTrackInnerCornerRadius) {
            return (int) (((float) this.trackThickness) * this.trackInnerCornerRadiusFraction);
        }
        return this.trackInnerCornerRadius;
    }

    public boolean useStrokeCap() {
        return super.useStrokeCap() && getTrackInnerCornerRadiusInPx() == getTrackCornerRadiusInPx();
    }

    /* access modifiers changed from: package-private */
    public void validateSpec() {
        super.validateSpec();
        if (this.trackStopIndicatorSize < 0) {
            throw new IllegalArgumentException("Stop indicator size must be >= 0.");
        } else if (this.indeterminateAnimationType != 0) {
        } else {
            if ((getTrackCornerRadiusInPx() > 0 || (this.hasInnerCornerRadius && getTrackInnerCornerRadiusInPx() > 0)) && this.indicatorTrackGapSize == 0) {
                throw new IllegalArgumentException("Rounded corners without gap are not supported in contiguous indeterminate animation.");
            } else if (this.indicatorColors.length < 3) {
                throw new IllegalArgumentException("Contiguous indeterminate animation must be used with 3 or more indicator colors.");
            }
        }
    }
}
