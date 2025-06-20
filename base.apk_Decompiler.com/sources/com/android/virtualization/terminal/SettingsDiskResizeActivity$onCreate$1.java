package com.android.virtualization.terminal;

import android.view.View;
import android.widget.SeekBar;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SettingsDiskResizeActivity.kt */
public final class SettingsDiskResizeActivity$onCreate$1 implements SeekBar.OnSeekBarChangeListener {
    final /* synthetic */ SettingsDiskResizeActivity this$0;

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    SettingsDiskResizeActivity$onCreate$1(SettingsDiskResizeActivity settingsDiskResizeActivity) {
        this.this$0 = settingsDiskResizeActivity;
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        seekBar.getClass();
        SettingsDiskResizeActivity settingsDiskResizeActivity = this.this$0;
        settingsDiskResizeActivity.updateSliderText(settingsDiskResizeActivity.progressToMb(i));
        View access$getButtons$p = this.this$0.buttons;
        View view = null;
        if (access$getButtons$p == null) {
            Intrinsics.throwUninitializedPropertyAccessException("buttons");
            access$getButtons$p = null;
        }
        access$getButtons$p.setVisibility(0);
        View access$getCancelButton$p = this.this$0.cancelButton;
        if (access$getCancelButton$p == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            access$getCancelButton$p = null;
        }
        access$getCancelButton$p.setVisibility(0);
        View access$getResizeButton$p = this.this$0.resizeButton;
        if (access$getResizeButton$p == null) {
            Intrinsics.throwUninitializedPropertyAccessException("resizeButton");
        } else {
            view = access$getResizeButton$p;
        }
        view.setVisibility(0);
    }
}
