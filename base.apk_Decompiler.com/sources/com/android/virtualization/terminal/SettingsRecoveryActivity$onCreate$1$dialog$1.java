package com.android.virtualization.terminal;

import android.content.DialogInterface;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: SettingsRecoveryActivity.kt */
final class SettingsRecoveryActivity$onCreate$1$dialog$1 implements DialogInterface.OnMultiChoiceClickListener {
    final /* synthetic */ Ref$BooleanRef $backupRootfs;

    SettingsRecoveryActivity$onCreate$1$dialog$1(Ref$BooleanRef ref$BooleanRef) {
        this.$backupRootfs = ref$BooleanRef;
    }

    public final void onClick(DialogInterface dialogInterface, int i, boolean z) {
        this.$backupRootfs.element = z;
    }
}
