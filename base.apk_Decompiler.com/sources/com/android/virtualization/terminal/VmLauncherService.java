package com.android.virtualization.terminal;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.os.StatFs;
import android.os.SystemProperties;
import android.system.virtualmachine.VirtualMachine;
import android.system.virtualmachine.VirtualMachineConfig;
import android.system.virtualmachine.VirtualMachineCustomImageConfig;
import android.system.virtualmachine.VirtualMachineException;
import android.util.Log;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.okhttp.OkHttpServerBuilder;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.StringsKt;

/* compiled from: VmLauncherService.kt */
public final class VmLauncherService extends Service {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private static final int VM_BOOT_TIMEOUT_SECONDS;
    private ExecutorService bgThreads;
    private DebianServiceImpl debianService;
    private Handler handler;
    private InstalledImage image;
    private ExecutorService mainWorkerThread;
    private PortNotifier portNotifier;
    /* access modifiers changed from: private */
    public Runner runner;
    /* access modifiers changed from: private */
    public Server server;

    /* compiled from: VmLauncherService.kt */
    public interface VmLauncherServiceCallback {
        void onTerminalAvailable(TerminalInfo terminalInfo);

        void onVmError();

        void onVmStart();

        void onVmStop();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        Context applicationContext = getApplicationContext();
        applicationContext.getClass();
        TerminalThreadFactory terminalThreadFactory = new TerminalThreadFactory(applicationContext);
        this.bgThreads = Executors.newCachedThreadPool(terminalThreadFactory);
        this.mainWorkerThread = Executors.newSingleThreadExecutor(terminalThreadFactory);
        this.image = InstalledImage.Companion.getDefault(this);
        this.handler = new Handler(Looper.getMainLooper());
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        intent.getClass();
        ResultReceiver resultReceiver = (ResultReceiver) intent.getParcelableExtra("android.intent.extra.RESULT_RECEIVER", ResultReceiver.class);
        String action = intent.getAction();
        if (action != null) {
            int hashCode = action.hashCode();
            ExecutorService executorService = null;
            if (hashCode != 714902196) {
                if (hashCode == 1190755936 && action.equals("com.android.virtualization.terminal.ACTION_SHUTDOWN_VM")) {
                    ExecutorService executorService2 = this.mainWorkerThread;
                    if (executorService2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mainWorkerThread");
                    } else {
                        executorService = executorService2;
                    }
                    executorService.execute(new VmLauncherService$onStartCommand$2(this, resultReceiver));
                    return 2;
                }
            } else if (action.equals("com.android.virtualization.terminal.ACTION_START_VM")) {
                Object parcelableExtra = intent.getParcelableExtra("com.android.virtualization.terminal.EXTRA_NOTIFICATION", Notification.class);
                parcelableExtra.getClass();
                Notification notification = (Notification) parcelableExtra;
                Object parcelableExtra2 = intent.getParcelableExtra("com.android.virtualization.terminal.EXTRA_DISPLAY_INFO", DisplayInfo.class);
                parcelableExtra2.getClass();
                DisplayInfo displayInfo = (DisplayInfo) parcelableExtra2;
                InstalledImage installedImage = this.image;
                if (installedImage == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("image");
                    installedImage = null;
                }
                long longExtra = intent.getLongExtra("com.android.virtualization.terminal.EXTRA_DISK_SIZE", installedImage.getApparentSize());
                ExecutorService executorService3 = this.mainWorkerThread;
                if (executorService3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mainWorkerThread");
                    executorService3 = null;
                }
                executorService3.execute(new VmLauncherService$onStartCommand$1(this, displayInfo, longExtra, resultReceiver));
                startForeground(hashCode(), notification);
                return 2;
            }
        }
        Log.e("VmTerminalApp", "Unknown command " + intent.getAction());
        stopSelf();
        return 2;
    }

    private final long calculateSparseDiskSize() {
        return InstalledImage.Companion.roundUp$packages__modules__Virtualization__android__TerminalApp__android_common_VmTerminalAppGoogle_apex10000_p__VmTerminalApp((new StatFs(getFilesDir().getAbsolutePath()).getTotalBytes() * ((long) 95)) / ((long) 100));
    }

