package com.android.virtualization.terminal;

import android.os.Build;
import android.os.Environment;
import android.util.Log;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import java.util.function.Function;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

/* compiled from: ImageArchive.kt */
public final class ImageArchive {
    private static final String BUILD_TAG;
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final String HOST_URL;
    private final Source source;

    public /* synthetic */ ImageArchive(URL url, DefaultConstructorMarker defaultConstructorMarker) {
        this(url);
    }

    public /* synthetic */ ImageArchive(Path path, DefaultConstructorMarker defaultConstructorMarker) {
        this(path);
    }

    /* compiled from: ImageArchive.kt */
    abstract class Source {
        public /* synthetic */ Source(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Source() {
        }
    }

    /* compiled from: ImageArchive.kt */
    final class UrlSource extends Source {
        private final Object value;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UrlSource) && Intrinsics.areEqual(this.value, ((UrlSource) obj).value);
        }

        public int hashCode() {
            Object obj = this.value;
            if (obj == null) {
                return 0;
            }
            return obj.hashCode();
        }

        public String toString() {
            Object obj = this.value;
            return "UrlSource(value=" + obj + ")";
        }

        public UrlSource(Object obj) {
            super((DefaultConstructorMarker) null);
            this.value = obj;
        }

        public final Object getValue() {
            return this.value;
        }
    }

    /* compiled from: ImageArchive.kt */
    final class PathSource extends Source {
        private final Object value;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof PathSource) && Intrinsics.areEqual(this.value, ((PathSource) obj).value);
        }

        public int hashCode() {
            Object obj = this.value;
            if (obj == null) {
                return 0;
            }
            return obj.hashCode();
        }

        public String toString() {
            Object obj = this.value;
            return "PathSource(value=" + obj + ")";
        }

        public PathSource(Object obj) {
            super((DefaultConstructorMarker) null);
            this.value = obj;
        }

        public final Object getValue() {
            return this.value;
        }
    }

    private ImageArchive(URL url) {
        this.source = new UrlSource(url);
    }

    private ImageArchive(Path path) {
        this.source = new PathSource(path);
    }

    public final boolean exists() {
        Source source2 = this.source;
        if (source2 instanceof UrlSource) {
            return true;
        }
        if (source2 instanceof PathSource) {
            return Files.exists((Path) ((PathSource) source2).getValue(), new LinkOption[0]);
        }
        throw new NoWhenBranchMatchedException();
    }

    public final String getPath() {
        Source source2 = this.source;
        if (source2 instanceof UrlSource) {
            return ((URL) ((UrlSource) source2).getValue()).toString();
        }
        if (source2 instanceof PathSource) {
            return ((PathSource) source2).getValue().toString();
        }
        throw new NoWhenBranchMatchedException();
    }

    public final long getSize() {
        if (exists()) {
            Source source2 = this.source;
            if (source2 instanceof UrlSource) {
                URLConnection openConnection = ((URL) ((UrlSource) source2).getValue()).openConnection();
                openConnection.getClass();
                HttpURLConnection httpURLConnection = (HttpURLConnection) openConnection;
                try {
                    httpURLConnection.setRequestMethod("HEAD");
                    httpURLConnection.getInputStream();
                    return (long) httpURLConnection.getContentLength();
                } finally {
                    httpURLConnection.disconnect();
                }
            } else if (source2 instanceof PathSource) {
                return Files.size((Path) ((PathSource) source2).getValue());
            } else {
                throw new NoWhenBranchMatchedException();
            }
        } else {
            throw new IllegalStateException("Cannot get size of non existing archive");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0032, code lost:
        r3 = (java.io.InputStream) r4.apply(r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.io.InputStream getInputStream(java.util.function.Function r4) {
        /*
            r3 = this;
            java.io.BufferedInputStream r0 = new java.io.BufferedInputStream
            com.android.virtualization.terminal.ImageArchive$Source r1 = r3.source
            boolean r2 = r1 instanceof com.android.virtualization.terminal.ImageArchive.UrlSource
            if (r2 == 0) goto L_0x0015
            com.android.virtualization.terminal.ImageArchive$UrlSource r1 = (com.android.virtualization.terminal.ImageArchive.UrlSource) r1
            java.lang.Object r3 = r1.getValue()
            java.net.URL r3 = (java.net.URL) r3
            java.io.InputStream r3 = r3.openStream()
            goto L_0x002d
        L_0x0015:
            boolean r1 = r1 instanceof com.android.virtualization.terminal.ImageArchive.PathSource
            if (r1 == 0) goto L_0x003d
            java.io.FileInputStream r1 = new java.io.FileInputStream
            com.android.virtualization.terminal.ImageArchive$Source r3 = r3.source
            com.android.virtualization.terminal.ImageArchive$PathSource r3 = (com.android.virtualization.terminal.ImageArchive.PathSource) r3
            java.lang.Object r3 = r3.getValue()
            java.nio.file.Path r3 = (java.nio.file.Path) r3
            java.io.File r3 = r3.toFile()
            r1.<init>(r3)
            r3 = r1
        L_0x002d:
            r0.<init>(r3)
            if (r4 == 0) goto L_0x003c
            java.lang.Object r3 = r4.apply(r0)
            java.io.InputStream r3 = (java.io.InputStream) r3
            if (r3 != 0) goto L_0x003b
            goto L_0x003c
        L_0x003b:
            return r3
        L_0x003c:
            return r0
        L_0x003d:
            kotlin.NoWhenBranchMatchedException r3 = new kotlin.NoWhenBranchMatchedException
            r3.<init>()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.ImageArchive.getInputStream(java.util.function.Function):java.io.InputStream");
    }

    public final void installTo(Path path, Function function) {
        String str;
        path.getClass();
        Source source2 = this.source;
        if (source2 instanceof PathSource) {
            str = ((PathSource) source2).getValue().toString();
        } else if (source2 instanceof UrlSource) {
            str = ((URL) ((UrlSource) source2).getValue()).toString();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        Log.d("VmTerminalApp", "Install started. source: " + str + ", destination: " + path);
        TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(new GzipCompressorInputStream(getInputStream(function)));
        try {
            Files.createDirectories(path, new FileAttribute[0]);
            while (true) {
                ArchiveEntry nextEntry = tarArchiveInputStream.getNextEntry();
                if (nextEntry != null) {
                    Path resolve = path.resolve(nextEntry.getName());
                    if (Files.isDirectory(resolve, new LinkOption[0])) {
                        Files.createDirectories(resolve, new FileAttribute[0]);
                    } else {
                        Log.d("VmTerminalApp", "Installing " + resolve);
                        Files.copy(tarArchiveInputStream, resolve, new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                    }
                } else {
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(tarArchiveInputStream, (Throwable) null);
                    Log.d("VmTerminalApp", "Installed");
                    commitInstallationAt(path);
                    return;
                }
            }
        } catch (Throwable th) {
            CloseableKt.closeFinally(tarArchiveInputStream, th);
            throw th;
        }
    }

    private final void commitInstallationAt(Path path) {
        Files.createFile(path.resolve("completed"), new FileAttribute[0]);
    }

    /* compiled from: ImageArchive.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Path getSdcardPathForTesting() {
            Path path = Environment.getExternalStoragePublicDirectory("linux").toPath();
            path.getClass();
            return path;
        }

        public final ImageArchive fromSdCard() {
            Path resolve = getSdcardPathForTesting().resolve("images.tar.gz");
            resolve.getClass();
            return new ImageArchive(resolve, (DefaultConstructorMarker) null);
        }

        public final ImageArchive fromInternet() {
            String[] strArr = Build.SUPPORTED_ABIS;
            String str = "x86_64";
            if (!CollectionsKt.listOf(Arrays.copyOf(strArr, strArr.length)).contains(str)) {
                str = "aarch64";
            }
            try {
                String access$getHOST_URL$cp = ImageArchive.HOST_URL;
                return new ImageArchive(new URL(access$getHOST_URL$cp + "/" + str + "/images.tar.gz"), (DefaultConstructorMarker) null);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }

        public final ImageArchive getDefault() {
            ImageArchive fromSdCard = fromSdCard();
            if (!Build.isDebuggable() || !fromSdCard.exists()) {
                return fromInternet();
            }
            return fromSdCard;
        }
    }

    static {
        String num = Integer.toString(Build.VERSION.SDK_INT_FULL);
        BUILD_TAG = num;
        HOST_URL = "https://dl.google.com/android/ferrochrome/" + num;
    }
}
