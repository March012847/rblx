package com.android.virtualization.terminal;

import android.net.nsd.NsdServiceInfo;
import android.os.ResultReceiver;
import kotlin.jvm.functions.Function1;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class VmLauncherService$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ ResultReceiver f$0;
    public final /* synthetic */ VmLauncherService f$1;

    public /* synthetic */ VmLauncherService$$ExternalSyntheticLambda3(ResultReceiver resultReceiver, VmLauncherService vmLauncherService) {
        this.f$0 = resultReceiver;
        this.f$1 = vmLauncherService;
    }

    public final Object invoke(Object obj) {
        return VmLauncherService.doStart$lambda$1(this.f$0, this.f$1, (NsdServiceInfo) obj);
    }
}
