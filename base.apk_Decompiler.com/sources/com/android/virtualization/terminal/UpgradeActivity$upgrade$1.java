package com.android.virtualization.terminal;

import android.util.Log;
import com.google.android.material.snackbar.Snackbar;
import java.io.IOException;

/* compiled from: UpgradeActivity.kt */
final class UpgradeActivity$upgrade$1 implements Runnable {
    final /* synthetic */ UpgradeActivity this$0;

    UpgradeActivity$upgrade$1(UpgradeActivity upgradeActivity) {
        this.this$0 = upgradeActivity;
    }

    public final void run() {
        try {
            InstalledImage.Companion.getDefault(this.this$0).uninstallAndBackup();
            final UpgradeActivity upgradeActivity = this.this$0;
            upgradeActivity.runOnUiThread(new Runnable() {
                public final void run() {
                    UpgradeActivity.this.findViewById(2131231129).setVisibility(4);
                    UpgradeActivity.this.restartTerminal();
                }
            });
        } catch (IOException e) {
            Snackbar.make(this.this$0.findViewById(16908290), 2131689730, -1).show();
            Log.e("VmTerminalApp", "Failed to upgrade ", e);
        }
    }
}
