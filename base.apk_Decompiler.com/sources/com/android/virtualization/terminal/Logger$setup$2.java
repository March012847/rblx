package com.android.virtualization.terminal;

import java.io.InputStream;

/* compiled from: Logger.kt */
final class Logger$setup$2 implements Runnable {
    final /* synthetic */ InputStream $log;
    final /* synthetic */ String $tag;

    Logger$setup$2(InputStream inputStream, String str) {
        this.$log = inputStream;
        this.$tag = str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001a, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001b, code lost:
        kotlin.io.CloseableKt.closeFinally(r0, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001e, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r3 = this;
            java.io.InputStream r0 = r3.$log
            java.lang.String r3 = r3.$tag
            com.android.virtualization.terminal.Logger r1 = com.android.virtualization.terminal.Logger.INSTANCE     // Catch:{ Exception -> 0x000c }
            r1.writeToLogd(r0, r3)     // Catch:{ Exception -> 0x000c }
            goto L_0x0012
        L_0x000a:
            r3 = move-exception
            goto L_0x0019
        L_0x000c:
            r1 = move-exception
            java.lang.String r2 = "Failed to log VM log output. VM may be shutting down"
            android.util.Log.w(r3, r2, r1)     // Catch:{ all -> 0x000a }
        L_0x0012:
            kotlin.Unit r3 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x000a }
            r3 = 0
            kotlin.io.CloseableKt.closeFinally(r0, r3)
            return
        L_0x0019:
            throw r3     // Catch:{ all -> 0x001a }
        L_0x001a:
            r1 = move-exception
            kotlin.io.CloseableKt.closeFinally(r0, r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.Logger$setup$2.run():void");
    }
}
