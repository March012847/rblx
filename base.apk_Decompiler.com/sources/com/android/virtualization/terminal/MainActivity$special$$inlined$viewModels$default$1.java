package com.android.virtualization.terminal;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;
import kotlin.jvm.functions.Function0;

/* compiled from: ActivityViewModelLazy.kt */
public final class MainActivity$special$$inlined$viewModels$default$1 implements Function0 {
    final /* synthetic */ ComponentActivity $this_viewModels;

    public MainActivity$special$$inlined$viewModels$default$1(ComponentActivity componentActivity) {
        this.$this_viewModels = componentActivity;
    }

    public final ViewModelProvider.Factory invoke() {
        return this.$this_viewModels.getDefaultViewModelProviderFactory();
    }
}
