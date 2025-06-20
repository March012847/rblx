package com.android.virtualization.terminal;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SettingsRecoveryActivity.kt */
public final class SettingsRecoveryActivity extends AppCompatActivity {
    private ExecutorService executorService;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context applicationContext = getApplicationContext();
        applicationContext.getClass();
        this.executorService = Executors.newSingleThreadExecutor(new TerminalThreadFactory(applicationContext));
        setContentView(2131427451);
        ((MaterialCardView) findViewById(2131231195)).setOnClickListener(new SettingsRecoveryActivity$onCreate$1(this));
        View findViewById = findViewById(2131231192);
        findViewById.getClass();
        findViewById.setVisibility(InstalledImage.Companion.getDefault(this).hasBackup() ? 0 : 8);
        findViewById.setOnClickListener(new SettingsRecoveryActivity$onCreate$2(this));
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        ExecutorService executorService2 = this.executorService;
        if (executorService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("executorService");
            executorService2 = null;
        }
        executorService2.shutdown();
    }

    /* access modifiers changed from: private */
    public final void removeBackup() {
        try {
            InstalledImage.Companion.getDefault(this).deleteBackup();
        } catch (IOException unused) {
            Snackbar.make(findViewById(16908290), 2131689710, -1).show();
            Log.e("VmTerminalApp", "cannot remove backup");
        }
    }

    /* access modifiers changed from: private */
    public final void uninstall(boolean z) {
        InstalledImage installedImage = InstalledImage.Companion.getDefault(this);
        if (z) {
            try {
                installedImage.uninstallAndBackup();
            } catch (IOException e) {
                Snackbar.make(findViewById(16908290), z ? 2131689709 : 2131689708, -1).show();
                Log.e("VmTerminalApp", "cannot recovery ", e);
            }
        } else {
            PortsStateManager.Companion.getInstance(this).clearEnabledPorts();
            installedImage.uninstallFully();
        }
    }

    /* access modifiers changed from: private */
    public final void runInBackgroundAndRestartApp(Runnable runnable) {
        findViewById(2131231172).setVisibility(4);
        findViewById(2131231135).setVisibility(0);
        ExecutorService executorService2 = this.executorService;
        if (executorService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("executorService");
            executorService2 = null;
        }
        executorService2.execute(new SettingsRecoveryActivity$runInBackgroundAndRestartApp$1(runnable, this));
    }
}
