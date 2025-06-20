package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R$attr;
import com.google.android.material.R$dimen;
import com.google.android.material.R$id;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialResources;
import java.util.ArrayList;
import java.util.List;

final class IndicatorViewController {
    /* access modifiers changed from: private */
    public Animator captionAnimator;
    private FrameLayout captionArea;
    /* access modifiers changed from: private */
    public int captionDisplayed;
    private final int captionFadeInAnimationDuration;
    private final TimeInterpolator captionFadeInAnimationInterpolator;
    private final int captionFadeOutAnimationDuration;
    private final TimeInterpolator captionFadeOutAnimationInterpolator;
    private int captionToShow;
    private final int captionTranslationYAnimationDuration;
    private final TimeInterpolator captionTranslationYAnimationInterpolator;
    private final float captionTranslationYPx;
    private final Context context;
    private boolean errorEnabled;
    private CharSequence errorText;
    private int errorTextAppearance;
    /* access modifiers changed from: private */
    public TextView errorView;
    private int errorViewAccessibilityLiveRegion;
    private CharSequence errorViewContentDescription;
    private ColorStateList errorViewTextColor;
    private CharSequence helperText;
    private boolean helperTextEnabled;
    private int helperTextTextAppearance;
    private TextView helperTextView;
    private ColorStateList helperTextViewTextColor;
    private LinearLayout indicatorArea;
    private int indicatorsAdded;
    /* access modifiers changed from: private */
    public final TextInputLayout textInputView;
    private Typeface typeface;

    /* access modifiers changed from: package-private */
    public boolean isCaptionView(int i) {
        return i == 0 || i == 1;
    }

