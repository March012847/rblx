package com.android.virtualization.terminal;

import android.view.View;

/* compiled from: SettingsPortForwardingInactiveAdapter.kt */
final class SettingsPortForwardingInactiveAdapter$onBindViewHolder$1 implements View.OnClickListener {
    final /* synthetic */ int $port;
    final /* synthetic */ SettingsPortForwardingInactiveAdapter this$0;

    SettingsPortForwardingInactiveAdapter$onBindViewHolder$1(SettingsPortForwardingInactiveAdapter settingsPortForwardingInactiveAdapter, int i) {
        this.this$0 = settingsPortForwardingInactiveAdapter;
        this.$port = i;
    }

    public final void onClick(View view) {
        this.this$0.portsStateManager.updateEnabledPort(this.$port, false);
    }
}
