package com.github.xxscloud5722.gson;





import java.lang.reflect.Type;
import java.util.List;


/**
 * @author Cat.
 */
public final class JsonUtils {

    private JsonUtils() {
    }

    private static final JsonTarget JSON_TARGET =
            JsonFactory.getJsonObject();


    public static <T> T parseObject(String json, Class<T> type) {
        return JSON_TARGET.parseObject(json, type);
    }

    public static <T> T parseObject(String json, Type type) {
        return JSON_TARGET.parseObject(json, type);
    }


    public static JsonObject parseObject(String json) {
        return JSON_TARGET.parseObject(json);
    }


    public static <T> List<T> parseArrayObject(String json, Type type) {
        return JSON_TARGET.parseArrayObject(json, type);
    }


    public static JsonArray parseArrayObject(String json) {
        return JSON_TARGET.parseArrayObject(json);
    }


    public static String stringify(Object obj) {
        return JSON_TARGET.stringify(obj);
    }

    public static String stringify(Object obj, boolean flag) {
        final String value = JSON_TARGET.stringify(obj);
        return flag ? asUnicodeJson(value) : value;
    }

    public static String asUnicodeJson(String value) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            final char c = value.charAt(i);
            if (c <= 127) {
                stringBuilder.append(c);
            } else {
                StringBuilder v = new StringBuilder(Integer.toHexString(c));
                for (int k = v.length(); k < 4; k++) {
                    v.insert(0, "0");
                }
                stringBuilder.append("\\u").append(v.toString().toUpperCase());
            }
        }
        return stringBuilder.toString();
    }
}
