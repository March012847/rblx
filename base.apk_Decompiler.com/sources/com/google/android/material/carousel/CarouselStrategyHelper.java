package com.google.android.material.carousel;

import android.content.Context;
import com.google.android.material.R$dimen;
import com.google.android.material.carousel.KeylineState;

abstract class CarouselStrategyHelper {
    static float addStart(float f, float f2, int i) {
        return i > 0 ? f + (f2 / 2.0f) : f;
    }

    static float updateCurPosition(float f, float f2, float f3, int i) {
        return i > 0 ? f2 + (f3 / 2.0f) : f;
    }

    static float getExtraSmallSize(Context context) {
        return context.getResources().getDimension(R$dimen.m3_carousel_gone_size);
    }

    static float getSmallSizeMin(Context context) {
        return context.getResources().getDimension(R$dimen.m3_carousel_small_item_size_min);
    }

    static float getSmallSizeMax(Context context) {
        return context.getResources().getDimension(R$dimen.m3_carousel_small_item_size_max);
    }

    static KeylineState createKeylineState(Context context, float f, int i, Arrangement arrangement, int i2) {
        if (i2 == 1) {
            return createCenterAlignedKeylineState(context, f, i, arrangement);
        }
        return createLeftAlignedKeylineState(context, f, i, arrangement);
    }

    static KeylineState createLeftAlignedKeylineState(Context context, float f, int i, Arrangement arrangement) {
        float min = Math.min(getExtraSmallSize(context) + f, arrangement.largeSize);
        float f2 = min / 2.0f;
        float f3 = 0.0f - f2;
        float addStart = addStart(0.0f, arrangement.largeSize, arrangement.largeCount);
        float updateCurPosition = updateCurPosition(0.0f, addEnd(addStart, arrangement.largeSize, arrangement.largeCount), arrangement.largeSize, arrangement.largeCount);
        float addStart2 = addStart(updateCurPosition, arrangement.mediumSize, arrangement.mediumCount);
        float addStart3 = addStart(updateCurPosition(updateCurPosition, addStart2, arrangement.mediumSize, arrangement.mediumCount), arrangement.smallSize, arrangement.smallCount);
        float f4 = ((float) i) + f2;
        float childMaskPercentage = CarouselStrategy.getChildMaskPercentage(min, arrangement.largeSize, f);
        float childMaskPercentage2 = CarouselStrategy.getChildMaskPercentage(arrangement.smallSize, arrangement.largeSize, f);
        float childMaskPercentage3 = CarouselStrategy.getChildMaskPercentage(arrangement.mediumSize, arrangement.largeSize, f);
        KeylineState.Builder addKeylineRange = new KeylineState.Builder(arrangement.largeSize, i).addAnchorKeyline(f3, childMaskPercentage, min).addKeylineRange(addStart, 0.0f, arrangement.largeSize, arrangement.largeCount, true);
        if (arrangement.mediumCount > 0) {
            addKeylineRange.addKeyline(addStart2, childMaskPercentage3, arrangement.mediumSize);
        }
        int i2 = arrangement.smallCount;
        if (i2 > 0) {
            addKeylineRange.addKeylineRange(addStart3, childMaskPercentage2, arrangement.smallSize, i2);
        }
        addKeylineRange.addAnchorKeyline(f4, childMaskPercentage, min);
        return addKeylineRange.build();
    }

    static KeylineState createCenterAlignedKeylineState(Context context, float f, int i, Arrangement arrangement) {
        float f2;
        float f3;
        float f4 = f;
        int i2 = i;
        Arrangement arrangement2 = arrangement;
        float min = Math.min(getExtraSmallSize(context) + f4, arrangement2.largeSize);
        float f5 = min / 2.0f;
        float f6 = 0.0f - f5;
        float addStart = addStart(0.0f, arrangement2.smallSize, arrangement2.smallCount);
        float updateCurPosition = updateCurPosition(0.0f, addEnd(addStart, arrangement2.smallSize, (int) Math.floor((double) (((float) arrangement2.smallCount) / 2.0f))), arrangement2.smallSize, arrangement2.smallCount);
        float addStart2 = addStart(updateCurPosition, arrangement2.mediumSize, arrangement2.mediumCount);
        float updateCurPosition2 = updateCurPosition(updateCurPosition, addEnd(addStart2, arrangement2.mediumSize, (int) Math.floor((double) (((float) arrangement2.mediumCount) / 2.0f))), arrangement2.mediumSize, arrangement2.mediumCount);
        float addStart3 = addStart(updateCurPosition2, arrangement2.largeSize, arrangement2.largeCount);
        float updateCurPosition3 = updateCurPosition(updateCurPosition2, addEnd(addStart3, arrangement2.largeSize, arrangement2.largeCount), arrangement2.largeSize, arrangement2.largeCount);
        float addStart4 = addStart(updateCurPosition3, arrangement2.mediumSize, arrangement2.mediumCount);
        float addStart5 = addStart(updateCurPosition(updateCurPosition3, addEnd(addStart4, arrangement2.mediumSize, (int) Math.ceil((double) (((float) arrangement2.mediumCount) / 2.0f))), arrangement2.mediumSize, arrangement2.mediumCount), arrangement2.smallSize, arrangement2.smallCount);
        float f7 = ((float) i2) + f5;
        float childMaskPercentage = CarouselStrategy.getChildMaskPercentage(min, arrangement2.largeSize, f4);
        float childMaskPercentage2 = CarouselStrategy.getChildMaskPercentage(arrangement2.smallSize, arrangement2.largeSize, f4);
        float childMaskPercentage3 = CarouselStrategy.getChildMaskPercentage(arrangement2.mediumSize, arrangement2.largeSize, f4);
        KeylineState.Builder addAnchorKeyline = new KeylineState.Builder(arrangement2.largeSize, i2).addAnchorKeyline(f6, childMaskPercentage, min);
        int i3 = arrangement2.smallCount;
        if (i3 > 0) {
            f2 = 2.0f;
            f3 = childMaskPercentage;
            addAnchorKeyline.addKeylineRange(addStart, childMaskPercentage2, arrangement2.smallSize, (int) Math.floor((double) (((float) i3) / 2.0f)));
        } else {
            f2 = 2.0f;
            f3 = childMaskPercentage;
        }
        int i4 = arrangement2.mediumCount;
        if (i4 > 0) {
            addAnchorKeyline.addKeylineRange(addStart2, childMaskPercentage3, arrangement2.mediumSize, (int) Math.floor((double) (((float) i4) / f2)));
        }
        float f8 = childMaskPercentage2;
        KeylineState.Builder builder = addAnchorKeyline;
        float f9 = f8;
        builder.addKeylineRange(addStart3, 0.0f, arrangement2.largeSize, arrangement2.largeCount, true);
        int i5 = arrangement2.mediumCount;
        if (i5 > 0) {
            builder.addKeylineRange(addStart4, childMaskPercentage3, arrangement2.mediumSize, (int) Math.ceil((double) (((float) i5) / f2)));
        }
        int i6 = arrangement2.smallCount;
        if (i6 > 0) {
            builder.addKeylineRange(addStart5, f9, arrangement2.smallSize, (int) Math.ceil((double) (((float) i6) / f2)));
        }
        builder.addAnchorKeyline(f7, f3, min);
        return builder.build();
    }

    static int maxValue(int[] iArr) {
        int i = Integer.MIN_VALUE;
        for (int i2 : iArr) {
            if (i2 > i) {
                i = i2;
            }
        }
        return i;
    }

    static float addEnd(float f, float f2, int i) {
        return f + (((float) Math.max(0, i - 1)) * f2);
    }
}
