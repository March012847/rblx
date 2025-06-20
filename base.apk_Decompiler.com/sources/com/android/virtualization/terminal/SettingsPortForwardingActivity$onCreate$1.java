package com.android.virtualization.terminal;

import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SettingsPortForwardingActivity.kt */
final class SettingsPortForwardingActivity$onCreate$1 implements View.OnClickListener {
    final /* synthetic */ SettingsPortForwardingActivity this$0;

    SettingsPortForwardingActivity$onCreate$1(SettingsPortForwardingActivity settingsPortForwardingActivity) {
        this.this$0 = settingsPortForwardingActivity;
    }

    public final void onClick(View view) {
        AlertDialog create = new MaterialAlertDialogBuilder(this.this$0).setTitle(2131689698).setView(2131427449).setPositiveButton(2131689696, new SettingsPortForwardingActivity$onCreate$1$dialog$1(this.this$0)).setNegativeButton(2131689692, (DialogInterface.OnClickListener) null).create();
        create.getClass();
        create.show();
        final Button button = create.getButton(-1);
        button.setEnabled(false);
        View findViewById = create.findViewById(2131231188);
        findViewById.getClass();
        final EditText editText = (EditText) findViewById;
        final SettingsPortForwardingActivity settingsPortForwardingActivity = this.this$0;
        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    int parseInt = Integer.parseInt(String.valueOf(charSequence));
                    if (parseInt > 65535 || parseInt < 1024) {
                        editText.setError(settingsPortForwardingActivity.getString(2131689695));
                        button.setEnabled(false);
                        return;
                    }
                    PortsStateManager access$getPortsStateManager$p = settingsPortForwardingActivity.portsStateManager;
                    PortsStateManager portsStateManager = null;
                    if (access$getPortsStateManager$p == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("portsStateManager");
                        access$getPortsStateManager$p = null;
                    }
                    if (!access$getPortsStateManager$p.getActivePorts().contains(Integer.valueOf(parseInt))) {
                        PortsStateManager access$getPortsStateManager$p2 = settingsPortForwardingActivity.portsStateManager;
                        if (access$getPortsStateManager$p2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("portsStateManager");
                        } else {
                            portsStateManager = access$getPortsStateManager$p2;
                        }
                        if (!portsStateManager.getEnabledPorts().contains(Integer.valueOf(parseInt))) {
                            button.setEnabled(true);
                            return;
                        }
                    }
                    editText.setError(settingsPortForwardingActivity.getString(2131689693));
                    button.setEnabled(false);
                } catch (NumberFormatException unused) {
                    editText.setError(settingsPortForwardingActivity.getString(2131689694));
                    button.setEnabled(false);
                }
            }
        });
    }
}
