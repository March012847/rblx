package com.android.virtualization.terminal;

import android.view.View;

/* compiled from: ModifierKeysController.kt */
final class ModifierKeysController$addTerminalView$1 implements View.OnFocusChangeListener {
    final /* synthetic */ TerminalView $terminalView;
    final /* synthetic */ ModifierKeysController this$0;

    ModifierKeysController$addTerminalView$1(ModifierKeysController modifierKeysController, TerminalView terminalView) {
        this.this$0 = modifierKeysController;
        this.$terminalView = terminalView;
    }

    public final void onFocusChange(View view, boolean z) {
        view.getClass();
        if (z) {
            this.this$0.activeTerminalView = this.$terminalView;
        } else {
            this.this$0.activeTerminalView = null;
            this.$terminalView.disableCtrlKey();
        }
        this.this$0.update();
    }
}
