package com.github.xxscloud5722.gson;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Cat.
 * Gson JsonObject Rewrite.
 */
@SuppressWarnings("ALL")
public final class JsonObject extends HashMap<String, Object> {
    private static final Pattern Z = Pattern.compile("^[-\\+]?[\\d]*$");
    private static final Type TYPE = new TypeToken<JsonElement>() {
    }.getType();

    private JsonObject() {

    }

    @NotNull
    @Contract(" -> new")
    public static JsonObject init() {
        return new JsonObject();
    }

    @NotNull
    public static JsonObject init(final JsonElement jsonElement) {
        final JsonObject data = new JsonObject();
        ((com.google.gson.JsonObject) jsonElement).entrySet().forEach(it -> {
            final JsonPrimitive jsonPrimitive = (JsonPrimitive) it.getValue();
            if (it.getValue() == null) {
                return;
            }
            if (jsonPrimitive.isString()) {
                data.put(it.getKey(), jsonPrimitive.getAsString());
                return;
            }
            if (jsonPrimitive.isJsonNull()) {
                return;
            }
            if (jsonPrimitive.isBoolean()) {
                data.put(it.getKey(), jsonPrimitive.getAsBoolean());
                return;
            }
            if (jsonPrimitive.isNumber()) {
                data.put(it.getKey(), jsonPrimitive.getAsNumber());
                return;
            }
            data.put(it.getKey(), it.getValue());
        });
        return data;
    }

    @NotNull
    public static JsonObject init(final Map<Object, Object> map) {
        final JsonObject data = new JsonObject();
        ((com.google.gson.JsonObject) new Gson().toJsonTree(map)).entrySet().forEach(it -> {
            if (it.getValue() == null) {
                return;
            }
            final JsonPrimitive jsonPrimitive = (JsonPrimitive) it.getValue();
            if (jsonPrimitive.isJsonNull()) {
                return;
            }
            if (jsonPrimitive.isString()) {
                data.put(it.getKey(), jsonPrimitive.getAsString());
                return;
            }
            if (jsonPrimitive.isBoolean()) {
                data.put(it.getKey(), jsonPrimitive.getAsBoolean());
                return;
            }
            if (jsonPrimitive.isNumber()) {
                data.put(it.getKey(), jsonPrimitive.getAsNumber());
                return;
            }
            data.put(it.getKey(), it.getValue());
        });
        return data;
    }

    @NotNull
    public static JsonObject init(final String json) {
        final JsonObject data = new JsonObject();
        ((com.google.gson.JsonObject) JsonUtils.parseObject(json, TYPE)).entrySet().forEach(it -> {
            if (it.getValue() == null) {
                return;
            }
            final JsonPrimitive jsonPrimitive = (JsonPrimitive) it.getValue();
            if (jsonPrimitive.isJsonNull()) {
                return;
            }
            if (jsonPrimitive.isString()) {
                data.put(it.getKey(), jsonPrimitive.getAsString());
                return;
            }
            if (jsonPrimitive.isBoolean()) {
                data.put(it.getKey(), jsonPrimitive.getAsBoolean());
                return;
            }
            if (jsonPrimitive.isNumber()) {
                data.put(it.getKey(), jsonPrimitive.getAsNumber());
                return;
            }
            data.put(it.getKey(), it.getValue());
        });
        return data;
    }

    @Override
    public Object get(Object key) {
        return super.get(key);
    }

    @Override
    public String toString() {
        return JsonUtils.stringify(this);
    }


    @Nullable
    public com.github.xxscloud5722.gson.JsonArray getJsonArray(String memberName) {
        final JsonElement element = this.getElement(memberName);
        if (element == null) {
            return null;
        }
        return new JsonArray(element.getAsJsonArray());
    }

    @Nullable
    public JsonObject getJsonObject(String memberName) {
        final JsonElement element = this.getElement(memberName);
        if (element == null) {
            return null;
        }
        return JsonObject.init(element.getAsJsonObject());
    }


