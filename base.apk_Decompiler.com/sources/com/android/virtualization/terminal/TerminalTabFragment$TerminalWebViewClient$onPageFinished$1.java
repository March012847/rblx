package com.android.virtualization.terminal;

import android.view.View;
import android.webkit.WebView;
import com.android.virtualization.terminal.TerminalTabFragment;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TerminalTabFragment.kt */
public final class TerminalTabFragment$TerminalWebViewClient$onPageFinished$1 extends WebView.VisualStateCallback {
    final /* synthetic */ TerminalTabFragment.TerminalWebViewClient this$0;
    final /* synthetic */ TerminalTabFragment this$1;

    TerminalTabFragment$TerminalWebViewClient$onPageFinished$1(TerminalTabFragment.TerminalWebViewClient terminalWebViewClient, TerminalTabFragment terminalTabFragment) {
        this.this$0 = terminalWebViewClient;
        this.this$1 = terminalTabFragment;
    }

    public void onComplete(long j) {
        if (j == this.this$0.requestId) {
            View access$getBootProgressView$p = this.this$1.bootProgressView;
            TerminalView terminalView = null;
            if (access$getBootProgressView$p == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bootProgressView");
                access$getBootProgressView$p = null;
            }
            access$getBootProgressView$p.setVisibility(8);
            TerminalView access$getTerminalView$p = this.this$1.terminalView;
            if (access$getTerminalView$p == null) {
                Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                access$getTerminalView$p = null;
            }
            access$getTerminalView$p.setVisibility(0);
            TerminalView access$getTerminalView$p2 = this.this$1.terminalView;
            if (access$getTerminalView$p2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                access$getTerminalView$p2 = null;
            }
            access$getTerminalView$p2.mapTouchToMouseEvent();
            TerminalView access$getTerminalView$p3 = this.this$1.terminalView;
            if (access$getTerminalView$p3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            } else {
                terminalView = access$getTerminalView$p3;
            }
            terminalView.applyTerminalDisconnectCallback();
            this.this$1.updateMainActivity();
            this.this$1.updateFocus();
        }
    }
}
