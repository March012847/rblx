package com.android.virtualization.terminal;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.ArrayList;

/* compiled from: SettingsActivity.kt */
public final class SettingsActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427444);
        View findViewById = findViewById(2131231198);
        findViewById.getClass();
        setSupportActionBar((MaterialToolbar) findViewById);
        ArrayList arrayList = new ArrayList();
        String string = getResources().getString(2131689689);
        string.getClass();
        String string2 = getResources().getString(2131689688);
        string2.getClass();
        arrayList.add(new SettingsItem(string, string2, 2131165308, SettingsItemEnum.DiskResize));
        String string3 = getResources().getString(2131689707);
        string3.getClass();
        String string4 = getResources().getString(2131689706);
        string4.getClass();
        arrayList.add(new SettingsItem(string3, string4, 2131165306, SettingsItemEnum.PortForwarding));
        String string5 = getResources().getString(2131689721);
        string5.getClass();
        String string6 = getResources().getString(2131689720);
        string6.getClass();
        arrayList.add(new SettingsItem(string5, string6, 2131165307, SettingsItemEnum.Recovery));
        SettingsItemAdapter settingsItemAdapter = new SettingsItemAdapter(arrayList);
        View findViewById2 = findViewById(2131231183);
        findViewById2.getClass();
        RecyclerView recyclerView = (RecyclerView) findViewById2;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(settingsItemAdapter);
    }
}
