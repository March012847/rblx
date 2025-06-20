package com.google.android.material.color;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.resources.MaterialAttributes;

public abstract class MaterialColors {
    public static int getColor(View view, int i) {
        return resolveColor(view.getContext(), MaterialAttributes.resolveTypedValueOrThrow(view, i));
    }

    public static int getColor(Context context, int i, String str) {
        return resolveColor(context, MaterialAttributes.resolveTypedValueOrThrow(context, i, str));
    }

    public static int getColor(View view, int i, int i2) {
        return getColor(view.getContext(), i, i2);
    }

    public static int getColor(Context context, int i, int i2) {
        Integer colorOrNull = getColorOrNull(context, i);
        return colorOrNull != null ? colorOrNull.intValue() : i2;
    }

    public static Integer getColorOrNull(Context context, int i) {
        TypedValue resolve = MaterialAttributes.resolve(context, i);
        if (resolve != null) {
            return Integer.valueOf(resolveColor(context, resolve));
        }
        return null;
    }

    public static ColorStateList getColorStateListOrNull(Context context, int i) {
        TypedValue resolve = MaterialAttributes.resolve(context, i);
        if (resolve == null) {
            return null;
        }
        int i2 = resolve.resourceId;
        if (i2 != 0) {
            return ContextCompat.getColorStateList(context, i2);
        }
        int i3 = resolve.data;
        if (i3 != 0) {
            return ColorStateList.valueOf(i3);
        }
        return null;
    }

    private static int resolveColor(Context context, TypedValue typedValue) {
        int i = typedValue.resourceId;
        if (i != 0) {
            return ContextCompat.getColor(context, i);
        }
        return typedValue.data;
    }

    public static int layer(View view, int i, int i2, float f) {
        return layer(getColor(view, i), getColor(view, i2), f);
    }

    public static int layer(int i, int i2, float f) {
        return layer(i, ColorUtils.setAlphaComponent(i2, Math.round(((float) Color.alpha(i2)) * f)));
    }

    public static int layer(int i, int i2) {
        return ColorUtils.compositeColors(i2, i);
    }

    public static int compositeARGBWithAlpha(int i, int i2) {
        return ColorUtils.setAlphaComponent(i, (Color.alpha(i) * i2) / 255);
    }

    public static boolean isColorLight(int i) {
        return i != 0 && ColorUtils.calculateLuminance(i) > 0.5d;
    }
}
