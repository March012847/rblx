package com.android.virtualization.terminal;

import java.net.URL;

/* compiled from: MainActivity.kt */
final class MainActivity$connectToTerminalService$1$1 implements Runnable {
    final /* synthetic */ TerminalView $terminalView;
    final /* synthetic */ URL $url;

    MainActivity$connectToTerminalService$1$1(TerminalView terminalView, URL url) {
        this.$terminalView = terminalView;
        this.$url = url;
    }

    public final void run() {
        this.$terminalView.loadUrl(String.valueOf(this.$url));
    }
}
