package com.android.virtualization.terminal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Environment;
import android.system.virtualmachine.VirtualMachineConfig;
import android.system.virtualmachine.VirtualMachineCustomImageConfig;
import android.view.WindowManager;
import android.view.WindowMetrics;
import com.google.gson.annotations.SerializedName;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: ConfigJson.kt */
public final class ConfigJson {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private static final boolean DEBUG = true;
    private final AudioJson audio;
    private final boolean auto_memory_balloon;
    private final String bootloader;
    private final boolean connect_console;
    private final String console_input_device;
    private final boolean console_out;
    private final String cpu_topology;
    private final boolean debuggable;
    private final DiskJson[] disks;
    private final DisplayJson display;
    private final GpuJson gpu;
    private final String initrd;
    private final InputJson input;
    @SerializedName("protected")
    private final boolean isProtected;
    private final String kernel;
    private final int memory_mib;
    private final String name;
    private final boolean network;
    private final String params;
    private final String platform_version;
    private final SharedPathJson[] sharedPath;

    public boolean equals(Object obj) {
        if (this == obj) {
            return DEBUG;
        }
        if (!(obj instanceof ConfigJson)) {
            return false;
        }
        ConfigJson configJson = (ConfigJson) obj;
        if (this.isProtected == configJson.isProtected && Intrinsics.areEqual(this.name, configJson.name) && Intrinsics.areEqual(this.cpu_topology, configJson.cpu_topology) && Intrinsics.areEqual(this.platform_version, configJson.platform_version) && this.memory_mib == configJson.memory_mib && Intrinsics.areEqual(this.console_input_device, configJson.console_input_device) && Intrinsics.areEqual(this.bootloader, configJson.bootloader) && Intrinsics.areEqual(this.kernel, configJson.kernel) && Intrinsics.areEqual(this.initrd, configJson.initrd) && Intrinsics.areEqual(this.params, configJson.params) && this.debuggable == configJson.debuggable && this.console_out == configJson.console_out && this.connect_console == configJson.connect_console && this.network == configJson.network && Intrinsics.areEqual(this.input, configJson.input) && Intrinsics.areEqual(this.audio, configJson.audio) && Intrinsics.areEqual(this.disks, configJson.disks) && Intrinsics.areEqual(this.sharedPath, configJson.sharedPath) && Intrinsics.areEqual(this.display, configJson.display) && Intrinsics.areEqual(this.gpu, configJson.gpu) && this.auto_memory_balloon == configJson.auto_memory_balloon) {
            return DEBUG;
        }
        return false;
    }

