package com.android.virtualization.terminal;

import android.view.MotionEvent;
import android.view.View;

/* compiled from: InputForwarder.kt */
final class InputForwarder$setupMouseReceiver$1 implements View.OnCapturedPointerListener {
    final /* synthetic */ InputForwarder this$0;

    InputForwarder$setupMouseReceiver$1(InputForwarder inputForwarder) {
        this.this$0 = inputForwarder;
    }

    public final boolean onCapturedPointer(View view, MotionEvent motionEvent) {
        motionEvent.getClass();
        if ((motionEvent.getSource() & 8) != 0) {
            return this.this$0.virtualMachine.sendTrackpadEvent(motionEvent);
        }
        return this.this$0.virtualMachine.sendMouseEvent(motionEvent);
    }
}
