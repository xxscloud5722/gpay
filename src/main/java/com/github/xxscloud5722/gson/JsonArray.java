package com.github.xxscloud5722.gson;


import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    public JsonArray(@NotNull final JsonElement jsonArray) {
        jsonArray.getAsJsonArray().forEach(this::add);
    }


    @Nullable
    public JsonObject getJsonObject(int i) {
        final JsonElement element = this.get(i);
        if (element == null) {
            return null;
        }
        if (element.isJsonNull()) {
            return null;
        }
        return JsonObject.init(element.getAsJsonObject());
    }

    @Nullable
    public JsonArray getJsonArray(int i) {
        final JsonElement element = this.get(i);
        if (element == null) {
            return null;
        }
        if (element.isJsonNull()) {
            return null;
        }
        return new JsonArray(element.getAsJsonArray());
    }

    @Nullable
    public Number getNumber(int i) {
        final JsonElement element = this.get(i);
        if (element == null) {
            return null;
        }
        if (element.isJsonNull()) {
            return null;
        }
        return element.getAsNumber();
    }

    @Nullable
    public String getString(int i) {
        final JsonElement element = this.get(i);
        if (element == null) {
            return null;
        }
        if (element.isJsonNull()) {
            return null;
        }
        return element.getAsString();
    }

    @Nullable
    public Double getDouble(int i) {
        final JsonElement element = this.get(i);
        if (element == null) {
            return null;
        }
        if (element.isJsonNull()) {
            return null;
        }
        return element.getAsDouble();
    }

    @Nullable
    public BigDecimal getBigDecimal(int i) {
        final JsonElement element = this.get(i);
        if (element == null) {
            return null;
        }
        if (element.isJsonNull()) {
            return null;
        }
        return element.getAsBigDecimal();
    }

    @Nullable
    public BigInteger getBigInteger(int i) {
        final JsonElement element = this.get(i);
        if (element == null) {
            return null;
        }
        if (element.isJsonNull()) {
            return null;
        }
        return element.getAsBigInteger();
    }

    @Nullable
    public Float getFloat(int i) {
        final JsonElement element = this.get(i);
        if (element == null) {
            return null;
        }
        if (element.isJsonNull()) {
            return null;
        }
        return element.getAsFloat();
    }

    @Nullable
    public Long getLong(int i) {
        final JsonElement element = this.get(i);
        if (element == null) {
            return null;
        }
        if (element.isJsonNull()) {
            return null;
        }
        return element.getAsLong();
    }

    @Nullable
    public Integer getInteger(int i) {
        final JsonElement element = this.get(i);
        if (element == null) {
            return null;
        }
        if (element.isJsonNull()) {
            return null;
        }
        return element.getAsInt();
    }

    public byte getByte(int i) {
        final JsonElement element = this.get(i);
        if (element == null) {
            throw new NullPointerException();
        }
        if (element.isJsonNull()) {
            throw new NullPointerException();
        }
        return element.getAsByte();
    }

    public char getCharacter(int i) {
        final JsonElement element = this.get(i);
        if (element == null) {
            throw new NullPointerException();
        }
        if (element.isJsonNull()) {
            throw new NullPointerException();
        }
        return element.getAsCharacter();
    }

    public short getShort(int i) {
        final JsonElement element = this.get(i);
        if (element == null) {
            throw new NullPointerException();
        }
        if (element.isJsonNull()) {
            throw new NullPointerException();
        }
        return element.getAsShort();
    }

    @Nullable
    public Boolean getBoolean(int i) {
        final JsonElement element = this.get(i);
        if (element == null) {
            return null;
        }
        if (element.isJsonNull()) {
            return null;
        }
        return element.getAsBoolean();
    }


    @Nullable
    public JsonObject getJsonObject() {
        if (this.size() == 1) {
            final JsonElement element = this.get(0);
            if (element == null) {
                return null;
            }
            if (element.isJsonNull()) {
                return null;
            }
            return JsonObject.init(element.getAsJsonObject());
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public Number getNumber() {
        if (this.size() == 1) {
            final JsonElement element = this.get(0);
            if (element == null) {
                return null;
            }
            if (element.isJsonNull()) {
                return null;
            }
            return element.getAsNumber();
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public String getString() {
        if (this.size() == 1) {
            final JsonElement element = this.get(0);
            if (element == null) {
                return null;
            }
            if (element.isJsonNull()) {
                return null;
            }
            return element.getAsString();
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public Double getDouble() {
        if (this.size() == 1) {
            final JsonElement element = this.get(0);
            if (element == null) {
                return null;
            }
            if (element.isJsonNull()) {
                return null;
            }
            return element.getAsDouble();
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public BigDecimal getBigDecimal() {
        if (this.size() == 1) {
            final JsonElement element = this.get(0);
            if (element == null) {
                return null;
            }
            if (element.isJsonNull()) {
                return null;
            }
            return element.getAsBigDecimal();
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public BigInteger getBigInteger() {
        if (this.size() == 1) {
            final JsonElement element = this.get(0);
            if (element == null) {
                return null;
            }
            if (element.isJsonNull()) {
                return null;
            }
            return element.getAsBigInteger();
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public Float getFloat() {
        if (this.size() == 1) {
            final JsonElement element = this.get(0);
            if (element == null) {
                return null;
            }
            if (element.isJsonNull()) {
                return null;
            }
            return element.getAsFloat();
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public Long getLong() {
        if (this.size() == 1) {
            final JsonElement element = this.get(0);
            if (element == null) {
                return null;
            }
            if (element.isJsonNull()) {
                return null;
            }
            return element.getAsLong();
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public Integer getInteger() {
        if (this.size() == 1) {
            final JsonElement element = this.get(0);
            if (element == null) {
                return null;
            }
            if (element.isJsonNull()) {
                return null;
            }
            return element.getAsInt();
        } else {
            throw new IllegalStateException();
        }
    }

    public byte getByte() {
        if (this.size() == 1) {
            final JsonElement element = this.get(0);
            if (element == null) {
                throw new NullPointerException();
            }
            if (element.isJsonNull()) {
                throw new NullPointerException();
            }
            return element.getAsByte();
        } else {
            throw new IllegalStateException();
        }
    }

    public char getCharacter() {
        if (this.size() == 1) {
            final JsonElement element = this.get(0);
            if (element == null) {
                throw new NullPointerException();
            }
            if (element.isJsonNull()) {
                throw new NullPointerException();
            }
            return element.getAsCharacter();
        } else {
            throw new IllegalStateException();
        }
    }

    public short getShort() {
        if (this.size() == 1) {
            final JsonElement element = this.get(0);
            if (element == null) {
                throw new NullPointerException();
            }
            if (element.isJsonNull()) {
                throw new NullPointerException();
            }
            return element.getAsShort();
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public Boolean getBoolean() {
        if (this.size() == 1) {
            final JsonElement element = this.get(0);
            if (element == null) {
                return null;
            }
            if (element.isJsonNull()) {
                return null;
            }
            return element.getAsBoolean();
        } else {
            throw new IllegalStateException();
        }
    }


    @Override
    public String toString() {
        return JsonUtils.stringify(this);
    }
}
