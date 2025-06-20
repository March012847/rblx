package com.android.virtualization.terminal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UpgradeActivity.kt */
public final class UpgradeActivity extends BaseActivity {
    private ExecutorService executorService;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context applicationContext = getApplicationContext();
        applicationContext.getClass();
        this.executorService = Executors.newSingleThreadExecutor(new TerminalThreadFactory(applicationContext));
        setContentView(2131427360);
        findViewById(2131231305).setOnClickListener(new UpgradeActivity$onCreate$1(this));
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
    public final void upgrade() {
        findViewById(2131231129).setVisibility(0);
        ExecutorService executorService2 = this.executorService;
        if (executorService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("executorService");
            executorService2 = null;
        }
        executorService2.execute(new UpgradeActivity$upgrade$1(this));
    }

    /* access modifiers changed from: private */
    public final void restartTerminal() {
        Intent launchIntentForPackage = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        if (launchIntentForPackage != null) {
            launchIntentForPackage.addFlags(32768);
        }
        finish();
        startActivity(launchIntentForPackage);
    }
}
