package com.android.virtualization.terminal;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TerminalView.kt */
public final class TerminalView$a11yNodeProvider$1 extends AccessibilityNodeProvider {
    final /* synthetic */ TerminalView this$0;

    TerminalView$a11yNodeProvider$1(TerminalView terminalView) {
        this.this$0 = terminalView;
    }

    private final AccessibilityNodeProvider getParent() {
        return TerminalView$a11yNodeProvider$1.super.getAccessibilityNodeProvider();
    }

    private final String getString(int i) {
        String string = this.this$0.getContext().getResources().getString(i);
        string.getClass();
        return string;
    }

    private final boolean isEmptyLine(AccessibilityNodeInfo accessibilityNodeInfo) {
        CharSequence text = accessibilityNodeInfo.getText();
        if (text == null) {
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            if (!TextUtils.isWhitespace(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
        AccessibilityNodeProvider parent = getParent();
        AccessibilityNodeInfo createAccessibilityNodeInfo = parent != null ? parent.createAccessibilityNodeInfo(i) : null;
        if (createAccessibilityNodeInfo == null) {
            return null;
        }
        String obj = createAccessibilityNodeInfo.getClassName().toString();
        if (!Intrinsics.areEqual("android.widget.EditText", obj)) {
            createAccessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
        }
        switch (obj.hashCode()) {
            case -149114526:
                if (obj.equals("android.widget.EditText")) {
                    createAccessibilityNodeInfo.setText((CharSequence) null);
                    createAccessibilityNodeInfo.setHintText(getString(2131689532));
                    createAccessibilityNodeInfo.setContentDescription(getString(2131689727));
                    createAccessibilityNodeInfo.setScreenReaderFocusable(true);
                    createAccessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_FOCUS);
                    return createAccessibilityNodeInfo;
                }
                break;
            case 66104940:
                if (obj.equals("android.webkit.WebView")) {
                    if (i != -1) {
                        createAccessibilityNodeInfo.setText((CharSequence) null);
                        createAccessibilityNodeInfo.setContentDescription(getString(2131689726));
                        createAccessibilityNodeInfo.setHintText(getString(2131689532));
                    }
                    createAccessibilityNodeInfo.setScreenReaderFocusable(false);
                    createAccessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_ACCESSIBILITY_FOCUS);
                    return createAccessibilityNodeInfo;
                }
                break;
            case 1540240509:
                if (obj.equals("android.widget.TextView")) {
                    Rect rect = new Rect();
                    createAccessibilityNodeInfo.getBoundsInScreen(rect);
                    if (rect.width() == 0) {
                        createAccessibilityNodeInfo.setText((CharSequence) null);
                        createAccessibilityNodeInfo.setContentDescription(getString(2131689533));
                    }
                    createAccessibilityNodeInfo.setScreenReaderFocusable(false);
                    return createAccessibilityNodeInfo;
                }
                break;
            case 1815484335:
                if (obj.equals("android.view.View") && isEmptyLine(createAccessibilityNodeInfo)) {
                    createAccessibilityNodeInfo.setContentDescription(getString(2131689533));
                    createAccessibilityNodeInfo.setHintText(getString(2131689532));
                    return createAccessibilityNodeInfo;
                }
        }
        return createAccessibilityNodeInfo;
    }

    public boolean performAction(int i, int i2, Bundle bundle) {
        AccessibilityNodeProvider parent = getParent();
        return parent != null && parent.performAction(i, i2, bundle);
    }

    public void addExtraDataToAccessibilityNodeInfo(int i, AccessibilityNodeInfo accessibilityNodeInfo, String str, Bundle bundle) {
        AccessibilityNodeProvider parent = getParent();
        if (parent != null) {
            parent.addExtraDataToAccessibilityNodeInfo(i, accessibilityNodeInfo, str, bundle);
        }
    }

    public List findAccessibilityNodeInfosByText(String str, int i) {
        AccessibilityNodeProvider parent = getParent();
        if (parent != null) {
            return parent.findAccessibilityNodeInfosByText(str, i);
        }
        return null;
    }

    public AccessibilityNodeInfo findFocus(int i) {
        AccessibilityNodeProvider parent = getParent();
        if (parent != null) {
            return parent.findFocus(i);
        }
        return null;
    }
}
