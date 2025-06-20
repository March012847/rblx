package com.android.virtualization.terminal;

import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: Logger.kt */
final class Logger$setup$1 implements Runnable {
    final /* synthetic */ InputStream $console;
    final /* synthetic */ OutputStream $file;
    final /* synthetic */ String $tag;

    Logger$setup$1(InputStream inputStream, String str, OutputStream outputStream) {
        this.$console = inputStream;
        this.$tag = str;
        this.$file = outputStream;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x001a, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r2, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x001e, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0020, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0024, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r4 = this;
            java.io.InputStream r0 = r4.$console     // Catch:{ Exception -> 0x0014 }
            java.io.OutputStream r1 = r4.$file     // Catch:{ Exception -> 0x0014 }
            com.android.virtualization.terminal.Logger$LineBufferedOutputStream r2 = new com.android.virtualization.terminal.Logger$LineBufferedOutputStream     // Catch:{ all -> 0x0016 }
            r2.<init>(r1)     // Catch:{ all -> 0x0016 }
            libcore.io.Streams.copy(r0, r2)     // Catch:{ all -> 0x0018 }
            r1 = 0
            kotlin.io.CloseableKt.closeFinally(r2, r1)     // Catch:{ all -> 0x0016 }
            kotlin.io.CloseableKt.closeFinally(r0, r1)     // Catch:{ Exception -> 0x0014 }
            return
        L_0x0014:
            r0 = move-exception
            goto L_0x0025
        L_0x0016:
            r1 = move-exception
            goto L_0x001f
        L_0x0018:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x001a }
        L_0x001a:
            r3 = move-exception
            kotlin.io.CloseableKt.closeFinally(r2, r1)     // Catch:{ all -> 0x0016 }
            throw r3     // Catch:{ all -> 0x0016 }
        L_0x001f:
            throw r1     // Catch:{ all -> 0x0020 }
        L_0x0020:
            r2 = move-exception
            kotlin.io.CloseableKt.closeFinally(r0, r1)     // Catch:{ Exception -> 0x0014 }
            throw r2     // Catch:{ Exception -> 0x0014 }
        L_0x0025:
            java.lang.String r4 = r4.$tag
            java.lang.String r1 = "Failed to log console output. VM may be shutting down"
            android.util.Log.w(r4, r1, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.Logger$setup$1.run():void");
    }
}
