package com.android.virtualization.terminal;

/* compiled from: VmLauncherService.kt */
final class VmLauncherService$startDebianServer$1 implements Runnable {
    final /* synthetic */ VmLauncherService this$0;

    VmLauncherService$startDebianServer$1(VmLauncherService vmLauncherService) {
        this.this$0 = vmLauncherService;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0038, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r1, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003c, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r3 = this;
            java.io.File r0 = new java.io.File
            com.android.virtualization.terminal.VmLauncherService r1 = r3.this$0
            java.io.File r1 = r1.getFilesDir()
            java.lang.String r2 = "debian_service_port"
            r0.<init>(r1, r2)
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x003d }
            r1.<init>(r0)     // Catch:{ IOException -> 0x003d }
            com.android.virtualization.terminal.VmLauncherService r3 = r3.this$0     // Catch:{ IOException -> 0x003d }
            io.grpc.Server r3 = r3.server     // Catch:{ all -> 0x0036 }
            r3.getClass()     // Catch:{ all -> 0x0036 }
            int r3 = r3.getPort()     // Catch:{ all -> 0x0036 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x0036 }
            java.nio.charset.Charset r0 = kotlin.text.Charsets.UTF_8     // Catch:{ all -> 0x0036 }
            byte[] r3 = r3.getBytes(r0)     // Catch:{ all -> 0x0036 }
            r3.getClass()     // Catch:{ all -> 0x0036 }
            r1.write(r3)     // Catch:{ all -> 0x0036 }
            kotlin.Unit r3 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0036 }
            r3 = 0
            kotlin.io.CloseableKt.closeFinally(r1, r3)     // Catch:{ IOException -> 0x003d }
            return
        L_0x0036:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0038 }
        L_0x0038:
            r0 = move-exception
            kotlin.io.CloseableKt.closeFinally(r1, r3)     // Catch:{ IOException -> 0x003d }
            throw r0     // Catch:{ IOException -> 0x003d }
        L_0x003d:
            r3 = move-exception
            java.lang.String r0 = "VmTerminalApp"
            java.lang.String r1 = "cannot write grpc port number"
            android.util.Log.d(r0, r1, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.VmLauncherService$startDebianServer$1.run():void");
    }
}
