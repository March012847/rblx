package com.google.android.material.textfield;

import android.view.View;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ClearTextEndIconDelegate$$ExternalSyntheticLambda1 implements View.OnFocusChangeListener {
    public final /* synthetic */ ClearTextEndIconDelegate f$0;

    public /* synthetic */ ClearTextEndIconDelegate$$ExternalSyntheticLambda1(ClearTextEndIconDelegate clearTextEndIconDelegate) {
        this.f$0 = clearTextEndIconDelegate;
    }

    public final void onFocusChange(View view, boolean z) {
        this.f$0.animateIcon(this.f$0.shouldBeVisible());
    }
}
