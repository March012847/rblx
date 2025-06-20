package com.android.virtualization.terminal;

/* compiled from: DebianServiceImpl.kt */
final class DebianServiceImpl$openShutdownRequestQueue$1 implements Runnable {
    final /* synthetic */ DebianServiceImpl this$0;

    DebianServiceImpl$openShutdownRequestQueue$1(DebianServiceImpl debianServiceImpl) {
        this.this$0 = debianServiceImpl;
    }

    public final void run() {
        this.this$0.shutdownRunnable = null;
    }
}
