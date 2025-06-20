package com.android.virtualization.terminal;

import io.grpc.ServerInterceptor;

/* compiled from: VmLauncherService.kt */
public final class VmLauncherService$startDebianServer$interceptor$1 implements ServerInterceptor {
    final /* synthetic */ String $ipAddress;

    VmLauncherService$startDebianServer$interceptor$1(String str) {
        this.$ipAddress = str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0014, code lost:
        r1 = r0.getAddress();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public io.grpc.ServerCall.Listener interceptCall(io.grpc.ServerCall r3, io.grpc.Metadata r4, io.grpc.ServerCallHandler r5) {
        /*
            r2 = this;
            r3.getClass()
            r5.getClass()
            io.grpc.Attributes r0 = r3.getAttributes()
            io.grpc.Attributes$Key r1 = io.grpc.Grpc.TRANSPORT_ATTR_REMOTE_ADDR
            java.lang.Object r0 = r0.get(r1)
            java.net.InetSocketAddress r0 = (java.net.InetSocketAddress) r0
            if (r0 == 0) goto L_0x001f
            java.net.InetAddress r1 = r0.getAddress()
            if (r1 == 0) goto L_0x001f
            java.lang.String r1 = r1.getHostAddress()
            goto L_0x0020
        L_0x001f:
            r1 = 0
        L_0x0020:
            java.lang.String r2 = r2.$ipAddress
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r2)
            if (r2 == 0) goto L_0x002d
            io.grpc.ServerCall$Listener r2 = r5.startCall(r3, r4)
            return r2
        L_0x002d:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "blocked grpc request from "
            r2.append(r4)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            java.lang.String r4 = "VmTerminalApp"
            android.util.Log.d(r4, r2)
            io.grpc.Status$Code r2 = io.grpc.Status.Code.PERMISSION_DENIED
            io.grpc.Status r2 = r2.toStatus()
            io.grpc.Metadata r4 = new io.grpc.Metadata
            r4.<init>()
            r3.close(r2, r4)
            com.android.virtualization.terminal.VmLauncherService$startDebianServer$interceptor$1$interceptCall$1 r2 = new com.android.virtualization.terminal.VmLauncherService$startDebianServer$interceptor$1$interceptCall$1
            r2.<init>()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.VmLauncherService$startDebianServer$interceptor$1.interceptCall(io.grpc.ServerCall, io.grpc.Metadata, io.grpc.ServerCallHandler):io.grpc.ServerCall$Listener");
    }
}
