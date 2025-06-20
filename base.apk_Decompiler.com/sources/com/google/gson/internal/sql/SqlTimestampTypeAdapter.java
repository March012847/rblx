package com.google.gson.internal.sql;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.sql.Timestamp;
import java.util.Date;

class SqlTimestampTypeAdapter extends TypeAdapter {
    static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        public TypeAdapter create(Gson gson, TypeToken typeToken) {
            if (typeToken.getRawType() == Timestamp.class) {
                return new SqlTimestampTypeAdapter(gson.getAdapter(Date.class));
            }
            return null;
        }
    };
    private final TypeAdapter dateTypeAdapter;

    private SqlTimestampTypeAdapter(TypeAdapter typeAdapter) {
        this.dateTypeAdapter = typeAdapter;
    }

    public Timestamp read(JsonReader jsonReader) {
        Date date = (Date) this.dateTypeAdapter.read(jsonReader);
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }

    public void write(JsonWriter jsonWriter, Timestamp timestamp) {
        this.dateTypeAdapter.write(jsonWriter, timestamp);
    }
}
