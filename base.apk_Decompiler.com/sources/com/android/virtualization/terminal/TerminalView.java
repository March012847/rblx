package com.android.virtualization.terminal;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.Charsets;

/* compiled from: TerminalView.kt */
public final class TerminalView extends WebView implements AccessibilityManager.AccessibilityStateChangeListener, AccessibilityManager.TouchExplorationStateChangeListener {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final View.AccessibilityDelegate a11yEventFilter = new TerminalView$a11yEventFilter$1(this);
    private final AccessibilityManager a11yManager;
    private final AccessibilityNodeProvider a11yNodeProvider = new TerminalView$a11yNodeProvider$1(this);
    private final String ctrlKeyHandler;
    private final String disableCtrlKey;
    private final String enableCtrlKey;
    private final String terminalClose;
    private final String terminalDisconnectCallback;
    private final String touchToMouseHandler;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public TerminalView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context.getClass();
        this.ctrlKeyHandler = readAssetAsString(context, "js/ctrl_key_handler.js");
        this.enableCtrlKey = readAssetAsString(context, "js/enable_ctrl_key.js");
        this.disableCtrlKey = readAssetAsString(context, "js/disable_ctrl_key.js");
        this.terminalDisconnectCallback = readAssetAsString(context, "js/terminal_disconnect.js");
        this.terminalClose = readAssetAsString(context, "js/terminal_close.js");
        this.touchToMouseHandler = readAssetAsString(context, "js/touch_to_mouse_handler.js");
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        accessibilityManager.addTouchExplorationStateChangeListener(this);
        accessibilityManager.addAccessibilityStateChangeListener(this);
        this.a11yManager = accessibilityManager;
    }

    private final String readAssetAsString(Context context, String str) {
        byte[] readAllBytes = context.getAssets().open(str).readAllBytes();
        readAllBytes.getClass();
        return new String(readAllBytes, Charsets.UTF_8);
    }

    public final void mapTouchToMouseEvent() {
        evaluateJavascript(this.touchToMouseHandler, (ValueCallback) null);
    }

    public final void mapCtrlKey() {
        evaluateJavascript(this.ctrlKeyHandler, (ValueCallback) null);
    }

    public final void enableCtrlKey() {
        evaluateJavascript(this.enableCtrlKey, (ValueCallback) null);
    }

    public final void disableCtrlKey() {
        evaluateJavascript(this.disableCtrlKey, (ValueCallback) null);
    }

    public final void applyTerminalDisconnectCallback() {
        evaluateJavascript(this.terminalDisconnectCallback, (ValueCallback) null);
    }

    public final void terminalClose() {
        evaluateJavascript(this.terminalClose, (ValueCallback) null);
    }

    public void onAccessibilityStateChanged(boolean z) {
        Log.d("VmTerminalApp", "accessibility " + z);
        adjustToA11yStateChange();
    }

    public void onTouchExplorationStateChanged(boolean z) {
        Log.d("VmTerminalApp", "touch exploration " + z);
        adjustToA11yStateChange();
    }

    private final void adjustToA11yStateChange() {
        if (!this.a11yManager.isEnabled()) {
            setFocusable(true);
            return;
        }
        setFocusable(false);
        setFocusableInTouchMode(true);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.a11yManager.isEnabled()) {
            ViewParent parent = getParent();
            parent.getClass();
            ((View) parent).setAccessibilityDelegate(this.a11yEventFilter);
        }
    }

    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        AccessibilityNodeProvider accessibilityNodeProvider = super.getAccessibilityNodeProvider();
        return (accessibilityNodeProvider == null || !this.a11yManager.isEnabled()) ? accessibilityNodeProvider : this.a11yNodeProvider;
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
        if (editorInfo != null) {
            editorInfo.inputType = 524433;
            editorInfo.imeOptions = Integer.MIN_VALUE;
        }
        return onCreateInputConnection;
    }

    /* compiled from: TerminalView.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
