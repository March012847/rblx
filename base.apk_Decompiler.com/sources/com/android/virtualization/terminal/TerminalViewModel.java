package com.android.virtualization.terminal;

import androidx.lifecycle.ViewModel;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/* compiled from: TerminalViewModel.kt */
public final class TerminalViewModel extends ViewModel {
    private String selectedTabViewId;
    private final Map terminalTabs = new LinkedHashMap();
    private final Set terminalViews = new LinkedHashSet();

    public final Set getTerminalViews() {
        return this.terminalViews;
    }

    public final String getSelectedTabViewId() {
        return this.selectedTabViewId;
    }

    public final void setSelectedTabViewId(String str) {
        this.selectedTabViewId = str;
    }

    public final Map getTerminalTabs() {
        return this.terminalTabs;
    }
}
