package com.android.virtualization.terminal;

import android.system.virtualmachine.VirtualMachine;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import kotlin.Unit;
import kotlin.ranges.RangesKt;

/* compiled from: Logger.kt */
public final class Logger {
    public static final Logger INSTANCE = new Logger();

    private Logger() {
    }

    public final void setup(VirtualMachine virtualMachine, Path path, ExecutorService executorService) {
        virtualMachine.getClass();
        path.getClass();
        executorService.getClass();
        String name = virtualMachine.getName();
        name.getClass();
        if (virtualMachine.getConfig().getDebugLevel() != 1) {
            Log.i(name, "Logs are not captured. Non-debuggable VM.");
            return;
        }
        try {
            if (Files.isRegularFile(path, new LinkOption[0])) {
                Log.i(name, "Removed legacy log file: " + path);
                Files.delete(path);
            }
            Files.createDirectories(path, new FileAttribute[0]);
            deleteOldLogs(path, 10);
            LocalDateTime now = LocalDateTime.now();
            Path resolve = path.resolve(now + ".txt");
            InputStream consoleOutput = virtualMachine.getConsoleOutput();
            consoleOutput.getClass();
            executorService.execute(new Logger$setup$1(consoleOutput, name, Files.newOutputStream(resolve, new OpenOption[]{StandardOpenOption.CREATE})));
            InputStream logOutput = virtualMachine.getLogOutput();
            logOutput.getClass();
            executorService.execute(new Logger$setup$2(logOutput, name));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public final void deleteOldLogs(Path path, long j) {
        path.getClass();
        Files.list(path).filter(new Logger$sam$java_util_function_Predicate$0(new Logger$$ExternalSyntheticLambda0())).sorted(Comparator.comparingLong(new Logger$sam$java_util_function_ToLongFunction$0(new Logger$$ExternalSyntheticLambda1())).reversed()).skip(j).forEach(new Logger$sam$java_util_function_Consumer$0(new Logger$$ExternalSyntheticLambda2()));
    }

    /* access modifiers changed from: private */
    public static final boolean deleteOldLogs$lambda$0(Path path) {
        return Files.isRegularFile(path, new LinkOption[0]);
    }

    /* access modifiers changed from: private */
    public static final long deleteOldLogs$lambda$1(Path path) {
        path.getClass();
        return Files.getLastModifiedTime(path, new LinkOption[0]).toMillis();
    }

    /* access modifiers changed from: private */
    public static final Unit deleteOldLogs$lambda$2(Path path) {
        try {
            Files.delete(path);
        } catch (IOException unused) {
        }
        return Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0032, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0033, code lost:
        kotlin.io.CloseableKt.closeFinally(r1, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0036, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void writeToLogd(java.io.InputStream r2, java.lang.String r3) {
        /*
            r1 = this;
            java.io.BufferedReader r1 = new java.io.BufferedReader
            java.io.InputStreamReader r0 = new java.io.InputStreamReader
            r0.<init>(r2)
            r1.<init>(r0)
            kotlin.sequences.Sequence r2 = kotlin.io.TextStreamsKt.lineSequence(r1)     // Catch:{ all -> 0x0030 }
            com.android.virtualization.terminal.Logger$$ExternalSyntheticLambda3 r0 = new com.android.virtualization.terminal.Logger$$ExternalSyntheticLambda3     // Catch:{ all -> 0x0030 }
            r0.<init>()     // Catch:{ all -> 0x0030 }
            kotlin.sequences.Sequence r2 = kotlin.sequences.SequencesKt.takeWhile(r2, r0)     // Catch:{ all -> 0x0030 }
            r0 = 0
            kotlin.io.CloseableKt.closeFinally(r1, r0)
            java.util.Iterator r1 = r2.iterator()
        L_0x001f:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x002f
            java.lang.Object r2 = r1.next()
            java.lang.String r2 = (java.lang.String) r2
            android.util.Log.d(r3, r2)
            goto L_0x001f
        L_0x002f:
            return
        L_0x0030:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0032 }
        L_0x0032:
            r3 = move-exception
            kotlin.io.CloseableKt.closeFinally(r1, r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.Logger.writeToLogd(java.io.InputStream, java.lang.String):void");
    }

    /* access modifiers changed from: private */
    public static final boolean writeToLogd$lambda$4$lambda$3(String str) {
        str.getClass();
        return !Thread.interrupted();
    }

    /* compiled from: Logger.kt */
    final class LineBufferedOutputStream extends BufferedOutputStream {
        public LineBufferedOutputStream(OutputStream outputStream) {
            super(outputStream);
        }

        public void write(byte[] bArr, int i, int i2) {
            Object obj;
            bArr.getClass();
            super.write(bArr, i, i2);
            Iterator it = RangesKt.until(0, i2).iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                if (bArr[((Number) obj).intValue() + i] == 10) {
                    break;
                }
            }
            if (((Integer) obj) != null) {
                flush();
            }
        }
    }
}
