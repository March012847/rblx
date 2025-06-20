package com.google.android.material.textfield;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R$dimen;
import com.google.android.material.R$id;
import com.google.android.material.R$layout;
import com.google.android.material.R$string;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Iterator;
import java.util.LinkedHashSet;

class EndCompoundLayout extends LinearLayout {
    private final AccessibilityManager accessibilityManager;
    /* access modifiers changed from: private */
    public EditText editText;
    /* access modifiers changed from: private */
    public final TextWatcher editTextWatcher = new TextWatcherAdapter() {
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            EndCompoundLayout.this.getEndIconDelegate().beforeEditTextChanged(charSequence, i, i2, i3);
        }

        public void afterTextChanged(Editable editable) {
            EndCompoundLayout.this.getEndIconDelegate().afterEditTextChanged(editable);
        }
    };
    private final LinkedHashSet endIconChangedListeners = new LinkedHashSet();
    private final EndIconDelegates endIconDelegates;
    private final FrameLayout endIconFrame;
    private int endIconMinSize;
    private int endIconMode = 0;
    private View.OnLongClickListener endIconOnLongClickListener;
    private ImageView.ScaleType endIconScaleType;
    private ColorStateList endIconTintList;
    private PorterDuff.Mode endIconTintMode;
    private final CheckableImageButton endIconView;
    private ColorStateList errorIconTintList;
    private PorterDuff.Mode errorIconTintMode;
    private final CheckableImageButton errorIconView;
    private boolean hintExpanded;
    private final TextInputLayout.OnEditTextAttachedListener onEditTextAttachedListener;
    private CharSequence suffixText;
    private final TextView suffixTextView;
    final TextInputLayout textInputLayout;
    private AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener;

    EndCompoundLayout(TextInputLayout textInputLayout2, TintTypedArray tintTypedArray) {
        super(textInputLayout2.getContext());
        AnonymousClass2 r1 = new TextInputLayout.OnEditTextAttachedListener() {
            public void onEditTextAttached(TextInputLayout textInputLayout) {
                if (EndCompoundLayout.this.editText != textInputLayout.getEditText()) {
                    if (EndCompoundLayout.this.editText != null) {
                        EndCompoundLayout.this.editText.removeTextChangedListener(EndCompoundLayout.this.editTextWatcher);
                        if (EndCompoundLayout.this.editText.getOnFocusChangeListener() == EndCompoundLayout.this.getEndIconDelegate().getOnEditTextFocusChangeListener()) {
                            EndCompoundLayout.this.editText.setOnFocusChangeListener((View.OnFocusChangeListener) null);
                        }
                    }
                    EditText unused = EndCompoundLayout.this.editText = textInputLayout.getEditText();
                    if (EndCompoundLayout.this.editText != null) {
                        EndCompoundLayout.this.editText.addTextChangedListener(EndCompoundLayout.this.editTextWatcher);
                    }
                    EndCompoundLayout.this.getEndIconDelegate().onEditTextAttached(EndCompoundLayout.this.editText);
                    EndCompoundLayout endCompoundLayout = EndCompoundLayout.this;
                    endCompoundLayout.setOnFocusChangeListenersIfNeeded(endCompoundLayout.getEndIconDelegate());
                }
            }
        };
        this.onEditTextAttachedListener = r1;
        this.accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        this.textInputLayout = textInputLayout2;
        setVisibility(8);
        setOrientation(0);
        setLayoutParams(new FrameLayout.LayoutParams(-2, -1, 8388613));
        FrameLayout frameLayout = new FrameLayout(getContext());
        this.endIconFrame = frameLayout;
        frameLayout.setVisibility(8);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
        LayoutInflater from = LayoutInflater.from(getContext());
        CheckableImageButton createIconView = createIconView(this, from, R$id.text_input_error_icon);
        this.errorIconView = createIconView;
        CheckableImageButton createIconView2 = createIconView(frameLayout, from, R$id.text_input_end_icon);
        this.endIconView = createIconView2;
        this.endIconDelegates = new EndIconDelegates(this, tintTypedArray);
        AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
        this.suffixTextView = appCompatTextView;
        initErrorIconView(tintTypedArray);
        initEndIconView(tintTypedArray);
        initSuffixTextView(tintTypedArray);
        frameLayout.addView(createIconView2);
        addView(appCompatTextView);
        addView(frameLayout);
        addView(createIconView);
        textInputLayout2.addOnEditTextAttachedListener(r1);
        addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            public void onViewAttachedToWindow(View view) {
                EndCompoundLayout.this.addTouchExplorationStateChangeListenerIfNeeded();
            }

            public void onViewDetachedFromWindow(View view) {
                EndCompoundLayout.this.removeTouchExplorationStateChangeListenerIfNeeded();
            }
        });
    }

    private CheckableImageButton createIconView(ViewGroup viewGroup, LayoutInflater layoutInflater, int i) {
        CheckableImageButton checkableImageButton = (CheckableImageButton) layoutInflater.inflate(R$layout.design_text_input_end_icon, viewGroup, false);
        checkableImageButton.setId(i);
        IconHelper.setCompatRippleBackgroundIfNeeded(checkableImageButton);
        if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
            ((ViewGroup.MarginLayoutParams) checkableImageButton.getLayoutParams()).setMarginStart(0);
        }
        return checkableImageButton;
    }

    private void initErrorIconView(TintTypedArray tintTypedArray) {
        int i = R$styleable.TextInputLayout_errorIconTint;
        if (tintTypedArray.hasValue(i)) {
            this.errorIconTintList = MaterialResources.getColorStateList(getContext(), tintTypedArray, i);
        }
        int i2 = R$styleable.TextInputLayout_errorIconTintMode;
        if (tintTypedArray.hasValue(i2)) {
            this.errorIconTintMode = ViewUtils.parseTintMode(tintTypedArray.getInt(i2, -1), (PorterDuff.Mode) null);
        }
        int i3 = R$styleable.TextInputLayout_errorIconDrawable;
        if (tintTypedArray.hasValue(i3)) {
            setErrorIconDrawable(tintTypedArray.getDrawable(i3));
        }
        this.errorIconView.setContentDescription(getResources().getText(R$string.error_icon_content_description));
        this.errorIconView.setImportantForAccessibility(2);
        this.errorIconView.setClickable(false);
        this.errorIconView.setPressable(false);
        this.errorIconView.setCheckable(false);
        this.errorIconView.setFocusable(false);
    }

    private void initEndIconView(TintTypedArray tintTypedArray) {
        int i = R$styleable.TextInputLayout_passwordToggleEnabled;
        if (!tintTypedArray.hasValue(i)) {
            int i2 = R$styleable.TextInputLayout_endIconTint;
            if (tintTypedArray.hasValue(i2)) {
                this.endIconTintList = MaterialResources.getColorStateList(getContext(), tintTypedArray, i2);
            }
            int i3 = R$styleable.TextInputLayout_endIconTintMode;
            if (tintTypedArray.hasValue(i3)) {
                this.endIconTintMode = ViewUtils.parseTintMode(tintTypedArray.getInt(i3, -1), (PorterDuff.Mode) null);
            }
        }
        int i4 = R$styleable.TextInputLayout_endIconMode;
        if (tintTypedArray.hasValue(i4)) {
            setEndIconMode(tintTypedArray.getInt(i4, 0));
            int i5 = R$styleable.TextInputLayout_endIconContentDescription;
            if (tintTypedArray.hasValue(i5)) {
                setEndIconContentDescription(tintTypedArray.getText(i5));
            }
            setEndIconCheckable(tintTypedArray.getBoolean(R$styleable.TextInputLayout_endIconCheckable, true));
        } else if (tintTypedArray.hasValue(i)) {
            int i6 = R$styleable.TextInputLayout_passwordToggleTint;
            if (tintTypedArray.hasValue(i6)) {
                this.endIconTintList = MaterialResources.getColorStateList(getContext(), tintTypedArray, i6);
            }
            int i7 = R$styleable.TextInputLayout_passwordToggleTintMode;
            if (tintTypedArray.hasValue(i7)) {
                this.endIconTintMode = ViewUtils.parseTintMode(tintTypedArray.getInt(i7, -1), (PorterDuff.Mode) null);
            }
            setEndIconMode(tintTypedArray.getBoolean(i, false) ? 1 : 0);
            setEndIconContentDescription(tintTypedArray.getText(R$styleable.TextInputLayout_passwordToggleContentDescription));
        }
        setEndIconMinSize(tintTypedArray.getDimensionPixelSize(R$styleable.TextInputLayout_endIconMinSize, getResources().getDimensionPixelSize(R$dimen.mtrl_min_touch_target_size)));
        int i8 = R$styleable.TextInputLayout_endIconScaleType;
        if (tintTypedArray.hasValue(i8)) {
            setEndIconScaleType(IconHelper.convertScaleType(tintTypedArray.getInt(i8, -1)));
        }
    }

    private void initSuffixTextView(TintTypedArray tintTypedArray) {
        this.suffixTextView.setVisibility(8);
        this.suffixTextView.setId(R$id.textinput_suffix_text);
        this.suffixTextView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2, 80.0f));
        this.suffixTextView.setAccessibilityLiveRegion(1);
        setSuffixTextAppearance(tintTypedArray.getResourceId(R$styleable.TextInputLayout_suffixTextAppearance, 0));
        int i = R$styleable.TextInputLayout_suffixTextColor;
        if (tintTypedArray.hasValue(i)) {
            setSuffixTextColor(tintTypedArray.getColorStateList(i));
        }
        setSuffixText(tintTypedArray.getText(R$styleable.TextInputLayout_suffixText));
    }

    /* access modifiers changed from: package-private */
    public void setErrorIconDrawable(Drawable drawable) {
        this.errorIconView.setImageDrawable(drawable);
        updateErrorIconVisibility();
        IconHelper.applyIconTint(this.textInputLayout, this.errorIconView, this.errorIconTintList, this.errorIconTintMode);
    }

    /* access modifiers changed from: package-private */
    public Drawable getErrorIconDrawable() {
        return this.errorIconView.getDrawable();
    }

    /* access modifiers changed from: package-private */
    public CheckableImageButton getEndIconView() {
        return this.endIconView;
    }

    /* access modifiers changed from: package-private */
    public EndIconDelegate getEndIconDelegate() {
        return this.endIconDelegates.get(this.endIconMode);
    }

    /* access modifiers changed from: package-private */
    public int getEndIconMode() {
        return this.endIconMode;
    }

    /* access modifiers changed from: package-private */
    public void setEndIconMode(int i) {
        if (this.endIconMode != i) {
            tearDownDelegate(getEndIconDelegate());
            int i2 = this.endIconMode;
            this.endIconMode = i;
            dispatchOnEndIconChanged(i2);
            setEndIconVisible(i != 0);
            EndIconDelegate endIconDelegate = getEndIconDelegate();
            setEndIconDrawable(getIconResId(endIconDelegate));
            setEndIconContentDescription(endIconDelegate.getIconContentDescriptionResId());
            setEndIconCheckable(endIconDelegate.isIconCheckable());
            if (endIconDelegate.isBoxBackgroundModeSupported(this.textInputLayout.getBoxBackgroundMode())) {
                setUpDelegate(endIconDelegate);
                setEndIconOnClickListener(endIconDelegate.getOnIconClickListener());
                EditText editText2 = this.editText;
                if (editText2 != null) {
                    endIconDelegate.onEditTextAttached(editText2);
                    setOnFocusChangeListenersIfNeeded(endIconDelegate);
                }
                IconHelper.applyIconTint(this.textInputLayout, this.endIconView, this.endIconTintList, this.endIconTintMode);
                refreshIconState(true);
                return;
            }
            throw new IllegalStateException("The current box background mode " + this.textInputLayout.getBoxBackgroundMode() + " is not supported by the end icon mode " + i);
        }
    }

    /* access modifiers changed from: package-private */
    public void refreshIconState(boolean z) {
        boolean z2;
        boolean isActivated;
        boolean isChecked;
        EndIconDelegate endIconDelegate = getEndIconDelegate();
        boolean z3 = true;
        if (!endIconDelegate.isIconCheckable() || (isChecked = this.endIconView.isChecked()) == endIconDelegate.isIconChecked()) {
            z2 = false;
        } else {
            this.endIconView.setChecked(!isChecked);
            z2 = true;
        }
        if (!endIconDelegate.isIconActivable() || (isActivated = this.endIconView.isActivated()) == endIconDelegate.isIconActivated()) {
            z3 = z2;
        } else {
            setEndIconActivated(!isActivated);
        }
        if (z || z3) {
            refreshEndIconDrawableState();
        }
    }

    private void setUpDelegate(EndIconDelegate endIconDelegate) {
        endIconDelegate.setUp();
        this.touchExplorationStateChangeListener = endIconDelegate.getTouchExplorationStateChangeListener();
        addTouchExplorationStateChangeListenerIfNeeded();
    }

    private void tearDownDelegate(EndIconDelegate endIconDelegate) {
        removeTouchExplorationStateChangeListenerIfNeeded();
        this.touchExplorationStateChangeListener = null;
        endIconDelegate.tearDown();
    }

    /* access modifiers changed from: private */
    public void addTouchExplorationStateChangeListenerIfNeeded() {
        if (this.touchExplorationStateChangeListener != null && this.accessibilityManager != null && isAttachedToWindow()) {
            this.accessibilityManager.addTouchExplorationStateChangeListener(this.touchExplorationStateChangeListener);
        }
    }

    /* access modifiers changed from: private */
    public void removeTouchExplorationStateChangeListenerIfNeeded() {
        AccessibilityManager accessibilityManager2;
        AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener2 = this.touchExplorationStateChangeListener;
        if (touchExplorationStateChangeListener2 != null && (accessibilityManager2 = this.accessibilityManager) != null) {
            accessibilityManager2.removeTouchExplorationStateChangeListener(touchExplorationStateChangeListener2);
        }
    }

    private int getIconResId(EndIconDelegate endIconDelegate) {
        int access$500 = this.endIconDelegates.customEndIconDrawableId;
        return access$500 == 0 ? endIconDelegate.getIconDrawableResId() : access$500;
    }

    /* access modifiers changed from: package-private */
    public void setEndIconOnClickListener(View.OnClickListener onClickListener) {
        IconHelper.setIconOnClickListener(this.endIconView, onClickListener, this.endIconOnLongClickListener);
    }

    /* access modifiers changed from: package-private */
    public void setEndIconOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.endIconOnLongClickListener = onLongClickListener;
        IconHelper.setIconOnLongClickListener(this.endIconView, onLongClickListener);
    }

    /* access modifiers changed from: private */
    public void setOnFocusChangeListenersIfNeeded(EndIconDelegate endIconDelegate) {
        if (this.editText != null) {
            if (endIconDelegate.getOnEditTextFocusChangeListener() != null) {
                this.editText.setOnFocusChangeListener(endIconDelegate.getOnEditTextFocusChangeListener());
            }
            if (endIconDelegate.getOnIconViewFocusChangeListener() != null) {
                this.endIconView.setOnFocusChangeListener(endIconDelegate.getOnIconViewFocusChangeListener());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void refreshErrorIconDrawableState() {
        IconHelper.refreshIconDrawableState(this.textInputLayout, this.errorIconView, this.errorIconTintList);
    }

    /* access modifiers changed from: package-private */
    public void setEndIconVisible(boolean z) {
        if (isEndIconVisible() != z) {
            this.endIconView.setVisibility(z ? 0 : 8);
            updateEndLayoutVisibility();
            updateSuffixTextViewPadding();
            this.textInputLayout.updateDummyDrawables();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isEndIconVisible() {
        return this.endIconFrame.getVisibility() == 0 && this.endIconView.getVisibility() == 0;
    }

    /* access modifiers changed from: package-private */
    public void setEndIconActivated(boolean z) {
        this.endIconView.setActivated(z);
    }

    /* access modifiers changed from: package-private */
    public void refreshEndIconDrawableState() {
        IconHelper.refreshIconDrawableState(this.textInputLayout, this.endIconView, this.endIconTintList);
    }

    /* access modifiers changed from: package-private */
    public void setEndIconCheckable(boolean z) {
        this.endIconView.setCheckable(z);
    }

    /* access modifiers changed from: package-private */
    public boolean isEndIconChecked() {
        return hasEndIcon() && this.endIconView.isChecked();
    }

    /* access modifiers changed from: package-private */
    public void checkEndIcon() {
        this.endIconView.performClick();
        this.endIconView.jumpDrawablesToCurrentState();
    }

    /* access modifiers changed from: package-private */
    public void setEndIconDrawable(int i) {
        setEndIconDrawable(i != 0 ? AppCompatResources.getDrawable(getContext(), i) : null);
    }

    /* access modifiers changed from: package-private */
    public void setEndIconDrawable(Drawable drawable) {
        this.endIconView.setImageDrawable(drawable);
        if (drawable != null) {
            IconHelper.applyIconTint(this.textInputLayout, this.endIconView, this.endIconTintList, this.endIconTintMode);
            refreshEndIconDrawableState();
        }
    }

    /* access modifiers changed from: package-private */
    public Drawable getEndIconDrawable() {
        return this.endIconView.getDrawable();
    }

    /* access modifiers changed from: package-private */
    public void setEndIconContentDescription(int i) {
        setEndIconContentDescription(i != 0 ? getResources().getText(i) : null);
    }

    /* access modifiers changed from: package-private */
    public void setEndIconContentDescription(CharSequence charSequence) {
        if (getEndIconContentDescription() != charSequence) {
            this.endIconView.setContentDescription(charSequence);
        }
    }

    /* access modifiers changed from: package-private */
    public CharSequence getEndIconContentDescription() {
        return this.endIconView.getContentDescription();
    }

    /* access modifiers changed from: package-private */
    public void setEndIconMinSize(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("endIconSize cannot be less than 0");
        } else if (i != this.endIconMinSize) {
            this.endIconMinSize = i;
            IconHelper.setIconMinSize(this.endIconView, i);
            IconHelper.setIconMinSize(this.errorIconView, i);
        }
    }

    /* access modifiers changed from: package-private */
    public void setEndIconScaleType(ImageView.ScaleType scaleType) {
        this.endIconScaleType = scaleType;
        IconHelper.setIconScaleType(this.endIconView, scaleType);
        IconHelper.setIconScaleType(this.errorIconView, scaleType);
    }

    /* access modifiers changed from: package-private */
    public boolean hasEndIcon() {
        return this.endIconMode != 0;
    }

    /* access modifiers changed from: package-private */
    public TextView getSuffixTextView() {
        return this.suffixTextView;
    }

    /* access modifiers changed from: package-private */
    public void setSuffixText(CharSequence charSequence) {
        this.suffixText = TextUtils.isEmpty(charSequence) ? null : charSequence;
        this.suffixTextView.setText(charSequence);
        updateSuffixTextVisibility();
    }

    /* access modifiers changed from: package-private */
    public CharSequence getSuffixText() {
        return this.suffixText;
    }

    /* access modifiers changed from: package-private */
    public void setSuffixTextAppearance(int i) {
        TextViewCompat.setTextAppearance(this.suffixTextView, i);
    }

    /* access modifiers changed from: package-private */
    public void setSuffixTextColor(ColorStateList colorStateList) {
        this.suffixTextView.setTextColor(colorStateList);
    }

    /* access modifiers changed from: package-private */
    public void onHintStateChanged(boolean z) {
        this.hintExpanded = z;
        updateSuffixTextVisibility();
    }

    /* access modifiers changed from: package-private */
    public void onTextInputBoxStateUpdated() {
        updateErrorIconVisibility();
        refreshErrorIconDrawableState();
        refreshEndIconDrawableState();
        if (getEndIconDelegate().shouldTintIconOnError()) {
            tintEndIconOnError(this.textInputLayout.shouldShowError());
        }
    }

    private void updateSuffixTextVisibility() {
        int visibility = this.suffixTextView.getVisibility();
        boolean z = false;
        int i = (this.suffixText == null || this.hintExpanded) ? 8 : 0;
        if (visibility != i) {
            EndIconDelegate endIconDelegate = getEndIconDelegate();
            if (i == 0) {
                z = true;
            }
            endIconDelegate.onSuffixVisibilityChanged(z);
        }
        updateEndLayoutVisibility();
        this.suffixTextView.setVisibility(i);
        this.textInputLayout.updateDummyDrawables();
    }

    /* access modifiers changed from: package-private */
    public void updateSuffixTextViewPadding() {
        if (this.textInputLayout.editText != null) {
            this.suffixTextView.setPaddingRelative(getContext().getResources().getDimensionPixelSize(R$dimen.material_input_text_to_prefix_suffix_padding), this.textInputLayout.editText.getPaddingTop(), (isEndIconVisible() || isErrorIconVisible()) ? 0 : this.textInputLayout.editText.getPaddingEnd(), this.textInputLayout.editText.getPaddingBottom());
        }
    }

    /* access modifiers changed from: package-private */
    public int getSuffixTextEndOffset() {
        int i;
        if (isEndIconVisible() || isErrorIconVisible()) {
            i = this.endIconView.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) this.endIconView.getLayoutParams()).getMarginStart();
        } else {
            i = 0;
        }
        return getPaddingEnd() + this.suffixTextView.getPaddingEnd() + i;
    }

    /* access modifiers changed from: package-private */
    public CheckableImageButton getCurrentEndIconView() {
        if (isErrorIconVisible()) {
            return this.errorIconView;
        }
        if (!hasEndIcon() || !isEndIconVisible()) {
            return null;
        }
        return this.endIconView;
    }

    /* access modifiers changed from: package-private */
    public boolean isErrorIconVisible() {
        return this.errorIconView.getVisibility() == 0;
    }

    private void updateErrorIconVisibility() {
        int i = 0;
        boolean z = getErrorIconDrawable() != null && this.textInputLayout.isErrorEnabled() && this.textInputLayout.shouldShowError();
        CheckableImageButton checkableImageButton = this.errorIconView;
        if (!z) {
            i = 8;
        }
        checkableImageButton.setVisibility(i);
        updateEndLayoutVisibility();
        updateSuffixTextViewPadding();
        if (!hasEndIcon()) {
            this.textInputLayout.updateDummyDrawables();
        }
    }

    private void updateEndLayoutVisibility() {
        int i = 8;
        this.endIconFrame.setVisibility((this.endIconView.getVisibility() != 0 || isErrorIconVisible()) ? 8 : 0);
        boolean z = (this.suffixText == null || this.hintExpanded) ? true : false;
        if (isEndIconVisible() || isErrorIconVisible() || !z) {
            i = 0;
        }
        setVisibility(i);
    }

    private void dispatchOnEndIconChanged(int i) {
        Iterator it = this.endIconChangedListeners.iterator();
        if (it.hasNext()) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            throw null;
        }
    }

    private void tintEndIconOnError(boolean z) {
        if (!z || getEndIconDrawable() == null) {
            IconHelper.applyIconTint(this.textInputLayout, this.endIconView, this.endIconTintList, this.endIconTintMode);
            return;
        }
        Drawable mutate = DrawableCompat.wrap(getEndIconDrawable()).mutate();
        mutate.setTint(this.textInputLayout.getErrorCurrentTextColors());
        this.endIconView.setImageDrawable(mutate);
    }

    class EndIconDelegates {
        /* access modifiers changed from: private */
        public final int customEndIconDrawableId;
        private final SparseArray delegates = new SparseArray();
        private final EndCompoundLayout endLayout;
        private final int passwordIconDrawableId;

        EndIconDelegates(EndCompoundLayout endCompoundLayout, TintTypedArray tintTypedArray) {
            this.endLayout = endCompoundLayout;
            this.customEndIconDrawableId = tintTypedArray.getResourceId(R$styleable.TextInputLayout_endIconDrawable, 0);
            this.passwordIconDrawableId = tintTypedArray.getResourceId(R$styleable.TextInputLayout_passwordToggleDrawable, 0);
        }

        /* access modifiers changed from: package-private */
        public EndIconDelegate get(int i) {
            EndIconDelegate endIconDelegate = (EndIconDelegate) this.delegates.get(i);
            if (endIconDelegate != null) {
                return endIconDelegate;
            }
            EndIconDelegate create = create(i);
            this.delegates.append(i, create);
            return create;
        }

        private EndIconDelegate create(int i) {
            if (i == -1) {
                return new CustomEndIconDelegate(this.endLayout);
            }
            if (i == 0) {
                return new NoEndIconDelegate(this.endLayout);
            }
            if (i == 1) {
                return new PasswordToggleEndIconDelegate(this.endLayout, this.passwordIconDrawableId);
            }
            if (i == 2) {
                return new ClearTextEndIconDelegate(this.endLayout);
            }
            if (i == 3) {
                return new DropdownMenuEndIconDelegate(this.endLayout);
            }
            throw new IllegalArgumentException("Invalid end icon mode: " + i);
        }
    }
}
