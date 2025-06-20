package com.android.virtualization.terminal;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.FileUtils;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.virtualization.terminal.IInstallProgressListener;
import com.android.virtualization.terminal.IInstallerService;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import java.lang.ref.WeakReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: InstallerActivity.kt */
public final class InstallerActivity extends BaseActivity {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private static final long ESTIMATED_IMG_SIZE_BYTES = FileUtils.parseSize("550MB");
    private TextView installButton;
    private final ConditionVariable installCompleted = new ConditionVariable();
    private InstallProgressListener installProgressListener;
    private boolean installRequested;
    /* access modifiers changed from: private */
    public ServiceConnection installerServiceConnection;
    /* access modifiers changed from: private */
    public IInstallerService service;
    private CheckBox waitForWifiCheckbox;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setResult(0);
        this.installProgressListener = new InstallProgressListener(this);
        setContentView(2131427359);
        updateSizeEstimation(ESTIMATED_IMG_SIZE_BYTES);
        measureImageSizeAndUpdateDescription();
        this.waitForWifiCheckbox = (CheckBox) findViewById(2131230985);
        TextView textView = (TextView) findViewById(2131230981);
        this.installButton = textView;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("installButton");
            textView = null;
        }
        textView.setOnClickListener(new InstallerActivity$onCreate$1(this));
        Intent intent = new Intent(this, InstallerService.class);
        InstallerServiceConnection installerServiceConnection2 = new InstallerServiceConnection(this);
        this.installerServiceConnection = installerServiceConnection2;
        if (!bindService(intent, installerServiceConnection2, 1)) {
            handleInternalError(new Exception("Failed to connect to installer service"));
        }
    }

    /* access modifiers changed from: private */
    public final void updateSizeEstimation(long j) {
        String string = getString(2131689544, new Object[]{Formatter.formatShortFileSize(this, j)});
        string.getClass();
        runOnUiThread(new InstallerActivity$updateSizeEstimation$1(this, string));
    }

    private final void measureImageSizeAndUpdateDescription() {
        new Thread(new InstallerActivity$measureImageSizeAndUpdateDescription$1(this)).start();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (Build.isDebuggable() && ImageArchive.Companion.fromSdCard().exists()) {
            showSnackBar("Auto installing", 0);
            requestInstall();
        }
    }

    public void onDestroy() {
        ServiceConnection serviceConnection = this.installerServiceConnection;
        if (serviceConnection != null) {
            serviceConnection.getClass();
            unbindService(serviceConnection);
            this.installerServiceConnection = null;
        }
        super.onDestroy();
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 108) {
            return super.onKeyUp(i, keyEvent);
        }
        requestInstall();
        return true;
    }

    @VisibleForTesting
    public final boolean waitForInstallCompleted(long j) {
        return this.installCompleted.block(j);
    }

    private final void showSnackBar(String str, int i) {
        Snackbar make = Snackbar.make(findViewById(16908290), (CharSequence) str, i);
        make.getClass();
        CheckBox checkBox = this.waitForWifiCheckbox;
        if (checkBox == null) {
            Intrinsics.throwUninitializedPropertyAccessException("waitForWifiCheckbox");
            checkBox = null;
        }
        make.setAnchorView(checkBox);
        make.show();
    }

    public final void handleInternalError(Exception exc) {
        exc.getClass();
        if (Build.isDebuggable()) {
            String message = exc.getMessage();
            showSnackBar(message + ". File a bugreport to go/ferrochrome-bug", -2);
        }
        Log.e("VmTerminalApp", "Internal error", exc);
        finishWithResult(0);
    }

    /* access modifiers changed from: private */
    public final void finishWithResult(int i) {
        if (i == -1) {
            this.installCompleted.open();
        }
        setResult(i);
        finish();
    }

    private final void setInstallEnabled(boolean z) {
        TextView textView = this.installButton;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("installButton");
            textView = null;
        }
        textView.setEnabled(z);
        CheckBox checkBox = this.waitForWifiCheckbox;
        if (checkBox == null) {
            Intrinsics.throwUninitializedPropertyAccessException("waitForWifiCheckbox");
            checkBox = null;
        }
        checkBox.setEnabled(z);
        ((LinearProgressIndicator) findViewById(2131230982)).setVisibility(z ? 4 : 0);
        int i = z ? 2131689549 : 2131689548;
        TextView textView3 = this.installButton;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("installButton");
        } else {
            textView2 = textView3;
        }
        textView2.setText(getString(i));
    }

    /* access modifiers changed from: private */
    public final void requestInstall() {
        setInstallEnabled(false);
        IInstallerService iInstallerService = this.service;
        if (iInstallerService != null) {
            try {
                iInstallerService.getClass();
                CheckBox checkBox = this.waitForWifiCheckbox;
                if (checkBox == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("waitForWifiCheckbox");
                    checkBox = null;
                }
                iInstallerService.requestInstall(checkBox.isChecked());
            } catch (RemoteException e) {
                handleInternalError(e);
            }
        } else {
            Log.d("VmTerminalApp", "requestInstall() is called, but not yet connected");
            this.installRequested = true;
        }
    }

    public final void handleInstallerServiceConnected() {
        try {
            IInstallerService iInstallerService = this.service;
            iInstallerService.getClass();
            InstallProgressListener installProgressListener2 = this.installProgressListener;
            if (installProgressListener2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("installProgressListener");
                installProgressListener2 = null;
            }
            iInstallerService.setProgressListener(installProgressListener2);
            IInstallerService iInstallerService2 = this.service;
            iInstallerService2.getClass();
            if (iInstallerService2.isInstalled()) {
                finishWithResult(-1);
            } else if (this.installRequested) {
                requestInstall();
            } else {
                IInstallerService iInstallerService3 = this.service;
                iInstallerService3.getClass();
                if (iInstallerService3.isInstalling()) {
                    setInstallEnabled(false);
                }
            }
        } catch (RemoteException e) {
            handleInternalError(e);
        }
    }

    public final void handleInstallerServiceDisconnected() {
        handleInternalError(new Exception("InstallerService is destroyed while in use"));
    }

    /* access modifiers changed from: private */
    public final void handleInstallError(String str) {
        showSnackBar(str, 0);
        setInstallEnabled(true);
    }

    /* compiled from: InstallerActivity.kt */
    final class InstallProgressListener extends IInstallProgressListener.Stub {
        /* access modifiers changed from: private */
        public final WeakReference activity;

        public InstallProgressListener(InstallerActivity installerActivity) {
            installerActivity.getClass();
            this.activity = new WeakReference(installerActivity);
        }

        public void onCompleted() {
            InstallerActivity installerActivity = (InstallerActivity) this.activity.get();
            if (installerActivity != null) {
                installerActivity.finishWithResult(-1);
            }
        }

        public void onError(String str) {
            str.getClass();
            InstallerActivity installerActivity = (InstallerActivity) this.activity.get();
            if (installerActivity != null) {
                installerActivity.runOnUiThread(new InstallerActivity$InstallProgressListener$onError$1(this, str));
            }
        }
    }

    /* compiled from: InstallerActivity.kt */
    public final class InstallerServiceConnection implements ServiceConnection {
        private final WeakReference activity;

        public InstallerServiceConnection(InstallerActivity installerActivity) {
            installerActivity.getClass();
            this.activity = new WeakReference(installerActivity);
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            InstallerActivity installerActivity = (InstallerActivity) this.activity.get();
            if (installerActivity != null && installerActivity.installerServiceConnection != null) {
                if (iBinder == null) {
                    installerActivity.handleInternalError(new Exception("service shouldn't be null"));
                }
                installerActivity.service = IInstallerService.Stub.asInterface(iBinder);
                installerActivity.handleInstallerServiceConnected();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            InstallerActivity installerActivity = (InstallerActivity) this.activity.get();
            if (installerActivity != null && installerActivity.installerServiceConnection != null) {
                ServiceConnection access$getInstallerServiceConnection$p = installerActivity.installerServiceConnection;
                access$getInstallerServiceConnection$p.getClass();
                installerActivity.unbindService(access$getInstallerServiceConnection$p);
                installerActivity.installerServiceConnection = null;
                installerActivity.handleInstallerServiceDisconnected();
            }
        }
    }

    /* compiled from: InstallerActivity.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
