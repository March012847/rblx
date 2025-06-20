package com.google.android.material.divider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.material.R$attr;
import com.google.android.material.R$style;
import com.google.android.material.shape.MaterialShapeDrawable;

public class MaterialDivider extends View {
    private static final int DEF_STYLE_RES = R$style.Widget_MaterialComponents_MaterialDivider;
    private int color;
    private final MaterialShapeDrawable dividerDrawable;
    private int insetEnd;
    private int insetStart;
    private int thickness;

    public MaterialDivider(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialDivider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.materialDividerStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MaterialDivider(android.content.Context r7, android.util.AttributeSet r8, int r9) {
        /*
            r6 = this;
            int r4 = DEF_STYLE_RES
            android.content.Context r7 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r7, r8, r9, r4)
            r6.<init>(r7, r8, r9)
            android.content.Context r0 = r6.getContext()
            com.google.android.material.shape.MaterialShapeDrawable r7 = new com.google.android.material.shape.MaterialShapeDrawable
            r7.<init>()
            r6.dividerDrawable = r7
            int[] r2 = com.google.android.material.R$styleable.MaterialDivider
            r7 = 0
            int[] r5 = new int[r7]
            r1 = r8
            r3 = r9
            android.content.res.TypedArray r8 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r0, r1, r2, r3, r4, r5)
            int r9 = com.google.android.material.R$styleable.MaterialDivider_dividerThickness
            android.content.res.Resources r1 = r6.getResources()
            int r2 = com.google.android.material.R$dimen.material_divider_thickness
            int r1 = r1.getDimensionPixelSize(r2)
            int r9 = r8.getDimensionPixelSize(r9, r1)
            r6.thickness = r9
            int r9 = com.google.android.material.R$styleable.MaterialDivider_dividerInsetStart
            int r9 = r8.getDimensionPixelOffset(r9, r7)
            r6.insetStart = r9
            int r9 = com.google.android.material.R$styleable.MaterialDivider_dividerInsetEnd
            int r7 = r8.getDimensionPixelOffset(r9, r7)
            r6.insetEnd = r7
            int r7 = com.google.android.material.R$styleable.MaterialDivider_dividerColor
            android.content.res.ColorStateList r7 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r0, (android.content.res.TypedArray) r8, (int) r7)
            int r7 = r7.getDefaultColor()
            r6.setDividerColor(r7)
            r8.recycle()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.divider.MaterialDivider.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i2);
        int measuredHeight = getMeasuredHeight();
        if (mode == Integer.MIN_VALUE || mode == 0) {
            int i3 = this.thickness;
            if (i3 > 0 && measuredHeight != i3) {
                measuredHeight = i3;
            }
            setMeasuredDimension(getMeasuredWidth(), measuredHeight);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int width;
        int i;
        super.onDraw(canvas);
        boolean z = true;
        if (getLayoutDirection() != 1) {
            z = false;
        }
        int i2 = z ? this.insetEnd : this.insetStart;
        if (z) {
            width = getWidth();
            i = this.insetStart;
        } else {
            width = getWidth();
            i = this.insetEnd;
        }
        this.dividerDrawable.setBounds(i2, 0, width - i, getBottom() - getTop());
        this.dividerDrawable.draw(canvas);
    }

    public void setDividerColor(int i) {
        if (this.color != i) {
            this.color = i;
            this.dividerDrawable.setFillColor(ColorStateList.valueOf(i));
            invalidate();
        }
    }
}
