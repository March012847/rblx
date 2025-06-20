package com.google.gson.internal.bind.util;

import java.util.TimeZone;

public abstract class ISO8601Utils {
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");

    /* JADX WARNING: Removed duplicated region for block: B:50:0x00dc A[Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x01c0 A[Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Date parse(java.lang.String r18, java.text.ParsePosition r19) {
        /*
            r1 = r18
            r2 = r19
            int r0 = r2.getIndex()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            int r3 = r0 + 4
            int r4 = parseInt(r1, r0, r3)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r5 = 45
            boolean r6 = checkOffset(r1, r3, r5)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r7 = 5
            if (r6 == 0) goto L_0x0019
            int r3 = r0 + 5
        L_0x0019:
            int r0 = r3 + 2
            int r6 = parseInt(r1, r3, r0)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            boolean r8 = checkOffset(r1, r0, r5)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            if (r8 == 0) goto L_0x0027
            int r0 = r3 + 3
        L_0x0027:
            int r3 = r0 + 2
            int r8 = parseInt(r1, r0, r3)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r9 = 84
            boolean r9 = checkOffset(r1, r3, r9)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r10 = 1
            r11 = 0
            if (r9 != 0) goto L_0x0051
            int r12 = r1.length()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            if (r12 > r3) goto L_0x0051
            java.util.GregorianCalendar r0 = new java.util.GregorianCalendar     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            int r6 = r6 - r10
            r0.<init>(r4, r6, r8)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r0.setLenient(r11)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r2.setIndex(r3)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.util.Date r0 = r0.getTime()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            return r0
        L_0x004e:
            r0 = move-exception
            goto L_0x01c8
        L_0x0051:
            r12 = 43
            r13 = 90
            r14 = 2
            if (r9 == 0) goto L_0x00d3
            int r3 = r0 + 3
            int r9 = r0 + 5
            int r3 = parseInt(r1, r3, r9)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r15 = 58
            boolean r16 = checkOffset(r1, r9, r15)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            if (r16 == 0) goto L_0x006a
            int r9 = r0 + 6
        L_0x006a:
            int r0 = r9 + 2
            int r16 = parseInt(r1, r9, r0)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            boolean r15 = checkOffset(r1, r0, r15)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            if (r15 == 0) goto L_0x0079
            int r9 = r9 + 3
            r0 = r9
        L_0x0079:
            int r9 = r1.length()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            if (r9 <= r0) goto L_0x00cb
            char r9 = r1.charAt(r0)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            if (r9 == r13) goto L_0x00cb
            if (r9 == r12) goto L_0x00cb
            if (r9 == r5) goto L_0x00cb
            int r9 = r0 + 2
            int r15 = parseInt(r1, r0, r9)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r11 = 59
            if (r15 <= r11) goto L_0x0099
            r11 = 63
            if (r15 >= r11) goto L_0x0099
            r15 = 59
        L_0x0099:
            r11 = 46
            boolean r11 = checkOffset(r1, r9, r11)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            if (r11 == 0) goto L_0x00c5
            int r9 = r0 + 3
            int r11 = r0 + 4
            int r11 = indexOfNonDigit(r1, r11)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            int r0 = r0 + 6
            int r0 = java.lang.Math.min(r11, r0)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            int r17 = parseInt(r1, r9, r0)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            int r0 = r0 - r9
            if (r0 == r10) goto L_0x00bc
            if (r0 == r14) goto L_0x00b9
            goto L_0x00be
        L_0x00b9:
            int r17 = r17 * 10
            goto L_0x00be
        L_0x00bc:
            int r17 = r17 * 100
        L_0x00be:
            r0 = r3
            r3 = r11
            r9 = r16
            r11 = r17
            goto L_0x00d6
        L_0x00c5:
            r0 = r3
            r3 = r9
            r9 = r16
            r11 = 0
            goto L_0x00d6
        L_0x00cb:
            r9 = r3
            r3 = r0
            r0 = r9
            r9 = r16
        L_0x00d0:
            r11 = 0
            r15 = 0
            goto L_0x00d6
        L_0x00d3:
            r0 = 0
            r9 = 0
            goto L_0x00d0
        L_0x00d6:
            int r14 = r1.length()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            if (r14 <= r3) goto L_0x01c0
            char r14 = r1.charAt(r3)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            if (r14 != r13) goto L_0x00e7
            java.util.TimeZone r5 = TIMEZONE_UTC     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            int r3 = r3 + r10
            goto L_0x018f
        L_0x00e7:
            if (r14 == r12) goto L_0x0108
            if (r14 != r5) goto L_0x00ec
            goto L_0x0108
        L_0x00ec:
            java.lang.IndexOutOfBoundsException r0 = new java.lang.IndexOutOfBoundsException     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r3.<init>()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.lang.String r4 = "Invalid time zone indicator '"
            r3.append(r4)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r3.append(r14)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.lang.String r4 = "'"
            r3.append(r4)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.lang.String r3 = r3.toString()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r0.<init>(r3)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            throw r0     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
        L_0x0108:
            java.lang.String r5 = r1.substring(r3)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            int r12 = r5.length()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            if (r12 < r7) goto L_0x0113
            goto L_0x0124
        L_0x0113:
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r12.<init>()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r12.append(r5)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.lang.String r5 = "00"
            r12.append(r5)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.lang.String r5 = r12.toString()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
        L_0x0124:
            int r12 = r5.length()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            int r3 = r3 + r12
            java.lang.String r12 = "+0000"
            boolean r12 = r12.equals(r5)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            if (r12 != 0) goto L_0x018d
            java.lang.String r12 = "+00:00"
            boolean r12 = r12.equals(r5)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            if (r12 == 0) goto L_0x013a
            goto L_0x018d
        L_0x013a:
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r12.<init>()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.lang.String r13 = "GMT"
            r12.append(r13)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r12.append(r5)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.lang.String r5 = r12.toString()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.util.TimeZone r12 = java.util.TimeZone.getTimeZone(r5)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.lang.String r13 = r12.getID()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            boolean r14 = r13.equals(r5)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            if (r14 != 0) goto L_0x018b
            java.lang.String r14 = ":"
            java.lang.String r7 = ""
            java.lang.String r7 = r13.replace(r14, r7)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            boolean r7 = r7.equals(r5)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            if (r7 == 0) goto L_0x0168
            goto L_0x018b
        L_0x0168:
            java.lang.IndexOutOfBoundsException r0 = new java.lang.IndexOutOfBoundsException     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r3.<init>()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.lang.String r4 = "Mismatching time zone indicator: "
            r3.append(r4)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r3.append(r5)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.lang.String r4 = " given, resolves to "
            r3.append(r4)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.lang.String r4 = r12.getID()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r3.append(r4)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.lang.String r3 = r3.toString()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r0.<init>(r3)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            throw r0     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
        L_0x018b:
            r5 = r12
            goto L_0x018f
        L_0x018d:
            java.util.TimeZone r5 = TIMEZONE_UTC     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
        L_0x018f:
            java.util.GregorianCalendar r7 = new java.util.GregorianCalendar     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r7.<init>(r5)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r5 = 0
            r7.setLenient(r5)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r7.set(r10, r4)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            int r6 = r6 - r10
            r4 = 2
            r7.set(r4, r6)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r4 = 5
            r7.set(r4, r8)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r4 = 11
            r7.set(r4, r0)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r0 = 12
            r7.set(r0, r9)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r0 = 13
            r7.set(r0, r15)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r0 = 14
            r7.set(r0, r11)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            r2.setIndex(r3)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.util.Date r0 = r7.getTime()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            return r0
        L_0x01c0:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            java.lang.String r3 = "No time zone indicator"
            r0.<init>(r3)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
            throw r0     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException | NumberFormatException -> 0x004e }
        L_0x01c8:
            if (r1 != 0) goto L_0x01cc
            r1 = 0
            goto L_0x01e0
        L_0x01cc:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r4 = 34
            r3.append(r4)
            r3.append(r1)
            r3.append(r4)
            java.lang.String r1 = r3.toString()
        L_0x01e0:
            java.lang.String r3 = r0.getMessage()
            if (r3 == 0) goto L_0x01ec
            boolean r4 = r3.isEmpty()
            if (r4 == 0) goto L_0x020a
        L_0x01ec:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "("
            r3.append(r4)
            java.lang.Class r4 = r0.getClass()
            java.lang.String r4 = r4.getName()
            r3.append(r4)
            java.lang.String r4 = ")"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
        L_0x020a:
            java.text.ParseException r4 = new java.text.ParseException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Failed to parse date ["
            r5.append(r6)
            r5.append(r1)
            java.lang.String r1 = "]: "
            r5.append(r1)
            r5.append(r3)
            java.lang.String r1 = r5.toString()
            int r2 = r2.getIndex()
            r4.<init>(r1, r2)
            r4.initCause(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.util.ISO8601Utils.parse(java.lang.String, java.text.ParsePosition):java.util.Date");
    }

    private static boolean checkOffset(String str, int i, char c) {
        return i < str.length() && str.charAt(i) == c;
    }

    private static int parseInt(String str, int i, int i2) {
        int i3;
        int i4;
        if (i < 0 || i2 > str.length() || i > i2) {
            throw new NumberFormatException(str);
        }
        if (i < i2) {
            i4 = i + 1;
            int digit = Character.digit(str.charAt(i), 10);
            if (digit >= 0) {
                i3 = -digit;
            } else {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
        } else {
            i3 = 0;
            i4 = i;
        }
        while (i4 < i2) {
            int i5 = i4 + 1;
            int digit2 = Character.digit(str.charAt(i4), 10);
            if (digit2 >= 0) {
                i3 = (i3 * 10) - digit2;
                i4 = i5;
            } else {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
        }
        return -i3;
    }

    private static int indexOfNonDigit(String str, int i) {
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt < '0' || charAt > '9') {
                return i;
            }
            i++;
        }
        return str.length();
    }
}
