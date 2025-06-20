package com.android.virtualization.terminal;

import android.view.View;

/* compiled from: ErrorActivity.kt */
final class ErrorActivity$onCreate$1 implements View.OnClickListener {
    final /* synthetic */ ErrorActivity this$0;

    ErrorActivity$onCreate$1(ErrorActivity errorActivity) {
        this.this$0 = errorActivity;
    }

    public final void onClick(View view) {
        this.this$0.launchRecoveryActivity();
    }
}
