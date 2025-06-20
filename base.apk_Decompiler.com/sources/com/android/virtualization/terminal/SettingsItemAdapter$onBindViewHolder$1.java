package com.android.virtualization.terminal;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.android.virtualization.terminal.SettingsItemAdapter;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: SettingsItemAdapter.kt */
final class SettingsItemAdapter$onBindViewHolder$1 implements View.OnClickListener {
    final /* synthetic */ int $position;
    final /* synthetic */ SettingsItemAdapter.ViewHolder $viewHolder;
    final /* synthetic */ SettingsItemAdapter this$0;

    /* compiled from: SettingsItemAdapter.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        /* JADX WARNING: Can't wrap try/catch for region: R(9:0|1|2|3|4|5|6|7|9) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0010 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0019 */
        static {
            /*
                com.android.virtualization.terminal.SettingsItemEnum[] r0 = com.android.virtualization.terminal.SettingsItemEnum.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                com.android.virtualization.terminal.SettingsItemEnum r1 = com.android.virtualization.terminal.SettingsItemEnum.DiskResize     // Catch:{ NoSuchFieldError -> 0x0010 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0010 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0010 }
            L_0x0010:
                com.android.virtualization.terminal.SettingsItemEnum r1 = com.android.virtualization.terminal.SettingsItemEnum.PortForwarding     // Catch:{ NoSuchFieldError -> 0x0019 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0019 }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0019 }
            L_0x0019:
                com.android.virtualization.terminal.SettingsItemEnum r1 = com.android.virtualization.terminal.SettingsItemEnum.Recovery     // Catch:{ NoSuchFieldError -> 0x0022 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0022 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0022 }
            L_0x0022:
                $EnumSwitchMapping$0 = r0
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.SettingsItemAdapter$onBindViewHolder$1.WhenMappings.<clinit>():void");
        }
    }

    SettingsItemAdapter$onBindViewHolder$1(SettingsItemAdapter.ViewHolder viewHolder, SettingsItemAdapter settingsItemAdapter, int i) {
        this.$viewHolder = viewHolder;
        this.this$0 = settingsItemAdapter;
        this.$position = i;
    }

    public final void onClick(View view) {
        Class cls;
        Context context = this.$viewHolder.itemView.getContext();
        int i = WhenMappings.$EnumSwitchMapping$0[((SettingsItem) this.this$0.dataSet.get(this.$position)).getSettingsItemEnum().ordinal()];
        if (i == 1) {
            cls = SettingsDiskResizeActivity.class;
        } else if (i == 2) {
            cls = SettingsPortForwardingActivity.class;
        } else if (i == 3) {
            cls = SettingsRecoveryActivity.class;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        view.getContext().startActivity(new Intent(context, cls));
    }
}
