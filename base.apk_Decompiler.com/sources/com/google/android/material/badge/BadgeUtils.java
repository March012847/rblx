package com.google.android.material.badge;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.material.R$dimen;

public abstract class BadgeUtils {
    public static void detachBadgeDrawable(BadgeDrawable badgeDrawable, View view) {
    }

    public static void attachBadgeDrawable(BadgeDrawable badgeDrawable, View view, FrameLayout frameLayout) {
        setBadgeDrawableBounds(badgeDrawable, view, frameLayout);
        throw null;
    }

    static void setToolbarOffset(BadgeDrawable badgeDrawable, Resources resources) {
        resources.getDimensionPixelOffset(R$dimen.mtrl_badge_toolbar_action_menu_item_horizontal_offset);
        throw null;
    }

    static void removeToolbarOffset(BadgeDrawable badgeDrawable) {
        throw null;
    }

    public static void setBadgeDrawableBounds(BadgeDrawable badgeDrawable, View view, FrameLayout frameLayout) {
        view.getDrawingRect(new Rect());
        throw null;
    }
}
