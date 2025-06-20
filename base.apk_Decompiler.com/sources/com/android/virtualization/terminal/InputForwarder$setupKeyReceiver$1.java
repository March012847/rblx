package com.android.virtualization.terminal;

import android.view.KeyEvent;
import android.view.View;

/* compiled from: InputForwarder.kt */
final class InputForwarder$setupKeyReceiver$1 implements View.OnKeyListener {
    final /* synthetic */ InputForwarder this$0;

    InputForwarder$setupKeyReceiver$1(InputForwarder inputForwarder) {
        this.this$0 = inputForwarder;
    }

    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (InputForwarder.Companion.isVolumeKey(i)) {
            return false;
        }
        return this.this$0.virtualMachine.sendKeyEvent(keyEvent);
    }
}
