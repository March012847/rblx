package com.android.virtualization.terminal;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: VmLauncherService.kt */
public final class DisplayInfo$Companion$CREATOR$1 implements Parcelable.Creator {
    DisplayInfo$Companion$CREATOR$1() {
    }

    public DisplayInfo createFromParcel(Parcel parcel) {
        parcel.getClass();
        return new DisplayInfo(parcel);
    }

    public DisplayInfo[] newArray(int i) {
        return new DisplayInfo[i];
    }
}
