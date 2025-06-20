package com.android.virtualization.terminal.proto;

import io.grpc.BindableService;
import io.grpc.MethodDescriptor;
import io.grpc.ServerServiceDefinition;
import io.grpc.ServiceDescriptor;
import io.grpc.protobuf.lite.ProtoLiteUtils;
import io.grpc.stub.ServerCalls;
import io.grpc.stub.StreamObserver;

public abstract class DebianServiceGrpc {
    private static volatile MethodDescriptor getOpenForwardingRequestQueueMethod;
    private static volatile MethodDescriptor getOpenShutdownRequestQueueMethod;
    private static volatile MethodDescriptor getOpenStorageBalloonRequestQueueMethod;
    private static volatile MethodDescriptor getReportVmActivePortsMethod;
    private static volatile ServiceDescriptor serviceDescriptor;

    public interface AsyncService {
        void openForwardingRequestQueue(QueueOpeningRequest queueOpeningRequest, StreamObserver streamObserver);

        void openShutdownRequestQueue(ShutdownQueueOpeningRequest shutdownQueueOpeningRequest, StreamObserver streamObserver);

        void openStorageBalloonRequestQueue(StorageBalloonQueueOpeningRequest storageBalloonQueueOpeningRequest, StreamObserver streamObserver);

        void reportVmActivePorts(ReportVmActivePortsRequest reportVmActivePortsRequest, StreamObserver streamObserver);
    }

