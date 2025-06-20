package com.android.virtualization.terminal;

import android.os.Environment;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MainActivity.kt */
final class MainActivity$installerLauncher$1 implements ActivityResultCallback {
    final /* synthetic */ MainActivity this$0;

    MainActivity$installerLauncher$1(MainActivity mainActivity) {
        this.this$0 = mainActivity;
    }

    public final void onActivityResult(ActivityResult activityResult) {
        activityResult.getClass();
        if (activityResult.getResultCode() != -1) {
            Log.e("VmTerminalApp", "Failed to start VM. Installer returned error.");
            this.this$0.finish();
        }
        if (!Environment.isExternalStorageManager()) {
            MainActivity mainActivity = this.this$0;
            ActivityResultLauncher access$getManageExternalStorageActivityResultLauncher$p = mainActivity.manageExternalStorageActivityResultLauncher;
            if (access$getManageExternalStorageActivityResultLauncher$p == null) {
                Intrinsics.throwUninitializedPropertyAccessException("manageExternalStorageActivityResultLauncher");
                access$getManageExternalStorageActivityResultLauncher$p = null;
            }
            mainActivity.requestStoragePermissions(mainActivity, access$getManageExternalStorageActivityResultLauncher$p);
            return;
        }
        this.this$0.startVm();
    }
}
