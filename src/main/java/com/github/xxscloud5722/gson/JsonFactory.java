package com.github.xxscloud5722.gson;


/**
 * @author Cat.
 */
public final class JsonFactory {
    public static JsonTarget getJsonObject() {
        return new GsonDefaultAdapter();
    }
}
