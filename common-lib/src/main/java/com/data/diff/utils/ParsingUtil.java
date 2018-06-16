package com.data.diff.utils;

import com.data.diff.constants.Constants;

public class ParsingUtil {

    public static <T> String objectToJson(T obj) {
        String jsonString = "";
        try {
            jsonString = Constants.OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static <T> T jsonToObject(String jsonString, Class<T> clazz) {
        T obj = null;
        try {
            obj = Constants.OBJECT_MAPPER.readValue(jsonString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
