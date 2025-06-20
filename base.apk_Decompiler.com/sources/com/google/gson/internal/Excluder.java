package com.google.gson.internal;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Excluder implements TypeAdapterFactory, Cloneable {
    public static final Excluder DEFAULT = new Excluder();
    private List deserializationStrategies;
    private int modifiers = 136;
    private boolean requireExpose;
    private List serializationStrategies;
    private boolean serializeInnerClasses = true;
    private double version = -1.0d;

    public Excluder() {
        List list = Collections.EMPTY_LIST;
        this.serializationStrategies = list;
        this.deserializationStrategies = list;
    }

    /* access modifiers changed from: protected */
    public Excluder clone() {
        try {
            return (Excluder) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public TypeAdapter create(Gson gson, TypeToken typeToken) {
        Class rawType = typeToken.getRawType();
        boolean excludeClassChecks = excludeClassChecks(rawType);
        final boolean z = excludeClassChecks || excludeClassInStrategy(rawType, true);
        final boolean z2 = excludeClassChecks || excludeClassInStrategy(rawType, false);
        if (!z && !z2) {
            return null;
        }
        final Gson gson2 = gson;
        final TypeToken typeToken2 = typeToken;
        return new TypeAdapter() {
            private TypeAdapter delegate;

            public Object read(JsonReader jsonReader) {
                if (!z2) {
                    return delegate().read(jsonReader);
                }
                jsonReader.skipValue();
                return null;
            }

            public void write(JsonWriter jsonWriter, Object obj) {
                if (z) {
                    jsonWriter.nullValue();
                } else {
                    delegate().write(jsonWriter, obj);
                }
            }

            private TypeAdapter delegate() {
                TypeAdapter typeAdapter = this.delegate;
                if (typeAdapter != null) {
                    return typeAdapter;
                }
                TypeAdapter delegateAdapter = gson2.getDelegateAdapter(Excluder.this, typeToken2);
                this.delegate = delegateAdapter;
                return delegateAdapter;
            }
        };
    }

    public boolean excludeField(Field field, boolean z) {
        Expose expose;
        if ((this.modifiers & field.getModifiers()) != 0) {
            return true;
        }
        if ((this.version != -1.0d && !isValidVersion((Since) field.getAnnotation(Since.class), (Until) field.getAnnotation(Until.class))) || field.isSynthetic()) {
            return true;
        }
        if (this.requireExpose && ((expose = (Expose) field.getAnnotation(Expose.class)) == null || (!z ? !expose.deserialize() : !expose.serialize()))) {
            return true;
        }
        if ((!this.serializeInnerClasses && isInnerClass(field.getType())) || isAnonymousOrNonStaticLocal(field.getType())) {
            return true;
        }
        List list = z ? this.serializationStrategies : this.deserializationStrategies;
        if (list.isEmpty()) {
            return false;
        }
        new FieldAttributes(field);
        Iterator it = list.iterator();
        if (!it.hasNext()) {
            return false;
        }
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
        throw null;
    }

    private boolean excludeClassChecks(Class cls) {
        if (this.version != -1.0d && !isValidVersion((Since) cls.getAnnotation(Since.class), (Until) cls.getAnnotation(Until.class))) {
            return true;
        }
        if (this.serializeInnerClasses || !isInnerClass(cls)) {
            return isAnonymousOrNonStaticLocal(cls);
        }
        return true;
    }

    public boolean excludeClass(Class cls, boolean z) {
        return excludeClassChecks(cls) || excludeClassInStrategy(cls, z);
    }

    private boolean excludeClassInStrategy(Class cls, boolean z) {
        Iterator it = (z ? this.serializationStrategies : this.deserializationStrategies).iterator();
        if (!it.hasNext()) {
            return false;
        }
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
        throw null;
    }

    private boolean isAnonymousOrNonStaticLocal(Class cls) {
        if (Enum.class.isAssignableFrom(cls) || isStatic(cls)) {
            return false;
        }
        return cls.isAnonymousClass() || cls.isLocalClass();
    }

    private boolean isInnerClass(Class cls) {
        return cls.isMemberClass() && !isStatic(cls);
    }

    private boolean isStatic(Class cls) {
        return (cls.getModifiers() & 8) != 0;
    }

    private boolean isValidVersion(Since since, Until until) {
        return isValidSince(since) && isValidUntil(until);
    }

    private boolean isValidSince(Since since) {
        if (since == null) {
            return true;
        }
        if (this.version >= since.value()) {
            return true;
        }
        return false;
    }

    private boolean isValidUntil(Until until) {
        if (until == null) {
            return true;
        }
        if (this.version < until.value()) {
            return true;
        }
        return false;
    }
}
