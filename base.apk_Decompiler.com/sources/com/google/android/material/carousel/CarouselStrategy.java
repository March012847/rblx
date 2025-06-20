package com.google.android.material.carousel;

import android.content.Context;
import android.view.View;

public abstract class CarouselStrategy {
    private float smallSizeMax;
    private float smallSizeMin;

    enum StrategyType {
        CONTAINED,
        UNCONTAINED
    }

    public static float getChildMaskPercentage(float f, float f2, float f3) {
        return 1.0f - ((f - f3) / (f2 - f3));
    }

    public abstract KeylineState onFirstChildMeasuredWithMargins(Carousel carousel, View view);

    public abstract boolean shouldRefreshKeylineState(Carousel carousel, int i);

    /* access modifiers changed from: package-private */
    public void initialize(Context context) {
        float f = this.smallSizeMin;
        if (f <= 0.0f) {
            f = CarouselStrategyHelper.getSmallSizeMin(context);
        }
        this.smallSizeMin = f;
        float f2 = this.smallSizeMax;
        if (f2 <= 0.0f) {
            f2 = CarouselStrategyHelper.getSmallSizeMax(context);
        }
        this.smallSizeMax = f2;
    }

    static int[] doubleCounts(int[] iArr) {
        int length = iArr.length;
        int[] iArr2 = new int[length];
        for (int i = 0; i < length; i++) {
            iArr2[i] = iArr[i] * 2;
        }
        return iArr2;
    }

    /* access modifiers changed from: package-private */
    public StrategyType getStrategyType() {
        return StrategyType.CONTAINED;
    }

    public float getSmallItemSizeMin() {
        return this.smallSizeMin;
    }

    public float getSmallItemSizeMax() {
        return this.smallSizeMax;
    }
}
