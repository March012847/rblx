package com.android.virtualization.terminal;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: MemBalloonController.kt */
public final class MemBalloonController$observer$1 implements DefaultLifecycleObserver {
    final /* synthetic */ MemBalloonController this$0;

    MemBalloonController$observer$1(MemBalloonController memBalloonController) {
        this.this$0 = memBalloonController;
    }

    public void onResume(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getClass();
        ScheduledFuture access$getOngoingInflation$p = this.this$0.ongoingInflation;
        if (access$getOngoingInflation$p != null) {
            access$getOngoingInflation$p.cancel(false);
        }
        this.this$0.executor.execute(new MemBalloonController$observer$1$onResume$1(this.this$0));
    }

    public void onStop(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getClass();
        ScheduledFuture access$getOngoingInflation$p = this.this$0.ongoingInflation;
        if (access$getOngoingInflation$p != null) {
            access$getOngoingInflation$p.cancel(false);
        }
        this.this$0.balloonPercent = 10;
        MemBalloonController memBalloonController = this.this$0;
        memBalloonController.ongoingInflation = memBalloonController.executor.scheduleAtFixedRate(new MemBalloonController$observer$1$onStop$1(this.this$0), 0, 60, TimeUnit.SECONDS);
    }
}
