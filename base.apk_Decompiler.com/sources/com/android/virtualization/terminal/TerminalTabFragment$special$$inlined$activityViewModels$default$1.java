package com.android.virtualization.terminal;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelStore;
import kotlin.jvm.functions.Function0;

/* compiled from: FragmentViewModelLazy.kt */
public final class TerminalTabFragment$special$$inlined$activityViewModels$default$1 implements Function0 {
    final /* synthetic */ Fragment $this_activityViewModels;

    public TerminalTabFragment$special$$inlined$activityViewModels$default$1(Fragment fragment) {
        this.$this_activityViewModels = fragment;
    }

    public final ViewModelStore invoke() {
        return this.$this_activityViewModels.requireActivity().getViewModelStore();
    }
}
