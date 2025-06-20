package com.google.android.material.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import com.google.android.material.R$attr;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.motion.MotionUtils;
import java.util.Iterator;
import java.util.LinkedHashSet;

@Deprecated
public class HideBottomViewOnScrollBehavior<V extends View> extends CoordinatorLayout.Behavior {
    private static final int ENTER_ANIM_DURATION_ATTR = R$attr.motionDurationLong2;
    private static final int ENTER_EXIT_ANIM_EASING_ATTR = R$attr.motionEasingEmphasizedInterpolator;
    private static final int EXIT_ANIM_DURATION_ATTR = R$attr.motionDurationMedium4;
    /* access modifiers changed from: private */
    public AccessibilityManager accessibilityManager;
    private int additionalHiddenOffsetY = 0;
    /* access modifiers changed from: private */
    public ViewPropertyAnimator currentAnimator;
    private int currentState = 2;
    private boolean disableOnTouchExploration = true;
    private int enterAnimDuration;
    private TimeInterpolator enterAnimInterpolator;
    private int exitAnimDuration;
    private TimeInterpolator exitAnimInterpolator;
    private int height = 0;
    private final LinkedHashSet onScrollStateChangedListeners = new LinkedHashSet();
    /* access modifiers changed from: private */
    public AccessibilityManager.TouchExplorationStateChangeListener touchExplorationListener;

    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i, int i2) {
        return i == 2;
    }

    public HideBottomViewOnScrollBehavior() {
    }

    public HideBottomViewOnScrollBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        this.height = view.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin;
        this.enterAnimDuration = MotionUtils.resolveThemeDuration(view.getContext(), ENTER_ANIM_DURATION_ATTR, 225);
        this.exitAnimDuration = MotionUtils.resolveThemeDuration(view.getContext(), EXIT_ANIM_DURATION_ATTR, 175);
        Context context = view.getContext();
        int i2 = ENTER_EXIT_ANIM_EASING_ATTR;
        this.enterAnimInterpolator = MotionUtils.resolveThemeInterpolator(context, i2, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        this.exitAnimInterpolator = MotionUtils.resolveThemeInterpolator(view.getContext(), i2, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        disableIfTouchExplorationEnabled(view);
        return super.onLayoutChild(coordinatorLayout, view, i);
    }

    private void disableIfTouchExplorationEnabled(View view) {
        if (this.accessibilityManager == null) {
            this.accessibilityManager = (AccessibilityManager) ContextCompat.getSystemService(view.getContext(), AccessibilityManager.class);
        }
        if (this.accessibilityManager != null && this.touchExplorationListener == null) {
            HideBottomViewOnScrollBehavior$$ExternalSyntheticLambda0 hideBottomViewOnScrollBehavior$$ExternalSyntheticLambda0 = new HideBottomViewOnScrollBehavior$$ExternalSyntheticLambda0(this, view);
            this.touchExplorationListener = hideBottomViewOnScrollBehavior$$ExternalSyntheticLambda0;
            this.accessibilityManager.addTouchExplorationStateChangeListener(hideBottomViewOnScrollBehavior$$ExternalSyntheticLambda0);
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                public void onViewAttachedToWindow(View view) {
                }

                public void onViewDetachedFromWindow(View view) {
                    if (HideBottomViewOnScrollBehavior.this.touchExplorationListener != null && HideBottomViewOnScrollBehavior.this.accessibilityManager != null) {
                        HideBottomViewOnScrollBehavior.this.accessibilityManager.removeTouchExplorationStateChangeListener(HideBottomViewOnScrollBehavior.this.touchExplorationListener);
                        AccessibilityManager.TouchExplorationStateChangeListener unused = HideBottomViewOnScrollBehavior.this.touchExplorationListener = null;
                    }
                }
            });
        }
    }

    /* renamed from: $r8$lambda$u8hW3V09BoGAavr-LZRTR540LS0  reason: not valid java name */
    public static /* synthetic */ void m124$r8$lambda$u8hW3V09BoGAavrLZRTR540LS0(HideBottomViewOnScrollBehavior hideBottomViewOnScrollBehavior, View view, boolean z) {
        if (!z) {
            hideBottomViewOnScrollBehavior.getClass();
        } else if (hideBottomViewOnScrollBehavior.isScrolledDown()) {
            hideBottomViewOnScrollBehavior.slideUp(view);
        }
    }

    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        if (i2 > 0) {
            slideDown(view);
        } else if (i2 < 0) {
            slideUp(view);
        }
    }

    public boolean isScrolledUp() {
        return this.currentState == 2;
    }

    public void slideUp(View view) {
        slideUp(view, true);
    }

    public void slideUp(View view, boolean z) {
        if (!isScrolledUp()) {
            ViewPropertyAnimator viewPropertyAnimator = this.currentAnimator;
            if (viewPropertyAnimator != null) {
                viewPropertyAnimator.cancel();
                view.clearAnimation();
            }
            updateCurrentState(view, 2);
            if (z) {
                animateChildTo(view, 0, (long) this.enterAnimDuration, this.enterAnimInterpolator);
                return;
            }
            view.setTranslationY((float) 0);
        }
    }

    public boolean isScrolledDown() {
        return this.currentState == 1;
    }

    public void slideDown(View view) {
        slideDown(view, true);
    }

    public void slideDown(View view, boolean z) {
        AccessibilityManager accessibilityManager2;
        if (!isScrolledDown()) {
            if (!this.disableOnTouchExploration || (accessibilityManager2 = this.accessibilityManager) == null || !accessibilityManager2.isTouchExplorationEnabled()) {
                ViewPropertyAnimator viewPropertyAnimator = this.currentAnimator;
                if (viewPropertyAnimator != null) {
                    viewPropertyAnimator.cancel();
                    view.clearAnimation();
                }
                updateCurrentState(view, 1);
                int i = this.height + this.additionalHiddenOffsetY;
                if (z) {
                    animateChildTo(view, i, (long) this.exitAnimDuration, this.exitAnimInterpolator);
                    return;
                }
                view.setTranslationY((float) i);
            }
        }
    }

    private void updateCurrentState(View view, int i) {
        this.currentState = i;
        Iterator it = this.onScrollStateChangedListeners.iterator();
        if (it.hasNext()) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            throw null;
        }
    }

    private void animateChildTo(View view, int i, long j, TimeInterpolator timeInterpolator) {
        this.currentAnimator = view.animate().translationY((float) i).setInterpolator(timeInterpolator).setDuration(j).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                ViewPropertyAnimator unused = HideBottomViewOnScrollBehavior.this.currentAnimator = null;
            }
        });
    }
}
