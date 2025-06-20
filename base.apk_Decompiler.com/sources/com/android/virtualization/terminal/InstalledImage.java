package com.android.virtualization.terminal;

import android.content.Context;
import android.os.FileUtils;
import android.util.Log;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;

/* compiled from: InstalledImage.kt */
public final class InstalledImage {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final Path backupFile;
    private final Lazy buildId$delegate;
    private final Path configPath;
    private final Path installDir;
    private final Path marker;
    private final Path rootPartition;

    public /* synthetic */ InstalledImage(Path path, DefaultConstructorMarker defaultConstructorMarker) {
        this(path);
    }

    private InstalledImage(Path path) {
        this.installDir = path;
        Path resolve = path.resolve("root_part");
        resolve.getClass();
        this.rootPartition = resolve;
        Path resolve2 = path.resolve("root_part_backup");
        resolve2.getClass();
        this.backupFile = resolve2;
        Path resolve3 = path.resolve("vm_config.json");
        resolve3.getClass();
        this.configPath = resolve3;
        Path resolve4 = path.resolve("completed");
        resolve4.getClass();
        this.marker = resolve4;
        this.buildId$delegate = LazyKt.lazy(new InstalledImage$$ExternalSyntheticLambda0(this));
    }

    public final Path getInstallDir() {
        return this.installDir;
    }

    public final Path getBackupFile() {
        return this.backupFile;
    }

    public final Path getConfigPath() {
        return this.configPath;
    }

    /* access modifiers changed from: private */
    public static final String buildId_delegate$lambda$0(InstalledImage installedImage) {
        return installedImage.readBuildId();
    }

    public final String getBuildId() {
        return (String) this.buildId$delegate.getValue();
    }

    public final boolean isInstalled() {
        return Files.exists(this.marker, new LinkOption[0]);
    }

