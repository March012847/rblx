package com.android.virtualization.terminal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.virtualization.terminal.PortsStateManager;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SettingsPortForwardingActivity.kt */
public final class SettingsPortForwardingActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public SettingsPortForwardingActiveAdapter activePortsAdapter;
    /* access modifiers changed from: private */
    public SettingsPortForwardingInactiveAdapter inactivePortsAdapter;
    private Listener portsStateListener;
    /* access modifiers changed from: private */
    public PortsStateManager portsStateManager;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427447);
        PortsStateManager instance = PortsStateManager.Companion.getInstance(this);
        this.portsStateManager = instance;
        SettingsPortForwardingInactiveAdapter settingsPortForwardingInactiveAdapter = null;
        if (instance == null) {
            Intrinsics.throwUninitializedPropertyAccessException("portsStateManager");
            instance = null;
        }
        this.activePortsAdapter = new SettingsPortForwardingActiveAdapter(instance, this);
        View findViewById = findViewById(2131231186);
        findViewById.getClass();
        RecyclerView recyclerView = (RecyclerView) findViewById;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SettingsPortForwardingActiveAdapter settingsPortForwardingActiveAdapter = this.activePortsAdapter;
        if (settingsPortForwardingActiveAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("activePortsAdapter");
            settingsPortForwardingActiveAdapter = null;
        }
        recyclerView.setAdapter(settingsPortForwardingActiveAdapter);
        PortsStateManager portsStateManager2 = this.portsStateManager;
        if (portsStateManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("portsStateManager");
            portsStateManager2 = null;
        }
        this.inactivePortsAdapter = new SettingsPortForwardingInactiveAdapter(portsStateManager2, this);
        View findViewById2 = findViewById(2131231191);
        findViewById2.getClass();
        RecyclerView recyclerView2 = (RecyclerView) findViewById2;
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        SettingsPortForwardingInactiveAdapter settingsPortForwardingInactiveAdapter2 = this.inactivePortsAdapter;
        if (settingsPortForwardingInactiveAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("inactivePortsAdapter");
        } else {
            settingsPortForwardingInactiveAdapter = settingsPortForwardingInactiveAdapter2;
        }
        recyclerView2.setAdapter(settingsPortForwardingInactiveAdapter);
        this.portsStateListener = new Listener();
        ((ImageButton) findViewById(2131231187)).setOnClickListener(new SettingsPortForwardingActivity$onCreate$1(this));
    }

    /* access modifiers changed from: private */
    public final void refreshAdapters() {
        runOnUiThread(new SettingsPortForwardingActivity$refreshAdapters$1(this));
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        PortsStateManager portsStateManager2 = this.portsStateManager;
        Listener listener = null;
        if (portsStateManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("portsStateManager");
            portsStateManager2 = null;
        }
        Listener listener2 = this.portsStateListener;
        if (listener2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("portsStateListener");
        } else {
            listener = listener2;
        }
        portsStateManager2.registerListener(listener);
        refreshAdapters();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        PortsStateManager portsStateManager2 = this.portsStateManager;
        Listener listener = null;
        if (portsStateManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("portsStateManager");
            portsStateManager2 = null;
        }
        Listener listener2 = this.portsStateListener;
        if (listener2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("portsStateListener");
        } else {
            listener = listener2;
        }
        portsStateManager2.unregisterListener(listener);
        super.onPause();
    }

    /* compiled from: SettingsPortForwardingActivity.kt */
    final class Listener implements PortsStateManager.Listener {
        public Listener() {
        }

        public void onPortsStateUpdated(Set set, Set set2) {
            set.getClass();
            set2.getClass();
            SettingsPortForwardingActivity.this.refreshAdapters();
        }
    }
}
