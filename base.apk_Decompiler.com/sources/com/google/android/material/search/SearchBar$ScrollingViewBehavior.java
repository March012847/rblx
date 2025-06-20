package com.google.android.material.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;

public class SearchBar$ScrollingViewBehavior extends AppBarLayout.ScrollingViewBehavior {
    private boolean initialized = false;

    /* access modifiers changed from: protected */
    public boolean shouldHeaderOverlapScrollingChild() {
        return true;
    }

    public SearchBar$ScrollingViewBehavior() {
    }

    public SearchBar$ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
        return super.onDependentViewChanged(coordinatorLayout, view, view2);
    }
}
