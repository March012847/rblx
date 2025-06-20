package com.android.virtualization.terminal;

import android.util.Log;
import java.util.function.Function;

/* compiled from: VmLauncherService.kt */
final class VmLauncherService$doShutdown$2$2 implements Function {
    final /* synthetic */ Runner $r;

    VmLauncherService$doShutdown$2$2(Runner runner) {
        this.$r = runner;
    }

    public final Void apply(Throwable th) {
        Log.e("VmTerminalApp", "Stop the service directly because the VM instance isn't stopped with graceful shutdown");
        this.$r.getVm().stop();
        return null;
    }
}
