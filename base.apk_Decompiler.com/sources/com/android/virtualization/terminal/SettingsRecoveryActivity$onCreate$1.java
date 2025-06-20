package com.android.virtualization.terminal;

import android.view.View;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: SettingsRecoveryActivity.kt */
final class SettingsRecoveryActivity$onCreate$1 implements View.OnClickListener {
    final /* synthetic */ SettingsRecoveryActivity this$0;

    SettingsRecoveryActivity$onCreate$1(SettingsRecoveryActivity settingsRecoveryActivity) {
        this.this$0 = settingsRecoveryActivity;
    }

    public final void onClick(View view) {
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        AlertDialog create = new MaterialAlertDialogBuilder(this.this$0).setTitle(2131689717).setMultiChoiceItems((CharSequence[]) new String[]{this.this$0.getString(2131689713)}, new boolean[]{ref$BooleanRef.element}, new SettingsRecoveryActivity$onCreate$1$dialog$1(ref$BooleanRef)).setPositiveButton(2131689715, new SettingsRecoveryActivity$onCreate$1$dialog$2(this.this$0, ref$BooleanRef)).setNegativeButton(2131689714, SettingsRecoveryActivity$onCreate$1$dialog$3.INSTANCE).create();
        create.getClass();
        create.show();
    }
}
