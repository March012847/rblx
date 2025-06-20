package com.android.virtualization.terminal;

import android.view.View;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/* compiled from: SettingsRecoveryActivity.kt */
final class SettingsRecoveryActivity$onCreate$2 implements View.OnClickListener {
    final /* synthetic */ SettingsRecoveryActivity this$0;

    SettingsRecoveryActivity$onCreate$2(SettingsRecoveryActivity settingsRecoveryActivity) {
        this.this$0 = settingsRecoveryActivity;
    }

    public final void onClick(View view) {
        AlertDialog create = new MaterialAlertDialogBuilder(this.this$0).setTitle(2131689712).setMessage(2131689711).setPositiveButton(2131689715, new SettingsRecoveryActivity$onCreate$2$dialog$1(this.this$0)).setNegativeButton(2131689714, SettingsRecoveryActivity$onCreate$2$dialog$2.INSTANCE).create();
        create.getClass();
        create.show();
    }
}
