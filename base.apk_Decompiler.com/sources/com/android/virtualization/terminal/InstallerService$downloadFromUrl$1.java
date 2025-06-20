package com.android.virtualization.terminal;

import com.android.virtualization.terminal.InstallerService;
import java.io.InputStream;
import java.util.function.Function;

/* compiled from: InstallerService.kt */
final class InstallerService$downloadFromUrl$1 implements Function {
    final /* synthetic */ boolean $isWifiOnly;
    final /* synthetic */ InstallerService this$0;

    InstallerService$downloadFromUrl$1(InstallerService installerService, boolean z) {
        this.this$0 = installerService;
        this.$isWifiOnly = z;
    }

    public final InputStream apply(InputStream inputStream) {
        inputStream.getClass();
        InstallerService.WifiCheckInputStream wifiCheckInputStream = new InstallerService.WifiCheckInputStream(this.this$0, inputStream);
        wifiCheckInputStream.setWifiOnly(this.$isWifiOnly);
        return wifiCheckInputStream;
    }
}
