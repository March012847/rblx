package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R$styleable;
import java.lang.ref.WeakReference;
import java.util.List;

public abstract class AppBarLayout extends LinearLayout {

    public class Behavior extends BaseBehavior<AppBarLayout> {
        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        public /* bridge */ /* synthetic */ boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            return super.onInterceptTouchEvent(coordinatorLayout, view, motionEvent);
        }

        public /* bridge */ /* synthetic */ boolean onLayoutChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i) {
            return super.onLayoutChild(coordinatorLayout, appBarLayout, i);
        }

        public /* bridge */ /* synthetic */ boolean onMeasureChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, int i3, int i4) {
            return super.onMeasureChild(coordinatorLayout, appBarLayout, i, i2, i3, i4);
        }

        public /* bridge */ /* synthetic */ void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int[] iArr, int i3) {
            super.onNestedPreScroll(coordinatorLayout, appBarLayout, view, i, i2, iArr, i3);
        }

        public /* bridge */ /* synthetic */ void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
            super.onNestedScroll(coordinatorLayout, appBarLayout, view, i, i2, i3, i4, i5, iArr);
        }

        public /* bridge */ /* synthetic */ void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, Parcelable parcelable) {
            super.onRestoreInstanceState(coordinatorLayout, appBarLayout, parcelable);
        }

        public /* bridge */ /* synthetic */ Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            return super.onSaveInstanceState(coordinatorLayout, appBarLayout);
        }

        public /* bridge */ /* synthetic */ boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, View view2, int i, int i2) {
            return super.onStartNestedScroll(coordinatorLayout, appBarLayout, view, view2, i, i2);
        }

        public /* bridge */ /* synthetic */ void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i) {
            super.onStopNestedScroll(coordinatorLayout, appBarLayout, view, i);
        }

        public /* bridge */ /* synthetic */ boolean onTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            return super.onTouchEvent(coordinatorLayout, view, motionEvent);
        }

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    public class BaseBehavior<T extends AppBarLayout> extends HeaderBehavior {
        private WeakReference lastNestedScrollingChildRef;
        private int lastStartedType;
        private ValueAnimator offsetAnimator;
        /* access modifiers changed from: private */
        public int offsetDelta;
        private SavedState savedState;

        /* access modifiers changed from: package-private */
        public /* bridge */ /* synthetic */ boolean canDragView(View view) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
            return canDragView((AppBarLayout) null);
        }

        /* access modifiers changed from: package-private */
        public /* bridge */ /* synthetic */ int getMaxDragOffset(View view) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
            return getMaxDragOffset((AppBarLayout) null);
        }

        /* access modifiers changed from: package-private */
        public /* bridge */ /* synthetic */ int getScrollRangeForDragFling(View view) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
            return getScrollRangeForDragFling((AppBarLayout) null);
        }

        /* access modifiers changed from: package-private */
        public /* bridge */ /* synthetic */ void onFlingFinished(CoordinatorLayout coordinatorLayout, View view) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
            onFlingFinished(coordinatorLayout, (AppBarLayout) null);
        }

        public /* bridge */ /* synthetic */ boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
            return onLayoutChild(coordinatorLayout, (AppBarLayout) null, i);
        }

        public /* bridge */ /* synthetic */ boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int i4) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
            return onMeasureChild(coordinatorLayout, (AppBarLayout) null, i, i2, i3, i4);
        }

        public /* bridge */ /* synthetic */ void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int[] iArr, int i3) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
            onNestedPreScroll(coordinatorLayout, (AppBarLayout) null, view2, i, i2, iArr, i3);
        }

        public /* bridge */ /* synthetic */ void onNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int i3, int i4, int i5, int[] iArr) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
            onNestedScroll(coordinatorLayout, (AppBarLayout) null, view2, i, i2, i3, i4, i5, iArr);
        }

        public /* bridge */ /* synthetic */ void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, View view, Parcelable parcelable) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
            onRestoreInstanceState(coordinatorLayout, (AppBarLayout) null, parcelable);
        }

        public /* bridge */ /* synthetic */ Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, View view) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
            return onSaveInstanceState(coordinatorLayout, (AppBarLayout) null);
        }

        public /* bridge */ /* synthetic */ boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i, int i2) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
            return onStartNestedScroll(coordinatorLayout, (AppBarLayout) null, view2, view3, i, i2);
        }

        public /* bridge */ /* synthetic */ void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
            onStopNestedScroll(coordinatorLayout, (AppBarLayout) null, view2, i);
        }

        /* access modifiers changed from: package-private */
        public /* bridge */ /* synthetic */ int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
            return setHeaderTopBottomOffset(coordinatorLayout, (AppBarLayout) null, i, i2, i3);
        }

        public BaseBehavior() {
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, View view2, int i, int i2) {
            if ((i & 2) == 0) {
                this.lastNestedScrollingChildRef = null;
                this.lastStartedType = i2;
                return false;
            }
            throw null;
        }

        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int[] iArr, int i3) {
            if (i2 == 0) {
                throw null;
            } else if (i2 < 0) {
                throw null;
            } else {
                throw null;
            }
        }

        public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
            if (i4 < 0) {
                throw null;
            } else if (i4 == 0) {
                addAccessibilityDelegateIfNeeded(coordinatorLayout, appBarLayout);
            }
        }

        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i) {
            if (this.lastStartedType == 0 || i == 1) {
                snapToChildIfNeeded(coordinatorLayout, appBarLayout);
                throw null;
            } else {
                this.lastNestedScrollingChildRef = new WeakReference(view);
            }
        }

        private void snapToChildIfNeeded(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            throw null;
        }

        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, int i3, int i4) {
            throw null;
        }

        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i) {
            super.onLayoutChild(coordinatorLayout, appBarLayout, i);
            throw null;
        }

        private void addAccessibilityDelegateIfNeeded(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            if (!ViewCompat.hasAccessibilityDelegate(coordinatorLayout)) {
                ViewCompat.setAccessibilityDelegate(coordinatorLayout, new AccessibilityDelegateCompat(appBarLayout, coordinatorLayout) {
                    final /* synthetic */ CoordinatorLayout val$coordinatorLayout;

                    {
                        this.val$coordinatorLayout = r3;
                    }

                    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                        accessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
                        throw null;
                    }

                    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                        if (i == 4096) {
                            throw null;
                        } else if (i != 8192) {
                            return super.performAccessibilityAction(view, i, bundle);
                        } else {
                            if (BaseBehavior.this.getTopBottomOffsetForScrollingSibling() == 0) {
                                return false;
                            }
                            if (BaseBehavior.this.getChildWithScrollingBehavior(this.val$coordinatorLayout).canScrollVertically(-1)) {
                                throw null;
                            }
                            throw null;
                        }
                    }
                });
            }
        }

        /* access modifiers changed from: private */
        public View getChildWithScrollingBehavior(CoordinatorLayout coordinatorLayout) {
            int childCount = coordinatorLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = coordinatorLayout.getChildAt(i);
                if (((CoordinatorLayout.LayoutParams) childAt.getLayoutParams()).getBehavior() instanceof ScrollingViewBehavior) {
                    return childAt;
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public boolean canDragView(AppBarLayout appBarLayout) {
            WeakReference weakReference = this.lastNestedScrollingChildRef;
            if (weakReference == null) {
                return true;
            }
            View view = (View) weakReference.get();
            if (view == null || !view.isShown() || view.canScrollVertically(-1)) {
                return false;
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public void onFlingFinished(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            snapToChildIfNeeded(coordinatorLayout, appBarLayout);
            throw null;
        }

        /* access modifiers changed from: package-private */
        public int getMaxDragOffset(AppBarLayout appBarLayout) {
            throw null;
        }

        /* access modifiers changed from: package-private */
        public int getScrollRangeForDragFling(AppBarLayout appBarLayout) {
            throw null;
        }

        /* access modifiers changed from: package-private */
        public int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, int i3) {
            int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling();
            if (i2 == 0 || topBottomOffsetForScrollingSibling < i2 || topBottomOffsetForScrollingSibling > i3) {
                this.offsetDelta = 0;
            } else if (topBottomOffsetForScrollingSibling != MathUtils.clamp(i, i2, i3)) {
                throw null;
            }
            addAccessibilityDelegateIfNeeded(coordinatorLayout, appBarLayout);
            return 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isOffsetAnimatorRunning() {
            ValueAnimator valueAnimator = this.offsetAnimator;
            return valueAnimator != null && valueAnimator.isRunning();
        }

        /* access modifiers changed from: package-private */
        public int getTopBottomOffsetForScrollingSibling() {
            return getTopAndBottomOffset() + this.offsetDelta;
        }

        public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            Parcelable onSaveInstanceState = super.onSaveInstanceState(coordinatorLayout, appBarLayout);
            SavedState saveScrollState = saveScrollState(onSaveInstanceState, appBarLayout);
            return saveScrollState == null ? onSaveInstanceState : saveScrollState;
        }

        public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, Parcelable parcelable) {
            if (parcelable instanceof SavedState) {
                restoreScrollState((SavedState) parcelable, true);
                super.onRestoreInstanceState(coordinatorLayout, appBarLayout, this.savedState.getSuperState());
                return;
            }
            super.onRestoreInstanceState(coordinatorLayout, appBarLayout, parcelable);
            this.savedState = null;
        }

        /* access modifiers changed from: package-private */
        public SavedState saveScrollState(Parcelable parcelable, AppBarLayout appBarLayout) {
            getTopAndBottomOffset();
            throw null;
        }

        /* access modifiers changed from: package-private */
        public void restoreScrollState(SavedState savedState2, boolean z) {
            if (this.savedState == null || z) {
                this.savedState = savedState2;
            }
        }

        public class SavedState extends AbsSavedState {
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
            boolean firstVisibleChildAtMinimumHeight;
            int firstVisibleChildIndex;
            float firstVisibleChildPercentageShown;
            boolean fullyExpanded;
            boolean fullyScrolled;

            public SavedState(Parcel parcel, ClassLoader classLoader) {
                super(parcel, classLoader);
                boolean z = false;
                this.fullyScrolled = parcel.readByte() != 0;
                this.fullyExpanded = parcel.readByte() != 0;
                this.firstVisibleChildIndex = parcel.readInt();
                this.firstVisibleChildPercentageShown = parcel.readFloat();
                this.firstVisibleChildAtMinimumHeight = parcel.readByte() != 0 ? true : z;
            }

            public void writeToParcel(Parcel parcel, int i) {
                super.writeToParcel(parcel, i);
                parcel.writeByte(this.fullyScrolled ? (byte) 1 : 0);
                parcel.writeByte(this.fullyExpanded ? (byte) 1 : 0);
                parcel.writeInt(this.firstVisibleChildIndex);
                parcel.writeFloat(this.firstVisibleChildPercentageShown);
                parcel.writeByte(this.firstVisibleChildAtMinimumHeight ? (byte) 1 : 0);
            }
        }
    }

    public class ScrollingViewBehavior extends HeaderScrollingViewBehavior {
        private void updateLiftedStateIfNeeded(View view, View view2) {
        }

        /* access modifiers changed from: package-private */
        public float getOverlapRatioForOffset(View view) {
            return 0.0f;
        }

        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return false;
        }

        public void onDependentViewRemoved(CoordinatorLayout coordinatorLayout, View view, View view2) {
        }

        public /* bridge */ /* synthetic */ boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            return super.onLayoutChild(coordinatorLayout, view, i);
        }

        public /* bridge */ /* synthetic */ boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int i4) {
            return super.onMeasureChild(coordinatorLayout, view, i, i2, i3, i4);
        }

        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ScrollingViewBehavior_Layout);
            setOverlayTop(obtainStyledAttributes.getDimensionPixelSize(R$styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
            obtainStyledAttributes.recycle();
        }

        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            offsetChildAsNeeded(view, view2);
            updateLiftedStateIfNeeded(view, view2);
            return false;
        }

        public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean z) {
            findFirstDependency(coordinatorLayout.getDependencies(view));
            return false;
        }

        private void offsetChildAsNeeded(View view, View view2) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) view2.getLayoutParams()).getBehavior();
            if (behavior instanceof BaseBehavior) {
                ViewCompat.offsetTopAndBottom(view, (((view2.getBottom() - view.getTop()) + ((BaseBehavior) behavior).offsetDelta) + getVerticalLayoutGap()) - getOverlapPixelsForOffset(view2));
            }
        }

        /* access modifiers changed from: package-private */
        public AppBarLayout findFirstDependency(List list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                View view = (View) list.get(i);
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public int getScrollRange(View view) {
            return super.getScrollRange(view);
        }
    }
}
