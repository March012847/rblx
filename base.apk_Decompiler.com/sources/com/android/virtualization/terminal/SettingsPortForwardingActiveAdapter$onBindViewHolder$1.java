package com.android.virtualization.terminal;

import android.widget.CompoundButton;

/* compiled from: SettingsPortForwardingActiveAdapter.kt */
final class SettingsPortForwardingActiveAdapter$onBindViewHolder$1 implements CompoundButton.OnCheckedChangeListener {
    final /* synthetic */ int $port;
    final /* synthetic */ SettingsPortForwardingActiveAdapter this$0;

    SettingsPortForwardingActiveAdapter$onBindViewHolder$1(SettingsPortForwardingActiveAdapter settingsPortForwardingActiveAdapter, int i) {
        this.this$0 = settingsPortForwardingActiveAdapter;
        this.$port = i;
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        compoundButton.getClass();
        this.this$0.portsStateManager.updateEnabledPort(this.$port, z);
    }
}
