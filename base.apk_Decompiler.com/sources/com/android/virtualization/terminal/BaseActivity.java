package com.android.virtualization.terminal;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: BaseActivity.kt */
public abstract class BaseActivity extends AppCompatActivity {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!(this instanceof ErrorActivity)) {
            Thread currentThread = Thread.currentThread();
            if (!(currentThread.getUncaughtExceptionHandler() instanceof TerminalExceptionHandler)) {
                Context applicationContext = getApplicationContext();
                applicationContext.getClass();
                currentThread.setUncaughtExceptionHandler(new TerminalExceptionHandler(applicationContext));
            }
        }
        if (getApplicationContext().checkSelfPermission("android.permission.POST_NOTIFICATIONS") != 0) {
            requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, 101);
        }
    }

    /* compiled from: BaseActivity.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
