package com.android.virtualization.terminal;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.virtualization.terminal.proto.ActivePort;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* compiled from: PortsStateManager.kt */
public final class PortsStateManager {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static PortsStateManager instance;
    private final Map activePorts;
    private final Set enabledPorts;
    private final Set listeners;
    private final Object lock;
    private final SharedPreferences sharedPref;

    /* compiled from: PortsStateManager.kt */
    public interface Listener {
        void onPortsStateUpdated(Set set, Set set2);
    }

    public /* synthetic */ PortsStateManager(SharedPreferences sharedPreferences, DefaultConstructorMarker defaultConstructorMarker) {
        this(sharedPreferences);
    }

    private PortsStateManager(SharedPreferences sharedPreferences) {
        this.sharedPref = sharedPreferences;
        this.lock = new Object();
        this.activePorts = new HashMap();
        Set<Map.Entry<String, ?>> entrySet = sharedPreferences.getAll().entrySet();
        ArrayList arrayList = new ArrayList();
        for (T next : entrySet) {
            if (TypeIntrinsics.isMutableMapEntry(next)) {
                arrayList.add(next);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            if ((((Number) ((Map.Entry) obj).getValue()).intValue() & 1) == 1) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        int size2 = arrayList2.size();
        while (i < size2) {
            Object obj2 = arrayList2.get(i);
            i++;
            arrayList3.add(StringsKt.toIntOrNull((String) ((Map.Entry) obj2).getKey()));
        }
        this.enabledPorts = CollectionsKt.toMutableSet(CollectionsKt.filterNotNull(arrayList3));
        this.listeners = new HashSet();
    }

    public final Set getActivePorts() {
        HashSet hashSet;
        synchronized (this.lock) {
            hashSet = new HashSet(this.activePorts.keySet());
        }
        return hashSet;
    }

    public final ActivePort getActivePortInfo(int i) {
        ActivePort activePort;
        synchronized (this.lock) {
            activePort = (ActivePort) this.activePorts.get(Integer.valueOf(i));
        }
        return activePort;
    }

    public final Set getEnabledPorts() {
        HashSet hashSet;
        synchronized (this.lock) {
            hashSet = new HashSet(this.enabledPorts);
        }
        return hashSet;
    }

    public final void updateActivePorts(List list) {
        list.getClass();
        Set activePorts2 = getActivePorts();
        synchronized (this.lock) {
            try {
                this.activePorts.clear();
                Map map = this.activePorts;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
                for (Object next : list) {
                    linkedHashMap.put(Integer.valueOf(((ActivePort) next).getPort()), next);
                }
                map.putAll(linkedHashMap);
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        notifyPortsStateUpdated(activePorts2, getActivePorts());
    }

    public final void updateEnabledPort(int i, boolean z) {
        synchronized (this.lock) {
            try {
                SharedPreferences.Editor edit = this.sharedPref.edit();
                edit.putInt(String.valueOf(i), z ? 1 : 0);
                edit.apply();
                if (z) {
                    this.enabledPorts.add(Integer.valueOf(i));
                } else {
                    this.enabledPorts.remove(Integer.valueOf(i));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        notifyPortsStateUpdated(getActivePorts(), getActivePorts());
    }

    public final void clearEnabledPorts() {
        synchronized (this.lock) {
            SharedPreferences.Editor edit = this.sharedPref.edit();
            edit.clear();
            edit.apply();
            this.enabledPorts.clear();
            Unit unit = Unit.INSTANCE;
        }
        notifyPortsStateUpdated(getActivePorts(), getActivePorts());
    }

    public final void registerListener(Listener listener) {
        listener.getClass();
        synchronized (this.lock) {
            this.listeners.add(listener);
        }
    }

    public final void unregisterListener(Listener listener) {
        listener.getClass();
        synchronized (this.lock) {
            this.listeners.remove(listener);
        }
    }

    private final void notifyPortsStateUpdated(Set set, Set set2) {
        HashSet<Listener> hashSet;
        synchronized (this.lock) {
            hashSet = new HashSet<>(this.listeners);
        }
        for (Listener onPortsStateUpdated : hashSet) {
            onPortsStateUpdated.onPortsStateUpdated(new HashSet(set), new HashSet(set2));
        }
    }

    /* compiled from: PortsStateManager.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final synchronized PortsStateManager getInstance(Context context) {
            PortsStateManager access$getInstance$cp;
            try {
                context.getClass();
                if (PortsStateManager.instance == null) {
                    String packageName = context.getPackageName();
                    SharedPreferences sharedPreferences = context.getSharedPreferences(packageName + ".PORTS", 0);
                    sharedPreferences.getClass();
                    PortsStateManager.instance = new PortsStateManager(sharedPreferences, (DefaultConstructorMarker) null);
                }
                access$getInstance$cp = PortsStateManager.instance;
                access$getInstance$cp.getClass();
            } catch (Throwable th) {
                while (true) {
                    throw th;
                }
            }
            return access$getInstance$cp;
        }
    }
}
