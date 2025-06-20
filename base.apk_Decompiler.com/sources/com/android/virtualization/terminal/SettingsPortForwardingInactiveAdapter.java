package com.android.virtualization.terminal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Set;
import kotlin.collections.CollectionsKt;

/* compiled from: SettingsPortForwardingInactiveAdapter.kt */
public final class SettingsPortForwardingInactiveAdapter extends SettingsPortForwardingBaseAdapter {
    private final Context context;
    /* access modifiers changed from: private */
    public final PortsStateManager portsStateManager;

    public SettingsPortForwardingInactiveAdapter(PortsStateManager portsStateManager2, Context context2) {
        portsStateManager2.getClass();
        context2.getClass();
        this.portsStateManager = portsStateManager2;
        this.context = context2;
    }

    public ArrayList getItems() {
        Set<Number> subtract = CollectionsKt.subtract(this.portsStateManager.getEnabledPorts(), this.portsStateManager.getActivePorts());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(subtract, 10));
        for (Number intValue : subtract) {
            arrayList.add(new SettingsPortForwardingItem(intValue.intValue(), true));
        }
        return (ArrayList) CollectionsKt.toCollection(arrayList, new ArrayList());
    }

    /* compiled from: SettingsPortForwardingInactiveAdapter.kt */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageButton closeButton;
        private final TextView port;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public ViewHolder(View view) {
            super(view);
            view.getClass();
            View findViewById = view.findViewById(2131231189);
            findViewById.getClass();
            this.closeButton = (ImageButton) findViewById;
            View findViewById2 = view.findViewById(2131231190);
            findViewById2.getClass();
            this.port = (TextView) findViewById2;
        }

        public final ImageButton getCloseButton() {
            return this.closeButton;
        }

        public final TextView getPort() {
            return this.port;
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        viewGroup.getClass();
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(2131427450, viewGroup, false);
        inflate.getClass();
        return new ViewHolder(inflate);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.getClass();
        int port = ((SettingsPortForwardingItem) getItems().get(i)).getPort();
        viewHolder.getPort().setText(String.valueOf(port));
        viewHolder.getCloseButton().setContentDescription(this.context.getString(2131689704, new Object[]{Integer.valueOf(port)}));
        viewHolder.getCloseButton().setOnClickListener(new SettingsPortForwardingInactiveAdapter$onBindViewHolder$1(this, port));
    }
}