    @Nullable
    public JsonElement getElement(Object key) {
        final Object obj = super.get(key);
        if (obj == null) {
            return null;
        }
        if (obj instanceof JsonElement) {
            return (JsonElement) obj;
        }
        if (obj instanceof String) {
            return new JsonPrimitive((String) obj);
        }
        if (obj instanceof Number) {
            return new JsonPrimitive((Number) obj);
        }
        if (obj instanceof Boolean) {
            return new JsonPrimitive((Boolean) obj);
        }
        if (obj instanceof Character) {
            return new JsonPrimitive((Character) obj);
        }
        return new Gson().toJsonTree(obj);
    }

    @Nullable
    public Number getNumber(String i) {
        final JsonElement element = this.getElement(i);
        if (element == null) {
            return null;
        }
        return element.getAsNumber();
    }

    @Nullable
    public String getString(String i) {
        final JsonElement element = this.getElement(i);
        if (element == null) {
            return null;
        }
        return element.getAsString();
    }

    @Nullable
    public Double getDouble(String i) {
        final JsonElement element = this.getElement(i);
        if (element == null) {
            return null;
        }
        return element.getAsDouble();
    }

    @Nullable
    public BigDecimal getBigDecimal(String i) {
        final JsonElement element = this.getElement(i);
        if (element == null) {
            return null;
        }
        return element.getAsBigDecimal();
    }

    @Nullable
    public BigInteger getBigInteger(String i) {
        final JsonElement element = this.getElement(i);
        if (element == null) {
            return null;
        }
        return element.getAsBigInteger();
    }

    @NotNull
    public Float getFloat(String i) {
        final JsonElement element = this.getElement(i);
        if (element == null) {
            throw new NullPointerException();
        }
        return element.getAsFloat();
    }

    @Nullable
    public Long getLong(String i) {
        final JsonElement element = this.getElement(i);
        if (element == null) {
            return null;
        }
        return element.getAsLong();
    }

    @Nullable
    public Integer getInteger(String i) {
        final JsonElement element = this.getElement(i);
        if (element == null) {
            return null;
        }
        return element.getAsInt();
    }

    public byte getByte(String i) {
        final JsonElement element = this.getElement(i);
        if (element == null) {
            throw new NullPointerException();
        }
        return element.getAsByte();
    }

    public char getCharacter(String i) {
        final JsonElement element = this.getElement(i);
        if (element == null) {
            throw new NullPointerException();
        }
        return element.getAsCharacter();
    }

    public short getShort(String i) {
        final JsonElement element = this.getElement(i);
        if (element == null) {
            throw new NullPointerException();
        }
        return element.getAsShort();
    }

    @Nullable
    public Boolean getBoolean(String i) {
        final JsonElement element = this.getElement(i);
        if (element == null) {
            return null;
        }
        return element.getAsBoolean();
    }

    @Nullable
    public Date getDate(String i) {
        final JsonElement element = this.getElement(i);
        if (element == null) {
            return null;
        }
        final String content = element.getAsString();
        //is Number
        if (Z.matcher(content).matches()) {
            return new Date(Long.parseLong(content));
        }
        //String
        try {
            final String z = "yyyy-MM-dd HH:mm:ss";
            if (z.length() >= content.length()) {
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(z.substring(0, content.length()));
                return simpleDateFormat.parse(content);
            }
            throw new JsonParseException(i + " to Date");
        } catch (ParseException e) {
            e.printStackTrace();
            throw new JsonParseException(i + " to Date");
        }
    }

    @Nullable
    public Date getDate(String i, String format) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        final JsonElement element = this.getElement(i);
        if (element == null) {
            return null;
        }
        final String content = element.getAsString();
        try {
            return simpleDateFormat.parse(content);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new JsonParseException(i + " to Date");
        }
    }

}
