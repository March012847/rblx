package com.android.virtualization.terminal;

import androidx.fragment.app.FragmentActivity;
import com.google.android.material.tabs.TabLayout;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TerminalTabFragment.kt */
final class TerminalTabFragment$TerminalViewInterface$closeTab$1 implements Runnable {
    final /* synthetic */ TerminalTabFragment this$0;

    TerminalTabFragment$TerminalViewInterface$closeTab$1(TerminalTabFragment terminalTabFragment) {
        this.this$0 = terminalTabFragment;
    }

    public final void run() {
        FragmentActivity activity = this.this$0.getActivity();
        activity.getClass();
        MainActivity mainActivity = (MainActivity) activity;
        Map terminalTabs = this.this$0.getTerminalViewModel().getTerminalTabs();
        String access$getId$p = this.this$0.id;
        if (access$getId$p == null) {
            Intrinsics.throwUninitializedPropertyAccessException("id");
            access$getId$p = null;
        }
        Object obj = terminalTabs.get(access$getId$p);
        obj.getClass();
        mainActivity.closeTab((TabLayout.Tab) obj);
    }
}
