package com.google.android.material.badge;

import android.graphics.drawable.Drawable;
import com.google.android.material.internal.TextDrawableHelper;

public abstract class BadgeDrawable extends Drawable implements TextDrawableHelper.TextDrawableDelegate {
    public abstract boolean onStateChange(int[] iArr);
}
