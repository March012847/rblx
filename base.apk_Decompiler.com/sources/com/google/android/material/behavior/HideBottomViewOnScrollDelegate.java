package com.google.android.material.behavior;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

final class HideBottomViewOnScrollDelegate extends HideViewOnScrollDelegate {
    /* access modifiers changed from: package-private */
    public int getTargetTranslation() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int getViewEdge() {
        return 1;
    }

    HideBottomViewOnScrollDelegate() {
    }

    /* access modifiers changed from: package-private */
    public int getSize(View view, ViewGroup.MarginLayoutParams marginLayoutParams) {
        return view.getMeasuredHeight() + marginLayoutParams.bottomMargin;
    }

    /* access modifiers changed from: package-private */
    public void setViewTranslation(View view, int i) {
        view.setTranslationY((float) i);
    }

    /* access modifiers changed from: package-private */
    public ViewPropertyAnimator getViewTranslationAnimator(View view, int i) {
        return view.animate().translationY((float) i);
    }
}
