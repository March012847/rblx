package com.android.virtualization.terminal;

/* compiled from: SettingsPortForwardingItem.kt */
public final class SettingsPortForwardingItem {
    private final boolean enabled;
    private final int port;

    public SettingsPortForwardingItem(int i, boolean z) {
        this.port = i;
        this.enabled = z;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final int getPort() {
        return this.port;
    }
}
