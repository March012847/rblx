package com.android.virtualization.terminal;

import kotlin.jvm.functions.Function1;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class MainActivity$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ MainActivity f$0;
    public final /* synthetic */ TerminalView f$1;

    public /* synthetic */ MainActivity$$ExternalSyntheticLambda0(MainActivity mainActivity, TerminalView terminalView) {
        this.f$0 = mainActivity;
        this.f$1 = terminalView;
    }

    public final Object invoke(Object obj) {
        return MainActivity.connectToTerminalService$lambda$1(this.f$0, this.f$1, (TerminalInfo) obj);
    }
}
