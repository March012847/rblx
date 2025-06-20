package com.google.android.material.bottomappbar;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.R$attr;
import com.google.android.material.R$style;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import java.lang.ref.WeakReference;

public abstract class BottomAppBar extends Toolbar {
    private static final int DEF_STYLE_RES = R$style.Widget_MaterialComponents_BottomAppBar;
    private static final int FAB_ALIGNMENT_ANIM_DURATION_ATTR = R$attr.motionDurationLong2;
    private static final int FAB_ALIGNMENT_ANIM_EASING_ATTR = R$attr.motionEasingEmphasizedInterpolator;

    static /* synthetic */ void access$1400(BottomAppBar bottomAppBar) {
        throw null;
    }

    static /* synthetic */ View access$3200(BottomAppBar bottomAppBar) {
        throw null;
    }

    /* access modifiers changed from: private */
    public static void updateFabAnchorGravity(BottomAppBar bottomAppBar, View view) {
        ((CoordinatorLayout.LayoutParams) view.getLayoutParams()).anchorGravity = 17;
        throw null;
    }

    public class Behavior extends HideBottomViewOnScrollBehavior<BottomAppBar> {
        private final Rect fabContentRect = new Rect();
        private final View.OnLayoutChangeListener fabLayoutListener = new View.OnLayoutChangeListener() {
            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(Behavior.this.viewRef.get());
                view.removeOnLayoutChangeListener(this);
            }
        };
        private int originalBottomMargin;
        /* access modifiers changed from: private */
        public WeakReference viewRef;

        public /* bridge */ /* synthetic */ boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
            return onLayoutChild(coordinatorLayout, (BottomAppBar) null, i);
        }

        public /* bridge */ /* synthetic */ boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i, int i2) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
            return onStartNestedScroll(coordinatorLayout, (BottomAppBar) null, view2, view3, i, i2);
        }

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, BottomAppBar bottomAppBar, int i) {
            this.viewRef = new WeakReference(bottomAppBar);
            View access$3200 = BottomAppBar.access$3200(bottomAppBar);
            if (access$3200 != null && !access$3200.isLaidOut()) {
                BottomAppBar.updateFabAnchorGravity(bottomAppBar, access$3200);
                this.originalBottomMargin = ((CoordinatorLayout.LayoutParams) access$3200.getLayoutParams()).bottomMargin;
                access$3200.addOnLayoutChangeListener(this.fabLayoutListener);
                BottomAppBar.access$1400(bottomAppBar);
            }
            coordinatorLayout.onLayoutChild(bottomAppBar, i);
            return super.onLayoutChild(coordinatorLayout, bottomAppBar, i);
        }

        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, BottomAppBar bottomAppBar, View view, View view2, int i, int i2) {
            throw null;
        }
    }
}
