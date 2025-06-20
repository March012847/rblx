package com.android.virtualization.terminal;

import android.os.ResultReceiver;
import kotlin.jvm.functions.Function1;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class VmLauncherService$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ MemBalloonController f$0;
    public final /* synthetic */ ResultReceiver f$1;
    public final /* synthetic */ VmLauncherService f$2;

    public /* synthetic */ VmLauncherService$$ExternalSyntheticLambda2(MemBalloonController memBalloonController, ResultReceiver resultReceiver, VmLauncherService vmLauncherService) {
        this.f$0 = memBalloonController;
        this.f$1 = resultReceiver;
        this.f$2 = vmLauncherService;
    }

    public final Object invoke(Object obj) {
        return VmLauncherService.doStart$lambda$0(this.f$0, this.f$1, this.f$2, ((Boolean) obj).booleanValue());
    }
}
