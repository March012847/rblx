package com.android.virtualization.terminal;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelStore;
import kotlin.jvm.functions.Function0;

/* compiled from: ActivityViewModelLazy.kt */
public final class MainActivity$special$$inlined$viewModels$default$2 implements Function0 {
    final /* synthetic */ ComponentActivity $this_viewModels;

    public MainActivity$special$$inlined$viewModels$default$2(ComponentActivity componentActivity) {
        this.$this_viewModels = componentActivity;
    }

    public final ViewModelStore invoke() {
        return this.$this_viewModels.getViewModelStore();
    }
}
