package com.google.android.material.sidesheet;

import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

final class LeftSheetDelegate extends SheetDelegate {
    final SideSheetBehavior sheetBehavior;

    /* access modifiers changed from: package-private */
    public int getSheetEdge() {
        return 1;
    }

    /* access modifiers changed from: package-private */
    public boolean isExpandingOutwards(float f) {
        return f > 0.0f;
    }

    LeftSheetDelegate(SideSheetBehavior sideSheetBehavior) {
        this.sheetBehavior = sideSheetBehavior;
    }

    /* access modifiers changed from: package-private */
    public int getHiddenOffset() {
        return (-this.sheetBehavior.getChildWidth()) - this.sheetBehavior.getInnerMargin();
    }

    /* access modifiers changed from: package-private */
    public int getExpandedOffset() {
        return Math.max(0, this.sheetBehavior.getParentInnerEdge() + this.sheetBehavior.getInnerMargin());
    }

    /* access modifiers changed from: package-private */
    public boolean isReleasedCloseToInnerEdge(View view) {
        return view.getRight() < (getExpandedOffset() - getHiddenOffset()) / 2;
    }

    /* access modifiers changed from: package-private */
    public boolean isSwipeSignificant(float f, float f2) {
        return SheetUtils.isSwipeMostlyHorizontal(f, f2) && Math.abs(f) > ((float) this.sheetBehavior.getSignificantVelocityThreshold());
    }

    /* access modifiers changed from: package-private */
    public boolean shouldHide(View view, float f) {
        return Math.abs(((float) view.getLeft()) + (f * this.sheetBehavior.getHideFriction())) > this.sheetBehavior.getHideThreshold();
    }

    /* access modifiers changed from: package-private */
    public int getOuterEdge(View view) {
        return view.getRight() + this.sheetBehavior.getInnerMargin();
    }

    /* access modifiers changed from: package-private */
    public float calculateSlideOffset(int i) {
        float hiddenOffset = (float) getHiddenOffset();
        return (((float) i) - hiddenOffset) / (((float) getExpandedOffset()) - hiddenOffset);
    }

    /* access modifiers changed from: package-private */
    public void updateCoplanarSiblingLayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams, int i, int i2) {
        if (i <= this.sheetBehavior.getParentWidth()) {
            marginLayoutParams.leftMargin = i2;
        }
    }

    public int getParentInnerEdge(CoordinatorLayout coordinatorLayout) {
        return coordinatorLayout.getLeft();
    }

    /* access modifiers changed from: package-private */
    public int calculateInnerMargin(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return marginLayoutParams.leftMargin;
    }

    /* access modifiers changed from: package-private */
    public int getMinViewPositionHorizontal() {
        return -this.sheetBehavior.getChildWidth();
    }

    /* access modifiers changed from: package-private */
    public int getMaxViewPositionHorizontal() {
        return this.sheetBehavior.getInnerMargin();
    }
}