    private final void convertToNonSparseDiskIfNecessary(InstalledImage installedImage) {
        try {
            long apparentSize = installedImage.getApparentSize();
            long physicalSize = installedImage.getPhysicalSize();
            Log.d("VmTerminalApp", "Current disk size: apparent=" + apparentSize + ", physical=" + physicalSize);
            if (apparentSize != calculateSparseDiskSize()) {
                if (physicalSize >= (((long) 90) * apparentSize) / ((long) 100)) {
                    return;
                }
            }
            Log.d("VmTerminalApp", "A sparse disk is detected. Shrink it to the minimum size.");
            long shrinkToMinimumSize = installedImage.shrinkToMinimumSize();
            Log.d("VmTerminalApp", "Shrink the disk image: " + apparentSize + " -> " + shrinkToMinimumSize);
        } catch (IOException e) {
            throw new RuntimeException("Failed to shrink rootfs disk", e);
        }
    }

    /* access modifiers changed from: private */
    public final void doStart(DisplayInfo displayInfo, long j, ResultReceiver resultReceiver) {
        InstalledImage installedImage = InstalledImage.Companion.getDefault(this);
        ConfigJson from = ConfigJson.Companion.from(this, installedImage.getConfigPath());
        VirtualMachineConfig.Builder configBuilder = from.toConfigBuilder(this);
        VirtualMachineCustomImageConfig.Builder customImageConfigBuilder = from.toCustomImageConfigBuilder(this);
        convertToNonSparseDiskIfNecessary(installedImage);
        installedImage.resize(j);
        customImageConfigBuilder.setAudioConfig(new VirtualMachineCustomImageConfig.AudioConfig.Builder().setUseSpeaker(true).setUseMicrophone(true).build());
        if (overrideConfigIfNecessary(customImageConfigBuilder, displayInfo)) {
            configBuilder.setCustomImageConfig(customImageConfigBuilder.build());
        }
        VirtualMachineConfig build = configBuilder.build();
        build.getClass();
        try {
            Runner create = Runner.Companion.create(this, build);
            this.runner = create;
            create.getClass();
            VirtualMachine vm = create.getVm();
            MemBalloonController memBalloonController = new MemBalloonController(this, vm);
            memBalloonController.start();
            Runner runner2 = this.runner;
            runner2.getClass();
            runner2.getExitStatus().thenAcceptAsync(new VmLauncherService$sam$java_util_function_Consumer$0(new VmLauncherService$$ExternalSyntheticLambda2(memBalloonController, resultReceiver, this)));
            Path path = getFileStreamPath(vm.getName() + ".log").toPath();
            Logger logger = Logger.INSTANCE;
            path.getClass();
            ExecutorService executorService = this.bgThreads;
            ExecutorService executorService2 = null;
            if (executorService == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bgThreads");
                executorService = null;
            }
            logger.setup(vm, path, executorService);
            resultReceiver.send(0, (Bundle) null);
            this.portNotifier = new PortNotifier(this);
            CompletableFuture terminalServiceInfo = getTerminalServiceInfo();
            VmLauncherService$sam$java_util_function_Consumer$0 vmLauncherService$sam$java_util_function_Consumer$0 = new VmLauncherService$sam$java_util_function_Consumer$0(new VmLauncherService$$ExternalSyntheticLambda3(resultReceiver, this));
            ExecutorService executorService3 = this.bgThreads;
            if (executorService3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bgThreads");
                executorService3 = null;
            }
            CompletableFuture<Void> thenAcceptAsync = terminalServiceInfo.thenAcceptAsync(vmLauncherService$sam$java_util_function_Consumer$0, executorService3);
            VmLauncherService$doStart$3 vmLauncherService$doStart$3 = new VmLauncherService$doStart$3(resultReceiver, this);
            ExecutorService executorService4 = this.bgThreads;
            if (executorService4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bgThreads");
            } else {
                executorService2 = executorService4;
            }
            thenAcceptAsync.exceptionallyAsync(vmLauncherService$doStart$3, executorService2);
        } catch (VirtualMachineException e) {
            throw new RuntimeException("cannot create runner", e);
        }
    }

