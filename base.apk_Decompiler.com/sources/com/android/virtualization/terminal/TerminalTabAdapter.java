package com.android.virtualization.terminal;

import androidx.core.os.BundleKt;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.ArrayList;
import java.util.UUID;
import kotlin.TuplesKt;

/* compiled from: TerminalTabAdapter.kt */
public final class TerminalTabAdapter extends FragmentStateAdapter {
    private final ArrayList tabs = new ArrayList();

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public TerminalTabAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragmentActivity.getClass();
    }

    public final ArrayList getTabs() {
        return this.tabs;
    }

    public Fragment createFragment(int i) {
        TerminalTabFragment terminalTabFragment = new TerminalTabFragment();
        terminalTabFragment.setArguments(BundleKt.bundleOf(TuplesKt.to("id", ((TabMetadata) this.tabs.get(i)).getId())));
        return terminalTabFragment;
    }

    public int getItemCount() {
        return this.tabs.size();
    }

    public long getItemId(int i) {
        return (long) ((TabMetadata) this.tabs.get(i)).getId().hashCode();
    }

    public boolean containsItem(long j) {
        ArrayList arrayList = this.tabs;
        if (arrayList != null && arrayList.isEmpty()) {
            return false;
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (((long) ((TabMetadata) obj).getId().hashCode()) == j) {
                return true;
            }
        }
        return false;
    }

    public final String addTab() {
        String uuid = UUID.randomUUID().toString();
        this.tabs.add(new TabMetadata(uuid));
        return uuid;
    }

    public final void deleteTab(int i) {
        if (i >= 0 && i < this.tabs.size()) {
            this.tabs.remove(i);
            notifyItemRemoved(i);
        }
    }
}
