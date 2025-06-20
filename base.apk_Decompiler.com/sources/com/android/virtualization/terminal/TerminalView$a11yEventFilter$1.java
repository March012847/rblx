package com.android.virtualization.terminal;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

/* compiled from: TerminalView.kt */
public final class TerminalView$a11yEventFilter$1 extends View.AccessibilityDelegate {
    final /* synthetic */ TerminalView this$0;

    TerminalView$a11yEventFilter$1(TerminalView terminalView) {
        this.this$0 = terminalView;
    }

    public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        viewGroup.getClass();
        view.getClass();
        accessibilityEvent.getClass();
        if (view != this.this$0) {
            return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }
        if (accessibilityEvent.getEventType() == 16384) {
            CharSequence charSequence = (CharSequence) accessibilityEvent.getText().get(0);
            if (charSequence.length() >= 200) {
                Log.i("VmTerminalApp", "Announcement skipped because it's too long: " + charSequence);
                return false;
            }
        }
        return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
    }
}
