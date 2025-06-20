package com.android.virtualization.terminal;

import com.android.virtualization.terminal.proto.ShutdownRequestItem;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;

/* compiled from: DebianServiceImpl.kt */
final class DebianServiceImpl$openShutdownRequestQueue$2 implements Runnable {
    final /* synthetic */ StreamObserver $responseObserver;
    final /* synthetic */ ServerCallStreamObserver $serverCallStreamObserver;
    final /* synthetic */ DebianServiceImpl this$0;

    DebianServiceImpl$openShutdownRequestQueue$2(ServerCallStreamObserver serverCallStreamObserver, StreamObserver streamObserver, DebianServiceImpl debianServiceImpl) {
        this.$serverCallStreamObserver = serverCallStreamObserver;
        this.$responseObserver = streamObserver;
        this.this$0 = debianServiceImpl;
    }

    public final void run() {
        if (!this.$serverCallStreamObserver.isCancelled()) {
            ((ServerCallStreamObserver) this.$responseObserver).onNext(ShutdownRequestItem.newBuilder().build());
            ((ServerCallStreamObserver) this.$responseObserver).onCompleted();
            this.this$0.shutdownRunnable = null;
        }
    }
}
