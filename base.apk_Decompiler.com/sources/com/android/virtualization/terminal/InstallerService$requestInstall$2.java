package com.android.virtualization.terminal;

import kotlin.Unit;

/* compiled from: InstallerService.kt */
final class InstallerService$requestInstall$2 implements Runnable {
    final /* synthetic */ boolean $isWifiOnly;
    final /* synthetic */ InstallerService this$0;

    InstallerService$requestInstall$2(InstallerService installerService, boolean z) {
        this.this$0 = installerService;
        this.$isWifiOnly = z;
    }

    public final void run() {
        boolean z = this.this$0.downloadFromSdcard() || this.this$0.downloadFromUrl(this.$isWifiOnly);
        this.this$0.stopForeground(1);
        Object access$getLock$p = this.this$0.lock;
        InstallerService installerService = this.this$0;
        synchronized (access$getLock$p) {
            installerService.isInstalling = false;
            Unit unit = Unit.INSTANCE;
        }
        if (z) {
            this.this$0.notifyCompleted();
        }
    }
}
