package com.android.virtualization.terminal;

import android.view.View;

/* compiled from: ModifierKeysController.kt */
final class ModifierKeysController$addClickListeners$1 implements View.OnClickListener {
    final /* synthetic */ ModifierKeysController this$0;

    ModifierKeysController$addClickListeners$1(ModifierKeysController modifierKeysController) {
        this.this$0 = modifierKeysController;
    }

    public final void onClick(View view) {
        TerminalView access$getActiveTerminalView$p = this.this$0.activeTerminalView;
        access$getActiveTerminalView$p.getClass();
        access$getActiveTerminalView$p.mapCtrlKey();
        TerminalView access$getActiveTerminalView$p2 = this.this$0.activeTerminalView;
        access$getActiveTerminalView$p2.getClass();
        access$getActiveTerminalView$p2.enableCtrlKey();
    }
}
