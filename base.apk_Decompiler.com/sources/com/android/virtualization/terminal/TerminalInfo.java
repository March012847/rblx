package com.android.virtualization.terminal;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: VmLauncherService.kt */
public final class TerminalInfo {
    private final String ipAddress;
    private final int port;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TerminalInfo)) {
            return false;
        }
        TerminalInfo terminalInfo = (TerminalInfo) obj;
        return Intrinsics.areEqual(this.ipAddress, terminalInfo.ipAddress) && this.port == terminalInfo.port;
    }

    public int hashCode() {
        return (this.ipAddress.hashCode() * 31) + Integer.hashCode(this.port);
    }

    public String toString() {
        String str = this.ipAddress;
        int i = this.port;
        return "TerminalInfo(ipAddress=" + str + ", port=" + i + ")";
    }

    public TerminalInfo(String str, int i) {
        str.getClass();
        this.ipAddress = str;
        this.port = i;
    }

    public final String getIpAddress() {
        return this.ipAddress;
    }

    public final int getPort() {
        return this.port;
    }
}
