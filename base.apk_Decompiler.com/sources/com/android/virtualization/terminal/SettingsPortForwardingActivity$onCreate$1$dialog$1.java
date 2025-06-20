package com.android.virtualization.terminal;

import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SettingsPortForwardingActivity.kt */
final class SettingsPortForwardingActivity$onCreate$1$dialog$1 implements DialogInterface.OnClickListener {
    final /* synthetic */ SettingsPortForwardingActivity this$0;

    SettingsPortForwardingActivity$onCreate$1$dialog$1(SettingsPortForwardingActivity settingsPortForwardingActivity) {
        this.this$0 = settingsPortForwardingActivity;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.getClass();
        View findViewById = ((AlertDialog) dialogInterface).findViewById(2131231188);
        findViewById.getClass();
        int parseInt = Integer.parseInt(((EditText) findViewById).getText().toString());
        PortsStateManager access$getPortsStateManager$p = this.this$0.portsStateManager;
        if (access$getPortsStateManager$p == null) {
            Intrinsics.throwUninitializedPropertyAccessException("portsStateManager");
            access$getPortsStateManager$p = null;
        }
        access$getPortsStateManager$p.updateEnabledPort(parseInt, true);
    }
}
