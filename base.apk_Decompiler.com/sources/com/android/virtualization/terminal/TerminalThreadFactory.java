package com.android.virtualization.terminal;

import android.content.Context;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* compiled from: TerminalThreadFactory.kt */
public final class TerminalThreadFactory implements ThreadFactory {
    private final Context context;

    public TerminalThreadFactory(Context context2) {
        context2.getClass();
        this.context = context2;
    }

    public Thread newThread(Runnable runnable) {
        runnable.getClass();
        Thread newThread = Executors.defaultThreadFactory().newThread(runnable);
        newThread.setUncaughtExceptionHandler(new TerminalExceptionHandler(this.context));
        return newThread;
    }
}
