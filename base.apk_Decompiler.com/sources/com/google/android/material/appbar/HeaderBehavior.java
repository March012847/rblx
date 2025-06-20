package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

abstract class HeaderBehavior extends ViewOffsetBehavior {
    private int activePointerId = -1;
    private Runnable flingRunnable;
    private boolean isBeingDragged;
    private int lastMotionY;
    OverScroller scroller;
    private int touchSlop = -1;
    private VelocityTracker velocityTracker;

    /* access modifiers changed from: package-private */
    public abstract boolean canDragView(View view);

    /* access modifiers changed from: package-private */
    public abstract int getMaxDragOffset(View view);

    /* access modifiers changed from: package-private */
    public abstract int getScrollRangeForDragFling(View view);

    /* access modifiers changed from: package-private */
    public abstract int getTopBottomOffsetForScrollingSibling();

    /* access modifiers changed from: package-private */
    public abstract void onFlingFinished(CoordinatorLayout coordinatorLayout, View view);

    /* access modifiers changed from: package-private */
    public abstract int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3);

    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        int findPointerIndex;
        if (this.touchSlop < 0) {
            this.touchSlop = ViewConfiguration.get(coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        if (motionEvent.getActionMasked() == 2 && this.isBeingDragged) {
            int i = this.activePointerId;
            if (i == -1 || (findPointerIndex = motionEvent.findPointerIndex(i)) == -1) {
                return false;
            }
            int y = (int) motionEvent.getY(findPointerIndex);
            if (Math.abs(y - this.lastMotionY) > this.touchSlop) {
                this.lastMotionY = y;
                return true;
            }
        }
        if (motionEvent.getActionMasked() == 0) {
            this.activePointerId = -1;
            int x = (int) motionEvent.getX();
            int y2 = (int) motionEvent.getY();
            boolean z = canDragView(view) && coordinatorLayout.isPointInChildBounds(view, x, y2);
            this.isBeingDragged = z;
            if (z) {
                this.lastMotionY = y2;
                this.activePointerId = motionEvent.getPointerId(0);
                ensureVelocityTracker();
                OverScroller overScroller = this.scroller;
                if (overScroller != null && !overScroller.isFinished()) {
                    this.scroller.abortAnimation();
                    return true;
                }
            }
        }
        VelocityTracker velocityTracker2 = this.velocityTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.addMovement(motionEvent);
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x008b A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout r10, android.view.View r11, android.view.MotionEvent r12) {
        /*
            r9 = this;
            int r1 = r12.getActionMasked()
            r6 = -1
            r7 = 0
            r8 = 1
            if (r1 == r8) goto L_0x004d
            r3 = 2
            if (r1 == r3) goto L_0x002d
            r2 = 3
            if (r1 == r2) goto L_0x0071
            r2 = 6
            if (r1 == r2) goto L_0x0013
            goto L_0x004b
        L_0x0013:
            int r1 = r12.getActionIndex()
            if (r1 != 0) goto L_0x001b
            r1 = r8
            goto L_0x001c
        L_0x001b:
            r1 = r7
        L_0x001c:
            int r2 = r12.getPointerId(r1)
            r9.activePointerId = r2
            float r1 = r12.getY(r1)
            r2 = 1056964608(0x3f000000, float:0.5)
            float r1 = r1 + r2
            int r1 = (int) r1
            r9.lastMotionY = r1
            goto L_0x004b
        L_0x002d:
            int r1 = r9.activePointerId
            int r1 = r12.findPointerIndex(r1)
            if (r1 != r6) goto L_0x0036
            return r7
        L_0x0036:
            float r1 = r12.getY(r1)
            int r1 = (int) r1
            int r3 = r9.lastMotionY
            int r3 = r3 - r1
            r9.lastMotionY = r1
            int r4 = r9.getMaxDragOffset(r11)
            r5 = 0
            r0 = r9
            r1 = r10
            r2 = r11
            r0.scroll(r1, r2, r3, r4, r5)
        L_0x004b:
            r1 = r7
            goto L_0x0080
        L_0x004d:
            android.view.VelocityTracker r1 = r9.velocityTracker
            if (r1 == 0) goto L_0x0071
            r1.addMovement(r12)
            android.view.VelocityTracker r1 = r9.velocityTracker
            r3 = 1000(0x3e8, float:1.401E-42)
            r1.computeCurrentVelocity(r3)
            android.view.VelocityTracker r1 = r9.velocityTracker
            int r3 = r9.activePointerId
            float r5 = r1.getYVelocity(r3)
            int r1 = r9.getScrollRangeForDragFling(r11)
            int r3 = -r1
            r4 = 0
            r0 = r9
            r1 = r10
            r2 = r11
            r0.fling(r1, r2, r3, r4, r5)
            r1 = r8
            goto L_0x0072
        L_0x0071:
            r1 = r7
        L_0x0072:
            r9.isBeingDragged = r7
            r9.activePointerId = r6
            android.view.VelocityTracker r2 = r9.velocityTracker
            if (r2 == 0) goto L_0x0080
            r2.recycle()
            r2 = 0
            r9.velocityTracker = r2
        L_0x0080:
            android.view.VelocityTracker r2 = r9.velocityTracker
            if (r2 == 0) goto L_0x0087
            r2.addMovement(r12)
        L_0x0087:
            boolean r0 = r9.isBeingDragged
            if (r0 != 0) goto L_0x008f
            if (r1 == 0) goto L_0x008e
            goto L_0x008f
        L_0x008e:
            return r7
        L_0x008f:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.HeaderBehavior.onTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
    }

    /* access modifiers changed from: package-private */
    public int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, View view, int i) {
        return setHeaderTopBottomOffset(coordinatorLayout, view, i, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /* access modifiers changed from: package-private */
    public final int scroll(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
        return setHeaderTopBottomOffset(coordinatorLayout, view, getTopBottomOffsetForScrollingSibling() - i, i2, i3);
    }

    /* access modifiers changed from: package-private */
    public final boolean fling(CoordinatorLayout coordinatorLayout, View view, int i, int i2, float f) {
        Runnable runnable = this.flingRunnable;
        if (runnable != null) {
            view.removeCallbacks(runnable);
            this.flingRunnable = null;
        }
        if (this.scroller == null) {
            this.scroller = new OverScroller(view.getContext());
        }
        this.scroller.fling(0, getTopAndBottomOffset(), 0, Math.round(f), 0, 0, i, i2);
        if (this.scroller.computeScrollOffset()) {
            FlingRunnable flingRunnable2 = new FlingRunnable(coordinatorLayout, view);
            this.flingRunnable = flingRunnable2;
            view.postOnAnimation(flingRunnable2);
            return true;
        }
        onFlingFinished(coordinatorLayout, view);
        return false;
    }

    private void ensureVelocityTracker() {
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
    }

    class FlingRunnable implements Runnable {
        private final View layout;
        private final CoordinatorLayout parent;

        FlingRunnable(CoordinatorLayout coordinatorLayout, View view) {
            this.parent = coordinatorLayout;
            this.layout = view;
        }

        public void run() {
            OverScroller overScroller;
            if (this.layout != null && (overScroller = HeaderBehavior.this.scroller) != null) {
                if (overScroller.computeScrollOffset()) {
                    HeaderBehavior headerBehavior = HeaderBehavior.this;
                    headerBehavior.setHeaderTopBottomOffset(this.parent, this.layout, headerBehavior.scroller.getCurrY());
                    this.layout.postOnAnimation(this);
                    return;
                }
                HeaderBehavior.this.onFlingFinished(this.parent, this.layout);
            }
        }
    }
}
