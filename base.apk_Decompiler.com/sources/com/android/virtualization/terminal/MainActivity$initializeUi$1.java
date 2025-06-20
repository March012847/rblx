package com.android.virtualization.terminal;

import android.content.Intent;
import android.view.View;

/* compiled from: MainActivity.kt */
final class MainActivity$initializeUi$1 implements View.OnClickListener {
    final /* synthetic */ MainActivity this$0;

    MainActivity$initializeUi$1(MainActivity mainActivity) {
        this.this$0 = mainActivity;
    }

    public final void onClick(View view) {
        this.this$0.startActivity(new Intent(this.this$0, SettingsActivity.class));
    }
}
