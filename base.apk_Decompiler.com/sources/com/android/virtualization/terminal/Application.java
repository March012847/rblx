package com.android.virtualization.terminal;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: Application.kt */
public final class Application extends android.app.Application {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);

    public void onCreate() {
        super.onCreate();
        setupNotificationChannels();
    }

    private final void setupNotificationChannels() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(new NotificationChannel("long_running", getString(2131689663), 3));
        notificationManager.createNotificationChannel(new NotificationChannel("system_events", getString(2131689664), 4));
    }

    /* compiled from: Application.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
