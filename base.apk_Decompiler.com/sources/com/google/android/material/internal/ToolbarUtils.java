package com.google.android.material.internal;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class ToolbarUtils {
    private static final Comparator VIEW_TOP_COMPARATOR = new Comparator() {
        public int compare(View view, View view2) {
            return view.getTop() - view2.getTop();
        }
    };

    public static TextView getTitleTextView(Toolbar toolbar) {
        List textViewsWithText = getTextViewsWithText(toolbar, toolbar.getTitle());
        if (textViewsWithText.isEmpty()) {
            return null;
        }
        return (TextView) Collections.min(textViewsWithText, VIEW_TOP_COMPARATOR);
    }

    public static TextView getSubtitleTextView(Toolbar toolbar) {
        List textViewsWithText = getTextViewsWithText(toolbar, toolbar.getSubtitle());
        if (textViewsWithText.isEmpty()) {
            return null;
        }
        return (TextView) Collections.max(textViewsWithText, VIEW_TOP_COMPARATOR);
    }

    private static List getTextViewsWithText(Toolbar toolbar, CharSequence charSequence) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View childAt = toolbar.getChildAt(i);
            if (childAt instanceof TextView) {
                TextView textView = (TextView) childAt;
                if (TextUtils.equals(textView.getText(), charSequence)) {
                    arrayList.add(textView);
                }
            }
        }
        return arrayList;
    }

    public static ImageView getLogoImageView(Toolbar toolbar) {
        return getImageView(toolbar, toolbar.getLogo());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0013, code lost:
        r2 = (android.widget.ImageView) r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.widget.ImageView getImageView(androidx.appcompat.widget.Toolbar r5, android.graphics.drawable.Drawable r6) {
        /*
            r0 = 0
            if (r6 != 0) goto L_0x0004
            return r0
        L_0x0004:
            r1 = 0
        L_0x0005:
            int r2 = r5.getChildCount()
            if (r1 >= r2) goto L_0x0033
            android.view.View r2 = r5.getChildAt(r1)
            boolean r3 = r2 instanceof android.widget.ImageView
            if (r3 == 0) goto L_0x0030
            android.widget.ImageView r2 = (android.widget.ImageView) r2
            android.graphics.drawable.Drawable r3 = r2.getDrawable()
            if (r3 == 0) goto L_0x0030
            android.graphics.drawable.Drawable$ConstantState r4 = r3.getConstantState()
            if (r4 == 0) goto L_0x0030
            android.graphics.drawable.Drawable$ConstantState r3 = r3.getConstantState()
            android.graphics.drawable.Drawable$ConstantState r4 = r6.getConstantState()
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0030
            return r2
        L_0x0030:
            int r1 = r1 + 1
            goto L_0x0005
        L_0x0033:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.internal.ToolbarUtils.getImageView(androidx.appcompat.widget.Toolbar, android.graphics.drawable.Drawable):android.widget.ImageView");
    }
}
