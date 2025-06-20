package com.android.virtualization.terminal;

import com.android.virtualization.terminal.PortsStateManager;
import java.util.Set;
import kotlin.collections.SetsKt;

/* compiled from: PortNotifier.kt */
public final class PortNotifier$portsStateListener$1 implements PortsStateManager.Listener {
    final /* synthetic */ PortNotifier this$0;

    PortNotifier$portsStateListener$1(PortNotifier portNotifier) {
        this.this$0 = portNotifier;
    }

    public void onPortsStateUpdated(Set set, Set set2) {
        set.getClass();
        set2.getClass();
        Set<Number> minus = SetsKt.minus(SetsKt.minus(set2, set), this.this$0.portsStateManager.getEnabledPorts());
        PortNotifier portNotifier = this.this$0;
        for (Number intValue : minus) {
            portNotifier.showNotificationFor(intValue.intValue());
        }
        Set<Number> minus2 = SetsKt.minus(set, set2);
        PortNotifier portNotifier2 = this.this$0;
        for (Number intValue2 : minus2) {
            portNotifier2.discardNotificationFor(intValue2.intValue());
        }
    }
}
