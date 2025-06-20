package com.google.android.material.sidesheet;

import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

final class RightSheetDelegate extends SheetDelegate {
    final SideSheetBehavior sheetBehavior;

    /* access modifiers changed from: package-private */
    public int getSheetEdge() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public boolean isExpandingOutwards(float f) {
        return f < 0.0f;
    }

    RightSheetDelegate(SideSheetBehavior sideSheetBehavior) {
        this.sheetBehavior = sideSheetBehavior;
    }

    /* access modifiers changed from: package-private */
    public int getHiddenOffset() {
        return this.sheetBehavior.getParentWidth();
    }

    /* access modifiers changed from: package-private */
    public int getExpandedOffset() {
        return Math.max(0, (getHiddenOffset() - this.sheetBehavior.getChildWidth()) - this.sheetBehavior.getInnerMargin());
    }

    /* access modifiers changed from: package-private */
    public boolean isReleasedCloseToInnerEdge(View view) {
        return view.getLeft() > (getHiddenOffset() + getExpandedOffset()) / 2;
    }

    /* access modifiers changed from: package-private */
    public boolean isSwipeSignificant(float f, float f2) {
        return SheetUtils.isSwipeMostlyHorizontal(f, f2) && Math.abs(f) > ((float) this.sheetBehavior.getSignificantVelocityThreshold());
    }

    /* access modifiers changed from: package-private */
    public boolean shouldHide(View view, float f) {
        return Math.abs(((float) view.getRight()) + (f * this.sheetBehavior.getHideFriction())) > this.sheetBehavior.getHideThreshold();
    }

    /* access modifiers changed from: package-private */
    public int getOuterEdge(View view) {
        return view.getLeft() - this.sheetBehavior.getInnerMargin();
    }

    /* access modifiers changed from: package-private */
    public float calculateSlideOffset(int i) {
        float hiddenOffset = (float) getHiddenOffset();
        return (hiddenOffset - ((float) i)) / (hiddenOffset - ((float) getExpandedOffset()));
    }

    /* access modifiers changed from: package-private */
    public void updateCoplanarSiblingLayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams, int i, int i2) {
        int parentWidth = this.sheetBehavior.getParentWidth();
        if (i <= parentWidth) {
            marginLayoutParams.rightMargin = parentWidth - i;
        }
    }

    public int getParentInnerEdge(CoordinatorLayout coordinatorLayout) {
        return coordinatorLayout.getRight();
    }

    /* access modifiers changed from: package-private */
    public int calculateInnerMargin(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return marginLayoutParams.rightMargin;
    }

    /* access modifiers changed from: package-private */
    public int getMinViewPositionHorizontal() {
        return getExpandedOffset();
    }

    /* access modifiers changed from: package-private */
    public int getMaxViewPositionHorizontal() {
        return this.sheetBehavior.getParentWidth();
    }
}
