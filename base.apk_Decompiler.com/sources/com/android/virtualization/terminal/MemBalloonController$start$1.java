package com.android.virtualization.terminal;

import androidx.lifecycle.ProcessLifecycleOwner;

/* compiled from: MemBalloonController.kt */
final class MemBalloonController$start$1 implements Runnable {
    final /* synthetic */ MemBalloonController this$0;

    MemBalloonController$start$1(MemBalloonController memBalloonController) {
        this.this$0 = memBalloonController;
    }

    public final void run() {
        ProcessLifecycleOwner.Companion.get().getLifecycle().addObserver(this.this$0.observer);
    }
}
