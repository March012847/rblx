package com.android.virtualization.terminal;

import androidx.activity.ComponentActivity;
import kotlin.jvm.functions.Function0;

/* compiled from: ActivityViewModelLazy.kt */
public final class MainActivity$special$$inlined$viewModels$default$3 implements Function0 {
    final /* synthetic */ Function0 $extrasProducer;
    final /* synthetic */ ComponentActivity $this_viewModels;

    public MainActivity$special$$inlined$viewModels$default$3(Function0 function0, ComponentActivity componentActivity) {
        this.$extrasProducer = function0;
        this.$this_viewModels = componentActivity;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = (androidx.lifecycle.viewmodel.CreationExtras) r0.invoke();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final androidx.lifecycle.viewmodel.CreationExtras invoke() {
        /*
            r1 = this;
            kotlin.jvm.functions.Function0 r0 = r1.$extrasProducer
            if (r0 == 0) goto L_0x000e
            java.lang.Object r0 = r0.invoke()
            androidx.lifecycle.viewmodel.CreationExtras r0 = (androidx.lifecycle.viewmodel.CreationExtras) r0
            if (r0 != 0) goto L_0x000d
            goto L_0x000e
        L_0x000d:
            return r0
        L_0x000e:
            androidx.activity.ComponentActivity r1 = r1.$this_viewModels
            androidx.lifecycle.viewmodel.CreationExtras r1 = r1.getDefaultViewModelCreationExtras()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.MainActivity$special$$inlined$viewModels$default$3.invoke():androidx.lifecycle.viewmodel.CreationExtras");
    }
}
