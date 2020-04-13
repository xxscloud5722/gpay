package com.xxscloud.gpay.gson;


import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Cat.
 */
public interface JsonTarget {


    /**
     * 解析字符串成指定对象.
     *
     * @param json 字符串.
     * @param type 类型.
     * @param <T>  模板.
     * @return 模板对象.
     */
    <T> T parseObject(String json, Class<T> type);

    /**
     * 解析字符串成指定对象.
     *
     * @param json 字符串.
     * @param type 类型.
     * @param <T>  模板.
     * @return 模板对象.
     */
    <T> T parseObject(String json, Type type);

    /**
     * 解析字符串成指定Map.
     *
     * @param json 字符串.
     * @return Map 对象.
     */
    JsonObject parseObject(String json);

    /**
     * 解析字符串成指定集合对象.
     *
     * @param json 字符串.
     * @param type 类型.
     * @param <T>  模板.
     * @return 模板集合对象.
     */
    <T> List<T> parseArrayObject(String json, Type type);

    /**
     * 解析字符串成指定集合Mao.
     *
     * @param json 字符串.
     * @return Map集合对象.
     */
    JsonArray parseArrayObject(String json);

    /**
     * 将任何对象解析成字符串.
     *
     * @param obj 对象.
     * @return JSON 字符串.
     */
    String stringify(Object obj);


}
