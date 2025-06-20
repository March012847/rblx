package com.google.android.material.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.customview.view.AbsSavedState;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.google.android.material.R$attr;
import com.google.android.material.R$style;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.shape.StateListShapeAppearanceModel;
import com.google.android.material.shape.StateListSizeChange;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class MaterialButton extends AppCompatButton implements Checkable, Shapeable {
    private static final int[] CHECKABLE_STATE_SET = {16842911};
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final int DEF_STYLE_RES = R$style.Widget_MaterialComponents_Button;
    private static final int MATERIAL_SIZE_OVERLAY_ATTR = R$attr.materialSizeOverlay;
    private static final FloatPropertyCompat WIDTH_INCREASE = new FloatPropertyCompat("widthIncrease") {
        public float getValue(MaterialButton materialButton) {
            return materialButton.getDisplayedWidthIncrease();
        }

        public void setValue(MaterialButton materialButton, float f) {
            materialButton.setDisplayedWidthIncrease(f);
        }
    };
    private String accessibilityClassName;
    int allowedWidthDecrease;
    private boolean broadcasting;
    private boolean checked;
    private float displayedWidthDecrease;
    private float displayedWidthIncrease;
    private Drawable icon;
    private int iconGravity;
    private int iconLeft;
    private int iconPadding;
    private int iconSize;
    private ColorStateList iconTint;
    private PorterDuff.Mode iconTintMode;
    private int iconTop;
    private boolean isInHorizontalButtonGroup;
    private final MaterialButtonHelper materialButtonHelper;
    private final LinkedHashSet onCheckedChangeListeners;
    private OnPressedChangeListener onPressedChangeListenerInternal;
    private boolean opticalCenterEnabled;
    private int opticalCenterShift;
    private int orientation;
    private LinearLayout.LayoutParams originalLayoutParams;
    private int originalPaddingEnd;
    private int originalPaddingStart;
    private float originalWidth;
    StateListSizeChange sizeChange;
    int widthChangeMax;
    private SpringAnimation widthIncreaseSpringAnimation;

    interface OnPressedChangeListener {
        void onPressedChanged(MaterialButton materialButton, boolean z);
    }

    public MaterialButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.materialButtonStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MaterialButton(android.content.Context r8, android.util.AttributeSet r9, int r10) {
        /*
            r7 = this;
            int r4 = DEF_STYLE_RES
            int r0 = MATERIAL_SIZE_OVERLAY_ATTR
            int[] r0 = new int[]{r0}
            android.content.Context r8 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r8, r9, r10, r4, r0)
            r7.<init>(r8, r9, r10)
            java.util.LinkedHashSet r8 = new java.util.LinkedHashSet
            r8.<init>()
            r7.onCheckedChangeListeners = r8
            r8 = 0
            r7.checked = r8
            r7.broadcasting = r8
            r6 = -1
            r7.orientation = r6
            r0 = -1082130432(0xffffffffbf800000, float:-1.0)
            r7.originalWidth = r0
            r7.originalPaddingStart = r6
            r7.originalPaddingEnd = r6
            r7.allowedWidthDecrease = r6
            android.content.Context r0 = r7.getContext()
            int[] r2 = com.google.android.material.R$styleable.MaterialButton
            int[] r5 = new int[r8]
            r1 = r9
            r3 = r10
            android.content.res.TypedArray r9 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r0, r1, r2, r3, r4, r5)
            int r10 = com.google.android.material.R$styleable.MaterialButton_iconPadding
            int r10 = r9.getDimensionPixelSize(r10, r8)
            r7.iconPadding = r10
            int r10 = com.google.android.material.R$styleable.MaterialButton_iconTintMode
            int r10 = r9.getInt(r10, r6)
            android.graphics.PorterDuff$Mode r2 = android.graphics.PorterDuff.Mode.SRC_IN
            android.graphics.PorterDuff$Mode r10 = com.google.android.material.internal.ViewUtils.parseTintMode(r10, r2)
            r7.iconTintMode = r10
            android.content.Context r10 = r7.getContext()
            int r2 = com.google.android.material.R$styleable.MaterialButton_iconTint
            android.content.res.ColorStateList r10 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r10, (android.content.res.TypedArray) r9, (int) r2)
            r7.iconTint = r10
            android.content.Context r10 = r7.getContext()
            int r2 = com.google.android.material.R$styleable.MaterialButton_icon
            android.graphics.drawable.Drawable r10 = com.google.android.material.resources.MaterialResources.getDrawable(r10, r9, r2)
            r7.icon = r10
            int r10 = com.google.android.material.R$styleable.MaterialButton_iconGravity
            r2 = 1
            int r10 = r9.getInteger(r10, r2)
            r7.iconGravity = r10
            int r10 = com.google.android.material.R$styleable.MaterialButton_iconSize
            int r10 = r9.getDimensionPixelSize(r10, r8)
            r7.iconSize = r10
            int r10 = com.google.android.material.R$styleable.MaterialButton_shapeAppearance
            com.google.android.material.shape.StateListShapeAppearanceModel r10 = com.google.android.material.shape.StateListShapeAppearanceModel.create(r0, r9, r10)
            if (r10 == 0) goto L_0x0082
            com.google.android.material.shape.ShapeAppearanceModel r0 = r10.getDefaultShape(r2)
            goto L_0x008a
        L_0x0082:
            com.google.android.material.shape.ShapeAppearanceModel$Builder r0 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r0, (android.util.AttributeSet) r1, (int) r3, (int) r4)
            com.google.android.material.shape.ShapeAppearanceModel r0 = r0.build()
        L_0x008a:
            int r1 = com.google.android.material.R$styleable.MaterialButton_opticalCenterEnabled
            boolean r1 = r9.getBoolean(r1, r8)
            com.google.android.material.button.MaterialButtonHelper r3 = new com.google.android.material.button.MaterialButtonHelper
            r3.<init>(r7, r0)
            r7.materialButtonHelper = r3
            r3.loadFromAttributes(r9)
            if (r10 == 0) goto L_0x00a6
            androidx.dynamicanimation.animation.SpringForce r0 = r7.createSpringForce()
            r3.setCornerSpringForce(r0)
            r3.setStateListShapeAppearanceModel(r10)
        L_0x00a6:
            r7.setOpticalCenterEnabled(r1)
            r9.recycle()
            int r9 = r7.iconPadding
            r7.setCompoundDrawablePadding(r9)
            android.graphics.drawable.Drawable r9 = r7.icon
            if (r9 == 0) goto L_0x00b6
            r8 = r2
        L_0x00b6:
            r7.updateIcon(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.button.MaterialButton.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    private void initializeSizeAnimation() {
        SpringAnimation springAnimation = new SpringAnimation(this, WIDTH_INCREASE);
        this.widthIncreaseSpringAnimation = springAnimation;
        springAnimation.setSpring(createSpringForce());
    }

    private SpringForce createSpringForce() {
        return MotionUtils.resolveThemeSpringForce(getContext(), R$attr.motionSpringFastSpatial, R$style.Motion_Material3_Spring_Standard_Fast_Spatial);
    }

    /* access modifiers changed from: package-private */
    public String getA11yClassName() {
        if (!TextUtils.isEmpty(this.accessibilityClassName)) {
            return this.accessibilityClassName;
        }
        return (isCheckable() ? CompoundButton.class : Button.class).getName();
    }

    public void setA11yClassName(String str) {
        this.accessibilityClassName = str;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(getA11yClassName());
        accessibilityNodeInfo.setCheckable(isCheckable());
        accessibilityNodeInfo.setChecked(isChecked());
        accessibilityNodeInfo.setClickable(isClickable());
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(getA11yClassName());
        accessibilityEvent.setChecked(isChecked());
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checked = this.checked;
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setChecked(savedState.checked);
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setSupportBackgroundTintList(colorStateList);
        } else {
            super.setSupportBackgroundTintList(colorStateList);
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getSupportBackgroundTintList();
        }
        return super.getSupportBackgroundTintList();
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setSupportBackgroundTintMode(mode);
        } else {
            super.setSupportBackgroundTintMode(mode);
        }
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getSupportBackgroundTintMode();
        }
        return super.getSupportBackgroundTintMode();
    }

    public void setBackgroundTintList(ColorStateList colorStateList) {
        setSupportBackgroundTintList(colorStateList);
    }

    public ColorStateList getBackgroundTintList() {
        return getSupportBackgroundTintList();
    }

    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        setSupportBackgroundTintMode(mode);
    }

    public PorterDuff.Mode getBackgroundTintMode() {
        return getSupportBackgroundTintMode();
    }

    public void setBackgroundColor(int i) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setBackgroundColor(i);
        } else {
            super.setBackgroundColor(i);
        }
    }

    public void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    public void setBackgroundResource(int i) {
        setBackgroundDrawable(i != 0 ? AppCompatResources.getDrawable(getContext(), i) : null);
    }

    public void setBackgroundDrawable(Drawable drawable) {
        if (!isUsingOriginalBackground()) {
            super.setBackgroundDrawable(drawable);
        } else if (drawable != getBackground()) {
            Log.w("MaterialButton", "MaterialButton manages its own background to control elevation, shape, color and states. Consider using backgroundTint, shapeAppearance and other attributes where available. A custom background will ignore these attributes and you should consider handling interaction states such as pressed, focused and disabled");
            this.materialButtonHelper.setBackgroundOverwritten();
            super.setBackgroundDrawable(drawable);
        } else {
            getBackground().setState(drawable.getState());
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        super.onLayout(z, i, i2, i3, i4);
        updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
        int i6 = getResources().getConfiguration().orientation;
        if (this.orientation != i6) {
            this.orientation = i6;
            this.originalWidth = -1.0f;
        }
        if (this.originalWidth == -1.0f) {
            this.originalWidth = (float) getMeasuredWidth();
            if (this.originalLayoutParams == null && (getParent() instanceof MaterialButtonGroup) && ((MaterialButtonGroup) getParent()).getButtonSizeChange() != null) {
                this.originalLayoutParams = (LinearLayout.LayoutParams) getLayoutParams();
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this.originalLayoutParams);
                layoutParams.width = (int) this.originalWidth;
                setLayoutParams(layoutParams);
            }
        }
        if (this.allowedWidthDecrease == -1) {
            if (this.icon == null) {
                i5 = 0;
            } else {
                int iconPadding2 = getIconPadding();
                int i7 = this.iconSize;
                if (i7 == 0) {
                    i7 = this.icon.getIntrinsicWidth();
                }
                i5 = iconPadding2 + i7;
            }
            this.allowedWidthDecrease = (getMeasuredWidth() - getTextLayoutWidth()) - i5;
        }
        if (this.originalPaddingStart == -1) {
            this.originalPaddingStart = getPaddingStart();
        }
        if (this.originalPaddingEnd == -1) {
            this.originalPaddingEnd = getPaddingEnd();
        }
    }

    /* access modifiers changed from: package-private */
    public void recoverOriginalLayoutParams() {
        LinearLayout.LayoutParams layoutParams = this.originalLayoutParams;
        if (layoutParams != null) {
            setLayoutParams(layoutParams);
            this.originalLayoutParams = null;
            this.originalWidth = -1.0f;
        }
    }

    public void setWidth(int i) {
        this.originalWidth = -1.0f;
        super.setWidth(i);
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isUsingOriginalBackground()) {
            MaterialShapeUtils.setParentAbsoluteElevation(this, this.materialButtonHelper.getMaterialShapeDrawable());
        }
        this.isInHorizontalButtonGroup = isInHorizontalButtonGroup();
    }

    public void setElevation(float f) {
        super.setElevation(f);
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.getMaterialShapeDrawable().setElevation(f);
        }
    }

    public void refreshDrawableState() {
        super.refreshDrawableState();
        if (this.icon != null) {
            if (this.icon.setState(getDrawableState())) {
                invalidate();
            }
        }
    }

    public void setTextAlignment(int i) {
        super.setTextAlignment(i);
        updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
    }

    private Layout.Alignment getGravityTextAlignment() {
        int gravity = getGravity() & 8388615;
        if (gravity == 1) {
            return Layout.Alignment.ALIGN_CENTER;
        }
        if (gravity == 5 || gravity == 8388613) {
            return Layout.Alignment.ALIGN_OPPOSITE;
        }
        return Layout.Alignment.ALIGN_NORMAL;
    }

    private Layout.Alignment getActualTextAlignment() {
        int textAlignment = getTextAlignment();
        if (textAlignment == 1) {
            return getGravityTextAlignment();
        }
        if (textAlignment == 6 || textAlignment == 3) {
            return Layout.Alignment.ALIGN_OPPOSITE;
        }
        if (textAlignment != 4) {
            return Layout.Alignment.ALIGN_NORMAL;
        }
        return Layout.Alignment.ALIGN_CENTER;
    }

    private void updateIconPosition(int i, int i2) {
        if (this.icon != null && getLayout() != null) {
            if (isIconStart() || isIconEnd()) {
                this.iconTop = 0;
                Layout.Alignment actualTextAlignment = getActualTextAlignment();
                int i3 = this.iconGravity;
                boolean z = true;
                if (i3 == 1 || i3 == 3 || ((i3 == 2 && actualTextAlignment == Layout.Alignment.ALIGN_NORMAL) || (i3 == 4 && actualTextAlignment == Layout.Alignment.ALIGN_OPPOSITE))) {
                    this.iconLeft = 0;
                    updateIcon(false);
                    return;
                }
                int i4 = this.iconSize;
                if (i4 == 0) {
                    i4 = this.icon.getIntrinsicWidth();
                }
                int textLayoutWidth = ((((i - getTextLayoutWidth()) - getPaddingEnd()) - i4) - this.iconPadding) - getPaddingStart();
                if (actualTextAlignment == Layout.Alignment.ALIGN_CENTER) {
                    textLayoutWidth /= 2;
                }
                boolean isLayoutRTL = isLayoutRTL();
                if (this.iconGravity != 4) {
                    z = false;
                }
                if (isLayoutRTL != z) {
                    textLayoutWidth = -textLayoutWidth;
                }
                if (this.iconLeft != textLayoutWidth) {
                    this.iconLeft = textLayoutWidth;
                    updateIcon(false);
                }
            } else if (isIconTop()) {
                this.iconLeft = 0;
                if (this.iconGravity == 16) {
                    this.iconTop = 0;
                    updateIcon(false);
                    return;
                }
                int i5 = this.iconSize;
                if (i5 == 0) {
                    i5 = this.icon.getIntrinsicHeight();
                }
                int max = Math.max(0, (((((i2 - getTextHeight()) - getPaddingTop()) - i5) - this.iconPadding) - getPaddingBottom()) / 2);
                if (this.iconTop != max) {
                    this.iconTop = max;
                    updateIcon(false);
                }
            }
        }
    }

    private int getTextLayoutWidth() {
        int lineCount = getLineCount();
        float f = 0.0f;
        for (int i = 0; i < lineCount; i++) {
            f = Math.max(f, getLayout().getLineWidth(i));
        }
        return (int) Math.ceil((double) f);
    }

    private int getTextHeight() {
        if (getLineCount() > 1) {
            return getLayout().getHeight();
        }
        TextPaint paint = getPaint();
        String charSequence = getText().toString();
        if (getTransformationMethod() != null) {
            charSequence = getTransformationMethod().getTransformation(charSequence, this).toString();
        }
        Rect rect = new Rect();
        paint.getTextBounds(charSequence, 0, charSequence.length(), rect);
        return Math.min(rect.height(), getLayout().getHeight());
    }

    private boolean isLayoutRTL() {
        return getLayoutDirection() == 1;
    }

    /* access modifiers changed from: package-private */
    public void setInternalBackground(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
    }

    public int getIconPadding() {
        return this.iconPadding;
    }

    private void updateIcon(boolean z) {
        Drawable drawable = this.icon;
        if (drawable != null) {
            Drawable mutate = DrawableCompat.wrap(drawable).mutate();
            this.icon = mutate;
            mutate.setTintList(this.iconTint);
            PorterDuff.Mode mode = this.iconTintMode;
            if (mode != null) {
                this.icon.setTintMode(mode);
            }
            int i = this.iconSize;
            if (i == 0) {
                i = this.icon.getIntrinsicWidth();
            }
            int i2 = this.iconSize;
            if (i2 == 0) {
                i2 = this.icon.getIntrinsicHeight();
            }
            Drawable drawable2 = this.icon;
            int i3 = this.iconLeft;
            int i4 = this.iconTop;
            drawable2.setBounds(i3, i4, i + i3, i2 + i4);
            this.icon.setVisible(true, z);
        }
        if (z) {
            resetIconDrawable();
            return;
        }
        Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
        Drawable drawable3 = compoundDrawablesRelative[0];
        Drawable drawable4 = compoundDrawablesRelative[1];
        Drawable drawable5 = compoundDrawablesRelative[2];
        if ((isIconStart() && drawable3 != this.icon) || ((isIconEnd() && drawable5 != this.icon) || (isIconTop() && drawable4 != this.icon))) {
            resetIconDrawable();
        }
    }

    private void resetIconDrawable() {
        if (isIconStart()) {
            setCompoundDrawablesRelative(this.icon, (Drawable) null, (Drawable) null, (Drawable) null);
        } else if (isIconEnd()) {
            setCompoundDrawablesRelative((Drawable) null, (Drawable) null, this.icon, (Drawable) null);
        } else if (isIconTop()) {
            setCompoundDrawablesRelative((Drawable) null, this.icon, (Drawable) null, (Drawable) null);
        }
    }

    private boolean isIconStart() {
        int i = this.iconGravity;
        return i == 1 || i == 2;
    }

    private boolean isIconEnd() {
        int i = this.iconGravity;
        return i == 3 || i == 4;
    }

    private boolean isIconTop() {
        int i = this.iconGravity;
        return i == 16 || i == 32;
    }

    public int getStrokeWidth() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getStrokeWidth();
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 2);
        if (isCheckable()) {
            Button.mergeDrawableStates(onCreateDrawableState, CHECKABLE_STATE_SET);
        }
        if (isChecked()) {
            Button.mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    public void setChecked(boolean z) {
        if (isCheckable() && this.checked != z) {
            this.checked = z;
            refreshDrawableState();
            if (getParent() instanceof MaterialButtonToggleGroup) {
                ((MaterialButtonToggleGroup) getParent()).onButtonCheckedStateChanged(this, this.checked);
            }
            if (!this.broadcasting) {
                this.broadcasting = true;
                Iterator it = this.onCheckedChangeListeners.iterator();
                if (!it.hasNext()) {
                    this.broadcasting = false;
                } else {
                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                    throw null;
                }
            }
        }
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void toggle() {
        setChecked(!this.checked);
    }

    public boolean performClick() {
        if (isEnabled() && this.materialButtonHelper.isToggleCheckedStateOnClick()) {
            toggle();
        }
        return super.performClick();
    }

    public boolean isCheckable() {
        MaterialButtonHelper materialButtonHelper2 = this.materialButtonHelper;
        return materialButtonHelper2 != null && materialButtonHelper2.isCheckable();
    }

    public void setCheckable(boolean z) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setCheckable(z);
        }
    }

    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setShapeAppearanceModel(shapeAppearanceModel);
            return;
        }
        throw new IllegalStateException("Attempted to set ShapeAppearanceModel on a MaterialButton which has an overwritten background.");
    }

    public ShapeAppearanceModel getShapeAppearanceModel() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getShapeAppearanceModel();
        }
        throw new IllegalStateException("Attempted to get ShapeAppearanceModel from a MaterialButton which has an overwritten background.");
    }

    public void setStateListShapeAppearanceModel(StateListShapeAppearanceModel stateListShapeAppearanceModel) {
        if (isUsingOriginalBackground()) {
            if (this.materialButtonHelper.getCornerSpringForce() == null && stateListShapeAppearanceModel.isStateful()) {
                this.materialButtonHelper.setCornerSpringForce(createSpringForce());
            }
            this.materialButtonHelper.setStateListShapeAppearanceModel(stateListShapeAppearanceModel);
            return;
        }
        throw new IllegalStateException("Attempted to set StateListShapeAppearanceModel on a MaterialButton which has an overwritten background.");
    }

    public StateListShapeAppearanceModel getStateListShapeAppearanceModel() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getStateListShapeAppearanceModel();
        }
        throw new IllegalStateException("Attempted to get StateListShapeAppearanceModel from a MaterialButton which has an overwritten background.");
    }

    /* access modifiers changed from: package-private */
    public void setOnPressedChangeListenerInternal(OnPressedChangeListener onPressedChangeListener) {
        this.onPressedChangeListenerInternal = onPressedChangeListener;
    }

    public void setPressed(boolean z) {
        OnPressedChangeListener onPressedChangeListener = this.onPressedChangeListenerInternal;
        if (onPressedChangeListener != null) {
            onPressedChangeListener.onPressedChanged(this, z);
        }
        super.setPressed(z);
        maybeAnimateSize(false);
    }

    private boolean isUsingOriginalBackground() {
        MaterialButtonHelper materialButtonHelper2 = this.materialButtonHelper;
        return materialButtonHelper2 != null && !materialButtonHelper2.isBackgroundOverwritten();
    }

    /* access modifiers changed from: package-private */
    public void setShouldDrawSurfaceColorStroke(boolean z) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setShouldDrawSurfaceColorStroke(z);
        }
    }

    private void maybeAnimateSize(boolean z) {
        if (this.sizeChange != null) {
            if (this.widthIncreaseSpringAnimation == null) {
                initializeSizeAnimation();
            }
            if (this.isInHorizontalButtonGroup) {
                this.widthIncreaseSpringAnimation.animateToFinalPosition((float) Math.min(this.widthChangeMax, this.sizeChange.getSizeChangeForState(getDrawableState()).widthChange.getChange(getWidth())));
                if (z) {
                    this.widthIncreaseSpringAnimation.skipToEnd();
                }
            }
        }
    }

    private boolean isInHorizontalButtonGroup() {
        return (getParent() instanceof MaterialButtonGroup) && ((MaterialButtonGroup) getParent()).getOrientation() == 0;
    }

    /* access modifiers changed from: package-private */
    public void setSizeChange(StateListSizeChange stateListSizeChange) {
        if (this.sizeChange != stateListSizeChange) {
            this.sizeChange = stateListSizeChange;
            maybeAnimateSize(true);
        }
    }

    /* access modifiers changed from: package-private */
    public void setWidthChangeMax(int i) {
        if (this.widthChangeMax != i) {
            this.widthChangeMax = i;
            maybeAnimateSize(true);
        }
    }

    /* access modifiers changed from: package-private */
    public int getAllowedWidthDecrease() {
        return this.allowedWidthDecrease;
    }

    /* access modifiers changed from: private */
    public float getDisplayedWidthIncrease() {
        return this.displayedWidthIncrease;
    }

    /* access modifiers changed from: private */
    public void setDisplayedWidthIncrease(float f) {
        if (this.displayedWidthIncrease != f) {
            this.displayedWidthIncrease = f;
            updatePaddingsAndSizeForWidthAnimation();
            invalidate();
            if (getParent() instanceof MaterialButtonGroup) {
                ((MaterialButtonGroup) getParent()).onButtonWidthChanged(this, (int) this.displayedWidthIncrease);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setDisplayedWidthDecrease(int i) {
        this.displayedWidthDecrease = (float) Math.min(i, this.allowedWidthDecrease);
        updatePaddingsAndSizeForWidthAnimation();
        invalidate();
    }

    public void setOpticalCenterEnabled(boolean z) {
        if (this.opticalCenterEnabled != z) {
            this.opticalCenterEnabled = z;
            if (z) {
                this.materialButtonHelper.setCornerSizeChangeListener(new MaterialButton$$ExternalSyntheticLambda0(this));
            } else {
                this.materialButtonHelper.setCornerSizeChangeListener((MaterialShapeDrawable.OnCornerSizeChangeListener) null);
            }
            post(new MaterialButton$$ExternalSyntheticLambda1(this));
        }
    }

    public static /* synthetic */ void $r8$lambda$yIekC4ZOwE7h3DZWcK1xG7ISabc(MaterialButton materialButton, float f) {
        int i = (int) (f * 0.11f);
        if (materialButton.opticalCenterShift != i) {
            materialButton.opticalCenterShift = i;
            materialButton.updatePaddingsAndSizeForWidthAnimation();
            materialButton.invalidate();
        }
    }

    /* renamed from: $r8$lambda$sTDU5mTMH-dVAgylGXPenYNnUDs  reason: not valid java name */
    public static /* synthetic */ void m125$r8$lambda$sTDU5mTMHdVAgylGXPenYNnUDs(MaterialButton materialButton) {
        materialButton.opticalCenterShift = materialButton.getOpticalCenterShift();
        materialButton.updatePaddingsAndSizeForWidthAnimation();
        materialButton.invalidate();
    }

    private void updatePaddingsAndSizeForWidthAnimation() {
        int i = (int) (this.displayedWidthIncrease - this.displayedWidthDecrease);
        int i2 = (i / 2) + this.opticalCenterShift;
        getLayoutParams().width = (int) (this.originalWidth + ((float) i));
        setPaddingRelative(this.originalPaddingStart + i2, getPaddingTop(), (this.originalPaddingEnd + i) - i2, getPaddingBottom());
    }

    private int getOpticalCenterShift() {
        MaterialShapeDrawable materialShapeDrawable;
        if (!this.opticalCenterEnabled || !this.isInHorizontalButtonGroup || (materialShapeDrawable = this.materialButtonHelper.getMaterialShapeDrawable()) == null) {
            return 0;
        }
        return (int) (materialShapeDrawable.getCornerSizeDiffX() * 0.11f);
    }

    class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() {
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        boolean checked;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            if (classLoader == null) {
                getClass().getClassLoader();
            }
            readFromParcel(parcel);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.checked ? 1 : 0);
        }

        private void readFromParcel(Parcel parcel) {
            boolean z = true;
            if (parcel.readInt() != 1) {
                z = false;
            }
            this.checked = z;
        }
    }
}
