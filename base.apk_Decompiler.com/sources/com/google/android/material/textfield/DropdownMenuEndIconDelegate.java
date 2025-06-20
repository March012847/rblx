package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R$attr;
import com.google.android.material.R$drawable;
import com.google.android.material.R$string;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.motion.MotionUtils;

class DropdownMenuEndIconDelegate extends EndIconDelegate {
    private AccessibilityManager accessibilityManager;
    private final int animationFadeInDuration;
    private final TimeInterpolator animationFadeInterpolator;
    private final int animationFadeOutDuration;
    private AutoCompleteTextView autoCompleteTextView;
    private long dropdownPopupActivatedAt = Long.MAX_VALUE;
    private boolean dropdownPopupDirty;
    private boolean editTextHasFocus;
    /* access modifiers changed from: private */
    public ValueAnimator fadeInAnim;
    private ValueAnimator fadeOutAnim;
    private boolean isEndIconChecked;
    private final View.OnFocusChangeListener onEditTextFocusChangeListener = new DropdownMenuEndIconDelegate$$ExternalSyntheticLambda4(this);
    private final View.OnClickListener onIconClickListener = new DropdownMenuEndIconDelegate$$ExternalSyntheticLambda3(this);
    private final AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener = new DropdownMenuEndIconDelegate$$ExternalSyntheticLambda5(this);

    /* access modifiers changed from: package-private */
    public boolean isBoxBackgroundModeSupported(int i) {
        return i != 0;
    }

    /* access modifiers changed from: package-private */
    public boolean isIconActivable() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean isIconCheckable() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldTintIconOnError() {
        return true;
    }

    public static /* synthetic */ void $r8$lambda$nNUP5cjMWoIMzjM1rxTH_Aso8Io(DropdownMenuEndIconDelegate dropdownMenuEndIconDelegate, View view, boolean z) {
        dropdownMenuEndIconDelegate.editTextHasFocus = z;
        dropdownMenuEndIconDelegate.refreshIconState();
        if (!z) {
            dropdownMenuEndIconDelegate.setEndIconChecked(false);
            dropdownMenuEndIconDelegate.dropdownPopupDirty = false;
        }
    }

    public static /* synthetic */ void $r8$lambda$B9_pgNgtuRR7l72K3jIEKbhb2Io(DropdownMenuEndIconDelegate dropdownMenuEndIconDelegate, boolean z) {
        AutoCompleteTextView autoCompleteTextView2 = dropdownMenuEndIconDelegate.autoCompleteTextView;
        if (autoCompleteTextView2 != null && !EditTextUtils.isEditable(autoCompleteTextView2)) {
            dropdownMenuEndIconDelegate.endIconView.setImportantForAccessibility(z ? 2 : 1);
        }
    }

    DropdownMenuEndIconDelegate(EndCompoundLayout endCompoundLayout) {
        super(endCompoundLayout);
        Context context = endCompoundLayout.getContext();
        int i = R$attr.motionDurationShort3;
        this.animationFadeInDuration = MotionUtils.resolveThemeDuration(context, i, 67);
        this.animationFadeOutDuration = MotionUtils.resolveThemeDuration(endCompoundLayout.getContext(), i, 50);
        this.animationFadeInterpolator = MotionUtils.resolveThemeInterpolator(endCompoundLayout.getContext(), R$attr.motionEasingLinearInterpolator, AnimationUtils.LINEAR_INTERPOLATOR);
    }

    /* access modifiers changed from: package-private */
    public void setUp() {
        initAnimators();
        this.accessibilityManager = (AccessibilityManager) this.context.getSystemService("accessibility");
    }

    /* access modifiers changed from: package-private */
    public void tearDown() {
        AutoCompleteTextView autoCompleteTextView2 = this.autoCompleteTextView;
        if (autoCompleteTextView2 != null) {
            autoCompleteTextView2.setOnTouchListener((View.OnTouchListener) null);
            this.autoCompleteTextView.setOnDismissListener((AutoCompleteTextView.OnDismissListener) null);
        }
    }

