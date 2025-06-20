package com.android.virtualization.terminal;

import android.content.Context;
import android.util.Log;
import java.lang.Thread;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: TerminalExceptionHandler.kt */
public final class TerminalExceptionHandler implements Thread.UncaughtExceptionHandler {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final Context context;

    public TerminalExceptionHandler(Context context2) {
        context2.getClass();
        this.context = context2;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        thread.getClass();
        th.getClass();
        Exception exc = th instanceof Exception ? (Exception) th : null;
        if (exc == null) {
            exc = new Exception(th);
        }
        try {
            ErrorActivity.Companion.start(this.context, exc);
        } catch (Exception unused) {
            Log.wtf("TerminalExceptionHandler", "Failed to launch error activity for an exception", exc);
        }
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler != null) {
            defaultUncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }

    /* compiled from: TerminalExceptionHandler.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
