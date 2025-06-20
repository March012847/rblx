package com.android.virtualization.terminal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

/* compiled from: SettingsItemAdapter.kt */
public final class SettingsItemAdapter extends RecyclerView.Adapter {
    /* access modifiers changed from: private */
    public final List dataSet;

    public SettingsItemAdapter(List list) {
        list.getClass();
        this.dataSet = list;
    }

    /* compiled from: SettingsItemAdapter.kt */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView card;
        private final ImageView icon;
        private final TextView subTitle;
        private final TextView title;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public ViewHolder(View view) {
            super(view);
            view.getClass();
            View findViewById = view.findViewById(2131231179);
            findViewById.getClass();
            this.card = (MaterialCardView) findViewById;
            View findViewById2 = view.findViewById(2131231180);
            findViewById2.getClass();
            this.icon = (ImageView) findViewById2;
            View findViewById3 = view.findViewById(2131231182);
            findViewById3.getClass();
            this.title = (TextView) findViewById3;
            View findViewById4 = view.findViewById(2131231181);
            findViewById4.getClass();
            this.subTitle = (TextView) findViewById4;
        }

        public final MaterialCardView getCard() {
            return this.card;
        }

        public final ImageView getIcon() {
            return this.icon;
        }

        public final TextView getTitle() {
            return this.title;
        }

        public final TextView getSubTitle() {
            return this.subTitle;
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        viewGroup.getClass();
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(2131427446, viewGroup, false);
        inflate.getClass();
        return new ViewHolder(inflate);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.getClass();
        viewHolder.getIcon().setImageResource(((SettingsItem) this.dataSet.get(i)).getIcon());
        viewHolder.getTitle().setText(((SettingsItem) this.dataSet.get(i)).getTitle());
        viewHolder.getSubTitle().setText(((SettingsItem) this.dataSet.get(i)).getSubTitle());
        viewHolder.getCard().setOnClickListener(new SettingsItemAdapter$onBindViewHolder$1(viewHolder, this, i));
    }

    public int getItemCount() {
        return this.dataSet.size();
    }
}
