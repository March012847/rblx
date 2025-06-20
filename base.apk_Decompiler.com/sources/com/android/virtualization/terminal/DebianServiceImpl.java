package com.android.virtualization.terminal;

import android.content.Context;
import android.util.Log;
import androidx.annotation.Keep;
import com.android.virtualization.terminal.PortsStateManager;
import com.android.virtualization.terminal.proto.DebianServiceGrpc;
import com.android.virtualization.terminal.proto.ForwardingRequestItem;
import com.android.virtualization.terminal.proto.QueueOpeningRequest;
import com.android.virtualization.terminal.proto.ReportVmActivePortsRequest;
import com.android.virtualization.terminal.proto.ReportVmActivePortsResponse;
import com.android.virtualization.terminal.proto.ShutdownQueueOpeningRequest;
import com.android.virtualization.terminal.proto.StorageBalloonQueueOpeningRequest;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: DebianServiceImpl.kt */
public final class DebianServiceImpl extends DebianServiceGrpc.DebianServiceImplBase {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final Object mLock = new Object();
    private PortsStateManager.Listener portsStateListener;
    private final PortsStateManager portsStateManager;
    /* access modifiers changed from: private */
    public Runnable shutdownRunnable;
    private StorageBalloonCallback storageBalloonCallback;

    /* compiled from: DebianServiceImpl.kt */
    abstract class StorageBalloonCallback {
        public abstract void closeConnection();
    }

    /* access modifiers changed from: private */
    public static final native void runForwarderHost(int i, ForwarderHostCallback forwarderHostCallback);

    /* access modifiers changed from: private */
    public static final native void terminateForwarderHost();

    /* access modifiers changed from: private */
    public static final native void updateListeningPorts(int[] iArr);

    public void openStorageBalloonRequestQueue(StorageBalloonQueueOpeningRequest storageBalloonQueueOpeningRequest, StreamObserver streamObserver) {
        streamObserver.getClass();
    }

    public DebianServiceImpl(Context context) {
        context.getClass();
        this.portsStateManager = PortsStateManager.Companion.getInstance(context);
    }

    public void reportVmActivePorts(ReportVmActivePortsRequest reportVmActivePortsRequest, StreamObserver streamObserver) {
        reportVmActivePortsRequest.getClass();
        streamObserver.getClass();
        PortsStateManager portsStateManager2 = this.portsStateManager;
        List portsList = reportVmActivePortsRequest.getPortsList();
        portsList.getClass();
        portsStateManager2.updateActivePorts(portsList);
        Set activePorts = this.portsStateManager.getActivePorts();
        Log.d("VmTerminalApp", "reportVmActivePorts: " + activePorts);
        streamObserver.onNext((ReportVmActivePortsResponse) ReportVmActivePortsResponse.newBuilder().setSuccess(true).build());
        streamObserver.onCompleted();
    }

    public void openForwardingRequestQueue(QueueOpeningRequest queueOpeningRequest, StreamObserver streamObserver) {
        queueOpeningRequest.getClass();
        streamObserver.getClass();
        Log.d("VmTerminalApp", "OpenForwardingRequestQueue");
        DebianServiceImpl$openForwardingRequestQueue$1 debianServiceImpl$openForwardingRequestQueue$1 = new DebianServiceImpl$openForwardingRequestQueue$1(this);
        this.portsStateListener = debianServiceImpl$openForwardingRequestQueue$1;
        this.portsStateManager.registerListener(debianServiceImpl$openForwardingRequestQueue$1);
        updateListeningPorts();
        Companion.runForwarderHost(queueOpeningRequest.getCid(), new ForwarderHostCallback(streamObserver));
        streamObserver.onCompleted();
    }

    public final boolean shutdownDebian() {
        Runnable runnable = this.shutdownRunnable;
        if (runnable == null) {
            Log.d("VmTerminalApp", "mShutdownRunnable is not ready.");
            return false;
        }
        runnable.getClass();
        runnable.run();
        return true;
    }

    public void openShutdownRequestQueue(ShutdownQueueOpeningRequest shutdownQueueOpeningRequest, StreamObserver streamObserver) {
        streamObserver.getClass();
        ServerCallStreamObserver serverCallStreamObserver = (ServerCallStreamObserver) streamObserver;
        serverCallStreamObserver.setOnCancelHandler(new DebianServiceImpl$openShutdownRequestQueue$1(this));
        Log.d("VmTerminalApp", "openShutdownRequestQueue");
        this.shutdownRunnable = new DebianServiceImpl$openShutdownRequestQueue$2(serverCallStreamObserver, streamObserver, this);
    }

    public final void closeStorageBalloonRequestQueue() {
        Log.d("VmTerminalApp", "Stopping storage balloon queue");
        synchronized (this.mLock) {
            Unit unit = Unit.INSTANCE;
        }
    }

    @Keep
    /* compiled from: DebianServiceImpl.kt */
    final class ForwarderHostCallback {
        private final StreamObserver responseObserver;

        public ForwarderHostCallback(StreamObserver streamObserver) {
            streamObserver.getClass();
            this.responseObserver = streamObserver;
        }

        public final void onForwardingRequestReceived(int i, int i2) {
            this.responseObserver.onNext((ForwardingRequestItem) ForwardingRequestItem.newBuilder().setGuestTcpPort(i).setVsockPort(i2).build());
        }
    }

    public final void killForwarderHost() {
        Log.d("VmTerminalApp", "Stopping port forwarding");
        PortsStateManager.Listener listener = this.portsStateListener;
        if (listener != null) {
            PortsStateManager portsStateManager2 = this.portsStateManager;
            listener.getClass();
            portsStateManager2.unregisterListener(listener);
            this.portsStateListener = null;
        }
        Companion.terminateForwarderHost();
    }

    /* access modifiers changed from: private */
    public final void updateListeningPorts() {
        Set activePorts = this.portsStateManager.getActivePorts();
        Set enabledPorts = this.portsStateManager.getEnabledPorts();
        Companion companion = Companion;
        ArrayList arrayList = new ArrayList();
        for (Object next : activePorts) {
            if (enabledPorts.contains(Integer.valueOf(((Number) next).intValue()))) {
                arrayList.add(next);
            }
        }
        companion.updateListeningPorts(CollectionsKt.toIntArray(arrayList));
    }

    /* compiled from: DebianServiceImpl.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* access modifiers changed from: private */
        public final void runForwarderHost(int i, ForwarderHostCallback forwarderHostCallback) {
            DebianServiceImpl.runForwarderHost(i, forwarderHostCallback);
        }

        /* access modifiers changed from: private */
        public final void terminateForwarderHost() {
            DebianServiceImpl.terminateForwarderHost();
        }

        /* access modifiers changed from: private */
        public final void updateListeningPorts(int[] iArr) {
            DebianServiceImpl.updateListeningPorts(iArr);
        }

        private Companion() {
        }
    }

    static {
        System.loadLibrary("forwarder_host_jni");
    }
}
