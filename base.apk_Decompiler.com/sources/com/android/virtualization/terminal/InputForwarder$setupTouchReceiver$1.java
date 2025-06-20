package com.android.virtualization.terminal;

import android.view.MotionEvent;
import android.view.View;

/* compiled from: InputForwarder.kt */
final class InputForwarder$setupTouchReceiver$1 implements View.OnTouchListener {
    final /* synthetic */ InputForwarder this$0;

    InputForwarder$setupTouchReceiver$1(InputForwarder inputForwarder) {
        this.this$0 = inputForwarder;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        return this.this$0.virtualMachine.sendMultiTouchEvent(motionEvent);
    }
}
