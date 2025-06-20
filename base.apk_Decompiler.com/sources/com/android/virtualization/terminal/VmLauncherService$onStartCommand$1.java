package com.android.virtualization.terminal;

import android.os.ResultReceiver;

/* compiled from: VmLauncherService.kt */
final class VmLauncherService$onStartCommand$1 implements Runnable {
    final /* synthetic */ long $diskSize;
    final /* synthetic */ DisplayInfo $displayInfo;
    final /* synthetic */ ResultReceiver $resultReceiver;
    final /* synthetic */ VmLauncherService this$0;

    VmLauncherService$onStartCommand$1(VmLauncherService vmLauncherService, DisplayInfo displayInfo, long j, ResultReceiver resultReceiver) {
        this.this$0 = vmLauncherService;
        this.$displayInfo = displayInfo;
        this.$diskSize = j;
        this.$resultReceiver = resultReceiver;
    }

    public final void run() {
        VmLauncherService vmLauncherService = this.this$0;
        DisplayInfo displayInfo = this.$displayInfo;
        long j = this.$diskSize;
        ResultReceiver resultReceiver = this.$resultReceiver;
        resultReceiver.getClass();
        vmLauncherService.doStart(displayInfo, j, resultReceiver);
    }
}
