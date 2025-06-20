package com.google.android.material.behavior;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

abstract class HideViewOnScrollDelegate {
    /* access modifiers changed from: package-private */
    public abstract int getSize(View view, ViewGroup.MarginLayoutParams marginLayoutParams);

    /* access modifiers changed from: package-private */
    public abstract int getTargetTranslation();

    /* access modifiers changed from: package-private */
    public abstract int getViewEdge();

    /* access modifiers changed from: package-private */
    public abstract ViewPropertyAnimator getViewTranslationAnimator(View view, int i);

    /* access modifiers changed from: package-private */
    public abstract void setViewTranslation(View view, int i);

    HideViewOnScrollDelegate() {
    }
}
