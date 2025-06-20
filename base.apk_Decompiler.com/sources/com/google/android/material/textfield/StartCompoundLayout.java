package com.google.android.material.textfield;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R$dimen;
import com.google.android.material.R$id;
import com.google.android.material.R$layout;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;

class StartCompoundLayout extends LinearLayout {
    private boolean hintExpanded;
    private CharSequence prefixText;
    private final TextView prefixTextView;
    private int startIconMinSize;
    private View.OnLongClickListener startIconOnLongClickListener;
    private ImageView.ScaleType startIconScaleType;
    private ColorStateList startIconTintList;
    private PorterDuff.Mode startIconTintMode;
    private final CheckableImageButton startIconView;
    private final TextInputLayout textInputLayout;

    StartCompoundLayout(TextInputLayout textInputLayout2, TintTypedArray tintTypedArray) {
        super(textInputLayout2.getContext());
        this.textInputLayout = textInputLayout2;
        setVisibility(8);
        setOrientation(0);
        setLayoutParams(new FrameLayout.LayoutParams(-2, -1, 8388611));
        CheckableImageButton checkableImageButton = (CheckableImageButton) LayoutInflater.from(getContext()).inflate(R$layout.design_text_input_start_icon, this, false);
        this.startIconView = checkableImageButton;
        IconHelper.setCompatRippleBackgroundIfNeeded(checkableImageButton);
        AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
        this.prefixTextView = appCompatTextView;
        initStartIconView(tintTypedArray);
        initPrefixTextView(tintTypedArray);
        addView(checkableImageButton);
        addView(appCompatTextView);
    }

