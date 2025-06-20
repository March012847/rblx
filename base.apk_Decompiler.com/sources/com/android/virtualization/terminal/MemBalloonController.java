package com.android.virtualization.terminal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.system.virtualmachine.VirtualMachine;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: MemBalloonController.kt */
public final class MemBalloonController {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final Handler mainHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public int balloonPercent;
    private final Context context;
    /* access modifiers changed from: private */
    public final ScheduledExecutorService executor;
    /* access modifiers changed from: private */
    public final MemBalloonController$observer$1 observer = new MemBalloonController$observer$1(this);
    /* access modifiers changed from: private */
    public ScheduledFuture ongoingInflation;
    private final VirtualMachine vm;

    /* compiled from: MemBalloonController.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* access modifiers changed from: private */
        public final void runOnMainThread(Runnable runnable) {
            MemBalloonController.mainHandler.post(runnable);
        }
    }

    public MemBalloonController(Context context2, VirtualMachine virtualMachine) {
        context2.getClass();
        virtualMachine.getClass();
        this.context = context2;
        this.vm = virtualMachine;
        Context applicationContext = context2.getApplicationContext();
        applicationContext.getClass();
        this.executor = Executors.newSingleThreadScheduledExecutor(new TerminalThreadFactory(applicationContext));
    }

    public final VirtualMachine getVm() {
        return this.vm;
    }

    public final void start() {
        Companion.runOnMainThread(new MemBalloonController$start$1(this));
    }

    public final void stop() {
        Companion.runOnMainThread(new MemBalloonController$stop$1(this));
    }
}
