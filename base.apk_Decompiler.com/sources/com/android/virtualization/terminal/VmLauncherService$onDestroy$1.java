package com.android.virtualization.terminal;

import android.os.ResultReceiver;
import android.system.virtualmachine.VirtualMachine;

/* compiled from: VmLauncherService.kt */
final class VmLauncherService$onDestroy$1 implements Runnable {
    final /* synthetic */ VmLauncherService this$0;

    VmLauncherService$onDestroy$1(VmLauncherService vmLauncherService) {
        this.this$0 = vmLauncherService;
    }

    public final void run() {
        VirtualMachine vm;
        Runner access$getRunner$p = this.this$0.runner;
        if (access$getRunner$p != null && (vm = access$getRunner$p.getVm()) != null && vm.getStatus() == 1) {
            this.this$0.doShutdown((ResultReceiver) null);
        }
    }
}
