package com.android.virtualization.terminal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: ModifierKeysController.kt */
public final class ModifierKeysController {
    /* access modifiers changed from: private */
    public static final Map BTN_KEY_CODE_MAP = MapsKt.mapOf(TuplesKt.to(2131230842, 61), TuplesKt.to(2131230832, 111), TuplesKt.to(2131230836, 111), TuplesKt.to(2131230838, 21), TuplesKt.to(2131230841, 22), TuplesKt.to(2131230843, 19), TuplesKt.to(2131230834, 20), TuplesKt.to(2131230837, 122), TuplesKt.to(2131230835, 123), TuplesKt.to(2131230840, 92), TuplesKt.to(2131230839, 93));
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public TerminalView activeTerminalView;
    private final MainActivity activity;
    private final View keysDoubleLine;
    private boolean keysInSingleLine;
    private final View keysSingleLine;
    private final ViewGroup parent;
    private final Window window;

    public ModifierKeysController(MainActivity mainActivity, ViewGroup viewGroup) {
        mainActivity.getClass();
        viewGroup.getClass();
        this.activity = mainActivity;
        this.parent = viewGroup;
        Window window2 = mainActivity.getWindow();
        this.window = window2;
        LayoutInflater from = LayoutInflater.from(mainActivity);
        View inflate = from.inflate(2131427402, viewGroup, false);
        this.keysSingleLine = inflate;
        View inflate2 = from.inflate(2131427401, viewGroup, false);
        this.keysDoubleLine = inflate2;
        addClickListeners(inflate);
        addClickListeners(inflate2);
        inflate.setVisibility(8);
        inflate2.setVisibility(8);
        viewGroup.addView(inflate2);
        window2.getDecorView().getRootView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                windowInsets.getClass();
                ModifierKeysController.this.update();
                return windowInsets;
            }
        });
    }

    public final void addTerminalView(TerminalView terminalView) {
        terminalView.getClass();
        terminalView.setOnFocusChangeListener(new ModifierKeysController$addTerminalView$1(this, terminalView));
    }

    private final void addClickListeners(View view) {
        view.findViewById(2131230833).setOnClickListener(new ModifierKeysController$addClickListeners$1(this));
        ModifierKeysController$addClickListeners$listener$1 modifierKeysController$addClickListeners$listener$1 = new ModifierKeysController$addClickListeners$listener$1(this);
        for (Number intValue : BTN_KEY_CODE_MAP.keySet()) {
            view.findViewById(intValue.intValue()).setOnClickListener(modifierKeysController$addClickListeners$listener$1);
        }
    }

    public final void update() {
        int i = 8;
        if (this.activeTerminalView == null) {
            (this.keysInSingleLine ? this.keysSingleLine : this.keysDoubleLine).setVisibility(8);
            return;
        }
        boolean needsKeysInSingleLine = needsKeysInSingleLine();
        if (this.keysInSingleLine != needsKeysInSingleLine) {
            if (needsKeysInSingleLine) {
                this.parent.removeView(this.keysDoubleLine);
                this.parent.addView(this.keysSingleLine);
            } else {
                this.parent.removeView(this.keysSingleLine);
                this.parent.addView(this.keysDoubleLine);
            }
            this.keysInSingleLine = needsKeysInSingleLine;
        }
        boolean needToShowKeys = needToShowKeys();
        View view = this.keysInSingleLine ? this.keysSingleLine : this.keysDoubleLine;
        if (needToShowKeys) {
            i = 0;
        }
        view.setVisibility(i);
    }

    private final boolean needToShowKeys() {
        if (!this.activity.getWindow().getDecorView().getRootWindowInsets().isVisible(WindowInsets.Type.ime())) {
            return false;
        }
        TerminalView terminalView = this.activeTerminalView;
        terminalView.getClass();
        return terminalView.hasFocus() && this.activity.getResources().getConfiguration().keyboard != 2;
    }

    private final boolean needsKeysInSingleLine() {
        View view = this.keysInSingleLine ? this.keysSingleLine : this.keysDoubleLine;
        TerminalView terminalView = this.activeTerminalView;
        terminalView.getClass();
        return ((float) (terminalView.getHeight() + view.getHeight())) < ((float) this.activity.getWindow().getDecorView().getHeight()) * 0.4f;
    }

    /* compiled from: ModifierKeysController.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
