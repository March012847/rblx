package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.R$attr;
import com.google.android.material.R$drawable;
import com.google.android.material.R$string;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.motion.MotionUtils;

class ClearTextEndIconDelegate extends EndIconDelegate {
    private final int animationFadeDuration;
    private final TimeInterpolator animationFadeInterpolator;
    private final int animationScaleDuration;
    private final TimeInterpolator animationScaleInterpolator;
    private EditText editText;
    private AnimatorSet iconInAnim;
    private ValueAnimator iconOutAnim;
    private final View.OnFocusChangeListener onFocusChangeListener = new ClearTextEndIconDelegate$$ExternalSyntheticLambda1(this);
    private final View.OnClickListener onIconClickListener = new ClearTextEndIconDelegate$$ExternalSyntheticLambda0(this);

    public static /* synthetic */ void $r8$lambda$0hH44JQ_Ha7XcbZ_7uARPxidtUk(ClearTextEndIconDelegate clearTextEndIconDelegate, View view) {
        EditText editText2 = clearTextEndIconDelegate.editText;
        if (editText2 != null) {
            Editable text = editText2.getText();
            if (text != null) {
                text.clear();
            }
            clearTextEndIconDelegate.refreshIconState();
        }
    }

    ClearTextEndIconDelegate(EndCompoundLayout endCompoundLayout) {
        super(endCompoundLayout);
        Context context = endCompoundLayout.getContext();
        int i = R$attr.motionDurationShort3;
        this.animationFadeDuration = MotionUtils.resolveThemeDuration(context, i, 100);
        this.animationScaleDuration = MotionUtils.resolveThemeDuration(endCompoundLayout.getContext(), i, 150);
        this.animationFadeInterpolator = MotionUtils.resolveThemeInterpolator(endCompoundLayout.getContext(), R$attr.motionEasingLinearInterpolator, AnimationUtils.LINEAR_INTERPOLATOR);
        this.animationScaleInterpolator = MotionUtils.resolveThemeInterpolator(endCompoundLayout.getContext(), R$attr.motionEasingEmphasizedInterpolator, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
    }

    /* access modifiers changed from: package-private */
    public void setUp() {
        initAnimators();
    }

    /* access modifiers changed from: package-private */
    public void tearDown() {
        EditText editText2 = this.editText;
        if (editText2 != null) {
            editText2.post(new ClearTextEndIconDelegate$$ExternalSyntheticLambda3(this));
        }
    }

    /* access modifiers changed from: package-private */
    public int getIconDrawableResId() {
        return R$drawable.mtrl_ic_cancel;
    }

    /* access modifiers changed from: package-private */
    public int getIconContentDescriptionResId() {
        return R$string.clear_text_end_icon_content_description;
    }

    /* access modifiers changed from: package-private */
    public void onSuffixVisibilityChanged(boolean z) {
        if (this.endLayout.getSuffixText() != null) {
            animateIcon(z);
        }
    }

    /* access modifiers changed from: package-private */
    public View.OnClickListener getOnIconClickListener() {
        return this.onIconClickListener;
    }

    public void onEditTextAttached(EditText editText2) {
        this.editText = editText2;
        this.textInputLayout.setEndIconVisible(shouldBeVisible());
    }

    /* access modifiers changed from: package-private */
    public void afterEditTextChanged(Editable editable) {
        if (this.endLayout.getSuffixText() == null) {
            animateIcon(shouldBeVisible());
        }
    }

    /* access modifiers changed from: package-private */
    public View.OnFocusChangeListener getOnEditTextFocusChangeListener() {
        return this.onFocusChangeListener;
    }

    /* access modifiers changed from: package-private */
    public View.OnFocusChangeListener getOnIconViewFocusChangeListener() {
        return this.onFocusChangeListener;
    }

    /* access modifiers changed from: private */
    public void animateIcon(boolean z) {
        boolean z2 = this.endLayout.isEndIconVisible() == z;
        if (z && !this.iconInAnim.isRunning()) {
            this.iconOutAnim.cancel();
            this.iconInAnim.start();
            if (z2) {
                this.iconInAnim.end();
            }
        } else if (!z) {
            this.iconInAnim.cancel();
            this.iconOutAnim.start();
            if (z2) {
                this.iconOutAnim.end();
            }
        }
    }

    private void initAnimators() {
        ValueAnimator scaleAnimator = getScaleAnimator();
        ValueAnimator alphaAnimator = getAlphaAnimator(0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        this.iconInAnim = animatorSet;
        animatorSet.playTogether(new Animator[]{scaleAnimator, alphaAnimator});
        this.iconInAnim.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                ClearTextEndIconDelegate.this.endLayout.setEndIconVisible(true);
            }
        });
        ValueAnimator alphaAnimator2 = getAlphaAnimator(1.0f, 0.0f);
        this.iconOutAnim = alphaAnimator2;
        alphaAnimator2.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                ClearTextEndIconDelegate.this.endLayout.setEndIconVisible(false);
            }
        });
    }

    private ValueAnimator getAlphaAnimator(float... fArr) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
        ofFloat.setInterpolator(this.animationFadeInterpolator);
        ofFloat.setDuration((long) this.animationFadeDuration);
        ofFloat.addUpdateListener(new ClearTextEndIconDelegate$$ExternalSyntheticLambda2(this));
        return ofFloat;
    }

    public static /* synthetic */ void $r8$lambda$o3nCOWRDa9a4Qn8eyddKdCWDWRM(ClearTextEndIconDelegate clearTextEndIconDelegate, ValueAnimator valueAnimator) {
        clearTextEndIconDelegate.getClass();
        clearTextEndIconDelegate.endIconView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    private ValueAnimator getScaleAnimator() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.8f, 1.0f});
        ofFloat.setInterpolator(this.animationScaleInterpolator);
        ofFloat.setDuration((long) this.animationScaleDuration);
        ofFloat.addUpdateListener(new ClearTextEndIconDelegate$$ExternalSyntheticLambda4(this));
        return ofFloat;
    }

    public static /* synthetic */ void $r8$lambda$uIZV0fdyhL28ssalFhIWtJjVwUo(ClearTextEndIconDelegate clearTextEndIconDelegate, ValueAnimator valueAnimator) {
        clearTextEndIconDelegate.getClass();
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        clearTextEndIconDelegate.endIconView.setScaleX(floatValue);
        clearTextEndIconDelegate.endIconView.setScaleY(floatValue);
    }

    private boolean shouldBeVisible() {
        EditText editText2 = this.editText;
        if (editText2 != null) {
            return (editText2.hasFocus() || this.endIconView.hasFocus()) && this.editText.getText().length() > 0;
        }
        return false;
    }
}
