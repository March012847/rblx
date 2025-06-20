package com.android.virtualization.terminal;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import com.android.virtualization.terminal.PortsStateManager;
import com.android.virtualization.terminal.proto.ActivePort;
import java.util.Arrays;
import java.util.Locale;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: PortNotifier.kt */
public final class PortNotifier {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final Context context;
    private final NotificationManager notificationManager;
    private final PortsStateManager.Listener portsStateListener;
    /* access modifiers changed from: private */
    public final PortsStateManager portsStateManager;
    private final BroadcastReceiver receiver;

    public PortNotifier(Context context2) {
        context2.getClass();
        this.context = context2;
        Object systemService = context2.getSystemService(NotificationManager.class);
        systemService.getClass();
        this.notificationManager = (NotificationManager) systemService;
        PortForwardingRequestReceiver portForwardingRequestReceiver = new PortForwardingRequestReceiver();
        context2.registerReceiver(portForwardingRequestReceiver, new IntentFilter("android.virtualization.PORT_FORWARDING"), 4);
        this.receiver = portForwardingRequestReceiver;
        PortsStateManager instance = PortsStateManager.Companion.getInstance(context2);
        this.portsStateManager = instance;
        PortNotifier$portsStateListener$1 portNotifier$portsStateListener$1 = new PortNotifier$portsStateListener$1(this);
        instance.registerListener(portNotifier$portsStateListener$1);
        this.portsStateListener = portNotifier$portsStateListener$1;
    }

    public final void stop() {
        this.portsStateManager.unregisterListener(this.portsStateListener);
        this.context.unregisterReceiver(this.receiver);
    }

    private final String getString(int i) {
        String string = this.context.getString(i);
        string.getClass();
        return string;
    }

    private final PendingIntent getPendingIntentFor(int i, boolean z) {
        Intent intent = new Intent("android.virtualization.PORT_FORWARDING");
        intent.setPackage(this.context.getPackageName());
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        intent.setIdentifier(String.format(Locale.ROOT, "%d_%b", Arrays.copyOf(new Object[]{Integer.valueOf(i), Boolean.valueOf(z)}, 2)));
        intent.putExtra("port", i);
        intent.putExtra("enabled", z);
        PendingIntent broadcast = PendingIntent.getBroadcast(this.context, 0, intent, 67108864);
        broadcast.getClass();
        return broadcast;
    }

    /* access modifiers changed from: private */
    public final void showNotificationFor(int i) {
        Intent intent = new Intent(this.context, SettingsPortForwardingActivity.class);
        intent.setFlags(603979776);
        PendingIntent activity = PendingIntent.getActivity(this.context, 0, intent, 67108864);
        String string = getString(2131689702);
        Context context2 = this.context;
        Integer valueOf = Integer.valueOf(i);
        ActivePort activePortInfo = this.portsStateManager.getActivePortInfo(i);
        String string2 = context2.getString(2131689700, new Object[]{valueOf, activePortInfo != null ? activePortInfo.getComm() : null});
        string2.getClass();
        String string3 = getString(2131689699);
        String string4 = getString(2131689701);
        Icon createWithResource = Icon.createWithResource(this.context, 2131165338);
        createWithResource.getClass();
        Notification.Action build = new Notification.Action.Builder(createWithResource, string3, getPendingIntentFor(i, true)).build();
        build.getClass();
        Notification.Action build2 = new Notification.Action.Builder(createWithResource, string4, getPendingIntentFor(i, false)).build();
        build2.getClass();
        Notification build3 = new Notification.Builder(this.context, "system_events").setSmallIcon(2131165347).setContentTitle(string).setContentText(string2).setFullScreenIntent(activity, true).addAction(build).addAction(build2).setAutoCancel(true).build();
        build3.getClass();
        this.notificationManager.notify("VmTerminalApp", i, build3);
    }

    /* access modifiers changed from: private */
    public final void discardNotificationFor(int i) {
        this.notificationManager.cancel("VmTerminalApp", i);
    }

    /* compiled from: PortNotifier.kt */
    final class PortForwardingRequestReceiver extends BroadcastReceiver {
        public PortForwardingRequestReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            intent.getClass();
            if (Intrinsics.areEqual("android.virtualization.PORT_FORWARDING", intent.getAction())) {
                performActionPortForwarding(intent);
            }
        }

        public final void performActionPortForwarding(Intent intent) {
            intent.getClass();
            int intExtra = intent.getIntExtra("port", 0);
            PortNotifier.this.portsStateManager.updateEnabledPort(intExtra, intent.getBooleanExtra("enabled", false));
            PortNotifier.this.discardNotificationFor(intExtra);
        }
    }

    /* compiled from: PortNotifier.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
