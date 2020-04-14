package com.github.xxscloud5722.gson;


/**
 * @author  Cat.
 */
public final class JsonFactory {
    public static JsonTarget getJsonObject() {
        final String id = System.getProperty("pudding.json");
        if (id != null) {
            try {
                final Class<?> clazz = Class.forName(id);
                if (clazz != null) {
                    final Object o = clazz.newInstance();
                    if (o instanceof JsonTarget) {
                        return (JsonTarget) o;
                    }
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return new GsonDefaultAdapter();
    }
}
