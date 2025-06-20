package com.android.virtualization.terminal;

import android.widget.Toast;

/* compiled from: VmLauncherService.kt */
final class VmLauncherService$overrideConfigIfNecessary$2 implements Runnable {
    final /* synthetic */ VmLauncherService this$0;

    VmLauncherService$overrideConfigIfNecessary$2(VmLauncherService vmLauncherService) {
        this.this$0 = vmLauncherService;
    }

    public final void run() {
        Toast.makeText(this.this$0, "gfxstream", 0).show();
    }
}
