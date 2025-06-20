package com.android.virtualization.terminal;

import android.app.ForegroundServiceStartNotAllowedException;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;
import androidx.lifecycle.ViewModelLazy;
import androidx.viewpager2.widget.ViewPager2;
import com.android.internal.annotations.VisibleForTesting;
import com.android.microdroid.test.common.DeviceProperties;
import com.android.virtualization.terminal.VmLauncherService;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Lazy;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: MainActivity.kt */
public final class MainActivity extends BaseActivity implements VmLauncherService.VmLauncherServiceCallback, AccessibilityManager.AccessibilityStateChangeListener {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private static final int TERMINAL_CONNECTION_TIMEOUT_MS;
    private AccessibilityManager accessibilityManager;
    private final ConditionVariable bootCompleted = new ConditionVariable();
    private Button displayMenu;
    private ExecutorService executorService;
    private InstalledImage image;
    private final ActivityResultLauncher installerLauncher = registerForActivityResult(new ActivityResultContracts$StartActivityForResult(), new MainActivity$installerLauncher$1(this));
    private boolean isVmRunning;
    /* access modifiers changed from: private */
    public ActivityResultLauncher manageExternalStorageActivityResultLauncher;
    public ModifierKeysController modifierKeysController;
    private Button tabAddButton;
    private TabLayout tabLayout;
    private HorizontalScrollView tabScrollView;
    private final CompletableFuture terminalInfo = new CompletableFuture();
    /* access modifiers changed from: private */
    public TerminalTabAdapter terminalTabAdapter;
    private final Lazy terminalViewModel$delegate = new ViewModelLazy(Reflection.getOrCreateKotlinClass(TerminalViewModel.class), new MainActivity$special$$inlined$viewModels$default$2(this), new MainActivity$special$$inlined$viewModels$default$1(this), new MainActivity$special$$inlined$viewModels$default$3((Function0) null, this));
    private ViewPager2 viewPager;

    public final Button getTabAddButton() {
        return this.tabAddButton;
    }

    public final ConditionVariable getBootCompleted() {
        return this.bootCompleted;
    }

