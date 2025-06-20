package com.android.virtualization.terminal;

import android.view.KeyEvent;
import android.view.View;

/* compiled from: ModifierKeysController.kt */
final class ModifierKeysController$addClickListeners$listener$1 implements View.OnClickListener {
    final /* synthetic */ ModifierKeysController this$0;

    ModifierKeysController$addClickListeners$listener$1(ModifierKeysController modifierKeysController) {
        this.this$0 = modifierKeysController;
    }

    public final void onClick(View view) {
        view.getClass();
        Integer num = (Integer) ModifierKeysController.BTN_KEY_CODE_MAP.get(Integer.valueOf(view.getId()));
        if (num != null) {
            ModifierKeysController modifierKeysController = this.this$0;
            int intValue = num.intValue();
            TerminalView access$getActiveTerminalView$p = modifierKeysController.activeTerminalView;
            access$getActiveTerminalView$p.getClass();
            access$getActiveTerminalView$p.dispatchKeyEvent(new KeyEvent(0, intValue));
            TerminalView access$getActiveTerminalView$p2 = modifierKeysController.activeTerminalView;
            access$getActiveTerminalView$p2.getClass();
            access$getActiveTerminalView$p2.dispatchKeyEvent(new KeyEvent(1, intValue));
        }
    }
}
