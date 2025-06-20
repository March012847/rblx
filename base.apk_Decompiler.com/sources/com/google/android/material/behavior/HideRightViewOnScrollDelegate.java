package com.google.android.material.behavior;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

final class HideRightViewOnScrollDelegate extends HideViewOnScrollDelegate {
    /* access modifiers changed from: package-private */
    public int getTargetTranslation() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int getViewEdge() {
        return 0;
    }

    HideRightViewOnScrollDelegate() {
    }

    /* access modifiers changed from: package-private */
    public int getSize(View view, ViewGroup.MarginLayoutParams marginLayoutParams) {
        return view.getMeasuredWidth() + marginLayoutParams.rightMargin;
    }

    /* access modifiers changed from: package-private */
    public void setViewTranslation(View view, int i) {
        view.setTranslationX((float) i);
    }

    /* access modifiers changed from: package-private */
    public ViewPropertyAnimator getViewTranslationAnimator(View view, int i) {
        return view.animate().translationX((float) i);
    }
}
