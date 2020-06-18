package com.github.xxscloud5722.gson;


import com.alibaba.fastjson.JSONArray;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.RandomAccess;


/**
 * @author Cat.
 * Gson JsonArray Rewrite.
 */
public final class JsonArray extends JSONArray implements List<Object>, Cloneable, RandomAccess, Serializable {
    public JsonArray() {
    }


    @NotNull
    public static JsonArray init() {
        return new JsonArray();
    }


    @NotNull
    public static JsonArray init(final String json) {
        return JsonUtils.parseArrayObject(json);
    }

    @Nullable
    public JsonObject getJsonObject(int i) {
        return (JsonObject) super.getJSONObject(i);
    }

    @Nullable
    public JsonArray getJsonArray(int i) {
        return (JsonArray) super.getJSONArray(i);
    }


    @Nullable
    public JsonObject getJsonObject() {
        if (this.size() == 1) {
            return (JsonObject) super.getJSONObject(0);
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public Number getNumber() {
        if (this.size() == 1) {
            return super.getDouble(0);
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public String getString() {
        if (this.size() == 1) {
            return super.getString(0);
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public Double getDouble() {
        if (this.size() == 1) {
            return super.getDouble(0);
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public BigDecimal getBigDecimal() {
        if (this.size() == 1) {
            return super.getBigDecimal(0);
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public BigInteger getBigInteger() {
        if (this.size() == 1) {
            return super.getBigInteger(0);
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public Float getFloat() {
        if (this.size() == 1) {
            return super.getFloat(0);
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public Long getLong() {
        if (this.size() == 1) {
            return super.getLong(0);
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public Integer getInteger() {
        if (this.size() == 1) {
            return super.getInteger(0);
        } else {
            throw new IllegalStateException();
        }
    }

    public byte getByte() {
        if (this.size() == 1) {
            return super.getByte(0);
        } else {
            throw new IllegalStateException();
        }
    }


    public Short getShort() {
        if (this.size() == 1) {
            return super.getShort(0);
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean getBoolean() {
        if (this.size() == 1) {
            return super.getBooleanValue(0);
        } else {
            throw new IllegalStateException();
        }
    }


    @Override
    public String toString() {
        return JsonUtils.stringify(this);
    }
}
