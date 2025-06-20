package com.google.android.material.datepicker;

import android.content.Context;
import com.google.android.material.R$string;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

abstract class DateStrings {
    static String getYearMonth(long j) {
        return UtcDates.getYearMonthFormat(Locale.getDefault()).format(new Date(j));
    }

    static String getMonthDayOfWeekDay(long j) {
        return getMonthDayOfWeekDay(j, Locale.getDefault());
    }

    static String getMonthDayOfWeekDay(long j, Locale locale) {
        return UtcDates.getMonthWeekdayDayFormat(locale).format(new Date(j));
    }

    static String getYearMonthDayOfWeekDay(long j) {
        return getYearMonthDayOfWeekDay(j, Locale.getDefault());
    }

    static String getYearMonthDayOfWeekDay(long j, Locale locale) {
        return UtcDates.getYearMonthWeekdayDayFormat(locale).format(new Date(j));
    }

    static String getOptionalYearMonthDayOfWeekDay(long j) {
        if (isDateWithinCurrentYear(j)) {
            return getMonthDayOfWeekDay(j);
        }
        return getYearMonthDayOfWeekDay(j);
    }

    private static boolean isDateWithinCurrentYear(long j) {
        Calendar todayCalendar = UtcDates.getTodayCalendar();
        Calendar utcCalendar = UtcDates.getUtcCalendar();
        utcCalendar.setTimeInMillis(j);
        if (todayCalendar.get(1) == utcCalendar.get(1)) {
            return true;
        }
        return false;
    }

    static String getDayContentDescription(Context context, long j, boolean z, boolean z2, boolean z3) {
        String optionalYearMonthDayOfWeekDay = getOptionalYearMonthDayOfWeekDay(j);
        if (z) {
            optionalYearMonthDayOfWeekDay = String.format(context.getString(R$string.mtrl_picker_today_description), new Object[]{optionalYearMonthDayOfWeekDay});
        }
        if (z2) {
            return String.format(context.getString(R$string.mtrl_picker_start_date_description), new Object[]{optionalYearMonthDayOfWeekDay});
        }
        return z3 ? String.format(context.getString(R$string.mtrl_picker_end_date_description), new Object[]{optionalYearMonthDayOfWeekDay}) : optionalYearMonthDayOfWeekDay;
    }

    static String getYearContentDescription(Context context, int i) {
        if (UtcDates.getTodayCalendar().get(1) == i) {
            return String.format(context.getString(R$string.mtrl_picker_navigate_to_current_year_description), new Object[]{Integer.valueOf(i)});
        }
        return String.format(context.getString(R$string.mtrl_picker_navigate_to_year_description), new Object[]{Integer.valueOf(i)});
    }
}
