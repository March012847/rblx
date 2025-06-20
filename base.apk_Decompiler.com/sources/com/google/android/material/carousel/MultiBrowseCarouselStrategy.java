package com.google.android.material.carousel;

import android.view.View;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.RecyclerView;

public final class MultiBrowseCarouselStrategy extends CarouselStrategy {
    private static final int[] MEDIUM_COUNTS = {1, 0};
    private static final int[] SMALL_COUNTS = {1};
    private int keylineCount = 0;

    public KeylineState onFirstChildMeasuredWithMargins(Carousel carousel, View view) {
        boolean z;
        int containerHeight = carousel.getContainerHeight();
        if (carousel.isHorizontal()) {
            containerHeight = carousel.getContainerWidth();
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        float f = (float) (layoutParams.topMargin + layoutParams.bottomMargin);
        float measuredHeight = (float) view.getMeasuredHeight();
        if (carousel.isHorizontal()) {
            f = (float) (layoutParams.leftMargin + layoutParams.rightMargin);
            measuredHeight = (float) view.getMeasuredWidth();
        }
        float smallItemSizeMin = getSmallItemSizeMin() + f;
        float max = Math.max(getSmallItemSizeMax() + f, smallItemSizeMin);
        float f2 = (float) containerHeight;
        float min = Math.min(measuredHeight + f, f2);
        float clamp = MathUtils.clamp((measuredHeight / 3.0f) + f, smallItemSizeMin + f, max + f);
        float f3 = (min + clamp) / 2.0f;
        int[] iArr = SMALL_COUNTS;
        float f4 = 2.0f * smallItemSizeMin;
        if (f2 <= f4) {
            iArr = new int[]{0};
        }
        int[] iArr2 = MEDIUM_COUNTS;
        if (carousel.getCarouselAlignment() == 1) {
            iArr = CarouselStrategy.doubleCounts(iArr);
            iArr2 = CarouselStrategy.doubleCounts(iArr2);
        }
        int[] iArr3 = iArr2;
        int[] iArr4 = iArr;
        int[] iArr5 = iArr3;
        float f5 = f;
        int ceil = (int) Math.ceil((double) (f2 / min));
        int max2 = (ceil - ((int) Math.max(1.0d, Math.floor((double) (((f2 - (((float) CarouselStrategyHelper.maxValue(iArr5)) * f3)) - (((float) CarouselStrategyHelper.maxValue(iArr4)) * max)) / min))))) + 1;
        int[] iArr6 = new int[max2];
        for (int i = 0; i < max2; i++) {
            iArr6[i] = ceil - i;
        }
        Arrangement findLowestCostArrangement = Arrangement.findLowestCostArrangement(f2, clamp, smallItemSizeMin, max, iArr4, f3, iArr5, min, iArr6);
        this.keylineCount = findLowestCostArrangement.getItemCount();
        boolean ensureArrangementFitsItemCount = ensureArrangementFitsItemCount(findLowestCostArrangement, carousel.getItemCount());
        int i2 = findLowestCostArrangement.mediumCount;
        if (i2 == 0 && findLowestCostArrangement.smallCount == 0 && f2 > f4) {
            findLowestCostArrangement.smallCount = 1;
            z = true;
        } else {
            z = ensureArrangementFitsItemCount;
        }
        if (z) {
            findLowestCostArrangement = Arrangement.findLowestCostArrangement(f2, clamp, smallItemSizeMin, max, new int[]{findLowestCostArrangement.smallCount}, f3, new int[]{i2}, min, new int[]{findLowestCostArrangement.largeCount});
        }
        return CarouselStrategyHelper.createKeylineState(view.getContext(), f5, containerHeight, findLowestCostArrangement, carousel.getCarouselAlignment());
    }

    /* access modifiers changed from: package-private */
    public boolean ensureArrangementFitsItemCount(Arrangement arrangement, int i) {
        int itemCount = arrangement.getItemCount() - i;
        boolean z = itemCount > 0 && (arrangement.smallCount > 0 || arrangement.mediumCount > 1);
        while (itemCount > 0) {
            int i2 = arrangement.smallCount;
            if (i2 > 0) {
                arrangement.smallCount = i2 - 1;
            } else {
                int i3 = arrangement.mediumCount;
                if (i3 > 1) {
                    arrangement.mediumCount = i3 - 1;
                }
            }
            itemCount--;
        }
        return z;
    }

    public boolean shouldRefreshKeylineState(Carousel carousel, int i) {
        if (i >= this.keylineCount || carousel.getItemCount() < this.keylineCount) {
            return i >= this.keylineCount && carousel.getItemCount() < this.keylineCount;
        }
        return true;
    }
}
