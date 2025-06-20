package com.android.virtualization.terminal;

import android.content.Intent;

/* compiled from: SettingsRecoveryActivity.kt */
final class SettingsRecoveryActivity$runInBackgroundAndRestartApp$1 implements Runnable {
    final /* synthetic */ Runnable $backgroundWork;
    final /* synthetic */ SettingsRecoveryActivity this$0;

    SettingsRecoveryActivity$runInBackgroundAndRestartApp$1(Runnable runnable, SettingsRecoveryActivity settingsRecoveryActivity) {
        this.$backgroundWork = runnable;
        this.this$0 = settingsRecoveryActivity;
    }

    public final void run() {
        this.$backgroundWork.run();
        final SettingsRecoveryActivity settingsRecoveryActivity = this.this$0;
        settingsRecoveryActivity.runOnUiThread(new Runnable() {
            public final void run() {
                SettingsRecoveryActivity.this.findViewById(2131231172).setVisibility(0);
                SettingsRecoveryActivity.this.findViewById(2131231135).setVisibility(4);
                Intent launchIntentForPackage = SettingsRecoveryActivity.this.getBaseContext().getPackageManager().getLaunchIntentForPackage(SettingsRecoveryActivity.this.getBaseContext().getPackageName());
                if (launchIntentForPackage != null) {
                    launchIntentForPackage.addFlags(32768);
                }
                SettingsRecoveryActivity.this.finish();
                SettingsRecoveryActivity.this.startActivity(launchIntentForPackage);
            }
        });
    }
}