    public AccessibilityManager.TouchExplorationStateChangeListener getTouchExplorationStateChangeListener() {
        return this.touchExplorationStateChangeListener;
    }

    /* access modifiers changed from: package-private */
    public int getIconDrawableResId() {
        return R$drawable.mtrl_dropdown_arrow;
    }

    /* access modifiers changed from: package-private */
    public int getIconContentDescriptionResId() {
        return R$string.exposed_dropdown_menu_content_description;
    }

    /* access modifiers changed from: package-private */
    public boolean isIconChecked() {
        return this.isEndIconChecked;
    }

    /* access modifiers changed from: package-private */
    public boolean isIconActivated() {
        return this.editTextHasFocus;
    }

    /* access modifiers changed from: package-private */
    public View.OnClickListener getOnIconClickListener() {
        return this.onIconClickListener;
    }

    public void onEditTextAttached(EditText editText) {
        this.autoCompleteTextView = castAutoCompleteTextViewOrThrow(editText);
        setUpDropdownShowHideBehavior();
        this.textInputLayout.setErrorIconDrawable((Drawable) null);
        if (!EditTextUtils.isEditable(editText) && this.accessibilityManager.isTouchExplorationEnabled()) {
            this.endIconView.setImportantForAccessibility(2);
        }
        this.textInputLayout.setEndIconVisible(true);
    }

    public void afterEditTextChanged(Editable editable) {
        if (this.accessibilityManager.isTouchExplorationEnabled() && EditTextUtils.isEditable(this.autoCompleteTextView) && !this.endIconView.hasFocus()) {
            this.autoCompleteTextView.dismissDropDown();
        }
        this.autoCompleteTextView.post(new DropdownMenuEndIconDelegate$$ExternalSyntheticLambda6(this));
    }

    /* renamed from: $r8$lambda$-y8fy3mWjKkDKEPGKU3W34ch_Uw  reason: not valid java name */
    public static /* synthetic */ void m127$r8$lambda$y8fy3mWjKkDKEPGKU3W34ch_Uw(DropdownMenuEndIconDelegate dropdownMenuEndIconDelegate) {
        boolean isPopupShowing = dropdownMenuEndIconDelegate.autoCompleteTextView.isPopupShowing();
        dropdownMenuEndIconDelegate.setEndIconChecked(isPopupShowing);
        dropdownMenuEndIconDelegate.dropdownPopupDirty = isPopupShowing;
    }

