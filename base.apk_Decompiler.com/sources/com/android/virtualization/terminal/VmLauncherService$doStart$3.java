package com.android.virtualization.terminal;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import java.util.function.Function;

/* compiled from: VmLauncherService.kt */
final class VmLauncherService$doStart$3 implements Function {
    final /* synthetic */ ResultReceiver $resultReceiver;
    final /* synthetic */ VmLauncherService this$0;

    VmLauncherService$doStart$3(ResultReceiver resultReceiver, VmLauncherService vmLauncherService) {
        this.$resultReceiver = resultReceiver;
        this.this$0 = vmLauncherService;
    }

    public final Void apply(Throwable th) {
        Log.e("VmTerminalApp", "Failed to start VM", th);
        this.$resultReceiver.send(2, (Bundle) null);
        this.this$0.stopSelf();
        return null;
    }
}
