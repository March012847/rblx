package com.android.virtualization.terminal;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: SettingsPortForwardingActivity.kt */
final class SettingsPortForwardingActivity$refreshAdapters$1 implements Runnable {
    final /* synthetic */ SettingsPortForwardingActivity this$0;

    SettingsPortForwardingActivity$refreshAdapters$1(SettingsPortForwardingActivity settingsPortForwardingActivity) {
        this.this$0 = settingsPortForwardingActivity;
    }

    public final void run() {
        SettingsPortForwardingActiveAdapter access$getActivePortsAdapter$p = this.this$0.activePortsAdapter;
        SettingsPortForwardingInactiveAdapter settingsPortForwardingInactiveAdapter = null;
        if (access$getActivePortsAdapter$p == null) {
            Intrinsics.throwUninitializedPropertyAccessException("activePortsAdapter");
            access$getActivePortsAdapter$p = null;
        }
        access$getActivePortsAdapter$p.refreshItems();
        SettingsPortForwardingInactiveAdapter access$getInactivePortsAdapter$p = this.this$0.inactivePortsAdapter;
        if (access$getInactivePortsAdapter$p == null) {
            Intrinsics.throwUninitializedPropertyAccessException("inactivePortsAdapter");
        } else {
            settingsPortForwardingInactiveAdapter = access$getInactivePortsAdapter$p;
        }
        settingsPortForwardingInactiveAdapter.refreshItems();
    }
}
