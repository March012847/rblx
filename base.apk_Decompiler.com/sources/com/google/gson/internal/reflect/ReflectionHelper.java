package com.google.gson.internal.reflect;

import com.google.gson.JsonIOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class ReflectionHelper {
    private static final RecordHelper RECORD_HELPER;

    static {
        RecordHelper recordHelper;
        try {
            recordHelper = new RecordSupportedHelper();
        } catch (NoSuchMethodException unused) {
            recordHelper = new RecordNotSupportedHelper();
        }
        RECORD_HELPER = recordHelper;
    }

    public static void makeAccessible(AccessibleObject accessibleObject) {
        try {
            accessibleObject.setAccessible(true);
        } catch (Exception e) {
            String accessibleObjectDescription = getAccessibleObjectDescription(accessibleObject, false);
            throw new JsonIOException("Failed making " + accessibleObjectDescription + " accessible; either increase its visibility or write a custom TypeAdapter for its declaring type.", e);
        }
    }

    public static String getAccessibleObjectDescription(AccessibleObject accessibleObject, boolean z) {
        String str;
        if (accessibleObject instanceof Field) {
            str = "field '" + fieldToString((Field) accessibleObject) + "'";
        } else if (accessibleObject instanceof Method) {
            Method method = (Method) accessibleObject;
            StringBuilder sb = new StringBuilder(method.getName());
            appendExecutableParameters(method, sb);
            str = "method '" + method.getDeclaringClass().getName() + "#" + sb.toString() + "'";
        } else if (accessibleObject instanceof Constructor) {
            str = "constructor '" + constructorToString((Constructor) accessibleObject) + "'";
        } else {
            str = "<unknown AccessibleObject> " + accessibleObject.toString();
        }
        if (!z || !Character.isLowerCase(str.charAt(0))) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static String fieldToString(Field field) {
        return field.getDeclaringClass().getName() + "#" + field.getName();
    }

    public static String constructorToString(Constructor constructor) {
        StringBuilder sb = new StringBuilder(constructor.getDeclaringClass().getName());
        appendExecutableParameters(constructor, sb);
        return sb.toString();
    }

    private static void appendExecutableParameters(AccessibleObject accessibleObject, StringBuilder sb) {
        Class[] clsArr;
        sb.append('(');
        if (accessibleObject instanceof Method) {
            clsArr = ((Method) accessibleObject).getParameterTypes();
        } else {
            clsArr = ((Constructor) accessibleObject).getParameterTypes();
        }
        for (int i = 0; i < clsArr.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(clsArr[i].getSimpleName());
        }
        sb.append(')');
    }

    public static String tryMakeAccessible(Constructor constructor) {
        try {
            constructor.setAccessible(true);
            return null;
        } catch (Exception e) {
            return "Failed making constructor '" + constructorToString(constructor) + "' accessible; either increase its visibility or write a custom InstanceCreator or TypeAdapter for its declaring type: " + e.getMessage();
        }
    }

    public static boolean isRecord(Class cls) {
        return RECORD_HELPER.isRecord(cls);
    }

    public static String[] getRecordComponentNames(Class cls) {
        return RECORD_HELPER.getRecordComponentNames(cls);
    }

    public static Method getAccessor(Class cls, Field field) {
        return RECORD_HELPER.getAccessor(cls, field);
    }

    public static Constructor getCanonicalRecordConstructor(Class cls) {
        return RECORD_HELPER.getCanonicalRecordConstructor(cls);
    }

    public static RuntimeException createExceptionForUnexpectedIllegalAccess(IllegalAccessException illegalAccessException) {
        throw new RuntimeException("Unexpected IllegalAccessException occurred (Gson 2.10.1). Certain ReflectionAccessFilter features require Java >= 9 to work correctly. If you are not using ReflectionAccessFilter, report this to the Gson maintainers.", illegalAccessException);
    }

    /* access modifiers changed from: private */
    public static RuntimeException createExceptionForRecordReflectionException(ReflectiveOperationException reflectiveOperationException) {
        throw new RuntimeException("Unexpected ReflectiveOperationException occurred (Gson 2.10.1). To support Java records, reflection is utilized to read out information about records. All these invocations happens after it is established that records exist in the JVM. This exception is unexpected behavior.", reflectiveOperationException);
    }

    abstract class RecordHelper {
        public abstract Method getAccessor(Class cls, Field field);

        /* access modifiers changed from: package-private */
        public abstract Constructor getCanonicalRecordConstructor(Class cls);

        /* access modifiers changed from: package-private */
        public abstract String[] getRecordComponentNames(Class cls);

        /* access modifiers changed from: package-private */
        public abstract boolean isRecord(Class cls);

        private RecordHelper() {
        }
    }

    class RecordSupportedHelper extends RecordHelper {
        private final Method getName;
        private final Method getRecordComponents;
        private final Method getType;
        private final Method isRecord;

        private RecordSupportedHelper() {
            super();
            Class[] clsArr = new Class[0];
            this.isRecord = Class.class.getMethod("isRecord", (Class[]) null);
            Class[] clsArr2 = new Class[0];
            Method method = Class.class.getMethod("getRecordComponents", (Class[]) null);
            this.getRecordComponents = method;
            Class<?> componentType = method.getReturnType().getComponentType();
            Class[] clsArr3 = new Class[0];
            this.getName = componentType.getMethod("getName", (Class[]) null);
            Class[] clsArr4 = new Class[0];
            this.getType = componentType.getMethod("getType", (Class[]) null);
        }

        /* access modifiers changed from: package-private */
        public boolean isRecord(Class cls) {
            try {
                return ((Boolean) this.isRecord.invoke(cls, (Object[]) null)).booleanValue();
            } catch (ReflectiveOperationException e) {
                throw ReflectionHelper.createExceptionForRecordReflectionException(e);
            }
        }

        /* access modifiers changed from: package-private */
        public String[] getRecordComponentNames(Class cls) {
            try {
                Object[] objArr = (Object[]) this.getRecordComponents.invoke(cls, (Object[]) null);
                String[] strArr = new String[objArr.length];
                for (int i = 0; i < objArr.length; i++) {
                    strArr[i] = (String) this.getName.invoke(objArr[i], (Object[]) null);
                }
                return strArr;
            } catch (ReflectiveOperationException e) {
                throw ReflectionHelper.createExceptionForRecordReflectionException(e);
            }
        }

        public Constructor getCanonicalRecordConstructor(Class cls) {
            try {
                Object[] objArr = (Object[]) this.getRecordComponents.invoke(cls, (Object[]) null);
                Class[] clsArr = new Class[objArr.length];
                for (int i = 0; i < objArr.length; i++) {
                    clsArr[i] = (Class) this.getType.invoke(objArr[i], (Object[]) null);
                }
                return cls.getDeclaredConstructor(clsArr);
            } catch (ReflectiveOperationException e) {
                throw ReflectionHelper.createExceptionForRecordReflectionException(e);
            }
        }

        public Method getAccessor(Class cls, Field field) {
            try {
                String name = field.getName();
                Class[] clsArr = new Class[0];
                return cls.getMethod(name, (Class[]) null);
            } catch (ReflectiveOperationException e) {
                throw ReflectionHelper.createExceptionForRecordReflectionException(e);
            }
        }
    }

    class RecordNotSupportedHelper extends RecordHelper {
        /* access modifiers changed from: package-private */
        public boolean isRecord(Class cls) {
            return false;
        }

        private RecordNotSupportedHelper() {
            super();
        }

        /* access modifiers changed from: package-private */
        public String[] getRecordComponentNames(Class cls) {
            throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
        }

        /* access modifiers changed from: package-private */
        public Constructor getCanonicalRecordConstructor(Class cls) {
            throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
        }

        public Method getAccessor(Class cls, Field field) {
            throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
        }
    }
}
