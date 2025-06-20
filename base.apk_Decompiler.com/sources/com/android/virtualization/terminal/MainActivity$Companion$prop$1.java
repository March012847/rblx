package com.android.virtualization.terminal;

import android.os.SystemProperties;
import com.android.microdroid.test.common.DeviceProperties;

/* compiled from: MainActivity.kt */
final class MainActivity$Companion$prop$1 implements DeviceProperties.PropertyGetter {
    public static final MainActivity$Companion$prop$1 INSTANCE = new MainActivity$Companion$prop$1();

    MainActivity$Companion$prop$1() {
    }

    public final String getProperty(String str) {
        str.getClass();
        return SystemProperties.get(str);
    }
}
