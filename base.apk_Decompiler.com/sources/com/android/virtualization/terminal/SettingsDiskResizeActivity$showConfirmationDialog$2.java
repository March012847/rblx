package com.android.virtualization.terminal;

import android.content.DialogInterface;

/* compiled from: SettingsDiskResizeActivity.kt */
final class SettingsDiskResizeActivity$showConfirmationDialog$2 implements DialogInterface.OnClickListener {
    final /* synthetic */ SettingsDiskResizeActivity this$0;

    SettingsDiskResizeActivity$showConfirmationDialog$2(SettingsDiskResizeActivity settingsDiskResizeActivity) {
        this.this$0 = settingsDiskResizeActivity;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.this$0.cancel();
    }
}
