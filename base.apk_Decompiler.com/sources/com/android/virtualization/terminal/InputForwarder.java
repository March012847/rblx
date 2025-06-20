package com.android.virtualization.terminal;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.system.virtualmachine.VirtualMachine;
import android.system.virtualmachine.VirtualMachineCustomImageConfig;
import android.util.Log;
import android.view.InputDevice;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: InputForwarder.kt */
public final class InputForwarder {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final Context context;
    private InputManager.InputDeviceListener inputDeviceListener;
    private boolean isTabletMode;
    /* access modifiers changed from: private */
    public final VirtualMachine virtualMachine;

    public InputForwarder(Context context2, VirtualMachine virtualMachine2, View view, View view2, View view3) {
        context2.getClass();
        virtualMachine2.getClass();
        view.getClass();
        view2.getClass();
        view3.getClass();
        this.context = context2;
        this.virtualMachine = virtualMachine2;
        VirtualMachineCustomImageConfig customImageConfig = virtualMachine2.getConfig().getCustomImageConfig();
        if (customImageConfig != null) {
            if (customImageConfig.useTouch()) {
                setupTouchReceiver(view);
            }
            if (customImageConfig.useMouse() || customImageConfig.useTrackpad()) {
                setupMouseReceiver(view2);
            }
            if (customImageConfig.useKeyboard()) {
                setupKeyReceiver(view3);
            }
            if (customImageConfig.useSwitches()) {
                setupTabletModeHandler(view.getHandler());
                return;
            }
            return;
        }
        throw new IllegalStateException("Required value was null.");
    }

    private final void setupTouchReceiver(View view) {
        view.setOnTouchListener(new InputForwarder$setupTouchReceiver$1(this));
    }

    private final void setupMouseReceiver(View view) {
        view.requestUnbufferedDispatch(-256);
        view.setOnCapturedPointerListener(new InputForwarder$setupMouseReceiver$1(this));
    }

    private final void setupKeyReceiver(View view) {
        view.setOnKeyListener(new InputForwarder$setupKeyReceiver$1(this));
    }

    private final void setupTabletModeHandler(Handler handler) {
        InputManager inputManager = (InputManager) this.context.getSystemService(InputManager.class);
        this.inputDeviceListener = new InputForwarder$setupTabletModeHandler$1(this);
        inputManager.getClass();
        inputManager.registerInputDeviceListener(this.inputDeviceListener, handler);
    }

    public final void setTabletModeConditionally() {
        boolean access$hasPhysicalKeyboard = Companion.hasPhysicalKeyboard();
        boolean z = !access$hasPhysicalKeyboard;
        if (z != this.isTabletMode) {
            Log.d("VmTerminalApp", "switching to " + (!access$hasPhysicalKeyboard ? "tablet mode" : "desktop mode"));
            this.isTabletMode = z;
            this.virtualMachine.sendTabletModeEvent(z);
        }
    }

    /* compiled from: InputForwarder.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* access modifiers changed from: private */
        public final boolean isVolumeKey(int i) {
            return i == 24 || i == 25 || i == 164;
        }

        private Companion() {
        }

        /* access modifiers changed from: private */
        public final boolean hasPhysicalKeyboard() {
            for (int device : InputDevice.getDeviceIds()) {
                InputDevice device2 = InputDevice.getDevice(device);
                device2.getClass();
                if (!device2.isVirtual() && device2.isEnabled() && device2.isFullKeyboard()) {
                    return true;
                }
            }
            return false;
        }
    }
}
