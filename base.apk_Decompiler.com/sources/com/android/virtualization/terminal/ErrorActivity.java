package com.android.virtualization.terminal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: ErrorActivity.kt */
public final class ErrorActivity extends BaseActivity {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427357);
        findViewById(2131231134).setOnClickListener(new ErrorActivity$onCreate$1(this));
        ((TextView) findViewById(2131230850)).setMovementMethod(new ScrollingMovementMethod());
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        intent.getClass();
        super.onNewIntent(intent);
        setIntent(intent);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Exception exc = (Exception) getIntent().getParcelableExtra("cause", Exception.class);
        ((TextView) findViewById(2131230850)).setText(exc != null ? getString(2131689535, new Object[]{Companion.getStackTrace(exc)}) : null);
    }

    /* access modifiers changed from: private */
    public final void launchRecoveryActivity() {
        startActivity(new Intent(this, SettingsRecoveryActivity.class));
    }

    /* compiled from: ErrorActivity.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void start(Context context, Exception exc) {
            context.getClass();
            exc.getClass();
            Intent intent = new Intent(context, ErrorActivity.class);
            intent.putExtra("cause", exc);
            intent.setFlags(268468224);
            context.startActivity(intent);
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x001d, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
            kotlin.io.CloseableKt.closeFinally(r0, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0021, code lost:
            throw r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0023, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
            kotlin.io.CloseableKt.closeFinally(r2, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0027, code lost:
            throw r0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.String getStackTrace(java.lang.Exception r3) {
            /*
                r2 = this;
                java.io.StringWriter r2 = new java.io.StringWriter     // Catch:{ IOException -> 0x0028 }
                r2.<init>()     // Catch:{ IOException -> 0x0028 }
                java.io.PrintWriter r0 = new java.io.PrintWriter     // Catch:{ all -> 0x0019 }
                r0.<init>(r2)     // Catch:{ all -> 0x0019 }
                r3.printStackTrace(r0)     // Catch:{ all -> 0x001b }
                java.lang.String r3 = r2.toString()     // Catch:{ all -> 0x001b }
                r1 = 0
                kotlin.io.CloseableKt.closeFinally(r0, r1)     // Catch:{ all -> 0x0019 }
                kotlin.io.CloseableKt.closeFinally(r2, r1)     // Catch:{ IOException -> 0x0028 }
                return r3
            L_0x0019:
                r3 = move-exception
                goto L_0x0022
            L_0x001b:
                r3 = move-exception
                throw r3     // Catch:{ all -> 0x001d }
            L_0x001d:
                r1 = move-exception
                kotlin.io.CloseableKt.closeFinally(r0, r3)     // Catch:{ all -> 0x0019 }
                throw r1     // Catch:{ all -> 0x0019 }
            L_0x0022:
                throw r3     // Catch:{ all -> 0x0023 }
            L_0x0023:
                r0 = move-exception
                kotlin.io.CloseableKt.closeFinally(r2, r3)     // Catch:{ IOException -> 0x0028 }
                throw r0     // Catch:{ IOException -> 0x0028 }
            L_0x0028:
                r2 = move-exception
                java.lang.RuntimeException r3 = new java.lang.RuntimeException
                r3.<init>(r2)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.ErrorActivity.Companion.getStackTrace(java.lang.Exception):java.lang.String");
        }
    }
}
