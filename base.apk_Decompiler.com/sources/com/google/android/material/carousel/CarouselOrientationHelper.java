package com.google.android.material.carousel;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

abstract class CarouselOrientationHelper {
    final int orientation;

    /* access modifiers changed from: package-private */
    public abstract int getParentBottom();

    /* access modifiers changed from: package-private */
    public abstract int getParentLeft();

    /* access modifiers changed from: package-private */
    public abstract int getParentRight();

    /* access modifiers changed from: package-private */
    public abstract int getParentStart();

    /* access modifiers changed from: package-private */
    public abstract int getParentTop();

    /* access modifiers changed from: package-private */
    public abstract void layoutDecoratedWithMargins(View view, int i, int i2);

    /* access modifiers changed from: package-private */
    public abstract void offsetChild(View view, Rect rect, float f, float f2);

    private CarouselOrientationHelper(int i) {
        this.orientation = i;
    }

    static CarouselOrientationHelper createOrientationHelper(CarouselLayoutManager carouselLayoutManager, int i) {
        if (i == 0) {
            return createHorizontalHelper(carouselLayoutManager);
        }
        if (i == 1) {
            return createVerticalHelper(carouselLayoutManager);
        }
        throw new IllegalArgumentException("invalid orientation");
    }

    private static CarouselOrientationHelper createVerticalHelper(final CarouselLayoutManager carouselLayoutManager) {
        return new CarouselOrientationHelper(1) {
            /* access modifiers changed from: package-private */
            public int getParentTop() {
                return 0;
            }

            /* access modifiers changed from: package-private */
            public int getParentLeft() {
                return carouselLayoutManager.getPaddingLeft();
            }

            /* access modifiers changed from: package-private */
            public int getParentStart() {
                return getParentTop();
            }

            /* access modifiers changed from: package-private */
            public int getParentRight() {
                return carouselLayoutManager.getWidth() - carouselLayoutManager.getPaddingRight();
            }

            /* access modifiers changed from: package-private */
            public int getParentBottom() {
                return carouselLayoutManager.getHeight();
            }

            /* access modifiers changed from: package-private */
            public int getDecoratedCrossAxisMeasurement(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                return carouselLayoutManager.getDecoratedMeasuredWidth(view) + layoutParams.leftMargin + layoutParams.rightMargin;
            }

            public void layoutDecoratedWithMargins(View view, int i, int i2) {
                int parentLeft = getParentLeft();
                carouselLayoutManager.layoutDecoratedWithMargins(view, parentLeft, i, parentLeft + getDecoratedCrossAxisMeasurement(view), i2);
            }

            public void offsetChild(View view, Rect rect, float f, float f2) {
                view.offsetTopAndBottom((int) (f2 - (((float) rect.top) + f)));
            }
        };
    }

    private static CarouselOrientationHelper createHorizontalHelper(final CarouselLayoutManager carouselLayoutManager) {
        return new CarouselOrientationHelper(0) {
            /* access modifiers changed from: package-private */
            public int getParentLeft() {
                return 0;
            }

            /* access modifiers changed from: package-private */
            public int getParentStart() {
                return carouselLayoutManager.isLayoutRtl() ? getParentRight() : getParentLeft();
            }

            /* access modifiers changed from: package-private */
            public int getParentRight() {
                return carouselLayoutManager.getWidth();
            }

            /* access modifiers changed from: package-private */
            public int getParentTop() {
                return carouselLayoutManager.getPaddingTop();
            }

            /* access modifiers changed from: package-private */
            public int getParentBottom() {
                return carouselLayoutManager.getHeight() - carouselLayoutManager.getPaddingBottom();
            }

            /* access modifiers changed from: package-private */
            public int getDecoratedCrossAxisMeasurement(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                return carouselLayoutManager.getDecoratedMeasuredHeight(view) + layoutParams.topMargin + layoutParams.bottomMargin;
            }

            public void layoutDecoratedWithMargins(View view, int i, int i2) {
                int parentTop = getParentTop();
                carouselLayoutManager.layoutDecoratedWithMargins(view, i, parentTop, i2, parentTop + getDecoratedCrossAxisMeasurement(view));
            }

            public void offsetChild(View view, Rect rect, float f, float f2) {
                view.offsetLeftAndRight((int) (f2 - (((float) rect.left) + f)));
            }
        };
    }
}
