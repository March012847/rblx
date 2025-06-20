package com.android.virtualization.terminal;

import com.google.android.material.tabs.TabLayout;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MainActivity.kt */
public final class MainActivity$initializeUi$4 implements TabLayout.OnTabSelectedListener {
    final /* synthetic */ MainActivity this$0;

    public void onTabReselected(TabLayout.Tab tab) {
    }

    public void onTabUnselected(TabLayout.Tab tab) {
    }

    MainActivity$initializeUi$4(MainActivity mainActivity) {
        this.this$0 = mainActivity;
    }

    public void onTabSelected(TabLayout.Tab tab) {
        if (tab != null) {
            int position = tab.getPosition();
            MainActivity mainActivity = this.this$0;
            TerminalViewModel access$getTerminalViewModel = mainActivity.getTerminalViewModel();
            TerminalTabAdapter access$getTerminalTabAdapter$p = mainActivity.terminalTabAdapter;
            if (access$getTerminalTabAdapter$p == null) {
                Intrinsics.throwUninitializedPropertyAccessException("terminalTabAdapter");
                access$getTerminalTabAdapter$p = null;
            }
            access$getTerminalViewModel.setSelectedTabViewId(((TabMetadata) access$getTerminalTabAdapter$p.getTabs().get(position)).getId());
        }
    }
}
