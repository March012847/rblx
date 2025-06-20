package com.android.virtualization.terminal;

import android.content.DialogInterface;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: SettingsRecoveryActivity.kt */
final class SettingsRecoveryActivity$onCreate$1$dialog$2 implements DialogInterface.OnClickListener {
    final /* synthetic */ Ref$BooleanRef $backupRootfs;
    final /* synthetic */ SettingsRecoveryActivity this$0;

    SettingsRecoveryActivity$onCreate$1$dialog$2(SettingsRecoveryActivity settingsRecoveryActivity, Ref$BooleanRef ref$BooleanRef) {
        this.this$0 = settingsRecoveryActivity;
        this.$backupRootfs = ref$BooleanRef;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        final SettingsRecoveryActivity settingsRecoveryActivity = this.this$0;
        final Ref$BooleanRef ref$BooleanRef = this.$backupRootfs;
        settingsRecoveryActivity.runInBackgroundAndRestartApp(new Runnable() {
            public final void run() {
                SettingsRecoveryActivity.this.uninstall(ref$BooleanRef.element);
            }
        });
    }
}