    public IndicatorViewController(TextInputLayout textInputLayout) {
        Context context2 = textInputLayout.getContext();
        this.context = context2;
        this.textInputView = textInputLayout;
        this.captionTranslationYPx = (float) context2.getResources().getDimensionPixelSize(R$dimen.design_textinput_caption_translate_y);
        int i = R$attr.motionDurationShort4;
        this.captionTranslationYAnimationDuration = MotionUtils.resolveThemeDuration(context2, i, 217);
        this.captionFadeInAnimationDuration = MotionUtils.resolveThemeDuration(context2, R$attr.motionDurationMedium4, 167);
        this.captionFadeOutAnimationDuration = MotionUtils.resolveThemeDuration(context2, i, 167);
        int i2 = R$attr.motionEasingEmphasizedDecelerateInterpolator;
        this.captionTranslationYAnimationInterpolator = MotionUtils.resolveThemeInterpolator(context2, i2, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        TimeInterpolator timeInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
        this.captionFadeInAnimationInterpolator = MotionUtils.resolveThemeInterpolator(context2, i2, timeInterpolator);
        this.captionFadeOutAnimationInterpolator = MotionUtils.resolveThemeInterpolator(context2, R$attr.motionEasingLinearInterpolator, timeInterpolator);
    }

    /* access modifiers changed from: package-private */
    public void showHelper(CharSequence charSequence) {
        cancelCaptionAnimator();
        this.helperText = charSequence;
        this.helperTextView.setText(charSequence);
        int i = this.captionDisplayed;
        if (i != 2) {
            this.captionToShow = 2;
        }
        updateCaptionViewsVisibility(i, this.captionToShow, shouldAnimateCaptionView(this.helperTextView, charSequence));
    }

    /* access modifiers changed from: package-private */
    public void hideHelperText() {
        cancelCaptionAnimator();
        int i = this.captionDisplayed;
        if (i == 2) {
            this.captionToShow = 0;
        }
        updateCaptionViewsVisibility(i, this.captionToShow, shouldAnimateCaptionView(this.helperTextView, ""));
    }

    /* access modifiers changed from: package-private */
    public void showError(CharSequence charSequence) {
        cancelCaptionAnimator();
        this.errorText = charSequence;
        this.errorView.setText(charSequence);
        int i = this.captionDisplayed;
        if (i != 1) {
            this.captionToShow = 1;
        }
        updateCaptionViewsVisibility(i, this.captionToShow, shouldAnimateCaptionView(this.errorView, charSequence));
    }

    /* access modifiers changed from: package-private */
    public void hideError() {
        this.errorText = null;
        cancelCaptionAnimator();
        if (this.captionDisplayed == 1) {
            if (!this.helperTextEnabled || TextUtils.isEmpty(this.helperText)) {
                this.captionToShow = 0;
            } else {
                this.captionToShow = 2;
            }
        }
        updateCaptionViewsVisibility(this.captionDisplayed, this.captionToShow, shouldAnimateCaptionView(this.errorView, ""));
    }

    private boolean shouldAnimateCaptionView(TextView textView, CharSequence charSequence) {
        if (!this.textInputView.isLaidOut() || !this.textInputView.isEnabled()) {
            return false;
        }
        return this.captionToShow != this.captionDisplayed || textView == null || !TextUtils.equals(textView.getText(), charSequence);
    }

    private void updateCaptionViewsVisibility(int i, int i2, boolean z) {
        IndicatorViewController indicatorViewController;
        if (i != i2) {
            if (z) {
                AnimatorSet animatorSet = new AnimatorSet();
                this.captionAnimator = animatorSet;
                ArrayList arrayList = new ArrayList();
                indicatorViewController = this;
                int i3 = i;
                int i4 = i2;
                indicatorViewController.createCaptionAnimators(arrayList, this.helperTextEnabled, this.helperTextView, 2, i3, i4);
                indicatorViewController.createCaptionAnimators(arrayList, indicatorViewController.errorEnabled, indicatorViewController.errorView, 1, i3, i4);
                AnimatorSetCompat.playTogether(animatorSet, arrayList);
                final TextView captionViewFromDisplayState = indicatorViewController.getCaptionViewFromDisplayState(i3);
                final TextView captionViewFromDisplayState2 = indicatorViewController.getCaptionViewFromDisplayState(i4);
                final int i5 = i3;
                final int i6 = i4;
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        int unused = IndicatorViewController.this.captionDisplayed = i6;
                        Animator unused2 = IndicatorViewController.this.captionAnimator = null;
                        TextView textView = captionViewFromDisplayState;
                        if (textView != null) {
                            textView.setVisibility(4);
                            if (i5 == 1 && IndicatorViewController.this.errorView != null) {
                                IndicatorViewController.this.errorView.setText((CharSequence) null);
                            }
                        }
                        TextView textView2 = captionViewFromDisplayState2;
                        if (textView2 != null) {
                            textView2.setTranslationY(0.0f);
                            captionViewFromDisplayState2.setAlpha(1.0f);
                        }
                    }

                    public void onAnimationStart(Animator animator) {
                        TextView textView = captionViewFromDisplayState2;
                        if (textView != null) {
                            textView.setVisibility(0);
                            captionViewFromDisplayState2.setAlpha(0.0f);
                        }
                    }
                });
                animatorSet.start();
            } else {
                indicatorViewController = this;
                indicatorViewController.setCaptionViewVisibilities(i, i2);
            }
            indicatorViewController.textInputView.updateEditTextBackground();
            indicatorViewController.textInputView.updateLabelState(z);
            indicatorViewController.textInputView.updateTextInputBoxState();
        }
    }

    private void setCaptionViewVisibilities(int i, int i2) {
        TextView captionViewFromDisplayState;
        TextView captionViewFromDisplayState2;
        if (i != i2) {
            if (!(i2 == 0 || (captionViewFromDisplayState2 = getCaptionViewFromDisplayState(i2)) == null)) {
                captionViewFromDisplayState2.setVisibility(0);
                captionViewFromDisplayState2.setAlpha(1.0f);
            }
            if (!(i == 0 || (captionViewFromDisplayState = getCaptionViewFromDisplayState(i)) == null)) {
                captionViewFromDisplayState.setVisibility(4);
                if (i == 1) {
                    captionViewFromDisplayState.setText((CharSequence) null);
                }
            }
            this.captionDisplayed = i2;
        }
    }

    private void createCaptionAnimators(List list, boolean z, TextView textView, int i, int i2, int i3) {
        if (textView != null && z) {
            if (i == i3 || i == i2) {
                ObjectAnimator createCaptionOpacityAnimator = createCaptionOpacityAnimator(textView, i3 == i);
                if (i == i3 && i2 != 0) {
                    createCaptionOpacityAnimator.setStartDelay((long) this.captionFadeOutAnimationDuration);
                }
                list.add(createCaptionOpacityAnimator);
                if (i3 == i && i2 != 0) {
                    ObjectAnimator createCaptionTranslationYAnimator = createCaptionTranslationYAnimator(textView);
                    createCaptionTranslationYAnimator.setStartDelay((long) this.captionFadeOutAnimationDuration);
                    list.add(createCaptionTranslationYAnimator);
                }
            }
        }
    }

    private ObjectAnimator createCaptionOpacityAnimator(TextView textView, boolean z) {
        int i;
        TimeInterpolator timeInterpolator;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(textView, View.ALPHA, new float[]{z ? 1.0f : 0.0f});
        if (z) {
            i = this.captionFadeInAnimationDuration;
        } else {
            i = this.captionFadeOutAnimationDuration;
        }
        ofFloat.setDuration((long) i);
        if (z) {
            timeInterpolator = this.captionFadeInAnimationInterpolator;
        } else {
            timeInterpolator = this.captionFadeOutAnimationInterpolator;
        }
        ofFloat.setInterpolator(timeInterpolator);
        return ofFloat;
    }

    private ObjectAnimator createCaptionTranslationYAnimator(TextView textView) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(textView, View.TRANSLATION_Y, new float[]{-this.captionTranslationYPx, 0.0f});
        ofFloat.setDuration((long) this.captionTranslationYAnimationDuration);
        ofFloat.setInterpolator(this.captionTranslationYAnimationInterpolator);
        return ofFloat;
    }

    /* access modifiers changed from: package-private */
    public void cancelCaptionAnimator() {
        Animator animator = this.captionAnimator;
        if (animator != null) {
            animator.cancel();
        }
    }

    private TextView getCaptionViewFromDisplayState(int i) {
        if (i == 1) {
            return this.errorView;
        }
        if (i != 2) {
            return null;
        }
        return this.helperTextView;
    }

    /* access modifiers changed from: package-private */
    public void adjustIndicatorPadding() {
        if (canAdjustIndicatorPadding()) {
            EditText editText = this.textInputView.getEditText();
            boolean isFontScaleAtLeast1_3 = MaterialResources.isFontScaleAtLeast1_3(this.context);
            LinearLayout linearLayout = this.indicatorArea;
            int i = R$dimen.material_helper_text_font_1_3_padding_horizontal;
            linearLayout.setPaddingRelative(getIndicatorPadding(isFontScaleAtLeast1_3, i, editText.getPaddingStart()), getIndicatorPadding(isFontScaleAtLeast1_3, R$dimen.material_helper_text_font_1_3_padding_top, this.context.getResources().getDimensionPixelSize(R$dimen.material_helper_text_default_padding_top)), getIndicatorPadding(isFontScaleAtLeast1_3, i, editText.getPaddingEnd()), 0);
        }
    }

    private boolean canAdjustIndicatorPadding() {
        return (this.indicatorArea == null || this.textInputView.getEditText() == null) ? false : true;
    }

    private int getIndicatorPadding(boolean z, int i, int i2) {
        return z ? this.context.getResources().getDimensionPixelSize(i) : i2;
    }

    /* access modifiers changed from: package-private */
    public void addIndicator(TextView textView, int i) {
        if (this.indicatorArea == null && this.captionArea == null) {
            LinearLayout linearLayout = new LinearLayout(this.context);
            this.indicatorArea = linearLayout;
            linearLayout.setOrientation(0);
            this.textInputView.addView(this.indicatorArea, -1, -2);
            this.captionArea = new FrameLayout(this.context);
            this.indicatorArea.addView(this.captionArea, new LinearLayout.LayoutParams(0, -2, 1.0f));
            if (this.textInputView.getEditText() != null) {
                adjustIndicatorPadding();
            }
        }
        if (isCaptionView(i)) {
            this.captionArea.setVisibility(0);
            this.captionArea.addView(textView);
        } else {
            this.indicatorArea.addView(textView, new LinearLayout.LayoutParams(-2, -2));
        }
        this.indicatorArea.setVisibility(0);
        this.indicatorsAdded++;
    }

    /* access modifiers changed from: package-private */
    public void removeIndicator(TextView textView, int i) {
        FrameLayout frameLayout;
        if (this.indicatorArea != null) {
            if (!isCaptionView(i) || (frameLayout = this.captionArea) == null) {
                this.indicatorArea.removeView(textView);
            } else {
                frameLayout.removeView(textView);
            }
            int i2 = this.indicatorsAdded - 1;
            this.indicatorsAdded = i2;
            setViewGroupGoneIfEmpty(this.indicatorArea, i2);
        }
    }

    private void setViewGroupGoneIfEmpty(ViewGroup viewGroup, int i) {
        if (i == 0) {
            viewGroup.setVisibility(8);
        }
    }

    /* access modifiers changed from: package-private */
    public void setErrorEnabled(boolean z) {
        if (this.errorEnabled != z) {
            cancelCaptionAnimator();
            if (z) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(this.context);
                this.errorView = appCompatTextView;
                appCompatTextView.setId(R$id.textinput_error);
                this.errorView.setTextAlignment(5);
                Typeface typeface2 = this.typeface;
                if (typeface2 != null) {
                    this.errorView.setTypeface(typeface2);
                }
                setErrorTextAppearance(this.errorTextAppearance);
                setErrorViewTextColor(this.errorViewTextColor);
                setErrorContentDescription(this.errorViewContentDescription);
                setErrorAccessibilityLiveRegion(this.errorViewAccessibilityLiveRegion);
                this.errorView.setVisibility(4);
                addIndicator(this.errorView, 0);
            } else {
                hideError();
                removeIndicator(this.errorView, 0);
                this.errorView = null;
                this.textInputView.updateEditTextBackground();
                this.textInputView.updateTextInputBoxState();
            }
            this.errorEnabled = z;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isErrorEnabled() {
        return this.errorEnabled;
    }

    /* access modifiers changed from: package-private */
    public boolean isHelperTextEnabled() {
        return this.helperTextEnabled;
    }

    /* access modifiers changed from: package-private */
    public void setHelperTextEnabled(boolean z) {
        if (this.helperTextEnabled != z) {
            cancelCaptionAnimator();
            if (z) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(this.context);
                this.helperTextView = appCompatTextView;
                appCompatTextView.setId(R$id.textinput_helper_text);
                this.helperTextView.setTextAlignment(5);
                Typeface typeface2 = this.typeface;
                if (typeface2 != null) {
                    this.helperTextView.setTypeface(typeface2);
                }
                this.helperTextView.setVisibility(4);
                this.helperTextView.setAccessibilityLiveRegion(1);
                setHelperTextAppearance(this.helperTextTextAppearance);
                setHelperTextViewTextColor(this.helperTextViewTextColor);
                addIndicator(this.helperTextView, 1);
                this.helperTextView.setAccessibilityDelegate(new View.AccessibilityDelegate() {
                    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                        EditText editText = IndicatorViewController.this.textInputView.getEditText();
                        if (editText != null) {
                            accessibilityNodeInfo.setLabeledBy(editText);
                        }
                    }
                });
            } else {
                hideHelperText();
                removeIndicator(this.helperTextView, 1);
                this.helperTextView = null;
                this.textInputView.updateEditTextBackground();
                this.textInputView.updateTextInputBoxState();
            }
            this.helperTextEnabled = z;
        }
    }

    /* access modifiers changed from: package-private */
    public View getHelperTextView() {
        return this.helperTextView;
    }

    /* access modifiers changed from: package-private */
    public boolean errorShouldBeShown() {
        return isCaptionStateError(this.captionToShow);
    }

    private boolean isCaptionStateError(int i) {
        if (i != 1 || this.errorView == null || TextUtils.isEmpty(this.errorText)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean helperTextIsDisplayed() {
        return isCaptionStateHelperText(this.captionDisplayed);
    }

    private boolean isCaptionStateHelperText(int i) {
        return i == 2 && this.helperTextView != null && !TextUtils.isEmpty(this.helperText);
    }

    /* access modifiers changed from: package-private */
    public CharSequence getErrorText() {
        return this.errorText;
    }

    /* access modifiers changed from: package-private */
    public int getErrorViewCurrentTextColor() {
        TextView textView = this.errorView;
        if (textView != null) {
            return textView.getCurrentTextColor();
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public ColorStateList getErrorViewTextColors() {
        TextView textView = this.errorView;
        if (textView != null) {
            return textView.getTextColors();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setErrorViewTextColor(ColorStateList colorStateList) {
        this.errorViewTextColor = colorStateList;
        TextView textView = this.errorView;
        if (textView != null && colorStateList != null) {
            textView.setTextColor(colorStateList);
        }
    }

    /* access modifiers changed from: package-private */
    public void setErrorTextAppearance(int i) {
        this.errorTextAppearance = i;
        TextView textView = this.errorView;
        if (textView != null) {
            this.textInputView.setTextAppearanceCompatWithErrorFallback(textView, i);
        }
    }

    /* access modifiers changed from: package-private */
    public void setErrorContentDescription(CharSequence charSequence) {
        this.errorViewContentDescription = charSequence;
        TextView textView = this.errorView;
        if (textView != null) {
            textView.setContentDescription(charSequence);
        }
    }

    /* access modifiers changed from: package-private */
    public void setErrorAccessibilityLiveRegion(int i) {
        this.errorViewAccessibilityLiveRegion = i;
        TextView textView = this.errorView;
        if (textView != null) {
            textView.setAccessibilityLiveRegion(i);
        }
    }

    /* access modifiers changed from: package-private */
    public void setHelperTextViewTextColor(ColorStateList colorStateList) {
        this.helperTextViewTextColor = colorStateList;
        TextView textView = this.helperTextView;
        if (textView != null && colorStateList != null) {
            textView.setTextColor(colorStateList);
        }
    }

    /* access modifiers changed from: package-private */
    public void setHelperTextAppearance(int i) {
        this.helperTextTextAppearance = i;
        TextView textView = this.helperTextView;
        if (textView != null) {
            TextViewCompat.setTextAppearance(textView, i);
        }
    }
}
