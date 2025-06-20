package com.android.virtualization.terminal;

import com.android.virtualization.terminal.PortsStateManager;
import java.util.Set;

/* compiled from: DebianServiceImpl.kt */
public final class DebianServiceImpl$openForwardingRequestQueue$1 implements PortsStateManager.Listener {
    final /* synthetic */ DebianServiceImpl this$0;

    DebianServiceImpl$openForwardingRequestQueue$1(DebianServiceImpl debianServiceImpl) {
        this.this$0 = debianServiceImpl;
    }

    public void onPortsStateUpdated(Set set, Set set2) {
        set.getClass();
        set2.getClass();
        this.this$0.updateListeningPorts();
    }
}