    private void initStartIconView(TintTypedArray tintTypedArray) {
        if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
            ((ViewGroup.MarginLayoutParams) this.startIconView.getLayoutParams()).setMarginEnd(0);
        }
        setStartIconOnClickListener((View.OnClickListener) null);
        setStartIconOnLongClickListener((View.OnLongClickListener) null);
        int i = R$styleable.TextInputLayout_startIconTint;
        if (tintTypedArray.hasValue(i)) {
            this.startIconTintList = MaterialResources.getColorStateList(getContext(), tintTypedArray, i);
        }
        int i2 = R$styleable.TextInputLayout_startIconTintMode;
        if (tintTypedArray.hasValue(i2)) {
            this.startIconTintMode = ViewUtils.parseTintMode(tintTypedArray.getInt(i2, -1), (PorterDuff.Mode) null);
        }
        int i3 = R$styleable.TextInputLayout_startIconDrawable;
        if (tintTypedArray.hasValue(i3)) {
            setStartIconDrawable(tintTypedArray.getDrawable(i3));
            int i4 = R$styleable.TextInputLayout_startIconContentDescription;
            if (tintTypedArray.hasValue(i4)) {
                setStartIconContentDescription(tintTypedArray.getText(i4));
            }
            setStartIconCheckable(tintTypedArray.getBoolean(R$styleable.TextInputLayout_startIconCheckable, true));
        }
        setStartIconMinSize(tintTypedArray.getDimensionPixelSize(R$styleable.TextInputLayout_startIconMinSize, getResources().getDimensionPixelSize(R$dimen.mtrl_min_touch_target_size)));
        int i5 = R$styleable.TextInputLayout_startIconScaleType;
        if (tintTypedArray.hasValue(i5)) {
            setStartIconScaleType(IconHelper.convertScaleType(tintTypedArray.getInt(i5, -1)));
        }
    }

    private void initPrefixTextView(TintTypedArray tintTypedArray) {
        this.prefixTextView.setVisibility(8);
        this.prefixTextView.setId(R$id.textinput_prefix_text);
        this.prefixTextView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.prefixTextView.setAccessibilityLiveRegion(1);
        setPrefixTextAppearance(tintTypedArray.getResourceId(R$styleable.TextInputLayout_prefixTextAppearance, 0));
        int i = R$styleable.TextInputLayout_prefixTextColor;
        if (tintTypedArray.hasValue(i)) {
            setPrefixTextColor(tintTypedArray.getColorStateList(i));
        }
        setPrefixText(tintTypedArray.getText(R$styleable.TextInputLayout_prefixText));
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        updatePrefixTextViewPadding();
    }

    /* access modifiers changed from: package-private */
    public TextView getPrefixTextView() {
        return this.prefixTextView;
    }

    /* access modifiers changed from: package-private */
    public void setPrefixText(CharSequence charSequence) {
        this.prefixText = TextUtils.isEmpty(charSequence) ? null : charSequence;
        this.prefixTextView.setText(charSequence);
        updateVisibility();
    }

    /* access modifiers changed from: package-private */
    public CharSequence getPrefixText() {
        return this.prefixText;
    }

    /* access modifiers changed from: package-private */
    public void setPrefixTextColor(ColorStateList colorStateList) {
        this.prefixTextView.setTextColor(colorStateList);
    }

    /* access modifiers changed from: package-private */
    public void setPrefixTextAppearance(int i) {
        TextViewCompat.setTextAppearance(this.prefixTextView, i);
    }

    /* access modifiers changed from: package-private */
    public void setStartIconDrawable(Drawable drawable) {
        this.startIconView.setImageDrawable(drawable);
        if (drawable != null) {
            IconHelper.applyIconTint(this.textInputLayout, this.startIconView, this.startIconTintList, this.startIconTintMode);
            setStartIconVisible(true);
            refreshStartIconDrawableState();
            return;
        }
        setStartIconVisible(false);
        setStartIconOnClickListener((View.OnClickListener) null);
        setStartIconOnLongClickListener((View.OnLongClickListener) null);
        setStartIconContentDescription((CharSequence) null);
    }

    /* access modifiers changed from: package-private */
    public Drawable getStartIconDrawable() {
        return this.startIconView.getDrawable();
    }

    /* access modifiers changed from: package-private */
    public void setStartIconOnClickListener(View.OnClickListener onClickListener) {
        IconHelper.setIconOnClickListener(this.startIconView, onClickListener, this.startIconOnLongClickListener);
    }

    /* access modifiers changed from: package-private */
    public void setStartIconOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.startIconOnLongClickListener = onLongClickListener;
        IconHelper.setIconOnLongClickListener(this.startIconView, onLongClickListener);
    }

    /* access modifiers changed from: package-private */
    public void setStartIconVisible(boolean z) {
        if (isStartIconVisible() != z) {
            this.startIconView.setVisibility(z ? 0 : 8);
            updatePrefixTextViewPadding();
            updateVisibility();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isStartIconVisible() {
        return this.startIconView.getVisibility() == 0;
    }

    /* access modifiers changed from: package-private */
    public void refreshStartIconDrawableState() {
        IconHelper.refreshIconDrawableState(this.textInputLayout, this.startIconView, this.startIconTintList);
    }

    /* access modifiers changed from: package-private */
    public void setStartIconCheckable(boolean z) {
        this.startIconView.setCheckable(z);
    }

    /* access modifiers changed from: package-private */
    public void setStartIconContentDescription(CharSequence charSequence) {
        if (getStartIconContentDescription() != charSequence) {
            this.startIconView.setContentDescription(charSequence);
        }
    }

    /* access modifiers changed from: package-private */
    public CharSequence getStartIconContentDescription() {
        return this.startIconView.getContentDescription();
    }

    /* access modifiers changed from: package-private */
    public void setStartIconMinSize(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("startIconSize cannot be less than 0");
        } else if (i != this.startIconMinSize) {
            this.startIconMinSize = i;
            IconHelper.setIconMinSize(this.startIconView, i);
        }
    }

    /* access modifiers changed from: package-private */
    public void setStartIconScaleType(ImageView.ScaleType scaleType) {
        this.startIconScaleType = scaleType;
        IconHelper.setIconScaleType(this.startIconView, scaleType);
    }

    /* access modifiers changed from: package-private */
    public void setupAccessibilityNodeInfo(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        if (this.prefixTextView.getVisibility() == 0) {
            accessibilityNodeInfoCompat.setLabelFor(this.prefixTextView);
            accessibilityNodeInfoCompat.setTraversalAfter(this.prefixTextView);
            return;
        }
        accessibilityNodeInfoCompat.setTraversalAfter(this.startIconView);
    }

    /* access modifiers changed from: package-private */
    public void updatePrefixTextViewPadding() {
        EditText editText = this.textInputLayout.editText;
        if (editText != null) {
            this.prefixTextView.setPaddingRelative(isStartIconVisible() ? 0 : editText.getPaddingStart(), editText.getCompoundPaddingTop(), getContext().getResources().getDimensionPixelSize(R$dimen.material_input_text_to_prefix_suffix_padding), editText.getCompoundPaddingBottom());
        }
    }

    /* access modifiers changed from: package-private */
    public int getPrefixTextStartOffset() {
        return getPaddingStart() + this.prefixTextView.getPaddingStart() + (isStartIconVisible() ? this.startIconView.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) this.startIconView.getLayoutParams()).getMarginEnd() : 0);
    }

    /* access modifiers changed from: package-private */
    public void onHintStateChanged(boolean z) {
        this.hintExpanded = z;
        updateVisibility();
    }

    private void updateVisibility() {
        int i = 8;
        int i2 = (this.prefixText == null || this.hintExpanded) ? 8 : 0;
        if (this.startIconView.getVisibility() == 0 || i2 == 0) {
            i = 0;
        }
        setVisibility(i);
        this.prefixTextView.setVisibility(i2);
        this.textInputLayout.updateDummyDrawables();
    }
}
