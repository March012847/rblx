package com.android.virtualization.terminal;

import android.util.Log;

/* compiled from: MemBalloonController.kt */
final class MemBalloonController$observer$1$onResume$1 implements Runnable {
    final /* synthetic */ MemBalloonController this$0;

    MemBalloonController$observer$1$onResume$1(MemBalloonController memBalloonController) {
        this.this$0 = memBalloonController;
    }

    public final void run() {
        Log.v("VmTerminalApp", "app resumed. deflating mem balloon to the minimum");
        this.this$0.getVm().setMemoryBalloonByPercent(0);
    }
}
