package com.android.virtualization.terminal;

import android.util.Log;
import java.io.IOException;

/* compiled from: InstallerActivity.kt */
final class InstallerActivity$measureImageSizeAndUpdateDescription$1 implements Runnable {
    final /* synthetic */ InstallerActivity this$0;

    InstallerActivity$measureImageSizeAndUpdateDescription$1(InstallerActivity installerActivity) {
        this.this$0 = installerActivity;
    }

    public final void run() {
        try {
            this.this$0.updateSizeEstimation(ImageArchive.Companion.getDefault().getSize());
        } catch (IOException e) {
            Log.w("VmTerminalApp", "Failed to measure image size.", e);
        }
    }
}
