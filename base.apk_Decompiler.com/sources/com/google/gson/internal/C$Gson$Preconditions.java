package com.google.gson.internal;

/* renamed from: com.google.gson.internal.$Gson$Preconditions  reason: invalid class name */
public abstract class C$Gson$Preconditions {
    public static void checkArgument(boolean z) {
        if (!z) {
            throw new IllegalArgumentException();
        }
    }
}
