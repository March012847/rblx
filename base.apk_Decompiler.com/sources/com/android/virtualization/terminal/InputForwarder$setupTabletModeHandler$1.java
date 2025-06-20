package com.android.virtualization.terminal;

import android.hardware.input.InputManager;

/* compiled from: InputForwarder.kt */
public final class InputForwarder$setupTabletModeHandler$1 implements InputManager.InputDeviceListener {
    final /* synthetic */ InputForwarder this$0;

    InputForwarder$setupTabletModeHandler$1(InputForwarder inputForwarder) {
        this.this$0 = inputForwarder;
    }

    public void onInputDeviceAdded(int i) {
        this.this$0.setTabletModeConditionally();
    }

    public void onInputDeviceRemoved(int i) {
        this.this$0.setTabletModeConditionally();
    }

    public void onInputDeviceChanged(int i) {
        this.this$0.setTabletModeConditionally();
    }
}
