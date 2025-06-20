package com.android.virtualization.terminal;

import android.crosvm.ICrosvmAndroidDisplayService;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.system.virtualizationservice_internal.IVirtualizationServiceInternal;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;
import libcore.io.IoBridge;

/* compiled from: DisplayProvider.kt */
public final class DisplayProvider {
    private CursorHandler cursorHandler;
    /* access modifiers changed from: private */
    public final SurfaceView cursorView;
    /* access modifiers changed from: private */
    public final SurfaceView mainView;
    private final Lazy virtService$delegate = LazyKt.lazy(new DisplayProvider$$ExternalSyntheticLambda0());

    public DisplayProvider(SurfaceView surfaceView, SurfaceView surfaceView2) {
        surfaceView.getClass();
        surfaceView2.getClass();
        this.mainView = surfaceView;
        this.cursorView = surfaceView2;
        surfaceView.setSurfaceLifecycle(2);
        surfaceView.getHolder().addCallback(new Callback(this, SurfaceKind.MAIN));
        surfaceView2.setSurfaceLifecycle(2);
        surfaceView2.getHolder().addCallback(new Callback(this, SurfaceKind.CURSOR));
        surfaceView2.getHolder().setFormat(1);
        surfaceView2.setZOrderMediaOverlay(true);
    }

    private final IVirtualizationServiceInternal getVirtService() {
        Object value = this.virtService$delegate.getValue();
        value.getClass();
        return (IVirtualizationServiceInternal) value;
    }

    /* access modifiers changed from: private */
    public static final IVirtualizationServiceInternal virtService_delegate$lambda$0() {
        return IVirtualizationServiceInternal.Stub.asInterface(ServiceManager.waitForService("android.system.virtualizationservice"));
    }

    public final void notifyDisplayIsGoingToInvisible() {
        try {
            getDisplayService().saveFrameForSurface(false);
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to save frame for the main surface", e);
        }
    }

    /* access modifiers changed from: private */
    public final synchronized ICrosvmAndroidDisplayService getDisplayService() {
        ICrosvmAndroidDisplayService asInterface;
        try {
            asInterface = ICrosvmAndroidDisplayService.Stub.asInterface(getVirtService().waitDisplayService());
            asInterface.getClass();
        } catch (Exception e) {
            throw new RuntimeException("Error while getting display service", e);
        }
        return asInterface;
    }

    /* compiled from: DisplayProvider.kt */
    public enum SurfaceKind {
        MAIN,
        CURSOR;

        static {
            SurfaceKind[] $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }
    }

    /* compiled from: DisplayProvider.kt */
    public final class Callback implements SurfaceHolder.Callback {
        private final SurfaceKind surfaceKind;
        final /* synthetic */ DisplayProvider this$0;