    public int hashCode() {
        int hashCode = Boolean.hashCode(this.isProtected) * 31;
        String str = this.name;
        int i = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.cpu_topology;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.platform_version;
        int hashCode4 = (((hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31) + Integer.hashCode(this.memory_mib)) * 31;
        String str4 = this.console_input_device;
        int hashCode5 = (hashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.bootloader;
        int hashCode6 = (hashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.kernel;
        int hashCode7 = (hashCode6 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.initrd;
        int hashCode8 = (hashCode7 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.params;
        int hashCode9 = (((((((((hashCode8 + (str8 == null ? 0 : str8.hashCode())) * 31) + Boolean.hashCode(this.debuggable)) * 31) + Boolean.hashCode(this.console_out)) * 31) + Boolean.hashCode(this.connect_console)) * 31) + Boolean.hashCode(this.network)) * 31;
        InputJson inputJson = this.input;
        int hashCode10 = (hashCode9 + (inputJson == null ? 0 : inputJson.hashCode())) * 31;
        AudioJson audioJson = this.audio;
        int hashCode11 = (hashCode10 + (audioJson == null ? 0 : audioJson.hashCode())) * 31;
        DiskJson[] diskJsonArr = this.disks;
        int hashCode12 = (hashCode11 + (diskJsonArr == null ? 0 : Arrays.hashCode(diskJsonArr))) * 31;
        SharedPathJson[] sharedPathJsonArr = this.sharedPath;
        int hashCode13 = (hashCode12 + (sharedPathJsonArr == null ? 0 : Arrays.hashCode(sharedPathJsonArr))) * 31;
        DisplayJson displayJson = this.display;
        int hashCode14 = (hashCode13 + (displayJson == null ? 0 : displayJson.hashCode())) * 31;
        GpuJson gpuJson = this.gpu;
        if (gpuJson != null) {
            i = gpuJson.hashCode();
        }
        return ((hashCode14 + i) * 31) + Boolean.hashCode(this.auto_memory_balloon);
    }

    public String toString() {
        boolean z = this.isProtected;
        String str = this.name;
        String str2 = this.cpu_topology;
        String str3 = this.platform_version;
        int i = this.memory_mib;
        String str4 = this.console_input_device;
        String str5 = this.bootloader;
        String str6 = this.kernel;
        String str7 = this.initrd;
        String str8 = this.params;
        boolean z2 = this.debuggable;
        boolean z3 = this.console_out;
        boolean z4 = this.connect_console;
        boolean z5 = this.network;
        InputJson inputJson = this.input;
        AudioJson audioJson = this.audio;
        String arrays = Arrays.toString(this.disks);
        String arrays2 = Arrays.toString(this.sharedPath);
        DisplayJson displayJson = this.display;
        GpuJson gpuJson = this.gpu;
        boolean z6 = this.auto_memory_balloon;
        return "ConfigJson(isProtected=" + z + ", name=" + str + ", cpu_topology=" + str2 + ", platform_version=" + str3 + ", memory_mib=" + i + ", console_input_device=" + str4 + ", bootloader=" + str5 + ", kernel=" + str6 + ", initrd=" + str7 + ", params=" + str8 + ", debuggable=" + z2 + ", console_out=" + z3 + ", connect_console=" + z4 + ", network=" + z5 + ", input=" + inputJson + ", audio=" + audioJson + ", disks=" + arrays + ", sharedPath=" + arrays2 + ", display=" + displayJson + ", gpu=" + gpuJson + ", auto_memory_balloon=" + z6 + ")";
    }

    private final int getCpuTopology() {
        String str = this.cpu_topology;
        if (Intrinsics.areEqual(str, "one_cpu")) {
            return 0;
        }
        if (Intrinsics.areEqual(str, "match_host")) {
            return 1;
        }
        String str2 = this.cpu_topology;
        throw new RuntimeException("invalid cpu topology: " + str2);
    }

    private final int getDebugLevel() {
        return this.debuggable ? 1 : 0;
    }

    public final VirtualMachineConfig.Builder toConfigBuilder(Context context) {
        context.getClass();
        long j = (long) 1024;
        VirtualMachineConfig.Builder connectVmConsole = new VirtualMachineConfig.Builder(context).setProtectedVm(this.isProtected).setMemoryBytes(((long) this.memory_mib) * j * j).setConsoleInputDevice(this.console_input_device).setCpuTopology(getCpuTopology()).setCustomImageConfig(toCustomImageConfigBuilder(context).build()).setDebugLevel(getDebugLevel()).setVmOutputCaptured(this.console_out).setConnectVmConsole(this.connect_console);
        connectVmConsole.getClass();
        return connectVmConsole;
    }

    public final VirtualMachineCustomImageConfig.Builder toCustomImageConfigBuilder(Context context) {
        List split;
        context.getClass();
        VirtualMachineCustomImageConfig.Builder builder = new VirtualMachineCustomImageConfig.Builder();
        builder.setName(this.name).setBootloaderPath(this.bootloader).setKernelPath(this.kernel).setInitrdPath(this.initrd).useNetwork(this.network).useAutoMemoryBalloon(this.auto_memory_balloon);
        InputJson inputJson = this.input;
        if (inputJson != null) {
            builder.useTouch(inputJson.getTouchscreen()).useKeyboard(this.input.getKeyboard()).useMouse(this.input.getMouse()).useTrackpad(this.input.getTrackpad()).useSwitches(this.input.getSwitches());
        }
        AudioJson audioJson = this.audio;
        if (audioJson != null) {
            builder.setAudioConfig(audioJson.toConfig());
        }
        DisplayJson displayJson = this.display;
        if (displayJson != null) {
            builder.setDisplayConfig(displayJson.toConfig(context));
        }
        GpuJson gpuJson = this.gpu;
        if (gpuJson != null) {
            builder.setGpuConfig(gpuJson.toConfig());
        }
        String str = this.params;
        int i = 0;
        if (!(str == null || (split = new Regex(" ").split(str, 0)) == null)) {
            ArrayList arrayList = new ArrayList();
            for (Object next : split) {
                if (((String) next).length() > 0) {
                    arrayList.add(next);
                }
            }
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                builder.addParam((String) obj);
            }
        }
        DiskJson[] diskJsonArr = this.disks;
        if (diskJsonArr != null) {
            for (DiskJson config : diskJsonArr) {
                builder.addDisk(config.toConfig());
            }
        }
        SharedPathJson[] sharedPathJsonArr = this.sharedPath;
        if (sharedPathJsonArr != null) {
            ArrayList arrayList2 = new ArrayList();
            for (SharedPathJson config2 : sharedPathJsonArr) {
                VirtualMachineCustomImageConfig.SharedPath config3 = config2.toConfig(context);
                if (config3 != null) {
                    arrayList2.add(config3);
                }
            }
            int size2 = arrayList2.size();
            while (i < size2) {
                Object obj2 = arrayList2.get(i);
                i++;
                builder.addSharedPath((VirtualMachineCustomImageConfig.SharedPath) obj2);
            }
        }
        return builder;
    }

    /* compiled from: ConfigJson.kt */
    public final class SharedPathJson {
        public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
        private static final int GUEST_GID = 100;
        private static final int GUEST_UID = 1000;
        private final String sharedPath;

        public boolean equals(Object obj) {
            if (this == obj) {
                return ConfigJson.DEBUG;
            }
            if ((obj instanceof SharedPathJson) && Intrinsics.areEqual(this.sharedPath, ((SharedPathJson) obj).sharedPath)) {
                return ConfigJson.DEBUG;
            }
            return false;
        }

        public int hashCode() {
            String str = this.sharedPath;
            if (str == null) {
                return 0;
            }
            return str.hashCode();
        }

        public String toString() {
            String str = this.sharedPath;
            return "SharedPathJson(sharedPath=" + str + ")";
        }

        public final VirtualMachineCustomImageConfig.SharedPath toConfig(Context context) {
            context.getClass();
            try {
                int terminalUid = getTerminalUid(context);
                String str = this.sharedPath;
                if (str == null || !StringsKt.contains$default(str, "emulated", false, 2, (Object) null)) {
                    Path resolve = context.getFilesDir().toPath().resolve("internal.virtiofs");
                    Files.deleteIfExists(resolve);
                    return new VirtualMachineCustomImageConfig.SharedPath(this.sharedPath, terminalUid, terminalUid, 0, 0, 7, "internal", "internal", ConfigJson.DEBUG, resolve.toString());
                } else if (!Environment.isExternalStorageManager()) {
                    return null;
                } else {
                    int userId = context.getUserId();
                    String str2 = this.sharedPath;
                    return new VirtualMachineCustomImageConfig.SharedPath(str2 + "/" + userId + "/Download", terminalUid, terminalUid, GUEST_UID, 100, 7, "android", "android", false, "");
                }
            } catch (PackageManager.NameNotFoundException | IOException unused) {
                return null;
            }
        }

        public final int getTerminalUid(Context context) {
            context.getClass();
            return context.getPackageManager().getPackageUidAsUser(context.getPackageName(), context.getUserId());
        }

        /* compiled from: ConfigJson.kt */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }
    }

    /* compiled from: ConfigJson.kt */
    public final class InputJson {
        private final boolean keyboard;
        private final boolean mouse;
        private final boolean switches;
        private final boolean touchscreen;
        private final boolean trackpad;

        public boolean equals(Object obj) {
            if (this == obj) {
                return ConfigJson.DEBUG;
            }
            if (!(obj instanceof InputJson)) {
                return false;
            }
            InputJson inputJson = (InputJson) obj;
            if (this.touchscreen == inputJson.touchscreen && this.keyboard == inputJson.keyboard && this.mouse == inputJson.mouse && this.switches == inputJson.switches && this.trackpad == inputJson.trackpad) {
                return ConfigJson.DEBUG;
            }
            return false;
        }

        public int hashCode() {
            return (((((((Boolean.hashCode(this.touchscreen) * 31) + Boolean.hashCode(this.keyboard)) * 31) + Boolean.hashCode(this.mouse)) * 31) + Boolean.hashCode(this.switches)) * 31) + Boolean.hashCode(this.trackpad);
        }

        public String toString() {
            boolean z = this.touchscreen;
            boolean z2 = this.keyboard;
            boolean z3 = this.mouse;
            boolean z4 = this.switches;
            boolean z5 = this.trackpad;
            return "InputJson(touchscreen=" + z + ", keyboard=" + z2 + ", mouse=" + z3 + ", switches=" + z4 + ", trackpad=" + z5 + ")";
        }

        public final boolean getTouchscreen() {
            return this.touchscreen;
        }

        public final boolean getKeyboard() {
            return this.keyboard;
        }

        public final boolean getMouse() {
            return this.mouse;
        }

        public final boolean getSwitches() {
            return this.switches;
        }

        public final boolean getTrackpad() {
            return this.trackpad;
        }
    }

    /* compiled from: ConfigJson.kt */
    public final class AudioJson {
        private final boolean microphone;
        private final boolean speaker;

        public boolean equals(Object obj) {
            if (this == obj) {
                return ConfigJson.DEBUG;
            }
            if (!(obj instanceof AudioJson)) {
                return false;
            }
            AudioJson audioJson = (AudioJson) obj;
            if (this.microphone == audioJson.microphone && this.speaker == audioJson.speaker) {
                return ConfigJson.DEBUG;
            }
            return false;
        }

        public int hashCode() {
            return (Boolean.hashCode(this.microphone) * 31) + Boolean.hashCode(this.speaker);
        }

        public String toString() {
            boolean z = this.microphone;
            boolean z2 = this.speaker;
            return "AudioJson(microphone=" + z + ", speaker=" + z2 + ")";
        }

        public final VirtualMachineCustomImageConfig.AudioConfig toConfig() {
            VirtualMachineCustomImageConfig.AudioConfig build = new VirtualMachineCustomImageConfig.AudioConfig.Builder().setUseMicrophone(this.microphone).setUseSpeaker(this.speaker).build();
            build.getClass();
            return build;
        }
    }

    /* compiled from: ConfigJson.kt */
    public final class DiskJson {
        private final String image;
        private final PartitionJson[] partitions;
        private final boolean writable;

        public boolean equals(Object obj) {
            if (this == obj) {
                return ConfigJson.DEBUG;
            }
            if (!(obj instanceof DiskJson)) {
                return false;
            }
            DiskJson diskJson = (DiskJson) obj;
            if (this.writable == diskJson.writable && Intrinsics.areEqual(this.image, diskJson.image) && Intrinsics.areEqual(this.partitions, diskJson.partitions)) {
                return ConfigJson.DEBUG;
            }
            return false;
        }

        public int hashCode() {
            int hashCode = Boolean.hashCode(this.writable) * 31;
            String str = this.image;
            int i = 0;
            int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
            PartitionJson[] partitionJsonArr = this.partitions;
            if (partitionJsonArr != null) {
                i = Arrays.hashCode(partitionJsonArr);
            }
            return hashCode2 + i;
        }

        public String toString() {
            boolean z = this.writable;
            String str = this.image;
            String arrays = Arrays.toString(this.partitions);
            return "DiskJson(writable=" + z + ", image=" + str + ", partitions=" + arrays + ")";
        }

        public final VirtualMachineCustomImageConfig.Disk toConfig() {
            VirtualMachineCustomImageConfig.Disk disk;
            if (this.writable) {
                disk = VirtualMachineCustomImageConfig.Disk.RWDisk(this.image);
            } else {
                disk = VirtualMachineCustomImageConfig.Disk.RODisk(this.image);
            }
            PartitionJson[] partitionJsonArr = this.partitions;
            if (partitionJsonArr != null) {
                for (PartitionJson partitionJson : partitionJsonArr) {
                    disk.addPartition(new VirtualMachineCustomImageConfig.Partition(partitionJson.getLabel(), partitionJson.getPath(), (!this.writable || !partitionJson.getWritable()) ? false : ConfigJson.DEBUG, partitionJson.getGuid()));
                }
            }
            disk.getClass();
            return disk;
        }
    }

    /* compiled from: ConfigJson.kt */
    public final class PartitionJson {
        private final String guid;
        private final String label;
        private final String path;
        private final boolean writable;

        public boolean equals(Object obj) {
            if (this == obj) {
                return ConfigJson.DEBUG;
            }
            if (!(obj instanceof PartitionJson)) {
                return false;
            }
            PartitionJson partitionJson = (PartitionJson) obj;
            if (this.writable == partitionJson.writable && Intrinsics.areEqual(this.label, partitionJson.label) && Intrinsics.areEqual(this.path, partitionJson.path) && Intrinsics.areEqual(this.guid, partitionJson.guid)) {
                return ConfigJson.DEBUG;
            }
            return false;
        }

        public int hashCode() {
            int hashCode = Boolean.hashCode(this.writable) * 31;
            String str = this.label;
            int i = 0;
            int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
            String str2 = this.path;
            int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
            String str3 = this.guid;
            if (str3 != null) {
                i = str3.hashCode();
            }
            return hashCode3 + i;
        }

        public String toString() {
            boolean z = this.writable;
            String str = this.label;
            String str2 = this.path;
            String str3 = this.guid;
            return "PartitionJson(writable=" + z + ", label=" + str + ", path=" + str2 + ", guid=" + str3 + ")";
        }

        public final boolean getWritable() {
            return this.writable;
        }

        public final String getLabel() {
            return this.label;
        }

        public final String getPath() {
            return this.path;
        }

        public final String getGuid() {
            return this.guid;
        }
    }

    /* compiled from: ConfigJson.kt */
    public final class DisplayJson {
        private final int height_pixels;
        private final int refresh_rate;
        private final float scale;
        private final int width_pixels;

        public boolean equals(Object obj) {
            if (this == obj) {
                return ConfigJson.DEBUG;
            }
            if (!(obj instanceof DisplayJson)) {
                return false;
            }
            DisplayJson displayJson = (DisplayJson) obj;
            if (Float.compare(this.scale, displayJson.scale) == 0 && this.refresh_rate == displayJson.refresh_rate && this.width_pixels == displayJson.width_pixels && this.height_pixels == displayJson.height_pixels) {
                return ConfigJson.DEBUG;
            }
            return false;
        }

        public int hashCode() {
            return (((((Float.hashCode(this.scale) * 31) + Integer.hashCode(this.refresh_rate)) * 31) + Integer.hashCode(this.width_pixels)) * 31) + Integer.hashCode(this.height_pixels);
        }

        public String toString() {
            float f = this.scale;
            int i = this.refresh_rate;
            int i2 = this.width_pixels;
            int i3 = this.height_pixels;
            return "DisplayJson(scale=" + f + ", refresh_rate=" + i + ", width_pixels=" + i2 + ", height_pixels=" + i3 + ")";
        }

        public final VirtualMachineCustomImageConfig.DisplayConfig toConfig(Context context) {
            context.getClass();
            WindowMetrics currentWindowMetrics = ((WindowManager) context.getSystemService(WindowManager.class)).getCurrentWindowMetrics();
            currentWindowMetrics.getClass();
            Rect bounds = currentWindowMetrics.getBounds();
            bounds.getClass();
            int i = this.width_pixels;
            if (i <= 0) {
                i = bounds.right;
            }
            int i2 = this.height_pixels;
            if (i2 <= 0) {
                i2 = bounds.bottom;
            }
            int density = (int) (((float) 160) * currentWindowMetrics.getDensity());
            float f = this.scale;
            if (f > 0.0f) {
                density = (int) (((float) density) * f);
            }
            int refreshRate = (int) context.getDisplay().getRefreshRate();
            int i3 = this.refresh_rate;
            if (i3 != 0) {
                refreshRate = i3;
            }
            VirtualMachineCustomImageConfig.DisplayConfig build = new VirtualMachineCustomImageConfig.DisplayConfig.Builder().setWidth(i).setHeight(i2).setHorizontalDpi(density).setVerticalDpi(density).setRefreshRate(refreshRate).build();
            build.getClass();
            return build;
        }
    }

    /* compiled from: ConfigJson.kt */
    public final class GpuJson {
        private final String backend;
        private final String[] context_types;
        private final String pci_address;
        private final String renderer_features;
        private final boolean renderer_use_egl;
        private final boolean renderer_use_gles;
        private final boolean renderer_use_glx;
        private final boolean renderer_use_surfaceless;
        private final boolean renderer_use_vulkan;

        public boolean equals(Object obj) {
            if (this == obj) {
                return ConfigJson.DEBUG;
            }
            if (!(obj instanceof GpuJson)) {
                return false;
            }
            GpuJson gpuJson = (GpuJson) obj;
            if (Intrinsics.areEqual(this.backend, gpuJson.backend) && Intrinsics.areEqual(this.pci_address, gpuJson.pci_address) && Intrinsics.areEqual(this.renderer_features, gpuJson.renderer_features) && this.renderer_use_egl == gpuJson.renderer_use_egl && this.renderer_use_gles == gpuJson.renderer_use_gles && this.renderer_use_glx == gpuJson.renderer_use_glx && this.renderer_use_surfaceless == gpuJson.renderer_use_surfaceless && this.renderer_use_vulkan == gpuJson.renderer_use_vulkan && Intrinsics.areEqual(this.context_types, gpuJson.context_types)) {
                return ConfigJson.DEBUG;
            }
            return false;
        }

        public int hashCode() {
            String str = this.backend;
            int i = 0;
            int hashCode = (str == null ? 0 : str.hashCode()) * 31;
            String str2 = this.pci_address;
            int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
            String str3 = this.renderer_features;
            int hashCode3 = (((((((((((hashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31) + Boolean.hashCode(this.renderer_use_egl)) * 31) + Boolean.hashCode(this.renderer_use_gles)) * 31) + Boolean.hashCode(this.renderer_use_glx)) * 31) + Boolean.hashCode(this.renderer_use_surfaceless)) * 31) + Boolean.hashCode(this.renderer_use_vulkan)) * 31;
            String[] strArr = this.context_types;
            if (strArr != null) {
                i = Arrays.hashCode(strArr);
            }
            return hashCode3 + i;
        }

        public String toString() {
            String str = this.backend;
            String str2 = this.pci_address;
            String str3 = this.renderer_features;
            boolean z = this.renderer_use_egl;
            boolean z2 = this.renderer_use_gles;
            boolean z3 = this.renderer_use_glx;
            boolean z4 = this.renderer_use_surfaceless;
            boolean z5 = this.renderer_use_vulkan;
            String arrays = Arrays.toString(this.context_types);
            return "GpuJson(backend=" + str + ", pci_address=" + str2 + ", renderer_features=" + str3 + ", renderer_use_egl=" + z + ", renderer_use_gles=" + z2 + ", renderer_use_glx=" + z3 + ", renderer_use_surfaceless=" + z4 + ", renderer_use_vulkan=" + z5 + ", context_types=" + arrays + ")";
        }

        public final VirtualMachineCustomImageConfig.GpuConfig toConfig() {
            VirtualMachineCustomImageConfig.GpuConfig build = new VirtualMachineCustomImageConfig.GpuConfig.Builder().setBackend(this.backend).setPciAddress(this.pci_address).setRendererFeatures(this.renderer_features).setRendererUseEgl(Boolean.valueOf(this.renderer_use_egl)).setRendererUseGles(Boolean.valueOf(this.renderer_use_gles)).setRendererUseGlx(Boolean.valueOf(this.renderer_use_glx)).setRendererUseSurfaceless(Boolean.valueOf(this.renderer_use_surfaceless)).setRendererUseVulkan(Boolean.valueOf(this.renderer_use_vulkan)).setContextTypes(this.context_types).build();
            build.getClass();
            return build;
        }
    }

    /* compiled from: ConfigJson.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:13:0x002e, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
            kotlin.io.CloseableKt.closeFinally(r2, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0032, code lost:
            throw r0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final com.android.virtualization.terminal.ConfigJson from(android.content.Context r3, java.nio.file.Path r4) {
            /*
                r2 = this;
                r3.getClass()
                r4.getClass()
                java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Exception -> 0x002a }
                java.io.File r0 = r4.toFile()     // Catch:{ Exception -> 0x002a }
                r2.<init>(r0)     // Catch:{ Exception -> 0x002a }
                com.android.virtualization.terminal.ConfigJson$Companion r0 = com.android.virtualization.terminal.ConfigJson.Companion     // Catch:{ all -> 0x002c }
                java.lang.String r3 = r0.replaceKeywords(r2, r3)     // Catch:{ all -> 0x002c }
                com.google.gson.Gson r0 = new com.google.gson.Gson     // Catch:{ all -> 0x002c }
                r0.<init>()     // Catch:{ all -> 0x002c }
                java.lang.Class<com.android.virtualization.terminal.ConfigJson> r1 = com.android.virtualization.terminal.ConfigJson.class
                java.lang.Object r3 = r0.fromJson((java.lang.String) r3, (java.lang.Class) r1)     // Catch:{ all -> 0x002c }
                r3.getClass()     // Catch:{ all -> 0x002c }
                com.android.virtualization.terminal.ConfigJson r3 = (com.android.virtualization.terminal.ConfigJson) r3     // Catch:{ all -> 0x002c }
                r0 = 0
                kotlin.io.CloseableKt.closeFinally(r2, r0)     // Catch:{ Exception -> 0x002a }
                return r3
            L_0x002a:
                r2 = move-exception
                goto L_0x0033
            L_0x002c:
                r3 = move-exception
                throw r3     // Catch:{ all -> 0x002e }
            L_0x002e:
                r0 = move-exception
                kotlin.io.CloseableKt.closeFinally(r2, r3)     // Catch:{ Exception -> 0x002a }
                throw r0     // Catch:{ Exception -> 0x002a }
            L_0x0033:
                java.lang.RuntimeException r3 = new java.lang.RuntimeException
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "Failed to parse "
                r0.append(r1)
                r0.append(r4)
                java.lang.String r4 = r0.toString()
                r3.<init>(r4, r2)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.ConfigJson.Companion.from(android.content.Context, java.nio.file.Path):com.android.virtualization.terminal.ConfigJson");
        }

        private final String replaceKeywords(Reader reader, Context context) {
            Throwable th;
            Map mapOf = MapsKt.mapOf(TuplesKt.to("\\$PAYLOAD_DIR", InstalledImage.Companion.getDefault(context).getInstallDir().toString()), TuplesKt.to("\\$USER_ID", String.valueOf(context.getUserId())), TuplesKt.to("\\$PACKAGE_NAME", context.getPackageName()), TuplesKt.to("\\$APP_DATA_DIR", context.getDataDir().toString()));
            BufferedReader bufferedReader = new BufferedReader(reader);
            try {
                String joinToString$default = SequencesKt.joinToString$default(SequencesKt.map(TextStreamsKt.lineSequence(bufferedReader), new ConfigJson$Companion$$ExternalSyntheticLambda0(mapOf)), "\n", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
                CloseableKt.closeFinally(bufferedReader, (Throwable) null);
                return joinToString$default;
            } catch (Throwable th2) {
                Throwable th3 = th2;
                CloseableKt.closeFinally(bufferedReader, th);
                throw th3;
            }
        }

        /* access modifiers changed from: private */
        public static final String replaceKeywords$lambda$3$lambda$2(Map map, String str) {
            str.getClass();
            for (Map.Entry entry : map.entrySet()) {
                str = new Regex((String) entry.getKey()).replace(str, (String) entry.getValue());
            }
            return str;
        }
    }
}
