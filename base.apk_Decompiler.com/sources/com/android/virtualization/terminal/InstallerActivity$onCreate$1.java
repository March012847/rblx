package com.android.virtualization.terminal;

import android.view.View;

/* compiled from: InstallerActivity.kt */
final class InstallerActivity$onCreate$1 implements View.OnClickListener {
    final /* synthetic */ InstallerActivity this$0;

    InstallerActivity$onCreate$1(InstallerActivity installerActivity) {
        this.this$0 = installerActivity;
    }

    public final void onClick(View view) {
        this.this$0.requestInstall();
    }
}
