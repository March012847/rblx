package com.android.virtualization.terminal;

import android.view.View;
import com.google.android.material.tabs.TabLayout;

/* compiled from: MainActivity.kt */
final class MainActivity$addTerminalTab$1 implements View.OnClickListener {
    final /* synthetic */ TabLayout.Tab $tab;
    final /* synthetic */ MainActivity this$0;

    MainActivity$addTerminalTab$1(MainActivity mainActivity, TabLayout.Tab tab) {
        this.this$0 = mainActivity;
        this.$tab = tab;
    }

    public final void onClick(View view) {
        this.this$0.closeTab(this.$tab);
    }
}
