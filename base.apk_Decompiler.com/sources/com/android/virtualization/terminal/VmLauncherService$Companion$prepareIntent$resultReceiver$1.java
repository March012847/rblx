package com.android.virtualization.terminal;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import com.android.virtualization.terminal.VmLauncherService;

/* compiled from: VmLauncherService.kt */
public final class VmLauncherService$Companion$prepareIntent$resultReceiver$1 extends ResultReceiver {
    final /* synthetic */ VmLauncherService.VmLauncherServiceCallback $callback;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    VmLauncherService$Companion$prepareIntent$resultReceiver$1(VmLauncherService.VmLauncherServiceCallback vmLauncherServiceCallback, Handler handler) {
        super(handler);
        this.$callback = vmLauncherServiceCallback;
    }

    /* access modifiers changed from: protected */
    public void onReceiveResult(int i, Bundle bundle) {
        if (i == 0) {
            this.$callback.onVmStart();
        } else if (i == 1) {
            this.$callback.onVmStop();
        } else if (i == 2) {
            this.$callback.onVmError();
        } else if (i != 3) {
            Log.e("VmTerminalApp", "unknown result code: " + i);
        } else {
            bundle.getClass();
            String string = bundle.getString("address");
            int i2 = bundle.getInt("port");
            VmLauncherService.VmLauncherServiceCallback vmLauncherServiceCallback = this.$callback;
            string.getClass();
            vmLauncherServiceCallback.onTerminalAvailable(new TerminalInfo(string, i2));
        }
    }
}