    /* access modifiers changed from: package-private */
    public View.OnFocusChangeListener getOnEditTextFocusChangeListener() {
        return this.onEditTextFocusChangeListener;
    }

    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        if (!EditTextUtils.isEditable(this.autoCompleteTextView)) {
            accessibilityNodeInfoCompat.setClassName(Spinner.class.getName());
        }
        if (accessibilityNodeInfoCompat.isShowingHintText()) {
            accessibilityNodeInfoCompat.setHintText((CharSequence) null);
        }
    }

    public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        if (this.accessibilityManager.isEnabled() && !EditTextUtils.isEditable(this.autoCompleteTextView)) {
            boolean z = (accessibilityEvent.getEventType() == 32768 || accessibilityEvent.getEventType() == 8) && this.isEndIconChecked && !this.autoCompleteTextView.isPopupShowing();
            if (accessibilityEvent.getEventType() == 1 || z) {
                showHideDropdown();
                updateDropdownPopupDirty();
            }
        }
    }

    /* access modifiers changed from: private */
    public void showHideDropdown() {
        if (this.autoCompleteTextView != null) {
            if (isDropdownPopupActive()) {
                this.dropdownPopupDirty = false;
            }
            if (!this.dropdownPopupDirty) {
                setEndIconChecked(!this.isEndIconChecked);
                if (this.isEndIconChecked) {
                    this.autoCompleteTextView.requestFocus();
                    this.autoCompleteTextView.showDropDown();
                    return;
                }
                this.autoCompleteTextView.dismissDropDown();
                return;
            }
            this.dropdownPopupDirty = false;
        }
    }

    private void setUpDropdownShowHideBehavior() {
        this.autoCompleteTextView.setOnTouchListener(new DropdownMenuEndIconDelegate$$ExternalSyntheticLambda1(this));
        this.autoCompleteTextView.setOnDismissListener(new DropdownMenuEndIconDelegate$$ExternalSyntheticLambda2(this));
        this.autoCompleteTextView.setThreshold(0);
    }

    /* renamed from: $r8$lambda$rHz86UpD1kaG-bWDU0-jLf2Nncs  reason: not valid java name */
    public static /* synthetic */ boolean m128$r8$lambda$rHz86UpD1kaGbWDU0jLf2Nncs(DropdownMenuEndIconDelegate dropdownMenuEndIconDelegate, View view, MotionEvent motionEvent) {
        dropdownMenuEndIconDelegate.getClass();
        if (motionEvent.getAction() == 1) {
            if (dropdownMenuEndIconDelegate.isDropdownPopupActive()) {
                dropdownMenuEndIconDelegate.dropdownPopupDirty = false;
            }
            dropdownMenuEndIconDelegate.showHideDropdown();
            dropdownMenuEndIconDelegate.updateDropdownPopupDirty();
        }
        return false;
    }

    /* renamed from: $r8$lambda$scsW-sP1aqfljH19IzwcrEYD_Bo  reason: not valid java name */
    public static /* synthetic */ void m129$r8$lambda$scsWsP1aqfljH19IzwcrEYD_Bo(DropdownMenuEndIconDelegate dropdownMenuEndIconDelegate) {
        dropdownMenuEndIconDelegate.updateDropdownPopupDirty();
        dropdownMenuEndIconDelegate.setEndIconChecked(false);
    }

    private boolean isDropdownPopupActive() {
        long uptimeMillis = SystemClock.uptimeMillis() - this.dropdownPopupActivatedAt;
        return uptimeMillis < 0 || uptimeMillis > 300;
    }

    private static AutoCompleteTextView castAutoCompleteTextViewOrThrow(EditText editText) {
        if (editText instanceof AutoCompleteTextView) {
            return (AutoCompleteTextView) editText;
        }
        throw new RuntimeException("EditText needs to be an AutoCompleteTextView if an Exposed Dropdown Menu is being used.");
    }

    private void updateDropdownPopupDirty() {
        this.dropdownPopupDirty = true;
        this.dropdownPopupActivatedAt = SystemClock.uptimeMillis();
    }

    private void setEndIconChecked(boolean z) {
        if (this.isEndIconChecked != z) {
            this.isEndIconChecked = z;
            this.fadeInAnim.cancel();
            this.fadeOutAnim.start();
        }
    }

    private void initAnimators() {
        this.fadeInAnim = getAlphaAnimator(this.animationFadeInDuration, 0.0f, 1.0f);
        ValueAnimator alphaAnimator = getAlphaAnimator(this.animationFadeOutDuration, 1.0f, 0.0f);
        this.fadeOutAnim = alphaAnimator;
        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                DropdownMenuEndIconDelegate.this.refreshIconState();
                DropdownMenuEndIconDelegate.this.fadeInAnim.start();
            }
        });
    }

    private ValueAnimator getAlphaAnimator(int i, float... fArr) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
        ofFloat.setInterpolator(this.animationFadeInterpolator);
        ofFloat.setDuration((long) i);
        ofFloat.addUpdateListener(new DropdownMenuEndIconDelegate$$ExternalSyntheticLambda0(this));
        return ofFloat;
    }

    public static /* synthetic */ void $r8$lambda$CZy5WigobS3oySmsGDEVS50zVNw(DropdownMenuEndIconDelegate dropdownMenuEndIconDelegate, ValueAnimator valueAnimator) {
        dropdownMenuEndIconDelegate.getClass();
        dropdownMenuEndIconDelegate.endIconView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }
}
