package com.google.android.material.sidesheet;

import android.view.View;
import androidx.core.view.accessibility.AccessibilityViewCommand;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SideSheetBehavior$$ExternalSyntheticLambda0 implements AccessibilityViewCommand {
    public final /* synthetic */ SideSheetBehavior f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ SideSheetBehavior$$ExternalSyntheticLambda0(SideSheetBehavior sideSheetBehavior, int i) {
        this.f$0 = sideSheetBehavior;
        this.f$1 = i;
    }

    public final boolean perform(View view, AccessibilityViewCommand.CommandArguments commandArguments) {
        return this.f$0.setState(this.f$1);
    }
}
