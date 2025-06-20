package com.android.virtualization.terminal;

import android.os.ResultReceiver;
import kotlin.jvm.functions.Function1;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class VmLauncherService$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ ResultReceiver f$0;

    public /* synthetic */ VmLauncherService$$ExternalSyntheticLambda0(ResultReceiver resultReceiver) {
        this.f$0 = resultReceiver;
    }

    public final Object invoke(Object obj) {
        return VmLauncherService.doShutdown$lambda$2(this.f$0, ((Boolean) obj).booleanValue());
    }
}
