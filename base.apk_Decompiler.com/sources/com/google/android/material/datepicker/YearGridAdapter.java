package com.google.android.material.datepicker;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R$layout;
import java.util.Locale;

class YearGridAdapter extends RecyclerView.Adapter {
    private final MaterialCalendar materialCalendar;

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;

        ViewHolder(TextView textView2) {
            super(textView2);
            this.textView = textView2;
        }
    }

    YearGridAdapter(MaterialCalendar materialCalendar2) {
        this.materialCalendar = materialCalendar2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder((TextView) LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.mtrl_calendar_year, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        int yearForPosition = getYearForPosition(i);
        viewHolder.textView.setText(String.format(Locale.getDefault(), "%d", new Object[]{Integer.valueOf(yearForPosition)}));
        TextView textView = viewHolder.textView;
        textView.setContentDescription(DateStrings.getYearContentDescription(textView.getContext(), yearForPosition));
        CalendarStyle calendarStyle = this.materialCalendar.getCalendarStyle();
        if (UtcDates.getTodayCalendar().get(1) == yearForPosition) {
            CalendarItemStyle calendarItemStyle = calendarStyle.todayYear;
        } else {
            CalendarItemStyle calendarItemStyle2 = calendarStyle.year;
        }
        this.materialCalendar.getDateSelector();
        throw null;
    }

    public int getItemCount() {
        return this.materialCalendar.getCalendarConstraints().getYearSpan();
    }

    /* access modifiers changed from: package-private */
    public int getPositionForYear(int i) {
        return i - this.materialCalendar.getCalendarConstraints().getStart().year;
    }

    /* access modifiers changed from: package-private */
    public int getYearForPosition(int i) {
        return this.materialCalendar.getCalendarConstraints().getStart().year + i;
    }
}
