package com.android.virtualization.terminal;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: VmLauncherService.kt */
public final class VmLauncherService$getTerminalServiceInfo$1 implements NsdManager.ServiceInfoCallback {
    final /* synthetic */ ExecutorService $executor;
    final /* synthetic */ NsdManager $nsdManager;
    final /* synthetic */ Ref$ObjectRef $resolvedInfo;
    private boolean found;

    public void onServiceInfoCallbackRegistrationFailed(int i) {
    }

    public void onServiceLost() {
    }

    VmLauncherService$getTerminalServiceInfo$1(ExecutorService executorService, NsdManager nsdManager, Ref$ObjectRef ref$ObjectRef) {
        this.$executor = executorService;
        this.$nsdManager = nsdManager;
        this.$resolvedInfo = ref$ObjectRef;
    }

    public void onServiceInfoCallbackUnregistered() {
        this.$executor.shutdown();
    }

    public void onServiceUpdated(NsdServiceInfo nsdServiceInfo) {
        nsdServiceInfo.getClass();
        Log.i("VmTerminalApp", "Service found: " + nsdServiceInfo);
        if (!this.found) {
            this.found = true;
            this.$nsdManager.unregisterServiceInfoCallback(this);
            ((CompletableFuture) this.$resolvedInfo.element).complete(nsdServiceInfo);
        }
    }
}
