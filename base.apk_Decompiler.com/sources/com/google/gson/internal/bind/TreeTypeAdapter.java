package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public final class TreeTypeAdapter<T> extends SerializationDelegatingTypeAdapter<T> {
    private final GsonContextImpl context;
    private volatile TypeAdapter delegate;
    final Gson gson;
    private final boolean nullSafe;
    private final TypeAdapterFactory skipPast;
    private final TypeToken typeToken;

    public TreeTypeAdapter(JsonSerializer jsonSerializer, JsonDeserializer jsonDeserializer, Gson gson2, TypeToken typeToken2, TypeAdapterFactory typeAdapterFactory, boolean z) {
        this.context = new GsonContextImpl();
        this.gson = gson2;
        this.typeToken = typeToken2;
        this.skipPast = typeAdapterFactory;
        this.nullSafe = z;
    }

    public TreeTypeAdapter(JsonSerializer jsonSerializer, JsonDeserializer jsonDeserializer, Gson gson2, TypeToken typeToken2, TypeAdapterFactory typeAdapterFactory) {
        this(jsonSerializer, jsonDeserializer, gson2, typeToken2, typeAdapterFactory, true);
    }

    public Object read(JsonReader jsonReader) {
        return delegate().read(jsonReader);
    }

    public void write(JsonWriter jsonWriter, Object obj) {
        delegate().write(jsonWriter, obj);
    }

    private TypeAdapter delegate() {
        TypeAdapter typeAdapter = this.delegate;
        if (typeAdapter != null) {
            return typeAdapter;
        }
        TypeAdapter delegateAdapter = this.gson.getDelegateAdapter(this.skipPast, this.typeToken);
        this.delegate = delegateAdapter;
        return delegateAdapter;
    }

    public TypeAdapter getSerializationDelegate() {
        return delegate();
    }

    final class SingleTypeFactory implements TypeAdapterFactory {
        private final TypeToken exactType;
        private final Class hierarchyType;
        private final boolean matchRawType;

        public TypeAdapter create(Gson gson, TypeToken typeToken) {
            boolean z;
            TypeToken typeToken2 = this.exactType;
            if (typeToken2 != null) {
                z = typeToken2.equals(typeToken) || (this.matchRawType && this.exactType.getType() == typeToken.getRawType());
            } else {
                z = this.hierarchyType.isAssignableFrom(typeToken.getRawType());
            }
            if (!z) {
                return null;
            }
            return new TreeTypeAdapter((JsonSerializer) null, (JsonDeserializer) null, gson, typeToken, this);
        }
    }

    final class GsonContextImpl {
        private GsonContextImpl() {
        }
    }
}
