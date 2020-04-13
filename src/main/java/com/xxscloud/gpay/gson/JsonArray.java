package com.xxscloud.gpay.gson;


import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;


/**
 * @author Cat.
 * Gson JsonArray Rewrite.
 */
public final class JsonArray extends ArrayList<JsonElement> {
    public JsonArray() {
    }

    public JsonArray(final String json) {
        final Type type = new TypeToken<JsonElement>() {
        }.getType();
        ((com.google.gson.JsonArray) JsonUtils.parseObject(json, type)).forEach(this::add);
    }

    public JsonArray(final JsonElement jsonArray) {
        jsonArray.getAsJsonArray().forEach(this::add);
    }


    public JsonObject getJsonObject(int i) {
        return new JsonObject(this.get(i).getAsJsonObject());
    }

    public JsonArray getJsonArray(int i) {
        return new JsonArray(this.get(i).getAsJsonArray());
    }

    public Number getNumber(int i) {
        return this.get(i).getAsNumber();
    }

    public String getString(int i) {
        return this.get(i).getAsString();
    }

    public double getDouble(int i) {
        return this.get(i).getAsDouble();
    }

    public BigDecimal getBigDecimal(int i) {
        return this.get(i).getAsBigDecimal();
    }

    public BigInteger getBigInteger(int i) {
        return this.get(i).getAsBigInteger();
    }

    public float getFloat(int i) {
        return this.get(i).getAsFloat();
    }

    public long getLong(int i) {
        return this.get(i).getAsLong();
    }

    public int getInteger(int i) {
        return this.get(i).getAsInt();
    }

    public byte getByte(int i) {
        return this.get(i).getAsByte();
    }

    public char getCharacter(int i) {
        return this.get(i).getAsCharacter();
    }

    public short getShort(int i) {
        return this.get(i).getAsShort();
    }

    public boolean getBoolean(int i) {
        return this.get(i).getAsBoolean();
    }


    public JsonObject getJsonObject() {
        if (this.size() == 1) {
            return new JsonObject(this.get(0).getAsJsonObject());
        } else {
            throw new IllegalStateException();
        }
    }

    public Number getNumber() {
        if (this.size() == 1) {
            return this.get(0).getAsNumber();
        } else {
            throw new IllegalStateException();
        }
    }

    public String getString() {
        if (this.size() == 1) {
            return this.get(0).getAsString();
        } else {
            throw new IllegalStateException();
        }
    }

    public double getDouble() {
        if (this.size() == 1) {
            return this.get(0).getAsDouble();
        } else {
            throw new IllegalStateException();
        }
    }

    public BigDecimal getBigDecimal() {
        if (this.size() == 1) {
            return this.get(0).getAsBigDecimal();
        } else {
            throw new IllegalStateException();
        }
    }

    public BigInteger getBigInteger() {
        if (this.size() == 1) {
            return this.get(0).getAsBigInteger();
        } else {
            throw new IllegalStateException();
        }
    }

    public float getFloat() {
        if (this.size() == 1) {
            return this.get(0).getAsFloat();
        } else {
            throw new IllegalStateException();
        }
    }

    public long getLong() {
        if (this.size() == 1) {
            return this.get(0).getAsLong();
        } else {
            throw new IllegalStateException();
        }
    }

    public int getInteger() {
        if (this.size() == 1) {
            return this.get(0).getAsInt();
        } else {
            throw new IllegalStateException();
        }
    }

    public byte getByte() {
        if (this.size() == 1) {
            return this.get(0).getAsByte();
        } else {
            throw new IllegalStateException();
        }
    }

    public char getCharacter() {
        if (this.size() == 1) {
            return this.get(0).getAsCharacter();
        } else {
            throw new IllegalStateException();
        }
    }

    public short getShort() {
        if (this.size() == 1) {
            return this.get(0).getAsShort();
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean getBoolean() {
        if (this.size() == 1) {
            return this.get(0).getAsBoolean();
        } else {
            throw new IllegalStateException();
        }
    }


    @Override
    public String toString() {
        return JsonUtils.stringify(this);
    }
}
