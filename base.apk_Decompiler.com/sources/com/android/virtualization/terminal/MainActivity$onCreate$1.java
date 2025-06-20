package com.android.virtualization.terminal;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;

/* compiled from: MainActivity.kt */
final class MainActivity$onCreate$1 implements ActivityResultCallback {
    final /* synthetic */ MainActivity this$0;

    MainActivity$onCreate$1(MainActivity mainActivity) {
        this.this$0 = mainActivity;
    }

    public final void onActivityResult(ActivityResult activityResult) {
        activityResult.getClass();
        this.this$0.startVm();
    }
}
