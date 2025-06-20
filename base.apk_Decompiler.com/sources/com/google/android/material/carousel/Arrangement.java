package com.google.android.material.carousel;

import androidx.core.math.MathUtils;

public final class Arrangement {
    final float cost;
    final int largeCount;
    float largeSize;
    int mediumCount;
    float mediumSize;
    final int priority;
    int smallCount;
    float smallSize;

    private float calculateLargeSize(float f, int i, float f2, int i2, int i3) {
        if (i <= 0) {
            f2 = 0.0f;
        }
        float f3 = (float) i;
        float f4 = ((float) i2) / 2.0f;
        return (f - ((f3 + f4) * f2)) / (((float) i3) + f4);
    }

    public Arrangement(int i, float f, float f2, float f3, int i2, float f4, int i3, float f5, int i4, float f6) {
        this.priority = i;
        this.smallSize = MathUtils.clamp(f, f2, f3);
        this.smallCount = i2;
        this.mediumSize = f4;
        this.mediumCount = i3;
        this.largeSize = f5;
        this.largeCount = i4;
        fit(f6, f2, f3, f5);
        this.cost = cost(f5);
    }

    public String toString() {
        return "Arrangement [priority=" + this.priority + ", smallCount=" + this.smallCount + ", smallSize=" + this.smallSize + ", mediumCount=" + this.mediumCount + ", mediumSize=" + this.mediumSize + ", largeCount=" + this.largeCount + ", largeSize=" + this.largeSize + ", cost=" + this.cost + "]";
    }

    private float getSpace() {
        return (this.largeSize * ((float) this.largeCount)) + (this.mediumSize * ((float) this.mediumCount)) + (this.smallSize * ((float) this.smallCount));
    }

    private void fit(float f, float f2, float f3, float f4) {
        float space = f - getSpace();
        int i = this.smallCount;
        if (i > 0 && space > 0.0f) {
            float f5 = this.smallSize;
            this.smallSize = f5 + Math.min(space / ((float) i), f3 - f5);
        } else if (i > 0 && space < 0.0f) {
            float f6 = this.smallSize;
            this.smallSize = f6 + Math.max(space / ((float) i), f2 - f6);
        }
        int i2 = this.smallCount;
        float f7 = i2 > 0 ? this.smallSize : 0.0f;
        this.smallSize = f7;
        float calculateLargeSize = calculateLargeSize(f, i2, f7, this.mediumCount, this.largeCount);
        this.largeSize = calculateLargeSize;
        float f8 = (this.smallSize + calculateLargeSize) / 2.0f;
        this.mediumSize = f8;
        int i3 = this.mediumCount;
        if (i3 > 0 && calculateLargeSize != f4) {
            float f9 = (f4 - calculateLargeSize) * ((float) this.largeCount);
            float min = Math.min(Math.abs(f9), f8 * 0.1f * ((float) i3));
            if (f9 > 0.0f) {
                this.mediumSize -= min / ((float) this.mediumCount);
                this.largeSize += min / ((float) this.largeCount);
                return;
            }
            this.mediumSize += min / ((float) this.mediumCount);
            this.largeSize -= min / ((float) this.largeCount);
        }
    }

    private boolean isValid() {
        int i = this.largeCount;
        if (i <= 0 || this.smallCount <= 0 || this.mediumCount <= 0) {
            return i <= 0 || this.smallCount <= 0 || this.largeSize > this.smallSize;
        }
        float f = this.largeSize;
        float f2 = this.mediumSize;
        return f > f2 && f2 > this.smallSize;
    }

    private float cost(float f) {
        if (!isValid()) {
            return Float.MAX_VALUE;
        }
        return Math.abs(f - this.largeSize) * ((float) this.priority);
    }

    public static Arrangement findLowestCostArrangement(float f, float f2, float f3, float f4, int[] iArr, float f5, int[] iArr2, float f6, int[] iArr3) {
        int[] iArr4 = iArr;
        int[] iArr5 = iArr2;
        Arrangement arrangement = null;
        int i = 1;
        for (int i2 : iArr3) {
            int length = iArr5.length;
            int i3 = 0;
            while (i3 < length) {
                int i4 = iArr5[i3];
                int length2 = iArr4.length;
                int i5 = 0;
                while (i5 < length2) {
                    int i6 = i3;
                    int i7 = i;
                    int i8 = i6;
                    int i9 = length2;
                    int i10 = i5;
                    int i11 = length;
                    Arrangement arrangement2 = new Arrangement(i7, f2, f3, f4, iArr4[i5], f5, i4, f6, i2, f);
                    if (arrangement == null || arrangement2.cost < arrangement.cost) {
                        if (arrangement2.cost == 0.0f) {
                            return arrangement2;
                        }
                        arrangement = arrangement2;
                    }
                    int i12 = i7 + 1;
                    i5 = i10 + 1;
                    i3 = i8;
                    i = i12;
                    length = i11;
                    length2 = i9;
                }
                int i13 = i3;
                int i14 = i;
                int i15 = i13;
                int i16 = length;
                int i17 = i14;
                i3 = i15 + 1;
                i = i17;
                length = i16;
            }
        }
        return arrangement;
    }

    /* access modifiers changed from: package-private */
    public int getItemCount() {
        return this.smallCount + this.mediumCount + this.largeCount;
    }
}
