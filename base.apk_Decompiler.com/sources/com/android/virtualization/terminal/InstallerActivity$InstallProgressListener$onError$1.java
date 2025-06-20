package com.android.virtualization.terminal;

import com.android.virtualization.terminal.InstallerActivity;

/* compiled from: InstallerActivity.kt */
final class InstallerActivity$InstallProgressListener$onError$1 implements Runnable {
    final /* synthetic */ String $displayText;
    final /* synthetic */ InstallerActivity.InstallProgressListener this$0;

    InstallerActivity$InstallProgressListener$onError$1(InstallerActivity.InstallProgressListener installProgressListener, String str) {
        this.this$0 = installProgressListener;
        this.$displayText = str;
    }

    public final void run() {
        InstallerActivity installerActivity = (InstallerActivity) this.this$0.activity.get();
        if (installerActivity != null) {
            installerActivity.handleInstallError(this.$displayText);
        }
    }
}
