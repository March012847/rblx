package com.android.virtualization.terminal;

import android.content.Intent;
import com.android.virtualization.terminal.VmLauncherService;

/* compiled from: SettingsDiskResizeActivity.kt */
public final class SettingsDiskResizeActivity$resize$intent$1 implements VmLauncherService.VmLauncherServiceCallback {
    final /* synthetic */ SettingsDiskResizeActivity this$0;

    public void onTerminalAvailable(TerminalInfo terminalInfo) {
        terminalInfo.getClass();
    }

    public void onVmError() {
    }

    public void onVmStart() {
    }

    SettingsDiskResizeActivity$resize$intent$1(SettingsDiskResizeActivity settingsDiskResizeActivity) {
        this.this$0 = settingsDiskResizeActivity;
    }

    public void onVmStop() {
        this.this$0.finish();
        Intent launchIntentForPackage = this.this$0.getBaseContext().getPackageManager().getLaunchIntentForPackage(this.this$0.getBaseContext().getPackageName());
        launchIntentForPackage.getClass();
        launchIntentForPackage.addFlags(32768);
        SettingsDiskResizeActivity settingsDiskResizeActivity = this.this$0;
        launchIntentForPackage.putExtra("com.android.virtualization.terminal.EXTRA_DISK_SIZE", settingsDiskResizeActivity.mbToBytes(settingsDiskResizeActivity.diskSizeMb));
        this.this$0.startActivity(launchIntentForPackage);
    }
}