    public final void uninstallFully() {
        FileUtils.deleteContentsAndDir(this.installDir.toFile());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0030, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r0, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0034, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.String readBuildId() {
        /*
            r2 = this;
            java.nio.file.Path r2 = r2.installDir
            java.lang.String r0 = "build_id"
            java.nio.file.Path r2 = r2.resolve(r0)
            r0 = 0
            java.nio.file.LinkOption[] r0 = new java.nio.file.LinkOption[r0]
            boolean r0 = java.nio.file.Files.exists(r2, r0)
            if (r0 != 0) goto L_0x0014
            java.lang.String r2 = "<no build id>"
            return r2
        L_0x0014:
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0035 }
            java.io.FileReader r1 = new java.io.FileReader     // Catch:{ IOException -> 0x0035 }
            java.io.File r2 = r2.toFile()     // Catch:{ IOException -> 0x0035 }
            r1.<init>(r2)     // Catch:{ IOException -> 0x0035 }
            r0.<init>(r1)     // Catch:{ IOException -> 0x0035 }
            java.lang.String r2 = r0.readLine()     // Catch:{ all -> 0x002e }
            r2.getClass()     // Catch:{ all -> 0x002e }
            r1 = 0
            kotlin.io.CloseableKt.closeFinally(r0, r1)     // Catch:{ IOException -> 0x0035 }
            return r2
        L_0x002e:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0030 }
        L_0x0030:
            r1 = move-exception
            kotlin.io.CloseableKt.closeFinally(r0, r2)     // Catch:{ IOException -> 0x0035 }
            throw r1     // Catch:{ IOException -> 0x0035 }
        L_0x0035:
            r2 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Failed to read build ID"
            r0.<init>(r1, r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.InstalledImage.readBuildId():java.lang.String");
    }

    public final boolean isOlderThanCurrentVersion() {
        int i;
        try {
            i = Integer.parseInt((String) CollectionsKt.last(StringsKt.split$default(getBuildId(), new String[]{" "}, false, 0, 6, (Object) null)));
        } catch (Exception unused) {
            i = 0;
        }
        return i < 2025;
    }

    public final Path uninstallAndBackup() {
        Files.delete(this.marker);
        Files.move(this.rootPartition, this.backupFile, new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
        return this.backupFile;
    }

    public final boolean hasBackup() {
        return Files.exists(this.backupFile, new LinkOption[0]);
    }

    public final void deleteBackup() {
        Files.deleteIfExists(this.backupFile);
    }

    public final long getApparentSize() {
        return Files.size(this.rootPartition);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0025, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0021, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0022, code lost:
        kotlin.io.CloseableKt.closeFinally(r0, r4);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final long getPhysicalSize() {
        /*
            r4 = this;
            java.io.RandomAccessFile r0 = new java.io.RandomAccessFile
            java.nio.file.Path r4 = r4.rootPartition
            java.io.File r4 = r4.toFile()
            java.lang.String r1 = "rw"
            r0.<init>(r4, r1)
            java.io.FileDescriptor r4 = r0.getFD()     // Catch:{ all -> 0x001f }
            android.system.StructStat r4 = android.system.Os.fstat(r4)     // Catch:{ all -> 0x001f }
            r1 = 0
            kotlin.io.CloseableKt.closeFinally(r0, r1)
            r0 = 512(0x200, double:2.53E-321)
            long r2 = r4.st_blocks
            long r2 = r2 * r0
            return r2
        L_0x001f:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x0021 }
        L_0x0021:
            r1 = move-exception
            kotlin.io.CloseableKt.closeFinally(r0, r4)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.InstalledImage.getPhysicalSize():long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x0043 A[SYNTHETIC, Splitter:B:6:0x0043] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final long getSmallestSizePossible() {
        /*
            r7 = this;
            com.android.virtualization.terminal.InstalledImage$Companion r0 = Companion
            java.nio.file.Path r1 = r7.rootPartition
            r0.runE2fsck(r1)
            java.nio.file.Path r7 = r7.rootPartition
            java.nio.file.Path r7 = r7.toAbsolutePath()
            java.lang.String r7 = r7.toString()
            java.lang.String r1 = "/system/bin/resize2fs"
            java.lang.String r2 = "-P"
            java.lang.String[] r1 = new java.lang.String[]{r1, r2, r7}
            java.lang.String r0 = r0.runCommand(r1)
            kotlin.text.Regex r1 = new kotlin.text.Regex
            java.lang.String r2 = "Estimated minimum size of the filesystem: ([0-9]+)"
            r1.<init>((java.lang.String) r2)
            java.util.List r2 = kotlin.text.StringsKt.lines(r0)
            java.util.Iterator r2 = r2.iterator()
        L_0x002c:
            boolean r3 = r2.hasNext()
            r4 = 0
            if (r3 == 0) goto L_0x0041
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            r5 = 0
            r6 = 2
            kotlin.text.MatchResult r4 = kotlin.text.Regex.find$default(r1, r3, r5, r6, r4)
            if (r4 == 0) goto L_0x002c
        L_0x0041:
            if (r4 == 0) goto L_0x0060
            java.util.List r1 = r4.getGroupValues()     // Catch:{ NumberFormatException -> 0x0060 }
            r2 = 1
            java.lang.Object r1 = r1.get(r2)     // Catch:{ NumberFormatException -> 0x0060 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ NumberFormatException -> 0x0060 }
            long r1 = java.lang.Long.parseLong(r1)     // Catch:{ NumberFormatException -> 0x0060 }
            com.android.virtualization.terminal.InstalledImage$Companion r3 = Companion     // Catch:{ NumberFormatException -> 0x0060 }
            r4 = 4
            long r4 = (long) r4     // Catch:{ NumberFormatException -> 0x0060 }
            long r1 = r1 * r4
            r4 = 1024(0x400, float:1.435E-42)
            long r4 = (long) r4     // Catch:{ NumberFormatException -> 0x0060 }
            long r1 = r1 * r4
            long r0 = r3.roundUp$packages__modules__Virtualization__android__TerminalApp__android_common_VmTerminalAppGoogle_apex10000_p__VmTerminalApp(r1)     // Catch:{ NumberFormatException -> 0x0060 }
            return r0
        L_0x0060:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Failed to get min size, p="
            r1.append(r2)
            r1.append(r7)
            java.lang.String r7 = ", result="
            r1.append(r7)
            r1.append(r0)
            java.lang.String r7 = r1.toString()
            java.lang.String r0 = "VmTerminalApp"
            android.util.Log.e(r0, r7)
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            r0.<init>(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.InstalledImage.getSmallestSizePossible():long");
    }

    public final long resize(long j) {
        Companion companion = Companion;
        long roundUp$packages__modules__Virtualization__android__TerminalApp__android_common_VmTerminalAppGoogle_apex10000_p__VmTerminalApp = companion.roundUp$packages__modules__Virtualization__android__TerminalApp__android_common_VmTerminalAppGoogle_apex10000_p__VmTerminalApp(j);
        long apparentSize = getApparentSize();
        companion.runE2fsck(this.rootPartition);
        int i = (roundUp$packages__modules__Virtualization__android__TerminalApp__android_common_VmTerminalAppGoogle_apex10000_p__VmTerminalApp > apparentSize ? 1 : (roundUp$packages__modules__Virtualization__android__TerminalApp__android_common_VmTerminalAppGoogle_apex10000_p__VmTerminalApp == apparentSize ? 0 : -1));
        if (i == 0) {
            return roundUp$packages__modules__Virtualization__android__TerminalApp__android_common_VmTerminalAppGoogle_apex10000_p__VmTerminalApp;
        }
        if (i > 0 && !allocateSpace(roundUp$packages__modules__Virtualization__android__TerminalApp__android_common_VmTerminalAppGoogle_apex10000_p__VmTerminalApp)) {
            return apparentSize;
        }
        companion.resizeFilesystem(this.rootPartition, roundUp$packages__modules__Virtualization__android__TerminalApp__android_common_VmTerminalAppGoogle_apex10000_p__VmTerminalApp);
        return getApparentSize();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0041, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r3, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0045, code lost:
        throw r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean allocateSpace(long r8) {
        /*
            r7 = this;
            java.lang.String r0 = "VmTerminalApp"
            long r1 = r7.getApparentSize()
            java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch:{ ErrnoException -> 0x003d }
            java.nio.file.Path r4 = r7.rootPartition     // Catch:{ ErrnoException -> 0x003d }
            java.io.File r4 = r4.toFile()     // Catch:{ ErrnoException -> 0x003d }
            java.lang.String r5 = "rw"
            r3.<init>(r4, r5)     // Catch:{ ErrnoException -> 0x003d }
            java.io.FileDescriptor r4 = r3.getFD()     // Catch:{ all -> 0x003f }
            r5 = 0
            android.system.Os.posix_fallocate(r4, r5, r8)     // Catch:{ all -> 0x003f }
            kotlin.Unit r4 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x003f }
            r4 = 0
            kotlin.io.CloseableKt.closeFinally(r3, r4)     // Catch:{ ErrnoException -> 0x003d }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ ErrnoException -> 0x003d }
            r3.<init>()     // Catch:{ ErrnoException -> 0x003d }
            java.lang.String r4 = "Allocated space to: "
            r3.append(r4)     // Catch:{ ErrnoException -> 0x003d }
            r3.append(r8)     // Catch:{ ErrnoException -> 0x003d }
            java.lang.String r8 = " bytes"
            r3.append(r8)     // Catch:{ ErrnoException -> 0x003d }
            java.lang.String r8 = r3.toString()     // Catch:{ ErrnoException -> 0x003d }
            android.util.Log.d(r0, r8)     // Catch:{ ErrnoException -> 0x003d }
            r7 = 1
            return r7
        L_0x003d:
            r8 = move-exception
            goto L_0x0046
        L_0x003f:
            r8 = move-exception
            throw r8     // Catch:{ all -> 0x0041 }
        L_0x0041:
            r9 = move-exception
            kotlin.io.CloseableKt.closeFinally(r3, r8)     // Catch:{ ErrnoException -> 0x003d }
            throw r9     // Catch:{ ErrnoException -> 0x003d }
        L_0x0046:
            java.lang.String r9 = "Failed to allocate space"
            android.util.Log.e(r0, r9, r8)
            int r3 = r8.errno
            int r4 = android.system.OsConstants.ENOSPC
            if (r3 != r4) goto L_0x005b
            java.lang.String r8 = "Trying to truncate disk into the original size"
            android.util.Log.d(r0, r8)
            r7.truncate(r1)
            r7 = 0
            return r7
        L_0x005b:
            java.io.IOException r7 = new java.io.IOException
            r7.<init>(r9, r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.InstalledImage.allocateSpace(long):boolean");
    }

    public final long shrinkToMinimumSize() {
        Companion companion = Companion;
        companion.runE2fsck(this.rootPartition);
        String unused = companion.runCommand("/system/bin/resize2fs", "-M", this.rootPartition.toAbsolutePath().toString());
        Path path = this.rootPartition;
        Log.d("VmTerminalApp", "resize2fs -M completed: " + path);
        companion.runE2fsck(this.rootPartition);
        return getApparentSize();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003a, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r1, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003e, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void truncate(long r4) {
        /*
            r3 = this;
            java.lang.String r0 = "VmTerminalApp"
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch:{ ErrnoException -> 0x0036 }
            java.nio.file.Path r3 = r3.rootPartition     // Catch:{ ErrnoException -> 0x0036 }
            java.io.File r3 = r3.toFile()     // Catch:{ ErrnoException -> 0x0036 }
            java.lang.String r2 = "rw"
            r1.<init>(r3, r2)     // Catch:{ ErrnoException -> 0x0036 }
            java.io.FileDescriptor r3 = r1.getFD()     // Catch:{ all -> 0x0038 }
            android.system.Os.ftruncate(r3, r4)     // Catch:{ all -> 0x0038 }
            kotlin.Unit r3 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0038 }
            r3 = 0
            kotlin.io.CloseableKt.closeFinally(r1, r3)     // Catch:{ ErrnoException -> 0x0036 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ ErrnoException -> 0x0036 }
            r3.<init>()     // Catch:{ ErrnoException -> 0x0036 }
            java.lang.String r1 = "Truncated space to: "
            r3.append(r1)     // Catch:{ ErrnoException -> 0x0036 }
            r3.append(r4)     // Catch:{ ErrnoException -> 0x0036 }
            java.lang.String r4 = " bytes"
            r3.append(r4)     // Catch:{ ErrnoException -> 0x0036 }
            java.lang.String r3 = r3.toString()     // Catch:{ ErrnoException -> 0x0036 }
            android.util.Log.d(r0, r3)     // Catch:{ ErrnoException -> 0x0036 }
            return
        L_0x0036:
            r3 = move-exception
            goto L_0x003f
        L_0x0038:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x003a }
        L_0x003a:
            r4 = move-exception
            kotlin.io.CloseableKt.closeFinally(r1, r3)     // Catch:{ ErrnoException -> 0x0036 }
            throw r4     // Catch:{ ErrnoException -> 0x0036 }
        L_0x003f:
            java.lang.String r4 = "Failed to truncate space"
            android.util.Log.e(r0, r4, r3)
            java.io.IOException r5 = new java.io.IOException
            r5.<init>(r4, r3)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.InstalledImage.truncate(long):void");
    }

    /* compiled from: InstalledImage.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final InstalledImage getDefault(Context context) {
            context.getClass();
            Path resolve = context.getFilesDir().toPath().resolve("linux");
            resolve.getClass();
            return new InstalledImage(resolve, (DefaultConstructorMarker) null);
        }

        /* access modifiers changed from: private */
        public final void runE2fsck(Path path) {
            runCommand("/system/bin/e2fsck", "-y", "-f", path.toAbsolutePath().toString());
            Log.d("VmTerminalApp", "e2fsck completed: " + path);
        }

        /* access modifiers changed from: private */
        public final void resizeFilesystem(Path path, long j) {
            long j2 = j / ((long) 1048576);
            if (j2 != 0) {
                String str = j2 + "M";
                runCommand("/system/bin/resize2fs", path.toAbsolutePath().toString(), str);
                Log.d("VmTerminalApp", "resize2fs completed: " + path + ", size: " + str);
                return;
            }
            Log.e("VmTerminalApp", "Invalid size: " + j + " bytes");
            throw new IllegalArgumentException("Size cannot be zero MB");
        }

        /* access modifiers changed from: private */
        public final String runCommand(String... strArr) {
            try {
                Process start = new ProcessBuilder((String[]) Arrays.copyOf(strArr, strArr.length)).redirectErrorStream(true).start();
                start.waitFor();
                byte[] readAllBytes = start.getInputStream().readAllBytes();
                readAllBytes.getClass();
                String str = new String(readAllBytes, Charsets.UTF_8);
                if (start.exitValue() != 0) {
                    String joinToString$default = CollectionsKt.joinToString$default(CollectionsKt.listOf(Arrays.copyOf(strArr, strArr.length)), " ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
                    int exitValue = start.exitValue();
                    Log.w("VmTerminalApp", "Process returned with error, command=" + joinToString$default + ",exitValue=" + exitValue + ", result=" + str);
                }
                return str;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Command interrupted", e);
            }
        }

        public final long roundUp$packages__modules__Virtualization__android__TerminalApp__android_common_VmTerminalAppGoogle_apex10000_p__VmTerminalApp(long j) {
            return ((long) Math.ceil(((double) j) / ((double) 4194304))) * 4194304;
        }
    }
}