        /* compiled from: DisplayProvider.kt */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            /* JADX WARNING: Failed to process nested try/catch */
            /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0010 */
            static {
                /*
                    com.android.virtualization.terminal.DisplayProvider$SurfaceKind[] r0 = com.android.virtualization.terminal.DisplayProvider.SurfaceKind.values()
                    int r0 = r0.length
                    int[] r0 = new int[r0]
                    com.android.virtualization.terminal.DisplayProvider$SurfaceKind r1 = com.android.virtualization.terminal.DisplayProvider.SurfaceKind.MAIN     // Catch:{ NoSuchFieldError -> 0x0010 }
                    int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0010 }
                    r2 = 1
                    r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0010 }
                L_0x0010:
                    com.android.virtualization.terminal.DisplayProvider$SurfaceKind r1 = com.android.virtualization.terminal.DisplayProvider.SurfaceKind.CURSOR     // Catch:{ NoSuchFieldError -> 0x0019 }
                    int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0019 }
                    r2 = 2
                    r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0019 }
                L_0x0019:
                    $EnumSwitchMapping$0 = r0
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.DisplayProvider.Callback.WhenMappings.<clinit>():void");
            }
        }

        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            surfaceHolder.getClass();
        }

        public Callback(DisplayProvider displayProvider, SurfaceKind surfaceKind2) {
            surfaceKind2.getClass();
            this.this$0 = displayProvider;
            this.surfaceKind = surfaceKind2;
        }

        public final boolean isForCursor() {
            return this.surfaceKind == SurfaceKind.CURSOR;
        }

        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            surfaceHolder.getClass();
            try {
                this.this$0.getDisplayService().setSurface(surfaceHolder.getSurface(), isForCursor());
            } catch (Exception e) {
                SurfaceKind surfaceKind2 = this.surfaceKind;
                Log.e("VmTerminalApp", "Failed to present surface " + surfaceKind2 + " to VM", e);
            }
            try {
                int i = WhenMappings.$EnumSwitchMapping$0[this.surfaceKind.ordinal()];
                if (i == 1) {
                    this.this$0.getDisplayService().drawSavedFrameForSurface(isForCursor());
                } else if (i == 2) {
                    this.this$0.getDisplayService().setCursorStream(this.this$0.createNewCursorStream());
                } else {
                    throw new NoWhenBranchMatchedException();
                }
            } catch (Exception e2) {
                SurfaceKind surfaceKind3 = this.surfaceKind;
                Log.e("VmTerminalApp", "Failed to configure surface " + surfaceKind3, e2);
            }
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            surfaceHolder.getClass();
            try {
                this.this$0.getDisplayService().removeSurface(isForCursor());
            } catch (RemoteException e) {
                SurfaceKind surfaceKind2 = this.surfaceKind;
                throw new RuntimeException("Error while destroying surface for " + surfaceKind2, e);
            }
        }
    }

    /* access modifiers changed from: private */
    public final ParcelFileDescriptor createNewCursorStream() {
        CursorHandler cursorHandler2 = this.cursorHandler;
        if (cursorHandler2 != null) {
            cursorHandler2.interrupt();
        }
        try {
            ParcelFileDescriptor[] createSocketPair = ParcelFileDescriptor.createSocketPair();
            createSocketPair.getClass();
            CursorHandler cursorHandler3 = new CursorHandler(this, createSocketPair[0]);
            cursorHandler3.start();
            this.cursorHandler = cursorHandler3;
            return createSocketPair[1];
        } catch (IOException e) {
            throw new RuntimeException("Failed to create socketpair for cursor stream", e);
        }
    }

    /* compiled from: DisplayProvider.kt */
    final class CursorHandler extends Thread {
        private final SurfaceControl cursor;
        private final ParcelFileDescriptor stream;
        final /* synthetic */ DisplayProvider this$0;
        private final SurfaceControl.Transaction transaction;

        public CursorHandler(DisplayProvider displayProvider, ParcelFileDescriptor parcelFileDescriptor) {
            parcelFileDescriptor.getClass();
            this.this$0 = displayProvider;
            this.stream = parcelFileDescriptor;
            SurfaceControl surfaceControl = displayProvider.cursorView.getSurfaceControl();
            surfaceControl.getClass();
            this.cursor = surfaceControl;
            SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
            this.transaction = transaction2;
            transaction2.reparent(surfaceControl, displayProvider.mainView.getSurfaceControl()).apply();
        }

        public void run() {
            try {
                ByteBuffer allocate = ByteBuffer.allocate(8);
                allocate.order(ByteOrder.LITTLE_ENDIAN);
                while (!Thread.interrupted()) {
                    allocate.clear();
                    if (IoBridge.read(this.stream.getFileDescriptor(), allocate.array(), 0, allocate.array().length) == -1) {
                        Log.e("VmTerminalApp", "cannot read from cursor stream, stop the handler");
                        return;
                    }
                    this.transaction.setPosition(this.cursor, (float) allocate.getInt(), (float) allocate.getInt()).apply();
                }
                Log.d("VmTerminalApp", "CursorHandler thread interrupted!");
            } catch (IOException e) {
                Log.e("VmTerminalApp", "failed to run CursorHandler", e);
            }
        }
    }
}