    /* access modifiers changed from: private */
    public static final Unit doStart$lambda$0(MemBalloonController memBalloonController, ResultReceiver resultReceiver, VmLauncherService vmLauncherService, boolean z) {
        memBalloonController.stop();
        resultReceiver.send(z ? 1 : 2, (Bundle) null);
        vmLauncherService.stopSelf();
        return Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public static final Unit doStart$lambda$1(ResultReceiver resultReceiver, VmLauncherService vmLauncherService, NsdServiceInfo nsdServiceInfo) {
        String hostAddress = ((InetAddress) nsdServiceInfo.getHostAddresses().get(0)).getHostAddress();
        int port = nsdServiceInfo.getPort();
        Bundle bundle = new Bundle();
        bundle.putString("address", hostAddress);
        bundle.putInt("port", port);
        resultReceiver.send(3, bundle);
        vmLauncherService.startDebianServer(hostAddress);
        return Unit.INSTANCE;
    }

    private final CompletableFuture getTerminalServiceInfo() {
        Context applicationContext = getApplicationContext();
        applicationContext.getClass();
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor(new TerminalThreadFactory(applicationContext));
        NsdManager nsdManager = (NsdManager) getSystemService(NsdManager.class);
        NsdServiceInfo nsdServiceInfo = new NsdServiceInfo();
        nsdServiceInfo.setServiceType("_http._tcp");
        nsdServiceInfo.setServiceName("ttyd");
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = new CompletableFuture();
        nsdManager.registerServiceInfoCallback(nsdServiceInfo, newSingleThreadExecutor, new VmLauncherService$getTerminalServiceInfo$1(newSingleThreadExecutor, nsdManager, ref$ObjectRef));
        ((CompletableFuture) ref$ObjectRef.element).orTimeout((long) VM_BOOT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        return (CompletableFuture) ref$ObjectRef.element;
    }

    private final Notification createNotificationForTerminalClose() {
        Intent intent = new Intent();
        intent.setClass(this, VmLauncherService.class);
        intent.setAction("com.android.virtualization.terminal.ACTION_SHUTDOWN_VM");
        PendingIntent service = PendingIntent.getService(this, 0, intent, 201326592);
        Icon createWithResource = Icon.createWithResource(getResources(), 2131165338);
        createWithResource.getClass();
        String string = getResources().getString(2131689676);
        Notification build = new Notification.Builder(this, "system_events").setSmallIcon(2131165347).setContentTitle(getResources().getString(2131689674)).setOngoing(true).setSilent(true).addAction(new Notification.Action.Builder(createWithResource, string, service).build()).build();
        build.getClass();
        return build;
    }

    private final void runOnMainThread(Runnable runnable) {
        Handler handler2 = this.handler;
        handler2.getClass();
        handler2.post(runnable);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x00bd A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x00ad  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean overrideConfigIfNecessary(android.system.virtualmachine.VirtualMachineCustomImageConfig.Builder r5, com.android.virtualization.terminal.DisplayInfo r6) {
        /*
            r4 = this;
            com.android.virtualization.terminal.ImageArchive$Companion r6 = com.android.virtualization.terminal.ImageArchive.Companion
            java.nio.file.Path r0 = r6.getSdcardPathForTesting()
            java.lang.String r1 = "virglrenderer"
            java.nio.file.Path r0 = r0.resolve(r1)
            r2 = 0
            java.nio.file.LinkOption[] r3 = new java.nio.file.LinkOption[r2]
            boolean r0 = java.nio.file.Files.exists(r0, r3)
            r3 = 1
            if (r0 == 0) goto L_0x0052
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder r6 = new android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder
            r6.<init>()
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder r6 = r6.setBackend(r1)
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder r6 = r6.setRendererUseEgl(r0)
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder r6 = r6.setRendererUseGles(r0)
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder r6 = r6.setRendererUseGlx(r1)
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder r6 = r6.setRendererUseSurfaceless(r0)
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder r6 = r6.setRendererUseVulkan(r1)
            java.lang.String r0 = "virgl2"
            java.lang.String[] r0 = new java.lang.String[]{r0}
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder r6 = r6.setContextTypes(r0)
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig r6 = r6.build()
            r5.setGpuConfig(r6)
            com.android.virtualization.terminal.VmLauncherService$overrideConfigIfNecessary$1 r6 = new com.android.virtualization.terminal.VmLauncherService$overrideConfigIfNecessary$1
            r6.<init>(r4)
            r4.runOnMainThread(r6)
        L_0x0050:
            r2 = r3
            goto L_0x00a1
        L_0x0052:
            java.nio.file.Path r6 = r6.getSdcardPathForTesting()
            java.lang.String r0 = "gfxstream"
            java.nio.file.Path r6 = r6.resolve(r0)
            java.nio.file.LinkOption[] r1 = new java.nio.file.LinkOption[r2]
            boolean r6 = java.nio.file.Files.exists(r6, r1)
            if (r6 == 0) goto L_0x00a1
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder r6 = new android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder
            r6.<init>()
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder r6 = r6.setBackend(r0)
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder r6 = r6.setRendererUseEgl(r0)
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder r6 = r6.setRendererUseGles(r0)
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder r6 = r6.setRendererUseGlx(r0)
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder r6 = r6.setRendererUseSurfaceless(r0)
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder r6 = r6.setRendererUseVulkan(r0)
            java.lang.String r0 = "gfxstream-vulkan"
            java.lang.String r1 = "gfxstream-composer"
            java.lang.String[] r0 = new java.lang.String[]{r0, r1}
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig$Builder r6 = r6.setContextTypes(r0)
            android.system.virtualmachine.VirtualMachineCustomImageConfig$GpuConfig r6 = r6.build()
            r5.setGpuConfig(r6)
            com.android.virtualization.terminal.VmLauncherService$overrideConfigIfNecessary$2 r6 = new com.android.virtualization.terminal.VmLauncherService$overrideConfigIfNecessary$2
            r6.<init>(r4)
            r4.runOnMainThread(r6)
            goto L_0x0050
        L_0x00a1:
            com.android.virtualization.terminal.InstalledImage$Companion r6 = com.android.virtualization.terminal.InstalledImage.Companion
            com.android.virtualization.terminal.InstalledImage r4 = r6.getDefault(r4)
            boolean r6 = r4.hasBackup()
            if (r6 == 0) goto L_0x00bd
            java.nio.file.Path r4 = r4.getBackupFile()
            java.lang.String r4 = r4.toString()
            android.system.virtualmachine.VirtualMachineCustomImageConfig$Disk r4 = android.system.virtualmachine.VirtualMachineCustomImageConfig.Disk.RWDisk(r4)
            r5.addDisk(r4)
            return r3
        L_0x00bd:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.VmLauncherService.overrideConfigIfNecessary(android.system.virtualmachine.VirtualMachineCustomImageConfig$Builder, com.android.virtualization.terminal.DisplayInfo):boolean");
    }

    private final void startDebianServer(String str) {
        VmLauncherService$startDebianServer$interceptor$1 vmLauncherService$startDebianServer$interceptor$1 = new VmLauncherService$startDebianServer$interceptor$1(str);
        try {
            this.debianService = new DebianServiceImpl(this);
            this.server = ((OkHttpServerBuilder) ((OkHttpServerBuilder) OkHttpServerBuilder.forPort(0, InsecureServerCredentials.create()).intercept(vmLauncherService$startDebianServer$interceptor$1)).addService(this.debianService)).build().start();
            ExecutorService executorService = this.bgThreads;
            if (executorService == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bgThreads");
                executorService = null;
            }
            executorService.execute(new VmLauncherService$startDebianServer$1(this));
        } catch (IOException e) {
            Log.d("VmTerminalApp", "grpc server error", e);
        }
    }

    /* access modifiers changed from: private */
    public final void doShutdown(ResultReceiver resultReceiver) {
        VirtualMachine vm;
        CompletableFuture exitStatus;
        Runner runner2 = this.runner;
        if (!(runner2 == null || (exitStatus = runner2.getExitStatus()) == null)) {
            exitStatus.thenAcceptAsync(new VmLauncherService$sam$java_util_function_Consumer$0(new VmLauncherService$$ExternalSyntheticLambda0(resultReceiver)));
        }
        DebianServiceImpl debianServiceImpl = this.debianService;
        if (debianServiceImpl != null) {
            debianServiceImpl.getClass();
            if (debianServiceImpl.shutdownDebian()) {
                ((NotificationManager) getSystemService(NotificationManager.class)).notify(hashCode(), createNotificationForTerminalClose());
                Runner runner3 = this.runner;
                if (runner3 != null) {
                    runner3.getExitStatus().thenAcceptAsync(new VmLauncherService$sam$java_util_function_Consumer$0(new VmLauncherService$$ExternalSyntheticLambda1())).orTimeout(3, TimeUnit.SECONDS).exceptionally(new VmLauncherService$doShutdown$2$2(runner3));
                }
                this.runner = null;
                return;
            }
        }
        Runner runner4 = this.runner;
        if (runner4 != null && (vm = runner4.getVm()) != null) {
            vm.stop();
        }
    }

    /* access modifiers changed from: private */
    public static final Unit doShutdown$lambda$2(ResultReceiver resultReceiver, boolean z) {
        if (resultReceiver != null) {
            resultReceiver.send(z ? 1 : 2, (Bundle) null);
        }
        return Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public static final Unit doShutdown$lambda$4$lambda$3(Boolean bool) {
        return Unit.INSTANCE;
    }

    private final void stopDebianServer() {
        DebianServiceImpl debianServiceImpl = this.debianService;
        if (debianServiceImpl != null) {
            debianServiceImpl.killForwarderHost();
        }
        DebianServiceImpl debianServiceImpl2 = this.debianService;
        if (debianServiceImpl2 != null) {
            debianServiceImpl2.closeStorageBalloonRequestQueue();
        }
        Server server2 = this.server;
        if (server2 != null) {
            server2.shutdown();
        }
    }

    public void onDestroy() {
        ExecutorService executorService = null;
        this.handler = null;
        ExecutorService executorService2 = this.mainWorkerThread;
        if (executorService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mainWorkerThread");
            executorService2 = null;
        }
        executorService2.execute(new VmLauncherService$onDestroy$1(this));
        PortNotifier portNotifier2 = this.portNotifier;
        if (portNotifier2 != null) {
            portNotifier2.stop();
        }
        ((NotificationManager) getSystemService(NotificationManager.class)).cancelAll();
        stopDebianServer();
        ExecutorService executorService3 = this.bgThreads;
        if (executorService3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bgThreads");
            executorService3 = null;
        }
        executorService3.shutdownNow();
        ExecutorService executorService4 = this.mainWorkerThread;
        if (executorService4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mainWorkerThread");
        } else {
            executorService = executorService4;
        }
        executorService.shutdown();
        stopForeground(1);
        super.onDestroy();
    }

    /* compiled from: VmLauncherService.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        private final Intent prepareIntent(Context context, VmLauncherServiceCallback vmLauncherServiceCallback) {
            Intent intent = new Intent(context.getApplicationContext(), VmLauncherService.class);
            Looper myLooper = Looper.myLooper();
            myLooper.getClass();
            VmLauncherService$Companion$prepareIntent$resultReceiver$1 vmLauncherService$Companion$prepareIntent$resultReceiver$1 = new VmLauncherService$Companion$prepareIntent$resultReceiver$1(vmLauncherServiceCallback, new Handler(myLooper));
            Parcel obtain = Parcel.obtain();
            obtain.getClass();
            vmLauncherService$Companion$prepareIntent$resultReceiver$1.writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            Object createFromParcel = ResultReceiver.CREATOR.createFromParcel(obtain);
            ResultReceiver resultReceiver = (ResultReceiver) createFromParcel;
            obtain.recycle();
            Unit unit = Unit.INSTANCE;
            intent.putExtra("android.intent.extra.RESULT_RECEIVER", (Parcelable) createFromParcel);
            return intent;
        }

        public final Intent getIntentForStart(Context context, VmLauncherServiceCallback vmLauncherServiceCallback, Notification notification, DisplayInfo displayInfo, Long l) {
            context.getClass();
            vmLauncherServiceCallback.getClass();
            displayInfo.getClass();
            Intent prepareIntent = prepareIntent(context, vmLauncherServiceCallback);
            prepareIntent.setAction("com.android.virtualization.terminal.ACTION_START_VM");
            prepareIntent.putExtra("com.android.virtualization.terminal.EXTRA_NOTIFICATION", notification);
            prepareIntent.putExtra("com.android.virtualization.terminal.EXTRA_DISPLAY_INFO", displayInfo);
            if (l != null) {
                prepareIntent.putExtra("com.android.virtualization.terminal.EXTRA_DISK_SIZE", l.longValue());
            }
            return prepareIntent;
        }

        public final Intent getIntentForShutdown(Context context, VmLauncherServiceCallback vmLauncherServiceCallback) {
            context.getClass();
            vmLauncherServiceCallback.getClass();
            Intent prepareIntent = prepareIntent(context, vmLauncherServiceCallback);
            prepareIntent.setAction("com.android.virtualization.terminal.ACTION_SHUTDOWN_VM");
            return prepareIntent;
        }
    }

    static {
        String str = SystemProperties.get("ro.product.vendor.device", "");
        str.getClass();
        VM_BOOT_TIMEOUT_SECONDS = (StringsKt.startsWith$default(str, "vsoc_", false, 2, (Object) null) || StringsKt.startsWith$default(str, "emu64", false, 2, (Object) null)) ? 180 : 30;
    }
}
