package com.google.android.material.textfield;

import android.text.StaticLayout;
import com.google.android.material.internal.StaticLayoutBuilderConfigurer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TextInputLayout$$ExternalSyntheticLambda2 implements StaticLayoutBuilderConfigurer {
    public final /* synthetic */ TextInputLayout f$0;

    public /* synthetic */ TextInputLayout$$ExternalSyntheticLambda2(TextInputLayout textInputLayout) {
        this.f$0 = textInputLayout;
    }

    public final void configure(StaticLayout.Builder builder) {
        builder.setBreakStrategy(this.f$0.placeholderTextView.getBreakStrategy());
    }
}
