package com.android.virtualization.terminal;

import android.os.ResultReceiver;

/* compiled from: VmLauncherService.kt */
final class VmLauncherService$onStartCommand$2 implements Runnable {
    final /* synthetic */ ResultReceiver $resultReceiver;
    final /* synthetic */ VmLauncherService this$0;

    VmLauncherService$onStartCommand$2(VmLauncherService vmLauncherService, ResultReceiver resultReceiver) {
        this.this$0 = vmLauncherService;
        this.$resultReceiver = resultReceiver;
    }

    public final void run() {
        this.this$0.doShutdown(this.$resultReceiver);
    }
}
