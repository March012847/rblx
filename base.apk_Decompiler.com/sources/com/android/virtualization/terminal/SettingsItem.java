package com.android.virtualization.terminal;

/* compiled from: SettingsItem.kt */
public final class SettingsItem {
    private final int icon;
    private final SettingsItemEnum settingsItemEnum;
    private final String subTitle;
    private final String title;

    public SettingsItem(String str, String str2, int i, SettingsItemEnum settingsItemEnum2) {
        str.getClass();
        str2.getClass();
        settingsItemEnum2.getClass();
        this.title = str;
        this.subTitle = str2;
        this.icon = i;
        this.settingsItemEnum = settingsItemEnum2;
    }

    public final String getTitle() {
        return this.title;
    }

    public final String getSubTitle() {
        return this.subTitle;
    }

    public final int getIcon() {
        return this.icon;
    }

    public final SettingsItemEnum getSettingsItemEnum() {
        return this.settingsItemEnum;
    }
}
