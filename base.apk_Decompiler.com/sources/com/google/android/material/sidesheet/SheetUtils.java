package com.google.android.material.sidesheet;

abstract class SheetUtils {
    static boolean isSwipeMostlyHorizontal(float f, float f2) {
        return Math.abs(f) > Math.abs(f2);
    }
}
