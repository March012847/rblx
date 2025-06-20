package com.android.virtualization.terminal;

import android.widget.TextView;

/* compiled from: InstallerActivity.kt */
final class InstallerActivity$updateSizeEstimation$1 implements Runnable {
    final /* synthetic */ String $desc;
    final /* synthetic */ InstallerActivity this$0;

    InstallerActivity$updateSizeEstimation$1(InstallerActivity installerActivity, String str) {
        this.this$0 = installerActivity;
        this.$desc = str;
    }

    public final void run() {
        ((TextView) this.this$0.findViewById(2131230980)).setText(this.$desc);
    }
}
