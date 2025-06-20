package com.google.gson.internal.bind;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public final class DefaultDateTypeAdapter<T extends Date> extends TypeAdapter {
    private final List dateFormats;
    private final DateType dateType;

    public abstract class DateType {
        public static final DateType DATE = new DateType(Date.class) {
            /* access modifiers changed from: protected */
            public Date deserialize(Date date) {
                return date;
            }
        };
        private final Class dateClass;

        /* access modifiers changed from: protected */
        public abstract Date deserialize(Date date);

        protected DateType(Class cls) {
            this.dateClass = cls;
        }
    }

    public void write(JsonWriter jsonWriter, Date date) {
        String format;
        if (date == null) {
            jsonWriter.nullValue();
            return;
        }
        DateFormat dateFormat = (DateFormat) this.dateFormats.get(0);
        synchronized (this.dateFormats) {
            format = dateFormat.format(date);
        }
        jsonWriter.value(format);
    }

    public Date read(JsonReader jsonReader) {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        return this.dateType.deserialize(deserializeToDate(jsonReader));
    }

    private Date deserializeToDate(JsonReader jsonReader) {
        String nextString = jsonReader.nextString();
        synchronized (this.dateFormats) {
            try {
                for (DateFormat parse : this.dateFormats) {
                    try {
                        Date parse2 = parse.parse(nextString);
                        return parse2;
                    } catch (ParseException unused) {
                    }
                }
                try {
                    return ISO8601Utils.parse(nextString, new ParsePosition(0));
                } catch (ParseException e) {
                    throw new JsonSyntaxException("Failed parsing '" + nextString + "' as Date; at path " + jsonReader.getPreviousPath(), e);
                }
            } catch (Throwable th) {
                while (true) {
                    throw th;
                }
            }
        }
    }

    public String toString() {
        DateFormat dateFormat = (DateFormat) this.dateFormats.get(0);
        if (dateFormat instanceof SimpleDateFormat) {
            return "DefaultDateTypeAdapter(" + ((SimpleDateFormat) dateFormat).toPattern() + ')';
        }
        return "DefaultDateTypeAdapter(" + dateFormat.getClass().getSimpleName() + ')';
    }
}
