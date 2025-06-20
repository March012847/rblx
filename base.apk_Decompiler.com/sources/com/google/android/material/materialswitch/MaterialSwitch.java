package com.google.android.material.materialswitch;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.R$attr;
import com.google.android.material.R$style;
import com.google.android.material.drawable.DrawableUtils;

public class MaterialSwitch extends SwitchCompat {
    private static final int DEF_STYLE_RES = R$style.Widget_Material3_CompoundButton_MaterialSwitch;
    private static final int[] STATE_SET_WITH_ICON = {R$attr.state_with_icon};
    private int[] currentStateChecked;
    private int[] currentStateUnchecked;
    private Drawable thumbDrawable;
    private Drawable thumbIconDrawable;
    private int thumbIconSize;
    private ColorStateList thumbIconTintList;
    private PorterDuff.Mode thumbIconTintMode;
    private ColorStateList thumbTintList;
    private Drawable trackDecorationDrawable;
    private ColorStateList trackDecorationTintList;
    private PorterDuff.Mode trackDecorationTintMode;
    private Drawable trackDrawable;
    private ColorStateList trackTintList;

    public MaterialSwitch(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialSwitch(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.materialSwitchStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MaterialSwitch(android.content.Context r8, android.util.AttributeSet r9, int r10) {
        /*
            r7 = this;
            int r4 = DEF_STYLE_RES
            android.content.Context r8 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r8, r9, r10, r4)
            r7.<init>(r8, r9, r10)
            r8 = -1
            r7.thumbIconSize = r8
            android.content.Context r0 = r7.getContext()
            android.graphics.drawable.Drawable r1 = super.getThumbDrawable()
            r7.thumbDrawable = r1
            android.content.res.ColorStateList r1 = super.getThumbTintList()
            r7.thumbTintList = r1
            r1 = 0
            super.setThumbTintList(r1)
            android.graphics.drawable.Drawable r2 = super.getTrackDrawable()
            r7.trackDrawable = r2
            android.content.res.ColorStateList r2 = super.getTrackTintList()
            r7.trackTintList = r2
            super.setTrackTintList(r1)
            int[] r2 = com.google.android.material.R$styleable.MaterialSwitch
            r6 = 0
            int[] r5 = new int[r6]
            r1 = r9
            r3 = r10
            androidx.appcompat.widget.TintTypedArray r9 = com.google.android.material.internal.ThemeEnforcement.obtainTintedStyledAttributes(r0, r1, r2, r3, r4, r5)
            int r10 = com.google.android.material.R$styleable.MaterialSwitch_thumbIcon
            android.graphics.drawable.Drawable r10 = r9.getDrawable(r10)
            r7.thumbIconDrawable = r10
            int r10 = com.google.android.material.R$styleable.MaterialSwitch_thumbIconSize
            int r10 = r9.getDimensionPixelSize(r10, r8)
            r7.thumbIconSize = r10
            int r10 = com.google.android.material.R$styleable.MaterialSwitch_thumbIconTint
            android.content.res.ColorStateList r10 = r9.getColorStateList(r10)
            r7.thumbIconTintList = r10
            int r10 = com.google.android.material.R$styleable.MaterialSwitch_thumbIconTintMode
            int r10 = r9.getInt(r10, r8)
            android.graphics.PorterDuff$Mode r0 = android.graphics.PorterDuff.Mode.SRC_IN
            android.graphics.PorterDuff$Mode r10 = com.google.android.material.internal.ViewUtils.parseTintMode(r10, r0)
            r7.thumbIconTintMode = r10
            int r10 = com.google.android.material.R$styleable.MaterialSwitch_trackDecoration
            android.graphics.drawable.Drawable r10 = r9.getDrawable(r10)
            r7.trackDecorationDrawable = r10
            int r10 = com.google.android.material.R$styleable.MaterialSwitch_trackDecorationTint
            android.content.res.ColorStateList r10 = r9.getColorStateList(r10)
            r7.trackDecorationTintList = r10
            int r10 = com.google.android.material.R$styleable.MaterialSwitch_trackDecorationTintMode
            int r8 = r9.getInt(r10, r8)
            android.graphics.PorterDuff$Mode r8 = com.google.android.material.internal.ViewUtils.parseTintMode(r8, r0)
            r7.trackDecorationTintMode = r8
            r9.recycle()
            r7.setEnforceSwitchWidth(r6)
            r7.refreshThumbDrawable()
            r7.refreshTrackDrawable()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.materialswitch.MaterialSwitch.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public void invalidate() {
        updateDrawableTints();
        super.invalidate();
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (this.thumbIconDrawable != null) {
            CompoundButton.mergeDrawableStates(onCreateDrawableState, STATE_SET_WITH_ICON);
        }
        this.currentStateUnchecked = DrawableUtils.getUncheckedState(onCreateDrawableState);
        this.currentStateChecked = DrawableUtils.getCheckedState(onCreateDrawableState);
        return onCreateDrawableState;
    }

    private void refreshThumbDrawable() {
        this.thumbDrawable = DrawableUtils.createTintableDrawableIfNeeded(this.thumbDrawable, this.thumbTintList, getThumbTintMode());
        this.thumbIconDrawable = DrawableUtils.createTintableDrawableIfNeeded(this.thumbIconDrawable, this.thumbIconTintList, this.thumbIconTintMode);
        updateDrawableTints();
        Drawable drawable = this.thumbDrawable;
        Drawable drawable2 = this.thumbIconDrawable;
        int i = this.thumbIconSize;
        super.setThumbDrawable(DrawableUtils.compositeTwoLayeredDrawable(drawable, drawable2, i, i));
        refreshDrawableState();
    }

    private void refreshTrackDrawable() {
        this.trackDrawable = DrawableUtils.createTintableDrawableIfNeeded(this.trackDrawable, this.trackTintList, getTrackTintMode());
        this.trackDecorationDrawable = DrawableUtils.createTintableDrawableIfNeeded(this.trackDecorationDrawable, this.trackDecorationTintList, this.trackDecorationTintMode);
        updateDrawableTints();
        Drawable drawable = this.trackDrawable;
        if (drawable != null && this.trackDecorationDrawable != null) {
            drawable = new LayerDrawable(new Drawable[]{this.trackDrawable, this.trackDecorationDrawable});
        } else if (drawable == null) {
            drawable = this.trackDecorationDrawable;
        }
        if (drawable != null) {
            setSwitchMinWidth(drawable.getIntrinsicWidth());
        }
        super.setTrackDrawable(drawable);
    }

    private void updateDrawableTints() {
        if (this.thumbTintList != null || this.thumbIconTintList != null || this.trackTintList != null || this.trackDecorationTintList != null) {
            float thumbPosition = getThumbPosition();
            ColorStateList colorStateList = this.thumbTintList;
            if (colorStateList != null) {
                setInterpolatedDrawableTintIfPossible(this.thumbDrawable, colorStateList, this.currentStateUnchecked, this.currentStateChecked, thumbPosition);
            }
            ColorStateList colorStateList2 = this.thumbIconTintList;
            if (colorStateList2 != null) {
                setInterpolatedDrawableTintIfPossible(this.thumbIconDrawable, colorStateList2, this.currentStateUnchecked, this.currentStateChecked, thumbPosition);
            }
            ColorStateList colorStateList3 = this.trackTintList;
            if (colorStateList3 != null) {
                setInterpolatedDrawableTintIfPossible(this.trackDrawable, colorStateList3, this.currentStateUnchecked, this.currentStateChecked, thumbPosition);
            }
            ColorStateList colorStateList4 = this.trackDecorationTintList;
            if (colorStateList4 != null) {
                setInterpolatedDrawableTintIfPossible(this.trackDecorationDrawable, colorStateList4, this.currentStateUnchecked, this.currentStateChecked, thumbPosition);
            }
        }
    }

    private static void setInterpolatedDrawableTintIfPossible(Drawable drawable, ColorStateList colorStateList, int[] iArr, int[] iArr2, float f) {
        if (drawable != null && colorStateList != null) {
            drawable.setTint(ColorUtils.blendARGB(colorStateList.getColorForState(iArr, 0), colorStateList.getColorForState(iArr2, 0), f));
        }
    }
}
