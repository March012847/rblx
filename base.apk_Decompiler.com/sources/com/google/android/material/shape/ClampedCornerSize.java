package com.google.android.material.shape;

import android.graphics.RectF;
import androidx.core.math.MathUtils;
import java.util.Arrays;

public final class ClampedCornerSize implements CornerSize {
    private final float target;

    private static float getMaxCornerSize(RectF rectF) {
        return Math.min(rectF.width() / 2.0f, rectF.height() / 2.0f);
    }

    public ClampedCornerSize(float f) {
        this.target = f;
    }

    public float getCornerSize(RectF rectF) {
        return MathUtils.clamp(this.target, 0.0f, getMaxCornerSize(rectF));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ClampedCornerSize) && this.target == ((ClampedCornerSize) obj).target;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.target)});
    }
}
