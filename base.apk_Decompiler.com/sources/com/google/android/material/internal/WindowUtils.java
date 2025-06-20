package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Rect;
import android.view.WindowManager;

public abstract class WindowUtils {
    public static Rect getCurrentWindowBounds(Context context) {
        return Api30Impl.getCurrentWindowBounds((WindowManager) context.getSystemService("window"));
    }

    abstract class Api30Impl {
        static Rect getCurrentWindowBounds(WindowManager windowManager) {
            return windowManager.getCurrentWindowMetrics().getBounds();
        }
    }
}
