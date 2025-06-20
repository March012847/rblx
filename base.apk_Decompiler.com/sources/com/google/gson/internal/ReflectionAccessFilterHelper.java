package com.google.gson.internal;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.google.gson.ReflectionAccessFilter$FilterResult;
import java.lang.reflect.AccessibleObject;
import java.util.Iterator;
import java.util.List;

public abstract class ReflectionAccessFilterHelper {
    public static ReflectionAccessFilter$FilterResult getFilterResult(List list, Class cls) {
        Iterator it = list.iterator();
        if (!it.hasNext()) {
            return ReflectionAccessFilter$FilterResult.ALLOW;
        }
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
        throw null;
    }

    public static boolean canAccess(AccessibleObject accessibleObject, Object obj) {
        return AccessChecker.INSTANCE.canAccess(accessibleObject, obj);
    }

    abstract class AccessChecker {
        public static final AccessChecker INSTANCE;

        public abstract boolean canAccess(AccessibleObject accessibleObject, Object obj);

        private AccessChecker() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:8:0x001f  */
        static {
            /*
                boolean r0 = com.google.gson.internal.JavaVersion.isJava9OrLater()
                if (r0 == 0) goto L_0x001c
                java.lang.Class<java.lang.reflect.AccessibleObject> r0 = java.lang.reflect.AccessibleObject.class
                java.lang.String r1 = "canAccess"
                r2 = 1
                java.lang.Class[] r2 = new java.lang.Class[r2]     // Catch:{ NoSuchMethodException -> 0x001c }
                java.lang.Class<java.lang.Object> r3 = java.lang.Object.class
                r4 = 0
                r2[r4] = r3     // Catch:{ NoSuchMethodException -> 0x001c }
                java.lang.reflect.Method r0 = r0.getDeclaredMethod(r1, r2)     // Catch:{ NoSuchMethodException -> 0x001c }
                com.google.gson.internal.ReflectionAccessFilterHelper$AccessChecker$1 r1 = new com.google.gson.internal.ReflectionAccessFilterHelper$AccessChecker$1     // Catch:{ NoSuchMethodException -> 0x001c }
                r1.<init>(r0)     // Catch:{ NoSuchMethodException -> 0x001c }
                goto L_0x001d
            L_0x001c:
                r1 = 0
            L_0x001d:
                if (r1 != 0) goto L_0x0024
                com.google.gson.internal.ReflectionAccessFilterHelper$AccessChecker$2 r1 = new com.google.gson.internal.ReflectionAccessFilterHelper$AccessChecker$2
                r1.<init>()
            L_0x0024:
                INSTANCE = r1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.ReflectionAccessFilterHelper.AccessChecker.<clinit>():void");
        }
    }
}
