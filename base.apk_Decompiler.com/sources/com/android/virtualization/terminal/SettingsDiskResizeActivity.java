package com.android.virtualization.terminal;

import android.icu.text.MeasureFormat;
import android.icu.text.NumberFormat;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: SettingsDiskResizeActivity.kt */
public final class SettingsDiskResizeActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public View buttons;
    /* access modifiers changed from: private */
    public View cancelButton;
    private final long defaultHostReserveSizeMb = 5120;
    private TextView diskMaxSizeText;
    /* access modifiers changed from: private */
    public long diskSizeMb;
    private SeekBar diskSizeSlider;
    private long diskSizeStepMb;
    private TextView diskSizeText;
    private final Pattern numberPattern;
    /* access modifiers changed from: private */
    public View resizeButton;

    private final long bytesToMb(long j) {
        return j >> 20;
    }

    /* access modifiers changed from: private */
    public final long mbToBytes(long j) {
        return j << 20;
    }

    public SettingsDiskResizeActivity() {
        Pattern compile = Pattern.compile("[\\d]*[\\٫.,]?[\\d]+");
        compile.getClass();
        this.numberPattern = compile;
    }

    private final long getAvailableSizeMb() {
        Object systemService = getApplicationContext().getSystemService("storage");
        systemService.getClass();
        return this.diskSizeMb + bytesToMb(((StorageManager) systemService).getAllocatableBytes(StorageManager.UUID_DEFAULT));
    }

    private final int mbToProgress(long j) {
        return (int) (j / this.diskSizeStepMb);
    }

    /* access modifiers changed from: private */
    public final long progressToMb(int i) {
        return ((long) i) * this.diskSizeStepMb;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427445);
        this.diskSizeStepMb = 1 << getResources().getInteger(2131296264);
        InstalledImage installedImage = InstalledImage.Companion.getDefault(this);
        this.diskSizeMb = bytesToMb(installedImage.getApparentSize());
        long coerceAtMost = RangesKt.coerceAtMost(bytesToMb(installedImage.getSmallestSizePossible()), this.diskSizeMb);
        long availableSizeMb = getAvailableSizeMb();
        long j = this.defaultHostReserveSizeMb;
        if (availableSizeMb > j) {
            availableSizeMb -= j;
        }
        View findViewById = findViewById(2131231177);
        findViewById.getClass();
        this.diskSizeText = (TextView) findViewById;
        TextView textView = (TextView) findViewById(2131231178);
        this.diskMaxSizeText = textView;
        View view = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("diskMaxSizeText");
            textView = null;
        }
        textView.setText(getString(2131689685, new Object[]{localizedFileSize(availableSizeMb, true)}));
        this.buttons = findViewById(2131230845);
        View findViewById2 = findViewById(2131231175);
        findViewById2.getClass();
        this.diskSizeSlider = (SeekBar) findViewById2;
        this.cancelButton = findViewById(2131231174);
        this.resizeButton = findViewById(2131231176);
        SeekBar seekBar = this.diskSizeSlider;
        if (seekBar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("diskSizeSlider");
            seekBar = null;
        }
        seekBar.setMin(mbToProgress(coerceAtMost));
        SeekBar seekBar2 = this.diskSizeSlider;
        if (seekBar2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("diskSizeSlider");
            seekBar2 = null;
        }
        seekBar2.setMax(mbToProgress(availableSizeMb));
        SeekBar seekBar3 = this.diskSizeSlider;
        if (seekBar3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("diskSizeSlider");
            seekBar3 = null;
        }
        seekBar3.setProgress(mbToProgress(this.diskSizeMb));
        updateSliderText(this.diskSizeMb);
        SeekBar seekBar4 = this.diskSizeSlider;
        if (seekBar4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("diskSizeSlider");
            seekBar4 = null;
        }
        seekBar4.setOnSeekBarChangeListener(new SettingsDiskResizeActivity$onCreate$1(this));
        View view2 = this.cancelButton;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            view2 = null;
        }
        view2.setOnClickListener(new SettingsDiskResizeActivity$onCreate$2(this));
        View view3 = this.resizeButton;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("resizeButton");
        } else {
            view = view3;
        }
        view.setOnClickListener(new SettingsDiskResizeActivity$onCreate$3(this));
    }

    public final void cancel() {
        SeekBar seekBar = this.diskSizeSlider;
        View view = null;
        if (seekBar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("diskSizeSlider");
            seekBar = null;
        }
        seekBar.setProgress(mbToProgress(this.diskSizeMb));
        View view2 = this.buttons;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("buttons");
        } else {
            view = view2;
        }
        view.setVisibility(8);
    }

    public final void showConfirmationDialog() {
        new MaterialAlertDialogBuilder(this).setTitle(2131689689).setMessage(2131689683).setPositiveButton(2131689682, new SettingsDiskResizeActivity$showConfirmationDialog$1(this)).setNegativeButton(2131689681, new SettingsDiskResizeActivity$showConfirmationDialog$2(this)).create().show();
    }

    /* JADX WARNING: type inference failed for: r0v4, types: [android.view.View] */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void resize() {
        /*
            r7 = this;
            android.widget.SeekBar r0 = r7.diskSizeSlider
            java.lang.String r1 = "diskSizeSlider"
            r2 = 0
            if (r0 != 0) goto L_0x000b
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r1)
            r0 = r2
        L_0x000b:
            int r0 = r0.getProgress()
            long r3 = r7.progressToMb(r0)
            long r5 = r7.getAvailableSizeMb()
            int r0 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r0 >= 0) goto L_0x0044
            r0 = 16908290(0x1020002, float:2.3877235E-38)
            android.view.View r0 = r7.findViewById(r0)
            r3 = 2131689680(0x7f0f00d0, float:1.9008382E38)
            r4 = -1
            com.google.android.material.snackbar.Snackbar r0 = com.google.android.material.snackbar.Snackbar.make((android.view.View) r0, (int) r3, (int) r4)
            r0.show()
            android.widget.SeekBar r0 = r7.diskSizeSlider
            if (r0 != 0) goto L_0x0035
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r1)
            goto L_0x0036
        L_0x0035:
            r2 = r0
        L_0x0036:
            int r0 = r7.mbToProgress(r5)
            r2.setMax(r0)
            r7.updateMaxSizeText(r5)
            r7.cancel()
            return
        L_0x0044:
            r7.diskSizeMb = r3
            android.view.View r0 = r7.buttons
            if (r0 != 0) goto L_0x0050
            java.lang.String r0 = "buttons"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
            goto L_0x0051
        L_0x0050:
            r2 = r0
        L_0x0051:
            r0 = 8
            r2.setVisibility(r0)
            com.android.virtualization.terminal.VmLauncherService$Companion r0 = com.android.virtualization.terminal.VmLauncherService.Companion
            com.android.virtualization.terminal.SettingsDiskResizeActivity$resize$intent$1 r1 = new com.android.virtualization.terminal.SettingsDiskResizeActivity$resize$intent$1
            r1.<init>(r7)
            android.content.Intent r0 = r0.getIntentForShutdown(r7, r1)
            r7.startService(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.SettingsDiskResizeActivity.resize():void");
    }

    public final void updateSliderText(long j) {
        TextView textView = this.diskSizeText;
        SeekBar seekBar = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("diskSizeText");
            textView = null;
        }
        String string = getString(2131689684, new Object[]{localizedFileSize(j, true)});
        string.getClass();
        textView.setText(enlargeFontOfNumber(string));
        SeekBar seekBar2 = this.diskSizeSlider;
        if (seekBar2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("diskSizeSlider");
        } else {
            seekBar = seekBar2;
        }
        seekBar.setStateDescription(getString(2131689684, new Object[]{localizedFileSize(j, false)}));
    }

    public final void updateMaxSizeText(long j) {
        TextView textView = this.diskMaxSizeText;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("diskMaxSizeText");
            textView = null;
        }
        textView.setText(getString(2131689685, new Object[]{localizedFileSize(j, true)}));
    }

    public final String localizedFileSize(long j, boolean z) {
        Measure measure = new Measure(Float.valueOf(((float) j) / 1024.0f), MeasureUnit.GIGABYTE);
        Locale locale = getResources().getConfiguration().getLocales().get(0);
        locale.getClass();
        NumberFormat instance = NumberFormat.getInstance(locale);
        instance.getClass();
        instance.setMinimumFractionDigits(1);
        instance.setMaximumFractionDigits(1);
        MeasureFormat instance2 = MeasureFormat.getInstance(locale, z ? MeasureFormat.FormatWidth.SHORT : MeasureFormat.FormatWidth.WIDE, instance);
        instance2.getClass();
        String format = instance2.format(measure);
        format.getClass();
        return format;
    }

    public final CharSequence enlargeFontOfNumber(CharSequence charSequence) {
        charSequence.getClass();
        if (TextUtils.isEmpty(charSequence)) {
            return "";
        }
        Matcher matcher = this.numberPattern.matcher(charSequence);
        if (!matcher.find()) {
            return charSequence;
        }
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new RelativeSizeSpan(2.0f), matcher.start(), matcher.end(), 33);
        return spannableString;
    }
}
