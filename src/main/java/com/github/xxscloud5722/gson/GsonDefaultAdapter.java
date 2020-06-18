package com.github.xxscloud5722.gson;


import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Cat.
 */
public final class GsonDefaultAdapter implements JsonTarget {


    @Override
    public <T> T parseObject(final String json, final Class<T> type) {
        if (json == null || type == null) {
            return null;
        }
        return JSONObject.parseObject(json, type);
    }

    @Override
    public <T> T parseObject(final String json, final Type type) {
        if (json == null || type == null) {
            return null;
        }
        return JSONObject.parseObject(json, type);
    }


    @Override
    public JsonObject parseObject(final String json) {
        if (json == null) {
            return null;
        }
        return JSONObject.parseObject(json, JsonObject.class);
    }

    @Override
    @SuppressWarnings("ALL")
    public <T> List<T> parseArrayObject(final String json, final Type type) {
        if (json == null || type == null) {
            return null;
        }
        return (List<T>) JSONObject.parseArray(json, new Type[]{type});
    }

    @Override
    public <T> List<T> parseArrayObject(final String json, final Class<T> type) {
        if (json == null || type == null) {
            return null;
        }
        return JSONObject.parseArray(json, type);
    }

    @Override
    public JsonArray parseArrayObject(final String json) {
        if (json == null) {
            return null;
        }
        return (JsonArray) JSONObject.parseArray(json);
    }

    @Override
    public String stringify(final Object obj) {
        if (obj == null) {
            return null;
        }
        return JSONObject.toJSONString(obj);
    }


}
