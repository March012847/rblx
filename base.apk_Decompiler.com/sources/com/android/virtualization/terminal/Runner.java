package com.android.virtualization.terminal;

import android.content.Context;
import android.system.virtualmachine.VirtualMachine;
import android.system.virtualmachine.VirtualMachineCallback;
import android.system.virtualmachine.VirtualMachineConfig;
import android.system.virtualmachine.VirtualMachineCustomImageConfig;
import android.system.virtualmachine.VirtualMachineManager;
import android.util.Log;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: Runner.kt */
public final class Runner {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final CompletableFuture exitStatus;
    private final VirtualMachine vm;

    public /* synthetic */ Runner(VirtualMachine virtualMachine, Callback callback, DefaultConstructorMarker defaultConstructorMarker) {
        this(virtualMachine, callback);
    }

    private Runner(VirtualMachine virtualMachine, Callback callback) {
        this.vm = virtualMachine;
        this.exitStatus = callback.getFinishedSuccessfully();
    }

    public final VirtualMachine getVm() {
        return this.vm;
    }

    public final CompletableFuture getExitStatus() {
        return this.exitStatus;
    }

    /* compiled from: Runner.kt */
    final class Callback implements VirtualMachineCallback {
        private final CompletableFuture finishedSuccessfully = new CompletableFuture();

        public void onPayloadFinished(VirtualMachine virtualMachine, int i) {
            virtualMachine.getClass();
        }

        public void onPayloadReady(VirtualMachine virtualMachine) {
            virtualMachine.getClass();
        }

        public void onPayloadStarted(VirtualMachine virtualMachine) {
            virtualMachine.getClass();
        }

        public final CompletableFuture getFinishedSuccessfully() {
            return this.finishedSuccessfully;
        }

        public void onError(VirtualMachine virtualMachine, int i, String str) {
            virtualMachine.getClass();
            str.getClass();
            Log.e("VmTerminalApp", "Error from VM. code: " + i + " (" + str + ")");
            this.finishedSuccessfully.complete(Boolean.FALSE);
        }

        public void onStopped(VirtualMachine virtualMachine, int i) {
            virtualMachine.getClass();
            Log.d("VmTerminalApp", "VM stopped. Reason: " + i);
            this.finishedSuccessfully.complete(Boolean.TRUE);
        }
    }

    /* compiled from: Runner.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Runner create(Context context, VirtualMachineConfig virtualMachineConfig) {
            context.getClass();
            virtualMachineConfig.getClass();
            VirtualMachineManager virtualMachineManager = (VirtualMachineManager) context.getApplicationContext().getSystemService(VirtualMachineManager.class);
            VirtualMachineCustomImageConfig customImageConfig = virtualMachineConfig.getCustomImageConfig();
            if (customImageConfig != null) {
                String name = customImageConfig.getName();
                if (!(name == null || name.length() == 0)) {
                    VirtualMachine virtualMachine = virtualMachineManager.get(name);
                    if (virtualMachine != null) {
                        if (virtualMachine.getStatus() != 0) {
                            Callback callback = new Callback();
                            Log.i("VmTerminalApp", "Virtual machine (" + name + ") is running. Waiting for it to stop.");
                            virtualMachine.setCallback(ForkJoinPool.commonPool(), callback);
                            callback.getFinishedSuccessfully().join();
                        }
                        virtualMachineManager.delete(name);
                    }
                    VirtualMachine create = virtualMachineManager.create(name, virtualMachineConfig);
                    create.getClass();
                    Callback callback2 = new Callback();
                    create.setCallback(ForkJoinPool.commonPool(), callback2);
                    create.run();
                    return new Runner(create, callback2, (DefaultConstructorMarker) null);
                }
                throw new IllegalArgumentException("Virtual machine's name is missing in the config");
            }
            throw new IllegalArgumentException("CustomImageConfig is missing");
        }
    }
}
