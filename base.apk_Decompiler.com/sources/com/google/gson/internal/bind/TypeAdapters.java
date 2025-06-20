package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public abstract class TypeAdapters {
    public static final TypeAdapter ATOMIC_BOOLEAN;
    public static final TypeAdapterFactory ATOMIC_BOOLEAN_FACTORY;
    public static final TypeAdapter ATOMIC_INTEGER;
    public static final TypeAdapter ATOMIC_INTEGER_ARRAY;
    public static final TypeAdapterFactory ATOMIC_INTEGER_ARRAY_FACTORY;
    public static final TypeAdapterFactory ATOMIC_INTEGER_FACTORY;
    public static final TypeAdapter BIG_DECIMAL = new TypeAdapter() {
        public BigDecimal read(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            String nextString = jsonReader.nextString();
            try {
                return new BigDecimal(nextString);
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException("Failed parsing '" + nextString + "' as BigDecimal; at path " + jsonReader.getPreviousPath(), e);
            }
        }

        public void write(JsonWriter jsonWriter, BigDecimal bigDecimal) {
            jsonWriter.value((Number) bigDecimal);
        }
    };
    public static final TypeAdapter BIG_INTEGER = new TypeAdapter() {
        public BigInteger read(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            String nextString = jsonReader.nextString();
            try {
                return new BigInteger(nextString);
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException("Failed parsing '" + nextString + "' as BigInteger; at path " + jsonReader.getPreviousPath(), e);
            }
        }

        public void write(JsonWriter jsonWriter, BigInteger bigInteger) {
            jsonWriter.value((Number) bigInteger);
        }
    };
    public static final TypeAdapter BIT_SET;
    public static final TypeAdapterFactory BIT_SET_FACTORY;
    public static final TypeAdapter BOOLEAN;
    public static final TypeAdapter BOOLEAN_AS_STRING = new TypeAdapter() {
        public Boolean read(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return Boolean.valueOf(jsonReader.nextString());
            }
            jsonReader.nextNull();
            return null;
        }

        public void write(JsonWriter jsonWriter, Boolean bool) {
            jsonWriter.value(bool == null ? "null" : bool.toString());
        }
    };
    public static final TypeAdapterFactory BOOLEAN_FACTORY;
    public static final TypeAdapter BYTE;
    public static final TypeAdapterFactory BYTE_FACTORY;
    public static final TypeAdapter CALENDAR;
    public static final TypeAdapterFactory CALENDAR_FACTORY;
    public static final TypeAdapter CHARACTER;
    public static final TypeAdapterFactory CHARACTER_FACTORY;
    public static final TypeAdapter CLASS;
    public static final TypeAdapterFactory CLASS_FACTORY;
    public static final TypeAdapter CURRENCY;
    public static final TypeAdapterFactory CURRENCY_FACTORY;
    public static final TypeAdapter DOUBLE = new TypeAdapter() {
        public Number read(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return Double.valueOf(jsonReader.nextDouble());
            }
            jsonReader.nextNull();
            return null;
        }

        public void write(JsonWriter jsonWriter, Number number) {
            if (number == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(number.doubleValue());
            }
        }
    };
    public static final TypeAdapterFactory ENUM_FACTORY = new TypeAdapterFactory() {
        public TypeAdapter create(Gson gson, TypeToken typeToken) {
            Class<? super Enum> rawType = typeToken.getRawType();
            Class<Enum> cls = Enum.class;
            if (!cls.isAssignableFrom(rawType) || rawType == cls) {
                return null;
            }
            if (!rawType.isEnum()) {
                rawType = rawType.getSuperclass();
            }
            return new EnumTypeAdapter(rawType);
        }
    };
    public static final TypeAdapter FLOAT = new TypeAdapter() {
        public Number read(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return Float.valueOf((float) jsonReader.nextDouble());
            }
            jsonReader.nextNull();
            return null;
        }

        public void write(JsonWriter jsonWriter, Number number) {
            if (number == null) {
                jsonWriter.nullValue();
                return;
            }
            if (!(number instanceof Float)) {
                number = Float.valueOf(number.floatValue());
            }
            jsonWriter.value(number);
        }
    };
    public static final TypeAdapter INET_ADDRESS;
    public static final TypeAdapterFactory INET_ADDRESS_FACTORY;
    public static final TypeAdapter INTEGER;
    public static final TypeAdapterFactory INTEGER_FACTORY;
    public static final TypeAdapter JSON_ELEMENT;
    public static final TypeAdapterFactory JSON_ELEMENT_FACTORY;
    public static final TypeAdapter LAZILY_PARSED_NUMBER = new TypeAdapter() {
        public LazilyParsedNumber read(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return new LazilyParsedNumber(jsonReader.nextString());
            }
            jsonReader.nextNull();
            return null;
        }

        public void write(JsonWriter jsonWriter, LazilyParsedNumber lazilyParsedNumber) {
            jsonWriter.value((Number) lazilyParsedNumber);
        }
    };
    public static final TypeAdapter LOCALE;
    public static final TypeAdapterFactory LOCALE_FACTORY;
    public static final TypeAdapter LONG = new TypeAdapter() {
        public Number read(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            try {
                return Long.valueOf(jsonReader.nextLong());
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException((Throwable) e);
            }
        }

        public void write(JsonWriter jsonWriter, Number number) {
            if (number == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(number.longValue());
            }
        }
    };
    public static final TypeAdapter SHORT;
    public static final TypeAdapterFactory SHORT_FACTORY;
    public static final TypeAdapter STRING;
    public static final TypeAdapter STRING_BUFFER;
    public static final TypeAdapterFactory STRING_BUFFER_FACTORY;
    public static final TypeAdapter STRING_BUILDER;
    public static final TypeAdapterFactory STRING_BUILDER_FACTORY;
    public static final TypeAdapterFactory STRING_FACTORY;
    public static final TypeAdapter URI;
    public static final TypeAdapterFactory URI_FACTORY;
    public static final TypeAdapter URL;
    public static final TypeAdapterFactory URL_FACTORY;
    public static final TypeAdapter UUID;
    public static final TypeAdapterFactory UUID_FACTORY;

    static {
        TypeAdapter nullSafe = new TypeAdapter() {
            public void write(JsonWriter jsonWriter, Class cls) {
                throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + cls.getName() + ". Forgot to register a type adapter?");
            }

            public Class read(JsonReader jsonReader) {
                throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
            }
        }.nullSafe();
        CLASS = nullSafe;
        CLASS_FACTORY = newFactory(Class.class, nullSafe);
        TypeAdapter nullSafe2 = new TypeAdapter() {
            public BitSet read(JsonReader jsonReader) {
                BitSet bitSet = new BitSet();
                jsonReader.beginArray();
                JsonToken peek = jsonReader.peek();
                int i = 0;
                while (peek != JsonToken.END_ARRAY) {
                    int i2 = AnonymousClass35.$SwitchMap$com$google$gson$stream$JsonToken[peek.ordinal()];
                    boolean z = true;
                    if (i2 == 1 || i2 == 2) {
                        int nextInt = jsonReader.nextInt();
                        if (nextInt == 0) {
                            z = false;
                        } else if (nextInt != 1) {
                            throw new JsonSyntaxException("Invalid bitset value " + nextInt + ", expected 0 or 1; at path " + jsonReader.getPreviousPath());
                        }
                    } else if (i2 == 3) {
                        z = jsonReader.nextBoolean();
                    } else {
                        throw new JsonSyntaxException("Invalid bitset value type: " + peek + "; at path " + jsonReader.getPath());
                    }
                    if (z) {
                        bitSet.set(i);
                    }
                    i++;
                    peek = jsonReader.peek();
                }
                jsonReader.endArray();
                return bitSet;
            }

            public void write(JsonWriter jsonWriter, BitSet bitSet) {
                jsonWriter.beginArray();
                int length = bitSet.length();
                for (int i = 0; i < length; i++) {
                    jsonWriter.value(bitSet.get(i) ? 1 : 0);
                }
                jsonWriter.endArray();
            }
        }.nullSafe();
        BIT_SET = nullSafe2;
        BIT_SET_FACTORY = newFactory(BitSet.class, nullSafe2);
        AnonymousClass3 r0 = new TypeAdapter() {
            public Boolean read(JsonReader jsonReader) {
                JsonToken peek = jsonReader.peek();
                if (peek == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                } else if (peek == JsonToken.STRING) {
                    return Boolean.valueOf(Boolean.parseBoolean(jsonReader.nextString()));
                } else {
                    return Boolean.valueOf(jsonReader.nextBoolean());
                }
            }

            public void write(JsonWriter jsonWriter, Boolean bool) {
                jsonWriter.value(bool);
            }
        };
        BOOLEAN = r0;
        BOOLEAN_FACTORY = newFactory(Boolean.TYPE, Boolean.class, r0);
        AnonymousClass5 r02 = new TypeAdapter() {
            public Number read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    int nextInt = jsonReader.nextInt();
                    if (nextInt <= 255 && nextInt >= -128) {
                        return Byte.valueOf((byte) nextInt);
                    }
                    throw new JsonSyntaxException("Lossy conversion from " + nextInt + " to byte; at path " + jsonReader.getPreviousPath());
                } catch (NumberFormatException e) {
                    throw new JsonSyntaxException((Throwable) e);
                }
            }

            public void write(JsonWriter jsonWriter, Number number) {
                if (number == null) {
                    jsonWriter.nullValue();
                } else {
                    jsonWriter.value((long) number.byteValue());
                }
            }
        };
        BYTE = r02;
        BYTE_FACTORY = newFactory(Byte.TYPE, Byte.class, r02);
        AnonymousClass6 r03 = new TypeAdapter() {
            public Number read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    int nextInt = jsonReader.nextInt();
                    if (nextInt <= 65535 && nextInt >= -32768) {
                        return Short.valueOf((short) nextInt);
                    }
                    throw new JsonSyntaxException("Lossy conversion from " + nextInt + " to short; at path " + jsonReader.getPreviousPath());
                } catch (NumberFormatException e) {
                    throw new JsonSyntaxException((Throwable) e);
                }
            }

            public void write(JsonWriter jsonWriter, Number number) {
                if (number == null) {
                    jsonWriter.nullValue();
                } else {
                    jsonWriter.value((long) number.shortValue());
                }
            }
        };
        SHORT = r03;
        SHORT_FACTORY = newFactory(Short.TYPE, Short.class, r03);
        AnonymousClass7 r04 = new TypeAdapter() {
            public Number read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    return Integer.valueOf(jsonReader.nextInt());
                } catch (NumberFormatException e) {
                    throw new JsonSyntaxException((Throwable) e);
                }
            }

            public void write(JsonWriter jsonWriter, Number number) {
                if (number == null) {
                    jsonWriter.nullValue();
                } else {
                    jsonWriter.value((long) number.intValue());
                }
            }
        };
        INTEGER = r04;
        INTEGER_FACTORY = newFactory(Integer.TYPE, Integer.class, r04);
        TypeAdapter nullSafe3 = new TypeAdapter() {
            public AtomicInteger read(JsonReader jsonReader) {
                try {
                    return new AtomicInteger(jsonReader.nextInt());
                } catch (NumberFormatException e) {
                    throw new JsonSyntaxException((Throwable) e);
                }
            }

            public void write(JsonWriter jsonWriter, AtomicInteger atomicInteger) {
                jsonWriter.value((long) atomicInteger.get());
            }
        }.nullSafe();
        ATOMIC_INTEGER = nullSafe3;
        ATOMIC_INTEGER_FACTORY = newFactory(AtomicInteger.class, nullSafe3);
        TypeAdapter nullSafe4 = new TypeAdapter() {
            public AtomicBoolean read(JsonReader jsonReader) {
                return new AtomicBoolean(jsonReader.nextBoolean());
            }

            public void write(JsonWriter jsonWriter, AtomicBoolean atomicBoolean) {
                jsonWriter.value(atomicBoolean.get());
            }
        }.nullSafe();
        ATOMIC_BOOLEAN = nullSafe4;
        ATOMIC_BOOLEAN_FACTORY = newFactory(AtomicBoolean.class, nullSafe4);
        TypeAdapter nullSafe5 = new TypeAdapter() {
            public AtomicIntegerArray read(JsonReader jsonReader) {
                ArrayList arrayList = new ArrayList();
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    try {
                        arrayList.add(Integer.valueOf(jsonReader.nextInt()));
                    } catch (NumberFormatException e) {
                        throw new JsonSyntaxException((Throwable) e);
                    }
                }
                jsonReader.endArray();
                int size = arrayList.size();
                AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(size);
                for (int i = 0; i < size; i++) {
                    atomicIntegerArray.set(i, ((Integer) arrayList.get(i)).intValue());
                }
                return atomicIntegerArray;
            }

            public void write(JsonWriter jsonWriter, AtomicIntegerArray atomicIntegerArray) {
                jsonWriter.beginArray();
                int length = atomicIntegerArray.length();
                for (int i = 0; i < length; i++) {
                    jsonWriter.value((long) atomicIntegerArray.get(i));
                }
                jsonWriter.endArray();
            }
        }.nullSafe();
        ATOMIC_INTEGER_ARRAY = nullSafe5;
        ATOMIC_INTEGER_ARRAY_FACTORY = newFactory(AtomicIntegerArray.class, nullSafe5);
        AnonymousClass14 r05 = new TypeAdapter() {
            public Character read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                String nextString = jsonReader.nextString();
                if (nextString.length() == 1) {
                    return Character.valueOf(nextString.charAt(0));
                }
                throw new JsonSyntaxException("Expecting character, got: " + nextString + "; at " + jsonReader.getPreviousPath());
            }

            public void write(JsonWriter jsonWriter, Character ch) {
                jsonWriter.value(ch == null ? null : String.valueOf(ch));
            }
        };
        CHARACTER = r05;
        CHARACTER_FACTORY = newFactory(Character.TYPE, Character.class, r05);
        AnonymousClass15 r06 = new TypeAdapter() {
            public String read(JsonReader jsonReader) {
                JsonToken peek = jsonReader.peek();
                if (peek == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                } else if (peek == JsonToken.BOOLEAN) {
                    return Boolean.toString(jsonReader.nextBoolean());
                } else {
                    return jsonReader.nextString();
                }
            }

            public void write(JsonWriter jsonWriter, String str) {
                jsonWriter.value(str);
            }
        };
        STRING = r06;
        STRING_FACTORY = newFactory(String.class, r06);
        AnonymousClass19 r07 = new TypeAdapter() {
            public StringBuilder read(JsonReader jsonReader) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    return new StringBuilder(jsonReader.nextString());
                }
                jsonReader.nextNull();
                return null;
            }

            public void write(JsonWriter jsonWriter, StringBuilder sb) {
                jsonWriter.value(sb == null ? null : sb.toString());
            }
        };
        STRING_BUILDER = r07;
        STRING_BUILDER_FACTORY = newFactory(StringBuilder.class, r07);
        AnonymousClass20 r08 = new TypeAdapter() {
            public StringBuffer read(JsonReader jsonReader) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    return new StringBuffer(jsonReader.nextString());
                }
                jsonReader.nextNull();
                return null;
            }

            public void write(JsonWriter jsonWriter, StringBuffer stringBuffer) {
                jsonWriter.value(stringBuffer == null ? null : stringBuffer.toString());
            }
        };
        STRING_BUFFER = r08;
        STRING_BUFFER_FACTORY = newFactory(StringBuffer.class, r08);
        AnonymousClass21 r09 = new TypeAdapter() {
            public URL read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                String nextString = jsonReader.nextString();
                if ("null".equals(nextString)) {
                    return null;
                }
                return new URL(nextString);
            }

            public void write(JsonWriter jsonWriter, URL url) {
                jsonWriter.value(url == null ? null : url.toExternalForm());
            }
        };
        URL = r09;
        URL_FACTORY = newFactory(URL.class, r09);
        AnonymousClass22 r010 = new TypeAdapter() {
            public URI read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    String nextString = jsonReader.nextString();
                    if ("null".equals(nextString)) {
                        return null;
                    }
                    return new URI(nextString);
                } catch (URISyntaxException e) {
                    throw new JsonIOException((Throwable) e);
                }
            }

            public void write(JsonWriter jsonWriter, URI uri) {
                jsonWriter.value(uri == null ? null : uri.toASCIIString());
            }
        };
        URI = r010;
        URI_FACTORY = newFactory(URI.class, r010);
        AnonymousClass23 r011 = new TypeAdapter() {
            public InetAddress read(JsonReader jsonReader) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    return InetAddress.getByName(jsonReader.nextString());
                }
                jsonReader.nextNull();
                return null;
            }

            public void write(JsonWriter jsonWriter, InetAddress inetAddress) {
                jsonWriter.value(inetAddress == null ? null : inetAddress.getHostAddress());
            }
        };
        INET_ADDRESS = r011;
        INET_ADDRESS_FACTORY = newTypeHierarchyFactory(InetAddress.class, r011);
        AnonymousClass24 r012 = new TypeAdapter() {
            public UUID read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                String nextString = jsonReader.nextString();
                try {
                    return UUID.fromString(nextString);
                } catch (IllegalArgumentException e) {
                    throw new JsonSyntaxException("Failed parsing '" + nextString + "' as UUID; at path " + jsonReader.getPreviousPath(), e);
                }
            }

            public void write(JsonWriter jsonWriter, UUID uuid) {
                jsonWriter.value(uuid == null ? null : uuid.toString());
            }
        };
        UUID = r012;
        UUID_FACTORY = newFactory(UUID.class, r012);
        TypeAdapter nullSafe6 = new TypeAdapter() {
            public Currency read(JsonReader jsonReader) {
                String nextString = jsonReader.nextString();
                try {
                    return Currency.getInstance(nextString);
                } catch (IllegalArgumentException e) {
                    throw new JsonSyntaxException("Failed parsing '" + nextString + "' as Currency; at path " + jsonReader.getPreviousPath(), e);
                }
            }

            public void write(JsonWriter jsonWriter, Currency currency) {
                jsonWriter.value(currency.getCurrencyCode());
            }
        }.nullSafe();
        CURRENCY = nullSafe6;
        CURRENCY_FACTORY = newFactory(Currency.class, nullSafe6);
        AnonymousClass26 r013 = new TypeAdapter() {
            public Calendar read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                jsonReader.beginObject();
                int i = 0;
                int i2 = 0;
                int i3 = 0;
                int i4 = 0;
                int i5 = 0;
                int i6 = 0;
                while (jsonReader.peek() != JsonToken.END_OBJECT) {
                    String nextName = jsonReader.nextName();
                    int nextInt = jsonReader.nextInt();
                    if ("year".equals(nextName)) {
                        i = nextInt;
                    } else if ("month".equals(nextName)) {
                        i2 = nextInt;
                    } else if ("dayOfMonth".equals(nextName)) {
                        i3 = nextInt;
                    } else if ("hourOfDay".equals(nextName)) {
                        i4 = nextInt;
                    } else if ("minute".equals(nextName)) {
                        i5 = nextInt;
                    } else if ("second".equals(nextName)) {
                        i6 = nextInt;
                    }
                }
                jsonReader.endObject();
                return new GregorianCalendar(i, i2, i3, i4, i5, i6);
            }

            public void write(JsonWriter jsonWriter, Calendar calendar) {
                if (calendar == null) {
                    jsonWriter.nullValue();
                    return;
                }
                jsonWriter.beginObject();
                jsonWriter.name("year");
                jsonWriter.value((long) calendar.get(1));
                jsonWriter.name("month");
                jsonWriter.value((long) calendar.get(2));
                jsonWriter.name("dayOfMonth");
                jsonWriter.value((long) calendar.get(5));
                jsonWriter.name("hourOfDay");
                jsonWriter.value((long) calendar.get(11));
                jsonWriter.name("minute");
                jsonWriter.value((long) calendar.get(12));
                jsonWriter.name("second");
                jsonWriter.value((long) calendar.get(13));
                jsonWriter.endObject();
            }
        };
        CALENDAR = r013;
        CALENDAR_FACTORY = newFactoryForMultipleTypes(Calendar.class, GregorianCalendar.class, r013);
        AnonymousClass27 r014 = new TypeAdapter() {
            public Locale read(JsonReader jsonReader) {
                String str = null;
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                StringTokenizer stringTokenizer = new StringTokenizer(jsonReader.nextString(), "_");
                String nextToken = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
                String nextToken2 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
                if (stringTokenizer.hasMoreElements()) {
                    str = stringTokenizer.nextToken();
                }
                if (nextToken2 == null && str == null) {
                    return new Locale(nextToken);
                }
                if (str == null) {
                    return new Locale(nextToken, nextToken2);
                }
                return new Locale(nextToken, nextToken2, str);
            }

            public void write(JsonWriter jsonWriter, Locale locale) {
                jsonWriter.value(locale == null ? null : locale.toString());
            }
        };
        LOCALE = r014;
        LOCALE_FACTORY = newFactory(Locale.class, r014);
        AnonymousClass28 r015 = new TypeAdapter() {
            private JsonElement tryBeginNesting(JsonReader jsonReader, JsonToken jsonToken) {
                int i = AnonymousClass35.$SwitchMap$com$google$gson$stream$JsonToken[jsonToken.ordinal()];
                if (i == 4) {
                    jsonReader.beginArray();
                    return new JsonArray();
                } else if (i != 5) {
                    return null;
                } else {
                    jsonReader.beginObject();
                    return new JsonObject();
                }
            }

            private JsonElement readTerminal(JsonReader jsonReader, JsonToken jsonToken) {
                int i = AnonymousClass35.$SwitchMap$com$google$gson$stream$JsonToken[jsonToken.ordinal()];
                if (i == 1) {
                    return new JsonPrimitive((Number) new LazilyParsedNumber(jsonReader.nextString()));
                }
                if (i == 2) {
                    return new JsonPrimitive(jsonReader.nextString());
                }
                if (i == 3) {
                    return new JsonPrimitive(Boolean.valueOf(jsonReader.nextBoolean()));
                }
                if (i == 6) {
                    jsonReader.nextNull();
                    return JsonNull.INSTANCE;
                }
                throw new IllegalStateException("Unexpected token: " + jsonToken);
            }

            public JsonElement read(JsonReader jsonReader) {
                JsonToken peek = jsonReader.peek();
                JsonElement tryBeginNesting = tryBeginNesting(jsonReader, peek);
                if (tryBeginNesting == null) {
                    return readTerminal(jsonReader, peek);
                }
                ArrayDeque arrayDeque = new ArrayDeque();
                while (true) {
                    if (jsonReader.hasNext()) {
                        String nextName = tryBeginNesting instanceof JsonObject ? jsonReader.nextName() : null;
                        JsonToken peek2 = jsonReader.peek();
                        JsonElement tryBeginNesting2 = tryBeginNesting(jsonReader, peek2);
                        boolean z = tryBeginNesting2 != null;
                        if (tryBeginNesting2 == null) {
                            tryBeginNesting2 = readTerminal(jsonReader, peek2);
                        }
                        if (tryBeginNesting instanceof JsonArray) {
                            ((JsonArray) tryBeginNesting).add(tryBeginNesting2);
                        } else {
                            ((JsonObject) tryBeginNesting).add(nextName, tryBeginNesting2);
                        }
                        if (z) {
                            arrayDeque.addLast(tryBeginNesting);
                            tryBeginNesting = tryBeginNesting2;
                        }
                    } else {
                        if (tryBeginNesting instanceof JsonArray) {
                            jsonReader.endArray();
                        } else {
                            jsonReader.endObject();
                        }
                        if (arrayDeque.isEmpty()) {
                            return tryBeginNesting;
                        }
                        tryBeginNesting = (JsonElement) arrayDeque.removeLast();
                    }
                }
            }

            public void write(JsonWriter jsonWriter, JsonElement jsonElement) {
                if (jsonElement == null || jsonElement.isJsonNull()) {
                    jsonWriter.nullValue();
                } else if (jsonElement.isJsonPrimitive()) {
                    JsonPrimitive asJsonPrimitive = jsonElement.getAsJsonPrimitive();
                    if (asJsonPrimitive.isNumber()) {
                        jsonWriter.value(asJsonPrimitive.getAsNumber());
                    } else if (asJsonPrimitive.isBoolean()) {
                        jsonWriter.value(asJsonPrimitive.getAsBoolean());
                    } else {
                        jsonWriter.value(asJsonPrimitive.getAsString());
                    }
                } else if (jsonElement.isJsonArray()) {
                    jsonWriter.beginArray();
                    Iterator it = jsonElement.getAsJsonArray().iterator();
                    while (it.hasNext()) {
                        write(jsonWriter, (JsonElement) it.next());
                    }
                    jsonWriter.endArray();
                } else if (jsonElement.isJsonObject()) {
                    jsonWriter.beginObject();
                    for (Map.Entry entry : jsonElement.getAsJsonObject().entrySet()) {
                        jsonWriter.name((String) entry.getKey());
                        write(jsonWriter, (JsonElement) entry.getValue());
                    }
                    jsonWriter.endObject();
                } else {
                    throw new IllegalArgumentException("Couldn't write " + jsonElement.getClass());
                }
            }
        };
        JSON_ELEMENT = r015;
        JSON_ELEMENT_FACTORY = newTypeHierarchyFactory(JsonElement.class, r015);
    }

    /* renamed from: com.google.gson.internal.bind.TypeAdapters$35  reason: invalid class name */
    abstract /* synthetic */ class AnonymousClass35 {
        static final /* synthetic */ int[] $SwitchMap$com$google$gson$stream$JsonToken;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|14) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.google.gson.stream.JsonToken[] r0 = com.google.gson.stream.JsonToken.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$google$gson$stream$JsonToken = r0
                com.google.gson.stream.JsonToken r1 = com.google.gson.stream.JsonToken.NUMBER     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$google$gson$stream$JsonToken     // Catch:{ NoSuchFieldError -> 0x001d }
                com.google.gson.stream.JsonToken r1 = com.google.gson.stream.JsonToken.STRING     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$google$gson$stream$JsonToken     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.google.gson.stream.JsonToken r1 = com.google.gson.stream.JsonToken.BOOLEAN     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$google$gson$stream$JsonToken     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.google.gson.stream.JsonToken r1 = com.google.gson.stream.JsonToken.BEGIN_ARRAY     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$google$gson$stream$JsonToken     // Catch:{ NoSuchFieldError -> 0x003e }
                com.google.gson.stream.JsonToken r1 = com.google.gson.stream.JsonToken.BEGIN_OBJECT     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$com$google$gson$stream$JsonToken     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.google.gson.stream.JsonToken r1 = com.google.gson.stream.JsonToken.NULL     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.TypeAdapters.AnonymousClass35.<clinit>():void");
        }
    }

    final class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter {
        private final Map constantToName = new HashMap();
        private final Map nameToConstant = new HashMap();
        private final Map stringToConstant = new HashMap();

        public EnumTypeAdapter(final Class cls) {
            try {
                for (Field field : (Field[]) AccessController.doPrivileged(new PrivilegedAction() {
                    public Field[] run() {
                        Field[] declaredFields = cls.getDeclaredFields();
                        ArrayList arrayList = new ArrayList(declaredFields.length);
                        for (Field field : declaredFields) {
                            if (field.isEnumConstant()) {
                                arrayList.add(field);
                            }
                        }
                        Field[] fieldArr = (Field[]) arrayList.toArray(new Field[0]);
                        AccessibleObject.setAccessible(fieldArr, true);
                        return fieldArr;
                    }
                })) {
                    Enum enumR = (Enum) field.get((Object) null);
                    String name = enumR.name();
                    String obj = enumR.toString();
                    SerializedName serializedName = (SerializedName) field.getAnnotation(SerializedName.class);
                    if (serializedName != null) {
                        name = serializedName.value();
                        for (String put : serializedName.alternate()) {
                            this.nameToConstant.put(put, enumR);
                        }
                    }
                    this.nameToConstant.put(name, enumR);
                    this.stringToConstant.put(obj, enumR);
                    this.constantToName.put(enumR, name);
                }
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }

        public Enum read(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            String nextString = jsonReader.nextString();
            Enum enumR = (Enum) this.nameToConstant.get(nextString);
            return enumR == null ? (Enum) this.stringToConstant.get(nextString) : enumR;
        }

        public void write(JsonWriter jsonWriter, Enum enumR) {
            jsonWriter.value(enumR == null ? null : (String) this.constantToName.get(enumR));
        }
    }

    /* renamed from: com.google.gson.internal.bind.TypeAdapters$30  reason: invalid class name */
    class AnonymousClass30 implements TypeAdapterFactory {
        final /* synthetic */ TypeToken val$type;
        final /* synthetic */ TypeAdapter val$typeAdapter;

        public TypeAdapter create(Gson gson, TypeToken typeToken) {
            if (typeToken.equals(this.val$type)) {
                return this.val$typeAdapter;
            }
            return null;
        }
    }

    public static TypeAdapterFactory newFactory(final Class cls, final TypeAdapter typeAdapter) {
        return new TypeAdapterFactory() {
            public TypeAdapter create(Gson gson, TypeToken typeToken) {
                if (typeToken.getRawType() == cls) {
                    return typeAdapter;
                }
                return null;
            }

            public String toString() {
                return "Factory[type=" + cls.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }

    public static TypeAdapterFactory newFactory(final Class cls, final Class cls2, final TypeAdapter typeAdapter) {
        return new TypeAdapterFactory() {
            public TypeAdapter create(Gson gson, TypeToken typeToken) {
                Class rawType = typeToken.getRawType();
                if (rawType == cls || rawType == cls2) {
                    return typeAdapter;
                }
                return null;
            }

            public String toString() {
                return "Factory[type=" + cls2.getName() + "+" + cls.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }

    public static TypeAdapterFactory newFactoryForMultipleTypes(final Class cls, final Class cls2, final TypeAdapter typeAdapter) {
        return new TypeAdapterFactory() {
            public TypeAdapter create(Gson gson, TypeToken typeToken) {
                Class rawType = typeToken.getRawType();
                if (rawType == cls || rawType == cls2) {
                    return typeAdapter;
                }
                return null;
            }

            public String toString() {
                return "Factory[type=" + cls.getName() + "+" + cls2.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }

    public static TypeAdapterFactory newTypeHierarchyFactory(final Class cls, final TypeAdapter typeAdapter) {
        return new TypeAdapterFactory() {
            public TypeAdapter create(Gson gson, TypeToken typeToken) {
                final Class rawType = typeToken.getRawType();
                if (!cls.isAssignableFrom(rawType)) {
                    return null;
                }
                return new TypeAdapter() {
                    public void write(JsonWriter jsonWriter, Object obj) {
                        typeAdapter.write(jsonWriter, obj);
                    }

                    public Object read(JsonReader jsonReader) {
                        Object read = typeAdapter.read(jsonReader);
                        if (read == null || rawType.isInstance(read)) {
                            return read;
                        }
                        throw new JsonSyntaxException("Expected a " + rawType.getName() + " but was " + read.getClass().getName() + "; at path " + jsonReader.getPreviousPath());
                    }
                };
            }

            public String toString() {
                return "Factory[typeHierarchy=" + cls.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }
}
