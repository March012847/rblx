package com.google.android.material.motion;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.AnimationUtils;
import android.view.animation.PathInterpolator;
import androidx.core.graphics.PathParser;
import androidx.dynamicanimation.animation.SpringForce;
import com.google.android.material.R$styleable;
import com.google.android.material.resources.MaterialAttributes;

public abstract class MotionUtils {
    public static SpringForce resolveThemeSpringForce(Context context, int i, int i2) {
        TypedArray typedArray;
        TypedValue resolve = MaterialAttributes.resolve(context, i);
        if (resolve == null) {
            typedArray = context.obtainStyledAttributes((AttributeSet) null, R$styleable.MaterialSpring, 0, i2);
        } else {
            typedArray = context.obtainStyledAttributes(resolve.resourceId, R$styleable.MaterialSpring);
        }
        SpringForce springForce = new SpringForce();
        try {
            float f = typedArray.getFloat(R$styleable.MaterialSpring_stiffness, Float.MIN_VALUE);
            if (f != Float.MIN_VALUE) {
                float f2 = typedArray.getFloat(R$styleable.MaterialSpring_damping, Float.MIN_VALUE);
                if (f2 != Float.MIN_VALUE) {
                    springForce.setStiffness(f);
                    springForce.setDampingRatio(f2);
                    return springForce;
                }
                throw new IllegalArgumentException("A MaterialSpring style must have a damping value.");
            }
            throw new IllegalArgumentException("A MaterialSpring style must have stiffness value.");
        } finally {
            typedArray.recycle();
        }
    }

    public static int resolveThemeDuration(Context context, int i, int i2) {
        return MaterialAttributes.resolveInteger(context, i, i2);
    }

    public static TimeInterpolator resolveThemeInterpolator(Context context, int i, TimeInterpolator timeInterpolator) {
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(i, typedValue, true)) {
            return timeInterpolator;
        }
        if (typedValue.type == 3) {
            String valueOf = String.valueOf(typedValue.string);
            if (isLegacyEasingAttribute(valueOf)) {
                return getLegacyThemeInterpolator(valueOf);
            }
            return AnimationUtils.loadInterpolator(context, typedValue.resourceId);
        }
        throw new IllegalArgumentException("Motion easing theme attribute must be an @interpolator resource for ?attr/motionEasing*Interpolator attributes or a string for ?attr/motionEasing* attributes.");
    }

    private static TimeInterpolator getLegacyThemeInterpolator(String str) {
        if (isLegacyEasingType(str, "cubic-bezier")) {
            String[] split = getLegacyEasingContent(str, "cubic-bezier").split(",");
            if (split.length == 4) {
                return new PathInterpolator(getLegacyControlPoint(split, 0), getLegacyControlPoint(split, 1), getLegacyControlPoint(split, 2), getLegacyControlPoint(split, 3));
            }
            throw new IllegalArgumentException("Motion easing theme attribute must have 4 control points if using bezier curve format; instead got: " + split.length);
        } else if (isLegacyEasingType(str, "path")) {
            return new PathInterpolator(PathParser.createPathFromPathData(getLegacyEasingContent(str, "path")));
        } else {
            throw new IllegalArgumentException("Invalid motion easing type: " + str);
        }
    }

    private static boolean isLegacyEasingAttribute(String str) {
        return isLegacyEasingType(str, "cubic-bezier") || isLegacyEasingType(str, "path");
    }

    private static boolean isLegacyEasingType(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append("(");
        return str.startsWith(sb.toString()) && str.endsWith(")");
    }

    private static String getLegacyEasingContent(String str, String str2) {
        return str.substring(str2.length() + 1, str.length() - 1);
    }

    private static float getLegacyControlPoint(String[] strArr, int i) {
        float parseFloat = Float.parseFloat(strArr[i]);
        if (parseFloat >= 0.0f && parseFloat <= 1.0f) {
            return parseFloat;
        }
        throw new IllegalArgumentException("Motion easing control point value must be between 0 and 1; instead got: " + parseFloat);
    }
}
