package com.android.virtualization.terminal;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import com.android.virtualization.terminal.IInstallerService;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: InstallerService.kt */
public final class InstallerService extends Service {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private ConnectivityManager connectivityManager;
    private ExecutorService executorService;
    /* access modifiers changed from: private */
    public boolean hasWifi;
    /* access modifiers changed from: private */
    public boolean isInstalling;
    /* access modifiers changed from: private */
    public IInstallProgressListener listener;
    /* access modifiers changed from: private */
    public final Object lock = new Object();
    private MyNetworkCallback networkCallback;
    private Notification notification;

    public void onCreate() {
        super.onCreate();
        this.notification = new Notification.Builder(this, "long_running").setSilent(true).setSmallIcon(2131165347).setContentTitle(getString(2131689552)).setContentText(getString(2131689551)).setOngoing(true).setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 67108864)).build();
        Context applicationContext = getApplicationContext();
        applicationContext.getClass();
        this.executorService = Executors.newSingleThreadExecutor(new TerminalThreadFactory(applicationContext));
        ConnectivityManager connectivityManager2 = (ConnectivityManager) getSystemService(ConnectivityManager.class);
        this.connectivityManager = connectivityManager2;
        MyNetworkCallback myNetworkCallback = null;
        if (connectivityManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("connectivityManager");
            connectivityManager2 = null;
        }
        Network boundNetworkForProcess = connectivityManager2.getBoundNetworkForProcess();
        if (boundNetworkForProcess != null) {
            ConnectivityManager connectivityManager3 = this.connectivityManager;
            if (connectivityManager3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("connectivityManager");
                connectivityManager3 = null;
            }
            NetworkCapabilities networkCapabilities = connectivityManager3.getNetworkCapabilities(boundNetworkForProcess);
            if (networkCapabilities != null) {
                this.hasWifi = networkCapabilities.hasTransport(1);
            }
        }
        this.networkCallback = new MyNetworkCallback();
        ConnectivityManager connectivityManager4 = this.connectivityManager;
        if (connectivityManager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("connectivityManager");
            connectivityManager4 = null;
        }
        MyNetworkCallback myNetworkCallback2 = this.networkCallback;
        if (myNetworkCallback2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("networkCallback");
        } else {
            myNetworkCallback = myNetworkCallback2;
        }
        connectivityManager4.registerDefaultNetworkCallback(myNetworkCallback);
    }

    public IBinder onBind(Intent intent) {
        return new InstallerServiceImpl(this);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        Log.d("VmTerminalApp", "Starting service ...");
        return 1;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d("VmTerminalApp", "Service is destroyed");
        ExecutorService executorService2 = this.executorService;
        MyNetworkCallback myNetworkCallback = null;
        if (executorService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("executorService");
            executorService2 = null;
        }
        executorService2.shutdown();
        ConnectivityManager connectivityManager2 = this.connectivityManager;
        if (connectivityManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("connectivityManager");
            connectivityManager2 = null;
        }
        MyNetworkCallback myNetworkCallback2 = this.networkCallback;
        if (myNetworkCallback2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("networkCallback");
        } else {
            myNetworkCallback = myNetworkCallback2;
        }
        connectivityManager2.unregisterNetworkCallback(myNetworkCallback);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001f, code lost:
        startService(new android.content.Intent(r4, com.android.virtualization.terminal.InstallerService.class));
        r0 = r4.notification;
        r1 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002c, code lost:
        if (r0 != null) goto L_0x0034;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002e, code lost:
        kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("notification");
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0034, code lost:
        startForeground(1313, r0, 1073741824);
        r0 = r4.executorService;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003d, code lost:
        if (r0 != null) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003f, code lost:
        kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("executorService");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0045, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0046, code lost:
        r1.execute(new com.android.virtualization.terminal.InstallerService$requestInstall$2(r4, r5));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void requestInstall(boolean r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.lock
            monitor-enter(r0)
            boolean r1 = r4.isInstalling     // Catch:{ all -> 0x0010 }
            if (r1 == 0) goto L_0x0012
            java.lang.String r4 = "VmTerminalApp"
            java.lang.String r5 = "already installing.."
            android.util.Log.i(r4, r5)     // Catch:{ all -> 0x0010 }
            monitor-exit(r0)
            return
        L_0x0010:
            r4 = move-exception
            goto L_0x004f
        L_0x0012:
            java.lang.String r1 = "VmTerminalApp"
            java.lang.String r2 = "installing.."
            android.util.Log.i(r1, r2)     // Catch:{ all -> 0x0010 }
            r1 = 1
            r4.isInstalling = r1     // Catch:{ all -> 0x0010 }
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0010 }
            monitor-exit(r0)
            android.content.Intent r0 = new android.content.Intent
            java.lang.Class<com.android.virtualization.terminal.InstallerService> r1 = com.android.virtualization.terminal.InstallerService.class
            r0.<init>(r4, r1)
            r4.startService(r0)
            android.app.Notification r0 = r4.notification
            r1 = 0
            if (r0 != 0) goto L_0x0034
            java.lang.String r0 = "notification"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
            r0 = r1
        L_0x0034:
            r2 = 1073741824(0x40000000, float:2.0)
            r3 = 1313(0x521, float:1.84E-42)
            r4.startForeground(r3, r0, r2)
            java.util.concurrent.ExecutorService r0 = r4.executorService
            if (r0 != 0) goto L_0x0045
            java.lang.String r0 = "executorService"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
            goto L_0x0046
        L_0x0045:
            r1 = r0
        L_0x0046:
            com.android.virtualization.terminal.InstallerService$requestInstall$2 r0 = new com.android.virtualization.terminal.InstallerService$requestInstall$2
            r0.<init>(r4, r5)
            r1.execute(r0)
            return
        L_0x004f:
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.InstallerService.requestInstall(boolean):void");
    }

    /* access modifiers changed from: private */
    public final boolean downloadFromSdcard() {
        ImageArchive fromSdCard = ImageArchive.Companion.fromSdCard();
        String path = fromSdCard.getPath();
        if (!Build.isDebuggable()) {
            Log.i("VmTerminalApp", "Non-debuggable build doesn't support installation from " + path);
            return false;
        } else if (!fromSdCard.exists()) {
            return false;
        } else {
            Log.i("VmTerminalApp", "trying to install " + path);
            try {
                fromSdCard.installTo(InstalledImage.Companion.getDefault(this).getInstallDir(), (Function) null);
                Log.i("VmTerminalApp", "image is installed from " + path);
                return true;
            } catch (IOException e) {
                Log.i("VmTerminalApp", "Failed to install " + path, e);
                return false;
            }
        }
    }

    /* access modifiers changed from: private */
    public final boolean checkForWifiOnly(boolean z) {
        boolean z2;
        if (!z) {
            return true;
        }
        synchronized (this.lock) {
            z2 = this.hasWifi;
        }
        return z2;
    }

    /* access modifiers changed from: private */
    public final boolean downloadFromUrl(boolean z) {
        if (!checkForWifiOnly(z)) {
            Log.e("VmTerminalApp", "Install isn't started because Wifi isn't available");
            notifyError(getString(2131689546));
            return false;
        }
        try {
            ImageArchive.Companion.fromInternet().installTo(InstalledImage.Companion.getDefault(this).getInstallDir(), new InstallerService$downloadFromUrl$1(this, z));
            return true;
        } catch (WifiCheckInputStream.NoWifiException unused) {
            Log.e("VmTerminalApp", "Install failed because of Wi-Fi is gone");
            notifyError(getString(2131689546));
            return false;
        } catch (UnknownHostException e) {
            String message = e.getMessage();
            Log.e("VmTerminalApp", "Install failed: " + message, e);
            notifyError(getString(2131689545));
            return false;
        } catch (SocketException e2) {
            String message2 = e2.getMessage();
            Log.e("VmTerminalApp", "Install failed: " + message2, e2);
            notifyError(getString(2131689545));
            return false;
        } catch (IOException e3) {
            Log.e("VmTerminalApp", "Installation failed", e3);
            notifyError(getString(2131689547));
            return false;
        }
    }

    private final void notifyError(String str) {
        IInstallProgressListener iInstallProgressListener;
        synchronized (this.lock) {
            iInstallProgressListener = this.listener;
            iInstallProgressListener.getClass();
            Unit unit = Unit.INSTANCE;
        }
        try {
            iInstallProgressListener.onError(str);
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: private */
    public final void notifyCompleted() {
        IInstallProgressListener iInstallProgressListener;
        synchronized (this.lock) {
            iInstallProgressListener = this.listener;
            iInstallProgressListener.getClass();
            Unit unit = Unit.INSTANCE;
        }
        try {
            iInstallProgressListener.onCompleted();
        } catch (Exception unused) {
        }
    }

    /* compiled from: InstallerService.kt */
    final class InstallerServiceImpl extends IInstallerService.Stub {
        private final WeakReference mService;

        public InstallerServiceImpl(InstallerService installerService) {
            this.mService = new WeakReference(installerService);
        }

        public final InstallerService ensureServiceConnected() {
            InstallerService installerService = (InstallerService) this.mService.get();
            if (installerService != null) {
                return installerService;
            }
            throw new RuntimeException("Internal error: Installer service is being accessed after destroyed");
        }

        public void requestInstall(boolean z) {
            InstallerService ensureServiceConnected = ensureServiceConnected();
            synchronized (ensureServiceConnected.lock) {
                ensureServiceConnected.requestInstall(z);
                Unit unit = Unit.INSTANCE;
            }
        }

        public void setProgressListener(IInstallProgressListener iInstallProgressListener) {
            iInstallProgressListener.getClass();
            InstallerService ensureServiceConnected = ensureServiceConnected();
            synchronized (ensureServiceConnected.lock) {
                ensureServiceConnected.listener = iInstallProgressListener;
                Unit unit = Unit.INSTANCE;
            }
        }

        public boolean isInstalling() {
            boolean access$isInstalling$p;
            InstallerService ensureServiceConnected = ensureServiceConnected();
            synchronized (ensureServiceConnected.lock) {
                access$isInstalling$p = ensureServiceConnected.isInstalling;
            }
            return access$isInstalling$p;
        }

        public boolean isInstalled() {
            boolean z;
            InstallerService ensureServiceConnected = ensureServiceConnected();
            synchronized (ensureServiceConnected.lock) {
                z = !ensureServiceConnected.isInstalling && InstalledImage.Companion.getDefault(ensureServiceConnected).isInstalled();
            }
            return z;
        }
    }

    /* compiled from: InstallerService.kt */
    final class WifiCheckInputStream extends InputStream {
        private final InputStream inputStream;
        private boolean isWifiOnly;
        final /* synthetic */ InstallerService this$0;

        public WifiCheckInputStream(InstallerService installerService, InputStream inputStream2) {
            inputStream2.getClass();
            this.this$0 = installerService;
            this.inputStream = inputStream2;
        }

        public final void setWifiOnly(boolean z) {
            this.isWifiOnly = z;
        }

        public int read(byte[] bArr, int i, int i2) {
            int i3 = 0;
            while (i2 > 0) {
                if (this.this$0.checkForWifiOnly(this.isWifiOnly)) {
                    int read = this.inputStream.read(bArr, i + i3, Math.min(1024, i2));
                    if (read <= 0) {
                        break;
                    }
                    i3 += read;
                    i2 -= read;
                } else {
                    throw new NoWifiException();
                }
            }
            return i3;
        }

        public int read() {
            if (this.this$0.checkForWifiOnly(this.isWifiOnly)) {
                return this.inputStream.read();
            }
            throw new NoWifiException();
        }

        /* compiled from: InstallerService.kt */
        public final class NoWifiException extends SocketException {
            public NoWifiException() {
            }
        }
    }

    /* compiled from: InstallerService.kt */
    final class MyNetworkCallback extends ConnectivityManager.NetworkCallback {
        public MyNetworkCallback() {
        }

        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            network.getClass();
            networkCapabilities.getClass();
            Object access$getLock$p = InstallerService.this.lock;
            InstallerService installerService = InstallerService.this;
            synchronized (access$getLock$p) {
                installerService.hasWifi = networkCapabilities.hasTransport(1);
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    /* compiled from: InstallerService.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
