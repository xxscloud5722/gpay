package com.xxscloud.gpay.gson;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

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
public final class JsonObject extends HashMap<String, Object> {
    private static final Pattern Z = Pattern.compile("^[-\\+]?[\\d]*$");

    public JsonObject() {

    }


    public JsonObject(final JsonElement jsonElement) {
        ((com.google.gson.JsonObject) jsonElement).entrySet().forEach(it -> {
            final JsonPrimitive jsonPrimitive = (JsonPrimitive) it.getValue();
            if (jsonPrimitive.isString()) {
                super.put(it.getKey(), jsonPrimitive.getAsString());
                return;
            }
            if (jsonPrimitive.isBoolean()) {
                super.put(it.getKey(), jsonPrimitive.getAsBoolean());
                return;
            }
            if (jsonPrimitive.isNumber()) {
                super.put(it.getKey(), jsonPrimitive.getAsNumber());
                return;
            }
            this.put(it.getKey(), it.getValue());
        });
    }

    public JsonObject(final Map<Object, Object> map) {
        ((com.google.gson.JsonObject) new Gson().toJsonTree(map)).entrySet().forEach(it -> {
            final JsonPrimitive jsonPrimitive = (JsonPrimitive) it.getValue();
            if (jsonPrimitive.isString()) {
                super.put(it.getKey(), jsonPrimitive.getAsString());
                return;
            }
            if (jsonPrimitive.isBoolean()) {
                super.put(it.getKey(), jsonPrimitive.getAsBoolean());
                return;
            }
            if (jsonPrimitive.isNumber()) {
                super.put(it.getKey(), jsonPrimitive.getAsNumber());
                return;
            }
            super.put(it.getKey(), it.getValue());
        });
    }

    public JsonObject(final String json) {
        final Type type = new TypeToken<JsonElement>() {
        }.getType();
        ((com.google.gson.JsonObject) JsonUtils.parseObject(json, type)).entrySet().forEach(it -> {
            final JsonPrimitive jsonPrimitive = (JsonPrimitive) it.getValue();
            if (jsonPrimitive.isString()) {
                super.put(it.getKey(), jsonPrimitive.getAsString());
                return;
            }
            if (jsonPrimitive.isBoolean()) {
                super.put(it.getKey(), jsonPrimitive.getAsBoolean());
                return;
            }
            if (jsonPrimitive.isNumber()) {
                super.put(it.getKey(), jsonPrimitive.getAsNumber());
                return;
            }
            super.put(it.getKey(), it.getValue());
        });
    }

    @Override
    public Object get(Object key) {
        return super.get(key);
    }

    //    @Override
//    public void put(String property, Object value) {
//        if (value == null) {
//            return value;
//        }
//        if (value instanceof String) {
//            this.put(property, new JsonPrimitive((String) value));
//            return value;
//        }
//        if (value instanceof Number) {
//            this.put(property, new JsonPrimitive((Number) value));
//            return value;
//        }
//        if (value instanceof Boolean) {
//            this.put(property, new JsonPrimitive((Boolean) value));
//            return value;
//        }
//        if (value instanceof Character) {
//            this.put(property, new JsonPrimitive((Character) value));
//            return value;
//        }
//        if (value instanceof JsonObject) {
//            this.put(property, new Gson().toJsonTree(new com.google.gson.JsonObject()));
//            return value;
//        }
//        this.put(property, new Gson().toJsonTree(value));
//        return value;
//    }

    @Override
    public String toString() {
        return JsonUtils.stringify(this);
    }

    public JsonArray getJsonArray(String memberName) {
        return new JsonArray(this.getElement(memberName).getAsJsonArray());
    }

    public JsonObject getJsonObject(String memberName) {
        return new JsonObject(this.getElement(memberName).getAsJsonObject());
    }


    public JsonElement getElement(Object key) {
        final Object obj = super.get(key);
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

    public Number getNumber(String i) {
        return this.getElement(i).getAsNumber();
    }

    public String getString(String i) {
        return this.getElement(i).getAsString();
    }

    public double getDouble(String i) {
        return this.getElement(i).getAsDouble();
    }

    public BigDecimal getBigDecimal(String i) {
        return this.getElement(i).getAsBigDecimal();
    }

    public BigInteger getBigInteger(String i) {
        return this.getElement(i).getAsBigInteger();
    }

    public float getFloat(String i) {
        return this.getElement(i).getAsFloat();
    }

    public long getLong(String i) {
        return this.getElement(i).getAsLong();
    }

    public int getInteger(String i) {
        return this.getElement(i).getAsInt();
    }

    public byte getByte(String i) {
        return this.getElement(i).getAsByte();
    }

    public char getCharacter(String i) {
        return this.getElement(i).getAsCharacter();
    }

    public short getShort(String i) {
        return this.getElement(i).getAsShort();
    }

    public boolean getBoolean(String i) {
        return this.getElement(i).getAsBoolean();
    }

    public Date getDate(String i) {
        final String content = this.getElement(i).getAsString();
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

    public Date getDate(String i, String format) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        final String content = this.getElement(i).getAsString();
        try {
            return simpleDateFormat.parse(content);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new JsonParseException(i + " to Date");
        }
    }

}
