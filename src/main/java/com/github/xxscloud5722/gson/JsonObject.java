package com.github.xxscloud5722.gson;


import com.alibaba.fastjson.JSONObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Cat.
 * Gson JsonObject Rewrite.
 */
@SuppressWarnings("ALL")
public final class JsonObject extends JSONObject {
    private static final Pattern Z = Pattern.compile("^[-\\+]?[\\d]*$");


    public JsonObject() {

    }

    @NotNull
    @Contract(" -> new")
    public static JsonObject init() {
        return new JsonObject();
    }


    @NotNull
    public static JsonObject init(final Map<Object, Object> map) {
        final JsonObject data = new JsonObject();
        map.forEach((k, v) -> {
            if (k != null && v != null) {
                data.put(String.valueOf(k), v);
            }
        });
        return data;
    }

    @NotNull
    public static JsonObject init(final String json) {
        return JsonUtils.parseObject(json);
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
    public JsonArray getJsonArray(String memberName) {
        return (JsonArray) this.getJSONArray(memberName);
    }

    @Nullable
    public JsonObject getJsonObject(String memberName) {
        return (JsonObject) this.getJSONObject(memberName);
    }


}
