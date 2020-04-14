package com.github.xxscloud5722.gson;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Cat.
 */
public final class GsonDefaultAdapter implements JsonTarget {
    private static Gson GSON = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .disableHtmlEscaping()
            .create();


    @Override
    public <T> T parseObject(final String json, final Class<T> type) {
        if (json == null || type == null) {
            return null;
        }
        return GSON.fromJson(json, type);
    }

    @Override
    public <T> T parseObject(final String json, final Type type) {
        if (json == null || type == null) {
            return null;
        }
        return GSON.fromJson(json, type);
    }

    @Override
    public JsonObject parseObject(final String json) {
        if (json == null) {
            return null;
        }
        return GSON.fromJson(json, new TypeToken<JsonElement>() {
        }.getType());
    }

    @Override
    public <T> List<T> parseArrayObject(final String json, final Type type) {
        if (json == null || type == null) {
            return null;
        }
        return GSON.fromJson(json, type);
    }

    @Override
    public JsonArray parseArrayObject(final String json) {
        if (json == null) {
            return null;
        }
        return (JsonArray) GSON.fromJson(json, new TypeToken<JsonElement>() {
        }.getType());
    }

    @Override
    public String stringify(final Object obj) {
        if (obj == null) {
            return null;
        }
        return GSON.toJson(obj);
    }


}
