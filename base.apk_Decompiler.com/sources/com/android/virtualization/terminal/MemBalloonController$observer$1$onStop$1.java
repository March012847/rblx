package com.android.virtualization.terminal;

import android.util.Log;
import java.util.concurrent.ScheduledFuture;

/* compiled from: MemBalloonController.kt */
final class MemBalloonController$observer$1$onStop$1 implements Runnable {
    final /* synthetic */ MemBalloonController this$0;

    MemBalloonController$observer$1$onStop$1(MemBalloonController memBalloonController) {
        this.this$0 = memBalloonController;
    }

    public final void run() {
        if (this.this$0.balloonPercent <= 50) {
            int access$getBalloonPercent$p = this.this$0.balloonPercent;
            Log.v("VmTerminalApp", "inflating mem balloon to " + access$getBalloonPercent$p + " %");
            this.this$0.getVm().setMemoryBalloonByPercent(this.this$0.balloonPercent);
            MemBalloonController memBalloonController = this.this$0;
            memBalloonController.balloonPercent = memBalloonController.balloonPercent + 5;
            return;
        }
        Log.v("VmTerminalApp", "mem balloon is inflated to its max (50 %)");
        ScheduledFuture access$getOngoingInflation$p = this.this$0.ongoingInflation;
        access$getOngoingInflation$p.getClass();
        access$getOngoingInflation$p.cancel(false);
    }
}
