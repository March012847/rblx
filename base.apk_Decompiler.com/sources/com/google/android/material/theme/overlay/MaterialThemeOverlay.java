package com.google.android.material.theme.overlay;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.R$attr;
import androidx.appcompat.view.ContextThemeWrapper;

public abstract class MaterialThemeOverlay {
    private static final int[] ANDROID_THEME_OVERLAY_ATTRS = {16842752, R$attr.theme};
    private static final int[] MATERIAL_THEME_OVERLAY_ATTR = {com.google.android.material.R$attr.materialThemeOverlay};

    public static Context wrap(Context context, AttributeSet attributeSet, int i, int i2) {
        return wrap(context, attributeSet, i, i2, new int[0]);
    }

    public static Context wrap(Context context, AttributeSet attributeSet, int i, int i2, int[] iArr) {
        int obtainMaterialThemeOverlayId = obtainMaterialThemeOverlayId(context, attributeSet, i, i2);
        boolean z = (context instanceof ContextThemeWrapper) && ((ContextThemeWrapper) context).getThemeResId() == obtainMaterialThemeOverlayId;
        if (obtainMaterialThemeOverlayId == 0 || z) {
            return context;
        }
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, obtainMaterialThemeOverlayId);
        for (int i3 : obtainMaterialOverlayIds(context, attributeSet, iArr, i, i2)) {
            if (i3 != 0) {
                contextThemeWrapper = new ContextThemeWrapper((Context) contextThemeWrapper, i3);
            }
        }
        int obtainAndroidThemeOverlayId = obtainAndroidThemeOverlayId(context, attributeSet);
        if (obtainAndroidThemeOverlayId != 0) {
            contextThemeWrapper.getTheme().applyStyle(obtainAndroidThemeOverlayId, true);
        }
        return contextThemeWrapper;
    }

    private static int obtainAndroidThemeOverlayId(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ANDROID_THEME_OVERLAY_ATTRS);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        int resourceId2 = obtainStyledAttributes.getResourceId(1, 0);
        obtainStyledAttributes.recycle();
        return resourceId != 0 ? resourceId : resourceId2;
    }

    private static int obtainMaterialThemeOverlayId(Context context, AttributeSet attributeSet, int i, int i2) {
        return obtainMaterialOverlayIds(context, attributeSet, MATERIAL_THEME_OVERLAY_ATTR, i, i2)[0];
    }

    private static int[] obtainMaterialOverlayIds(Context context, AttributeSet attributeSet, int[] iArr, int i, int i2) {
        int[] iArr2 = new int[iArr.length];
        if (iArr.length > 0) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, i2);
            for (int i3 = 0; i3 < iArr.length; i3++) {
                iArr2[i3] = obtainStyledAttributes.getResourceId(i3, 0);
            }
            obtainStyledAttributes.recycle();
        }
        return iArr2;
    }
}