    public final ModifierKeysController getModifierKeysController() {
        ModifierKeysController modifierKeysController2 = this.modifierKeysController;
        if (modifierKeysController2 != null) {
            return modifierKeysController2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("modifierKeysController");
        return null;
    }

    public final void setModifierKeysController(ModifierKeysController modifierKeysController2) {
        modifierKeysController2.getClass();
        this.modifierKeysController = modifierKeysController2;
    }

    /* access modifiers changed from: private */
    public final TerminalViewModel getTerminalViewModel() {
        return (TerminalViewModel) this.terminalViewModel$delegate.getValue();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        lockOrientationIfNecessary();
        this.image = InstalledImage.Companion.getDefault(this);
        boolean installIfNecessary = installIfNecessary();
        initializeUi();
        AccessibilityManager accessibilityManager2 = (AccessibilityManager) getSystemService(AccessibilityManager.class);
        this.accessibilityManager = accessibilityManager2;
        ActivityResultLauncher activityResultLauncher = null;
        if (accessibilityManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("accessibilityManager");
            accessibilityManager2 = null;
        }
        accessibilityManager2.addAccessibilityStateChangeListener(this);
        this.manageExternalStorageActivityResultLauncher = registerForActivityResult(new ActivityResultContracts$StartActivityForResult(), new MainActivity$onCreate$1(this));
        Context applicationContext = getApplicationContext();
        applicationContext.getClass();
        this.executorService = Executors.newSingleThreadExecutor(new TerminalThreadFactory(applicationContext));
        if (!installIfNecessary) {
            InstalledImage installedImage = this.image;
            if (installedImage == null) {
                Intrinsics.throwUninitializedPropertyAccessException("image");
                installedImage = null;
            }
            if (installedImage.isOlderThanCurrentVersion()) {
                Intent intent = new Intent(this, UpgradeActivity.class);
                intent.setFlags(268468224);
                startActivity(intent);
                finish();
            } else if (!Environment.isExternalStorageManager()) {
                ActivityResultLauncher activityResultLauncher2 = this.manageExternalStorageActivityResultLauncher;
                if (activityResultLauncher2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("manageExternalStorageActivityResultLauncher");
                } else {
                    activityResultLauncher = activityResultLauncher2;
                }
                requestStoragePermissions(this, activityResultLauncher);
            } else {
                startVm();
            }
        }
    }

    private final void initializeUi() {
        TabLayout tabLayout2;
        ViewPager2 viewPager2;
        setContentView(2131427358);
        this.tabLayout = (TabLayout) findViewById(2131231245);
        this.displayMenu = (Button) findViewById(2131230907);
        this.tabAddButton = (Button) findViewById(2131231243);
        this.tabScrollView = (HorizontalScrollView) findViewById(2131231246);
        View findViewById = findViewById(2131231037);
        findViewById.getClass();
        ViewGroup viewGroup = (ViewGroup) findViewById;
        ((Button) findViewById(2131231173)).setOnClickListener(new MainActivity$initializeUi$1(this));
        Button button = this.displayMenu;
        if (button != null) {
            button.setVisibility(8);
            button.setEnabled(false);
        }
        setModifierKeysController(new ModifierKeysController(this, viewGroup));
        this.terminalTabAdapter = new TerminalTabAdapter(this);
        ViewPager2 viewPager22 = (ViewPager2) findViewById(2131231113);
        this.viewPager = viewPager22;
        TabLayout tabLayout3 = null;
        if (viewPager22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager22 = null;
        }
        TerminalTabAdapter terminalTabAdapter2 = this.terminalTabAdapter;
        if (terminalTabAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalTabAdapter");
            terminalTabAdapter2 = null;
        }
        viewPager22.setAdapter(terminalTabAdapter2);
        ViewPager2 viewPager23 = this.viewPager;
        if (viewPager23 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager23 = null;
        }
        viewPager23.setUserInputEnabled(false);
        ViewPager2 viewPager24 = this.viewPager;
        if (viewPager24 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager24 = null;
        }
        viewPager24.setOffscreenPageLimit(2);
        TabLayout tabLayout4 = this.tabLayout;
        if (tabLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tabLayout");
            tabLayout2 = null;
        } else {
            tabLayout2 = tabLayout4;
        }
        ViewPager2 viewPager25 = this.viewPager;
        if (viewPager25 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager2 = null;
        } else {
            viewPager2 = viewPager25;
        }
        new TabLayoutMediator(tabLayout2, viewPager2, false, false, MainActivity$initializeUi$3.INSTANCE).attach();
        TabLayout tabLayout5 = this.tabLayout;
        if (tabLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tabLayout");
        } else {
            tabLayout3 = tabLayout5;
        }
        tabLayout3.addOnTabSelectedListener((TabLayout.OnTabSelectedListener) new MainActivity$initializeUi$4(this));
        addTerminalTab();
        Button button2 = this.tabAddButton;
        if (button2 != null) {
            button2.setOnClickListener(new MainActivity$initializeUi$5(this));
        }
    }

    /* access modifiers changed from: private */
    public final void addTerminalTab() {
        TabLayout tabLayout2 = this.tabLayout;
        TabLayout tabLayout3 = null;
        if (tabLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tabLayout");
            tabLayout2 = null;
        }
        TabLayout.Tab newTab = tabLayout2.newTab();
        newTab.getClass();
        newTab.setCustomView(2131427453);
        ViewPager2 viewPager2 = this.viewPager;
        if (viewPager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager2 = null;
        }
        viewPager2.setOffscreenPageLimit(viewPager2.getOffscreenPageLimit() + 1);
        TerminalTabAdapter terminalTabAdapter2 = this.terminalTabAdapter;
        if (terminalTabAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalTabAdapter");
            terminalTabAdapter2 = null;
        }
        String addTab = terminalTabAdapter2.addTab();
        getTerminalViewModel().setSelectedTabViewId(addTab);
        getTerminalViewModel().getTerminalTabs().put(addTab, newTab);
        View customView = newTab.getCustomView();
        customView.getClass();
        ((Button) customView.findViewById(2131231244)).setOnClickListener(new MainActivity$addTerminalTab$1(this, newTab));
        TabLayout tabLayout4 = this.tabLayout;
        if (tabLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tabLayout");
        } else {
            tabLayout3 = tabLayout4;
        }
        tabLayout3.addTab(newTab, true);
    }

    public final void closeTab(TabLayout.Tab tab) {
        tab.getClass();
        TerminalTabAdapter terminalTabAdapter2 = this.terminalTabAdapter;
        TabLayout tabLayout2 = null;
        if (terminalTabAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalTabAdapter");
            terminalTabAdapter2 = null;
        }
        if (terminalTabAdapter2.getTabs().size() == 1) {
            finish();
        }
        ViewPager2 viewPager2 = this.viewPager;
        if (viewPager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager2 = null;
        }
        viewPager2.setOffscreenPageLimit(viewPager2.getOffscreenPageLimit() - 1);
        TerminalTabAdapter terminalTabAdapter3 = this.terminalTabAdapter;
        if (terminalTabAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalTabAdapter");
            terminalTabAdapter3 = null;
        }
        terminalTabAdapter3.deleteTab(tab.getPosition());
        TabLayout tabLayout3 = this.tabLayout;
        if (tabLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tabLayout");
        } else {
            tabLayout2 = tabLayout3;
        }
        tabLayout2.removeTab(tab);
    }

    private final void lockOrientationIfNecessary() {
        if (getResources().getConfiguration().keyboard == 2) {
            setRequestedOrientation(-1);
        } else if (getResources().getBoolean(2130968582)) {
            setRequestedOrientation(1);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        configuration.getClass();
        super.onConfigurationChanged(configuration);
        lockOrientationIfNecessary();
        getModifierKeysController().update();
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        keyEvent.getClass();
        if (!Build.isDebuggable() || keyEvent.getKeyCode() != 0) {
            return super.dispatchKeyEvent(keyEvent);
        }
        if (keyEvent.getAction() == 1) {
            ErrorActivity.Companion.start(this, new Exception("Debug: KeyEvent.KEYCODE_UNKNOWN"));
        }
        return true;
    }

    /* access modifiers changed from: private */
    public final void requestStoragePermissions(Context context, ActivityResultLauncher activityResultLauncher) {
        Intent intent = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
        intent.setData(Uri.fromParts("package", context.getPackageName(), (String) null));
        activityResultLauncher.launch(intent);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        int userId = getUserId();
        MediaScannerConnection.scanFile(this, new String[]{"/storage/emulated/" + userId + "/Download"}, (String[]) null, (MediaScannerConnection.OnScanCompletedListener) null);
    }

    private final URL getTerminalServiceUrl(String str, int i) {
        Configuration configuration = getResources().getConfiguration();
        int i2 = (int) (configuration.fontScale * ((float) 13));
        int i3 = configuration.fontWeightAdjustment;
        int i4 = i3 + 400;
        int i5 = i3 + 700;
        AccessibilityManager accessibilityManager2 = this.accessibilityManager;
        if (accessibilityManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("accessibilityManager");
            accessibilityManager2 = null;
        }
        try {
            return new URL("https", str, i, "/" + ("?fontSize=" + i2 + "&fontWeight=" + i4 + "&fontWeightBold=" + i5 + "&screenReaderMode=" + accessibilityManager2.isEnabled()));
        } catch (MalformedURLException unused) {
            return null;
        }
    }

    public final void connectToTerminalService(TerminalView terminalView) {
        terminalView.getClass();
        CompletableFuture completableFuture = this.terminalInfo;
        MainActivity$sam$java_util_function_Consumer$0 mainActivity$sam$java_util_function_Consumer$0 = new MainActivity$sam$java_util_function_Consumer$0(new MainActivity$$ExternalSyntheticLambda0(this, terminalView));
        ExecutorService executorService2 = this.executorService;
        if (executorService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("executorService");
            executorService2 = null;
        }
        completableFuture.thenAcceptAsync(mainActivity$sam$java_util_function_Consumer$0, executorService2);
    }

    /* access modifiers changed from: private */
    public static final Unit connectToTerminalService$lambda$1(MainActivity mainActivity, TerminalView terminalView, TerminalInfo terminalInfo2) {
        mainActivity.runOnUiThread(new MainActivity$connectToTerminalService$1$1(terminalView, mainActivity.getTerminalServiceUrl(terminalInfo2.getIpAddress(), terminalInfo2.getPort())));
        return Unit.INSTANCE;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        ExecutorService executorService2 = this.executorService;
        if (executorService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("executorService");
            executorService2 = null;
        }
        executorService2.shutdown();
        ((AccessibilityManager) getSystemService(AccessibilityManager.class)).removeAccessibilityStateChangeListener(this);
        if (this.isVmRunning) {
            startService(VmLauncherService.Companion.getIntentForShutdown(this, this));
        }
        super.onDestroy();
    }

    public void onVmStart() {
        Log.i("VmTerminalApp", "onVmStart()");
        this.isVmRunning = true;
    }

    public void onTerminalAvailable(TerminalInfo terminalInfo2) {
        terminalInfo2.getClass();
        this.terminalInfo.complete(terminalInfo2);
    }

    public void onVmStop() {
        Log.i("VmTerminalApp", "onVmStop()");
        this.isVmRunning = false;
        finish();
    }

    public void onVmError() {
        Log.i("VmTerminalApp", "onVmError()");
        this.isVmRunning = false;
        ErrorActivity.Companion.start(this, new Exception("onVmError"));
    }

    public void onAccessibilityStateChanged(boolean z) {
        for (TerminalView connectToTerminalService : getTerminalViewModel().getTerminalViews()) {
            connectToTerminalService(connectToTerminalService);
        }
    }

    private final boolean installIfNecessary() {
        InstalledImage installedImage = this.image;
        if (installedImage == null) {
            Intrinsics.throwUninitializedPropertyAccessException("image");
            installedImage = null;
        }
        if (installedImage.isInstalled()) {
            return false;
        }
        this.installerLauncher.launch(new Intent(this, InstallerActivity.class));
        return true;
    }

    /* access modifiers changed from: private */
    public final void startVm() {
        InstalledImage installedImage = InstalledImage.Companion.getDefault(this);
        if (installedImage.isInstalled()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(603979776);
            PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 67108864);
            Intent intent2 = new Intent(this, SettingsActivity.class);
            intent2.setFlags(603979776);
            PendingIntent activity2 = PendingIntent.getActivity(this, 0, intent2, 67108864);
            VmLauncherService.Companion companion = VmLauncherService.Companion;
            PendingIntent service = PendingIntent.getService(this, 0, companion.getIntentForShutdown(this, this), 201326592);
            Icon createWithResource = Icon.createWithResource(getResources(), 2131165338);
            createWithResource.getClass();
            Notification build = new Notification.Builder(this, "long_running").setSilent(true).setSmallIcon(2131165347).setContentTitle(getResources().getString(2131689679)).setContentText(getResources().getString(2131689675)).setContentIntent(activity).setOngoing(true).addAction(new Notification.Action.Builder(createWithResource, getResources().getString(2131689678), activity2).build()).addAction(new Notification.Action.Builder(createWithResource, getResources().getString(2131689677), service).build()).build();
            build.getClass();
            try {
                startForegroundService(companion.getIntentForStart(this, this, build, getDisplayInfo(), Long.valueOf(getIntent().getLongExtra("com.android.virtualization.terminal.EXTRA_DISK_SIZE", installedImage.getApparentSize()))));
            } catch (ForegroundServiceStartNotAllowedException e) {
                Log.e("VmTerminalApp", "Failed to start VM", e);
                finish();
            }
        }
    }

    @VisibleForTesting
    public final boolean waitForBootCompleted(long j) {
        return this.bootCompleted.block(j);
    }

    /* compiled from: MainActivity.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        DeviceProperties create = DeviceProperties.create(MainActivity$Companion$prop$1.INSTANCE);
        TERMINAL_CONNECTION_TIMEOUT_MS = (create.isCuttlefish() || create.isGoldfish()) ? 180000 : 20000;
    }

    public final DisplayInfo getDisplayInfo() {
        WindowMetrics currentWindowMetrics = ((WindowManager) getSystemService(WindowManager.class)).getCurrentWindowMetrics();
        currentWindowMetrics.getClass();
        Rect bounds = currentWindowMetrics.getBounds();
        bounds.getClass();
        return new DisplayInfo(Math.max(bounds.right, bounds.bottom), Math.min(bounds.right, bounds.bottom), (int) (((float) 160) * currentWindowMetrics.getDensity()), (int) getDisplay().getRefreshRate());
    }
}
