package com.google.gson.internal.bind;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.ReflectionAccessFilter$FilterResult;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.ReflectionAccessFilterHelper;
import com.google.gson.internal.reflect.ReflectionHelper;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;
    private final Excluder excluder;
    private final FieldNamingStrategy fieldNamingPolicy;
    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
    private final List reflectionFilters;

    public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor2, FieldNamingStrategy fieldNamingStrategy, Excluder excluder2, JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory, List list) {
        this.constructorConstructor = constructorConstructor2;
        this.fieldNamingPolicy = fieldNamingStrategy;
        this.excluder = excluder2;
        this.jsonAdapterFactory = jsonAdapterAnnotationTypeAdapterFactory;
        this.reflectionFilters = list;
    }

    private boolean includeField(Field field, boolean z) {
        return !this.excluder.excludeClass(field.getType(), z) && !this.excluder.excludeField(field, z);
    }

    private List getFieldNames(Field field) {
        SerializedName serializedName = (SerializedName) field.getAnnotation(SerializedName.class);
        if (serializedName == null) {
            return Collections.singletonList(this.fieldNamingPolicy.translateName(field));
        }
        String value = serializedName.value();
        String[] alternate = serializedName.alternate();
        if (alternate.length == 0) {
            return Collections.singletonList(value);
        }
        ArrayList arrayList = new ArrayList(alternate.length + 1);
        arrayList.add(value);
        Collections.addAll(arrayList, alternate);
        return arrayList;
    }

    public TypeAdapter create(Gson gson, TypeToken typeToken) {
        Class rawType = typeToken.getRawType();
        if (!Object.class.isAssignableFrom(rawType)) {
            return null;
        }
        ReflectionAccessFilter$FilterResult filterResult = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, rawType);
        if (filterResult != ReflectionAccessFilter$FilterResult.BLOCK_ALL) {
            boolean z = filterResult == ReflectionAccessFilter$FilterResult.BLOCK_INACCESSIBLE;
            if (ReflectionHelper.isRecord(rawType)) {
                return new RecordAdapter(rawType, getBoundFields(gson, typeToken, rawType, z, true), z);
            }
            TypeToken typeToken2 = typeToken;
            return new FieldReflectionAdapter(this.constructorConstructor.get(typeToken2), getBoundFields(gson, typeToken2, rawType, z, false));
        }
        throw new JsonIOException("ReflectionAccessFilter does not permit using reflection for " + rawType + ". Register a TypeAdapter for this type or adjust the access filter.");
    }

    /* access modifiers changed from: private */
    public static void checkAccessible(Object obj, AccessibleObject accessibleObject) {
        if (Modifier.isStatic(((Member) accessibleObject).getModifiers())) {
            obj = null;
        }
        if (!ReflectionAccessFilterHelper.canAccess(accessibleObject, obj)) {
            String accessibleObjectDescription = ReflectionHelper.getAccessibleObjectDescription(accessibleObject, true);
            throw new JsonIOException(accessibleObjectDescription + " is not accessible and ReflectionAccessFilter does not permit making it accessible. Register a TypeAdapter for the declaring type, adjust the access filter or increase the visibility of the element and its declaring type.");
        }
    }

    private BoundField createBoundField(Gson gson, Field field, Method method, String str, TypeToken typeToken, boolean z, boolean z2, boolean z3) {
        final TypeToken typeToken2 = typeToken;
        final boolean isPrimitive = Primitives.isPrimitive(typeToken2.getRawType());
        int modifiers = field.getModifiers();
        final boolean z4 = Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
        Field field2 = field;
        JsonAdapter jsonAdapter = (JsonAdapter) field2.getAnnotation(JsonAdapter.class);
        TypeAdapter typeAdapter = jsonAdapter != null ? this.jsonAdapterFactory.getTypeAdapter(this.constructorConstructor, gson, typeToken2, jsonAdapter) : null;
        final boolean z5 = typeAdapter != null;
        if (typeAdapter == null) {
            typeAdapter = gson.getAdapter(typeToken2);
        }
        final TypeAdapter typeAdapter2 = typeAdapter;
        final Gson gson2 = gson;
        final Method method2 = method;
        final boolean z6 = z3;
        return new BoundField(str, field2, z, z2) {
            /* access modifiers changed from: package-private */
            public void write(JsonWriter jsonWriter, Object obj) {
                Object obj2;
                TypeAdapter typeAdapter;
                if (this.serialized) {
                    if (z6) {
                        Method method = method2;
                        if (method == null) {
                            ReflectiveTypeAdapterFactory.checkAccessible(obj, this.field);
                        } else {
                            ReflectiveTypeAdapterFactory.checkAccessible(obj, method);
                        }
                    }
                    Method method2 = method2;
                    if (method2 != null) {
                        try {
                            obj2 = method2.invoke(obj, (Object[]) null);
                        } catch (InvocationTargetException e) {
                            String accessibleObjectDescription = ReflectionHelper.getAccessibleObjectDescription(method2, false);
                            throw new JsonIOException("Accessor " + accessibleObjectDescription + " threw exception", e.getCause());
                        }
                    } else {
                        obj2 = this.field.get(obj);
                    }
                    if (obj2 != obj) {
                        jsonWriter.name(this.name);
                        if (z5) {
                            typeAdapter = typeAdapter2;
                        } else {
                            typeAdapter = new TypeAdapterRuntimeTypeWrapper(gson2, typeAdapter2, typeToken2.getType());
                        }
                        typeAdapter.write(jsonWriter, obj2);
                    }
                }
            }

            /* access modifiers changed from: package-private */
            public void readIntoArray(JsonReader jsonReader, int i, Object[] objArr) {
                Object read = typeAdapter2.read(jsonReader);
                if (read != null || !isPrimitive) {
                    objArr[i] = read;
                    return;
                }
                throw new JsonParseException("null is not allowed as value for record component '" + this.fieldName + "' of primitive type; at path " + jsonReader.getPath());
            }

            /* access modifiers changed from: package-private */
            public void readIntoField(JsonReader jsonReader, Object obj) {
                Object read = typeAdapter2.read(jsonReader);
                if (read != null || !isPrimitive) {
                    if (z6) {
                        ReflectiveTypeAdapterFactory.checkAccessible(obj, this.field);
                    } else if (z4) {
                        String accessibleObjectDescription = ReflectionHelper.getAccessibleObjectDescription(this.field, false);
                        throw new JsonIOException("Cannot set value of 'static final' " + accessibleObjectDescription);
                    }
                    this.field.set(obj, read);
                }
            }
        };
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0131 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0123 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Map getBoundFields(com.google.gson.Gson r21, com.google.gson.reflect.TypeToken r22, java.lang.Class r23, boolean r24, boolean r25) {
        /*
            r20 = this;
            r0 = r20
            r9 = r23
            java.util.LinkedHashMap r10 = new java.util.LinkedHashMap
            r10.<init>()
            boolean r1 = r9.isInterface()
            if (r1 == 0) goto L_0x0011
            goto L_0x018d
        L_0x0011:
            r11 = r22
            r1 = r24
            r12 = r9
        L_0x0016:
            java.lang.Class<java.lang.Object> r2 = java.lang.Object.class
            if (r12 == r2) goto L_0x018d
            java.lang.reflect.Field[] r13 = r12.getDeclaredFields()
            r14 = 1
            r15 = 0
            if (r12 == r9) goto L_0x0036
            int r2 = r13.length
            if (r2 <= 0) goto L_0x0036
            java.util.List r1 = r0.reflectionFilters
            com.google.gson.ReflectionAccessFilter$FilterResult r1 = com.google.gson.internal.ReflectionAccessFilterHelper.getFilterResult(r1, r12)
            com.google.gson.ReflectionAccessFilter$FilterResult r2 = com.google.gson.ReflectionAccessFilter$FilterResult.BLOCK_ALL
            if (r1 == r2) goto L_0x0038
            com.google.gson.ReflectionAccessFilter$FilterResult r2 = com.google.gson.ReflectionAccessFilter$FilterResult.BLOCK_INACCESSIBLE
            if (r1 != r2) goto L_0x0035
            r1 = r14
            goto L_0x0036
        L_0x0035:
            r1 = r15
        L_0x0036:
            r8 = r1
            goto L_0x005c
        L_0x0038:
            com.google.gson.JsonIOException r0 = new com.google.gson.JsonIOException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "ReflectionAccessFilter does not permit using reflection for "
            r1.append(r2)
            r1.append(r12)
            java.lang.String r2 = " (supertype of "
            r1.append(r2)
            r1.append(r9)
            java.lang.String r2 = "). Register a TypeAdapter for this type or adjust the access filter."
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>((java.lang.String) r1)
            throw r0
        L_0x005c:
            int r1 = r13.length
            r2 = r15
        L_0x005e:
            if (r2 >= r1) goto L_0x0170
            r3 = r2
            r2 = r13[r3]
            boolean r4 = r0.includeField(r2, r14)
            boolean r5 = r0.includeField(r2, r15)
            if (r4 != 0) goto L_0x0077
            if (r5 != 0) goto L_0x0077
            r16 = r1
            r17 = r3
            r22 = r11
            goto L_0x0123
        L_0x0077:
            if (r25 == 0) goto L_0x00c8
            int r7 = r2.getModifiers()
            boolean r7 = java.lang.reflect.Modifier.isStatic(r7)
            if (r7 == 0) goto L_0x0087
            r5 = r3
            r7 = r15
        L_0x0085:
            r3 = 0
            goto L_0x00cb
        L_0x0087:
            java.lang.reflect.Method r7 = com.google.gson.internal.reflect.ReflectionHelper.getAccessor(r12, r2)
            if (r8 != 0) goto L_0x0090
            com.google.gson.internal.reflect.ReflectionHelper.makeAccessible(r7)
        L_0x0090:
            java.lang.Class<com.google.gson.annotations.SerializedName> r6 = com.google.gson.annotations.SerializedName.class
            java.lang.annotation.Annotation r6 = r7.getAnnotation(r6)
            if (r6 == 0) goto L_0x00c1
            java.lang.Class<com.google.gson.annotations.SerializedName> r6 = com.google.gson.annotations.SerializedName.class
            java.lang.annotation.Annotation r6 = r2.getAnnotation(r6)
            if (r6 == 0) goto L_0x00a1
            goto L_0x00c1
        L_0x00a1:
            java.lang.String r0 = com.google.gson.internal.reflect.ReflectionHelper.getAccessibleObjectDescription(r7, r15)
            com.google.gson.JsonIOException r1 = new com.google.gson.JsonIOException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "@SerializedName on "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r0 = " is not supported"
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>((java.lang.String) r0)
            throw r1
        L_0x00c1:
            r19 = r5
            r5 = r3
            r3 = r7
            r7 = r19
            goto L_0x00cb
        L_0x00c8:
            r7 = r5
            r5 = r3
            goto L_0x0085
        L_0x00cb:
            if (r8 != 0) goto L_0x00d2
            if (r3 != 0) goto L_0x00d2
            com.google.gson.internal.reflect.ReflectionHelper.makeAccessible(r2)
        L_0x00d2:
            java.lang.reflect.Type r6 = r11.getType()
            java.lang.reflect.Type r14 = r2.getGenericType()
            java.lang.reflect.Type r14 = com.google.gson.internal.C$Gson$Types.resolve(r6, r12, r14)
            java.util.List r6 = r0.getFieldNames(r2)
            int r15 = r6.size()
            r22 = r11
            r9 = 0
            r11 = 0
        L_0x00ea:
            if (r11 >= r15) goto L_0x011d
            java.lang.Object r16 = r6.get(r11)
            java.lang.String r16 = (java.lang.String) r16
            if (r11 == 0) goto L_0x00f5
            r4 = 0
        L_0x00f5:
            r17 = r5
            com.google.gson.reflect.TypeToken r5 = com.google.gson.reflect.TypeToken.get((java.lang.reflect.Type) r14)
            r18 = r6
            r6 = r4
            r4 = r16
            r16 = r1
            r1 = r21
            com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$BoundField r5 = r0.createBoundField(r1, r2, r3, r4, r5, r6, r7, r8)
            java.lang.Object r0 = r10.put(r4, r5)
            com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$BoundField r0 = (com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField) r0
            if (r9 != 0) goto L_0x0111
            r9 = r0
        L_0x0111:
            int r11 = r11 + 1
            r0 = r20
            r4 = r6
            r1 = r16
            r5 = r17
            r6 = r18
            goto L_0x00ea
        L_0x011d:
            r16 = r1
            r17 = r5
            if (r9 != 0) goto L_0x0131
        L_0x0123:
            int r2 = r17 + 1
            r0 = r20
            r11 = r22
            r9 = r23
            r1 = r16
            r14 = 1
            r15 = 0
            goto L_0x005e
        L_0x0131:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "Class "
            r1.append(r3)
            java.lang.String r3 = r23.getName()
            r1.append(r3)
            java.lang.String r3 = " declares multiple JSON fields named '"
            r1.append(r3)
            java.lang.String r3 = r9.name
            r1.append(r3)
            java.lang.String r3 = "'; conflict is caused by fields "
            r1.append(r3)
            java.lang.reflect.Field r3 = r9.field
            java.lang.String r3 = com.google.gson.internal.reflect.ReflectionHelper.fieldToString(r3)
            r1.append(r3)
            java.lang.String r3 = " and "
            r1.append(r3)
            java.lang.String r2 = com.google.gson.internal.reflect.ReflectionHelper.fieldToString(r2)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0170:
            r22 = r11
            java.lang.reflect.Type r0 = r22.getType()
            java.lang.reflect.Type r1 = r12.getGenericSuperclass()
            java.lang.reflect.Type r0 = com.google.gson.internal.C$Gson$Types.resolve(r0, r12, r1)
            com.google.gson.reflect.TypeToken r11 = com.google.gson.reflect.TypeToken.get((java.lang.reflect.Type) r0)
            java.lang.Class r12 = r11.getRawType()
            r0 = r20
            r9 = r23
            r1 = r8
            goto L_0x0016
        L_0x018d:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.getBoundFields(com.google.gson.Gson, com.google.gson.reflect.TypeToken, java.lang.Class, boolean, boolean):java.util.Map");
    }

    abstract class BoundField {
        final boolean deserialized;
        final Field field;
        final String fieldName;
        final String name;
        final boolean serialized;

        /* access modifiers changed from: package-private */
        public abstract void readIntoArray(JsonReader jsonReader, int i, Object[] objArr);

        /* access modifiers changed from: package-private */
        public abstract void readIntoField(JsonReader jsonReader, Object obj);

        /* access modifiers changed from: package-private */
        public abstract void write(JsonWriter jsonWriter, Object obj);

        protected BoundField(String str, Field field2, boolean z, boolean z2) {
            this.name = str;
            this.field = field2;
            this.fieldName = field2.getName();
            this.serialized = z;
            this.deserialized = z2;
        }
    }

    public abstract class Adapter<T, A> extends TypeAdapter {
        final Map boundFields;

        /* access modifiers changed from: package-private */
        public abstract Object createAccumulator();

        /* access modifiers changed from: package-private */
        public abstract Object finalize(Object obj);

        /* access modifiers changed from: package-private */
        public abstract void readField(Object obj, JsonReader jsonReader, BoundField boundField);

        Adapter(Map map) {
            this.boundFields = map;
        }

        public void write(JsonWriter jsonWriter, Object obj) {
            if (obj == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginObject();
            try {
                for (BoundField write : this.boundFields.values()) {
                    write.write(jsonWriter, obj);
                }
                jsonWriter.endObject();
            } catch (IllegalAccessException e) {
                throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e);
            }
        }

        public Object read(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            Object createAccumulator = createAccumulator();
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    BoundField boundField = (BoundField) this.boundFields.get(jsonReader.nextName());
                    if (boundField != null) {
                        if (boundField.deserialized) {
                            readField(createAccumulator, jsonReader, boundField);
                        }
                    }
                    jsonReader.skipValue();
                }
                jsonReader.endObject();
                return finalize(createAccumulator);
            } catch (IllegalStateException e) {
                throw new JsonSyntaxException((Throwable) e);
            } catch (IllegalAccessException e2) {
                throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e2);
            }
        }
    }

    final class FieldReflectionAdapter<T> extends Adapter<T, T> {
        private final ObjectConstructor constructor;

        /* access modifiers changed from: package-private */
        public Object finalize(Object obj) {
            return obj;
        }

        FieldReflectionAdapter(ObjectConstructor objectConstructor, Map map) {
            super(map);
            this.constructor = objectConstructor;
        }

        /* access modifiers changed from: package-private */
        public Object createAccumulator() {
            return this.constructor.construct();
        }

        /* access modifiers changed from: package-private */
        public void readField(Object obj, JsonReader jsonReader, BoundField boundField) {
            boundField.readIntoField(jsonReader, obj);
        }
    }

    final class RecordAdapter<T> extends Adapter<T, Object[]> {
        static final Map PRIMITIVE_DEFAULTS = primitiveDefaults();
        private final Map componentIndices = new HashMap();
        private final Constructor constructor;
        private final Object[] constructorArgsDefaults;

        RecordAdapter(Class cls, Map map, boolean z) {
            super(map);
            Constructor canonicalRecordConstructor = ReflectionHelper.getCanonicalRecordConstructor(cls);
            this.constructor = canonicalRecordConstructor;
            if (z) {
                ReflectiveTypeAdapterFactory.checkAccessible((Object) null, canonicalRecordConstructor);
            } else {
                ReflectionHelper.makeAccessible(canonicalRecordConstructor);
            }
            String[] recordComponentNames = ReflectionHelper.getRecordComponentNames(cls);
            for (int i = 0; i < recordComponentNames.length; i++) {
                this.componentIndices.put(recordComponentNames[i], Integer.valueOf(i));
            }
            Class[] parameterTypes = this.constructor.getParameterTypes();
            this.constructorArgsDefaults = new Object[parameterTypes.length];
            for (int i2 = 0; i2 < parameterTypes.length; i2++) {
                this.constructorArgsDefaults[i2] = PRIMITIVE_DEFAULTS.get(parameterTypes[i2]);
            }
        }

        private static Map primitiveDefaults() {
            HashMap hashMap = new HashMap();
            hashMap.put(Byte.TYPE, (byte) 0);
            hashMap.put(Short.TYPE, (short) 0);
            hashMap.put(Integer.TYPE, 0);
            hashMap.put(Long.TYPE, 0L);
            hashMap.put(Float.TYPE, Float.valueOf(0.0f));
            hashMap.put(Double.TYPE, Double.valueOf(0.0d));
            hashMap.put(Character.TYPE, 0);
            hashMap.put(Boolean.TYPE, Boolean.FALSE);
            return hashMap;
        }

        /* access modifiers changed from: package-private */
        public Object[] createAccumulator() {
            return (Object[]) this.constructorArgsDefaults.clone();
        }

        /* access modifiers changed from: package-private */
        public void readField(Object[] objArr, JsonReader jsonReader, BoundField boundField) {
            Integer num = (Integer) this.componentIndices.get(boundField.fieldName);
            if (num != null) {
                boundField.readIntoArray(jsonReader, num.intValue(), objArr);
                return;
            }
            throw new IllegalStateException("Could not find the index in the constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' for field with name '" + boundField.fieldName + "', unable to determine which argument in the constructor the field corresponds to. This is unexpected behavior, as we expect the RecordComponents to have the same names as the fields in the Java class, and that the order of the RecordComponents is the same as the order of the canonical constructor parameters.");
        }

        /* access modifiers changed from: package-private */
        public Object finalize(Object[] objArr) {
            try {
                return this.constructor.newInstance(objArr);
            } catch (IllegalAccessException e) {
                throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e);
            } catch (IllegalArgumentException | InstantiationException e2) {
                throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' with args " + Arrays.toString(objArr), e2);
            } catch (InvocationTargetException e3) {
                throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' with args " + Arrays.toString(objArr), e3.getCause());
            }
        }
    }
}
