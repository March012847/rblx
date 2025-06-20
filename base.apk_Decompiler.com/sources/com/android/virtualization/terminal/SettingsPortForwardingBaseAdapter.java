package com.android.virtualization.terminal;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import androidx.recyclerview.widget.SortedListAdapterCallback;
import java.util.ArrayList;

/* compiled from: SettingsPortForwardingBaseAdapter.kt */
public abstract class SettingsPortForwardingBaseAdapter extends RecyclerView.Adapter {
    private SortedList items = new SortedList(SettingsPortForwardingItem.class, new SortedListAdapterCallback(this) {
        public int compare(SettingsPortForwardingItem settingsPortForwardingItem, SettingsPortForwardingItem settingsPortForwardingItem2) {
            settingsPortForwardingItem.getClass();
            settingsPortForwardingItem2.getClass();
            return settingsPortForwardingItem.getPort() - settingsPortForwardingItem2.getPort();
        }

        public boolean areContentsTheSame(SettingsPortForwardingItem settingsPortForwardingItem, SettingsPortForwardingItem settingsPortForwardingItem2) {
            settingsPortForwardingItem.getClass();
            settingsPortForwardingItem2.getClass();
            return settingsPortForwardingItem.getPort() == settingsPortForwardingItem2.getPort() && settingsPortForwardingItem.getEnabled() == settingsPortForwardingItem2.getEnabled();
        }

        public boolean areItemsTheSame(SettingsPortForwardingItem settingsPortForwardingItem, SettingsPortForwardingItem settingsPortForwardingItem2) {
            settingsPortForwardingItem.getClass();
            settingsPortForwardingItem2.getClass();
            return settingsPortForwardingItem.getPort() == settingsPortForwardingItem2.getPort();
        }
    });

    /* renamed from: getItems  reason: collision with other method in class */
    public abstract ArrayList m109getItems();

    public final SortedList getItems() {
        return this.items;
    }

    public int getItemCount() {
        return this.items.size();
    }

    public final void refreshItems() {
        this.items.replaceAll(getItems());
    }
}