    public static MethodDescriptor getReportVmActivePortsMethod() {
        MethodDescriptor methodDescriptor;
        MethodDescriptor methodDescriptor2 = getReportVmActivePortsMethod;
        if (methodDescriptor2 != null) {
            return methodDescriptor2;
        }
        synchronized (DebianServiceGrpc.class) {
            try {
                methodDescriptor = getReportVmActivePortsMethod;
                if (methodDescriptor == null) {
                    methodDescriptor = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName("com.android.virtualization.terminal.proto.DebianService", "ReportVmActivePorts")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(ReportVmActivePortsRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(ReportVmActivePortsResponse.getDefaultInstance())).build();
                    getReportVmActivePortsMethod = methodDescriptor;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return methodDescriptor;
    }

    public static MethodDescriptor getOpenForwardingRequestQueueMethod() {
        MethodDescriptor methodDescriptor;
        MethodDescriptor methodDescriptor2 = getOpenForwardingRequestQueueMethod;
        if (methodDescriptor2 != null) {
            return methodDescriptor2;
        }
        synchronized (DebianServiceGrpc.class) {
            try {
                methodDescriptor = getOpenForwardingRequestQueueMethod;
                if (methodDescriptor == null) {
                    methodDescriptor = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.SERVER_STREAMING).setFullMethodName(MethodDescriptor.generateFullMethodName("com.android.virtualization.terminal.proto.DebianService", "OpenForwardingRequestQueue")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(QueueOpeningRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(ForwardingRequestItem.getDefaultInstance())).build();
                    getOpenForwardingRequestQueueMethod = methodDescriptor;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return methodDescriptor;
    }

    public static MethodDescriptor getOpenShutdownRequestQueueMethod() {
        MethodDescriptor methodDescriptor;
        MethodDescriptor methodDescriptor2 = getOpenShutdownRequestQueueMethod;
        if (methodDescriptor2 != null) {
            return methodDescriptor2;
        }
        synchronized (DebianServiceGrpc.class) {
            try {
                methodDescriptor = getOpenShutdownRequestQueueMethod;
                if (methodDescriptor == null) {
                    methodDescriptor = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.SERVER_STREAMING).setFullMethodName(MethodDescriptor.generateFullMethodName("com.android.virtualization.terminal.proto.DebianService", "OpenShutdownRequestQueue")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(ShutdownQueueOpeningRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(ShutdownRequestItem.getDefaultInstance())).build();
                    getOpenShutdownRequestQueueMethod = methodDescriptor;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return methodDescriptor;
    }

    public static MethodDescriptor getOpenStorageBalloonRequestQueueMethod() {
        MethodDescriptor methodDescriptor;
        MethodDescriptor methodDescriptor2 = getOpenStorageBalloonRequestQueueMethod;
        if (methodDescriptor2 != null) {
            return methodDescriptor2;
        }
        synchronized (DebianServiceGrpc.class) {
            try {
                methodDescriptor = getOpenStorageBalloonRequestQueueMethod;
                if (methodDescriptor == null) {
                    methodDescriptor = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.SERVER_STREAMING).setFullMethodName(MethodDescriptor.generateFullMethodName("com.android.virtualization.terminal.proto.DebianService", "OpenStorageBalloonRequestQueue")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(StorageBalloonQueueOpeningRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(StorageBalloonRequestItem.getDefaultInstance())).build();
                    getOpenStorageBalloonRequestQueueMethod = methodDescriptor;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return methodDescriptor;
    }

    public abstract class DebianServiceImplBase implements BindableService, AsyncService {
        public final ServerServiceDefinition bindService() {
            return DebianServiceGrpc.bindService(this);
        }
    }

    final class MethodHandlers implements ServerCalls.UnaryMethod, ServerCalls.ServerStreamingMethod {
        private final int methodId;
        private final AsyncService serviceImpl;

        MethodHandlers(AsyncService asyncService, int i) {
            this.serviceImpl = asyncService;
            this.methodId = i;
        }

        public void invoke(Object obj, StreamObserver streamObserver) {
            int i = this.methodId;
            if (i == 0) {
                this.serviceImpl.reportVmActivePorts((ReportVmActivePortsRequest) obj, streamObserver);
            } else if (i == 1) {
                this.serviceImpl.openForwardingRequestQueue((QueueOpeningRequest) obj, streamObserver);
            } else if (i == 2) {
                this.serviceImpl.openShutdownRequestQueue((ShutdownQueueOpeningRequest) obj, streamObserver);
            } else if (i == 3) {
                this.serviceImpl.openStorageBalloonRequestQueue((StorageBalloonQueueOpeningRequest) obj, streamObserver);
            } else {
                throw new AssertionError();
            }
        }
    }

    public static final ServerServiceDefinition bindService(AsyncService asyncService) {
        return ServerServiceDefinition.builder(getServiceDescriptor()).addMethod(getReportVmActivePortsMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(asyncService, 0))).addMethod(getOpenForwardingRequestQueueMethod(), ServerCalls.asyncServerStreamingCall(new MethodHandlers(asyncService, 1))).addMethod(getOpenShutdownRequestQueueMethod(), ServerCalls.asyncServerStreamingCall(new MethodHandlers(asyncService, 2))).addMethod(getOpenStorageBalloonRequestQueueMethod(), ServerCalls.asyncServerStreamingCall(new MethodHandlers(asyncService, 3))).build();
    }

    public static ServiceDescriptor getServiceDescriptor() {
        ServiceDescriptor serviceDescriptor2;
        ServiceDescriptor serviceDescriptor3 = serviceDescriptor;
        if (serviceDescriptor3 != null) {
            return serviceDescriptor3;
        }
        synchronized (DebianServiceGrpc.class) {
            try {
                serviceDescriptor2 = serviceDescriptor;
                if (serviceDescriptor2 == null) {
                    serviceDescriptor2 = ServiceDescriptor.newBuilder("com.android.virtualization.terminal.proto.DebianService").addMethod(getReportVmActivePortsMethod()).addMethod(getOpenForwardingRequestQueueMethod()).addMethod(getOpenShutdownRequestQueueMethod()).addMethod(getOpenStorageBalloonRequestQueueMethod()).build();
                    serviceDescriptor = serviceDescriptor2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return serviceDescriptor2;
    }
}
