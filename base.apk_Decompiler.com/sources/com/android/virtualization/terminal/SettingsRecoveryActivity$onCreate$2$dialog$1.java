package com.android.virtualization.terminal;

import android.content.DialogInterface;

/* compiled from: SettingsRecoveryActivity.kt */
final class SettingsRecoveryActivity$onCreate$2$dialog$1 implements DialogInterface.OnClickListener {
    final /* synthetic */ SettingsRecoveryActivity this$0;

    SettingsRecoveryActivity$onCreate$2$dialog$1(SettingsRecoveryActivity settingsRecoveryActivity) {
        this.this$0 = settingsRecoveryActivity;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        final SettingsRecoveryActivity settingsRecoveryActivity = this.this$0;
        settingsRecoveryActivity.runInBackgroundAndRestartApp(new Runnable() {
            public final void run() {
                SettingsRecoveryActivity.this.removeBackup();
            }
        });
    }
}
