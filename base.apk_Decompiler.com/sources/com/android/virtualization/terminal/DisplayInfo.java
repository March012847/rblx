package com.android.virtualization.terminal;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: VmLauncherService.kt */
public final class DisplayInfo implements Parcelable {
    public static final Parcelable.Creator<DisplayInfo> CREATOR = new DisplayInfo$Companion$CREATOR$1();
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final int dpi;
    private final int height;
    private final int refreshRate;
    private final int width;

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DisplayInfo)) {
            return false;
        }
        DisplayInfo displayInfo = (DisplayInfo) obj;
        return this.width == displayInfo.width && this.height == displayInfo.height && this.dpi == displayInfo.dpi && this.refreshRate == displayInfo.refreshRate;
    }

    public int hashCode() {
        return (((((Integer.hashCode(this.width) * 31) + Integer.hashCode(this.height)) * 31) + Integer.hashCode(this.dpi)) * 31) + Integer.hashCode(this.refreshRate);
    }

    public String toString() {
        int i = this.width;
        int i2 = this.height;
        int i3 = this.dpi;
        int i4 = this.refreshRate;
        return "DisplayInfo(width=" + i + ", height=" + i2 + ", dpi=" + i3 + ", refreshRate=" + i4 + ")";
    }

    public DisplayInfo(int i, int i2, int i3, int i4) {
        this.width = i;
        this.height = i2;
        this.dpi = i3;
        this.refreshRate = i4;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public DisplayInfo(Parcel parcel) {
        this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        parcel.getClass();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.getClass();
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeInt(this.dpi);
        parcel.writeInt(this.refreshRate);
    }

    /* compiled from: VmLauncherService.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
