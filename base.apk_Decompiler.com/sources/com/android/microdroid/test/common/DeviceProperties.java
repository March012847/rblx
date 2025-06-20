package com.android.microdroid.test.common;

import java.util.Objects;

public final class DeviceProperties {
    private final PropertyGetter mPropertyGetter;

    public interface PropertyGetter {
        String getProperty(String str);
    }

    private DeviceProperties(PropertyGetter propertyGetter) {
        Objects.requireNonNull(propertyGetter);
        this.mPropertyGetter = propertyGetter;
    }

    public static DeviceProperties create(PropertyGetter propertyGetter) {
        return new DeviceProperties(propertyGetter);
    }

    public boolean isCuttlefish() {
        String property = getProperty("ro.product.vendor.device");
        return property != null && property.startsWith("vsoc_");
    }

    public boolean isGoldfish() {
        String property = getProperty("ro.product.vendor.device");
        return property != null && property.startsWith("emu64");
    }

    private String getProperty(String str) {
        try {
            return this.mPropertyGetter.getProperty(str);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot get property for the key: " + str, e);
        }
    }
}
