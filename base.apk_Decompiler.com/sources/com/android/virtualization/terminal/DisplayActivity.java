package com.android.virtualization.terminal;

import android.os.Bundle;
import android.system.virtualmachine.VirtualMachine;
import android.system.virtualmachine.VirtualMachineManager;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DisplayActivity.kt */
public final class DisplayActivity extends BaseActivity {
    private DisplayProvider displayProvider;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427356);
        SurfaceView surfaceView = (SurfaceView) findViewById(2131231240);
        SurfaceView surfaceView2 = (SurfaceView) findViewById(2131230882);
        makeFullscreen();
        surfaceView.getClass();
        surfaceView2.getClass();
        this.displayProvider = new DisplayProvider(surfaceView, surfaceView2);
        VirtualMachine virtualMachine = ((VirtualMachineManager) getApplicationContext().getSystemService(VirtualMachineManager.class)).get("debian");
        if (virtualMachine != null) {
            View findViewById = findViewById(2131230817);
            findViewById.getClass();
            View findViewById2 = findViewById(2131231240);
            findViewById2.getClass();
            View findViewById3 = findViewById(2131231240);
            findViewById3.getClass();
            new InputForwarder(this, virtualMachine, findViewById, findViewById2, findViewById3);
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        DisplayProvider displayProvider2 = this.displayProvider;
        if (displayProvider2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("displayProvider");
            displayProvider2 = null;
        }
        displayProvider2.notifyDisplayIsGoingToInvisible();
    }

    private final void makeFullscreen() {
        Window window = getWindow();
        window.setDecorFitsSystemWindows(false);
        WindowInsetsController insetsController = window.getInsetsController();
        if (insetsController != null) {
            insetsController.hide(WindowInsets.Type.systemBars());
        }
        if (insetsController != null) {
            insetsController.setSystemBarsBehavior(2);
        }
    }
}
