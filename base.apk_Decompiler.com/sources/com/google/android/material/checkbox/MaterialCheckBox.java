package com.google.android.material.checkbox;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.autofill.AutofillManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.widget.CompoundButtonCompat;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat$AnimationCallback;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import com.google.android.material.R$attr;
import com.google.android.material.R$id;
import com.google.android.material.R$string;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ViewUtils;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class MaterialCheckBox extends AppCompatCheckBox {
    private static final int[][] CHECKBOX_STATES;
    private static final int DEF_STYLE_RES = R$style.Widget_MaterialComponents_CompoundButton_CheckBox;
    private static final int[] ERROR_STATE_SET;
    private static final int FRAMEWORK_BUTTON_DRAWABLE_RES_ID = Resources.getSystem().getIdentifier("btn_check_material_anim", "drawable", "android");
    private static final int[] INDETERMINATE_STATE_SET = {R$attr.state_indeterminate};
    private boolean broadcasting;
    private Drawable buttonDrawable;
    private Drawable buttonIconDrawable;
    ColorStateList buttonIconTintList;
    private PorterDuff.Mode buttonIconTintMode;
    ColorStateList buttonTintList;
    private boolean centerIfNoTextEnabled;
    private int checkedState;
    /* access modifiers changed from: private */
    public int[] currentStateChecked;
    private CharSequence customStateDescription;
    private CharSequence errorAccessibilityLabel;
    private boolean errorShown;
    private ColorStateList materialThemeColorsTintList;
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    private final LinkedHashSet onCheckedStateChangedListeners;
    private final LinkedHashSet onErrorChangedListeners;
    private final AnimatedVectorDrawableCompat transitionToUnchecked;
    private final Animatable2Compat$AnimationCallback transitionToUncheckedCallback;
    private boolean useMaterialThemeColors;
    private boolean usingMaterialButtonDrawable;

    static {
        int i = R$attr.state_error;
        ERROR_STATE_SET = new int[]{i};
        CHECKBOX_STATES = new int[][]{new int[]{16842910, i}, new int[]{16842910, 16842912}, new int[]{16842910, -16842912}, new int[]{-16842910, 16842912}, new int[]{-16842910, -16842912}};
    }

    public MaterialCheckBox(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, androidx.appcompat.R$attr.checkboxStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MaterialCheckBox(android.content.Context r8, android.util.AttributeSet r9, int r10) {
        /*
            r7 = this;
            int r4 = DEF_STYLE_RES
            android.content.Context r8 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r8, r9, r10, r4)
            r7.<init>(r8, r9, r10)
            java.util.LinkedHashSet r8 = new java.util.LinkedHashSet
            r8.<init>()
            r7.onErrorChangedListeners = r8
            java.util.LinkedHashSet r8 = new java.util.LinkedHashSet
            r8.<init>()
            r7.onCheckedStateChangedListeners = r8
            android.content.Context r8 = r7.getContext()
            int r0 = com.google.android.material.R$drawable.mtrl_checkbox_button_checked_unchecked
            androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat r8 = androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat.create(r8, r0)
            r7.transitionToUnchecked = r8
            com.google.android.material.checkbox.MaterialCheckBox$1 r8 = new com.google.android.material.checkbox.MaterialCheckBox$1
            r8.<init>()
            r7.transitionToUncheckedCallback = r8
            android.content.Context r0 = r7.getContext()
            android.graphics.drawable.Drawable r8 = androidx.core.widget.CompoundButtonCompat.getButtonDrawable(r7)
            r7.buttonDrawable = r8
            android.content.res.ColorStateList r8 = r7.getSuperButtonTintList()
            r7.buttonTintList = r8
            r8 = 0
            r7.setSupportButtonTintList(r8)
            int[] r2 = com.google.android.material.R$styleable.MaterialCheckBox
            r6 = 0
            int[] r5 = new int[r6]
            r1 = r9
            r3 = r10
            androidx.appcompat.widget.TintTypedArray r9 = com.google.android.material.internal.ThemeEnforcement.obtainTintedStyledAttributes(r0, r1, r2, r3, r4, r5)
            int r10 = com.google.android.material.R$styleable.MaterialCheckBox_buttonIcon
            android.graphics.drawable.Drawable r10 = r9.getDrawable(r10)
            r7.buttonIconDrawable = r10
            android.graphics.drawable.Drawable r10 = r7.buttonDrawable
            r1 = 1
            if (r10 == 0) goto L_0x007b
            boolean r10 = com.google.android.material.internal.ThemeEnforcement.isMaterial3Theme(r0)
            if (r10 == 0) goto L_0x007b
            boolean r10 = r7.isButtonDrawableLegacy(r9)
            if (r10 == 0) goto L_0x007b
            super.setButtonDrawable((android.graphics.drawable.Drawable) r8)
            int r8 = com.google.android.material.R$drawable.mtrl_checkbox_button
            android.graphics.drawable.Drawable r8 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r0, r8)
            r7.buttonDrawable = r8
            r7.usingMaterialButtonDrawable = r1
            android.graphics.drawable.Drawable r8 = r7.buttonIconDrawable
            if (r8 != 0) goto L_0x007b
            int r8 = com.google.android.material.R$drawable.mtrl_checkbox_button_icon
            android.graphics.drawable.Drawable r8 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r0, r8)
            r7.buttonIconDrawable = r8
        L_0x007b:
            int r8 = com.google.android.material.R$styleable.MaterialCheckBox_buttonIconTint
            android.content.res.ColorStateList r8 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r0, (androidx.appcompat.widget.TintTypedArray) r9, (int) r8)
            r7.buttonIconTintList = r8
            int r8 = com.google.android.material.R$styleable.MaterialCheckBox_buttonIconTintMode
            r10 = -1
            int r8 = r9.getInt(r8, r10)
            android.graphics.PorterDuff$Mode r10 = android.graphics.PorterDuff.Mode.SRC_IN
            android.graphics.PorterDuff$Mode r8 = com.google.android.material.internal.ViewUtils.parseTintMode(r8, r10)
            r7.buttonIconTintMode = r8
            int r8 = com.google.android.material.R$styleable.MaterialCheckBox_useMaterialThemeColors
            boolean r8 = r9.getBoolean(r8, r6)
            r7.useMaterialThemeColors = r8
            int r8 = com.google.android.material.R$styleable.MaterialCheckBox_centerIfNoTextEnabled
            boolean r8 = r9.getBoolean(r8, r1)
            r7.centerIfNoTextEnabled = r8
            int r8 = com.google.android.material.R$styleable.MaterialCheckBox_errorShown
            boolean r8 = r9.getBoolean(r8, r6)
            r7.errorShown = r8
            int r8 = com.google.android.material.R$styleable.MaterialCheckBox_errorAccessibilityLabel
            java.lang.CharSequence r8 = r9.getText(r8)
            r7.errorAccessibilityLabel = r8
            int r8 = com.google.android.material.R$styleable.MaterialCheckBox_checkedState
            boolean r10 = r9.hasValue(r8)
            if (r10 == 0) goto L_0x00c1
            int r8 = r9.getInt(r8, r6)
            r7.setCheckedState(r8)
        L_0x00c1:
            r9.recycle()
            r7.refreshButtonDrawable()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.checkbox.MaterialCheckBox.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Drawable buttonDrawable2;
        if (!this.centerIfNoTextEnabled || !TextUtils.isEmpty(getText()) || (buttonDrawable2 = CompoundButtonCompat.getButtonDrawable(this)) == null) {
            super.onDraw(canvas);
            return;
        }
        int width = ((getWidth() - buttonDrawable2.getIntrinsicWidth()) / 2) * (ViewUtils.isLayoutRtl(this) ? -1 : 1);
        int save = canvas.save();
        canvas.translate((float) width, 0.0f);
        super.onDraw(canvas);
        canvas.restoreToCount(save);
        if (getBackground() != null) {
            Rect bounds = buttonDrawable2.getBounds();
            getBackground().setHotspotBounds(bounds.left + width, bounds.top, bounds.right + width, bounds.bottom);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.useMaterialThemeColors && this.buttonTintList == null && this.buttonIconTintList == null) {
            setUseMaterialThemeColors(true);
        }
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 2);
        if (getCheckedState() == 2) {
            CheckBox.mergeDrawableStates(onCreateDrawableState, INDETERMINATE_STATE_SET);
        }
        if (isErrorShown()) {
            CheckBox.mergeDrawableStates(onCreateDrawableState, ERROR_STATE_SET);
        }
        this.currentStateChecked = DrawableUtils.getCheckedState(onCreateDrawableState);
        return onCreateDrawableState;
    }

    public void setChecked(boolean z) {
        setCheckedState(z ? 1 : 0);
    }

    public boolean isChecked() {
        return this.checkedState == 1;
    }

    public void toggle() {
        setChecked(!isChecked());
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener2) {
        this.onCheckedChangeListener = onCheckedChangeListener2;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (accessibilityNodeInfo != null && isErrorShown()) {
            accessibilityNodeInfo.setText(accessibilityNodeInfo.getText() + ", " + this.errorAccessibilityLabel);
        }
    }

    public void setCheckedState(int i) {
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener2;
        if (this.checkedState != i) {
            this.checkedState = i;
            super.setChecked(i == 1);
            refreshDrawableState();
            setDefaultStateDescription();
            if (!this.broadcasting) {
                this.broadcasting = true;
                LinkedHashSet linkedHashSet = this.onCheckedStateChangedListeners;
                if (linkedHashSet != null) {
                    Iterator it = linkedHashSet.iterator();
                    if (it.hasNext()) {
                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                        throw null;
                    }
                }
                if (!(this.checkedState == 2 || (onCheckedChangeListener2 = this.onCheckedChangeListener) == null)) {
                    onCheckedChangeListener2.onCheckedChanged(this, isChecked());
                }
                AutofillManager autofillManager = (AutofillManager) getContext().getSystemService(AutofillManager.class);
                if (autofillManager != null) {
                    autofillManager.notifyValueChanged(this);
                }
                this.broadcasting = false;
            }
        }
    }

    public int getCheckedState() {
        return this.checkedState;
    }

    public boolean isErrorShown() {
        return this.errorShown;
    }

    public void setButtonDrawable(int i) {
        setButtonDrawable(AppCompatResources.getDrawable(getContext(), i));
    }

    public void setButtonDrawable(Drawable drawable) {
        this.buttonDrawable = drawable;
        this.usingMaterialButtonDrawable = false;
        refreshButtonDrawable();
    }

    public Drawable getButtonDrawable() {
        return this.buttonDrawable;
    }

    public void setButtonTintList(ColorStateList colorStateList) {
        if (this.buttonTintList != colorStateList) {
            this.buttonTintList = colorStateList;
            refreshButtonDrawable();
        }
    }

    public ColorStateList getButtonTintList() {
        return this.buttonTintList;
    }

    public void setButtonTintMode(PorterDuff.Mode mode) {
        setSupportButtonTintMode(mode);
        refreshButtonDrawable();
    }

    public void setUseMaterialThemeColors(boolean z) {
        this.useMaterialThemeColors = z;
        if (z) {
            CompoundButtonCompat.setButtonTintList(this, getMaterialThemeColorsTintList());
        } else {
            CompoundButtonCompat.setButtonTintList(this, (ColorStateList) null);
        }
    }

    private void refreshButtonDrawable() {
        this.buttonDrawable = DrawableUtils.createTintableMutatedDrawableIfNeeded(this.buttonDrawable, this.buttonTintList, CompoundButtonCompat.getButtonTintMode(this));
        this.buttonIconDrawable = DrawableUtils.createTintableMutatedDrawableIfNeeded(this.buttonIconDrawable, this.buttonIconTintList, this.buttonIconTintMode);
        setUpDefaultButtonDrawableAnimationIfNeeded();
        updateButtonTints();
        super.setButtonDrawable(DrawableUtils.compositeTwoLayeredDrawable(this.buttonDrawable, this.buttonIconDrawable));
        refreshDrawableState();
    }

    private void setUpDefaultButtonDrawableAnimationIfNeeded() {
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat;
        if (this.usingMaterialButtonDrawable) {
            AnimatedVectorDrawableCompat animatedVectorDrawableCompat2 = this.transitionToUnchecked;
            if (animatedVectorDrawableCompat2 != null) {
                animatedVectorDrawableCompat2.unregisterAnimationCallback(this.transitionToUncheckedCallback);
                this.transitionToUnchecked.registerAnimationCallback(this.transitionToUncheckedCallback);
            }
            Drawable drawable = this.buttonDrawable;
            if ((drawable instanceof AnimatedStateListDrawable) && (animatedVectorDrawableCompat = this.transitionToUnchecked) != null) {
                int i = R$id.checked;
                int i2 = R$id.unchecked;
                ((AnimatedStateListDrawable) drawable).addTransition(i, i2, animatedVectorDrawableCompat, false);
                ((AnimatedStateListDrawable) this.buttonDrawable).addTransition(R$id.indeterminate, i2, this.transitionToUnchecked, false);
            }
        }
    }

    private void updateButtonTints() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        Drawable drawable = this.buttonDrawable;
        if (!(drawable == null || (colorStateList2 = this.buttonTintList) == null)) {
            drawable.setTintList(colorStateList2);
        }
        Drawable drawable2 = this.buttonIconDrawable;
        if (drawable2 != null && (colorStateList = this.buttonIconTintList) != null) {
            drawable2.setTintList(colorStateList);
        }
    }

    public void setStateDescription(CharSequence charSequence) {
        this.customStateDescription = charSequence;
        if (charSequence == null) {
            setDefaultStateDescription();
        } else {
            super.setStateDescription(charSequence);
        }
    }

    private void setDefaultStateDescription() {
        if (this.customStateDescription == null) {
            super.setStateDescription(getButtonStateDescription());
        }
    }

    private String getButtonStateDescription() {
        int i = this.checkedState;
        if (i == 1) {
            return getResources().getString(R$string.mtrl_checkbox_state_description_checked);
        }
        if (i == 0) {
            return getResources().getString(R$string.mtrl_checkbox_state_description_unchecked);
        }
        return getResources().getString(R$string.mtrl_checkbox_state_description_indeterminate);
    }

    private ColorStateList getSuperButtonTintList() {
        ColorStateList colorStateList = this.buttonTintList;
        if (colorStateList != null) {
            return colorStateList;
        }
        if (super.getButtonTintList() != null) {
            return super.getButtonTintList();
        }
        return getSupportButtonTintList();
    }

    private boolean isButtonDrawableLegacy(TintTypedArray tintTypedArray) {
        int resourceId = tintTypedArray.getResourceId(R$styleable.MaterialCheckBox_android_button, 0);
        int resourceId2 = tintTypedArray.getResourceId(R$styleable.MaterialCheckBox_buttonCompat, 0);
        if (resourceId == FRAMEWORK_BUTTON_DRAWABLE_RES_ID && resourceId2 == 0) {
            return true;
        }
        return false;
    }

    private ColorStateList getMaterialThemeColorsTintList() {
        if (this.materialThemeColorsTintList == null) {
            int[][] iArr = CHECKBOX_STATES;
            int[] iArr2 = new int[iArr.length];
            int color = MaterialColors.getColor(this, androidx.appcompat.R$attr.colorControlActivated);
            int color2 = MaterialColors.getColor(this, androidx.appcompat.R$attr.colorError);
            int color3 = MaterialColors.getColor(this, R$attr.colorSurface);
            int color4 = MaterialColors.getColor(this, R$attr.colorOnSurface);
            iArr2[0] = MaterialColors.layer(color3, color2, 1.0f);
            iArr2[1] = MaterialColors.layer(color3, color, 1.0f);
            iArr2[2] = MaterialColors.layer(color3, color4, 0.54f);
            iArr2[3] = MaterialColors.layer(color3, color4, 0.38f);
            iArr2[4] = MaterialColors.layer(color3, color4, 0.38f);
            this.materialThemeColorsTintList = new ColorStateList(iArr, iArr2);
        }
        return this.materialThemeColorsTintList;
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checkedState = getCheckedState();
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setCheckedState(savedState.checkedState);
    }

    class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int checkedState;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.checkedState = ((Integer) parcel.readValue(getClass().getClassLoader())).intValue();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Integer.valueOf(this.checkedState));
        }

        public String toString() {
            return "MaterialCheckBox.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " CheckedState=" + getCheckedStateString() + "}";
        }

        private String getCheckedStateString() {
            int i = this.checkedState;
            if (i == 1) {
                return "checked";
            }
            if (i != 2) {
                return "unchecked";
            }
            return "indeterminate";
        }
    }
}
