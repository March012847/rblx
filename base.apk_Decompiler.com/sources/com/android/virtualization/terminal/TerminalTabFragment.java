package com.android.virtualization.terminal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ClientCertRequest;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentViewModelLazyKt;
import com.google.android.material.tabs.TabLayout;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt;

/* compiled from: TerminalTabFragment.kt */
public final class TerminalTabFragment extends Fragment {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public View bootProgressView;
    /* access modifiers changed from: private */
    public X509Certificate[] certificates;
    /* access modifiers changed from: private */
    public String id;
    /* access modifiers changed from: private */
    public PrivateKey privateKey;
    /* access modifiers changed from: private */
    public TerminalView terminalView;
    private final Lazy terminalViewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(TerminalViewModel.class), new TerminalTabFragment$special$$inlined$activityViewModels$default$1(this), new TerminalTabFragment$special$$inlined$activityViewModels$default$2((Function0) null, this), new TerminalTabFragment$special$$inlined$activityViewModels$default$3(this));

    /* access modifiers changed from: private */
    public final TerminalViewModel getTerminalViewModel() {
        return (TerminalViewModel) this.terminalViewModel$delegate.getValue();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater.getClass();
        View inflate = layoutInflater.inflate(2131427377, viewGroup, false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String string = arguments.getString("id");
            string.getClass();
            this.id = string;
        }
        inflate.getClass();
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        view.getClass();
        super.onViewCreated(view, bundle);
        this.terminalView = (TerminalView) view.findViewById(2131231319);
        this.bootProgressView = view.findViewById(2131230825);
        initializeWebView();
        readClientCertificate();
        TerminalView terminalView2 = this.terminalView;
        TerminalView terminalView3 = null;
        if (terminalView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView2 = null;
        }
        terminalView2.setWebViewClient(new TerminalWebViewClient());
        if (bundle != null) {
            TerminalView terminalView4 = this.terminalView;
            if (terminalView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            } else {
                terminalView3 = terminalView4;
            }
            terminalView3.restoreState(bundle);
            return;
        }
        FragmentActivity activity = getActivity();
        activity.getClass();
        MainActivity mainActivity = (MainActivity) activity;
        TerminalView terminalView5 = this.terminalView;
        if (terminalView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
        } else {
            terminalView3 = terminalView5;
        }
        mainActivity.connectToTerminalService(terminalView3);
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.getClass();
        super.onSaveInstanceState(bundle);
        TerminalView terminalView2 = this.terminalView;
        if (terminalView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView2 = null;
        }
        terminalView2.saveState(bundle);
    }

    public void onResume() {
        super.onResume();
        updateFocus();
    }

    private final void initializeWebView() {
        TerminalView terminalView2 = this.terminalView;
        TerminalView terminalView3 = null;
        if (terminalView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView2 = null;
        }
        terminalView2.getSettings().setDatabaseEnabled(true);
        TerminalView terminalView4 = this.terminalView;
        if (terminalView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView4 = null;
        }
        terminalView4.getSettings().setDomStorageEnabled(true);
        TerminalView terminalView5 = this.terminalView;
        if (terminalView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView5 = null;
        }
        terminalView5.getSettings().setJavaScriptEnabled(true);
        TerminalView terminalView6 = this.terminalView;
        if (terminalView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView6 = null;
        }
        terminalView6.getSettings().setCacheMode(-1);
        TerminalView terminalView7 = this.terminalView;
        if (terminalView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView7 = null;
        }
        terminalView7.setWebChromeClient(new TerminalWebChromeClient());
        TerminalView terminalView8 = this.terminalView;
        if (terminalView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView8 = null;
        }
        terminalView8.setWebViewClient(new TerminalWebViewClient());
        TerminalView terminalView9 = this.terminalView;
        if (terminalView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView9 = null;
        }
        Context context = getContext();
        context.getClass();
        terminalView9.addJavascriptInterface(new TerminalViewInterface(this, context), "TerminalApp");
        FragmentActivity activity = getActivity();
        activity.getClass();
        ModifierKeysController modifierKeysController = ((MainActivity) activity).getModifierKeysController();
        TerminalView terminalView10 = this.terminalView;
        if (terminalView10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView10 = null;
        }
        modifierKeysController.addTerminalView(terminalView10);
        Set terminalViews = getTerminalViewModel().getTerminalViews();
        TerminalView terminalView11 = this.terminalView;
        if (terminalView11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
        } else {
            terminalView3 = terminalView11;
        }
        terminalViews.add(terminalView3);
    }

    /* compiled from: TerminalTabFragment.kt */
    final class TerminalWebChromeClient extends WebChromeClient {
        public TerminalWebChromeClient() {
        }

        public void onReceivedTitle(WebView webView, String str) {
            View customView;
            TextView textView;
            super.onReceivedTitle(webView, str);
            if (str != null) {
                TerminalTabFragment terminalTabFragment = TerminalTabFragment.this;
                String str2 = null;
                if (StringsKt.endsWith$default(str, " | login -f droid (localhost)", false, 2, (Object) null)) {
                    str = StringsKt.dropLast(str, 29);
                }
                Map terminalTabs = terminalTabFragment.getTerminalViewModel().getTerminalTabs();
                String access$getId$p = terminalTabFragment.id;
                if (access$getId$p == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("id");
                } else {
                    str2 = access$getId$p;
                }
                TabLayout.Tab tab = (TabLayout.Tab) terminalTabs.get(str2);
                if (tab != null && (customView = tab.getCustomView()) != null && (textView = (TextView) customView.findViewById(2131231247)) != null) {
                    textView.setText(str);
                }
            }
        }
    }

    /* compiled from: TerminalTabFragment.kt */
    public final class TerminalViewInterface {
        private final Context mContext;
        final /* synthetic */ TerminalTabFragment this$0;

        public TerminalViewInterface(TerminalTabFragment terminalTabFragment, Context context) {
            context.getClass();
            this.this$0 = terminalTabFragment;
            this.mContext = context;
        }

        @JavascriptInterface
        public final void closeTab() {
            FragmentActivity activity;
            Map terminalTabs = this.this$0.getTerminalViewModel().getTerminalTabs();
            String access$getId$p = this.this$0.id;
            if (access$getId$p == null) {
                Intrinsics.throwUninitializedPropertyAccessException("id");
                access$getId$p = null;
            }
            if (terminalTabs.containsKey(access$getId$p) && this.this$0.getActivity() != null && (activity = this.this$0.getActivity()) != null) {
                activity.runOnUiThread(new TerminalTabFragment$TerminalViewInterface$closeTab$1(this.this$0));
            }
        }
    }

    /* compiled from: TerminalTabFragment.kt */
    final class TerminalWebViewClient extends WebViewClient {
        private boolean loadFailed;
        /* access modifiers changed from: private */
        public long requestId;

        public TerminalWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            Intent intent = new Intent("android.intent.action.VIEW", webResourceRequest != null ? webResourceRequest.getUrl() : null);
            intent.setFlags(268435456);
            TerminalTabFragment.this.startActivity(intent);
            return true;
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            this.loadFailed = false;
        }

        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            webView.getClass();
            webResourceRequest.getClass();
            webResourceError.getClass();
            this.loadFailed = true;
            int errorCode = webResourceError.getErrorCode();
            if (errorCode == -11 || errorCode == -8 || errorCode == -6 || errorCode == -2) {
                webView.reload();
                return;
            }
            String uri = webResourceRequest.getUrl().toString();
            CharSequence description = webResourceError.getDescription();
            Log.e("VmTerminalApp", "Failed to load " + uri + ": " + description);
        }

        public void onPageFinished(WebView webView, String str) {
            webView.getClass();
            if (!this.loadFailed) {
                long j = this.requestId + 1;
                this.requestId = j;
                webView.postVisualStateCallback(j, new TerminalTabFragment$TerminalWebViewClient$onPageFinished$1(this, TerminalTabFragment.this));
            }
        }

        public void onReceivedClientCertRequest(WebView webView, ClientCertRequest clientCertRequest) {
            clientCertRequest.getClass();
            if (TerminalTabFragment.this.privateKey == null || TerminalTabFragment.this.certificates == null) {
                super.onReceivedClientCertRequest(webView, clientCertRequest);
            } else {
                clientCertRequest.proceed(TerminalTabFragment.this.privateKey, TerminalTabFragment.this.certificates);
            }
        }

        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.getClass();
            sslErrorHandler.proceed();
        }
    }

    /* access modifiers changed from: private */
    public final void updateMainActivity() {
        FragmentActivity activity = getActivity();
        activity.getClass();
        MainActivity mainActivity = (MainActivity) activity;
        Button tabAddButton = mainActivity.getTabAddButton();
        tabAddButton.getClass();
        tabAddButton.setEnabled(true);
        mainActivity.getBootCompleted().open();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.security.cert.X509Certificate[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void readClientCertificate() {
        /*
            r4 = this;
            com.android.virtualization.terminal.CertificateUtils r0 = com.android.virtualization.terminal.CertificateUtils.INSTANCE
            java.security.KeyStore$PrivateKeyEntry r1 = r0.createOrGetKey()
            androidx.fragment.app.FragmentActivity r2 = r4.getActivity()
            r2.getClass()
            java.security.cert.Certificate r3 = r1.getCertificate()
            r3.getClass()
            r0.writeCertificateToFile(r2, r3)
            java.security.PrivateKey r0 = r1.getPrivateKey()
            r4.privateKey = r0
            r0 = 1
            java.security.cert.X509Certificate[] r0 = new java.security.cert.X509Certificate[r0]
            java.security.cert.Certificate r1 = r1.getCertificate()
            r1.getClass()
            r2 = 0
            r0[r2] = r1
            r4.certificates = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.TerminalTabFragment.readClientCertificate():void");
    }

    /* access modifiers changed from: private */
    public final void updateFocus() {
        String selectedTabViewId = getTerminalViewModel().getSelectedTabViewId();
        String str = this.id;
        TerminalView terminalView2 = null;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("id");
            str = null;
        }
        if (Intrinsics.areEqual(selectedTabViewId, str)) {
            TerminalView terminalView3 = this.terminalView;
            if (terminalView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            } else {
                terminalView2 = terminalView3;
            }
            terminalView2.requestFocus();
        }
    }

    /* compiled from: TerminalTabFragment.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public void onDestroy() {
        TerminalView terminalView2 = this.terminalView;
        TerminalView terminalView3 = null;
        if (terminalView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView2 = null;
        }
        terminalView2.terminalClose();
        Set terminalViews = getTerminalViewModel().getTerminalViews();
        TerminalView terminalView4 = this.terminalView;
        if (terminalView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
        } else {
            terminalView3 = terminalView4;
        }
        terminalViews.remove(terminalView3);
        super.onDestroy();
    }
}
