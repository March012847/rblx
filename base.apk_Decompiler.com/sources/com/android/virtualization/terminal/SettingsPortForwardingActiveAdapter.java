package com.android.virtualization.terminal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.virtualization.terminal.proto.ActivePort;
import com.google.android.material.materialswitch.MaterialSwitch;
import java.util.ArrayList;
import java.util.Set;
import kotlin.collections.CollectionsKt;

/* compiled from: SettingsPortForwardingActiveAdapter.kt */
public final class SettingsPortForwardingActiveAdapter extends SettingsPortForwardingBaseAdapter {
    private final Context context;
    /* access modifiers changed from: private */
    public final PortsStateManager portsStateManager;

    public SettingsPortForwardingActiveAdapter(PortsStateManager portsStateManager2, Context context2) {
        portsStateManager2.getClass();
        context2.getClass();
        this.portsStateManager = portsStateManager2;
        this.context = context2;
    }

    public ArrayList getItems() {
        Set enabledPorts = this.portsStateManager.getEnabledPorts();
        Set<Number> activePorts = this.portsStateManager.getActivePorts();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(activePorts, 10));
        for (Number intValue : activePorts) {
            int intValue2 = intValue.intValue();
            arrayList.add(new SettingsPortForwardingItem(intValue2, enabledPorts.contains(Integer.valueOf(intValue2))));
        }
        return (ArrayList) CollectionsKt.toCollection(arrayList, new ArrayList());
    }

    /* compiled from: SettingsPortForwardingActiveAdapter.kt */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialSwitch enabledSwitch;
        private final TextView port;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public ViewHolder(View view) {
            super(view);
            view.getClass();
            View findViewById = view.findViewById(2131231184);
            findViewById.getClass();
            this.enabledSwitch = (MaterialSwitch) findViewById;
            View findViewById2 = view.findViewById(2131231185);
            findViewById2.getClass();
            this.port = (TextView) findViewById2;
        }

        public final MaterialSwitch getEnabledSwitch() {
            return this.enabledSwitch;
        }

        public final TextView getPort() {
            return this.port;
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        viewGroup.getClass();
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(2131427448, viewGroup, false);
        inflate.getClass();
        return new ViewHolder(inflate);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.getClass();
        int port = ((SettingsPortForwardingItem) getItems().get(i)).getPort();
        TextView port2 = viewHolder.getPort();
        Context context2 = this.context;
        Integer valueOf = Integer.valueOf(port);
        ActivePort activePortInfo = this.portsStateManager.getActivePortInfo(port);
        port2.setText(context2.getString(2131689690, new Object[]{valueOf, activePortInfo != null ? activePortInfo.getComm() : null}));
        viewHolder.getEnabledSwitch().setContentDescription(viewHolder.getPort().getText());
        viewHolder.getEnabledSwitch().setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        viewHolder.getEnabledSwitch().setChecked(((SettingsPortForwardingItem) getItems().get(i)).getEnabled());
        viewHolder.getEnabledSwitch().setOnCheckedChangeListener(new SettingsPortForwardingActiveAdapter$onBindViewHolder$1(this, port));
    }
}
