package com.google.android.material.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
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

public class HideViewOnScrollBehavior<V extends View> extends CoordinatorLayout.Behavior {
    private static final int ENTER_ANIM_DURATION_ATTR = R$attr.motionDurationLong2;
    private static final int ENTER_EXIT_ANIM_EASING_ATTR = R$attr.motionEasingEmphasizedInterpolator;
    private static final int EXIT_ANIM_DURATION_ATTR = R$attr.motionDurationMedium4;
    /* access modifiers changed from: private */
    public AccessibilityManager accessibilityManager;
    private int additionalHiddenOffset = 0;
    /* access modifiers changed from: private */
    public ViewPropertyAnimator currentAnimator;
    private int currentState = 2;
    private boolean disableOnTouchExploration = true;
    private int enterAnimDuration;
    private TimeInterpolator enterAnimInterpolator;
    private int exitAnimDuration;
    private TimeInterpolator exitAnimInterpolator;
    private HideViewOnScrollDelegate hideOnScrollViewDelegate;
    private final LinkedHashSet onScrollStateChangedListeners = new LinkedHashSet();
    private int size = 0;
    /* access modifiers changed from: private */
    public AccessibilityManager.TouchExplorationStateChangeListener touchExplorationListener;

    private boolean isGravityBottom(int i) {
        return i == 80 || i == 81;
    }

    private boolean isGravityLeft(int i) {
        return i == 3 || i == 19;
    }

    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i, int i2) {
        return i == 2;
    }

    public HideViewOnScrollBehavior() {
    }

    public HideViewOnScrollBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void setViewEdge(View view, int i) {
        int i2 = ((CoordinatorLayout.LayoutParams) view.getLayoutParams()).gravity;
        if (isGravityBottom(i2)) {
            setViewEdge(1);
        } else {
            setViewEdge(isGravityLeft(Gravity.getAbsoluteGravity(i2, i)) ? 2 : 0);
        }
    }

    public void setViewEdge(int i) {
        HideViewOnScrollDelegate hideViewOnScrollDelegate = this.hideOnScrollViewDelegate;
        if (hideViewOnScrollDelegate != null && hideViewOnScrollDelegate.getViewEdge() == i) {
            return;
        }
        if (i == 0) {
            this.hideOnScrollViewDelegate = new HideRightViewOnScrollDelegate();
        } else if (i == 1) {
            this.hideOnScrollViewDelegate = new HideBottomViewOnScrollDelegate();
        } else if (i == 2) {
            this.hideOnScrollViewDelegate = new HideLeftViewOnScrollDelegate();
        } else {
            throw new IllegalArgumentException("Invalid view edge position value: " + i + ". Must be " + 0 + ", " + 1 + " or " + 2 + ".");
        }
    }

    private void disableIfTouchExplorationEnabled(View view) {
        if (this.accessibilityManager == null) {
            this.accessibilityManager = (AccessibilityManager) ContextCompat.getSystemService(view.getContext(), AccessibilityManager.class);
        }
        if (this.accessibilityManager != null && this.touchExplorationListener == null) {
            HideViewOnScrollBehavior$$ExternalSyntheticLambda0 hideViewOnScrollBehavior$$ExternalSyntheticLambda0 = new HideViewOnScrollBehavior$$ExternalSyntheticLambda0(this, view);
            this.touchExplorationListener = hideViewOnScrollBehavior$$ExternalSyntheticLambda0;
            this.accessibilityManager.addTouchExplorationStateChangeListener(hideViewOnScrollBehavior$$ExternalSyntheticLambda0);
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                public void onViewAttachedToWindow(View view) {
                }

                public void onViewDetachedFromWindow(View view) {
                    if (HideViewOnScrollBehavior.this.touchExplorationListener != null && HideViewOnScrollBehavior.this.accessibilityManager != null) {
                        HideViewOnScrollBehavior.this.accessibilityManager.removeTouchExplorationStateChangeListener(HideViewOnScrollBehavior.this.touchExplorationListener);
                        AccessibilityManager.TouchExplorationStateChangeListener unused = HideViewOnScrollBehavior.this.touchExplorationListener = null;
                    }
                }
            });
        }
    }

    public static /* synthetic */ void $r8$lambda$O4yxcwWX0oqhtqJeVevJwrB8hK4(HideViewOnScrollBehavior hideViewOnScrollBehavior, View view, boolean z) {
        if (hideViewOnScrollBehavior.disableOnTouchExploration && z && hideViewOnScrollBehavior.isScrolledOut()) {
            hideViewOnScrollBehavior.slideIn(view);
        }
    }

    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        disableIfTouchExplorationEnabled(view);
        setViewEdge(view, i);
        this.size = this.hideOnScrollViewDelegate.getSize(view, (ViewGroup.MarginLayoutParams) view.getLayoutParams());
        this.enterAnimDuration = MotionUtils.resolveThemeDuration(view.getContext(), ENTER_ANIM_DURATION_ATTR, 225);
        this.exitAnimDuration = MotionUtils.resolveThemeDuration(view.getContext(), EXIT_ANIM_DURATION_ATTR, 175);
        Context context = view.getContext();
        int i2 = ENTER_EXIT_ANIM_EASING_ATTR;
        this.enterAnimInterpolator = MotionUtils.resolveThemeInterpolator(context, i2, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        this.exitAnimInterpolator = MotionUtils.resolveThemeInterpolator(view.getContext(), i2, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        return super.onLayoutChild(coordinatorLayout, view, i);
    }

    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        if (i2 > 0) {
            slideOut(view);
        } else if (i2 < 0) {
            slideIn(view);
        }
    }

    public boolean isScrolledIn() {
        return this.currentState == 2;
    }

    public void slideIn(View view) {
        slideIn(view, true);
    }

    public void slideIn(View view, boolean z) {
        if (!isScrolledIn()) {
            ViewPropertyAnimator viewPropertyAnimator = this.currentAnimator;
            if (viewPropertyAnimator != null) {
                viewPropertyAnimator.cancel();
                view.clearAnimation();
            }
            updateCurrentState(view, 2);
            int targetTranslation = this.hideOnScrollViewDelegate.getTargetTranslation();
            if (z) {
                animateChildTo(view, targetTranslation, (long) this.enterAnimDuration, this.enterAnimInterpolator);
                return;
            }
            this.hideOnScrollViewDelegate.setViewTranslation(view, targetTranslation);
        }
    }

    public boolean isScrolledOut() {
        return this.currentState == 1;
    }

    public void slideOut(View view) {
        slideOut(view, true);
    }

    public void slideOut(View view, boolean z) {
        AccessibilityManager accessibilityManager2;
        if (!isScrolledOut()) {
            if (!this.disableOnTouchExploration || (accessibilityManager2 = this.accessibilityManager) == null || !accessibilityManager2.isTouchExplorationEnabled()) {
                ViewPropertyAnimator viewPropertyAnimator = this.currentAnimator;
                if (viewPropertyAnimator != null) {
                    viewPropertyAnimator.cancel();
                    view.clearAnimation();
                }
                updateCurrentState(view, 1);
                int i = this.size + this.additionalHiddenOffset;
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
        this.currentAnimator = this.hideOnScrollViewDelegate.getViewTranslationAnimator(view, i).setInterpolator(timeInterpolator).setDuration(j).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                ViewPropertyAnimator unused = HideViewOnScrollBehavior.this.currentAnimator = null;
            }
        });
    }
}
